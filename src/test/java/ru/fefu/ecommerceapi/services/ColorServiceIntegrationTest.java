package ru.fefu.ecommerceapi.services;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.fefu.ecommerceapi.AbstractDatabaseIntegrationTest;
import ru.fefu.ecommerceapi.dto.ColorDto;
import ru.fefu.ecommerceapi.entity.Color;
import ru.fefu.ecommerceapi.exceptions.NotFoundException;
import ru.fefu.ecommerceapi.repository.ColorRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ColorServiceIntegrationTest extends AbstractDatabaseIntegrationTest {

    @Autowired
    private ColorService colorService;
    @Autowired
    private ColorRepository colorRepository;

    @Test
    void getColors() {
        List<ColorDto> colors = colorService.getColors();
        assertEquals(3, colors.size());
    }

    @Test
    @Transactional
    void createColor() {
        ColorDto colorDto = ColorDto.builder().hexCode("#1234").name("random").build();
        colorService.createColor(colorDto);

        List<ColorDto> colors = colorService.getColors();

        assertEquals(4, colors.size());
    }


    @Test
    void editColor_ShouldThrowException_WhenGivenWrongId() {
        assertThrows(NotFoundException.class, () -> colorService.editColor(4L, new ColorDto()));
    }

    @Test
    @Transactional
    void editColor() {
        colorService.editColor(1L, ColorDto.builder().hexCode("#0000").name("new color").build());
        Color color = colorRepository.findById(1L).orElseThrow();
        assertEquals("#0000", color.getHexCode());
        assertEquals("new color", color.getName());
    }

}