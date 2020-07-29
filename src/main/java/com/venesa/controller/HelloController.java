package com.venesa.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.venesa.entity.User;

@RestController
@CrossOrigin
@RequestMapping("/hello")
public class HelloController {
	
	@ResponseBody 
	@PostMapping
	public String hello(Authentication authentication, @RequestBody User user, HttpServletRequest request, HttpServletResponse response) {
//		try {
			System.out.println("====bodyy===" );
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		System.out.println("=====authen===" + authentication.getName());
//		System.out.println("=====authen===" + authentication.getPrincipal());
//		HttpServletResponseCopier responseCopier = new HttpServletResponseCopier((HttpServletResponse) response);
		return "hello";
	}
}
