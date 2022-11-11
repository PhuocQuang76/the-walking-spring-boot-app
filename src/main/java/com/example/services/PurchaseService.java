package com.example.services;

import com.example.dto.CreatePurchaseDTO;
import com.example.dto.Message;
import com.example.dto.PurchasedDTO;
import com.example.entities.EPurchaseStatus;
import com.example.entities.Product;
import com.example.entities.Purchase;
import com.example.entities.User;
import com.example.repositories.ProductRepository;
import com.example.repositories.PurchaseRepository;
import com.example.repositories.UserRepository;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
@CrossOrigin
@Service
@Transactional
public class PurchaseService {
    private static final Logger logger = LogManager.getLogger(ProductService.class);
    @Autowired
    UserRepository userRepo;
    @Autowired
    ProductRepository productRepo;
    @Autowired
    PurchaseRepository purchaseRepo;
    public Purchase createPurchase(CreatePurchaseDTO createPurchaseDTO) {
        int userId = createPurchaseDTO.getUserId();
        int productId = createPurchaseDTO.getProductId();

        logger.log(Level.INFO, "Find User By Id ");
        User user = null;
        user = userRepo.findById(userId);
        if(user != null){
            logger.log(Level.INFO, "The user was found.");
        }

        logger.log(Level.INFO, "Find Product By Id ");
        Optional<Product> product = productRepo.findById(productId);
        Product getProduct = null;

        if(product.isPresent()){
            logger.log(Level.INFO, "The product was found.");
            getProduct = product.get();
        }

        getProduct.setUnitsInStock(getProduct.getUnitsInStock() - 1);
        Purchase p = new Purchase();
        logger.log(Level.INFO, "start to crate purchase");

        p.setUser(user);
        p.setPurchaseStatus(EPurchaseStatus.CART);
        p.setPrice(getProduct.getUnitPrice());
        p.setQuantity(1);
        p.setProduct(getProduct);
        p.setTotal(getProduct.getUnitPrice() + (getProduct.getUnitPrice() *  0.1));
        logger.log(Level.INFO, "start to save.");
        productRepo.save(getProduct);
        purchaseRepo.save(p);
        logger.log(Level.INFO, "saved.");
        return p;

    }

    public List<Purchase> getPurchaseByUserIdAndPurchaseStatus(int userId, String purchaseStatus) {
        List<Purchase> purchase = null;
        purchase  = purchaseRepo.getPurchaseByUserIdAndPurchaseStatus(userId, purchaseStatus);
        //list = purchaseRepo.getPurchaseByUserIdAndStatusId(userId);

        return purchase;
    }



    public Purchase updatePurchaseByUserIdAndPurchaseId(int userId, int purchaseId, PurchasedDTO purchasedDTO) {
        Purchase purchase = null;
        purchase = findPurchaseByUserIdAndPurchaseId(userId, purchaseId);
//        if (purchase != null) {
//            logger.log(Level.INFO, "Purchase found.");
//        }
//        int productId = purchase.getProduct().getId();
        Product product = null;
        product = purchase.getProduct();
        product.setUnitsInStock(purchasedDTO.getProduct().getUnitsInStock());
        productRepo.save(product);

        purchase.setQuantity(purchasedDTO.getQuantity());
        purchase.setPurchaseStatus(purchasedDTO.getPurchaseStatus());
        purchase.setProduct(product);
        purchase.setTotal(purchasedDTO.getTotal());
        purchaseRepo.save(purchase);
        return purchase;
    }

    public Purchase findPurchaseByUserIdAndPurchaseId(int userId, int purchaseId) {
        Purchase purchase = null;
        purchase = purchaseRepo.getPurchaseByIdAndUserId(userId,purchaseId);
        if(purchase != null){
            logger.log(Level.INFO, "The purchase was found.");
        }
        return purchase;
    }

    public Message updatePurchaseStatus(int userId, int purchaseId,  PurchasedDTO purchasedDTO) {
        Message m = new Message();
        Purchase purchase = null;
        purchase = purchaseRepo.getPurchaseByIdAndUserId(userId,purchaseId);
        if(purchase != null){
            logger.log(Level.INFO, "The purchase was found.");
        }
        purchase.setPurchaseStatus(purchasedDTO.getPurchaseStatus());
        purchaseRepo.save(purchase);
        m.setInfo ("Your purchase has been summited.");
        return m;
    }

    public Message updatePurchaseStatusToCancel(int userId, int purchaseId, PurchasedDTO purchasedDTO) {
        Message m = new Message();
        Purchase purchase = null;
        purchase = purchaseRepo.getPurchaseByIdAndUserId(userId,purchaseId);
        if(purchase != null){
            logger.log(Level.INFO, "The purchase was found.");
        }

        Product product = null;
        product = purchase.getProduct();
        product.setUnitsInStock(purchasedDTO.getProduct().getUnitsInStock());
        productRepo.save(product);

        purchase.setProduct(product);
        purchase.setPurchaseStatus(purchasedDTO.getPurchaseStatus());
        purchaseRepo.save(purchase);
        m.setInfo ("Your purchase has been summited.");
        return m;
    }

    //get Pending Status Purchases
    public List<Purchase> getStatusPurchases(String purchaseStatus) {
        List<Purchase> purchase = null;
        purchase  = purchaseRepo.getStatusPurchases(purchaseStatus);
        return purchase;

    }

    //update purchase by purchaseObject
    public Purchase  updatePurchaseByPurchaseObject(int id,PurchasedDTO purchasedDTO){
        Purchase p = null;
        Optional<Purchase> findPurchase = null;
        findPurchase = purchaseRepo.findById(id);
        if(findPurchase.isPresent()){
            p= findPurchase.get();
        }
        p.setPurchaseStatus(purchasedDTO.getPurchaseStatus());
        purchaseRepo.save(p);
        return p;
    }


}
