package com.sia.modul.domain;

public class CheckedMK {
	private String[] idMK;
	private String[] statusMK;
	private String idPd;
	private String idRelasi;
	
	public String[] getIdMK() {
		return idMK;
	}
	public void setIdMK(String[] idMK) {
		this.idMK = idMK;
	}
	public String[] getStatusMK() {
		return statusMK;
	}
	public void setStatusMK(String[] statusMK) {
		this.statusMK = statusMK;
	}
	public String getIdPd() {
		return idPd;
	}
	public void setIdPd(String idPd) {
		this.idPd = idPd;
	}
	public String getIdRelasi() {
		return idRelasi;
	}
	public void setIdRelasi(String idRelasi) {
		this.idRelasi = idRelasi;
	}
}
