package ru.fefu.ecommerceapi.services;

import jakarta.persistence.OptimisticLockException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.fefu.ecommerceapi.dto.order.OrderCreateDto;
import ru.fefu.ecommerceapi.dto.order.OrderDto;
import ru.fefu.ecommerceapi.dto.pagination.PageDto;
import ru.fefu.ecommerceapi.dto.pagination.PaginationParams;
import ru.fefu.ecommerceapi.entity.*;
import ru.fefu.ecommerceapi.exceptions.NotFoundException;
import ru.fefu.ecommerceapi.exceptions.OrderException;
import ru.fefu.ecommerceapi.mappers.AddressMapper;
import ru.fefu.ecommerceapi.mappers.CartMapper;
import ru.fefu.ecommerceapi.mappers.OrderMapper;
import ru.fefu.ecommerceapi.mappers.ProductVariationRepository;
import ru.fefu.ecommerceapi.repository.CartRepository;
import ru.fefu.ecommerceapi.repository.OrderPagingRepository;
import ru.fefu.ecommerceapi.repository.OrderRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Validated
@RequiredArgsConstructor
public class OrderService {

    private final CartService cartService;
    private final OrderRepository orderRepository;
    private final OrderPagingRepository orderPagingRepository;
    private final CartRepository cartRepository;
    private final ProductVariationRepository productRepository;
    private final AddressMapper addressMapper;
    private final CartMapper cartMapper;
    private final OrderMapper orderMapper;

    @Transactional
    @Retryable(retryFor = OptimisticLockException.class, maxAttempts = 3)
    public void create(@Valid OrderCreateDto orderCreateDto) {
        Order order = Order.builder()
                .name(orderCreateDto.getName())
                .email(orderCreateDto.getEmail())
                .receiveType(orderCreateDto.getReceiveType())
                .comment(orderCreateDto.getComment())
                .address(addressMapper.dtoToEntity(orderCreateDto.getAddress()))
                .build();

        List<ProductVariationOrder> productVariationOrderList = new ArrayList<>();
        Cart cart = cartRepository.findCartByUuid(orderCreateDto.getCartUuid())
                .orElseThrow(NotFoundException::new);

        List<ProductVariationCart> notActualProducts = cartService.findNotActualProducts(cart);
        if (!notActualProducts.isEmpty()) {
            throw new OrderException(cartMapper.entityToDto(cart, notActualProducts));
        }
        for (ProductVariationCart product : cart.getProducts()) {
            ProductVariation productVariation = productRepository.findBySku(product.getProductVariation().getSku())
                    .orElseThrow(OrderException::new);
            productVariation.setStock(productVariation.getStock()-product.getCount());
            productVariation.setCountOnFitting(productVariation.getCountOnFitting()+product.getCount());
            ProductVariationOrder productVariationOrder = new ProductVariationOrder();
            productVariationOrder.setOrder(order);
            productVariationOrder.setCount(product.getCount());
            productVariationOrder.setProductVariation(productVariation);
            productVariationOrderList.add(productVariationOrder);
        }
        order.setProductVariations(productVariationOrderList);
        orderRepository.save(order);
    }

    public Page<OrderDto> getPageDto(PaginationParams paginationParams) {
        Pageable pageable = PageRequest.of(paginationParams.getCurrentPage()-1,
                                                 paginationParams.getItemsOnPage());
        return orderPagingRepository.findAll(pageable).map(orderMapper::entityToDto);
    }

}
