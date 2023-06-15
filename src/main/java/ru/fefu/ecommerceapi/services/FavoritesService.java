package ru.fefu.ecommerceapi.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.fefu.ecommerceapi.dto.product.ShortProductDto;
import ru.fefu.ecommerceapi.entity.Favorites;
import ru.fefu.ecommerceapi.entity.ProductVariation;
import ru.fefu.ecommerceapi.entity.User;
import ru.fefu.ecommerceapi.exceptions.NotFoundException;
import ru.fefu.ecommerceapi.mappers.ProductMapper;
import ru.fefu.ecommerceapi.repository.FavoritesRepository;
import ru.fefu.ecommerceapi.repository.ProductAttributesRepository;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoritesService {

    private final FavoritesRepository favoritesRepository;
    private final ProductAttributesRepository productAttributesRepository;
    private final ProductMapper productMapper;

    public void addToFavorites(Long sku, User user) {
        ProductVariation productVariation = productAttributesRepository.findBySku(sku)
                .orElseThrow(NotFoundException::new);
        if (isProductInFavorites(sku, user)) {
            return;
        }
        Favorites favorites = new Favorites();
        favorites.setUser(user);
        favorites.setProductVariation(productVariation);
        favoritesRepository.save(favorites);
    }

    public boolean isProductInFavorites(Long sku, User user) {
        return favoritesRepository.findByUserIdAndProductVariationSku(user.getId(), sku).isPresent();
    }

    public List<ShortProductDto> getFavorites(User user) {
        return productMapper.favoritesToShortDto(favoritesRepository.getFavoritesProductsByUserId(user.getId()));
    }

    @Transactional
    public void deleteFromFavorites(Long sku, User user) {
        favoritesRepository.deleteByUserIdAndProductVariationSku(user.getId(), sku);
    }

}
