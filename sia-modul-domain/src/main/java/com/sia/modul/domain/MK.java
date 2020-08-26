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
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="mk")
public class MK{

	@Id
	@GeneratedValue(generator="uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@org.hibernate.annotations.Type(type="pg-uuid")
	@Column(name="id_mk")
	private UUID idMK; 
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="id_rumpun_mk", nullable=true)
	private RumpunMK rumpunMK;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="id_kurikulum")
	private Kurikulum kurikulum;
	 
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="id_konversi")
	private KonversiNilai konversiNilai;
	
	@Column(name="kode_mk")
	@NotEmpty(message="Kode mata kuliah tidak boleh kosong")
	private String kodeMK;
	
	@Column(name="nm_mk")
	@NotEmpty(message="Nama mata kuliah tidak boleh kosong")
	private String namaMK;
	   
	@Column(name="deskripsi_mk")
	private String deskripsiMK;
	
	@Column(name="jumlah_sks_mk")
	@Min(value = 1,message = "Nilai minimal untuk jumlah sks adalah 1")
    @Max(value = 99,message="Nilai maksimal untuk jumlah sks adalah 99")
	@NotNull(message="Jumlah sks mata kuliah tidak boleh kosong")
	private Integer jumlahSKS;
	
	@Column(name="tingkat_pemb")
	@Min(value = 1,message = "Nilai minimal untuk tingkat pembelajaran adalah 1")
    @Max(value = 99,message="Nilai maksimal untuk tingkat pembelajaran adalah 99")
	@NotNull(message="Tingkat pembelajaran mata kuliah tidak boleh kosong")
	private Integer tingkatPemb;
	
	@Column(name="a_sifat_mk")
	@NotNull(message="Sifat mata kuliah tidak boleh kosong")
	private boolean sifatMK;
	
	@Column(name="a_status_mk")
	private boolean statusMK;

	public UUID getIdMK() {
		return idMK;
	}

	public void setIdMK(UUID idMK) {
		this.idMK = idMK;
	}

	public RumpunMK getRumpunMK() {
		return rumpunMK;
	}

	public void setRumpunMK(RumpunMK rumpunMK) {
		this.rumpunMK = rumpunMK;
	}

	public Kurikulum getKurikulum() {
		return kurikulum;
	}

	public void setKurikulum(Kurikulum kurikulum) {
		this.kurikulum = kurikulum;
	}

	public String getKodeMK() {
		return kodeMK;
	}

	public void setKodeMK(String kodeMK) {
		this.kodeMK = kodeMK;
	}

	public String getNamaMK() {
		return namaMK;
	}

	public void setNamaMK(String namaMK) {
		this.namaMK = namaMK;
	}

	public String getDeskripsiMK() {
		return deskripsiMK;
	}

	public void setDeskripsiMK(String deskripsiMK) {
		this.deskripsiMK = deskripsiMK;
	}

	public Integer getJumlahSKS() {
		return jumlahSKS;
	}

	public void setJumlahSKS(Integer jumlahSKS) {
		this.jumlahSKS = jumlahSKS;
	}

	public Integer getTingkatPemb() {
		return tingkatPemb;
	}

	public void setTingkatPemb(Integer tingkatPemb) {
		this.tingkatPemb = tingkatPemb;
	}

	public boolean getSifatMK() {
		return sifatMK;
	}

	public void setSifatMK(boolean sifatMK) {
		this.sifatMK = sifatMK;
	}

	public boolean getStatusMK() {
		return statusMK;
	}

	public void setStatusMK(boolean statusMK) {
		this.statusMK = statusMK;
	}

	public KonversiNilai getKonversiNilai() {
		return konversiNilai;
	}

	public void setKonversiNilai(KonversiNilai konversiNilai) {
		this.konversiNilai = konversiNilai;
	}
	
}