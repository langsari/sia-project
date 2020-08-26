package com.bustan.siakad.service;

import java.util.List;
import java.util.UUID;

import com.bustan.siakad.service.Datatable;
import com.sia.modul.domain.AlihJenjangMKTerakui;

public interface AlihJenjangMKTerakuiService {
	public List<AlihJenjangMKTerakui> get();
	public List<AlihJenjangMKTerakui> get(String where);
	public List<AlihJenjangMKTerakui> get(String where, String order);
	public List<AlihJenjangMKTerakui> get(String where, String order, int limit, int offset);
	public AlihJenjangMKTerakui getById(UUID idAlihJenjangMKTerakui);
    public String save(AlihJenjangMKTerakui alihJenjangMKTerakui);
    public String delete(UUID idKrsCalonPD);
    public Datatable getdatatable(String sEcho,int iDisplayLength, int iDisplayStart, int iSortCol_0, String sSortDir_0, String sSearch, String filter);
}
