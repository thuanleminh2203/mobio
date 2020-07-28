package com.eureka.zuul.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.eureka.zuul.entity.User;

@RestController
@CrossOrigin
@RequestMapping("/hello")
public class HelloController {
	
	@ResponseBody
	@PostMapping
	public String hello(Authentication authentication, @RequestBody User user) {
//		System.out.println("=====authen===" + authentication.getName());
//		System.out.println("=====authen===" + authentication.getPrincipal());
		return "hello";
	}
}
