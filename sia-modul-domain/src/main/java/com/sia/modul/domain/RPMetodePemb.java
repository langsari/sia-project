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
@Table(name="rp_metode_pemb")
public class RPMetodePemb {

	@Id
	@GeneratedValue(generator="uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@org.hibernate.annotations.Type(type="pg-uuid")
	@Column(name="id_rp_metode_pemb")
	private UUID idRPMetodePemb;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="id_rp_per_temu")
	private RPPerTemu rpPerTemu; 

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="id_metode_pemb")
	private MetodePemb metodePemb;

	public UUID getIdRPMetodePemb() {
		return idRPMetodePemb;
	}

	public void setIdRPMetodePemb(UUID idRPMetodePemb) {
		this.idRPMetodePemb = idRPMetodePemb;
	}

	public RPPerTemu getRpPerTemu() {
		return rpPerTemu;
	}

	public void setRpPerTemu(RPPerTemu rpPerTemu) {
		this.rpPerTemu = rpPerTemu;
	}

	public MetodePemb getMetodePemb() {
		return metodePemb;
	}

	public void setMetodePemb(MetodePemb metodePemb) {
		this.metodePemb = metodePemb;
	}  
	
	
	
}
