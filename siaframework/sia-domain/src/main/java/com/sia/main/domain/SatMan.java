package com.sia.main.domain;

import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@Entity
@Table(name="sat_man")
public class SatMan{
	
	@Id
	@Column(name="id_sat_man")
	@GeneratedValue(generator="uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Type(type="pg-uuid")
	private UUID idSatMan; 
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sat_id_sat_man", nullable = true)
	private SatMan satManInduk;
	
	@Column(name="nm_sat_man", nullable = false)
	private String nmSatMan;
	
	@Column(name="a_sat_man_aktif", nullable = false)
	private boolean aSatManAktif;
	
	@Column(name="is_sat_man_has_kurikulum", nullable = false)
	private boolean satManHasKurikulum;
	
	@Column(name="a_sat_man_prodi", nullable = false)
	private boolean aSatManProdi;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "satManInduk")
	private List<SatMan> satManBawahan;
	
	public SatMan() {
		
	}

	public SatMan(UUID idSatMan, SatMan satManInduk, String nmSatMan,
			boolean aSatManAktif, boolean satManHasKurikulum,
			boolean aSatManProdi, List<SatMan> satManBawahan) {
		super();
		this.idSatMan = idSatMan;
		this.satManInduk = satManInduk;
		this.nmSatMan = nmSatMan;
		this.aSatManAktif = aSatManAktif;
		this.satManHasKurikulum = satManHasKurikulum;
		this.aSatManProdi = aSatManProdi;
		this.satManBawahan = satManBawahan;
	}

	public UUID getIdSatMan() {
		return idSatMan;
	}

	public void setIdSatMan(UUID idSatMan) {
		this.idSatMan = idSatMan;
	}

	public SatMan getSatManInduk() {
		return satManInduk;
	}

	public void setSatManInduk(SatMan satManInduk) {
		this.satManInduk = satManInduk;
	}

	public String getNmSatMan() {
		return nmSatMan;
	}

	public void setNmSatMan(String nmSatMan) {
		this.nmSatMan = nmSatMan;
	}

	public boolean isaSatManAktif() {
		return aSatManAktif;
	}

	public void setaSatManAktif(boolean aSatManAktif) {
		this.aSatManAktif = aSatManAktif;
	}

	public boolean isSatManHasKurikulum() {
		return satManHasKurikulum;
	}

	public void setSatManHasKurikulum(boolean satManHasKurikulum) {
		this.satManHasKurikulum = satManHasKurikulum;
	}

	public boolean isaSatManProdi() {
		return aSatManProdi;
	}

	public void setaSatManProdi(boolean aSatManProdi) {
		this.aSatManProdi = aSatManProdi;
	}

	public List<SatMan> getSatManBawahan() {
		return satManBawahan;
	}

	public void setSatManBawahan(List<SatMan> satManBawahan) {
		this.satManBawahan = satManBawahan;
	}
	
}