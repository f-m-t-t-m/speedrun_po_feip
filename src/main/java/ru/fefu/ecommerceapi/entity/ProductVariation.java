package ru.fefu.ecommerceapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product_variations")
public class ProductVariation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sku;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "color_id")
    private Color color;
    private String size;
    private Long stock;
    @Builder.Default
    private Long countOnFitting = 0L;
    private BigDecimal price;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;
    @Version
    private Long version;

}
