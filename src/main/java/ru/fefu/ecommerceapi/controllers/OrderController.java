package ru.fefu.ecommerceapi.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.fefu.ecommerceapi.dto.order.OrderCreateDto;
import ru.fefu.ecommerceapi.dto.pagination.PaginationParams;
import ru.fefu.ecommerceapi.services.OrderService;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    private ResponseEntity<?> get(PaginationParams paginationParams) {
        paginationParams.setPaginationAbleClass("orderPaginationAble");
        return ResponseEntity.ok(orderService.getPageDto(paginationParams));
    }


    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    private ResponseEntity<?> create(@RequestBody OrderCreateDto order) {
        orderService.create(order);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
