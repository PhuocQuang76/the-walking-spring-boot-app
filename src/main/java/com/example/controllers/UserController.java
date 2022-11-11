package com.example.controllers;

import com.example.dto.UserDTO;
import com.example.entities.Customer;
import com.example.entities.Role;
import com.example.entities.User;
import com.example.payload.request.SignupRequest;
import com.example.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("api/main")
public class UserController {

    @Autowired
    UserService userService;

    //Get all customers
    @GetMapping("customers")
    public List<Customer> getAllCustomer(){
        return userService.getAllCustomer();
    }

    //Get all employees
//    @GetMapping("employees")
//    public List<Employee> getAllEmployee(){
//        return userService.getAllEmployee();
//    }

    //get user_category
    @GetMapping("roles")
    public List<Role> GetAllUserRole(){
        return userService.getAllUserRole();
    }

    //Get all users
    @GetMapping("users")
    public List<User> getAllUser(){
        return userService.getAllUser();
    }


    //Find all employee
    @GetMapping(value = "employees/info")
    public List<User> getUserByRoleId(@RequestParam int roleId){
        return userService.getAllUserByRoleId(roleId);
    }

    //Update User by user  Obbject
//    @PutMapping(value = "user/update")
//    public User updateUserByObject(@RequestBody UserDTO userDTO){
//        return userService.updateUserByObject(userDTO);
//    }

    @PutMapping(value = "user/update")
    public ResponseEntity<?> updateUserByObject(@RequestBody SignupRequest userDTO){
        return userService.updateUserByObject(userDTO);
    }

    @PutMapping(value = "employee/update")
    public ResponseEntity<?> updateEmployeeByObject(@RequestBody SignupRequest userDTO){
        return userService.updateEmployeeByObject(userDTO);
    }

    //Add new Employee
    @PostMapping("/employee/register")
    public ResponseEntity<?> addANewEmployee(@RequestBody SignupRequest userDTO){
        return userService.registerANewEmployee(userDTO);
    }

    //GetAddressFromUserByUserId
    @GetMapping("user/address/{id}")
    public String getAddressFromUserByUserId(@PathVariable int id){
        return userService.getAddressFromUserByUserId(id);
    }

    //Find User By Id
    @GetMapping("user/{id}")
    public User getUserById(@PathVariable  int id){
        return userService.getUserById(id);
    }


    @DeleteMapping("user/delete/{id}")
    public void deleteUserById(@PathVariable int id){
        userService.deleteUserById(id);
    }
}
