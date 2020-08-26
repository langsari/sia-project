package com.its.sia.service;

import java.util.List;
import java.util.UUID;

import javax.xml.crypto.dsig.keyinfo.KeyValue;

import org.springframework.web.bind.annotation.RequestParam;

import com.sia.modul.domain.Pd;

public interface PdService {
	public List<Pd> get();
	public List<Pd> get(String where);
	public List<Pd> get(String where, String order);
	public List<Pd> get(String where, String order, int limit, int offset);
	public List<Integer> getAngkatan();
	public List<Integer> getAngkatan(String where);
	public List<Integer> getAngkatan(String where, String order);
	public List<Integer> getAngkatan(String where, String order, int limit, int offset);
    public Pd getById(UUID idPd);
    public String save(Pd pd);
    public String delete(UUID idPd);
    public Datatable getdatatable(String sEcho,int iDisplayLength, int iDisplayStart, int iSortCol_0, String sSortDir_0, String sSearch, String filter);
    public Datatable getKrsSetuju(String sEcho,int iDisplayLength, int iDisplayStart, int iSortCol_0, String sSortDir_0, String sSearch, String filter);
    public Datatable getAnakWali(String sEcho,int iDisplayLength, int iDisplayStart, int iSortCol_0, String sSortDir_0, String sSearch, String filter);
    public Datatable getAnggotaRombel(String sEcho,int iDisplayLength, int iDisplayStart, int iSortCol_0, String sSortDir_0, String sSearch, String filter);
}
