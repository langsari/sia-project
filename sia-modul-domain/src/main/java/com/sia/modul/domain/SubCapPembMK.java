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
@Table(name="sub_cap_pemb_mk")
public class SubCapPembMK {
	@Id
	@GeneratedValue(generator="uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@org.hibernate.annotations.Type(type="pg-uuid")
	@Column(name="id_sub_cap_pemb_mk")
	private UUID idSubCapPembMK;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="id_cap_pemb_mk")
	private CapPembMK capPembMK;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="id_cap_pemb", nullable=true)
	private CapPemb capPemb; 

	@Column(name="a_status_sub_cap_pemb_mk")
	private boolean statusSubCapPembMK;

	public UUID getIdSubCapPembMK() {
		return idSubCapPembMK;
	}

	public void setIdSubCapPembMK(UUID idSubCapPembMK) {
		this.idSubCapPembMK = idSubCapPembMK;
	}

	public CapPembMK getCapPembMK() {
		return capPembMK;
	}

	public void setCapPembMK(CapPembMK capPembMK) {
		this.capPembMK = capPembMK;
	}

	public CapPemb getCapPemb() {
		return capPemb;
	}

	public void setCapPemb(CapPemb capPemb) {
		this.capPemb = capPemb;
	}

	public boolean isStatusSubCapPembMK() {
		return statusSubCapPembMK;
	}

	public void setStatusSubCapPembMK(boolean statusHapusSubCapPembMK) {
		this.statusSubCapPembMK = statusHapusSubCapPembMK;
	}
	
	
}
