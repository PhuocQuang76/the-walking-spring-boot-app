package com.example.repositories;

import com.example.entities.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory,Integer> {

    @Query(value = "select * from product_category where product_category_name = ?1",nativeQuery = true)
    List<ProductCategory> findByName(String productCategoryName);
}
