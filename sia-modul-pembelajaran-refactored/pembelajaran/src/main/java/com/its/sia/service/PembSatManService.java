package com.its.sia.service;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.AnggotaRombel;
import com.sia.modul.domain.Pd;
import com.sia.modul.domain.Pemb;
import com.sia.modul.domain.PembSatMan;
import com.sia.modul.domain.Ptk;;

public interface PembSatManService {
	public List<PembSatMan> get();
	public List<PembSatMan> get(String where);
	public List<PembSatMan> get(String where, String order);
	public List<PembSatMan> get(String where, String order, int limit, int offset);
    public PembSatMan getById(UUID idPembSatMan);
    public String save(PembSatMan pembSatMan);
    public String delete(UUID idPembSatMan);
    public Datatable getdatatable(String sEcho,int iDisplayLength, int iDisplayStart, int iSortCol_0, String sSortDir_0, String sSearch, String filter);
    public List<Pemb> getPemb();
	public List<Pemb> getPemb(String where);
	public List<Pemb> getPemb(String where, String order);
	public List<Pemb> getPemb(String where, String order, int limit, int offset);
	public Datatable getPembDatatable(String sEcho,int iDisplayLength, int iDisplayStart, int iSortCol_0, String sSortDir_0, String sSearch, String filter);
	
}
