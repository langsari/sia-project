package com.its.sia.service;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.AnggotaRombel;
import com.sia.modul.domain.Ips;
import com.sia.modul.domain.Pd;
import com.sia.modul.domain.Ptk;;

public interface IpsService {
	public List<Ips> get();
	public List<Ips> get(String where);
	public List<Ips> get(String where, String order);
	public List<Ips> get(String where, String order, int limit, int offset);
    public Ips getById(UUID idIps);
    public Ips getIpsTerakhir(UUID idPd);
    public String save(Ips ips);
    public String delete(UUID idIps);
    public Datatable getdatatable(String sEcho,int iDisplayLength, int iDisplayStart, int iSortCol_0, String sSortDir_0, String sSearch, String filter);
}
