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
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="pertanyaan_kuisioner")
public class PertanyaanKuisioner {
	@Id
	@Column(name="id_pertanyaan_kuisioner")
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@org.hibernate.annotations.Type(type="pg-uuid")
	private UUID idPertanyaanKuisioner;
	
	@NotNull
	@Column(name="pertanyaan")
	private String pertanyaan;
	
	@NotNull
	@Column(name="a_pertanyaan_aktif")
	private boolean aPertanyaanAktif;
	
	@ManyToOne(fetch = FetchType.EAGER, targetEntity=Kuisioner.class)
	@JoinColumn(name="id_kuisioner")
	private Kuisioner kuisioner;

	public UUID getIdPertanyaanKuisioner() {
		return idPertanyaanKuisioner;
	}

	public void setIdPertanyaanKuisioner(UUID idPertanyaanKuisioner) {
		this.idPertanyaanKuisioner = idPertanyaanKuisioner;
	}

	public String getPertanyaan() {
		return pertanyaan;
	}

	public void setPertanyaan(String pertanyaan) {
		this.pertanyaan = pertanyaan;
	}

	public boolean isaPertanyaanAktif() {
		return aPertanyaanAktif;
	}

	public void setaPertanyaanAktif(boolean aPertanyaanAktif) {
		this.aPertanyaanAktif = aPertanyaanAktif;
	}

	public Kuisioner getKuisioner() {
		return kuisioner;
	}

	public void setKuisioner(Kuisioner kuisioner) {
		this.kuisioner = kuisioner;
	}
}
