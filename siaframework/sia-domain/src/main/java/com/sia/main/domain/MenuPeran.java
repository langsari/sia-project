package com.sia.main.domain;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "menu_peran")
public class MenuPeran {
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Type(type = "pg-uuid")
	@Column(name = "id_menu_peran")
	private UUID idMenuPeran;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_peran", nullable = false)
	private Peran peran;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_menu", nullable = false)
	private Menu menu;

	public MenuPeran(UUID idMenuPeran, Peran peran, Menu menu) {
		this.peran = peran;
		this.menu = menu;
		this.idMenuPeran = idMenuPeran;
	}

	public MenuPeran() {
	}

	public UUID getIdMenuPeran() {
		return idMenuPeran;
	}

	public void setIdMenuPeran(UUID idMenuPeran) {
		this.idMenuPeran = idMenuPeran;
	}

	public Peran getPeran() {
		return peran;
	}

	public void setPeran(Peran peran) {
		this.peran = peran;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

}
