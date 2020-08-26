package com.bustan.siakad.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bustan.siakad.repository.KatalogAlihjenjangRepository;
import com.sia.modul.domain.KatalogAlihjenjang;

@Service
public class KatalogAlihjenjangServiceImpl implements KatalogAlihjenjangService{

	private String[] column = {"katalog.idKatalogAlihjenjang","katalog.nmKatalog",
			"katalog.catatan","katalog.aTerhapus"};
	private Boolean[] searchable = {false,true,true,false};
	
	@Autowired
	KatalogAlihjenjangRepository katalogAlihjenjangRepository;
	
	@Override
	public List<KatalogAlihjenjang> get() {
		return get("");
	}

	@Override
	public List<KatalogAlihjenjang> get(String where) {
		return get(where,"");
	}

	@Override
	public List<KatalogAlihjenjang> get(String where, String order) {
		return get(where,order,-1,-1);
	}

	@Override
	public List<KatalogAlihjenjang> get(String where, String order, int limit,
			int offset) {
		return katalogAlihjenjangRepository.get(where,order,limit,offset);
	}

	@Override
	public KatalogAlihjenjang getById(UUID idKatalogAlihjenjang) {
		return katalogAlihjenjangRepository.getById(idKatalogAlihjenjang);
	}

	@Override
	public String save(KatalogAlihjenjang katalogAlihjenjang) {
		if(katalogAlihjenjang.getIdKatalogAlihjenjang() != null)
		{
			//update
			katalogAlihjenjangRepository.update(katalogAlihjenjang);
			return katalogAlihjenjang.getIdKatalogAlihjenjang().toString();
		}
		else
		{
			//insert
			return katalogAlihjenjangRepository.insert(katalogAlihjenjang).toString();
		}
	}

	@Override
	public String delete(UUID idKatalogAlihjenjang) {
		KatalogAlihjenjang katalogAlihjenjang = katalogAlihjenjangRepository.getById(idKatalogAlihjenjang);
		if(katalogAlihjenjang==null) return null;
		else{
			katalogAlihjenjang.setaTerhapus(true);
			katalogAlihjenjangRepository.update(katalogAlihjenjang);
			return "Ok";
		}
	}

	@Override
	public Datatable getdatatable(String sEcho, int iDisplayLength,
			int iDisplayStart, int iSortCol_0, String sSortDir_0,
			String sSearch, String filter) {
		DatatableExtractParams parameter = new DatatableExtractParams(sSearch, this.column, this.searchable, iSortCol_0, sSortDir_0);
		Datatable smtDatatable= new Datatable();
		smtDatatable.setsEcho(sEcho);
		String dbFilter = "";
		if(filter != null && !filter.equals("")) dbFilter+=" AND "+filter; 
		List<KatalogAlihjenjang> queryResult = get("("+parameter.getWhere()+")"+dbFilter, parameter.getOrder(), iDisplayLength, iDisplayStart);
		List<String[]> aData = new ArrayList<String[]>();
		for (KatalogAlihjenjang katalogAlihjenjang : queryResult) {
			String[] katalogAlihjenjangString = new String[4];
			katalogAlihjenjangString[0] = katalogAlihjenjang.getIdKatalogAlihjenjang().toString();		
			katalogAlihjenjangString[1] = String.valueOf(katalogAlihjenjang.getNmKatalog());
			katalogAlihjenjangString[2] = String.valueOf(katalogAlihjenjang.getCatatan());
			katalogAlihjenjangString[3] = String.valueOf(katalogAlihjenjang.isaTerhapus());
			aData.add(katalogAlihjenjangString);
		}
		smtDatatable.setAaData(aData);
		smtDatatable.setiTotalRecords(katalogAlihjenjangRepository.count(""));
		smtDatatable.setiTotalDisplayRecords(katalogAlihjenjangRepository.count("("+parameter.getWhere()+")"+dbFilter));

		return smtDatatable;
	}

}
