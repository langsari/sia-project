package com.its.sia.model;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.swing.text.View;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonView;

@Entity
@Table(name="ptk")
public class Ptk {

	@Id
	@Column(name="id_ptk")
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@org.hibernate.annotations.Type(type="pg-uuid")
	private UUID idPtk;
	
    @NotEmpty(message="NIP Pendidik/Tenaga kependidikan tidak boleh kosong")
	@Column(name="nip_ptk")
	private String nipPtk;

    @NotNull(message="Nama Pendidik/Tenaga kependidikan tidak boleh kosong")
	@Column(name="nm_ptk")
	private String nmPtk;
    
    @NotNull(message="Status Pendidik/Tenaga kependidikan tidak boleh kosong")
	@Column(name="status_ptk")
	private Boolean statusPtk;

    @NotNull(message="Status hapus Pendidik/Tenaga kependidikan tidak boleh kosong")
	@Column(name="a_ptk_terhapus")
	private boolean aPtkTerhapus;
    
	public UUID getIdPtk() {
		return idPtk;
	}

	public void setIdPtk(UUID idPtk) {
		this.idPtk = idPtk;
	}

	public String getNipPtk() {
		return nipPtk;
	}

	public void setNipPtk(String nipPtk) {
		this.nipPtk = nipPtk.trim();
	}

	public String getNmPtk() {
		return nmPtk;
	}

	public void setNmPtk(String nmPtk) {
		this.nmPtk = nmPtk.trim();
	}
	
	public Boolean getStatusPtk() {
		return statusPtk;
	}

	public void setStatusPtk(Boolean statusPtk) {
		this.statusPtk = statusPtk;
	}

	public boolean isaPtkTerhapus() {
		return aPtkTerhapus;
	}

	public void setaPtkTerhapus(boolean aPtkTerhapus) {
		this.aPtkTerhapus = aPtkTerhapus;
	}
	
	public Ptk(){
		
	}
	
	public Ptk(Ptk copyOfPtk)
	{
		this.setIdPtk(copyOfPtk.getIdPtk());
		this.setNipPtk(copyOfPtk.getNipPtk());
		this.setNmPtk(copyOfPtk.getNmPtk());
		this.setStatusPtk(copyOfPtk.getStatusPtk());
		this.setaPtkTerhapus(copyOfPtk.isaPtkTerhapus());
	}

}
