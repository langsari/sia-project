package com.bustan.siakad.service;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.SatMan;

public interface SatManService {
	public List<SatMan> get();
	public List<SatMan> get(String where);
	public List<SatMan> get(String where, String order);
	public List<SatMan> get(String where, String order, int limit, int offset);
    public SatMan getById(UUID idSatMan);
    public String save(SatMan satMan);
    public String delete(UUID idPtk);
    public Datatable getdatatable(String sEcho,int iDisplayLength, int iDisplayStart, int iSortCol_0, String sSortDir_0, String sSearch, String filter);
    public List<SatMan> listChild(UUID idSatMan);
}
