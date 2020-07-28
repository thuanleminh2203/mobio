package com.venesa.component;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.venesa.dto.ResponseData;

@Component
public class WapperResponseData {
	
	public ResponseEntity<?> success(ResponseData body){
		return new ResponseEntity<>(body, HttpStatus.OK);
	}
	
	public ResponseEntity<?> error(ResponseData body, HttpStatus status){
		return new ResponseEntity<>(body, status);
	}
	
	
}
