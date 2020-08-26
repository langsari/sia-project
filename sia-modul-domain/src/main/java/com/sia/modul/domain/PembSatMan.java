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
@Table(name="pembel_sat_man")
public class PembSatMan {

	@Id
	@Column(name="id_pembel_sat_man")
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@org.hibernate.annotations.Type(type="pg-uuid")
	private UUID idPembSatMan;

	@ManyToOne(fetch = FetchType.EAGER, targetEntity=Pemb.class)
	@JoinColumn(name = "id_pemb")
    private Pemb pemb;

	@ManyToOne(fetch = FetchType.EAGER, targetEntity=SatMan.class)
	@JoinColumn(name = "id_sat_man")
    private SatMan satMan;

	public UUID getIdPembSatMan() {
		return idPembSatMan;
	}

	public void setIdPembSatMan(UUID idPembSatMan) {
		this.idPembSatMan = idPembSatMan;
	}

	public Pemb getPemb() {
		return pemb;
	}

	public void setPemb(Pemb pemb) {
		this.pemb = pemb;
	}

	public SatMan getSatMan() {
		return satMan;
	}

	public void setSatMan(SatMan satMan) {
		this.satMan = satMan;
	}
	
}
