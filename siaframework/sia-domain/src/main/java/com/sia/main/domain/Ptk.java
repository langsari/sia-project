package com.sia.main.domain;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "ptk")
public class Ptk {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Type(type = "pg-uuid")
	@Column(name = "id_ptk")
	private UUID idPtk;
	
	@Column(name = "nip_ptk", unique = true, nullable = false)
	private String niPtk;
	
	@Column(name = "nm_ptk", nullable = false)
	private String namaPtk;
	
	@Column(name = "a_ptk_terhapus", nullable = false)
	private boolean ptkTerhapus;
	
	@Column(name = "status_ptk", nullable = false)
	private boolean statusPtk;
	
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "ptk")
	@JoinColumn(name = "id_pengguna", nullable = true)
	private Pengguna user;
	
	public Ptk() {
		
	}
	
	public Ptk(UUID idPtk, String niPtk, String namaPtk, boolean ptkTerhapus,
			boolean statusPtk, Pengguna user) {
		super();
		this.idPtk = idPtk;
		this.niPtk = niPtk;
		this.namaPtk = namaPtk;
		this.ptkTerhapus = ptkTerhapus;
		this.statusPtk = statusPtk;
		this.user = user;
	}

	public UUID getIdPtk() {
		return idPtk;
	}

	public void setIdPtk(UUID idPtk) {
		this.idPtk = idPtk;
	}

	public String getNiPtk() {
		return niPtk;
	}

	public void setNiPtk(String niPtk) {
		this.niPtk = niPtk;
	}

	public String getNamaPtk() {
		return namaPtk;
	}

	public void setNamaPtk(String namaPtk) {
		this.namaPtk = namaPtk;
	}

	public boolean isPtkTerhapus() {
		return ptkTerhapus;
	}

	public void setPtkTerhapus(boolean ptkTerhapus) {
		this.ptkTerhapus = ptkTerhapus;
	}

	public boolean isStatusPtk() {
		return statusPtk;
	}

	public void setStatusPtk(boolean statusPtk) {
		this.statusPtk = statusPtk;
	}

	public Pengguna getUser() {
		return user;
	}

	public void setUser(Pengguna user) {
		this.user = user;
	}
	
}
