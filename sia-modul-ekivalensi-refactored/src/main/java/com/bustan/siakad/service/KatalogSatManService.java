package com.bustan.siakad.service;

import java.util.List;
import java.util.UUID;

import com.bustan.siakad.service.Datatable;
import com.sia.modul.domain.KatalogSatMan;

public interface KatalogSatManService {
	public List<KatalogSatMan> get();
	public List<KatalogSatMan> get(String where);
	public List<KatalogSatMan> get(String where, String order);
	public List<KatalogSatMan> get(String where, String order, int limit, int offset);
	public KatalogSatMan getById(UUID idKatalogSatMan);
    public String save(KatalogSatMan katalogSatMan);
    public String delete(UUID idKatalogSatMan);
    public Datatable getdatatable(String sEcho,int iDisplayLength, int iDisplayStart, int iSortCol_0, String sSortDir_0, String sSearch, String filter);
}
