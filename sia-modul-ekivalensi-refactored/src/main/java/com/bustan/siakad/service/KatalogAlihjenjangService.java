package com.bustan.siakad.service;

import java.util.List;
import java.util.UUID;

import com.bustan.siakad.service.Datatable;
import com.sia.modul.domain.KatalogAlihjenjang;

public interface KatalogAlihjenjangService {
	public List<KatalogAlihjenjang> get();
	public List<KatalogAlihjenjang> get(String where);
	public List<KatalogAlihjenjang> get(String where, String order);
	public List<KatalogAlihjenjang> get(String where, String order, int limit, int offset);
	public KatalogAlihjenjang getById(UUID idKatalogAlihjenjang);
    public String save(KatalogAlihjenjang katalogAlihjenjang);
    public String delete(UUID idKatalogAlihjenjang);
    public Datatable getdatatable(String sEcho,int iDisplayLength, int iDisplayStart, int iSortCol_0, String sSortDir_0, String sSearch, String filter);
}
