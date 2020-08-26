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
@Table(name="mk_luar")
public class MKLuar {

	@Id
	@Column(name="id_mk_luar")
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@org.hibernate.annotations.Type(type="pg-uuid")
	private UUID idMKLuar;
	
	@NotEmpty(message="Nama Matakuliah tidak boleh kosong")
	@Column(name="nm_mk_luar")
	private String nmMKLuar;
	
	@Min(value = 1,message = "Nilai minimal untuk jumlah sks adalah 1")
	@Column(name="sks")
	private Integer sks;
	
	@Column(name="deskripsi_mk_luar")
	private String deskripsiMKLuar;
	
	@NotNull(message="Status Hapus Matakuliah tidak boleh kosong")
	@Column(name="a_mk_luar_terhapus")
	private boolean aMKLuarTerhapus;
	
	@ManyToOne(fetch = FetchType.EAGER, targetEntity=PedomanEkuivalensi.class)
	@JoinColumn(name = "id_pedoman_ekuivalensi")
    private PedomanEkuivalensi pedomanEkuivalensi;
	
	public MKLuar() {
		// TODO Auto-generated constructor stub
	}

	
	public String getDeskripsiMKLuar() {
		return deskripsiMKLuar;
	}


	public void setDeskripsiMKLuar(String deskripsiMKLuar) {
		this.deskripsiMKLuar = deskripsiMKLuar;
	}


	public UUID getIdMKLuar() {
		return idMKLuar;
	}

	public void setIdMKLuar(UUID idMKLuar) {
		this.idMKLuar = idMKLuar;
	}

	public String getNmMKLuar() {
		return nmMKLuar;
	}

	public void setNmMKLuar(String nmMKLuar) {
		this.nmMKLuar = nmMKLuar;
	}

	public Integer getSks() {
		return sks;
	}

	public void setSks(Integer sks) {
		this.sks = sks;
	}


	public boolean isaMKLuarTerhapus() {
		return aMKLuarTerhapus;
	}

	public void setaMKLuarTerhapus(boolean aMKLuarTerhapus) {
		this.aMKLuarTerhapus = aMKLuarTerhapus;
	}

	public PedomanEkuivalensi getPedomanEkuivalensi() {
		return pedomanEkuivalensi;
	}

	public void setPedomanEkuivalensi(PedomanEkuivalensi pedomanEkuivalensi) {
		this.pedomanEkuivalensi = pedomanEkuivalensi;
	}

}
