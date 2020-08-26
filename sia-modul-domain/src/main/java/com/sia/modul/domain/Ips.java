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
import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.joda.deser.LocalDateTimeDeserializer;

@Entity
@Table(name="ips")
public class Ips {

	@Id
	@Column(name="id_ips")
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@org.hibernate.annotations.Type(type="pg-uuid")
	private UUID idIps;

	@ManyToOne(fetch = FetchType.EAGER, targetEntity=Pd.class)
	@JoinColumn(name = "id_pd")
    private Pd pd;

	@ManyToOne(fetch = FetchType.EAGER, targetEntity=TglSmt.class)
	@JoinColumn(name = "id_tgl_smt")
    private TglSmt tglSmt;

	/*aku kasih property yang beda supaya nggak sama dengan entitas*/
	@Column(name="nilai_ips")
	private Double nilaiIps;
	
	@JsonDeserialize(using=LocalDateTimeDeserializer.class)
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	@Column(name="tgl_buat_ips")
	private LocalDateTime tglBuatIps;
	
	public UUID getIdIps() {
		return idIps;
	}

	public void setIdIps(UUID idIps) {
		this.idIps = idIps;
	}

	public Pd getPd() {
		return pd;
	}

	public void setPd(Pd pd) {
		this.pd = pd;
	}

	public TglSmt getTglSmt() {
		return tglSmt;
	}

	public void setTglSmt(TglSmt tglSmt) {
		this.tglSmt = tglSmt;
	}

	public Double getNilaiIps() {
		return nilaiIps;
	}

	public void setNilaiIps(Double nilaiIps) {
		this.nilaiIps = nilaiIps;
	}

	public LocalDateTime getTglBuatIps() {
		return tglBuatIps;
	}

	public void setTglBuatIps(LocalDateTime tglBuatIps) {
		this.tglBuatIps = tglBuatIps;
	}
	
}
