package com.venesa.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.venesa.component.WapperResponseData;
import com.venesa.dto.ResponseData;
import com.venesa.dto.UserDTO;
import com.venesa.service.JwtUserDetailsService;
import com.venesa.utils.ConstantsUtil;


@RestController
@CrossOrigin
public class UserController {
	
	@Autowired
	private JwtUserDetailsService jwtUserDetailsService;
	
	@Autowired
	private WapperResponseData wapperResponse;
	
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody UserDTO user , HttpServletRequest request){
		ResponseEntity<?> responseEntity;
		try {
			responseEntity = wapperResponse.success(new ResponseData(ConstantsUtil.SUCCSESS, ConstantsUtil.SUCCSESS_MESS, jwtUserDetailsService.save(user)));
		
		} catch (Exception e) {
			responseEntity = wapperResponse.error(new ResponseData(ConstantsUtil.ERROR, e.getMessage(), null), HttpStatus.BAD_REQUEST);
		}
		return responseEntity;
	}
}
