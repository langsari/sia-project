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
@Table(name="nilai_kuisioner")
public class NilaiKuisioner {
	@Id
	@Column(name="id_nilai_kuisioner")
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@org.hibernate.annotations.Type(type="pg-uuid")
	private UUID idNilaiKuisioner;
	
	@Column(name="nilai_pertanyaan")
	private double nilaiPertanyaan;
	
	@ManyToOne(fetch = FetchType.EAGER, targetEntity = Krs.class)
	@JoinColumn(name="id_krs")
	private Krs krs;
	
	@ManyToOne(fetch = FetchType.EAGER, targetEntity = PertanyaanKuisioner.class)
	@JoinColumn(name="id_pertanyaan_kuisioner")
	private PertanyaanKuisioner pertanyaanKuisioner;

	public UUID getIdNilaiKuisioner() {
		return idNilaiKuisioner;
	}

	public void setIdNilaiKuisioner(UUID idNilaiKuisioner) {
		this.idNilaiKuisioner = idNilaiKuisioner;
	}

	public double getNilaiPertanyaan() {
		return nilaiPertanyaan;
	}

	public void setNilaiPertanyaan(double nilaiPertanyaan) {
		this.nilaiPertanyaan = nilaiPertanyaan;
	}

	public Krs getKrs() {
		return krs;
	}

	public void setKrs(Krs krs) {
		this.krs = krs;
	}

	public PertanyaanKuisioner getPertanyaanKuisioner() {
		return pertanyaanKuisioner;
	}

	public void setPertanyaanKuisioner(PertanyaanKuisioner pertanyaanKuisioner) {
		this.pertanyaanKuisioner = pertanyaanKuisioner;
	}
}
