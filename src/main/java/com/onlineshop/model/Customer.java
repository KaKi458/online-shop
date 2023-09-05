package com.onlineshop.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email
    @NotBlank
    private String email;

//    @NotBlank
//    @Min(8)
//    @Max(25)
    private String password;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

//    @OneToMany(mappedBy = "customer")
//    private List<Address> addressList;

    @OneToMany(mappedBy = "customer")
    private List<Order> orders;

    @OneToMany(mappedBy = "customer")
    private List<ShoppingCartPosition> shoppingCart;
}
