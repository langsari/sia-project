package com.its.sia.service;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.AnggotaRombel;
import com.sia.modul.domain.Krs;
import com.sia.modul.domain.Pd;
import com.sia.modul.domain.Pemb;
import com.sia.modul.domain.Ptk;;

public interface KrsService {
	public List<Krs> get();
	public List<Krs> get(String where);
	public List<Krs> get(String where, String order);
	public List<Krs> get(String where, String order, int limit, int offset);
    public Krs getById(UUID idKrs);
    public void batasiKrs(Pemb pemb);
    public String save(Krs krs);
    public String delete(UUID idKrs);
    public String batal(UUID idKrs);
    public Long count(String where);
	public List<Pd> getPeserta();
	public List<Pd> getPeserta(String where);
	public List<Pd> getPeserta(String where, String order);
	public List<Pd> getPeserta(String where, String order, int limit, int offset);
    public Datatable getdatatable(String sEcho,int iDisplayLength, int iDisplayStart, int iSortCol_0, String sSortDir_0, String sSearch, String filter);
    public Datatable getPesertaDatatable(String sEcho,int iDisplayLength, int iDisplayStart, int iSortCol_0, String sSortDir_0, String sSearch, String filter);
    public Datatable getPdKrsDatatable(String sEcho,int iDisplayLength, int iDisplayStart, int iSortCol_0, String sSortDir_0, String sSearch, String filter);
    public String deletePdInKrs(UUID idPd, UUID idPemb);
    public String addFromRombel(UUID idRombel, UUID idPemb);
}
