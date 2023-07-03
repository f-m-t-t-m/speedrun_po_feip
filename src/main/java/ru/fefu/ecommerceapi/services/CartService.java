package ru.fefu.ecommerceapi.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.fefu.ecommerceapi.dto.cart.CartAddDto;
import ru.fefu.ecommerceapi.dto.cart.CartDto;
import ru.fefu.ecommerceapi.entity.Cart;
import ru.fefu.ecommerceapi.entity.ProductVariation;
import ru.fefu.ecommerceapi.entity.ProductVariationCart;
import ru.fefu.ecommerceapi.entity.User;
import ru.fefu.ecommerceapi.exceptions.NotFoundException;
import ru.fefu.ecommerceapi.mappers.CartMapper;
import ru.fefu.ecommerceapi.mappers.ProductVariationRepository;
import ru.fefu.ecommerceapi.repository.CartRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final FavoritesService favoritesService;
    private final CartMapper cartMapper;
    private final ProductVariationRepository productVariationRepository;

    public CartDto getCart(String uuid, User user) {
        Cart cart = cartRepository.findCartByUuid(uuid).orElseThrow(NotFoundException::new);
        List<ProductVariationCart> notActualProducts = findNotActualProducts(cart);
        if (!notActualProducts.isEmpty()) {
            addToFavorites(notActualProducts, user);
            countCartSummary(cart);
            cartRepository.save(cart);
        }
        return cartMapper.entityToDto(cart, notActualProducts);
    }

    public CartDto addItem(String cartUuid, CartAddDto cartAddDto, User user) {
        Cart cart = cartRepository.findCartByUuid(cartUuid).orElseGet(Cart::new);
        cart.setUuid(cartUuid);

        ProductVariation productVariation = productVariationRepository
                .findBySku(cartAddDto.getSku()).orElseThrow(NotFoundException::new);

        ProductVariationCart productVariationCart = ProductVariationCart.builder()
                .cart(cart)
                .productVariation(productVariation)
                .count(cartAddDto.getCount())
                .build();
        cart.getProducts().stream()
                        .filter(product -> product.getProductVariation().getSku()
                                .equals(cartAddDto.getSku()))
                        .findFirst()
                        .ifPresentOrElse(product -> product.setCount(cartAddDto.getCount()),
                                         () -> cart.getProducts().add(productVariationCart));

        List<ProductVariationCart> notActualProducts = findNotActualProducts(cart);
        if (!notActualProducts.isEmpty()) {
            addToFavorites(notActualProducts, user);
        }
        countCartSummary(cart);
        cartRepository.save(cart);
        return cartMapper.entityToDto(cart, notActualProducts);
    }

    public CartDto deleteProduct(String cartUuid, Long sku, User user) {
        Cart cart = cartRepository.findCartByUuid(cartUuid).orElseThrow(NotFoundException::new);

        cart.getProducts()
                .removeIf(product -> product.getProductVariation().getSku()
                        .equals(sku));

        List<ProductVariationCart> notActualProducts = findNotActualProducts(cart);
        if (!notActualProducts.isEmpty()) {
            addToFavorites(notActualProducts, user);
        }
        countCartSummary(cart);
        cartRepository.save(cart);
        return cartMapper.entityToDto(cart, notActualProducts);
    }

    public List<ProductVariationCart> findNotActualProducts(Cart cart) {
        List<ProductVariationCart> notActualProducts = cart.getProducts().stream()
                .filter(productVariationCart ->
                        productVariationCart.getCount() > productVariationCart.getProductVariation().getStock())
                .toList();
        cart.getProducts().removeIf(notActualProducts::contains);
        return notActualProducts;
    }

    private void addToFavorites(List<ProductVariationCart> products, User user) {
        if (user == null) {
            return;
        }
        products.stream()
                .map(product -> product.getProductVariation().getSku())
                .forEach(sku -> favoritesService.addToFavorites(sku, user));
    }

    private void countCartSummary(Cart cart) {
        Long productsCount = 0L;
        BigDecimal orderSum = BigDecimal.ZERO;
        for (ProductVariationCart product: cart.getProducts()) {
            productsCount += product.getCount();
            orderSum = orderSum.add(
                    product.getProductVariation().getPrice().multiply(BigDecimal.valueOf(product.getCount())));
        }
        cart.setProductsCount(productsCount);
        cart.setOrderSum(orderSum);
    }

}
