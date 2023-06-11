package ru.fefu.ecommerceapi.services;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.fefu.ecommerceapi.dto.order.OrderCreateDto;
import ru.fefu.ecommerceapi.dto.order.OrderCreateProductVariation;
import ru.fefu.ecommerceapi.dto.order.OrderDto;
import ru.fefu.ecommerceapi.entity.Order;
import ru.fefu.ecommerceapi.entity.ProductVariation;
import ru.fefu.ecommerceapi.entity.ProductVariationOrder;
import ru.fefu.ecommerceapi.exceptions.OrderException;
import ru.fefu.ecommerceapi.mappers.AddressMapper;
import ru.fefu.ecommerceapi.mappers.ProductVariationRepository;
import ru.fefu.ecommerceapi.repository.OrderRepository;
import ru.fefu.ecommerceapi.services.pagination.PaginationService;

import java.util.ArrayList;
import java.util.List;

@Service
@Validated
@RequiredArgsConstructor
public class OrderService extends PaginationService<OrderDto> {

    private final OrderRepository orderRepository;
    private final ProductVariationRepository productRepository;
    private final AddressMapper addressMapper;

    @Transactional
    public void create(@Valid OrderCreateDto orderCreateDto) {
        Order order = Order.builder()
                .name(orderCreateDto.getName())
                .email(orderCreateDto.getEmail())
                .receiveType(orderCreateDto.getReceiveType())
                .comment(orderCreateDto.getComment())
                .address(addressMapper.dtoToEntity(orderCreateDto.getAddress()))
                .build();

        List<ProductVariationOrder> productVariationOrderList = new ArrayList<>();
        for (OrderCreateProductVariation product : orderCreateDto.getProductVariation()) {
            ProductVariation productVariation = productRepository.findBySku(product.getId())
                    .orElseThrow(OrderException::new);
            if (productVariation.getStock() < product.getCount()) {
                throw new OrderException("На складе недостаточно продукта " + product.getId());
            }
            ProductVariationOrder productVariationOrder = new ProductVariationOrder();
            productVariationOrder.setOrder(order);
            productVariationOrder.setCount(product.getCount());
            productVariationOrder.setProductVariation(productVariation);
            productVariationOrderList.add(productVariationOrder);
        }

        order.setProductVariations(productVariationOrderList);
        orderRepository.save(order);
    }

}
