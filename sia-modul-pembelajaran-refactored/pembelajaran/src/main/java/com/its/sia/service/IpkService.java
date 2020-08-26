package com.its.sia.service;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.Ipk;
import com.sia.modul.domain.Pd;

public interface IpkService {
	public List<Ipk> get();
	public List<Ipk> get(String where);
	public List<Ipk> get(String where, String order);
	public List<Ipk> get(String where, String order, int limit, int offset);
    public Ipk getById(UUID idAnggotaRombel);
    public Ipk getByPd(Pd pd);
    public String save(Ipk Ipk);
    public String delete(UUID idIpk);
    public Datatable getdatatable(String sEcho,int iDisplayLength, int iDisplayStart, int iSortCol_0, String sSortDir_0, String sSearch, String filter);
}
