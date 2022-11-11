package com.example.repositories;

import com.example.entities.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase,Integer> {

    @Query(value="select * from purchase where user_id = ?1 AND purchase_status = ?2", nativeQuery = true)
    List<Purchase> getPurchaseByUserIdAndPurchaseStatus(int userId, String purchase_status);

    @Query(value="select * from purchase where user_id = ?1 AND purchase_id = ?2", nativeQuery = true)
    Purchase getPurchaseByIdAndUserId(int userId,int purchaseId);

    @Query(value = "select * from purchase where purchase_status = ?1", nativeQuery = true)
    List<Purchase> getStatusPurchases(String purchase_status);
}
