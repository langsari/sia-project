package com.sia.main.web;

import com.sia.main.domain.Modul;
import com.sia.main.plugin.modul.Accessable;

public class AdministratorModule extends Modul implements Accessable {

	private String url;
	
	@Override
	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String getUrl() {
		return this.url;
	}

}
