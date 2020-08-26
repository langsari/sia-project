package com.bustan.siakad.service;

import java.util.List;
import java.util.UUID;

import com.bustan.siakad.service.Datatable;
import com.sia.modul.domain.MKAlihjenjang;

public interface MKAlihjenjangService {
	public List<MKAlihjenjang> get();
	public List<MKAlihjenjang> get(String where);
	public List<MKAlihjenjang> get(String where, String order);
	public List<MKAlihjenjang> get(String where, String order, int limit, int offset);
	public MKAlihjenjang getById(UUID idMKAlihjenjang);
    public String save(MKAlihjenjang mkAlihjenjang);
    public String delete(UUID mkAlihjenjang);
    public Datatable getdatatable(String sEcho,int iDisplayLength, int iDisplayStart, int iSortCol_0, String sSortDir_0, String sSearch, String filter);
}
