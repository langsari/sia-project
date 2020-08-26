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
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.joda.deser.LocalDateDeserializer;

@Entity
@Table(name="pertemuan_pembelajaran")
public class PertemuanPembelajaran {
	@Id
	@Column(name="id_pertemuan_pembelajaran")
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@org.hibernate.annotations.Type(type="pg-uuid")
	private UUID idPertemuanPembelajaran;
	
	@NotNull(message="Pertemuan pembelajaran tidak boleh kosong")
	@Min(value = 1, message="Pertemuan minimal 1")
	@Column(name="pertemuan_pembelajaran")
	private Integer pertemuan;
	
	@Column(name="tgl_pertemuan")
	@JsonDeserialize(using=LocalDateDeserializer.class)
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	@DateTimeFormat(pattern = "dd-MM-yyyy")
    @NotNull(message="Tanggal Pertemuan tidak boleh kosong")
	private LocalDate tglPertemuan;

	public LocalDate getTglPertemuan() {
		return tglPertemuan;
	}

	public void setTglPertemuan(LocalDate tglPertemuan) {
		this.tglPertemuan = tglPertemuan;
	}

	@NotEmpty(message="Materi pertemuan tidak boleh kosong")
	@Column(name="materi")
	private String materi;

	@Column(name="kendala_perkuliahan")
	private String kendalaPerkuliahan;

	@Column(name="tanggapan_peserta_didik")
	private String tanggapanPd;
	
	@ManyToOne(fetch = FetchType.EAGER, targetEntity=Pemb.class)
	@JoinColumn(name="id_pemb")
	private Pemb pemb;

	public UUID getIdPertemuanPembelajaran() {
		return idPertemuanPembelajaran;
	}

	public void setIdPertemuanPembelajaran(UUID idPertemuanPembelajaran) {
		this.idPertemuanPembelajaran = idPertemuanPembelajaran;
	}

	public Integer getPertemuan() {
		return pertemuan;
	}

	public void setPertemuan(Integer pertemuan) {
		this.pertemuan = pertemuan;
	}

	public String getMateri() {
		return materi;
	}

	public void setMateri(String materi) {
		this.materi = materi;
	}

	public String getKendalaPerkuliahan() {
		return kendalaPerkuliahan;
	}

	public void setKendalaPerkuliahan(String kendalaPerkuliahan) {
		this.kendalaPerkuliahan = kendalaPerkuliahan;
	}

	public String getTanggapanPd() {
		return tanggapanPd;
	}

	public void setTanggapanPd(String tanggapanPd) {
		this.tanggapanPd = tanggapanPd;
	}

	public Pemb getPemb() {
		return pemb;
	}

	public void setPemb(Pemb pemb) {
		this.pemb = pemb;
	}
	
	
}
