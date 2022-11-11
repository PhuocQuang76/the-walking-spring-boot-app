package com.example.dto;

import com.example.entities.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductCategoryDTO {
    private int id;
    private String productCategoryName;
    private Product products;
    private int subProductCategories;
}
