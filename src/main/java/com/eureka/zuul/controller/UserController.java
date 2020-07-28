package com.eureka.zuul.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.eureka.zuul.dto.ResponseData;
import com.eureka.zuul.dto.UserDTO;
import com.eureka.zuul.service.JwtUserDetailsService;


@RestController
@CrossOrigin
public class UserController {
	
	@Autowired
	private JwtUserDetailsService jwtUserDetailsService;
	
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody UserDTO user){
		ResponseEntity<?> responseEntity;
		try {
			jwtUserDetailsService.save(user);
			responseEntity = new ResponseEntity<>(new ResponseData(null, "susscess", user), HttpStatus.OK);
		
		} catch (Exception e) {
			responseEntity =  new ResponseEntity<>( new ResponseData(null,e.getMessage(),null), HttpStatus.BAD_REQUEST);
		}
		return responseEntity;
	}
}
