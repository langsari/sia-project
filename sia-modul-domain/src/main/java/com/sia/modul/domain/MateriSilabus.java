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
@Table(name="materi_silabus")
public class MateriSilabus {

	@Id
	@GeneratedValue(generator="uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@org.hibernate.annotations.Type(type="pg-uuid")
	@Column(name="id_materi_silabus")
	private UUID idMateriSilabus;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="id_detail_silabus")
	private DetailSilabus detailSilabus;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="id_rp_per_temu")
	private RPPerTemu rpPerTemu; 
	
	@Column(name="a_status_materi_silabus")
	private boolean statusMateriSilabus;

	public UUID getIdMateriSilabus() {
		return idMateriSilabus;
	}

	public void setIdMateriSilabus(UUID idMateriSilabus) {
		this.idMateriSilabus = idMateriSilabus;
	}

	public DetailSilabus getDetailSilabus() {
		return detailSilabus;
	}

	public void setDetailSilabus(DetailSilabus detailSilabus) {
		this.detailSilabus = detailSilabus;
	}

	public RPPerTemu getRpPerTemu() {
		return rpPerTemu;
	}

	public void setRpPerTemu(RPPerTemu rpPerTemu) {
		this.rpPerTemu = rpPerTemu;
	}

	public boolean isStatusMateriSilabus() {
		return statusMateriSilabus;
	}

	public void setStatusMateriSilabus(boolean statusMateriSilabus) {
		this.statusMateriSilabus = statusMateriSilabus;
	} 
}
