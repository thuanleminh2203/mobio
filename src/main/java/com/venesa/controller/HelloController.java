package com.venesa.controller;

import com.venesa.component.WrapperResponseData;
import com.venesa.component.WebClientComponent;
import com.venesa.dto.RateServiceDTO;
import com.venesa.dto.ResponseData;
import com.venesa.utils.ConstantsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@CrossOrigin
@RequestMapping("/hello")
public class HelloController {

	@Autowired
	private WrapperResponseData wapperResponse;

	@Autowired
	private WebClientComponent webClient;

	@PostMapping
	public ResponseEntity<?> hello(Authentication authentication, @RequestBody RateServiceDTO rq,
								   HttpServletRequest request, HttpServletResponse response) {
		String token = request.getHeader("Authorization");
		try {
			RateServiceDTO res = webClient.callInternalService(new ParameterizedTypeReference<RateServiceDTO>() {
			}, rq, HttpMethod.POST, "http://localhost:8763/customer", RateServiceDTO.class, token);
			return wapperResponse.success(new ResponseData<>(ConstantsUtil.SUCCSESS, ConstantsUtil.SUCCSESS_MESS, res));
		} catch (Exception e) {
			return wapperResponse.error(new ResponseData<>(ConstantsUtil.ERROR, ConstantsUtil.ERR_BUSINESS, null),
					HttpStatus.BAD_REQUEST);
		}

	}
}
