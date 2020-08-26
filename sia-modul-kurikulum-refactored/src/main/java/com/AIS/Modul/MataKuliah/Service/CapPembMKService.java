package com.AIS.Modul.MataKuliah.Service;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.CapPemb;
import com.sia.modul.domain.CapPembMK;

public interface CapPembMKService {

	public Datatable getdatatable(String sEcho, int iDisplayLength, int iDisplayStart,
			int iSortCol_0, String sSortDir_0, String sSearch, String filter);

	public String save(CapPembMK capPembMK);

	public CapPembMK findById(UUID idCapPembMK);

	public List<CapPemb> findParent(CapPembMK capPembMK);

	public String delete(UUID uuid);

	public Datatable getdatatable(String sEcho, int iDisplayLength,
			int iDisplayStart, int iSortCol_0, String sSortDir_0, String sSearch);

	public List<CapPembMK> findAll();

	public List<CapPembMK> findByMK(UUID idMK);
 
}
