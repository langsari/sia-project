package com.AIS.Modul.MataKuliah.Service;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.Pustaka;

public interface PustakaService {

	public Datatable getdatatable(String sEcho, int iDisplayLength,
			int iDisplayStart, int iSortCol_0, String sSortDir_0,
			String sSearch, String filter);

	public String save(Pustaka pustaka);

	public Pustaka findById(UUID idPustaka);

	public String delete(UUID uuid);

	public List<Pustaka> findAll();

}
