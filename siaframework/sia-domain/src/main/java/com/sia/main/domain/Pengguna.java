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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "pengguna")
public class Pengguna {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Type(type = "pg-uuid")
	@Column(name = "id_pengguna")
	private UUID idPengguna;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_pd", nullable = true)
	private Pd pd;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_ptk", nullable = true)
	private Ptk ptk;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_sat_man", nullable = false)
	private SatMan satMan;
	
	@Column(name = "username", unique = true, nullable = false)
	private String username;
	
	@Column(name = "password", nullable = false)
	private String password;
	
	@Column(name = "a_pengguna_aktif", nullable = false)
	private boolean statusKeaktifan;
	
	@Column(name = "kode_reset_password", unique = true, nullable = true)
	private String kodeResetPassword;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_tipe", nullable = false)
	private TipePengguna tipePengguna;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pengguna")
	private List<PeranPengguna> peranPenggunaList;

	public Pengguna() {
		
	}
	
	public Pengguna(UUID idPengguna, Pd pd, Ptk ptk, SatMan satMan,
			String username, String password, boolean statusKeaktifan,
			String kodeResetPassword, List<PeranPengguna> peranPenggunaList,
			TipePengguna tipePengguna) {
		super();
		this.idPengguna = idPengguna;
		this.pd = pd;
		this.ptk = ptk;
		this.satMan = satMan;
		this.username = username;
		this.password = password;
		this.statusKeaktifan = statusKeaktifan;
		this.kodeResetPassword = kodeResetPassword;
		this.peranPenggunaList = peranPenggunaList;
		this.tipePengguna = tipePengguna;
	}

	public UUID getIdPengguna() {
		return idPengguna;
	}

	public void setIdPengguna(UUID idPengguna) {
		this.idPengguna = idPengguna;
	}

	public Pd getPd() {
		return pd;
	}

	public void setPd(Pd pd) {
		this.pd = pd;
	}

	public Ptk getPtk() {
		return ptk;
	}

	public void setPtk(Ptk ptk) {
		this.ptk = ptk;
	}

	public SatMan getSatMan() {
		return satMan;
	}

	public void setSatMan(SatMan satMan) {
		this.satMan = satMan;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isStatusKeaktifan() {
		return statusKeaktifan;
	}

	public void setStatusKeaktifan(boolean statusKeaktifan) {
		this.statusKeaktifan = statusKeaktifan;
	}

	public String getKodeResetPassword() {
		return kodeResetPassword;
	}

	public void setKodeResetPassword(String kodeResetPassword) {
		this.kodeResetPassword = kodeResetPassword;
	}

	public List<PeranPengguna> getPeranPenggunaList() {
		return peranPenggunaList;
	}

	public void setPeranPenggunaList(List<PeranPengguna> peranPenggunaList) {
		this.peranPenggunaList = peranPenggunaList;
	}

	public TipePengguna getTipePengguna() {
		return tipePengguna;
	}

	public void setTipePengguna(TipePengguna tipePengguna) {
		this.tipePengguna = tipePengguna;
	}

}
