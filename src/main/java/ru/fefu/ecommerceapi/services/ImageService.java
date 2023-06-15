package ru.fefu.ecommerceapi.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.fefu.ecommerceapi.dto.categories.CategoryCreateDto;
import ru.fefu.ecommerceapi.dto.product.ImageCreateDto;
import ru.fefu.ecommerceapi.dto.product.ImageDto;
import ru.fefu.ecommerceapi.entity.Color;
import ru.fefu.ecommerceapi.entity.Image;
import ru.fefu.ecommerceapi.entity.Product;
import ru.fefu.ecommerceapi.exceptions.FileUploadException;
import ru.fefu.ecommerceapi.exceptions.NotFoundException;
import ru.fefu.ecommerceapi.repository.ColorRepository;
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
    private final ColorRepository colorRepository;

    public void saveImages(List<ImageCreateDto> imagesDto, Product product) {
        List<Image> images = new ArrayList<>();
        try {
            for (ImageCreateDto image : imagesDto) {
                Files.createDirectories(Path.of("src/main/images/" + product.getId()));
                String path = String.format("src/main/images/%d/%s",
                        product.getId(), image.getFile().getOriginalFilename());
                String url = String.format("/images/%d/%s",
                        product.getId(), image.getFile().getOriginalFilename());
                image.getFile().transferTo(new File(path).getAbsoluteFile());
                Color color = colorRepository.findById(image.getColorId()).orElseThrow(NotFoundException::new);
                images.add(new Image().setProduct(product).setUrl(url).setColor(color));
            }
        } catch (IOException e) {
            throw new FileUploadException(e);
        }
        imageRepository.saveAll(images);
    }

    public String saveCategoryImage(CategoryCreateDto category) {
        try {
            Files.createDirectories(Path.of("src/main/images/categories/" + category.getName()));
            String path = String.format("src/main/images/categories/%s/%s", category.getName(),
                    category.getImage().getOriginalFilename());
            String url = String.format("/images/categories/%s/%s",
                    category.getName(),  category.getImage().getOriginalFilename());
            category.getImage().transferTo(new File(path).getAbsoluteFile());
            return url;
        } catch (IOException e) {
            throw new FileUploadException(e);
        }
    }

    public void deleteImages(List<String> imageUrls) {
        for (String imageUrl : imageUrls) {
            File image = new File("src/main" + imageUrl);
            if (!image.isFile()) {
                continue;
            }
            image.delete();
            imageRepository.deleteByUrl(imageUrl);
        }
    }

}
