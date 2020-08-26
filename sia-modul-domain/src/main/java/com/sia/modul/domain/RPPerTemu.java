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
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="rp_per_temu")
public class RPPerTemu {

	@Id
	@GeneratedValue(generator="uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@org.hibernate.annotations.Type(type="pg-uuid")
	@Column(name="id_rp_per_temu")
	private UUID idRPPerTemu;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="id_rp")
	private RP rp; 

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="id_bentuk_penilaian")
	private BentukPenilaian bentukPenilaian; 
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="id_metode_pemb")
	private MetodePemb metodePemb; 
	
	@Column(name="minggu_pemb_ke")
	@Min(value=0,message="Minimal batas bawah pertemuan adalah 1")
	@Max(value=16,message="Maksimal batas atas pertemuan adalah 16")
	private int mingguPembKe;
	
	
	@Column(name="waktu_pemb")
	private int waktuPemb;
	  
	@Column(name="bobot_penilaian")
	private double bobotPenilaian;  
	
	@Column(name="a_status_rp_per_temu")
	private boolean statusRPPerTemu;

	@Column(name="indikator_penilaian")
	private String indikatorPenilaian;
	
	public UUID getIdRPPerTemu() {
		return idRPPerTemu;
	}

	public void setIdRPPerTemu(UUID idRPPerTemu) {
		this.idRPPerTemu = idRPPerTemu;
	}

	public RP getRp() {
		return rp;
	}

	public void setRp(RP rp) {
		this.rp = rp;
	}

	public BentukPenilaian getBentukPenilaian() {
		return bentukPenilaian;
	}

	public void setBentukPenilaian(BentukPenilaian bentukPenilaian) {
		this.bentukPenilaian = bentukPenilaian;
	}

	public MetodePemb getMetodePemb() {
		return metodePemb;
	}

	public void setMetodePemb(MetodePemb metodePemb) {
		this.metodePemb = metodePemb;
	}

	public int getMingguPembKe() {
		return mingguPembKe;
	}

	public void setMingguPembKe(int mingguPembKe) {
		this.mingguPembKe = mingguPembKe;
	}

	public int getWaktuPemb() {
		return waktuPemb;
	}

	public void setWaktuPemb(int waktuPemb) {
		this.waktuPemb = waktuPemb;
	}

	public double getBobotPenilaian() {
		return bobotPenilaian;
	}

	public void setBobotPenilaian(double bobotPenilaian) {
		this.bobotPenilaian = bobotPenilaian;
	}

	public boolean isStatusRPPerTemu() {
		return statusRPPerTemu;
	}

	public void setStatusRPPerTemu(boolean statusRPPerTemu) {
		this.statusRPPerTemu = statusRPPerTemu;
	}

	public String getIndikatorPenilaian() {
		return indikatorPenilaian;
	}

	public void setIndikatorPenilaian(String indikatorPenilaian) {
		this.indikatorPenilaian = indikatorPenilaian;
	}

	
}
