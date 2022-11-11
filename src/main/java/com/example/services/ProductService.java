package com.example.services;

import com.example.dto.Message;
import com.example.dto.ProductCategoryDTO;
import com.example.dto.ProductDTO;
import com.example.dto.SubProductCategoryDTO;
import com.example.entities.Product;
import com.example.entities.ProductCategory;
import com.example.entities.SubProductCategory;
import com.example.repositories.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@CrossOrigin("http://localhost:4200")
@Service
@Transactional
public class ProductService {
    private static final Logger logger = LogManager.getLogger(ProductService.class);
    @Autowired
    CustomerRepository customerRepo;
    @Autowired
    EmployeeRepository employeeRepo;
    @Autowired
    RoleRepository roleRepo;
    @Autowired
    UserRepository userRepo;
    @Autowired
    ProductRepository productRepo;
    @Autowired
    ProductCategoryRepository productCategoryRepo;
    @Autowired
    SubProductCategoryRepository subProductCategoryRepo;

    public Message addNewProduct(ProductDTO productDTO) {
        Message m = new Message();
        logger.log(Level.INFO, "Begin adding new product");
        Product product = new Product();

        //find userCategory
        ProductCategory productCategory = findProductCategoryById(productDTO.getProductCategory().getId());
        logger.log(Level.INFO, "Product Category Id" + productCategory.getId());
        if (productCategory != null) {
            logger.log(Level.INFO, "Product Category was found" + productCategory.getProductCategoryName());
        }


        SubProductCategory subProductCategory = findSubProductCategoryById(productDTO.getSubProductCategory().getId());
        logger.log(Level.INFO, "Sub Product Category Id" + subProductCategory.getId());
        if (subProductCategory != null) {
            logger.log(Level.INFO, "Sub Product Category was found" + subProductCategory.getSubProductCategoryName());
        }

        product.setProductCategory(productCategory);
        product.setSubProductCategory(subProductCategory);
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setUnitPrice(productDTO.getUnitPrice());
        product.setImageUrl(productDTO.getImageUrl());
        product.setActive(productDTO.getActive());
        product.setUnitsInStock(productDTO.getUnitsInStock());
        product.setDateCreated(productDTO.getDateCreated());
        product.setLastUpdated(productDTO.getLastUpdated());

        productRepo.save(product);

        m.setInfo("Done with add new product");
        return m;
    }

    private SubProductCategory findSubProductCategoryById(int id) {
        SubProductCategory subProductCategory1 = null;
        Optional<SubProductCategory> spc = subProductCategoryRepo.findById(id);
        if (spc.isPresent()) {
            subProductCategory1 = spc.get();
            logger.log(Level.INFO, "Sub Product category was found");
        } else {
            logger.log(Level.INFO, "Can not find the sub product category");
        }
        return subProductCategory1;
    }

    private ProductCategory findProductCategoryById(int id) {
        ProductCategory productCategory1 = null;
        Optional<ProductCategory> pc = productCategoryRepo.findById(id);
        if (pc.isPresent()) {
            productCategory1 = pc.get();
            logger.log(Level.INFO, "Product category was found");
        } else {
            logger.log(Level.INFO, "Can not find the product category");
        }
        return productCategory1;
    }

    public List<Product> getAllProduct() {
        Message m = new Message();
        List<Product> list = null;
        list = productRepo.findAll();
//        m.setInfo("list:" + list);
        return list;
    }

    //Get all product category
    public List<ProductCategory> getAllProductCategory() {
//        logger.log(Level.INFO,"start get all product category.");
        List<ProductCategory> list = null;
        list = productCategoryRepo.findAll();
//        logger.log(Level.INFO,list);
        return list;
    }

    public List<SubProductCategory> getAllSubProductCategory() {
        List<SubProductCategory> list = null;
        list = subProductCategoryRepo.findAll();
        return list;
    }

    public List<Product> findProductByCategoryId(int id) {
        List<Product> list = null;
        list = productRepo.findProductByCategoryId(id);
//        System.out.println("ist" + list);
        return list;
    }

    public List<Product> findProductByCategoryIdAndSubCategoryId(int id1, int id2) {
        List<Product> list = null;
        list = productRepo.findProductByCategoryIdAndSubCategoryId(id1, id2);
        return list;
    }

    public Optional<Product> getProductById(int id) {
        Optional<Product> p = null;
        p = productRepo.findById(id);
        if (p != null) {
            logger.log(Level.INFO, "product found.");
        } else {
            logger.log(Level.INFO, "Can not find produc by id = " + id);
        }
        return p;
    }

