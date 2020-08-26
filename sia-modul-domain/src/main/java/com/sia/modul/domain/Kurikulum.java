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
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="kurikulum")
public class Kurikulum{
	
	@Id
	@GeneratedValue(generator="uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@org.hibernate.annotations.Type(type="pg-uuid")
	@Column(name="id_kurikulum")
	private UUID idKurikulum;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="id_sat_man")
	private SatMan satMan;
	
	@Column(name="nm_kurikulum")
	private String namaKurikulum;
	
	@NotEmpty(message="Tahun mulai kurikulum tidak boleh kosong")
	@Min(value = 0000,message = "Nilai minimal untuk Minimal Pertemuan Pembelajaran adalah 0000")
    @Max(value = 9999,message="Nilai maksimal untuk Minimal Pertemuan Pembelajaran adalah 9999")
	@Column(name="thn_mulai")
	private String thnMulai;
	
	@NotEmpty(message="Tahun berakhirnya kurikulum tidak boleh kosong") 
	@Min(value = 0000,message = "Nilai minimal untuk Minimal Pertemuan Pembelajaran adalah 0000")
    @Max(value = 9999,message="Nilai maksimal untuk Minimal Pertemuan Pembelajaran adalah 9999")
	@Column(name="thn_akhir")
	private String thnAkhir;
	
	@Column(name="a_status_kurikulum")
	private boolean statusKurikulum;
	
	@Column(name="a_status_berlaku")
	private boolean statusBerlaku;

	public UUID getIdKurikulum() {
		return idKurikulum;
	}

	public boolean isStatusBerlaku() {
		return statusBerlaku;
	}

	public void setStatusBerlaku(boolean statusBerlaku) {
		this.statusBerlaku = statusBerlaku;
	}

	public void setIdKurikulum(UUID idKurikulum) {
		this.idKurikulum = idKurikulum;
	}

	public SatMan getSatMan() {
		return satMan;
	}

	public void setSatMan(SatMan satMan) {
		this.satMan = satMan;
	}

	public String getNamaKurikulum() {
		return namaKurikulum;
	}

	public void setNamaKurikulum(String namaKurikulum) {
		this.namaKurikulum = namaKurikulum;
	}

	public String getThnMulai() {
		return thnMulai;
	}

	public void setThnMulai(String thnMulai) {
		this.thnMulai = thnMulai;
	}

	public String getThnAkhir() {
		return thnAkhir;
	}

	public void setThnAkhir(String thnAkhir) {
		this.thnAkhir = thnAkhir;
	}

	public boolean getStatusKurikulum() {
		return statusKurikulum;
	}

	public void setStatusKurikulum(boolean statusKurikulum) {
		this.statusKurikulum = statusKurikulum;
	}

}