package com.its.sia.service;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.Smt;

public interface SmtService {
	public List<Smt> get();
	public List<Smt> get(String where);
	public List<Smt> get(String where, String order);
	public List<Smt> get(String where, String order, int limit, int offset);
    public Smt getById(UUID idSmt);
    public String save(Smt smt);
    public String delete(UUID idSmt);
    public Datatable getdatatable(String sEcho,int iDisplayLength, int iDisplayStart, int iSortCol_0, String sSortDir_0, String sSearch, String filter);
}
