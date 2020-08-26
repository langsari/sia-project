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
@Table(name="ekuivalensi_mk_pekuivalensi")
public class EkuivalensiMKPekuivalensi {
	
	@Id
	@GeneratedValue(generator="uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@org.hibernate.annotations.Type(type="pg-uuid")
	@Column(name="id_ekuivalensi_mk_pekuivalensi")
	private UUID idEkuivalensiMKPekuivalensi;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="id_mk")
	private MK mk;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="id_mk_luar")
	private MKLuar mkLuar;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="id_relasi_mk_pekuivalensi")
	private RelasiMKPekuivalensi relasiMKPekuivalensi;

	public UUID getIdEkuivalensiMKPekuivalensi() {
		return idEkuivalensiMKPekuivalensi;
	}

	public void setIdEkuivalensiMKPekuivalensi(UUID idEkuivalensiMKPekuivalensi) {
		this.idEkuivalensiMKPekuivalensi = idEkuivalensiMKPekuivalensi;
	}

	public MK getMk() {
		return mk;
	}

	public void setMk(MK mk) {
		this.mk = mk;
	}

	public MKLuar getMkLuar() {
		return mkLuar;
	}

	public void setMkLuar(MKLuar mkLuar) {
		this.mkLuar = mkLuar;
	}

	public RelasiMKPekuivalensi getRelasiMKPekuivalensi() {
		return relasiMKPekuivalensi;
	}

	public void setRelasiMKPekuivalensi(RelasiMKPekuivalensi relasiMKPekuivalensi) {
		this.relasiMKPekuivalensi = relasiMKPekuivalensi;
	}
}
