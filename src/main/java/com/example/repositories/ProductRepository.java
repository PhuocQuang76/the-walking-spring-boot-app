package com.example.repositories;

import com.example.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository  extends JpaRepository<Product,Integer> {
    List<Product> findAll();

    @Query(value="select * from product where product_category_id =?1", nativeQuery = true)
    List<Product> findProductByCategoryId(int id);

    @Query(value = "select * from product where product_category_id = ?1 AND sub_product_category_id = ?2",nativeQuery = true)
    List<Product> findProductByCategoryIdAndSubCategoryId(int id1, int id2);
}


