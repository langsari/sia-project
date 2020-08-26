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
@Table(name="sat_man_mk")
public class SatManMK {
	
	@Id
	@GeneratedValue(generator="uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@org.hibernate.annotations.Type(type="pg-uuid")
	@Column(name="id_sat_man_mk")
	private UUID idSatManMK; 
	
	//satman
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="id_sat_man")
	private SatMan satMan;
	
	//parent MK
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="id_mk")
	private MK mk; 
	
	@Column(name="a_status_sat_man_mk")
	private boolean statusSatManMK; 
	
	@Column(name = "tingkat_pemb")
	private Integer tingkatPemb;
	
	public UUID getIdSatManMK() {
		return idSatManMK;
	}

	public void setIdSatManMK(UUID idSatManMK) {
		this.idSatManMK = idSatManMK;
	}

	public SatMan getSatMan() {
		return satMan;
	}

	public void setSatMan(SatMan satMan) {
		this.satMan = satMan;
	}

	public MK getMk() {
		return mk;
	}

	public void setMk(MK mk) {
		this.mk = mk;
	}

	public boolean isStatusSatManMK() {
		return statusSatManMK;
	}

	public void setStatusSatManMK(boolean statusSatManMK) {
		this.statusSatManMK = statusSatManMK;
	}

	public Integer getTingkatPemb() {
		return tingkatPemb;
	}

	public void setTingkatPemb(Integer tingkatPemb) {
		this.tingkatPemb = tingkatPemb;
	}
	
}