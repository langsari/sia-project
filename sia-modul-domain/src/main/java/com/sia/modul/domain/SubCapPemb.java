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
@Table(name="sub_cap_pemb")
public class SubCapPemb {
	@Id
	@GeneratedValue(generator="uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@org.hibernate.annotations.Type(type="pg-uuid")
	@Column(name="id_sub_cap_pemb")
	private UUID idSubCapPemb;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="id_cap_pemb")
	private CapPemb childCapPemb;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="cap_id_cap_pemb", nullable=true)
	private CapPemb parentCapPemb; 

	@Column(name="a_status_sub_cap_pemb")
	private boolean statusSubCapPemb;
	
	public UUID getIdSubCapPemb() {
		return idSubCapPemb;
	}

	public void setIdSubCapPemb(UUID idSubCapPemb) {
		this.idSubCapPemb = idSubCapPemb;
	}

	public CapPemb getChildCapPemb() {
		return childCapPemb;
	}

	public void setChildCapPemb(CapPemb childCapPemb) {
		this.childCapPemb = childCapPemb;
	}

	public CapPemb getParentCapPemb() {
		return parentCapPemb;
	}

	public void setParentCapPemb(CapPemb parentCapPemb) {
		this.parentCapPemb = parentCapPemb;
	}

	public boolean isStatusSubCapPemb() {
		return statusSubCapPemb;
	}

	public void setStatusSubCapPemb(boolean statusHapusSubCapPemb) {
		this.statusSubCapPemb = statusHapusSubCapPemb;
	} 
	
	
}
