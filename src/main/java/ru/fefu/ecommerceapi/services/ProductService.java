package ru.fefu.ecommerceapi.services;

import jakarta.servlet.ServletContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.fefu.ecommerceapi.dto.ImageCreateDto;
import ru.fefu.ecommerceapi.dto.ProductCreateDto;
import ru.fefu.ecommerceapi.dto.ProductDto;
import ru.fefu.ecommerceapi.entity.Image;
import ru.fefu.ecommerceapi.entity.Product;
import ru.fefu.ecommerceapi.exceptions.NotFoundException;
import ru.fefu.ecommerceapi.mappers.ProductMapper;
import ru.fefu.ecommerceapi.repository.ImageRepository;
import ru.fefu.ecommerceapi.repository.ProductRepository;
import ru.fefu.ecommerceapi.services.pagination.PaginationService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;


@Service
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

    public void saveProduct(ProductCreateDto productCreateDto) {
        Product product = productMapper.createDtoToEntity(productCreateDto);
        product.getProductAttributes().forEach(attr -> attr.setProduct(product));
        productRepository.save(product);
        imageService.saveImages(productCreateDto.getImages(), product);
    }

}