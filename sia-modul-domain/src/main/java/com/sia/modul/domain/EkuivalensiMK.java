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
@Table(name="ekuivalensi_mk")
public class EkuivalensiMK {

	@Id
	@GeneratedValue(generator="uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@org.hibernate.annotations.Type(type="pg-uuid")
	@Column(name="id_ekuivalensi_mk")
	private UUID idEkuivalensiMK;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="id_mk_lama")
	private MK mkLama;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="id_mk_baru")
	private MK mkBaru;
	
	@ManyToOne(fetch = FetchType.EAGER,targetEntity=RelasiEkuivalensi.class)
	@JoinColumn(name="id_relasi_ekuivalensi")
	private RelasiEkuivalensi relasiEkuivalensi;
	
	@Column(name="id_relasi_ekuivalensi", insertable=false, updatable=false)
	private UUID idRelasi;
	
	@Column(name="id_mk_lama", insertable=false, updatable=false)
	private UUID idMKLama;
	
	@Column(name="id_mk_baru", insertable=false, updatable=false)
	private UUID idMKBaru;

	public UUID getIdMKLama() {
		return idMKLama;
	}

	public void setIdMKLama(UUID idMKLama) {
		this.idMKLama = idMKLama;
	}

	public UUID getIdMKBaru() {
		return idMKBaru;
	}

	public void setIdMKBaru(UUID idMKBaru) {
		this.idMKBaru = idMKBaru;
	}

	public UUID getIdEkuivalensiMK() {
		return idEkuivalensiMK;
	}

	public UUID getIdRelasi() {
		return idRelasi;
	}

	public void setIdRelasi(UUID idRelasi) {
		this.idRelasi = idRelasi;
	}

	public void setIdEkuivalensiMK(UUID idEkuivalensiMK) {
		this.idEkuivalensiMK = idEkuivalensiMK;
	}

	public MK getMkLama() {
		return mkLama;
	}

	public void setMkLama(MK mkLama) {
		this.mkLama = mkLama;
	}

	public MK getMkBaru() {
		return mkBaru;
	}

	public void setMkBaru(MK mkBaru) {
		this.mkBaru = mkBaru;
	}

	public RelasiEkuivalensi getRelasiEkuivalensi() {
		return relasiEkuivalensi;
	}

	public void setRelasiEkuivalensi(RelasiEkuivalensi relasiEkuivalensi) {
		this.relasiEkuivalensi = relasiEkuivalensi;
	}

}
