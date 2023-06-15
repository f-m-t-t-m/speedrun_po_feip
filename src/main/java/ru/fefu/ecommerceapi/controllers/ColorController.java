package ru.fefu.ecommerceapi.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.fefu.ecommerceapi.dto.ColorDto;
import ru.fefu.ecommerceapi.services.ColorService;

@RestController
@RequestMapping("/colors")
@RequiredArgsConstructor
public class ColorController {

    private final ColorService colorService;

    @GetMapping
    public ResponseEntity<?> getAllColors() {
        return ResponseEntity.ok(colorService.getColors());
    }

    @PostMapping
    public ResponseEntity<?> createColor(@RequestBody ColorDto colorDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(colorService.createColor(colorDto));
    }

    @PutMapping("/{colorId}")
    public ResponseEntity<?> updateColor(@PathVariable Long colorId, @RequestBody ColorDto colorDto) {
        colorService.editColor(colorId, colorDto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
