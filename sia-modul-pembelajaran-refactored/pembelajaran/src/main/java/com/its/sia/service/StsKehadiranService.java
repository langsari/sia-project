package com.its.sia.service;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.StsKehadiran;

public interface StsKehadiranService {
	public List<StsKehadiran> get();
	public List<StsKehadiran> get(String where);
	public List<StsKehadiran> get(String where, String order);
	public List<StsKehadiran> get(String where, String order, int limit, int offset);
    public StsKehadiran getById(UUID idStsKehadiran);
    public String save(StsKehadiran stsKehadiran);
    public String delete(UUID idStsKehadiran);
    public Datatable getdatatable(String sEcho,int iDisplayLength, int iDisplayStart, int iSortCol_0, String sSortDir_0, String sSearch, String filter);
}
