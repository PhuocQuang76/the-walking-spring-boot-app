package com.example.controllers;

import com.example.dto.CreatePurchaseDTO;
import com.example.dto.Message;
import com.example.dto.PurchasedDTO;
import com.example.entities.Purchase;
import com.example.services.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/main")
public class PurchaseController {
    @Autowired
    PurchaseService purchaseService;

    //create new Purchase
    @PostMapping(value="create/purchase")
    public Purchase createPurchase(@RequestBody CreatePurchaseDTO createPurchase){
        return purchaseService.createPurchase(createPurchase);
    }

    //Get All Purchase By UserId and purchase Status ="Cart"
    @GetMapping(value="purchases/{userId}")
    public List<Purchase> getPurchaseByUserIdAndPurchaseStatus(@PathVariable int userId, @RequestParam String purchaseStatus){
        return purchaseService.getPurchaseByUserIdAndPurchaseStatus(userId, purchaseStatus);
    }

    //Find purchase By Id
    @GetMapping(value="purchase/{userId}/{productId}")
    public Purchase findPurchaseByUserIdAndProductId(@PathVariable int userId, @PathVariable int productId){
        return purchaseService.findPurchaseByUserIdAndPurchaseId(userId, productId);
    }


    //update purchase by UserId and PurchaseId
    @PutMapping(value="purchase/update/{userId}/{purchaseId}")
    public Purchase updatePurchaseByUserIdAndProductId(@PathVariable int userId, @PathVariable int purchaseId,@RequestBody PurchasedDTO purchasedDTO){
        return purchaseService.updatePurchaseByUserIdAndPurchaseId(userId,purchaseId, purchasedDTO);
    }

    //Update purchase Status by UserId And purchaseId
    @PutMapping(value="buy/{userId}/{purchaseId}")
    public Message updatePurchaseStatus(@PathVariable int userId, @PathVariable int purchaseId, @RequestBody PurchasedDTO purchasedDTO){
        return purchaseService.updatePurchaseStatus(userId, purchaseId, purchasedDTO);
    }


    //update purchase status by cancel by user Id and purchase id
    @PutMapping(value="cancel/{userId}/{purchaseId}")
    public Message updatePurchaseStatusToCancel(@PathVariable int userId, @PathVariable int purchaseId, @RequestBody PurchasedDTO purchasedDTO){
        return purchaseService.updatePurchaseStatusToCancel(userId, purchaseId, purchasedDTO);
    }
    //Get spending status purchase
    @GetMapping(value="purchases/purchase-status")
    public List<Purchase> getStatusPurchases(@RequestParam  String purchaseStatus){
        return purchaseService.getStatusPurchases(purchaseStatus);
    }

    //updatePurchaseByPurchaseObject
    @PutMapping(value="purchase/pending/update/{id}")
    public Purchase updatePurchaseByPurchaseObject(@PathVariable int id, @RequestBody PurchasedDTO purchasedDTO){
        return purchaseService.updatePurchaseByPurchaseObject(id,purchasedDTO);
    }
}
