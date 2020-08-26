package com.its.sia.service;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.AnggotaRombel;
import com.sia.modul.domain.BatasAmbilSks;
import com.sia.modul.domain.Ips;
import com.sia.modul.domain.Pd;
import com.sia.modul.domain.Ptk;;

public interface BatasAmbilSksService {
	public List<BatasAmbilSks> get();
	public List<BatasAmbilSks> get(String where);
	public List<BatasAmbilSks> get(String where, String order);
	public List<BatasAmbilSks> get(String where, String order, int limit, int offset);
    public BatasAmbilSks getById(UUID idBatasAmbilSks);
    public String save(BatasAmbilSks batasAmbilSks);
    public String delete(UUID idBatasAmbilSks);
    public Datatable getdatatable(String sEcho,int iDisplayLength, int iDisplayStart, int iSortCol_0, String sSortDir_0, String sSearch, String filter);
}
