package com.onlineshop.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "customer_order")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    private LocalDateTime createdTimeStamp;

    @UpdateTimestamp
    private LocalDateTime updatedTimeStamp;

    @ManyToOne
    private Customer customer;

//    @ManyToOne
//    private Address address;

    @OneToMany(mappedBy = "order")
    private List<OrderPosition> orderPositionList;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
}
