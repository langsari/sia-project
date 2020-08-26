package com.sia.modul.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="kondisi")
public class Kondisi {
	@Id
	@SequenceGenerator(name="next_id_kondisi",sequenceName="next_id_kondisi", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="next_id_kondisi")
	@Column(name="id_kondisi")
	private Integer idKondisi;
	
	@Column(name="nm_kondisi")
	private String nmKondisi;
	
	@Column(name="column_reference")
	private String columnReference;
	
	@Column(name="a_kondisi_terhapus")
	private boolean aKondisiTerhapus;
	
	public boolean isaKondisiTerhapus() {
		return aKondisiTerhapus;
	}
	public void setaKondisiTerhapus(boolean aKondisiTerhapus) {
		this.aKondisiTerhapus = aKondisiTerhapus;
	}
	public Integer getIdKondisi() {
		return idKondisi;
	}	
	public String getNmKondisi() {
		return nmKondisi;
	}
	public void setNmKondisi(String nmKondisi) {
		this.nmKondisi = nmKondisi;
	}
	public void setIdKondisi(Integer idKondisi) {
		this.idKondisi = idKondisi;
	}
	public String getColumnReference() {
		return columnReference;
	}
	public void setColumnReference(String columnReference) {
		this.columnReference = columnReference;
	}
	
}
