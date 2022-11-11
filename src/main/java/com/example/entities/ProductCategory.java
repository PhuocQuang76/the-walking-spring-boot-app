package com.example.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@Table(name="product_category")
public class ProductCategory {
    @Id
    @Column(name="product_category_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="product_category_name")
    private String productCategoryName;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productCategory")
    @JsonIgnore
    private Set<Product> products;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productCategory")
    @JsonIgnore
    private Set<SubProductCategory> subProductCategories;

}
