package com.example.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@Table(name="sub_product_category")
public class SubProductCategory {
    @Id
    @Column(name="sub_product_category_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="sub_product_category_name")
    private String subProductCategoryName;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "subProductCategory")
    @JsonIgnore
    private Set<Product> products;

    @ManyToOne
    @JoinColumn(name = "product_category_id", nullable = false)
    private ProductCategory productCategory;


}
