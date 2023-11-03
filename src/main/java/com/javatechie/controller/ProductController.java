package com.javatechie.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import com.javatechie.dto.AuthRequest;
import com.javatechie.entity.LogEntry;
import com.javatechie.entity.UserInfo;
import com.javatechie.repository.UserInfoRepository;
import com.javatechie.service.EmailService;
import com.javatechie.service.JwtService;
import com.javatechie.service.ProductService;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class ProductController {

    @Autowired
    private ProductService service;
    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserInfoRepository repo;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private EmailService emailService;

    Logger log = LoggerFactory.getLogger(UserInfo.class);

    @PostMapping("api/logs")
    public ResponseEntity<String> receiveLog(@RequestBody LogEntry logEntry) {
        try {
            // Log the received log entry and HTTP status code using the configured logger
            log.info("HTTP Status Code: {} - {}", HttpStatus.OK.value(), logEntry.getMessage());
            return ResponseEntity.ok("Log received and stored successfully");
        } catch (Exception e) {
            log.error("HTTP Status Code: {} - Error occurred while receiving log: {}", HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
            throw e;
        }
    }

    @PostMapping("/new")
    public ResponseEntity<String> addNewUser(@RequestBody UserInfo userInfo) {
        try {
            // emailService.sendRegistrationEmail(userInfo.getEmail());
            String result = service.addUser(userInfo);
            log.info("HTTP Status Code: {} - User Registered Successfully", HttpStatus.OK.value());
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("HTTP Status Code: {} - Error occurred while adding a new user: {}", HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
            throw e;
        }
    }

    @GetMapping("/getroles/{username}")
    public ResponseEntity<String> getRoles(@PathVariable String username) {
        try {
            String roles = service.getRoles(username);
            log.info("HTTP Status Code: {} - Retrieved Roles Successfully", HttpStatus.OK.value());
            return ResponseEntity.ok(roles);
        } catch (Exception e) {
            log.error("HTTP Status Code: {} - Error occurred while retrieving roles: {}", HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
            throw e;
        }
    }

    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
            if (authentication.isAuthenticated()) {
                log.info("HTTP Status Code: {} - Login Successful", HttpStatus.OK.value());
                UserInfo obj = repo.findByName(authRequest.getUsername()).orElse(null);
                String token = jwtService.generateToken(authRequest.getUsername(), obj.getRoles());
                return ResponseEntity.ok(token);
            } else {
                log.info("HTTP Status Code: {} - Invalid Login", HttpStatus.UNAUTHORIZED.value());
                throw new UsernameNotFoundException("Invalid user request !");
            }
        } catch (Exception e) {
            log.error("HTTP Status Code: {} - Error occurred during authentication: {}", HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
            throw e;
        }
    }
}















//package com.javatechie.controller;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.javatechie.dto.AuthRequest;
//import com.javatechie.entity.LogEntry;
//import com.javatechie.entity.UserInfo;
//import com.javatechie.repository.UserInfoRepository;
//import com.javatechie.service.EmailService;
//import com.javatechie.service.JwtService;
//import com.javatechie.service.ProductService;
//
//
//@RestController
//@RequestMapping("/auth")
//@CrossOrigin("*")
//public class ProductController {
//
//    @Autowired
//    private ProductService service;
//    @Autowired
//    private JwtService jwtService;
//    
//    @Autowired
//    private UserInfoRepository repo;
//
//    @Autowired
//    private AuthenticationManager authenticationManager;
//    
//    @Autowired 
//	   private EmailService emailService;
//    
//    Logger log = LoggerFactory.getLogger(UserInfo.class);
//
//
//    @PostMapping("api/logs")
//    public ResponseEntity<String> receiveLog(@RequestBody LogEntry logEntry) {
//        // Log the received log entry and HTTP status code using the configured logger
//        log.info("HTTP Status Code: {} - {}", HttpStatus.OK.value(), logEntry.getMessage());
//
//        return ResponseEntity.ok("Log received and stored successfully");
//    }
//
//    @PostMapping("/new")
//    public String addNewUser(@RequestBody UserInfo userInfo) {
//    	//emailService.sendRegistrationEmail(userInfo.getEmail());
//    	log.info("USER REGISTERED SUCCESSFUL");
//    	return service.addUser(userInfo);
//    }
//    
//
//    
//    @GetMapping("/getroles/{username}")
//    public String getRoles(@PathVariable String username) {
//    	return service.getRoles(username);
//    }
//    
//   
//
//    
//
//
//
//    @PostMapping("/authenticate")
//    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
//    	
//        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
//        if (authentication.isAuthenticated()) {
//        	log.info("LOGIN SUCCESSFUL");
//        	UserInfo obj = repo.findByName(authRequest.getUsername()).orElse(null);
//            return jwtService.generateToken(authRequest.getUsername(),obj.getRoles());
//        } else {
//        	log.info("INVALID LOGIN");
//            throw new UsernameNotFoundException("invalid user request !");
//        }
//
//
//    }
//}
