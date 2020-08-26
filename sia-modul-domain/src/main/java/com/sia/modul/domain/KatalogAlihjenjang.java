package com.sia.modul.domain;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="katalog_alihjenjang")
public class KatalogAlihjenjang {

	@Id
	@Column(name="id_katalog_alihjenjang")
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@org.hibernate.annotations.Type(type="pg-uuid")
	private UUID idKatalogAlihjenjang;
	
	@NotEmpty(message="Nama Katalog tidak boleh kosong")
	@Column(name="nm_katalog")
	private String nmKatalog;
	
	@Column(name="catatan")
	private String catatan;
	
	@NotNull(message="Status hapus tidak boleh kosong")
	@Column(name="a_katalog_alihjenjang_terhapus")
	private boolean aTerhapus;
	
	public KatalogAlihjenjang() {
	}

	public UUID getIdKatalogAlihjenjang() {
		return idKatalogAlihjenjang;
	}

	public void setIdKatalogAlihjenjang(UUID idKatalogAlihjenjang) {
		this.idKatalogAlihjenjang = idKatalogAlihjenjang;
	}

	public String getNmKatalog() {
		return nmKatalog;
	}

	public void setNmKatalog(String nmKatalog) {
		this.nmKatalog = nmKatalog;
	}

	public String getCatatan() {
		return catatan;
	}

	public void setCatatan(String catatan) {
		this.catatan = catatan;
	}

	public boolean isaTerhapus() {
		return aTerhapus;
	}

	public void setaTerhapus(boolean aTerhapus) {
		this.aTerhapus = aTerhapus;
	}
	
}
