package com.its.sia.service;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.AnggotaRombel;
import com.sia.modul.domain.Krs;
import com.sia.modul.domain.Pd;
import com.sia.modul.domain.PertemuanPembelajaran;
import com.sia.modul.domain.Ptk;;

public interface PertemuanPembelajaranService {
	public List<PertemuanPembelajaran> get();
	public List<PertemuanPembelajaran> get(String where);
	public List<PertemuanPembelajaran> get(String where, String order);
	public List<PertemuanPembelajaran> get(String where, String order, int limit, int offset);
    public PertemuanPembelajaran getById(UUID idKrs);
    public String save(PertemuanPembelajaran pertemuanPembelajaran);
    public String delete(UUID idPertemuanPembelajaran);
    public Long count(String where);
    public Datatable getdatatable(String sEcho,int iDisplayLength, int iDisplayStart, int iSortCol_0, String sSortDir_0, String sSearch, String filter);
}
