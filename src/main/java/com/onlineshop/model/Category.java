package com.onlineshop.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "category")
    private List<Product> products;

//    @OneToMany(mappedBy = "parentCategory")
//    private Category childCategory;
//
//    @ManyToOne
//    private Category parentCategory;

//    @ManyToMany
//    private List<Product> products;
}
