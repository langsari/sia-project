package com.sia.main.plugin.modul;

import java.util.List;

public interface HasMenu {

	public void addMenu(Menu menu);
	
	public void setMenus(List<Menu> menus); 
	
	public List<Menu> getMenus();

}
