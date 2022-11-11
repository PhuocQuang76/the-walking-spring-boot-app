package com.example.services;

import com.example.dto.UserDTO;
import com.example.entities.*;
import com.example.payload.request.SignupRequest;
import com.example.payload.response.MessageResponse;
import com.example.repositories.CustomerRepository;
import com.example.repositories.EmployeeRepository;
import com.example.repositories.RoleRepository;
import com.example.repositories.UserRepository;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.example.entities.ERole.EMPLOYEE;
import static com.example.entities.ERole.OWNER;


@CrossOrigin("http://localhost:4200")
@Transactional
@Service
public class UserService {
    private static final Logger logger = LogManager.getLogger(ProductService.class);
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    UserRepository userRepo;
    @Autowired
    CustomerRepository customerRepo;
    @Autowired
    EmployeeRepository employeeRepo;
    @Autowired
    RoleRepository roleRepo;

    public List<User> getAllUser() {
        List<User> list = null;
        list = userRepo.findAll();
        return list;
    }


    public List<Customer> getAllCustomer() {
        List<Customer> list = null;
        list = customerRepo.findAll();
        logger.log(Level.INFO, list);
        return list;
    }


    //Get all role
    public List<Role> getAllUserRole() {
        List<Role> list = roleRepo.findAll();
        return list;
    }

    public User getUserById(int id) {
        User user = null;
        user = userRepo.findById(id);
        logger.log(Level.INFO, user);
        return user;
    }

    public List<User> getAllUserByRoleId(int roleId) {
        List<User> list = null;
        list = userRepo.getAllUserByRoleId(roleId);
        System.out.println(list);
        return list;
    }

    public ResponseEntity<?> updateUserByObject(SignupRequest userDTO) {
        User user = null;
        int userId = userDTO.getUserId();
        String username = getUsernameByUserId(userId);
        System.out.println("username:"+ username);

        String email = getEmailByUserId(userId);
        System.out.println("email:"+ username);

//        String password = getPasswordByUserName(userId);
//        System.out.println("password:"+ username);

        String usernameDTO = userDTO.getUsername();
        String emailDTO = userDTO.getEmail();
//        String passwordDTO = userDTO.getPassword();


        user = getUserById(userId);
        if(user != null){
            logger.log(Level.INFO, "User found");
        }

        if (userRepo.existsByUsername(userDTO.getUsername()) &&  !username.equals(usernameDTO)) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepo.existsByEmail(userDTO.getEmail()) &&  !email.equals(emailDTO)) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }


        user.setUsername(userDTO.getUsername());
        user.setEmail(emailDTO);
        user.setPassword(userDTO.getPassword());
        user.setAddress(userDTO.getAddress());
        userRepo.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }




    //Add new Employee
    @PostMapping("/employee/register")
    public ResponseEntity<?> registerANewEmployee(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepo.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepo.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                signUpRequest.getRole(),
                encoder.encode(signUpRequest.getPassword()),
                signUpRequest.getAddress());


        //Customer customer = new Customer();
        Employee employee = new Employee();

        Set<Role> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role employeeRole = roleRepo.findByName(
                            ERole.EMPLOYEE)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(employeeRole);
            employee.setUser(user);

//			customer = new Customer();
//			customer.setUser(user);
        } else {
            //employee = new Employee();
            //employee.setUser(user);
            strRoles.forEach(role -> {
                switch (role.toLowerCase()) {
//				case "employee":
//					Role employeeRole = roleRepository.findByName(
//							ERole.EMPLOYEE)
//							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//					roles.add(employeeRole);
//
//					break;
                    case "owner":
                        Role ownerRole = roleRepo.findByName(
                                        ERole.OWNER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(ownerRole);

                        break;

                    default:
                        Role employeeRole = roleRepo.findByName(
                                        ERole.EMPLOYEE)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(employeeRole);

                        //customerRepository.save(customer);


                }

            });
        }

        user.setRoles(roles);

        userRepo.save(user);
        employeeRepo.save(employee);
        //customerRepository.save(customer);
        //customerRepository.save(customer);
        //employeeRepository.save(employee);
        // mail service to send plain text mail to user's email account about successful
        // registration
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    //Get Address from User By UserId
    public String getAddressFromUserByUserId(int id){
        String data = new String();
        data = (userRepo.getAddressFromUserByUserId(id));

        return data;
    }

    //Get userName by UserId
    public String getUsernameByUserId(int userId){
        String  userName = "";
        userName = userRepo.findUserNameByUserId(userId);
        return userName;
    }


    //GetEmailByUserId
    public String getEmailByUserId(int userId){
        String email = "";
        email = userRepo.findEmailByUserId(userId);
        return email;
    }

    private String getPasswordByUserName(int userId) {
        String password = "";
        password = userRepo.findPasswordByUserId(userId);
        return password;
    }

    //Delete User
    public int deleteUserById(int userId) {
        int returnValue  = 0;
        int role = 0;
        Customer customer = null;
        Employee employee = null;
        role = findUserRoleIdByUserId(userId);
        System.out.println("roleId:"+ role);
        if(role == 1 || role ==  2){
            employee = employeeRepo.findEmployee(userId);
            employeeRepo.delete(employee);
            System.out.println("employee:"+ employee);
            if(employee!=null){
                userRepo.deleteById(userId);
                returnValue = 1;
            }
        }else if(role == 3){
            customer = customerRepo.findCustomer(userId);
            customerRepo.delete(customer);
            System.out.println("customer:"+ customer);
            if(customer!=null){
                userRepo.deleteById(userId);
                returnValue = 1;
            }

        }

        return returnValue;
    }

    public int findUserRoleIdByUserId(int id){
        int role = 0;
        role = userRepo.findUserRoleIdByUserId(id);
        return role;
    }


    public ResponseEntity<?> updateEmployeeByObject(SignupRequest userDTO) {
        User user = null;
        int userId = userDTO.getUserId();
        String username = getUsernameByUserId(userId);
        System.out.println("username:"+ username);

        String email = getEmailByUserId(userId);
        System.out.println("email:"+ username);

//        String password = getPasswordByUserName(userId);
//        System.out.println("password:"+ username);

        String usernameDTO = userDTO.getUsername();
        String emailDTO = userDTO.getEmail();
//        String passwordDTO = userDTO.getPassword();


        user = getUserById(userId);
        if(user != null){
            logger.log(Level.INFO, "User found");
        }

        if (userRepo.existsByUsername(userDTO.getUsername()) &&  !username.equals(usernameDTO)) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepo.existsByEmail(userDTO.getEmail()) &&  !email.equals(emailDTO)) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }


        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setAddress(userDTO.getAddress());
        userRepo.save(user);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

}

