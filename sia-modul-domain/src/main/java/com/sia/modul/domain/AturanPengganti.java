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
import org.joda.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.joda.deser.LocalDateDeserializer;

@Entity
@Table(name="aturan_pengganti")
public class AturanPengganti {

	@Id
	@Column(name="id_aturan_pengganti")
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@org.hibernate.annotations.Type(type="pg-uuid")
	private UUID idAturanPengganti;
	
	@ManyToOne(fetch = FetchType.EAGER, targetEntity=TglSmt.class)
	@JoinColumn(name = "id_tgl_smt")
    private TglSmt tglSmt;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="id_sat_man")
	private SatMan satMan;
	
	@Column(name="pengganti_tgl_akhir_bayar")
	@JsonDeserialize(using=LocalDateDeserializer.class)
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private LocalDate tglAkhirBayar;

	@Column(name="pengganti_tgl_awal_susun_krs")
	@JsonDeserialize(using=LocalDateDeserializer.class)
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private LocalDate tglAwalSusunKrs;

	@Column(name="pengganti_tgl_akhir_susun_krs")
	@JsonDeserialize(using=LocalDateDeserializer.class)
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private LocalDate tglAkhirSusunKrs;

	@Column(name="pengganti_tgl_akhir_ubah_krs")
	@JsonDeserialize(using=LocalDateDeserializer.class)
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private LocalDate tglAkhirUbahKrs;

	@Column(name="pengganti_tgl_akhir_batal_mk")
	@JsonDeserialize(using=LocalDateDeserializer.class)
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private LocalDate tglAkhirBatalMk;
    
	@Column(name="pengganti_tgl_akhir_penilaian")
	@JsonDeserialize(using=LocalDateDeserializer.class)
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	@DateTimeFormat(pattern = "dd-MM-yyyy")
    @NotNull(message="Tanggal Akhir Penilaian tidak boleh kosong")
	private LocalDate tglAkhirPenilaian;
    	
	public AturanPengganti() {
		// TODO Auto-generated constructor stub
	}

	public UUID getIdAturanPengganti() {
		return idAturanPengganti;
	}

	public void setIdAturanPengganti(UUID idAturanPengganti) {
		this.idAturanPengganti = idAturanPengganti;
	}

	public TglSmt getTglSmt() {
		return tglSmt;
	}

	public void setTglSmt(TglSmt tglSmt) {
		this.tglSmt = tglSmt;
	}

	public LocalDate getTglAkhirBayar() {
		return tglAkhirBayar;
	}

	public void setTglAkhirBayar(LocalDate tglAkhirBayar) {
		this.tglAkhirBayar = tglAkhirBayar;
	}

	public LocalDate getTglAwalSusunKrs() {
		return tglAwalSusunKrs;
	}

	public void setTglAwalSusunKrs(LocalDate tglAwalSusunKrs) {
		this.tglAwalSusunKrs = tglAwalSusunKrs;
	}

	public LocalDate getTglAkhirSusunKrs() {
		return tglAkhirSusunKrs;
	}

	public void setTglAkhirSusunKrs(LocalDate tglAkhirSusunKrs) {
		this.tglAkhirSusunKrs = tglAkhirSusunKrs;
	}

	public LocalDate getTglAkhirUbahKrs() {
		return tglAkhirUbahKrs;
	}

	public void setTglAkhirUbahKrs(LocalDate tglAkhirSetujuKrs) {
		this.tglAkhirUbahKrs = tglAkhirSetujuKrs;
	}

	public LocalDate getTglAkhirBatalMk() {
		return tglAkhirBatalMk;
	}

	public void setTglAkhirBatalMk(LocalDate tglAkhirBatalMk) {
		this.tglAkhirBatalMk = tglAkhirBatalMk;
	}

	public LocalDate getTglAkhirPenilaian() {
		return tglAkhirPenilaian;
	}

	public void setTglAkhirPenilaian(LocalDate tglAkhirPenilaian) {
		this.tglAkhirPenilaian = tglAkhirPenilaian;
	}

	public SatMan getSatMan() {
		return satMan;
	}

	public void setSatMan(SatMan satMan) {
		this.satMan = satMan;
	}
	
}
