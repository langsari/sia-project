package com.bustan.siakad.service;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.RelasiMKAlihjenjang;

public interface RelasiMKAlihjenjangService {
	public List<RelasiMKAlihjenjang> get();
	public List<RelasiMKAlihjenjang> get(String where);
	public List<RelasiMKAlihjenjang> get(String where, String order);
	public List<RelasiMKAlihjenjang> get(String where, String order, int limit, int offset);
	public RelasiMKAlihjenjang getById(UUID idRelasiMKAlihjenjang);
	public List<Object[]> getRelasi(String where, String order, int limit, int offset);
    public String save(RelasiMKAlihjenjang relasiMKAlihjenjang);
    public String delete(UUID idRelasiMKAlihjenjang);
    public Datatable getdatatable(String sEcho,int iDisplayLength, int iDisplayStart, int iSortCol_0, String sSortDir_0, String sSearch, String filter);
}
