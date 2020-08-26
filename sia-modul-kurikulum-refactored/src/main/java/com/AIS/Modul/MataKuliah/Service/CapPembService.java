package com.AIS.Modul.MataKuliah.Service;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.*;

public interface CapPembService { 
	public List<CapPemb> findBySatMan(UUID idSatMan);

	public Datatable getdatatable(String sEcho, int iDisplayLength,
			int iDisplayStart, int iSortCol_0, String sSortDir_0,
			String sSearch, String filter);

	public List<CapPemb> get(String where, String order, int limit, int offset);

	public List<CapPemb> get(String where, String order);

	public List<CapPemb> get(String where);

	public List<CapPemb> get();

	public String save(CapPemb capPemb);

	public CapPemb findById(UUID idCapPemb);

	public String delete(UUID idCapPemb);

	public List<CapPemb> findAll();

	public Datatable getdatatable1(String sEcho, int iDisplayLength,
			int iDisplayStart, int iSortCol_0, String sSortDir_0,
			String sSearch);

	public List<CapPemb> findByKurikulum(UUID idKurikulum);

	public List<CapPemb> findByParent(UUID idCapPemb);
}
