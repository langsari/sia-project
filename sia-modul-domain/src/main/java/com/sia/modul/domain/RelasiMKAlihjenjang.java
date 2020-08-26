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
@Table(name="relasi_mk_alihjenjang")
public class RelasiMKAlihjenjang {
	@Id
	@GeneratedValue(generator="uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@org.hibernate.annotations.Type(type="pg-uuid")
	@Column(name="id_relasi_mk_alihjenjang")
	private UUID idRelasiMKAlihjenjang;
	
	@ManyToOne(fetch = FetchType.EAGER, targetEntity = KatalogAlihjenjang.class)
	@JoinColumn(name="katalog_id")
	private KatalogAlihjenjang katalog;
	
	@ManyToOne(fetch = FetchType.EAGER, targetEntity = Kurikulum.class)
	@JoinColumn(name="kurikulum_id")
	private Kurikulum kurikulum;
	
	@Column(name="relasi_mk_alihjenjang")
	private String relasiMKAlihjenjang;

	@Column(name="detail_relasi_mk_alihjenjang")
	private String detailRelasiMKAlihjenjang;

	@Column(name="relasi_mk")
	private String relasiMK;

	@Column(name="detail_relasi_mk")
	private String detailRelasiMK;

	public Kurikulum getKurikulum() {
		return kurikulum;
	}

	public void setKurikulum(Kurikulum kurikulum) {
		this.kurikulum = kurikulum;
	}

	public UUID getIdRelasiMKAlihjenjang() {
		return idRelasiMKAlihjenjang;
	}

	public void setIdRelasiMKAlihjenjang(UUID idRelasiMKAlihjenjang) {
		this.idRelasiMKAlihjenjang = idRelasiMKAlihjenjang;
	}

	

	public KatalogAlihjenjang getKatalog() {
		return katalog;
	}

	public void setKatalog(KatalogAlihjenjang katalog) {
		this.katalog = katalog;
	}

	public String getRelasiMKAlihjenjang() {
		return relasiMKAlihjenjang;
	}

	public void setRelasiMKAlihjenjang(String relasiMKAlihjenjang) {
		this.relasiMKAlihjenjang = relasiMKAlihjenjang;
	}

	public String getDetailRelasiMKAlihjenjang() {
		return detailRelasiMKAlihjenjang;
	}

	public void setDetailRelasiMKAlihjenjang(String detailRelasiMKAlihjenjang) {
		this.detailRelasiMKAlihjenjang = detailRelasiMKAlihjenjang;
	}

	public String getRelasiMK() {
		return relasiMK;
	}

	public void setRelasiMK(String relasiMK) {
		this.relasiMK = relasiMK;
	}

	public String getDetailRelasiMK() {
		return detailRelasiMK;
	}

	public void setDetailRelasiMK(String detailRelasiMK) {
		this.detailRelasiMK = detailRelasiMK;
	}
}
