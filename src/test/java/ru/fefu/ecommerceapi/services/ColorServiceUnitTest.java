package ru.fefu.ecommerceapi.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.fefu.ecommerceapi.dto.ColorDto;
import ru.fefu.ecommerceapi.entity.Color;
import ru.fefu.ecommerceapi.exceptions.NotFoundException;
import ru.fefu.ecommerceapi.repository.ColorRepository;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class ColorServiceUnitTest {

    @MockBean
    private ColorRepository colorRepository;
    @Autowired
    private ColorService colorService;

    @Test
    void getColors() {
        Color color1 = Color.builder().hexCode("#1234").id(1L).name("random").build();
        Color color2 = Color.builder().hexCode("#1234").id(2L).name("random").build();
        when(colorRepository.findAll()).thenReturn(List.of(color1, color2));

        List<ColorDto> colors = colorService.getColors();

        assertEquals(colors.size(), 2);
        verify(colorRepository, times(1)).findAll();
    }

    @Test
    void createColor() {
        Color color1 = Color.builder().hexCode("#1234").name("random").id(1L).build();
        when(colorRepository.save(any(Color.class))).thenReturn(color1);

        ColorDto colorDto = ColorDto.builder().hexCode("#1234").name("random").build();
        Long id = colorService.createColor(colorDto);

        assertEquals(id, 1L);
        verify(colorRepository, times(1)).save(any(Color.class));
    }

    @Test
    void editColor_withWrongId() {
        when(colorRepository.findById(any(Long.class))).thenThrow(NotFoundException.class);
        assertThrows(NotFoundException.class, () -> colorService.editColor(1L, new ColorDto()));
        verify(colorRepository, never()).save(any(Color.class));
    }

    @Test
    void editColor() {
        when(colorRepository.findById(any(Long.class))).thenReturn(Optional.of(new Color()));
        colorService.editColor(1L, new ColorDto());
        verify(colorRepository, times(1)).findById(any(Long.class));
    }

}