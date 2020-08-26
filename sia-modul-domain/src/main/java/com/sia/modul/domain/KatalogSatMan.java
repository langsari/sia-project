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
@Table(name="katalog_satman")
public class KatalogSatMan {

	@Id
	@Column(name="id_katalog_satman")
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@org.hibernate.annotations.Type(type="pg-uuid")
	private UUID idKatalogSatMan;
	
	@ManyToOne(fetch = FetchType.EAGER, targetEntity=KatalogAlihjenjang.class)
	@JoinColumn(name = "id_katalog_alihjenjang")
    private KatalogAlihjenjang katalog;
	
	@ManyToOne(fetch = FetchType.EAGER, targetEntity=SatMan.class)
	@JoinColumn(name = "id_sat_man")
    private SatMan satMan;

	public UUID getIdKatalogSatMan() {
		return idKatalogSatMan;
	}

	public void setIdKatalogSatMan(UUID idKatalogSatMan) {
		this.idKatalogSatMan = idKatalogSatMan;
	}

	public KatalogAlihjenjang getKatalog() {
		return katalog;
	}

	public void setKatalog(KatalogAlihjenjang katalog) {
		this.katalog = katalog;
	}

	public SatMan getSatMan() {
		return satMan;
	}

	public void setSatMan(SatMan satMan) {
		this.satMan = satMan;
	}
}
