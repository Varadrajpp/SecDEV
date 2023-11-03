package com.javatechie.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.javatechie.dto.Product;
import com.javatechie.entity.UserInfo;
import com.javatechie.repository.UserInfoRepository;

@Service
public class ProductService {

    List<Product> productList = null;

    @Autowired
    private UserInfoRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    Logger log = LoggerFactory.getLogger(UserInfo.class);

//    

  

    public String addUser(UserInfo userInfo) {
    	String name = userInfo.getName();
    	UserInfo obj1 = repository.findByName(name).orElse(null);    	
    	System.out.println(obj1);
    	if(obj1==null)
    	{
	        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
	        repository.save(userInfo);
	        log.info("USER REGISTERED SUCCESSFUL FROM SERVICE");
	        return "Registration Successfully ";
	       
    	}else {
    		log.info("USER ALREADY REGISTERED ");
    		return "This UserName is Already Registered.";
    	}
    }
    
 public String getRoles(String username) {
	 UserInfo obj1=repository.findByName(username).orElse(null);
	 if(obj1!=null) {
 		return obj1.getRoles();
 	}

	 return "Not Found";
 }
   

}
