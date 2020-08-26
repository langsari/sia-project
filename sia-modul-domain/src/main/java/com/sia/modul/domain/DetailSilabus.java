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
@Table(name="detail_silabus")
public class DetailSilabus {

	@Id
	@GeneratedValue(generator="uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@org.hibernate.annotations.Type(type="pg-uuid")
	@Column(name="id_detail_silabus")
	private UUID idDetailSilabus;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="id_silabus")
	private Silabus silabus;
	
	@Column(name="pokok_bahasan")
	private String pokokBahasan;

	@Column(name="a_status_detail_silabus")
	private boolean statusDetailSilabus;
	
	public UUID getIdDetailSilabus() {
		return idDetailSilabus;
	}

	public void setIdDetailSilabus(UUID idDetailSilabus) {
		this.idDetailSilabus = idDetailSilabus;
	}

	public Silabus getSilabus() {
		return silabus;
	}

	public void setSilabus(Silabus silabus) {
		this.silabus = silabus;
	}

	public String getPokokBahasan() {
		return pokokBahasan;
	}

	public void setPokokBahasan(String pokokBahasan) {
		this.pokokBahasan = pokokBahasan;
	}

	public boolean isStatusDetailSilabus() {
		return statusDetailSilabus;
	}

	public void setStatusDetailSilabus(boolean statusDetailSilabus) {
		this.statusDetailSilabus = statusDetailSilabus;
	}
	
	
}
