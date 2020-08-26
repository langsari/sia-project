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
@Table(name="silabus")
public class Silabus {

	@Id
	@GeneratedValue(generator="uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@org.hibernate.annotations.Type(type="pg-uuid")
	@Column(name="id_silabus")
	private UUID idSilabus;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="id_mk")
	private MK mk;

	public UUID getIdSilabus() {
		return idSilabus;
	}

	public void setIdSilabus(UUID idSilabus) {
		this.idSilabus = idSilabus;
	}

	public MK getMk() {
		return mk;
	}

	public void setMk(MK mk) {
		this.mk = mk;
	}	
	
	
}
