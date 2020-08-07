package com.venesa.controller;

import com.venesa.component.WebClientComponent;
import com.venesa.component.WrapperResponseData;
import com.venesa.dto.Customer;
import com.venesa.dto.ResponseData;
import com.venesa.utils.ConstantsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@CrossOrigin
public class RateServiceController {

    private final WrapperResponseData wrapperResponse;

    private final WebClientComponent webClient;

    @Autowired
    public RateServiceController(WrapperResponseData wrapperResponse, WebClientComponent webClient) {
        this.wrapperResponse = wrapperResponse;
        this.webClient = webClient;
    }

    @PostMapping("/rate")
    public ResponseEntity<?> hello(Authentication authentication, @RequestBody Customer customer,
                                   HttpServletRequest request, HttpServletResponse response) {
        String token = request.getHeader("Authorization");
        try {
            Customer res = webClient.callInternalService(new ParameterizedTypeReference<Customer>() {
            }, customer, HttpMethod.POST, "http://localhost:8763/customer", Customer.class, token);
            return wrapperResponse.success(new ResponseData<>(ConstantsUtil.SUCCSESS, ConstantsUtil.SUCCSESS_MESS, res));
        } catch (Exception e) {
            System.out.println("=====here====" + e.getCause().getMessage());
            return wrapperResponse.error(new ResponseData<>(ConstantsUtil.ERROR,  e.getCause().getMessage(), null),
                    HttpStatus.BAD_REQUEST);
        }

    }

}
