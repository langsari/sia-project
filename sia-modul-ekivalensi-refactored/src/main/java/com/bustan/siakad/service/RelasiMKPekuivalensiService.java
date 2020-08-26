package com.bustan.siakad.service;

import java.util.List;
import java.util.UUID;

import com.bustan.siakad.service.Datatable;
import com.sia.modul.domain.RelasiMKPekuivalensi;

public interface RelasiMKPekuivalensiService {
	public List<RelasiMKPekuivalensi> get();
	public List<RelasiMKPekuivalensi> get(String where);
	public List<RelasiMKPekuivalensi> get(String where, String order);
	public List<RelasiMKPekuivalensi> get(String where, String order, int limit, int offset);
	public RelasiMKPekuivalensi getById(UUID idMKLuar);
    public String save(RelasiMKPekuivalensi mkLuar);
    public void delete(RelasiMKPekuivalensi mkLuar);
//    public Datatable getdatatable(String sEcho,int iDisplayLength, int iDisplayStart, int iSortCol_0, String sSortDir_0, String sSearch, String filter);
}
