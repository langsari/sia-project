package com.sia.main.service.services;

import java.util.List;
import java.util.UUID;

import com.sia.main.domain.Menu;

public interface MenuService {

	public Menu insertInto(Menu menu);

	public Menu update(Menu menu);

	public Menu delete(Menu menu);

	public List<Menu> getAll();

	public Menu getById(UUID idMenu);
	
	public List<Menu> getByParam(String queryParam);
	
}
