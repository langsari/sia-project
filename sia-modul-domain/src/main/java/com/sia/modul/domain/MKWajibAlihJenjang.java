package com.sia.modul.domain;

public class MKWajibAlihJenjang {
	private String[] idMK;
	private String[] kodeMK;
	private String[] namaMK;
	private String[] jumlahSKS;
	private String[] status;
	private String idCalonPD;
	private String idKurikulum;
	
	public String getIdKurikulum() {
		return idKurikulum;
	}
	public void setIdKurikulum(String idKurikulum) {
		this.idKurikulum = idKurikulum;
	}
	public String getIdCalonPD() {
		return idCalonPD;
	}
	public void setIdCalonPD(String idCalonPD) {
		this.idCalonPD = idCalonPD;
	}
	public String[] getIdMK() {
		return idMK;
	}
	public void setIdMK(String[] idMK) {
		this.idMK = idMK;
	}
	public String[] getKodeMK() {
		return kodeMK;
	}
	public void setKodeMK(String[] kodeMK) {
		this.kodeMK = kodeMK;
	}
	public String[] getNamaMK() {
		return namaMK;
	}
	public void setNamaMK(String[] namaMK) {
		this.namaMK = namaMK;
	}
	public String[] getJumlahSKS() {
		return jumlahSKS;
	}
	public void setJumlahSKS(String[] jumlahSKS) {
		this.jumlahSKS = jumlahSKS;
	}
	public String[] getStatus() {
		return status;
	}
	public void setStatus(String[] status) {
		this.status = status;
	}
	
}
