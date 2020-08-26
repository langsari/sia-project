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
@Table(name="prasyarat_mk")
public class PrasyaratMK{

	@Id
	@GeneratedValue(generator="uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@org.hibernate.annotations.Type(type="pg-uuid")
	@Column(name="id_prasyarat_mk")
	private UUID idPrasyaratMK; 
	
	//anak MK
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="id_mk")
	private MK childMK;
	
	//parent MK
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="mk_id_mk")
	private MK parentMK; 
	
	@Column(name="a_status_prasyarat_mk")
	private boolean statusPrasyarat;
	
	public UUID getIdPrasyaratMK() {
		return idPrasyaratMK;
	}

	public void setIdPrasyaratMK(UUID idPrasyaratMK) {
		this.idPrasyaratMK = idPrasyaratMK;
	}

	public MK getChildMK() {
		return childMK;
	}

	public void setChildMK(MK childMK) {
		this.childMK = childMK;
	}

	public MK getParentMK() {
		return parentMK;
	}

	public void setParentMK(MK parentMK) {
		this.parentMK = parentMK;
	}

	public boolean getStatusPrasyarat() {
		return statusPrasyarat;
	}

	public void setStatusPrasyarat(boolean statusHapusPrasyarat) {
		this.statusPrasyarat = statusHapusPrasyarat;
	}

}