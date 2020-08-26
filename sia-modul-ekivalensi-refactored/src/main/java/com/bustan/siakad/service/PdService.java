package com.bustan.siakad.service;

import java.util.List;
import java.util.UUID;

import com.bustan.siakad.service.Datatable;
import com.sia.modul.domain.Pd;

public interface PdService {
	public List<Pd> get();
	public List<Pd> get(String where);
	public List<Pd> get(String where, String order);
	public List<Pd> get(String where, String order, int limit, int offset);
	public Pd getById(UUID idPd);
    public Datatable getdatatable(String sEcho,int iDisplayLength, int iDisplayStart, int iSortCol_0, String sSortDir_0, String sSearch, String filter);
}
