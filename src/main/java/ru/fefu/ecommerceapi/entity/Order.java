package ru.fefu.ecommerceapi.entity;

import jakarta.persistence.*;
import lombok.*;
import ru.fefu.ecommerceapi.dto.enums.ReceiveType;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    @Enumerated(EnumType.STRING)
    private ReceiveType receiveType;
    private String comment;
    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<ProductVariationOrder> productVariations = new ArrayList<>();
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "address_id")
    private Address address;

}
