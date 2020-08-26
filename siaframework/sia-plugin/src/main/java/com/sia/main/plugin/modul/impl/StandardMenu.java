package com.sia.main.plugin.modul.impl;

import com.sia.main.plugin.modul.Menu;

public class StandardMenu implements Menu {

	private String menuName;
	
	private String url;
	
	public StandardMenu(String menuName, String url) {
		this.menuName = menuName;
		this.url = url;
	}
	
	public StandardMenu() {
		
	}
	
	@Override
	public void setUrl(String homeUrl) {
		this.url = homeUrl;
	}
	
	@Override
	public String getUrl() {
		return this.url;
	}

	@Override
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	@Override
	public String getMenuName() {
		return this.menuName;
	}

}
