package com.its.sia.service;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.AnggotaRombel;
import com.sia.modul.domain.Pd;
import com.sia.modul.domain.PresensiPd;
import com.sia.modul.domain.Ptk;;

public interface PresensiPdService {
	public List<PresensiPd> get();
	public List<PresensiPd> get(String where);
	public List<PresensiPd> get(String where, String order);
	public List<PresensiPd> get(String where, String order, int limit, int offset);
    public PresensiPd getById(UUID idPresensiPd);
    public String save(PresensiPd presensiPd);
    public String delete(UUID idPresensiPd);
    public Datatable getdatatable(String sEcho,int iDisplayLength, int iDisplayStart, int iSortCol_0, String sSortDir_0, String sSearch, String filter);
}
