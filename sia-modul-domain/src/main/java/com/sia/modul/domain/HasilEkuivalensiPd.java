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
@Table(name="hasil_ekuivalensi_pd")
public class HasilEkuivalensiPd {

	@Id
	@Column(name="id_hasil_ekuivalensi_pd")
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@org.hibernate.annotations.Type(type="pg-uuid")
	private UUID idHasilEkuivalensiPd;
	
	@NotNull(message="Status Ambil Matakuliah tidak boleh kosong")
	@Column(name="a_ambil_mk")
	private boolean aAmbilMK;
	
	@ManyToOne(fetch = FetchType.EAGER, targetEntity=Pd.class)
	@JoinColumn(name = "id_pd")
    private Pd pd;
	
	@ManyToOne(fetch = FetchType.EAGER, targetEntity=MK.class)
	@JoinColumn(name = "id_mk")
    private MK mk;
	
	public HasilEkuivalensiPd() {
		// TODO Auto-generated constructor stub
	}

	public UUID getIdHasilEkuivalensiPd() {
		return idHasilEkuivalensiPd;
	}

	public void setIdHasilEkuivalensiPd(UUID idHasilEkuivalensiPd) {
		this.idHasilEkuivalensiPd = idHasilEkuivalensiPd;
	}

	public boolean isaAmbilMK() {
		return aAmbilMK;
	}

	public void setaAmbilMK(boolean aAmbilMK) {
		this.aAmbilMK = aAmbilMK;
	}

	public Pd getPd() {
		return pd;
	}

	public void setPd(Pd pd) {
		this.pd = pd;
	}

	public MK getMk() {
		return mk;
	}

	public void setMk(MK mk) {
		this.mk = mk;
	}
	
}
