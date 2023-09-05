package com.onlineshop.model;

import com.onlineshop.exception.ApiException;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @ManyToOne
    private Category category;

    @Min(0)
    private BigDecimal price;

    @Min(0)
    private Integer quantity;

//    @ManyToMany
//    @JoinTable(
//        name = "category_product",
//        joinColumns = @JoinColumn(name = "product_id"),
//        inverseJoinColumns = @JoinColumn(name = "category_id"))
//    private List<Category> categories;
//
//    @ManyToMany
//    @JoinTable(
//            name = "attribute_product",
//            joinColumns = @JoinColumn(name = "product_id"),
//            inverseJoinColumns = @JoinColumn(name = "attribute_id"))
//    private List<Attribute> attributes;

    public void increaseQuantity(Integer quantity) {
        this.quantity += quantity;
    }

    public void decreaseQuantity(Integer quantity) {
        if (this.quantity < quantity) {
            throw new ApiException(
                    HttpStatus.CONFLICT, "The quantity of the product cannot be decreased. Too low value."
            );
        }
        this.quantity -= quantity;
    }


}
