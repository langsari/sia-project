package com.sia.main.domain;

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
import org.hibernate.annotations.Type;

@Entity
@Table(name = "peran_pengguna")
public class PeranPengguna {
	
	@Id
	@GeneratedValue(generator = "uuid-generator")
	@GenericGenerator(name = "uuid-generator", strategy = "uuid2")
	@Type(type = "pg-uuid")
	@Column(name = "id_peran_pengguna")
	private UUID idPeranPengguna;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_pengguna", nullable = false)
	private Pengguna pengguna;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_peran", nullable = false)
	private Peran peran;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_sat_man", nullable = false)
	private SatMan satMan;

	public PeranPengguna() {
		
	}
	
	public PeranPengguna(UUID idPeranPengguna, Pengguna pengguna, Peran peran,
			com.sia.main.domain.SatMan satMan) {
		super();
		this.idPeranPengguna = idPeranPengguna;
		this.pengguna = pengguna;
		this.peran = peran;
		this.satMan = satMan;
	}

	public UUID getIdPeranPengguna() {
		return idPeranPengguna;
	}

	public void setIdPeranPengguna(UUID idPeranPengguna) {
		this.idPeranPengguna = idPeranPengguna;
	}

	public Pengguna getPengguna() {
		return pengguna;
	}

	public void setPengguna(Pengguna pengguna) {
		this.pengguna = pengguna;
	}

	public Peran getPeran() {
		return peran;
	}

	public void setPeran(Peran peran) {
		this.peran = peran;
	}

	public SatMan getSatMan() {
		return satMan;
	}

	public void setSatMan(SatMan satMan) {
		this.satMan = satMan;
	}
	
}
