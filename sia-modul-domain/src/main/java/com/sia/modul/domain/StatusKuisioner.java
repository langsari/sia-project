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
@Table(name="status_kuisioner")
public class StatusKuisioner {
	@Id
	@Column(name="id_status_kuisioner")
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@org.hibernate.annotations.Type(type="pg-uuid")
	private UUID idStatusKuisioner;
	
	@NotNull
	@Column(name="a_kuisioner_terisi")
	private boolean aKuisionerTerisi;
	
	@Column(name="nilai_kuisioner")
	private Double nilaiKuisioner;
	
	@ManyToOne(fetch = FetchType.EAGER, targetEntity = Krs.class)
	@JoinColumn(name="id_krs")
	private Krs krs;
	
	@ManyToOne(fetch = FetchType.EAGER, targetEntity = Kuisioner.class)
	@JoinColumn(name="id_kuisioner")
	private Kuisioner kuisioner;

	public UUID getIdStatusKuisioner() {
		return idStatusKuisioner;
	}

	public void setIdStatusKuisioner(UUID idStatusKuisioner) {
		this.idStatusKuisioner = idStatusKuisioner;
	}

	public boolean isaKuisionerTerisi() {
		return aKuisionerTerisi;
	}

	public void setaKuisionerTerisi(boolean aKuisionerTerisi) {
		this.aKuisionerTerisi = aKuisionerTerisi;
	}

	public Double getNilaiKuisioner() {
		return nilaiKuisioner;
	}

	public void setNilaiKuisioner(Double nilaiKuisioner) {
		this.nilaiKuisioner = nilaiKuisioner;
	}

	public Krs getKrs() {
		return krs;
	}

	public void setKrs(Krs krs) {
		this.krs = krs;
	}

	public Kuisioner getKuisioner() {
		return kuisioner;
	}

	public void setKuisioner(Kuisioner kuisioner) {
		this.kuisioner = kuisioner;
	}
}
