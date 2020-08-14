package com.venesa.controller;

import com.venesa.common.Utils.ConstantsUtil;
import com.venesa.component.WrapperResponseData;
import com.venesa.dto.ResponseData;
import com.venesa.dto.UserDTO;
import com.venesa.service.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


@RestController
@CrossOrigin
public class UserController {
    private final JwtUserDetailsService jwtUserDetailsService;

    private final WrapperResponseData wrapperResponse;

    @Autowired
    public UserController(WrapperResponseData wrapperResponse, JwtUserDetailsService jwtUserDetailsService) {
        this.wrapperResponse = wrapperResponse;
        this.jwtUserDetailsService = jwtUserDetailsService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDTO user, HttpServletRequest request) {
        ResponseEntity<?> responseEntity;
        try {
            responseEntity = wrapperResponse.success(new ResponseData<>(ConstantsUtil.SUCCSESS, ConstantsUtil.SUCCSESS_MESS, jwtUserDetailsService.save(user)));

        } catch (Exception e) {
            responseEntity = wrapperResponse.error(new ResponseData<>(ConstantsUtil.ERROR, e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
        return responseEntity;
    }
}
