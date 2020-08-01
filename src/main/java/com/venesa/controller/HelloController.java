package com.venesa.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.venesa.component.WapperResponseData;
import com.venesa.component.WebClientComponent;
import com.venesa.dto.Customer;
import com.venesa.dto.ResponseData;
import com.venesa.utils.ConstantsUtil;

@RestController
@CrossOrigin
@RequestMapping("/hello")
public class HelloController {

	@Autowired
	private WapperResponseData wapperResponse;

	@Autowired
	private WebClientComponent webClient;

	@PostMapping
	public ResponseEntity<?> hello(Authentication authentication, @RequestBody Customer customer,
			HttpServletRequest request, HttpServletResponse response) {
		String token = request.getHeader("Authorization");
		try {
			Customer res = webClient.callInternalService(new ParameterizedTypeReference<Customer>() {
			}, customer, HttpMethod.POST, "http://localhost:8763/customer", Customer.class, token);
			return wapperResponse.success(new ResponseData<>(ConstantsUtil.SUCCSESS, ConstantsUtil.SUCCSESS_MESS, res));
		} catch (Exception e) {
			return wapperResponse.error(new ResponseData<>(ConstantsUtil.ERROR, ConstantsUtil.ERR_BUSINESS, null),
					HttpStatus.BAD_REQUEST);
		}

	}
}
