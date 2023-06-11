package ru.fefu.ecommerceapi.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.fefu.ecommerceapi.dto.product.ImageCreateDto;
import ru.fefu.ecommerceapi.dto.product.ImageDto;
import ru.fefu.ecommerceapi.entity.Image;
import ru.fefu.ecommerceapi.entity.Product;
import ru.fefu.ecommerceapi.exceptions.FileUploadException;
import ru.fefu.ecommerceapi.repository.ImageRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;

    public void saveImages(List<ImageCreateDto> imagesDto, Product product) {
        List<Image> images = new ArrayList<>();
        try {
            for (ImageCreateDto image: imagesDto) {
                Files.createDirectories(Path.of("src/main/images/" + product.getId()));
                String path = String.format("src/main/images/%d/%s",
                        product.getId(), image.getFile().getOriginalFilename());
                String url = String.format("/images/%d/%s",
                        product.getId(), image.getFile().getOriginalFilename());
                image.getFile().transferTo(new File(path).getAbsoluteFile());
                images.add(new Image().setProduct(product).setUrl(url).setColor(image.getColor()));
            }
        } catch (IOException e) {
            throw new FileUploadException(e);
        }
        imageRepository.saveAll(images);
    }

    public void deleteImages(Long productId, List<ImageDto> imageDtos) {
        for (ImageDto imageDto: imageDtos) {
            String imageFileName = imageDto.getUrl().substring(imageDto.getUrl().lastIndexOf('/') + 1);
            File image = new File(String.format("src/main/images/%d/%s", productId, imageFileName));
            if (!image.isFile()) {
                continue;
            }
            image.delete();
            imageRepository.deleteByUrl(imageDto.getUrl());
        }
    }

}
