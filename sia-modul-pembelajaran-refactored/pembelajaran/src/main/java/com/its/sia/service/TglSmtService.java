package com.its.sia.service;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.TglSmt;

public interface TglSmtService {
	public List<TglSmt> get();
	public List<TglSmt> get(String where);
	public List<TglSmt> get(String where, String order);
	public List<TglSmt> get(String where, String order, int limit, int offset);
    public TglSmt getById(UUID idTglSmt);
    public TglSmt getAktif();
    public String save(TglSmt tglSmt);
    public String delete(UUID idTglSmt);
    public Datatable getdatatable(String sEcho,int iDisplayLength, int iDisplayStart, int iSortCol_0, String sSortDir_0, String sSearch, String filter);
}
