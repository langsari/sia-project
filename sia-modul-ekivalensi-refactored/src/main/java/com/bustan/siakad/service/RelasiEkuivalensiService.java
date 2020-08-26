package com.bustan.siakad.service;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.RelasiEkuivalensi;

public interface RelasiEkuivalensiService {
	public List<RelasiEkuivalensi> get();
	public List<RelasiEkuivalensi> get(String where);
	public List<RelasiEkuivalensi> get(String where, String order);
	public List<RelasiEkuivalensi> get(String where, String order, int limit, int offset);
	public List<RelasiEkuivalensi> get(String where, String order, String groupby, int limit, int offset);
	public List<Object[]> getUUID(String where, String order, int limit, int offset);
	public List<Object[]> getA(String where, String order, int limit, int offset);
	public RelasiEkuivalensi getById(UUID idRelasiEkuivalensi);
    public String save(RelasiEkuivalensi relasiEkuivalensi);
    public String delete(UUID idRelasiEkuivalensi);
    public Datatable getdatatable(String sEcho,int iDisplayLength, int iDisplayStart, int iSortCol_0, String sSortDir_0, String sSearch, String filter);
}
