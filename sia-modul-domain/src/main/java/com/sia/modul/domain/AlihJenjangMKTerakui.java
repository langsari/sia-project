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
@Table(name="alihjenjang_mk_terakui")
public class AlihJenjangMKTerakui {
	
	@Id
	@GeneratedValue(generator="uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@org.hibernate.annotations.Type(type="pg-uuid")
	@Column(name="id_alihjenjang_mk_terakui")
	private UUID idAlihJenjangMKTerakui;
	
	@ManyToOne(fetch = FetchType.EAGER, targetEntity=CalonPD.class)
	@JoinColumn(name="id_calon_pd")
	private CalonPD calonPD;
	
	@ManyToOne(fetch = FetchType.EAGER, targetEntity=MK.class)
	@JoinColumn(name="id_mk")
	private MK mk;

	public UUID getIdAlihJenjangMKTerakui() {
		return idAlihJenjangMKTerakui;
	}

	public void setIdAlihJenjangMKTerakui(UUID idAlihJenjangMKTerakui) {
		this.idAlihJenjangMKTerakui = idAlihJenjangMKTerakui;
	}

	public CalonPD getCalonPD() {
		return calonPD;
	}

	public void setCalonPD(CalonPD calonPD) {
		this.calonPD = calonPD;
	}

	public MK getMk() {
		return mk;
	}

	public void setMk(MK mk) {
		this.mk = mk;
	}
}
