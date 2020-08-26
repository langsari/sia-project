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
@Table(name="rp_bentuk_penilaian")
public class RPBentukPenilaian {

	@Id
	@GeneratedValue(generator="uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@org.hibernate.annotations.Type(type="pg-uuid")
	@Column(name="id_rp_bentuk_penilaian")
	private UUID idRPBentuk;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="id_bentuk_penilaian")
	private BentukPenilaian bentukPenilaian; 

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="id_rp_per_temu")
	private RPPerTemu rpPerTemu;

	public UUID getIdRPBentuk() {
		return idRPBentuk;
	}

	public void setIdRPBentuk(UUID idRPBentuk) {
		this.idRPBentuk = idRPBentuk;
	}

	public BentukPenilaian getBentukPenilaian() {
		return bentukPenilaian;
	}

	public void setBentukPenilaian(BentukPenilaian bentukPenilaian) {
		this.bentukPenilaian = bentukPenilaian;
	}

	public RPPerTemu getRpPerTemu() {
		return rpPerTemu;
	}

	public void setRpPerTemu(RPPerTemu rpPerTemu) {
		this.rpPerTemu = rpPerTemu;
	}
	
	
}
