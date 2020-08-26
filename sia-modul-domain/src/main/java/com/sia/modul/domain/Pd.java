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
@Table(name="pd")
public class Pd {

	@Id
	@Column(name="id_pd")
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@org.hibernate.annotations.Type(type="pg-uuid")
	private UUID idPd;
	
    @NotEmpty(message="NIM Peserta didik tidak boleh kosong")
	@Column(name="nim_pd")
	private String nimPd;

    @NotEmpty(message="Nama Peserta didik tidak boleh kosong")
	@Column(name="nm_pd")
	private String nmPd;

    @NotNull(message="Angkatan Peserta didik tidak boleh kosong")
    @Min(value=0,message="Angkatan peserta didik tidak boleh minus")
	@Column(name="angkatan_pd")
	private Integer angkatanPd;
    
    @NotNull(message="Status hapus Peserta didik tidak boleh kosong")
	@Column(name="a_pd_terhapus")
	private boolean aPdTerhapus;
    
	@ManyToOne(fetch = FetchType.EAGER, targetEntity=Ptk.class)
	@JoinColumn(name = "id_ptk")
    private Ptk ptk;
	
	/*@OneToOne(fetch = FetchType.EAGER, targetEntity=Pengguna.class)
	@JoinColumn(name = "id_pd")
	private Pengguna pengguna;*/
    
	/*public Pengguna getPengguna() {
		return pengguna;
	}

	public void setPengguna(Pengguna pengguna) {
		this.pengguna = pengguna;
	}*/

	public Pd() {
		// TODO Auto-generated constructor stub
	}
	
	public Pd(Pd copyOfPd){
		this.setIdPd(copyOfPd.getIdPd());
		this.setNimPd(copyOfPd.getNimPd());
		this.setNmPd(copyOfPd.getNmPd());
		this.setAngkatanPd(copyOfPd.getAngkatanPd());
		this.setPtk(copyOfPd.getPtk()==null?null:new Ptk(copyOfPd.getPtk()));
		this.setaPdTerhapus(copyOfPd.isaPdTerhapus());
	}

	public UUID getIdPd() {
		return idPd;
	}

	public void setIdPd(UUID idPd) {
		this.idPd = idPd;
	}

	public String getNimPd() {
		return nimPd;
	}

	public void setNimPd(String nimPd) {
		this.nimPd = nimPd.trim();
	}

	public String getNmPd() {
		return nmPd;
	}

	public void setNmPd(String nmPd) {
		this.nmPd = nmPd.trim();
	}

	public boolean isaPdTerhapus() {
		return aPdTerhapus;
	}

	public void setaPdTerhapus(boolean aPdTerhapus) {
		this.aPdTerhapus = aPdTerhapus;
	}

	public Ptk getPtk() {
		return ptk;
	}

	public void setPtk(Ptk ptk) {
		this.ptk = ptk;
	}

	public Integer getAngkatanPd() {
		return angkatanPd;
	}

	public void setAngkatanPd(Integer angkatanPd) {
		this.angkatanPd = angkatanPd;
	}
	
	
}
