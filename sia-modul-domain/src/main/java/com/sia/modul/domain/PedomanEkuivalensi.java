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
@Table(name="pedoman_ekuivalensi")
public class PedomanEkuivalensi {

	@Id
	@Column(name="id_pedoman_ekuivalensi")
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@org.hibernate.annotations.Type(type="pg-uuid")
	private UUID idPedomanEkuivalensi;
	
	@NotEmpty(message="Nama Prodi tidak boleh kosong")
	@Column(name="nm_prodi")
	private String nmProdi;
	
	@Column(name="catatan")
	private String catatan;
	
	@NotNull(message="Status hapus tidak boleh kosong")
	@Column(name="a_pedoman_ekuivalensi_terhapus")
	private boolean aPedomanEkuivalensiTerhapus;
	
	@ManyToOne(fetch = FetchType.EAGER, targetEntity=SatMan.class)
	@JoinColumn(name = "id_sat_man")
    private SatMan satMan;
	
	public PedomanEkuivalensi() {
		// TODO Auto-generated constructor stub
	}

	public UUID getIdPedomanEkuivalensi() {
		return idPedomanEkuivalensi;
	}

	public void setIdPedomanEkuivalensi(UUID idPedomanEkuivalensi) {
		this.idPedomanEkuivalensi = idPedomanEkuivalensi;
	}

	public String getNmProdi() {
		return nmProdi;
	}

	public void setNmProdi(String nmProdi) {
		this.nmProdi = nmProdi;
	}

	public String getCatatan() {
		return catatan;
	}

	public void setCatatan(String catatan) {
		this.catatan = catatan;
	}

	public boolean isaPedomanEkuivalensiTerhapus() {
		return aPedomanEkuivalensiTerhapus;
	}

	public void setaPedomanEkuivalensiTerhapus(boolean aPedomanEkuivalensiTerhapus) {
		this.aPedomanEkuivalensiTerhapus = aPedomanEkuivalensiTerhapus;
	}

	public SatMan getSatMan() {
		return satMan;
	}

	public void setSatMan(SatMan satMan) {
		this.satMan = satMan;
	}

}
