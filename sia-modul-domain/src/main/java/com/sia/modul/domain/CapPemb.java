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
@Table(name="cap_pemb")
public class CapPemb{
	
	@Id
	@GeneratedValue(generator="uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@org.hibernate.annotations.Type(type="pg-uuid")
	@Column(name="id_cap_pemb")
	private UUID idCapPemb;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="id_kurikulum")
	private Kurikulum kurikulum;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="id_sat_man")
	private SatMan satMan;
	
	@Column(name="nm_cap_pemb")
	private String namaCapPemb;
	
	@Column(name="deskripsi_cap_pemb")
	private String deskripsiCapPemb;
	
	@Column(name="waktu_tambah")
	private Timestamp waktuTambah;
	
	@Column(name="waktu_hapus")
	private Timestamp waktuHapus;
	
	@Column(name="a_status_cap_pemb")
	private boolean statusCapPemb;

	public UUID getIdCapPemb() {
		return idCapPemb;
	}

	public void setIdCapPemb(UUID idCapPemb) {
		this.idCapPemb = idCapPemb;
	}

	public Kurikulum getKurikulum() {
		return kurikulum;
	}

	public void setKurikulum(Kurikulum kurikulum) {
		this.kurikulum = kurikulum;
	}

	public SatMan getSatMan() {
		return satMan;
	}

	public void setSatMan(SatMan satMan) {
		this.satMan = satMan;
	}

	public String getNamaCapPemb() {
		return namaCapPemb;
	}

	public void setNamaCapPemb(String namaCapPemb) {
		this.namaCapPemb = namaCapPemb;
	}

	public String getDeskripsiCapPemb() {
		return deskripsiCapPemb;
	}

	public void setDeskripsiCapPemb(String deskripsiCapPemb) {
		this.deskripsiCapPemb = deskripsiCapPemb;
	}

	public boolean isStatusCapPemb() {
		return statusCapPemb;
	}

	public void setStatusCapPemb(boolean statusHapusCapPemb) {
		this.statusCapPemb = statusHapusCapPemb;
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