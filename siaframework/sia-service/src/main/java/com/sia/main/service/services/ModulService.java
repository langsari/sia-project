package com.sia.main.service.services;

import java.util.List;
import java.util.UUID;

import com.sia.main.domain.Modul;
import com.sia.main.plugin.common.Response;

public interface ModulService {

	public Response installModule(Object modulFile, Object hostBundle);
	
	public Response uninstallModule(Modul modul);

	public Modul update(Modul modul);

	public List<Modul> getAll();

	public Modul getById(UUID idModul);
	
	public List<Modul> getByParam(String queryParam);
	
}
