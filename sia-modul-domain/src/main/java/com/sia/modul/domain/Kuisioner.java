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
@Table(name="kuisioner")
public class Kuisioner {
	@Id
	@Column(name="id_kuisioner")
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@org.hibernate.annotations.Type(type="pg-uuid")
	private UUID idKuisioner;
	
	@NotNull
	@Column(name="nm_kuisioner")
	private String nmKuisioner;
	
	@NotNull
	@Column(name="a_kuisioner_aktif")
	private boolean aKuisionerAktif;
	
	@NotNull
	@Column(name="skala_kuisioner")
	private int skalaKuisioner;

	public UUID getIdKuisioner() {
		return idKuisioner;
	}

	public void setIdKuisioner(UUID idKuisioner) {
		this.idKuisioner = idKuisioner;
	}

	public String getNmKuisioner() {
		return nmKuisioner;
	}

	public void setNmKuisioner(String nmKuisioner) {
		this.nmKuisioner = nmKuisioner;
	}

	public boolean isaKuisionerAktif() {
		return aKuisionerAktif;
	}

	public void setaKuisionerAktif(boolean aKuisionerAktif) {
		this.aKuisionerAktif = aKuisionerAktif;
	}

	public int getSkalaKuisioner() {
		return skalaKuisioner;
	}

	public void setSkalaKuisioner(int skalaKuisioner) {
		this.skalaKuisioner = skalaKuisioner;
	}
}
