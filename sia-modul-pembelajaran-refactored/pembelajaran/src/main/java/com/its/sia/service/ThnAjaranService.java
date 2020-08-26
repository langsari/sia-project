package com.its.sia.service;

import java.util.List;
import java.util.UUID;

import javax.xml.crypto.dsig.keyinfo.KeyValue;

import org.springframework.web.bind.annotation.RequestParam;

import com.sia.modul.domain.ThnAjaran;

public interface ThnAjaranService {
	public List<ThnAjaran> get();
	public List<ThnAjaran> get(String where);
	public List<ThnAjaran> get(String where, String order);
	public List<ThnAjaran> get(String where, String order, int limit, int offset);
    public ThnAjaran getById(UUID idThnAjaran);
    public String save(ThnAjaran thnAjaran);
    public String delete(UUID idThnAjaran);
    public Datatable getdatatable(String sEcho,int iDisplayLength, int iDisplayStart, int iSortCol_0, String sSortDir_0, String sSearch, String filter);
}
