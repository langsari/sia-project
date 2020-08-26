package com.bustan.siakad.service;

import java.util.List;
import java.util.UUID;

import com.bustan.siakad.service.Datatable;
import com.sia.modul.domain.KrsHapus;

public interface KrsHapusService {
	public List<KrsHapus> get();
	public List<KrsHapus> get(String where);
	public List<KrsHapus> get(String where, String order);
	public List<KrsHapus> get(String where, String order, int limit, int offset);
	public KrsHapus getById(UUID idKrsHapus);
    public String save(KrsHapus krsHapus);
    public String delete(UUID krsHapus);
    public Datatable getdatatable(String sEcho,int iDisplayLength, int iDisplayStart, int iSortCol_0, String sSortDir_0, String sSearch, String filter);
}
