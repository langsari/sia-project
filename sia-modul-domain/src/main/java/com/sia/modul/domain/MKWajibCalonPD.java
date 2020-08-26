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
@Table(name="mkwajib_calon_pd")
public class MKWajibCalonPD {
	@Id
	@Column(name="id_mkwajib_calon_pd")
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@org.hibernate.annotations.Type(type="pg-uuid")
	private UUID idMKWajibCalonPD;
	
	@ManyToOne(fetch = FetchType.EAGER, targetEntity=MK.class)
	@JoinColumn(name = "id_mk")
	private MK mk;
	
	@ManyToOne(fetch = FetchType.EAGER, targetEntity=CalonPD.class)
	@JoinColumn(name = "id_calon_pd")
	private CalonPD calonPD;

	@NotNull(message="Status Ambil tidak boleh kosong")
	@Column(name = "a_status_ambil")
	private boolean aStatusAmbil;
	
	public boolean isaStatusAmbil() {
		return aStatusAmbil;
	}

	public void setaStatusAmbil(boolean aStatusAmbil) {
		this.aStatusAmbil = aStatusAmbil;
	}

	public UUID getIdMKWajibCalonPD() {
		return idMKWajibCalonPD;
	}

	public void setIdMKWajibCalonPD(UUID idMKWajibCalonPD) {
		this.idMKWajibCalonPD = idMKWajibCalonPD;
	}

	public MK getMk() {
		return mk;
	}

	public void setMk(MK mk) {
		this.mk = mk;
	}

	public CalonPD getCalonPD() {
		return calonPD;
	}

	public void setCalonPD(CalonPD calonPD) {
		this.calonPD = calonPD;
	}
}
