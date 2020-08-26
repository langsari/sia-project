package com.its.sia.service;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.AnggotaRombel;
import com.sia.modul.domain.Pd;
import com.sia.modul.domain.Ptk;;

public interface AnggotaRombelService {
	public List<AnggotaRombel> get();
	public List<AnggotaRombel> get(String where);
	public List<AnggotaRombel> get(String where, String order);
	public List<AnggotaRombel> get(String where, String order, int limit, int offset);
    public AnggotaRombel getById(UUID idAnggotaRombel);
    public String save(AnggotaRombel anggotaRombel);
    public String delete(UUID idAnggotaRombel);
    public Datatable getdatatable(String sEcho,int iDisplayLength, int iDisplayStart, int iSortCol_0, String sSortDir_0, String sSearch, String filter);
    public Long countAnggota(String where);

}
