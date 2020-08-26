package com.siakad.modul_penilaian.service;

import java.util.UUID;

public class JSONNilai {
	private UUID idKrs;
	private UUID idKomp;
	private double nilai;
	
	public JSONNilai() {
		
	}
	
	public UUID getIdKrs() {
		return idKrs;
	}
	public void setIdKrs(UUID idKrs) {
		this.idKrs = idKrs;
	}
	public UUID getIdKomp() {
		return idKomp;
	}
	public void setIdKomp(UUID idKomp) {
		this.idKomp = idKomp;
	}
	public double getNilai() {
		return nilai;
	}
	public void setNilai(double nilai) {
		this.nilai = nilai;
	}	
}
