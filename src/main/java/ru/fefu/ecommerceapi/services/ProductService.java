package ru.fefu.ecommerceapi.services;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.fefu.ecommerceapi.dto.product.ProductCreateDto;
import ru.fefu.ecommerceapi.dto.product.ProductDto;
import ru.fefu.ecommerceapi.dto.product.ProductUpdateDto;
import ru.fefu.ecommerceapi.dto.product.ProductVariationDto;
import ru.fefu.ecommerceapi.entity.Product;
import ru.fefu.ecommerceapi.entity.ProductVariation;
import ru.fefu.ecommerceapi.exceptions.NotFoundException;
import ru.fefu.ecommerceapi.mappers.ProductMapper;
import ru.fefu.ecommerceapi.repository.CategoryRepository;
import ru.fefu.ecommerceapi.repository.ColorRepository;
import ru.fefu.ecommerceapi.repository.ProductRepository;
import ru.fefu.ecommerceapi.services.pagination.PaginationService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@Validated
@RequiredArgsConstructor
public class ProductService extends PaginationService<ProductDto> {

    private final ImageService imageService;
    private final ProductRepository productRepository;
    private final ColorRepository colorRepository;
    private final ProductMapper productMapper;
    private final CategoryRepository categoryRepository;

    public ProductDto getProductById(Long id) {
        return productRepository.findById(id)
                .map(productMapper::entityToDto)
                .orElseThrow(NotFoundException::new);
    }

    @Transactional
    public void saveProduct(@Valid ProductCreateDto productCreateDto) {
        Set<ProductVariation> productVariations = productCreateDto.getProductVariations().stream()
                        .map(pv ->
                            ProductVariation.builder()
                                    .price(pv.getPrice())
                                    .stock(pv.getStock())
                                    .size(pv.getSize())
                                    .color(colorRepository.findById(pv.getColorId()).orElseThrow(NotFoundException::new))
                                    .build()
                        ).collect(Collectors.toSet());

        Product product = Product.builder()
                .productVariations(productVariations)
                .category(categoryRepository.findById(productCreateDto.getCategoryId()).orElseThrow(NotFoundException::new))
                .brand(productCreateDto.getBrand())
                .name(productCreateDto.getName())
                .description(productCreateDto.getDescription())
                .build();
        product.getProductVariations().forEach(attr -> attr.setProduct(product));
        productRepository.save(product);
        imageService.saveImages(productCreateDto.getImages(), product);
    }

    @Transactional
    public void update(Long id, @Valid ProductUpdateDto productUpdateDto) {
        Product product = productRepository.findByIdWithVariations(id)
                .orElseThrow(NotFoundException::new);

        product.setBrand(productUpdateDto.getBrand());
        product.setName(productUpdateDto.getName());
        product.setDescription(productUpdateDto.getDescription());
        product.setCategory(categoryRepository.findById(productUpdateDto.getCategoryId())
                .orElseThrow(NotFoundException::new));
        product.getProductVariations().forEach(pv ->
            productUpdateDto.getProductVariations().stream()
                    .filter(pvDto -> pv.getSku().equals(pvDto.getSku()))
                    .findFirst()
                    .ifPresentOrElse(
                            pvDto -> {
                                pv.setSize(pvDto.getSize());
                                pv.setPrice(pvDto.getPrice());
                                pv.setStock(pvDto.getStock());
                                pv.setColor(colorRepository.findById(pvDto.getColorId()).orElseThrow(NotFoundException::new));
                                productUpdateDto.getProductVariations().remove(pvDto);
                            },
                            () -> product.getProductVariations().remove(pv))
        );

        Set<ProductVariation> productVariations = productUpdateDto.getProductVariations().stream()
                .map(pv ->
                        ProductVariation.builder()
                                .price(pv.getPrice())
                                .stock(pv.getStock())
                                .size(pv.getSize())
                                .color(colorRepository.findById(pv.getColorId()).orElseThrow(NotFoundException::new))
                                .build()
                ).collect(Collectors.toSet());
        product.getProductVariations().addAll(productVariations);
        product.getProductVariations().forEach(attr -> attr.setProduct(product));
        imageService.saveImages(productUpdateDto.getImagesToAdd(), product);
        imageService.deleteImages(productUpdateDto.getImageUrlsToDelete());
    }

}