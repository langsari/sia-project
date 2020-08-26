package com.sia.modul.domain;

import java.util.Date;
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
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="rombel")
public class Rombel {

	@Id
	@Column(name="id_rombel")
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@org.hibernate.annotations.Type(type="pg-uuid")
	private UUID idRombel;
	
    @NotEmpty(message="Nama Semester tidak boleh kosong")
	@Column(name="nm_rombel")
	private String nmRombel;

    @NotNull(message="Status hapus tidak boleh kosong")
	@Column(name="a_rombel_terhapus")
	private boolean aRombelTerhapus;
    
	@Column(name="tgl_buat_rombel", insertable = false, updatable = false)
	private Date tglBuatRombel;

	@ManyToOne(fetch = FetchType.EAGER, targetEntity=SatMan.class)
	@JoinColumn(name = "id_sat_man")
    private SatMan satMan;
	
	public Rombel() {
		// TODO Auto-generated constructor stub
	}

	public UUID getIdRombel() {
		return idRombel;
	}

	public void setIdRombel(UUID idRombel) {
		this.idRombel = idRombel;
	}

	public String getNmRombel() {
		return nmRombel;
	}

	public void setNmRombel(String nmRombel) {
		this.nmRombel = nmRombel==null?null:nmRombel.trim();
	}

	public void setaRombelTerhapus(boolean aRombelTerhapus) {
		this.aRombelTerhapus = aRombelTerhapus;
	}

	public void setTglBuatRombel(Date tglBuatRombel) {
		this.tglBuatRombel = tglBuatRombel;
	}

	public SatMan getSatMan() {
		return satMan;
	}

	public void setSatMan(SatMan satMan) {
		this.satMan = satMan;
	}

	public boolean isaRombelTerhapus() {
		return aRombelTerhapus;
	}

	public Date getTglBuatRombel() {
		return tglBuatRombel;
	}
	
}
