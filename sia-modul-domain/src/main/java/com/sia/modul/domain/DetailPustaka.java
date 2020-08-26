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
@Table(name="detail_pustaka")
public class DetailPustaka {
	@Id
	@GeneratedValue(generator="uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@org.hibernate.annotations.Type(type="pg-uuid")
	@Column(name="id_detail_pustaka")
	private UUID idDetailPustaka;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="id_silabus")
	private Silabus silabus;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="id_pustaka")
	private Pustaka pustaka;

	@Column(name="a_status_detail_pustaka")
	private boolean statusPustaka;
	
	public UUID getIdDetailPustaka() {
		return idDetailPustaka;
	}

	public void setIdDetailPustaka(UUID idDetailPustaka) {
		this.idDetailPustaka = idDetailPustaka;
	}

	public Silabus getSilabus() {
		return silabus;
	}

	public void setSilabus(Silabus silabus) {
		this.silabus = silabus;
	}

	public Pustaka getPustaka() {
		return pustaka;
	}

	public void setPustaka(Pustaka pustaka) {
		this.pustaka = pustaka;
	}

	public boolean isStatusPustaka() {
		return statusPustaka;
	}

	public void setStatusPustaka(boolean statusPustaka) {
		this.statusPustaka = statusPustaka;
	}
	
	
	
}
