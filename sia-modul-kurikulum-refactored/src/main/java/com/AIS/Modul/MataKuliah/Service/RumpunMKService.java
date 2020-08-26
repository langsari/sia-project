package com.AIS.Modul.MataKuliah.Service;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.*;

public interface RumpunMKService {

	public Datatable getdatatable(String sEcho, int iDisplayLength,	int iDisplayStart, int iSortCol_0, String sSortDir_0, String sSearch, String filter);
	public List<RumpunMK> get(); 
	public List<RumpunMK> get(String where); 
	public List<RumpunMK> get(String where, String order); 
	public List<RumpunMK> get(String where, String order, int limit, int offset);
	public String save(RumpunMK rumpunMK);
	public RumpunMK findById(UUID idRumpunMK);
	public String delete(UUID uuid); 
	public List<RumpunMK> findAll();

}
