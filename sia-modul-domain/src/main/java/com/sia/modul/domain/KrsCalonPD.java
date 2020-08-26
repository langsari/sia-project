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
@Table(name="krs_calon_pd")
public class KrsCalonPD {
	
	@Id
	@GeneratedValue(generator="uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@org.hibernate.annotations.Type(type="pg-uuid")
	@Column(name="id_krs_calon_pd")
	private UUID idKrsCalonPD;
	
	@ManyToOne(fetch = FetchType.EAGER, targetEntity=CalonPD.class)
	@JoinColumn(name="id_calon_pd")
	private CalonPD calonPD;
	
	@ManyToOne(fetch = FetchType.EAGER, targetEntity=MKAlihjenjang.class)
	@JoinColumn(name="id_mk_alihjenjang")
	private MKAlihjenjang mkAlihjenjang;

	@ManyToOne(fetch = FetchType.EAGER, targetEntity=KonversiNilai.class)
	@JoinColumn(name="id_konversi")
	private KonversiNilai konversiNilai;
	
	@NotNull(message="Status Lulus tidak boleh kosong")
	@Column(name = "a_lulus")
	private boolean aLulus;
	
	public KonversiNilai getKonversiNilai() {
		return konversiNilai;
	}

	public void setKonversiNilai(KonversiNilai konversiNilai) {
		this.konversiNilai = konversiNilai;
	}

	public boolean isaLulus() {
		return aLulus;
	}

	public void setaLulus(boolean aLulus) {
		this.aLulus = aLulus;
	}

	public UUID getIdKrsCalonPD() {
		return idKrsCalonPD;
	}

	public void setIdKrsCalonPD(UUID idKrsCalonPD) {
		this.idKrsCalonPD = idKrsCalonPD;
	}

	public CalonPD getCalonPD() {
		return calonPD;
	}

	public void setCalonPD(CalonPD calonPD) {
		this.calonPD = calonPD;
	}

	public MKAlihjenjang getMkAlihjenjang() {
		return mkAlihjenjang;
	}

	public void setMkAlihjenjang(MKAlihjenjang mkAlihjenjang) {
		this.mkAlihjenjang = mkAlihjenjang;
	}
}
