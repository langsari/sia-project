package com.its.sia.service;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.Pd;
import com.sia.modul.domain.Ptk;;

public interface PtkService {
	public List<Ptk> get();
	public List<Ptk> get(String where);
	public List<Ptk> get(String where, String order);
	public List<Ptk> get(String where, String order, int limit, int offset);
    public Ptk getById(UUID idPtk);
    public String save(Ptk ptk);
    public String delete(UUID idPtk);
    public Datatable getdatatable(String sEcho,int iDisplayLength, int iDisplayStart, int iSortCol_0, String sSortDir_0, String sSearch, String filter);
    public Datatable getPerwalianDatatable(String sEcho,int iDisplayLength, int iDisplayStart, int iSortCol_0, String sSortDir_0, String sSearch, String filter);
    public List<Pd> listPerwalian(UUID idPtk);
}
