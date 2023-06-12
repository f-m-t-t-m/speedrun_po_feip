package ru.fefu.ecommerceapi.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.fefu.ecommerceapi.dto.product.ProductDto;
import ru.fefu.ecommerceapi.entity.Favorites;
import ru.fefu.ecommerceapi.entity.ProductVariation;
import ru.fefu.ecommerceapi.entity.User;
import ru.fefu.ecommerceapi.exceptions.NotFoundException;
import ru.fefu.ecommerceapi.mappers.ProductMapper;
import ru.fefu.ecommerceapi.repository.FavoritesRepository;
import ru.fefu.ecommerceapi.repository.ProductAttributesRepository;
import ru.fefu.ecommerceapi.repository.ProductRepository;
import ru.fefu.ecommerceapi.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoritesService {

    private final FavoritesRepository favoritesRepository;
    private final UserRepository userRepository;
    private final ProductAttributesRepository productAttributesRepository;
    private final ProductMapper productMapper;

    public void addToFavorites(Long userId, Long sku) {
        User user = userRepository.findById(userId).orElseThrow(NotFoundException::new);
        ProductVariation productVariation = productAttributesRepository.findBySku(sku)
                .orElseThrow(NotFoundException::new);
        Favorites favorites = new Favorites();
        favorites.setUser(user);
        favorites.setProductVariation(productVariation);
        favoritesRepository.save(favorites);
    }

    public List<ProductDto> getFavorites(Long userId) {
        return favoritesRepository.getFavoritesProductsByUserId(userId)
                .stream().map(productMapper::entityToDto).toList();
    }

    @Transactional
    public void deleteFromFavorites(Long userId, Long sku) {
        favoritesRepository.deleteByUserIdAndProductVariationSku(userId, sku);
    }

}
