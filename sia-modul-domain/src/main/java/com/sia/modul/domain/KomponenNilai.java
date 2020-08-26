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
@Table(name="komponen_nilai")
public class KomponenNilai {
	@Id
	@Column(name="id_komponen")
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@org.hibernate.annotations.Type(type="pg-uuid")
	private UUID idKomponen;
	
	@NotNull
	@Column(name="nama_komponen")
	private String namaKomponen;
	
	@NotNull
	@Column(name="persentase_komponen")
	private double persentaseKomponen;
	
	@NotNull
	@Column(name="a_komp_aktif")
	private boolean aKompAktif;	

	@ManyToOne(fetch = FetchType.EAGER, targetEntity = Pemb.class)
	@JoinColumn(name="id_pemb")
	private Pemb pemb;

	public UUID getIdKomponen() {
		return idKomponen;
	}

	public void setIdKomponen(UUID idKomponen) {
		this.idKomponen = idKomponen;
	}

	public String getNamaKomponen() {
		return namaKomponen;
	}

	public void setNamaKomponen(String namaKomponen) {
		this.namaKomponen = namaKomponen;
	}

	public double getPersentaseKomponen() {
		return persentaseKomponen;
	}

	public void setPersentaseKomponen(double persentaseKomponen) {
		this.persentaseKomponen = persentaseKomponen;
	}

	public boolean isaKompAktif() {
		return aKompAktif;
	}

	public void setaKompAktif(boolean aKompAktif) {
		this.aKompAktif = aKompAktif;
	}

	public Pemb getPemb() {
		return pemb;
	}

	public void setPemb(Pemb pemb) {
		this.pemb = pemb;
	}

	
}
