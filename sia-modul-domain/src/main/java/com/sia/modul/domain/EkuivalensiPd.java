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
import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.joda.deser.LocalDateTimeDeserializer;

@Entity
@Table(name="ekuivalensi_pd")
public class EkuivalensiPd {

	@Id
	@Column(name="id_ekuivalensi_pd")
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@org.hibernate.annotations.Type(type="pg-uuid")
	private UUID idEkuivalensiPd;
	
	@Column(name="tgl_pembuatan")
	@JsonDeserialize(using=LocalDateTimeDeserializer.class)
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime tglPembuatan;
	
	@NotNull(message="Status Ekuivalensi tidak boleh kosong")
	@Column(name="a_status_ekuivalensi")
	private boolean aEkuivalensi;
	
	@ManyToOne(fetch = FetchType.EAGER, targetEntity=Pd.class)
	@JoinColumn(name = "id_pd")
    private Pd pd;
	
	@ManyToOne(fetch = FetchType.EAGER, targetEntity=Ptk.class)
	@JoinColumn(name = "id_ptk")
    private Ptk ptk;
	
	@ManyToOne(fetch = FetchType.EAGER, targetEntity=Kurikulum.class)
	@JoinColumn(name = "id_kurikulum_lama")
    private Kurikulum kurikulumLama;
	
	@ManyToOne(fetch = FetchType.EAGER, targetEntity=Kurikulum.class)
	@JoinColumn(name = "id_kurikulum_baru")
    private Kurikulum kurikulumBaru;
	
	public EkuivalensiPd() {
		// TODO Auto-generated constructor stub
	}

	public boolean isaEkuivalensi() {
		return aEkuivalensi;
	}

	public void setaEkuivalensi(boolean aEkuivalensi) {
		this.aEkuivalensi = aEkuivalensi;
	}

	public UUID getIdEkuivalensiPd() {
		return idEkuivalensiPd;
	}

	public void setIdEkuivalensiPd(UUID idEkuivalensiPd) {
		this.idEkuivalensiPd = idEkuivalensiPd;
	}

	public LocalDateTime getTglPembuatan() {
		return tglPembuatan;
	}

	public void setTglPembuatan(LocalDateTime tglPembuatan) {
		this.tglPembuatan = tglPembuatan;
	}

	public Pd getPd() {
		return pd;
	}

	public void setPd(Pd pd) {
		this.pd = pd;
	}

	public Ptk getPtk() {
		return ptk;
	}

	public void setPtk(Ptk ptk) {
		this.ptk = ptk;
	}

	public Kurikulum getKurikulumLama() {
		return kurikulumLama;
	}

	public void setKurikulumLama(Kurikulum kurikulumLama) {
		this.kurikulumLama = kurikulumLama;
	}

	public Kurikulum getKurikulumBaru() {
		return kurikulumBaru;
	}

	public void setKurikulumBaru(Kurikulum kurikulumBaru) {
		this.kurikulumBaru = kurikulumBaru;
	}

}
