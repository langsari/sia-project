package com.bustan.siakad.service;

import java.util.List;
import java.util.UUID;

import com.bustan.siakad.service.Datatable;
import com.sia.modul.domain.CalonPD;

public interface CalonPDService {
	public List<CalonPD> get();
	public List<CalonPD> get(String where);
	public List<CalonPD> get(String where, String order);
	public List<CalonPD> get(String where, String order, int limit, int offset);
	public CalonPD getById(UUID idCalonPD);
    public String save(CalonPD calonPD);
    public String delete(UUID idCalonPD);
    public Datatable getdatatable(String sEcho,int iDisplayLength, int iDisplayStart, int iSortCol_0, String sSortDir_0, String sSearch, String filter);
}
