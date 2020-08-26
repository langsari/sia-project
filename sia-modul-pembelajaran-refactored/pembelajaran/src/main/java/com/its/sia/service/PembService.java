package com.its.sia.service;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.Pemb;
import com.sia.modul.domain.SatMan;

public interface PembService {
	public List<Pemb> get();
	public List<Pemb> get(String where);
	public List<Pemb> get(String where, String order);
	public List<Pemb> get(String where, String order, int limit, int offset);
    public Pemb getById(UUID idPemb);
    public String save(Pemb pemb);
    public String delete(UUID idPemb);
    public Datatable getdatatable(String sEcho,int iDisplayLength, int iDisplayStart, int iSortCol_0, String sSortDir_0, String sSearch, String filter);
	public List<Pemb> getPembInSatMan();
	public List<Pemb> getPembInSatMan(String where);
	public List<Pemb> getPembInSatMan(String where, String order);
	public List<Pemb> getPembInSatMan(String where, String order, int limit, int offset);
	public List<SatMan> getSatManKurikulum(String where, String order, int limit, int offset);
	public List<SatMan> getSatManKurikulum();
	public List<SatMan> getSatManKurikulum(String where);
	public List<SatMan> getSatManKurikulum(String where, String order);
}
