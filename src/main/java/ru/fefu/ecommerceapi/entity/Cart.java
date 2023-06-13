package ru.fefu.ecommerceapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.*;

@Entity
@Getter
@Setter
@Table(name = "carts")
@NoArgsConstructor
@AllArgsConstructor
@NamedEntityGraph(name = "graph.Cart",
        attributeNodes = @NamedAttributeNode(value = "products", subgraph = "productVariation"),
        subgraphs = {
                @NamedSubgraph(name = "productVariation", attributeNodes = @NamedAttributeNode(value = "productVariation",
                        subgraph = "subgraph-product")),
                @NamedSubgraph(name = "subgraph-product", attributeNodes = @NamedAttributeNode(value = "product",
                        subgraph = "images")),
                @NamedSubgraph(name = "images", attributeNodes = @NamedAttributeNode("images"))
        })
public class Cart {

    @Id
    private String uuid;
    @OneToMany(mappedBy = "cart", fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private Set<ProductVariationCart> products = new LinkedHashSet<>();
    private Long productsCount;
    private BigDecimal orderSum;
    private BigDecimal discountSum;
    private BigDecimal orderSumWithDiscount;

}
