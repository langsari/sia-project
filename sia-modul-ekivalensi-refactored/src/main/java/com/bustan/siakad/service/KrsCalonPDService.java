package com.bustan.siakad.service;

import java.util.List;
import java.util.UUID;

import com.bustan.siakad.service.Datatable;
import com.sia.modul.domain.KrsCalonPD;
import com.sia.modul.domain.MKLuar;

public interface KrsCalonPDService {
	public List<KrsCalonPD> get();
	public List<KrsCalonPD> get(String where);
	public List<KrsCalonPD> get(String where, String order);
	public List<KrsCalonPD> get(String where, String order, int limit, int offset);
	public KrsCalonPD getById(UUID idkrsCalonPD);
    public String save(KrsCalonPD krsCalonPD);
    public String delete(UUID idKrsCalonPD);
    public Datatable getdatatable(String sEcho,int iDisplayLength, int iDisplayStart, int iSortCol_0, String sSortDir_0, String sSearch, String filter);
}
