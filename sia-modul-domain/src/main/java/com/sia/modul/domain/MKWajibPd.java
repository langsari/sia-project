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
@Table(name="mkwajib_pd")
public class MKWajibPd {

	@Id
	@Column(name="id_mkwajib_pd")
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@org.hibernate.annotations.Type(type="pg-uuid")
	private UUID idMKWajibPd;
	
	@NotNull(message="Status Hapus Matakuliah tidak boleh kosong")
	@Column(name="a_mk_ambil")
	private boolean aMKAmbil;
	
	@ManyToOne(fetch = FetchType.EAGER, targetEntity=EkuivalensiPd.class)
	@JoinColumn(name = "id_ekuivalensi_pd")
    private EkuivalensiPd ekuivalensiPd;
	
	@ManyToOne(fetch = FetchType.EAGER, targetEntity=MK.class)
	@JoinColumn(name = "id_mk")
    private MK mk;
	
	public MKWajibPd() {
		// TODO Auto-generated constructor stub
	}

	public UUID getIdMKWajibPd() {
		return idMKWajibPd;
	}

	public void setIdMKWajibPd(UUID idMKWajibPd) {
		this.idMKWajibPd = idMKWajibPd;
	}

	public boolean isaMKAmbil() {
		return aMKAmbil;
	}

	public void setaMKAmbil(boolean aMKAmbil) {
		this.aMKAmbil = aMKAmbil;
	}

	public EkuivalensiPd getEkuivalensiPd() {
		return ekuivalensiPd;
	}

	public void setEkuivalensiPd(EkuivalensiPd ekuivalensiPd) {
		this.ekuivalensiPd = ekuivalensiPd;
	}

	public MK getMk() {
		return mk;
	}

	public void setMk(MK mk) {
		this.mk = mk;
	}
	
}
