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
@Table(name="smt")
public class Smt {

	@Id
	@Column(name="id_smt")
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@org.hibernate.annotations.Type(type="pg-uuid")
	private UUID idSmt;
	
    @NotEmpty(message="Nama Semester tidak boleh kosong")
	@Column(name="nm_smt")
	private String nmSmt;

    @NotNull(message="Jumlah Minggu tidak boleh kosong")
	@Column(name="jml_pertemuan")
	private Integer jmlPertemuan;
    
    @NotNull(message="Jenis Semester tidak boleh kosong")
	@Column(name="jenis_smt")
	private Integer jenisSmt;

    @NotNull(message="Status hapus tidak boleh kosong")
	@Column(name="a_smt_hapus")
	private boolean aSmthapus;
	
	public Smt() {
		// TODO Auto-generated constructor stub
	}

	public UUID getIdSmt() {
		return idSmt;
	}

	public void setIdSmt(UUID idSmt) {
		this.idSmt = idSmt;
	}

	public String getNmSmt() {
		return nmSmt;
	}

	public void setNmSmt(String nmSmt) {
		this.nmSmt = nmSmt==null?null:nmSmt.trim();
	}

	public Integer getJmlPertemuan() {
		return jmlPertemuan;
	}

	public void setJmlPertemuan(Integer jmlPertemuan) {
		this.jmlPertemuan = jmlPertemuan;
	}

	public boolean isaSmthapus() {
		return aSmthapus;
	}

	public void setaSmthapus(boolean aSmthapus) {
		this.aSmthapus = aSmthapus;
	}

	public Integer getJenisSmt() {
		return jenisSmt;
	}

	public void setJenisSmt(Integer jenisSmt) {
		this.jenisSmt = jenisSmt;
	}
	
	
}
