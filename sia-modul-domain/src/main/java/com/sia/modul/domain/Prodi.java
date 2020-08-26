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
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="prodi")
public class Prodi {

	@Id
	@Column(name="id_prodi")
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@org.hibernate.annotations.Type(type="pg-uuid")
	private UUID idProdi;
	
	@NotEmpty(message="Nama Prodi tidak boleh kosong")
	@Column(name="nm_prodi")
	private String nmProdi;
	
	@NotNull(message="Status hapus Prodi tidak boleh kosong")
	@Column(name="a_prodi_terhapus")
	private boolean aProdiTerhapus;
	
	@ManyToOne(fetch = FetchType.EAGER, targetEntity=Ptk.class)
	@JoinColumn(name = "id_perguruan_tinggi")
    private PerguruanTinggi PerguruanTinggi;
	
	public Prodi() {
		// TODO Auto-generated constructor stub
	}

	public UUID getIdProdi() {
		return idProdi;
	}

	public void setIdProdi(UUID idProdi) {
		this.idProdi = idProdi;
	}

	public String getNmProdi() {
		return nmProdi;
	}

	public void setNmProdi(String nmProdi) {
		this.nmProdi = nmProdi;
	}

	public boolean isaProdiTerhapus() {
		return aProdiTerhapus;
	}

	public void setaProdiTerhapus(boolean aProdiTerhapus) {
		this.aProdiTerhapus = aProdiTerhapus;
	}

	public PerguruanTinggi getPerguruanTinggi() {
		return PerguruanTinggi;
	}

	public void setPerguruanTinggi(PerguruanTinggi perguruanTinggi) {
		PerguruanTinggi = perguruanTinggi;
	}

}
