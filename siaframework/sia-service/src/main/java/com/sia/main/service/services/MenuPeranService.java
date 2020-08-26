package com.sia.main.service.services;

import java.util.List;
import java.util.UUID;

import com.sia.main.domain.MenuPeran;
import com.sia.main.plugin.common.Response;

public interface MenuPeranService {

	public Response insertInto(MenuPeran menuPeran);

	public Response update(MenuPeran menuPeran);

	public Response delete(MenuPeran menuPeran);

	public List<MenuPeran> getAll();

	public MenuPeran getById(UUID idMenuPeran);
	
	public List<MenuPeran> getByParam(String queryParam);
	
}
