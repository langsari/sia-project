package com.sia.modul.domain;

import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="pemb")
public class Pemb {

	@Id
	@Column(name="id_pemb")
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@org.hibernate.annotations.Type(type="pg-uuid")
	private UUID idPemb;

	@ManyToOne(fetch = FetchType.EAGER, targetEntity=TglSmt.class)
	@JoinColumn(name = "id_tgl_smt")
    private TglSmt tglSmt;

	@ManyToOne(fetch = FetchType.EAGER, targetEntity=MK.class)
	@JoinColumn(name = "id_mk")
    private MK mk;
	
	@NotEmpty(message="Nama pembelajaran tidak boleh kosong")
	@Column(name="nm_pemb")
	private String nmPemb;
	
	public boolean isaPembTerhapus() {
		return aPembTerhapus;
	}

	public void setaPembTerhapus(boolean aPembTerhapus) {
		this.aPembTerhapus = aPembTerhapus;
	}

	@NotNull(message="Status hapus tidak boleh kosong")
	@Column(name="a_pemb_terhapus")
	private boolean aPembTerhapus;

	@NotNull(message="Nama pembelajaran tidak boleh kosong")
	@Column(name="temu_dalam_seminggu")
	@Min(value=1,message="Minimal 1 kali pertemuan dalam seminggu")
	private Integer temuDalamSeminggu;
	
	@NotNull(message="Kuota tidak boleh kosong")
	@Column(name="kuota_pemb")
	@Min(value=0,message="Kuota tidak boleh minus")
	private Integer kuota;
	
	@Transient
	private List<UUID> listSatManList;
	
	@Transient
	private Long jumlahPertemuan;

	public UUID getIdPemb() {
		return idPemb;
	}

	public void setIdPemb(UUID idPemb) {
		this.idPemb = idPemb;
	}

	public TglSmt getTglSmt() {
		return tglSmt;
	}

	public void setTglSmt(TglSmt tglSmt) {
		this.tglSmt = tglSmt;
	}

	public MK getMk() {
		return mk;
	}

	public void setMk(MK mk) {
		this.mk = mk;
	}

	public String getNmPemb() {
		return nmPemb;
	}

	public void setNmPemb(String nmPemb) {
		this.nmPemb = nmPemb.trim();
	}

	public Integer getTemuDalamSeminggu() {
		return temuDalamSeminggu;
	}

	public void setTemuDalamSeminggu(Integer temuDalamSeminggu) {
		this.temuDalamSeminggu = temuDalamSeminggu;
	}

	public Integer getKuota() {
		return kuota;
	}

	public void setKuota(Integer kuota) {
		this.kuota = kuota;
	}

	public List<UUID> getListSatManList() {
		return listSatManList;
	}

	public void setListSatManList(List<UUID> listSatManList) {
		this.listSatManList = listSatManList;
	}

	public Long getJumlahPertemuan() {
		return jumlahPertemuan;
	}

	public void setJumlahPertemuan(Long jumlahPertemuan) {
		this.jumlahPertemuan = jumlahPertemuan;
	}
	
}
