package com.sia.main.service.services.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.sia.main.data.dao.MenuDAO;
import com.sia.main.domain.Menu;
import com.sia.main.domain.MenuPeran;
import com.sia.main.service.services.MenuPeranService;
import com.sia.main.service.services.MenuService;

@Service
public class MenuServiceImpl implements MenuService  {

	private MenuDAO menuDAO;
	
	private MenuPeranService menuPeranService;
	
	public void setMenuDAO(MenuDAO menuDAO) {
		this.menuDAO = menuDAO;
	}

	public void setMenuPeranService(MenuPeranService menuPeranService) {
		this.menuPeranService = menuPeranService;
	}

	@Override
	public Menu insertInto(Menu menu) {
		List<Menu> menusFound = this.getByParam("where namaMenu = '" + menu.getNamaMenu() + "' and modul.idModul = '" + menu.getModul().getIdModul() + "'"); 
		if(menusFound != null && menusFound.size() > 0) {
			Menu menuFound = menusFound.get(0);
			menuFound.setModul(menu.getModul());
			menuFound.setNamaMenu(menu.getNamaMenu());
			menuFound.setUrlMenu(menu.getUrlMenu());
			this.update(menuFound);
		} else {
			this.menuDAO.insert(menu);
		}
		return menu;
	}

	@Override
	public Menu update(Menu menu) {
		this.menuDAO.update(menu);
		return menu;
	}

	@Override
	public Menu delete(Menu menu) {
		for(MenuPeran menuPeran : menu.getDaftarMenuPeran()) {
			this.menuPeranService.delete(menuPeran);
		}
		this.menuDAO.delete(menu);
		return menu;
	}

	@Override
	public List<Menu> getAll() {
		return this.menuDAO.getAll();
	}

	@Override
	public Menu getById(UUID idMenu) {
		return this.menuDAO.getById(idMenu);
	}

	@Override
	public List<Menu> getByParam(String queryParam) {
		return this.menuDAO.getByParam(queryParam);
	}

}
