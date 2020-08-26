package com.bustan.siakad.service;

import java.util.List;

public class Datatable {
	private String sEcho;
	private long iTotalRecords;
	private long iTotalDisplayRecords;
	private List<String[]> aaData;
	public Datatable() {
		// TODO Auto-generated constructor stub
	}
	public String getsEcho() {
		return sEcho;
	}
	public void setsEcho(String sEcho) {
		this.sEcho = sEcho;
	}
	public long getiTotalRecords() {
		return iTotalRecords;
	}
	public void setiTotalRecords(long iTotalRecords) {
		this.iTotalRecords = iTotalRecords;
	}
	public long getiTotalDisplayRecords() {
		return iTotalDisplayRecords;
	}
	public void setiTotalDisplayRecords(long iTotalDisplayRecords) {
		this.iTotalDisplayRecords = iTotalDisplayRecords;
	}
	public List<String[]> getAaData() {
		return aaData;
	}
	public void setAaData(List<String[]> aaData) {
		this.aaData = aaData;
	}
}
