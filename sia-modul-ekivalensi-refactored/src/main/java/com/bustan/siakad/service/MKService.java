package com.bustan.siakad.service;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.MK;

public interface MKService {
	public List<MK> get();
	public List<MK> get(String where);
	public List<MK> get(String where, String order);
	public List<MK> get(String where, String order, int limit, int offset);
    public MK getById(UUID idMK);
    public String save(MK mk);
    public String delete(UUID idMK);
    public Datatable getdatatable(String sEcho,int iDisplayLength, int iDisplayStart, int iSortCol_0, String sSortDir_0, String sSearch, String filter);
}
