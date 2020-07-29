package com.venesa.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
	private long timestamp = new Date().getTime();
	private String status;
	private String error;
	private String message;
	public ErrorResponse(String status, String error, String message) {
		this.status = status;
		this.error = error;
		this.message = message;
	}
	
	
//	private String path;
}
