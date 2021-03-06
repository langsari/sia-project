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
@Table(name="presensi_pengajar")
public class PresensiPengajar {
	@Id
	@Column(name="id_presensi_pengajar")
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@org.hibernate.annotations.Type(type="pg-uuid")
	private UUID idPresensiPengajar;
		
	@ManyToOne(fetch = FetchType.EAGER, targetEntity=StsKehadiran.class)
	@JoinColumn(name="id_sts_kehadiran")
	private StsKehadiran stsKehadiran;
	
	@ManyToOne(fetch = FetchType.EAGER, targetEntity=PendidikPengajar.class)
	@JoinColumn(name="id_pendidik_pengajar")
	private PendidikPengajar pendidikPengajar;
	
	@ManyToOne(fetch = FetchType.EAGER, targetEntity=PertemuanPembelajaran.class)
	@JoinColumn(name="id_pertemuan_pembelajaran")
	private PertemuanPembelajaran pertemuanPembelajaran;

	public StsKehadiran getStsKehadiran() {
		return stsKehadiran;
	}

	public void setStsKehadiran(StsKehadiran stsKehadiran) {
		this.stsKehadiran = stsKehadiran;
	}

	public PertemuanPembelajaran getPertemuanPembelajaran() {
		return pertemuanPembelajaran;
	}

	public void setPertemuanPembelajaran(PertemuanPembelajaran pertemuanPembelajaran) {
		this.pertemuanPembelajaran = pertemuanPembelajaran;
	}

	public UUID getIdPresensiPengajar() {
		return idPresensiPengajar;
	}

	public void setIdPresensiPengajar(UUID idPresensiPengajar) {
		this.idPresensiPengajar = idPresensiPengajar;
	}

	public PendidikPengajar getPendidikPengajar() {
		return pendidikPengajar;
	}

	public void setPendidikPengajar(PendidikPengajar pendidikPengajar) {
		this.pendidikPengajar = pendidikPengajar;
	}
	
}