    public Product updateProduct(ProductDTO productDTO) {
        int id = productDTO.getId();
        Optional<Product> product = getProductById(id);
        Product updateProduct = null;
        if (product.isPresent()) {
            logger.log(Level.INFO, "Product is present");
            updateProduct = product.get();

            ProductCategory pc = findProductCategoryById(productDTO.getProductCategory().getId());
            if (pc != null) {
                logger.log(Level.INFO, "ProductCategory object found" + pc.getProductCategoryName());
            }
            SubProductCategory spc = findSubProductCategoryById(productDTO.getSubProductCategory().getId());
            if (spc != null) {
                logger.log(Level.INFO, "SubProductCategory object found" + pc.getProductCategoryName());
            }

            updateProduct.setProductCategory(pc);
            updateProduct.setSubProductCategory(spc);
            updateProduct.setName(productDTO.getName());
            updateProduct.setDescription(productDTO.getDescription());
            updateProduct.setUnitPrice(productDTO.getUnitPrice());
            updateProduct.setImageUrl(productDTO.getImageUrl());
            updateProduct.setActive(productDTO.getActive());
            updateProduct.setUnitsInStock(productDTO.getUnitsInStock());
            updateProduct.setDateCreated(productDTO.getDateCreated());
            updateProduct.setLastUpdated(productDTO.getLastUpdated());

        }
        productRepo.save(updateProduct);
        return updateProduct;
    }
    //Add Ne Category Product
    public ProductCategory addNewCategory(int id, ProductCategoryDTO productCategoryDTO) {
        ProductCategory pc = null;
        String productCategoryName = productCategoryDTO.getProductCategoryName();

        List<ProductCategory> checkCategory = findProductCategoryByName(productCategoryName);
        if(checkCategory != null){
            logger.log(Level.INFO, "The category is not found. You can add it into database.");

        }
        pc = new ProductCategory();
        pc.setProductCategoryName(productCategoryName);
        productCategoryRepo.save(pc);
        return pc;
    }

    private List<ProductCategory> findProductCategoryByName(String productCategoryName) {
        List<ProductCategory> list = null;
        list =  productCategoryRepo.findByName(productCategoryName);
        return list;
    }

    //Add New Sub Category Product
    public SubProductCategory addNewSubCategory(SubProductCategoryDTO subProductCategoryDTO) {
//        int CategoryProductId = subProductCategoryDTO.getProductCategory().getId();
        SubProductCategory scp = new SubProductCategory();
        scp.setProductCategory(subProductCategoryDTO.getProductCategory());
        scp.setSubProductCategoryName(subProductCategoryDTO.getSubProductCategoryName());
        subProductCategoryRepo.save(scp);
        return scp;
    }

//    public List<Customer> getAllCustomer() {
//        List<Customer> list = null;
//        list = customerRepo.findAll();
//        logger.log(Level.INFO, list);
//        return list;
//    }

    //Add new User
//    public Message addUser(UserDTO userDTO) {
//        Message m = new Message();
//        logger.log(Level.INFO, "Begin adding new user");
//        User user = new User();
//        Customer customer;
//        Employee employee;
//        //find userCategory
//        Role userCategory = findUserCategoryById(userDTO.getUser_category_id());
//        int userCategoryId = userCategory.getId();
//
//        logger.log(Level.INFO, "UserCategory Id" + userCategoryId);
//        if (userCategory != null) {
//            logger.log(Level.INFO, "UserCategory was found" + userCategory.getUserCategoryName());
//        }
//        if (userCategoryId == 1 || userCategoryId == 2) {
//            m.setInfo("userCategoryId: " + userCategoryId);
//            employee = new Employee();
//            user.setUserCategory(userCategory);
//            user.setUsername(userDTO.getUsername());
//            user.setPassword(userDTO.getPassword());
//            user.setEmail(userDTO.getEmail());
//            user.setAddress(userDTO.getAddress());
//            user.setDateOrdered(userDTO.getDateOrdered());
//            user.setLastUpdated(userDTO.getLastUpdated());
//            employee.setUser(user);
//            userRepo.save(user);
//            employeeRepo.save(employee);
//
//        } else if (userCategory.getId() == 3) {
//            customer = new Customer();
//            user.setUserCategory(userCategory);
//            user.setUsername(userDTO.getUsername());
//            user.setPassword(userDTO.getPassword());
//            user.setEmail(userDTO.getEmail());
//            user.setAddress(userDTO.getAddress());
//            user.setDateOrdered(userDTO.getDateOrdered());
//            user.setLastUpdated(userDTO.getLastUpdated());
//            customer.setUser(user);
//            userRepo.save(user);
//            customerRepo.save(customer);
//        }
//
//        m.setInfo("Done with add new user");
//        return m;
//    }

//    private Role findUserCategoryById(int id) {
//        Role userCategory1 = null;
//        Optional<Role> uc = userCategoryRepo.findById(id);
//        if (uc.isPresent()) {
//            userCategory1 = uc.get();
//        }
//        return userCategory1;
//    }

//    public List<Role> getAllUserCategory() {
//        List<Role> list = userCategoryRepo.findAll();
//        return list;
//    }

//    public List<User> getAllUser() {
//        List<User> list = null;
//        list = userRepo.findAll();
//        return list;
//    }


//    public List<Employee> getAllEmployee() {
//        List<Employee> list = null;
//        list = employeeRepo.findAll();
//        return list;
//    }


//    //Get all role
//    public List<Role> getAllUserRole() {
//        List<Role> list = roleRepo.findAll();
//        return list;
//    }

}
