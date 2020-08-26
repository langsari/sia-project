package com.bustan.siakad.service;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.Kurikulum;

public interface KurikulumService {
	public List<Kurikulum> get();
	public List<Kurikulum> get(String where);
	public List<Kurikulum> get(String where, String order);
	public List<Kurikulum> get(String where, String order, int limit, int offset);
    public Kurikulum getById(UUID idPd);
    public String save(Kurikulum pd);
    public String delete(UUID idPd);
    public Datatable getdatatable(String sEcho,int iDisplayLength, int iDisplayStart, int iSortCol_0, String sSortDir_0, String sSearch, String filter);
}
