package ru.fefu.ecommerceapi.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.fefu.ecommerceapi.dto.ColorDto;
import ru.fefu.ecommerceapi.entity.Color;
import ru.fefu.ecommerceapi.exceptions.NotFoundException;
import ru.fefu.ecommerceapi.mappers.ColorMapper;
import ru.fefu.ecommerceapi.repository.ColorRepository;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ColorService {

    private final ColorRepository colorRepository;
    private final ColorMapper colorMapper;

    public List<ColorDto> getColors() {
        return colorRepository.findAll().stream()
                .map(colorMapper::entityToDto)
                .toList();
    }

    public Long createColor(ColorDto colorDto) {
        Color color = colorMapper.dtoToEntity(colorDto);
        colorRepository.save(color);
        return color.getId();
    }

    @Transactional
    public void editColor(Long colorId, ColorDto colorDto) {
        Color color = colorRepository.findById(colorId).orElseThrow(NotFoundException::new);
        colorMapper.updateEntity(color, colorDto);
    }

}
