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
@Table(name="rp")
public class RP {
	
	@Id
	@GeneratedValue(generator="uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@org.hibernate.annotations.Type(type="pg-uuid")
	@Column(name="id_rp")
	private UUID idRP; 
	  
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="id_silabus")
	private Silabus silabus;
	
	@Column(name="bahan_kajian")
	private String bahanKajian;
	

	public UUID getIdRP() {
		return idRP;
	}

	public void setIdRP(UUID idRP) {
		this.idRP = idRP;
	}
 

	public Silabus getSilabus() {
		return silabus;
	}

	public void setSilabus(Silabus silabus) {
		this.silabus = silabus;
	}

	public String getBahanKajian() {
		return bahanKajian;
	}

	public void setBahanKajian(String bahanKajian) {
		this.bahanKajian = bahanKajian;
	}
	 
	
}
