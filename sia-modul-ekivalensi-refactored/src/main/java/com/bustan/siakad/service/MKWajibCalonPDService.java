package com.bustan.siakad.service;

import java.util.List;
import java.util.UUID;

import com.bustan.siakad.service.Datatable;
import com.sia.modul.domain.MKWajibCalonPD;

public interface MKWajibCalonPDService {
	public List<MKWajibCalonPD> get();
	public List<MKWajibCalonPD> get(String where);
	public List<MKWajibCalonPD> get(String where, String order);
	public List<MKWajibCalonPD> get(String where, String order, int limit, int offset);
	public MKWajibCalonPD getById(UUID idMKWajibCalonPD);
    public String save(MKWajibCalonPD mkWajibCalonPD);
    public String delete(UUID idMKWajibCalonPD);
    public Datatable getdatatable(String sEcho,int iDisplayLength, int iDisplayStart, int iSortCol_0, String sSortDir_0, String sSearch, String filter);
}
