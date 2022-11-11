package com.example.dto;

import com.example.entities.ProductCategory;
import com.example.entities.SubProductCategory;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ProductDTO {
    private int id;
    private ProductCategory productCategory;
    private SubProductCategory subProductCategory;
    private String name;
    private String description;
    private double unitPrice;
    private String imageUrl;
    private boolean active;
    private int unitsInStock;
    private Date dateCreated;
    private Date lastUpdated;

    @JsonProperty(value="active")
    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean getActive() {
        return this.active;
    }
}

