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
@Table(name="relasi_ekuivalensi")
public class RelasiEkuivalensi {

	@Id
	@GeneratedValue(generator="uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@org.hibernate.annotations.Type(type="pg-uuid")
	@Column(name="id_relasi_ekuivalensi")
	private UUID idRelasiEkuivalensi;

	@Column(name="relasi_mk_lama")
	private String relasiMKLama;
	
	@Column(name="detail_relasi_mk_lama")
	private String detailRelasiMKLama;
	
	@Column(name="relasi_mk_baru")
	private String relasiMKBaru;
	
	@Column(name="detail_relasi_mk_Baru")
	private String detailRelasiMKBaru;

	@ManyToOne(fetch = FetchType.EAGER, targetEntity = Kurikulum.class)
	@JoinColumn(name = "id_kurikulum_lama")
	private Kurikulum kurikulumLama;
	
	@ManyToOne(fetch = FetchType.EAGER, targetEntity = Kurikulum.class)
	@JoinColumn(name = "id_kurikulum_baru")
	private Kurikulum kurikulumBaru;
	
	@Column(name="id_kurikulum_lama", insertable=false, updatable=false)
	private UUID idKurikulumLama;
	
	@Column(name="id_kurikulum_baru", insertable=false, updatable=false)
	private UUID idKurikulumBaru;
	
	public UUID getIdKurikulumLama() {
		return idKurikulumLama;
	}

	public void setIdKurikulumLama(UUID idKurikulumLama) {
		this.idKurikulumLama = idKurikulumLama;
	}

	public UUID getIdKurikulumBaru() {
		return idKurikulumBaru;
	}

	public void setIdKurikulumBaru(UUID idKurikulumBaru) {
		this.idKurikulumBaru = idKurikulumBaru;
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

	public UUID getIdRelasiEkuivalensi() {
		return idRelasiEkuivalensi;
	}

	public void setIdRelasiEkuivalensi(UUID idRelasiEkuivalensi) {
		this.idRelasiEkuivalensi = idRelasiEkuivalensi;
	}

	public String getRelasiMKLama() {
		return relasiMKLama;
	}

	public void setRelasiMKLama(String relasiMKLama) {
		this.relasiMKLama = relasiMKLama;
	}

	public String getDetailRelasiMKLama() {
		return detailRelasiMKLama;
	}

	public void setDetailRelasiMKLama(String detailRelasiMKLama) {
		this.detailRelasiMKLama = detailRelasiMKLama;
	}

	public String getRelasiMKBaru() {
		return relasiMKBaru;
	}

	public void setRelasiMKBaru(String relasiMKBaru) {
		this.relasiMKBaru = relasiMKBaru;
	}

	public String getDetailRelasiMKBaru() {
		return detailRelasiMKBaru;
	}

	public void setDetailRelasiMKBaru(String detailRelasiMKBaru) {
		this.detailRelasiMKBaru = detailRelasiMKBaru;
	}
	
}
