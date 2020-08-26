package com.bustan.siakad.service;

import java.util.List;
import java.util.UUID;

import com.bustan.siakad.service.Datatable;
import com.sia.modul.domain.MKWajibPd;

public interface MKWajibPdService {
	public List<MKWajibPd> get();
	public List<MKWajibPd> get(String where);
	public List<MKWajibPd> get(String where, String order);
	public List<MKWajibPd> get(String where, String order, int limit, int offset);
	public MKWajibPd getById(UUID idMKWajibPd);
    public String save(MKWajibPd mkWajibPd);
    public String delete(UUID idMKWajibPd);
    public Datatable getdatatable(String sEcho,int iDisplayLength, int iDisplayStart, int iSortCol_0, String sSortDir_0, String sSearch, String filter);
}
