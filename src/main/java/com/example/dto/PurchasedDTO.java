package com.example.dto;

import com.example.entities.EPurchaseStatus;
import com.example.entities.Product;
import com.example.entities.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PurchasedDTO {
    private int purchaseId;
    private int purchaseStatusId;
    private User user;
    private EPurchaseStatus purchaseStatus;
    private Product product;
    private int quantity;
    private double price;
    private double total;

}
