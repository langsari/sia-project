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
@Table(name="ipk")
public class Ipk {
	@Id
	@Column(name="id_ipk")
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@org.hibernate.annotations.Type(type="pg-uuid")
	private UUID idIpk;

	@ManyToOne(fetch = FetchType.EAGER, targetEntity=Pd.class)
	@JoinColumn(name = "id_pd")
    private Pd pd;

	@Column(name="nilai_ipk")
	private Double nilaiIpk;

	public UUID getIdIpk() {
		return idIpk;
	}

	public void setIdIpk(UUID idIpk) {
		this.idIpk = idIpk;
	}

	public Pd getPd() {
		return pd;
	}

	public void setPd(Pd pd) {
		this.pd = pd;
	}

	public Double getNilaiIpk() {
		return nilaiIpk;
	}

	public void setNilaiIpk(Double nilaiIpk) {
		this.nilaiIpk = nilaiIpk;
	}
	
}
