package ru.fefu.ecommerceapi.repository;

import org.springframework.data.repository.CrudRepository;
import ru.fefu.ecommerceapi.entity.Color;

import java.util.List;

public interface ColorRepository extends CrudRepository<Color, Long> {

    List<Color> findAll();

}
