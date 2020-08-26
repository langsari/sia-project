package com.its.sia.service;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.Pemb;
import com.sia.modul.domain.PendidikPengajar;

public interface PendidikPengajarService {
	public List<PendidikPengajar> get();
	public List<PendidikPengajar> get(String where);
	public List<PendidikPengajar> get(String where, String order);
	public List<PendidikPengajar> get(String where, String order, int limit, int offset);
    public PendidikPengajar getById(UUID idPendidikPengajar);
    public String save(PendidikPengajar pendidikPengajar);
    public String delete(UUID idPendidikPengajar);
    public String deleteInPembelajaran(Pemb pemb);
    public Datatable getdatatable(String sEcho,int iDisplayLength, int iDisplayStart, int iSortCol_0, String sSortDir_0, String sSearch, String filter);
	public List<Pemb> getPemb();
	public List<Pemb> getPemb(String where);
	public List<Pemb> getPemb(String where, String order);
	public List<Pemb> getPemb(String where, String order, int limit, int offset);
	public Datatable getPembDatatable(String sEcho,int iDisplayLength, int iDisplayStart, int iSortCol_0, String sSortDir_0, String sSearch, String filter);
	
}
