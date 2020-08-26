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
@Table(name="pembayaran")
public class Pembayaran {

	@Id
	@Column(name="id_pembayaran")
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@org.hibernate.annotations.Type(type="pg-uuid")
	private UUID idPembayaran;

	@ManyToOne(fetch = FetchType.EAGER, targetEntity=Pd.class)
	@JoinColumn(name = "id_pd")
    private Pd pd;

	@ManyToOne(fetch = FetchType.EAGER, targetEntity=TglSmt.class)
	@JoinColumn(name = "id_tgl_smt")
    private TglSmt tglSmt;
	
	@JsonDeserialize(using=LocalDateTimeDeserializer.class)
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	@Column(name="tgl_pembayaran")
	private LocalDateTime tglPembayaran;

	public UUID getIdPembayaran() {
		return idPembayaran;
	}

	public void setIdPembayaran(UUID idPembayaran) {
		this.idPembayaran = idPembayaran;
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

	public LocalDateTime getTglPembayaran() {
		return tglPembayaran;
	}

	public void setTglPembayaran(LocalDateTime tglPembayaran) {
		this.tglPembayaran = tglPembayaran;
	}
	
	
}
