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

@Entity
@Table(name="krs_hapus")
public class KrsHapus {

	@Id
	@Column(name="id_krs_hapus")
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@org.hibernate.annotations.Type(type="pg-uuid")
	private UUID idKrsHapus;
	
	@NotNull(message="Status Hapus Krs tidak boleh kosong")
	@Column(name="a_krs_hapus")
	private boolean aKrsHapus;
	
	@ManyToOne(fetch = FetchType.EAGER, targetEntity=EkuivalensiPd.class)
	@JoinColumn(name = "id_ekuivalensi_pd")
    private EkuivalensiPd ekuivalensiPd;
	
	@ManyToOne(fetch = FetchType.EAGER, targetEntity=Krs.class)
	@JoinColumn(name = "id_krs")
    private Krs krs;
	
	public KrsHapus() {
		// TODO Auto-generated constructor stub
	}

	public UUID getIdKrsHapus() {
		return idKrsHapus;
	}

	public void setIdKrsHapus(UUID idKrsHapus) {
		this.idKrsHapus = idKrsHapus;
	}

	public boolean isaKrsHapus() {
		return aKrsHapus;
	}

	public void setaKrsHapus(boolean aKrsHapus) {
		this.aKrsHapus = aKrsHapus;
	}

	public EkuivalensiPd getEkuivalensiPd() {
		return ekuivalensiPd;
	}

	public void setEkuivalensiPd(EkuivalensiPd ekuivalensiPd) {
		this.ekuivalensiPd = ekuivalensiPd;
	}

	public Krs getKrs() {
		return krs;
	}

	public void setKrs(Krs krs) {
		this.krs = krs;
	}
	
}
