package com.sia.main.domain;

import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "tipe_pengguna")
public class TipePengguna {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Type(type = "pg-uuid")
	@Column(name = "id_tipe")
	private UUID idTipe;
	
	@Column(name = "nama_tipe", unique = true, nullable = false)
	private String namaTipe;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tipePengguna")
	private List<Pengguna> users;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tipePengguna")
	private List<Peran> daftarPeran;
	
	public TipePengguna() {
		
	}

	public TipePengguna(UUID idTipe, String namaTipe, List<Pengguna> users,
			List<Peran> daftarPeran) {
		super();
		this.idTipe = idTipe;
		this.namaTipe = namaTipe;
		this.users = users;
		this.daftarPeran = daftarPeran;
	}

	public UUID getIdTipe() {
		return idTipe;
	}

	public void setIdTipe(UUID idTipe) {
		this.idTipe = idTipe;
	}

	public String getNamaTipe() {
		return namaTipe;
	}

	public void setNamaTipe(String namaTipe) {
		this.namaTipe = namaTipe;
	}

	public List<Pengguna> getUsers() {
		return users;
	}

	public void setUsers(List<Pengguna> users) {
		this.users = users;
	}

	public List<Peran> getDaftarPeran() {
		return daftarPeran;
	}

	public void setDaftarPeran(List<Peran> daftarPeran) {
		this.daftarPeran = daftarPeran;
	}
		
}
