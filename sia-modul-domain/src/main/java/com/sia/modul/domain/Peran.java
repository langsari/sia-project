package com.sia.modul.domain;

import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "peran")
public class Peran {
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Type(type = "pg-uuid")
	@Column(name = "id_peran")
	private UUID idPeran;
	
	@Column(name = "nama_peran", nullable = false)
	private String namaPeran;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_tipe", nullable = false)
	private TipePengguna tipePengguna;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "peran")
	private List<PeranPengguna> peranPenggunaList;
	
	public Peran() {
		
	}

	public Peran(UUID idPeran, String namaPeran, TipePengguna tipePengguna,
			List<PeranPengguna> peranPenggunaList) {
		super();
		this.idPeran = idPeran;
		this.namaPeran = namaPeran;
		this.tipePengguna = tipePengguna;
		this.peranPenggunaList = peranPenggunaList;
	}

	public UUID getIdPeran() {
		return idPeran;
	}

	public void setIdPeran(UUID idPeran) {
		this.idPeran = idPeran;
	}

	public String getNamaPeran() {
		return namaPeran;
	}

	public void setNamaPeran(String namaPeran) {
		this.namaPeran = namaPeran;
	}

	public TipePengguna getTipePengguna() {
		return tipePengguna;
	}

	public void setTipePengguna(TipePengguna tipePengguna) {
		this.tipePengguna = tipePengguna;
	}

	public List<PeranPengguna> getPeranPenggunaList() {
		return peranPenggunaList;
	}

	public void setPeranPenggunaList(List<PeranPengguna> peranPenggunaList) {
		this.peranPenggunaList = peranPenggunaList;
	}

}
