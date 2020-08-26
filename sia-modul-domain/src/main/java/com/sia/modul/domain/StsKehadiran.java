package com.sia.modul.domain;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="sts_kehadiran")
public class StsKehadiran {

	@Id
	@Column(name="id_sts_kehadiran")
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@org.hibernate.annotations.Type(type="pg-uuid")
	private UUID idStsKehadiran;
	
    @NotEmpty(message="Kode Status kehadiran tidak boleh kosong")
	@Column(name="kode_sts_kehadiran")
	private String kodeStsKehadiran;

    @NotEmpty(message="Nama Status kehadiran tidak boleh kosong")
	@Column(name="nm_sts_kehadiran")
	private String nmStsKehadiran;
    
    @NotNull(message="Terhitung Absen tidak boleh kosong")
	@Column(name="a_absen")
	private Boolean aAbsen;

    @NotNull(message="Status hapus tidak boleh kosong")
	@Column(name="a_kehadiran_terhapus")
	private boolean aKehadiranTerhapus;
	
    @NotNull(message="Status Kehadiran awal tidak boleh kosong")
	@Column(name="a_kehadiran_awal")
    private boolean aKehadiranAwal;
    
	public StsKehadiran() {
		// TODO Auto-generated constructor stub
	}

	public UUID getIdStsKehadiran() {
		return idStsKehadiran;
	}

	public void setIdStsKehadiran(UUID idStsKehadiran) {
		this.idStsKehadiran = idStsKehadiran;
	}

	public String getKodeStsKehadiran() {
		return kodeStsKehadiran;
	}

	public void setKodeStsKehadiran(String kodeStsKehadiran) {
		this.kodeStsKehadiran = kodeStsKehadiran==null?null:kodeStsKehadiran.trim();
	}

	public String getNmStsKehadiran() {
		return nmStsKehadiran;
	}

	public void setNmStsKehadiran(String nmStsKehadiran) {
		this.nmStsKehadiran = nmStsKehadiran==null?null:nmStsKehadiran.trim();
	}

	public Boolean getaAbsen() {
		return aAbsen;
	}

	public void setaAbsen(Boolean aAbsen) {
		this.aAbsen = aAbsen;
	}

	public boolean isaKehadiranTerhapus() {
		return aKehadiranTerhapus;
	}

	public void setaKehadiranTerhapus(boolean aKehadiranTerhapus) {
		this.aKehadiranTerhapus = aKehadiranTerhapus;
	}

	public boolean isaKehadiranAwal() {
		return aKehadiranAwal;
	}

	public void setaKehadiranAwal(boolean aKehadiranAwal) {
		this.aKehadiranAwal = aKehadiranAwal;
	}
	
}
