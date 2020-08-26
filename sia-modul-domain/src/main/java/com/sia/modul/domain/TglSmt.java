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
@Table(name="tgl_smt")
public class TglSmt {

	@Id
	@Column(name="id_tgl_smt")
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@org.hibernate.annotations.Type(type="pg-uuid")
	private UUID idTglSmt;
	
	@Column(name="tgl_akhir_bayar")
	@JsonDeserialize(using=LocalDateDeserializer.class)
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private LocalDate tglAkhirBayar;

	@Column(name="tgl_awal_susun_krs")
	@JsonDeserialize(using=LocalDateDeserializer.class)
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private LocalDate tglAwalSusunKrs;

	@Column(name="tgl_akhir_susun_krs")
	@JsonDeserialize(using=LocalDateDeserializer.class)
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private LocalDate tglAkhirSusunKrs;

	@Column(name="tgl_akhir_ubah_krs")
	@JsonDeserialize(using=LocalDateDeserializer.class)
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private LocalDate tglAkhirUbahKrs;

	@Column(name="tgl_akhir_batal_mk")
	@JsonDeserialize(using=LocalDateDeserializer.class)
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private LocalDate tglAkhirBatalMk;
    
	@Column(name="tgl_akhir_penilaian")
	@JsonDeserialize(using=LocalDateDeserializer.class)
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	@DateTimeFormat(pattern = "dd-MM-yyyy")
    @NotNull(message="Tanggal Akhir Penilaian tidak boleh kosong")
	private LocalDate tglAkhirPenilaian;

    @NotNull(message="Status aktif tidak boleh kosong")
	@Column(name="a_tgl_smt_aktif")
	private Boolean aTglSmtAktif;
    
    @NotNull(message="Status hapus tidak boleh kosong")
	@Column(name="a_tgl_smt_terhapus")
	private boolean aTglSmtTerhapus;

	@ManyToOne(fetch = FetchType.EAGER, targetEntity=ThnAjaran.class)
	@JoinColumn(name = "id_thn_ajaran")
    private ThnAjaran thnAjaran;

	@ManyToOne(fetch = FetchType.EAGER, targetEntity=Smt.class)
	@JoinColumn(name = "id_smt")
    private Smt smt;
	
    
	public TglSmt() {
		// TODO Auto-generated constructor stub
	}

	public UUID getIdTglSmt() {
		return idTglSmt;
	}

	public void setIdTglSmt(UUID idTglSmt) {
		this.idTglSmt = idTglSmt;
	}

	public LocalDate getTglAkhirBayar() {
		return tglAkhirBayar;
	}

	public void setTglAkhirBayar(LocalDate tglAkhirBayar) {
		this.tglAkhirBayar = tglAkhirBayar;
	}

	public boolean isaTglSmtTerhapus() {
		return aTglSmtTerhapus;
	}

	public void setaTglSmtTerhapus(boolean aTglSmtTerhapus) {
		this.aTglSmtTerhapus = aTglSmtTerhapus;
	}

	public ThnAjaran getThnAjaran() {
		return thnAjaran;
	}

	public void setThnAjaran(ThnAjaran thnAjaran) {
		this.thnAjaran = thnAjaran;
	}

	public Smt getSmt() {
		return smt;
	}

	public void setSmt(Smt smt) {
		this.smt = smt;
	}

	public Boolean getaTglSmtAktif() {
		return aTglSmtAktif;
	}

	public void setaTglSmtAktif(Boolean aTglSmtAktif) {
		this.aTglSmtAktif = aTglSmtAktif;
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
	
}
