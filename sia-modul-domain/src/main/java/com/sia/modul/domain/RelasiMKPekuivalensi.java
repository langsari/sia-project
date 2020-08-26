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

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name="relasi_mk_pekuivalensi")
public class RelasiMKPekuivalensi {
	@Id
	@GeneratedValue(generator="uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@org.hibernate.annotations.Type(type="pg-uuid")
	@Column(name="id_relasi_mk_pekuivalensi")
	private UUID idRelasiMKPekuivalensi;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="id_pedoman_ekuivalensi")
	private PedomanEkuivalensi pedomanEkuivalensi;
	
	@Column(name="relasi")
	private String relasi;

	@Column(name="relasi_id")
	private String relasiID;
	
	public String getRelasiID() {
		return relasiID;
	}

	public void setRelasiID(String relasiID) {
		this.relasiID = relasiID;
	}

	public UUID getIdRelasiMKPekuivalensi() {
		return idRelasiMKPekuivalensi;
	}

	public void setIdRelasiMKPekuivalensi(UUID idRelasiMKPekuivalensi) {
		this.idRelasiMKPekuivalensi = idRelasiMKPekuivalensi;
	}

	public PedomanEkuivalensi getPedomanEkuivalensi() {
		return pedomanEkuivalensi;
	}

	public void setPedomanEkuivalensi(PedomanEkuivalensi pedomanEkuivalensi) {
		this.pedomanEkuivalensi = pedomanEkuivalensi;
	}

	public String getRelasi() {
		return relasi;
	}

	public void setRelasi(String relasi) {
		this.relasi = relasi;
	}
	
	
}
