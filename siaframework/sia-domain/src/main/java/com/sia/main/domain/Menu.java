package com.sia.main.domain;

import java.util.ArrayList;
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
@Table(name = "menu")
public class Menu {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Type(type = "pg-uuid")
	@Column(name = "id_menu")
	private UUID idMenu;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_modul", nullable = false)
	private Modul modul;
	
	@Column(name = "nama_menu", nullable = false)
	private String namaMenu;

	@Column(name = "url_menu", nullable = false)
	private String urlMenu;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "menu")
	private List<MenuPeran> daftarMenuPeran;

	public Menu() {
		this.daftarMenuPeran = new ArrayList<MenuPeran>();
	}

	public Menu(UUID idMenu, Modul modul, String namaMenu, String urlMenu,
			List<MenuPeran> daftarMenuPeran) {
		super();
		this.idMenu = idMenu;
		this.modul = modul;
		this.namaMenu = namaMenu;
		this.urlMenu = urlMenu;
		this.daftarMenuPeran = daftarMenuPeran;
	}

	public UUID getIdMenu() {
		return idMenu;
	}

	public void setIdMenu(UUID idMenu) {
		this.idMenu = idMenu;
	}

	public Modul getModul() {
		return modul;
	}

	public void setModul(Modul modul) {
		this.modul = modul;
	}

	public String getNamaMenu() {
		return namaMenu;
	}

	public void setNamaMenu(String namaMenu) {
		this.namaMenu = namaMenu;
	}

	public String getUrlMenu() {
		return urlMenu;
	}

	public void setUrlMenu(String urlMenu) {
		this.urlMenu = urlMenu;
	}

	public String getCompleteUrl() {
		String completeUrl = this.getModul().getUrlMapping().split("/\\*")[0] + this.getUrlMenu();
		return completeUrl;
	}
	
	public List<MenuPeran> getDaftarMenuPeran() {
		return daftarMenuPeran;
	}

	public void setDaftarMenuPeran(List<MenuPeran> daftarMenuPeran) {
		this.daftarMenuPeran = daftarMenuPeran;
	}
	
}
