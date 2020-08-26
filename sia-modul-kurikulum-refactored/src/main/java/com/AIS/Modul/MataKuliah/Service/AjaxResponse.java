package com.AIS.Modul.MataKuliah.Service;

public class AjaxResponse {
	private String status;
	private String message;
	private Object data;
	public AjaxResponse(String status, String message, Object data) {
		// TODO Auto-generated constructor stub
		this.status = status;
		this.message = message;
		this.setData(data);
	}
	
	public AjaxResponse() {
		// TODO Auto-generated constructor stub
		this.status = "ok";
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
