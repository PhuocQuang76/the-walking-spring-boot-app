package com.example.controllers;

import com.example.dto.Message;
import com.example.dto.ProductCategoryDTO;
import com.example.dto.ProductDTO;
import com.example.dto.SubProductCategoryDTO;
import com.example.entities.*;
import com.example.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api/main")
public class ProductController {
    @Autowired
    ProductService productService;

    //Add new Product
    @PostMapping("/product/add")
    public Message addNewProduct(@RequestBody ProductDTO productDTO){
        return productService.addNewProduct(productDTO);
    }

    //Get all products
    @GetMapping("/products")
    public List<Product>  getAllProduct(){
        Message m = new Message();
        m.setInfo("list checked");
        return productService.getAllProduct();
    }


    //Get all product category
    @GetMapping("category/products")
    public List<ProductCategory> getAllProductCategory(){
        return productService.getAllProductCategory();
    }
    //get all sub product category
    @GetMapping("sub-category/products")
    public List<SubProductCategory> getAllSubProductCategory(){
        return productService.getAllSubProductCategory();
    }

    @GetMapping("products/category/{id}")
    public List<Product> findProductByProductCategory(@PathVariable int id){
        return productService.findProductByCategoryId(id);
    }

    //Get product by category product and sub category product
    @GetMapping("products/sub-category/{id1}/{id2}")
    public List<Product> findProductByCategoryAndSubCategory(@PathVariable int id1,@PathVariable  int id2){
        return productService.findProductByCategoryIdAndSubCategoryId(id1,id2);
    }

    //Get product by id
    @GetMapping("product/{id}")
    public Optional<Product> findProductById(@PathVariable int id){
        return productService.getProductById(id);
    }

    //Edit Product By Id
    @PutMapping("product/update")
    public Product updateProductById(@RequestBody ProductDTO productDTO){
        return productService.updateProduct(productDTO);
    }

    //add new Category Product
    @PostMapping("category/add/{id}")
    public ProductCategory addNewCategory(@PathVariable int id, @RequestBody ProductCategoryDTO productCategoryDTO){
        return productService.addNewCategory(id, productCategoryDTO);
    }

    //add new Category Product
    @PostMapping("category/add")
    public SubProductCategory addNewSubCategory(@RequestBody SubProductCategoryDTO subProductCategoryDTO){
        return productService.addNewSubCategory(subProductCategoryDTO);
    }


    //    //Add new user
//    @PostMapping("register/user")
//    public Message addUser(@RequestBody UserDTO userDTO){
//        Message message = theWalkingService.addUser(userDTO);
//        return message;
//    }

//    //Get all customers
//    @GetMapping("customers")
//    public List<Customer> getAllCustomer(){
//        return productService.getAllCustomer();
//    }
//
//    //Get all employees
//    @GetMapping("employees")
//    public List<Employee> getAllEmployee(){
//        return productService.getAllEmployee();
//    }
//
//    //get user_category
//    @GetMapping("roles")
//    public List<Role> GetAllUserRole(){
//        return productService.getAllUserRole();
//    }
//
//    //Get all users
//    @GetMapping("users")
//    public List<User> getAllUser(){
//        return productService.getAllUser();
//    }
}
