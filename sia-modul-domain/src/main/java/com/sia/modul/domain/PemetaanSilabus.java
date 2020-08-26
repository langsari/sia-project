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
@Table(name="pemetaan_silabus")
public class PemetaanSilabus {
	@Id
	@GeneratedValue(generator="uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@org.hibernate.annotations.Type(type="pg-uuid")
	@Column(name="id_pemetaan_silabus")
	private UUID idPemetaanSilabus;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="id_detail_silabus")
	private DetailSilabus detailSilabus;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="id_cap_pemb_mk")
	private CapPembMK capPembMK;

	@Column(name="a_status_pemetaan_silabus")
	private boolean statusPemetaanSilabus;
	
	public UUID getIdPemetaanSilabus() {
		return idPemetaanSilabus;
	}

	public void setIdPemetaanSilabus(UUID idPemetaanSilabus) {
		this.idPemetaanSilabus = idPemetaanSilabus;
	}

	public DetailSilabus getDetailSilabus() {
		return detailSilabus;
	}

	public void setDetailSilabus(DetailSilabus detailSilabus) {
		this.detailSilabus = detailSilabus;
	}

	public CapPembMK getCapPembMK() {
		return capPembMK;
	}

	public void setCapPembMK(CapPembMK capPembMK) {
		this.capPembMK = capPembMK;
	}

	public boolean isStatusPemetaanSilabus() {
		return statusPemetaanSilabus;
	}

	public void setStatusPemetaanSilabus(boolean statusPemetaanSilabus) {
		this.statusPemetaanSilabus = statusPemetaanSilabus;
	}
	
	
	
}
