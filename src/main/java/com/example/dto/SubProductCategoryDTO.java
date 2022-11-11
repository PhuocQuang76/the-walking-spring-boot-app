package com.example.dto;

import com.example.entities.Product;
import com.example.entities.ProductCategory;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubProductCategoryDTO {
    private int id;
    private String subProductCategoryName;
    private Product products;
    private ProductCategory productCategory;
}
