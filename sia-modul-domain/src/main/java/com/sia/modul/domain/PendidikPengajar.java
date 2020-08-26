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

@Entity
@Table(name="pendidik_pengajar")
public class PendidikPengajar {

	@Id
	@Column(name="id_pendidik_pengajar")
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@org.hibernate.annotations.Type(type="pg-uuid")
	private UUID idPendidikPengajar;
	
	@Column(name="nilai_ipd")
	private double nilaiIpd;

    @NotNull(message="Status hapus Pendidik/Tenaga kependidikan tidak boleh kosong")
	@Column(name="a_pendidik_pengajar_terhapus")
	private boolean aPendidikPengajarTerhapus;
    
    @NotNull
    @Column(name="a_pendidik_pengajar_ketua")
    private boolean aPendidikPengajarKetua;

	@ManyToOne(fetch = FetchType.EAGER, targetEntity=Ptk.class)
	@JoinColumn(name = "id_ptk")
    private Ptk ptk;

	@ManyToOne(fetch = FetchType.EAGER, targetEntity=Pemb.class)
	@JoinColumn(name = "id_pemb")
    private Pemb pemb;

	public UUID getIdPendidikPengajar() {
		return idPendidikPengajar;
	}

	public void setIdPendidikPengajar(UUID idPendidikPengajar) {
		this.idPendidikPengajar = idPendidikPengajar;
	}

	public double getNilaiIpd() {
		return nilaiIpd;
	}

	public void setNilaiIpd(double nilaiIpd) {
		this.nilaiIpd = nilaiIpd;
	}

	public boolean isaPendidikPengajarTerhapus() {
		return aPendidikPengajarTerhapus;
	}

	public void setaPendidikPengajarTerhapus(boolean aPendidikPengajarTerhapus) {
		this.aPendidikPengajarTerhapus = aPendidikPengajarTerhapus;
	}

	public boolean isaPendidikPengajarKetua() {
		return aPendidikPengajarKetua;
	}

	public void setaPendidikPengajarKetua(boolean aPendidikPengajarKetua) {
		this.aPendidikPengajarKetua = aPendidikPengajarKetua;
	}

	public Ptk getPtk() {
		return ptk;
	}

	public void setPtk(Ptk ptk) {
		this.ptk = ptk;
	}

	public Pemb getPemb() {
		return pemb;
	}

	public void setPemb(Pemb pemb) {
		this.pemb = pemb;
	}
	
	
}
