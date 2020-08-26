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
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="mk_alihjenjang")
public class MKAlihjenjang {

	@Id
	@Column(name="id_mk_alihjenjang")
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@org.hibernate.annotations.Type(type="pg-uuid")
	private UUID idMKAlihjenjang;
	
	@NotEmpty(message="Nama Matakuliah tidak boleh kosong")
	@Column(name="nm_mk")
	private String nmMKAlihjenjang;
	
	@Min(value = 1,message = "Nilai minimal untuk jumlah sks adalah 1")
	@Column(name="jumlah_sks")
	private Integer jumlahSKS;
	
	@Column(name="deskripsi_mk")
	private String deskripsiMKAlihjenjang;
	
	@NotNull(message="Status Hapus Matakuliah tidak boleh kosong")
	@Column(name="a_mk_terhapus")
	private boolean aMKAlihjenjangTerhapus;
	
	@ManyToOne(fetch = FetchType.EAGER, targetEntity=KatalogAlihjenjang.class)
	@JoinColumn(name = "id_katalog_alihjenjang")
    private KatalogAlihjenjang katalogAlihjenjang;
	
	@Column(name="kode_mk_alihjenjang")
	private String kodeMKAlihjenjang;
	
	public MKAlihjenjang() {
		// TODO Auto-generated constructor stub
	}
	
	public String getKodeMKAlihjenjang() {
		return kodeMKAlihjenjang;
	}

	public void setKodeMKAlihjenjang(String kodeMKAlihjenjang) {
		this.kodeMKAlihjenjang = kodeMKAlihjenjang;
	}

	public UUID getIdMKAlihjenjang() {
		return idMKAlihjenjang;
	}

	public void setIdMKAlihjenjang(UUID idMKAlihjenjang) {
		this.idMKAlihjenjang = idMKAlihjenjang;
	}

	public String getNmMKAlihjenjang() {
		return nmMKAlihjenjang;
	}

	public void setNmMKAlihjenjang(String nmMKAlihjenjang) {
		this.nmMKAlihjenjang = nmMKAlihjenjang;
	}

	public Integer getJumlahSKS() {
		return jumlahSKS;
	}

	public void setJumlahSKS(Integer jumlahSKS) {
		this.jumlahSKS = jumlahSKS;
	}

	public String getDeskripsiMKAlihjenjang() {
		return deskripsiMKAlihjenjang;
	}

	public void setDeskripsiMKAlihjenjang(String deskripsiMKAlihjenjang) {
		this.deskripsiMKAlihjenjang = deskripsiMKAlihjenjang;
	}

	public boolean isaMKAlihjenjangTerhapus() {
		return aMKAlihjenjangTerhapus;
	}

	public void setaMKAlihjenjangTerhapus(boolean aMKAlihjenjangTerhapus) {
		this.aMKAlihjenjangTerhapus = aMKAlihjenjangTerhapus;
	}

	public KatalogAlihjenjang getKatalogAlihjenjang() {
		return katalogAlihjenjang;
	}

	public void setKatalogAlihjenjang(KatalogAlihjenjang katalogAlihjenjang) {
		this.katalogAlihjenjang = katalogAlihjenjang;
	}
}
