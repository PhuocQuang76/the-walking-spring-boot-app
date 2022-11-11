package com.example.repositories;

import com.example.entities.SubProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface SubProductCategoryRepository extends JpaRepository<SubProductCategory, Integer> {
    @Query(value="select * from sub_product_category where product_category_id = ?1", nativeQuery=true)
    List<SubProductCategory> getAllSubCategoryProductByCategory(int product_category_id);

}
