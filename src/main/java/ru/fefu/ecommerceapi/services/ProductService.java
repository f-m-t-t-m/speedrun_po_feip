package ru.fefu.ecommerceapi.services;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.fefu.ecommerceapi.dto.product.ProductCreateDto;
import ru.fefu.ecommerceapi.dto.product.ProductDto;
import ru.fefu.ecommerceapi.dto.product.ProductUpdateDto;
import ru.fefu.ecommerceapi.entity.Product;
import ru.fefu.ecommerceapi.exceptions.NotFoundException;
import ru.fefu.ecommerceapi.mappers.ProductMapper;
import ru.fefu.ecommerceapi.repository.ProductRepository;
import ru.fefu.ecommerceapi.services.pagination.PaginationService;


@Service
@Validated
@RequiredArgsConstructor
public class ProductService extends PaginationService<ProductDto> {

    private final ImageService imageService;
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductDto getProductById(Long id) {
        return productRepository.findById(id)
                .map(productMapper::entityToDto)
                .orElseThrow(NotFoundException::new);
    }

    @Transactional
    public void saveProduct(@Valid ProductCreateDto productCreateDto) {
        Product product = productMapper.createDtoToEntity(productCreateDto);
        product.getProductAttributes().forEach(attr -> attr.setProduct(product));
        productRepository.save(product);
        imageService.saveImages(productCreateDto.getImages(), product);
    }

    @Transactional
    public void update(Long id, @Valid ProductUpdateDto productUpdateDto) {
        Product product = productRepository.findByIdWithVariations(id)
                .orElseThrow(NotFoundException::new);
        productMapper.updateProduct(productUpdateDto, product);
        product.getProductAttributes().forEach(attr -> attr.setProduct(product));
        imageService.saveImages(productUpdateDto.getImagesToAdd(), product);
        imageService.deleteImages(product.getId(), productUpdateDto.getImagesToDelete());
    }

}