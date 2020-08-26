package com.sia.main.web.json_model;

public class SatMan {

	private String idSatMan;
	
	private String namaSatMan;

	public SatMan() {
		
	}
	
	public SatMan(String idSatMan, String namaSatMan) {
		super();
		this.idSatMan = idSatMan;
		this.namaSatMan = namaSatMan;
	}

	public String getIdSatMan() {
		return idSatMan;
	}

	public void setIdSatMan(String idSatMan) {
		this.idSatMan = idSatMan;
	}

	public String getNamaSatMan() {
		return namaSatMan;
	}

	public void setNamaSatMan(String namaSatMan) {
		this.namaSatMan = namaSatMan;
	}
	
}
