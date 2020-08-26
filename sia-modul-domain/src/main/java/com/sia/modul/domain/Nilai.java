package com.sia.modul.domain;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="nilai")
public class Nilai {
	@Id
	@Column(name="id_nilai")
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@org.hibernate.annotations.Type(type="pg-uuid")
	private UUID idNilai;
	
	@Column(name="nilai")
	private double nilai;
	
	@ManyToOne(fetch = FetchType.EAGER, targetEntity = Krs.class)
	@JoinColumn(name="id_krs")
	private Krs krs;
	
	@ManyToOne(fetch = FetchType.EAGER, targetEntity = KomponenNilai.class)
	@JoinColumn(name="id_komponen")
	private KomponenNilai komponenNilai;

	public UUID getIdNilai() {
		return idNilai;
	}

	public void setIdNilai(UUID idNilai) {
		this.idNilai = idNilai;
	}

	public double getNilai() {
		return nilai;
	}

	public void setNilai(double nilai) {
		this.nilai = nilai;
	}

	public Krs getKrs() {
		return krs;
	}

	public void setKrs(Krs krs) {
		this.krs = krs;
	}

	public KomponenNilai getKomponenNilai() {
		return komponenNilai;
	}

	public void setKomponenNilai(KomponenNilai komponenNilai) {
		this.komponenNilai = komponenNilai;
	}
}
