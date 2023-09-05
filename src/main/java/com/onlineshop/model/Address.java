//package com.onlineshop.model;
//
//import jakarta.persistence.*;
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.Pattern;
//import lombok.*;
//
//import java.util.List;
//
//@Entity
//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
//@Builder
//public class Address {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne
//    private Customer customer;
//
//    @NotBlank
//    private String street;
//
//    @NotBlank
//    private String number;
//
//    @NotBlank
//    @Pattern(regexp = "[0-9]{2}-[0-9]{3}")
//    private String zipCode;
//
//    @NotBlank
//    private String city;
//
//    @OneToMany(mappedBy = "address")
//    private List<Order> orders;
//}
