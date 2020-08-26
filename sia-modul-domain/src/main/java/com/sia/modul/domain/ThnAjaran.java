package com.sia.modul.domain;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="thn_ajaran")
public class ThnAjaran {
	@Id
	@Column(name="id_thn_ajaran")
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@org.hibernate.annotations.Type(type="pg-uuid")
	private UUID idThnAjaran;
	
    @NotNull(message="Tahun Ajaran tidak boleh kosong")
	@Column(name="thn_thn_ajaran")
	private Integer thnThnAjaran;

    @NotNull(message="Minimal Kehadiran Peserta Didik tidak boleh kosong")
    @Min(value=0,message="Nilai minimal untuk Minimal Kehadiran Peserta Didik adalah 0")
    @Max(value=100,message="Nilai maksimal untuk Minimal Kehadiran Peserta Didik adalah 100")
	@Column(name="persen_hadir_minim_pd")
	private Float persenHadirMinimPd;

    @NotNull(message="Minimal Pertemuan Pembelajaran tidak boleh kosong")
    @Min(value = 0,message = "Nilai minimal untuk Minimal Pertemuan Pembelajaran adalah 0")
    @Max(value = 100,message="Nilai maksimal untuk Minimal Pertemuan Pembelajaran adalah 100")
	@Column(name="persen_minim_pertemuan")
	private Float persenMinimPertemuan;
	
	@Column(name="a_thn_ajaran_terhapus")
	private boolean aThnAjaranTerhapus;
	
	public ThnAjaran() {
		// TODO Auto-generated constructor stub
	}

	public UUID getIdThnAjaran() {
		return idThnAjaran;
	}

	public void setIdThnAjaran(UUID idThnAjaran) {
		this.idThnAjaran = idThnAjaran;
	}

	public Integer getThnThnAjaran() {
		return thnThnAjaran;
	}

	public void setThnThnAjaran(Integer thnThnAjaran) {
		this.thnThnAjaran = thnThnAjaran;
	}

	public Float getPersenHadirMinimPd() {
		return persenHadirMinimPd;
	}

	public void setPersenHadirMinimPd(Float persenHadirMinimPd) {
		this.persenHadirMinimPd = persenHadirMinimPd;
	}

	public Float getPersenMinimPertemuan() {
		return persenMinimPertemuan;
	}

	public void setPersenMinimPertemuan(Float persenMinimPertemuan) {
		this.persenMinimPertemuan = persenMinimPertemuan;
	}

	public boolean isaThnAjaranTerhapus() {
		return aThnAjaranTerhapus;
	}

	public void setaThnAjaranTerhapus(boolean aThnAjaranTerhapus) {
		this.aThnAjaranTerhapus = aThnAjaranTerhapus;
	}

}
