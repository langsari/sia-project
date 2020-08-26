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
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.joda.deser.LocalDateDeserializer;

/**
 * @author Bustan A Alfirdaus
 *
 */
@Entity
@Table(name="calon_pd")
public class CalonPD {

	@Id
	@Column(name="id_calon_pd")
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@org.hibernate.annotations.Type(type="pg-uuid")
	private UUID idCalonPD;
	
	@NotEmpty(message="Nama Calon Peserta Didik tidak boleh kosong")
	@Column(name="nm_calon_pd")
	private String nmCalonPD;
	
	@Column(name="pt_asal")
	private String ptAsal;
	
	@NotNull(message="Alamat tidak boleh kosong")
	@Column(name="alamat")
	private String alamat;
	
	@NotNull(message="Tempat Lahir tidak boleh kosong")
	@Column(name="tempat_lahir")
	private String tempatLahir;
	
	@Column(name="tgl_lahir")
	@JsonDeserialize(using=LocalDateDeserializer.class)
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private LocalDate tglLahir;
	
	@ManyToOne(fetch = FetchType.EAGER, targetEntity=SatMan.class)
	@JoinColumn(name = "id_sat_man")
    private SatMan satMan;
	
	@NotNull(message="Status Ekuivalensi tidak boleh kosong")
	@Column(name="a_status_ekuivalensi")
	private boolean aEkuivalensi;
	
	@ManyToOne(fetch = FetchType.EAGER, targetEntity=KatalogAlihjenjang.class)
	@JoinColumn(name = "id_katalog_alihjenjang")
	private KatalogAlihjenjang katalogAlihjenjang;

	public CalonPD() {
		// TODO Auto-generated constructor stub
	}

	public String getAlamat() {
		return alamat;
	}

	public void setAlamat(String alamat) {
		this.alamat = alamat;
	}

	public String getTempatLahir() {
		return tempatLahir;
	}

	public void setTempatLahir(String tempatLahir) {
		this.tempatLahir = tempatLahir;
	}

	public LocalDate getTglLahir() {
		return tglLahir;
	}

	public void setTglLahir(LocalDate tglLahir) {
		this.tglLahir = tglLahir;
	}

	public KatalogAlihjenjang getKatalogAlihjenjang() {
		return katalogAlihjenjang;
	}

	public void setKatalogAlihjenjang(KatalogAlihjenjang katalogAlihjenjang) {
		this.katalogAlihjenjang = katalogAlihjenjang;
	}

	public boolean isaEkuivalensi() {
		return aEkuivalensi;
	}

	public void setaEkuivalensi(boolean aEkuivalensi) {
		this.aEkuivalensi = aEkuivalensi;
	}

	public UUID getIdCalonPD() {
		return idCalonPD;
	}

	public void setIdCalonPD(UUID idCalonPD) {
		this.idCalonPD = idCalonPD;
	}

	public String getNmCalonPD() {
		return nmCalonPD;
	}

	public void setNmCalonPD(String nmCalonPD) {
		this.nmCalonPD = nmCalonPD;
	}

	public String getPtAsal() {
		return ptAsal;
	}

	public void setPtAsal(String ptAsal) {
		this.ptAsal = ptAsal;
	}

	public SatMan getSatMan() {
		return satMan;
	}

	public void setSatMan(SatMan satMan) {
		this.satMan = satMan;
	}

}
