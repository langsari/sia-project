package com.its.sia.service;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.Rombel;

public interface RombelService {
	public List<Rombel> get();
	public List<Rombel> get(String where);
	public List<Rombel> get(String where, String order);
	public List<Rombel> get(String where, String order, int limit, int offset);
    public Rombel getById(UUID idRombel);
    public String save(Rombel rombel);
    public String delete(UUID idRombel);
    public Datatable getdatatable(String sEcho,int iDisplayLength, int iDisplayStart, int iSortCol_0, String sSortDir_0, String sSearch, String filter);
}
