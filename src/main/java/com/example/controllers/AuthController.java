package com.example.controllers;

import com.example.entities.*;
import com.example.payload.request.LoginRequest;
import com.example.payload.request.SignupRequest;
import com.example.payload.response.JwtResponse;
import com.example.payload.response.MessageResponse;
import com.example.repositories.CustomerRepository;
import com.example.repositories.EmployeeRepository;
import com.example.repositories.RoleRepository;
import com.example.repositories.UserRepository;
import com.example.security.jwt.JwtUtils;
import com.example.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin
//@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	EmployeeRepository employeeRepository;
	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		jwtUtils.addGeneratedToken(jwt);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(new JwtResponse(jwt,
												 userDetails.getId(),
												 userDetails.getUsername(), 
												 userDetails.getEmail(), 
												 roles));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Username is already taken!"));
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
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


		Customer customer = new Customer();
//		Employee employee = new Employee();

		Set<Role> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();

		if (strRoles == null) {
			Role customerRole = roleRepository.findByName(
					ERole.CUSTOMER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(customerRole);
			customer.setUser(user);

//			customer = new Customer();
//			customer.setUser(user);
		} else {
			//employee = new Employee();
			//employee.setUser(user);
			strRoles.forEach(role -> {
				switch (role.toLowerCase()) {
				case "employee":
					Role employeeRole = roleRepository.findByName(
							ERole.EMPLOYEE)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(employeeRole);

					break;
				case "owner":
					Role ownerRole = roleRepository.findByName(
							ERole.OWNER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(ownerRole);

					break;

				default:
					Role customerRole = roleRepository.findByName(
							ERole.CUSTOMER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(customerRole);

					//customerRepository.save(customer);
					
					
				}

			});
		}

		user.setRoles(roles);

		userRepository.save(user);
		customerRepository.save(customer);
		//customerRepository.save(customer);
		//customerRepository.save(customer);
		//employeeRepository.save(employee);
		// mail service to send plain text mail to user's email account about successful
		// registration
		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}
	
	  @PostMapping("/signout")
	  @PreAuthorize("hasAuthority('CUSTOMER') or hasAuthority('EMPLOYEE') or hasAuthority('OWNER')")
	  public ResponseEntity<?> logoutUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {
		  System.out.println(authorization);
		  // String auths = String.valueOf(authorization.split(" "));
		  String jwtToken = authorization.split(" ")[1];
		  // ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
		  // return logoutUser();
		  jwtUtils.destroyToken(jwtToken);
		  return ResponseEntity.ok(new MessageResponse("You've been signed out!"));
		  // return logout;
	  }
}
