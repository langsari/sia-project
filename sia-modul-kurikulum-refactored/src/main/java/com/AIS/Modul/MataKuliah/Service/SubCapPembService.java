package com.AIS.Modul.MataKuliah.Service;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.CapPemb;
import com.sia.modul.domain.SubCapPemb;

public interface SubCapPembService {

	public List<SubCapPemb> findParent(String idSubCapPemb);

	public String save(SubCapPemb scpNew);

	public Datatable getdatatable(String sEcho, int iDisplayLength,
			int iDisplayStart, int iSortCol_0, String sSortDir_0,
			String sSearch, String filter);

	public List<SubCapPemb> get(String string, String order,
			int iDisplayLength, int iDisplayStart);

	public void delete(UUID idSubCapPemb);

	
}
