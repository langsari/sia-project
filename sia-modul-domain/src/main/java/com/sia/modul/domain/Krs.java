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
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.joda.deser.LocalDateTimeDeserializer;

@Entity
@Table(name="krs")
public class Krs {
	@Id
	@Column(name="id_krs")
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@org.hibernate.annotations.Type(type="pg-uuid")
	private UUID idKrs;
	
	@NotNull
	@Column(name="a_krs_batal")
	private boolean aKrsBatal;
	
	@Column(name="nilai_akhir")
	private double nilaiAkhir;
	
	@JsonDeserialize(using=LocalDateTimeDeserializer.class)
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	@Column(name="waktu_batal")
	private LocalDateTime waktuBatal;
	
	@ManyToOne(fetch = FetchType.EAGER, targetEntity=Pd.class)
	@JoinColumn(name="id_pd")
	private Pd pd;
	
	@ManyToOne(fetch = FetchType.EAGER, targetEntity=Pemb.class)
	@JoinColumn(name="id_pemb")
	private Pemb pemb;
	
	@ManyToOne(fetch = FetchType.EAGER, targetEntity=KonversiNilai.class)
	@JoinColumn(name="id_huruf")
	private KonversiNilai konversiNilai;

	@JsonDeserialize(using=LocalDateTimeDeserializer.class)
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	@Column(name="waktu_ambil")
	private LocalDateTime waktuAmbil;

	@JsonDeserialize(using=LocalDateTimeDeserializer.class)
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	@Column(name="waktu_hapus")
	private LocalDateTime waktuHapus;

    @NotNull(message="Status hapus KRS tidak boleh kosong")
	@Column(name="a_krs_terhapus")
	private boolean aKrsTerhapus;
    
    @NotNull(message="Status hapus KRS tidak boleh kosong")
	@Column(name="a_krs_disetujui")
	private boolean aKrsDisetujui;
    
    @NotNull(message="Status ulang KRS tidak boleh kosong")
	@Column(name="a_krs_diulang")
	private boolean aKrsDiulang;
    
    @NotNull(message="Status lulus KRS tidak boleh kosong")
	@Column(name="a_krs_lulus")
	private boolean aKrsLulus;

	public boolean isaKrsLulus() {
		return aKrsLulus;
	}

	public void setaKrsLulus(boolean aKrsLulus) {
		this.aKrsLulus = aKrsLulus;
	}

	public UUID getIdKrs() {
		return idKrs;
	}

	public void setIdKrs(UUID idKrs) {
		this.idKrs = idKrs;
	}

	public boolean isaKrsBatal() {
		return aKrsBatal;
	}

	public void setaKrsBatal(boolean aKrsBatal) {
		this.aKrsBatal = aKrsBatal;
	}

	public double getNilaiAkhir() {
		return nilaiAkhir;
	}

	public void setNilaiAkhir(double nilaiAkhir) {
		this.nilaiAkhir = nilaiAkhir;
	}

	public LocalDateTime getWaktuBatal() {
		return waktuBatal;
	}

	public void setWaktuBatal(LocalDateTime waktuBatal) {
		this.waktuBatal = waktuBatal;
	}

	public Pd getPd() {
		return pd;
	}

	public void setPd(Pd pd) {
		this.pd = pd;
	}

	public Pemb getPemb() {
		return pemb;
	}

	public void setPemb(Pemb pemb) {
		this.pemb = pemb;
	}

	public KonversiNilai getKonversiNilai() {
		return konversiNilai;
	}

	public void setKonversiNilai(KonversiNilai konversiNilai) {
		this.konversiNilai = konversiNilai;
	}

	public LocalDateTime getWaktuAmbil() {
		return waktuAmbil;
	}

	public void setWaktuAmbil(LocalDateTime waktuAmbil) {
		this.waktuAmbil = waktuAmbil;
	}

	public LocalDateTime getWaktuHapus() {
		return waktuHapus;
	}

	public void setWaktuHapus(LocalDateTime waktuHapus) {
		this.waktuHapus = waktuHapus;
	}

	public boolean isaKrsTerhapus() {
		return aKrsTerhapus;
	}

	public void setaKrsTerhapus(boolean aKrsTerhapus) {
		this.aKrsTerhapus = aKrsTerhapus;
	}

	public boolean isaKrsDisetujui() {
		return aKrsDisetujui;
	}

	public void setaKrsDisetujui(boolean aKrsDisetujui) {
		this.aKrsDisetujui = aKrsDisetujui;
	}

	public boolean isaKrsDiulang() {
		return aKrsDiulang;
	}

	public void setaKrsDiulang(boolean aKrsDiulang) {
		this.aKrsDiulang = aKrsDiulang;
	}
	
}
