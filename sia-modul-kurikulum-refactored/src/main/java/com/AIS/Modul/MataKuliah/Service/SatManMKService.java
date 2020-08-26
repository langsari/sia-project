package com.AIS.Modul.MataKuliah.Service;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.MK;
import com.sia.modul.domain.SatMan;
import com.sia.modul.domain.SatManMK;

public interface SatManMKService {

	public String save(SatManMK satManMK);

	public SatManMK findById(UUID idSatManMK);

	public String delete(UUID uuid);

	public Datatable getdatatable(String sEcho, int iDisplayLength,
			int iDisplayStart, int iSortCol_0, String sSortDir_0,
			String sSearch, String filter);

	public List<SatManMK> get();

	public List<SatManMK> get(String where);

	public List<SatManMK> get(String where, String order);

	public List<SatManMK> get(String where, String order, int limit, int offset);

	public List<SatManMK> findByMK(UUID idMK);

	public List<SatManMK> findAll();

}
