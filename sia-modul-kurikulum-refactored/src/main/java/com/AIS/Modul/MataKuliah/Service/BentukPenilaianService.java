package com.AIS.Modul.MataKuliah.Service;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.BentukPenilaian;

public interface BentukPenilaianService {

	public Datatable getdatatable(String sEcho, int iDisplayLength,
			int iDisplayStart, int iSortCol_0, String sSortDir_0,
			String sSearch, String filter);

	public String save(BentukPenilaian bentukPenilaian);

	public BentukPenilaian findById(UUID idBentuk);

	public String delete(UUID uuid);

	public List<BentukPenilaian> findAll(); 

}
