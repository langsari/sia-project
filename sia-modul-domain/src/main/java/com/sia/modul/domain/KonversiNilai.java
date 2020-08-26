package com.sia.modul.domain;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="konversi_nilai")
public class KonversiNilai {
	@Id
	@Column(name="id_konversi")
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@org.hibernate.annotations.Type(type="pg-uuid")
	private UUID idKonversi;
	
	@NotNull
	@Column(name="huruf")
	private String huruf;
	
	@NotNull
	@Column(name="batas_bawah")
	private double batasBawah;
	
	@NotNull
	@Column(name="nilai_huruf")
	private double nilaiHuruf;
	
	@NotNull
	@Column(name="a_status_konversi_aktif")
	private boolean aStatusKonversiAktif;

	public UUID getIdKonversi() {
		return idKonversi;
	}

	public void setIdKonversi(UUID idKonversi) {
		this.idKonversi = idKonversi;
	}

	public String getHuruf() {
		return huruf;
	}

	public void setHuruf(String huruf) {
		this.huruf = huruf;
	}

	public double getBatasBawah() {
		return batasBawah;
	}

	public void setBatasBawah(double batasBawah) {
		this.batasBawah = batasBawah;
	}

	public double getNilaiHuruf() {
		return nilaiHuruf;
	}

	public void setNilaiHuruf(double nilaiHuruf) {
		this.nilaiHuruf = nilaiHuruf;
	}

	public boolean isaStatusKonversiAktif() {
		return aStatusKonversiAktif;
	}

	public void setaStatusKonversiAktif(boolean aStatusKonversiAktif) {
		this.aStatusKonversiAktif = aStatusKonversiAktif;
	}
	
}
