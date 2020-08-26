package com.AIS.Modul.MataKuliah.Service;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.Kurikulum;
import com.sia.modul.domain.MK;

public interface MKService {

	public Datatable getdatatable(String sEcho, int iDisplayLength, int iDisplayStart, int iSortCol_0, String sSortDir_0, String sSearch, String filter);

	public List<MK> get();

	public List<MK> get(String where);

	public List<MK> get(String where, String order);

	public List<MK> get(String where, String order, int limit, int offset);

	public String save(MK mk);

	public MK findById(UUID idMK);

	public String delete(UUID uuid);

	public List<MK> findAll();
	
	public MK findByIdString(String idMK);

}
