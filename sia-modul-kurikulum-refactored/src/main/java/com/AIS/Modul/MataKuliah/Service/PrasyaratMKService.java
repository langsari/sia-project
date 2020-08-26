package com.AIS.Modul.MataKuliah.Service;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.PrasyaratMK;
import com.sia.modul.domain.RumpunMK;

public interface PrasyaratMKService {

	Datatable getdatatable(String sEcho, int iDisplayLength, int iDisplayStart,
			int iSortCol_0, String sSortDir_0, String sSearch, String filter);

	List<PrasyaratMK> get();

	List<PrasyaratMK> get(String where);

	List<PrasyaratMK> get(String where, String order);

	List<PrasyaratMK> get(String where, String order, int limit, int offset);

	String save(PrasyaratMK prasyaratMK);

	PrasyaratMK findById(UUID idPrasyaratMK);

	String delete(UUID idPrasyaratMK);

	List<PrasyaratMK> findAll();

	List<PrasyaratMK> findParentMK(UUID idMK);

}
