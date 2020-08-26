package com.sia.main.domain;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "pd")
public class Pd {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Type(type = "pg-uuid")
	@Column(name = "id_pd")
	private UUID idPd;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_ptk", nullable = true)
	private Ptk ptk;
	
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "pd")
	@JoinColumn(name = "id_pengguna", nullable = true)
	private Pengguna user;
	
	@Column(name = "nim_pd", unique = true, nullable = false)
	private String niPd;
	
	@Column(name = "nm_pd", nullable = false)
	private String namaPd;
	
	@Column(name = "a_pd_terhapus", nullable = false)
	private boolean statusKeaktifanPd;
	
	@Column(name = "angkatan_pd", nullable = false)
	private Integer angkatanPd;
	
	public Pd(){
		
	}
	
	public Pd(UUID idPd, Ptk ptk, Pengguna user, String niPd, String namaPd,
			boolean statusKeaktifanPd, int angkatanPd) {
		super();
		this.idPd = idPd;
		this.ptk = ptk;
		this.user = user;
		this.niPd = niPd;
		this.namaPd = namaPd;
		this.statusKeaktifanPd = statusKeaktifanPd;
		this.angkatanPd = angkatanPd;
	}

	public UUID getIdPd() {
		return idPd;
	}

	public void setIdPd(UUID idPd) {
		this.idPd = idPd;
	}

	public Ptk getPtk() {
		return ptk;
	}

	public void setPtk(Ptk ptk) {
		this.ptk = ptk;
	}

	public Pengguna getUser() {
		return user;
	}

	public void setUser(Pengguna user) {
		this.user = user;
	}

	public String getNiPd() {
		return niPd;
	}

	public void setNiPd(String niPd) {
		this.niPd = niPd;
	}

	public String getNamaPd() {
		return namaPd;
	}

	public void setNamaPd(String namaPd) {
		this.namaPd = namaPd;
	}

	public boolean isStatusKeaktifanPd() {
		return statusKeaktifanPd;
	}

	public void setStatusKeaktifanPd(boolean statusKeaktifanPd) {
		this.statusKeaktifanPd = statusKeaktifanPd;
	}

	public Integer getAngkatanPd() {
		return angkatanPd;
	}

	public void setAngkatanPd(Integer angkatanPd) {
		this.angkatanPd = angkatanPd;
	}
	
}
