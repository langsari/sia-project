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
@Table(name="presensi_pd")
public class PresensiPd {
	@Id
	@Column(name="id_presensi_pd")
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@org.hibernate.annotations.Type(type="pg-uuid")
	private UUID idPresensiPd;
		
	@ManyToOne(fetch = FetchType.EAGER, targetEntity=StsKehadiran.class)
	@JoinColumn(name="id_sts_kehadiran")
	private StsKehadiran stsKehadiran;
	
	@ManyToOne(fetch = FetchType.EAGER, targetEntity=Pd.class)
	@JoinColumn(name="id_pd")
	private Pd pd;
	
	@ManyToOne(fetch = FetchType.EAGER, targetEntity=PertemuanPembelajaran.class)
	@JoinColumn(name="id_pertemuan_pembelajaran")
	private PertemuanPembelajaran pertemuanPembelajaran;

	public UUID getIdPresensiPd() {
		return idPresensiPd;
	}

	public void setIdPresensiPd(UUID idPresensiPd) {
		this.idPresensiPd = idPresensiPd;
	}

	public StsKehadiran getStsKehadiran() {
		return stsKehadiran;
	}

	public void setStsKehadiran(StsKehadiran stsKehadiran) {
		this.stsKehadiran = stsKehadiran;
	}

	public Pd getPd() {
		return pd;
	}

	public void setPd(Pd pd) {
		this.pd = pd;
	}

	public PertemuanPembelajaran getPertemuanPembelajaran() {
		return pertemuanPembelajaran;
	}

	public void setPertemuanPembelajaran(PertemuanPembelajaran pertemuanPembelajaran) {
		this.pertemuanPembelajaran = pertemuanPembelajaran;
	}
	
	
}
