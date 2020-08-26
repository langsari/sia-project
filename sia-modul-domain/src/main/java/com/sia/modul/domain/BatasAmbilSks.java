package com.sia.modul.domain;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="batas_ambil_sks")
public class BatasAmbilSks {

	@Id
	@Column(name="id_batas_ambil_sks")
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@org.hibernate.annotations.Type(type="pg-uuid")
	private UUID idBatasAmbilSks;
	
	@NotNull(message="Batas bawah IPS tidak boleh kosong")		
    @Min(value=0,message="Minimal batas bawah IPS 0")
	@Column(name="batas_bawah_ips")
	private Double batasBawahIps;

    @NotNull(message="Batas pengambilan SKS tidak boleh kosong")
    @Min(value=1,message="Minimal pengambilan SKS 1")
	@Column(name="batas_pengambilan_sks")
	private Integer batasPengambilanSks;

	public UUID getIdBatasAmbilSks() {
		return idBatasAmbilSks;
	}

	public void setIdBatasAmbilSks(UUID idBatasAmbilSks) {
		this.idBatasAmbilSks = idBatasAmbilSks;
	}

	public Double getBatasBawahIps() {
		return batasBawahIps;
	}

	public void setBatasBawahIps(Double batasBawahIps) {
		this.batasBawahIps = batasBawahIps;
	}

	public Integer getBatasPengambilanSks() {
		return batasPengambilanSks;
	}

	public void setBatasPengambilanSks(Integer batasPengambilanSks) {
		this.batasPengambilanSks = batasPengambilanSks;
	}
    
    
}
