package com.AIS.Modul.MataKuliah.Service;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.MetodePemb;

public interface MetodePembService {

	public Datatable getdatatable(String sEcho, int iDisplayLength, int iDisplayStart,
			int iSortCol_0, String sSortDir_0, String sSearch, String filter);

	public String save(MetodePemb metodePemb);

	public MetodePemb findById(UUID idRumpunMK);

	public String delete(UUID uuid);

	public List<MetodePemb> findAll();

}
