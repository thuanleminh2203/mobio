package com.venesa.dto;

public class ResponseData {
	private String errCode;
	private String mess;
	private Object data;

	public ResponseData() {
	}

	public ResponseData(String errCode, String mess, Object data) {
		this.errCode = errCode;
		this.mess = mess;
		this.data = data;
	}

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public String getMess() {
		return mess;
	}

	public void setMess(String mess) {
		this.mess = mess;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
