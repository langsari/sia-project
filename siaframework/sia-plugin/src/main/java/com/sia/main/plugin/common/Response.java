package com.sia.main.plugin.common;

public class Response {

	public static String ok = "OK";
	
	public static String warning = "Warning";
	
	public static String error = "Error";
	
	private String status;
	
	private String message;
	
	private Object data;
	
	public Response(String status, String message, Object data) {
		this.status = status;
		this.message = message;
		this.data = data;
	}
	
	public Response() {
		this.status = "";
		this.message = "";
		this.data = null;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
