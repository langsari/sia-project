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
@Table(name="ekuivalensi_mk_alihjenjang")
public class EkuivalensiMKAlihjenjang {
	
	@Id
	@GeneratedValue(generator="uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@org.hibernate.annotations.Type(type="pg-uuid")
	@Column(name="id_ekuivalensi_mk_alihjenjang")
	private UUID idEkuivalensiMKAlihjenjang;
	
	@ManyToOne(fetch = FetchType.EAGER, targetEntity=MK.class)
	@JoinColumn(name="id_mk")
	private MK mk;
	
	@ManyToOne(fetch = FetchType.EAGER, targetEntity=MKAlihjenjang.class)
	@JoinColumn(name="id_mk_alihjenjang")
	private MKAlihjenjang mkAlihjenjang;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="id_relasi_mk_alihjenjang")
	private RelasiMKAlihjenjang relasiMKAlihjenjang;

	public UUID getIdEkuivalensiMKAlihjenjang() {
		return idEkuivalensiMKAlihjenjang;
	}

	public void setIdEkuivalensiMKAlihjenjang(UUID idEkuivalensiMKAlihjenjang) {
		this.idEkuivalensiMKAlihjenjang = idEkuivalensiMKAlihjenjang;
	}

	public MK getMk() {
		return mk;
	}

	public void setMk(MK mk) {
		this.mk = mk;
	}

	public MKAlihjenjang getMkAlihjenjang() {
		return mkAlihjenjang;
	}

	public void setMkAlihjenjang(MKAlihjenjang mkAlihjenjang) {
		this.mkAlihjenjang = mkAlihjenjang;
	}

	public RelasiMKAlihjenjang getRelasiMKAlihjenjang() {
		return relasiMKAlihjenjang;
	}

	public void setRelasiMKAlihjenjang(RelasiMKAlihjenjang relasiMKAlihjenjang) {
		this.relasiMKAlihjenjang = relasiMKAlihjenjang;
	}
}
