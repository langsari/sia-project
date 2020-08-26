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
@Table(name="detail_pemetaan")
public class DetailPemetaan {
	@Id
	@GeneratedValue(generator="uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@org.hibernate.annotations.Type(type="pg-uuid")
	@Column(name="id_detail_pemetaan")
	private UUID idDetailPemetaan;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="id_rp_per_temu")
	private RPPerTemu rpPerTemu;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="id_cap_pemb_mk")
	private CapPembMK capPembMK; 

	@Column(name="a_detail_pemetaan")
	private boolean statusDetailPemetaan;

	public UUID getIdDetailPemetaan() {
		return idDetailPemetaan;
	}

	public void setIdDetailPemetaan(UUID idDetailPemetaan) {
		this.idDetailPemetaan = idDetailPemetaan;
	}

	public RPPerTemu getRpPerTemu() {
		return rpPerTemu;
	}

	public void setRpPerTemu(RPPerTemu rpPerTemu) {
		this.rpPerTemu = rpPerTemu;
	}

	public CapPembMK getCapPembMK() {
		return capPembMK;
	}

	public void setCapPembMK(CapPembMK capPembMK) {
		this.capPembMK = capPembMK;
	}

	public boolean isStatusDetailPemetaan() {
		return statusDetailPemetaan;
	}

	public void setStatusDetailPemetaan(boolean statusDetailPemetaan) {
		this.statusDetailPemetaan = statusDetailPemetaan;
	}
	
	
}
