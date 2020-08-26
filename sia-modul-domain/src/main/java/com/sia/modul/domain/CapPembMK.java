package com.sia.modul.domain;

import java.sql.Timestamp;
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
@Table(name="cap_pemb_mk")
public class CapPembMK {

	@Id
	@GeneratedValue(generator="uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@org.hibernate.annotations.Type(type="pg-uuid")
	@Column(name="id_cap_pemb_mk")
	private UUID idCapPembMK;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="id_mk")
	private MK mk; 
	
	@Column(name="nm_cap_pemb_mk")
	private String namaCapPembMK;
	
	@Column(name="deskripsi_cap_pemb_mk")
	private String deskripsiCapPembMK;
	
	@Column(name="a_status_cap_pemb_mk")
	private boolean statusCapPembMK;
	
	@Column(name="waktu_tambah")
	private Timestamp waktuTambah;

	@Column(name="waktu_hapus")
	private Timestamp waktuHapus;
	
	public UUID getIdCapPembMK() {
		return idCapPembMK;
	}

	public void setIdCapPembMK(UUID idSubCapPembMK) {
		this.idCapPembMK = idSubCapPembMK;
	}

	public MK getMk() {
		return mk;
	}

	public void setMk(MK mk) {
		this.mk = mk;
	}

	public String getNamaCapPembMK() {
		return namaCapPembMK;
	}

	public void setNamaCapPembMK(String namaCapPembMK) {
		this.namaCapPembMK = namaCapPembMK;
	}

	public String getDeskripsiCapPembMK() {
		return deskripsiCapPembMK;
	}

	public void setDeskripsiCapPembMK(String deskripsiCapPembMK) {
		this.deskripsiCapPembMK = deskripsiCapPembMK;
	}

	public boolean isStatusCapPembMK() {
		return statusCapPembMK;
	}

	public void setStatusCapPembMK(boolean statusHapusCapPembMK) {
		this.statusCapPembMK = statusHapusCapPembMK;
	}

	public Timestamp getWaktuTambah() {
		return waktuTambah;
	}

	public void setWaktuTambah(Timestamp timestamp) {
		this.waktuTambah = timestamp;
	}

	public Timestamp getWaktuHapus() {
		return waktuHapus;
	}

	public void setWaktuHapus(Timestamp timestamp) {
		this.waktuHapus = timestamp;
	}
	
	
	
}
