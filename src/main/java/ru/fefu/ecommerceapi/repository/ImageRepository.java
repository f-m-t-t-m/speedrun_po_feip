package ru.fefu.ecommerceapi.repository;

import org.springframework.data.repository.CrudRepository;
import ru.fefu.ecommerceapi.entity.Image;

public interface ImageRepository extends CrudRepository<Image, Long> {
}
