package com.bustan.siakad.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bustan.siakad.repository.KatalogSatManRepository;
import com.sia.modul.domain.KatalogSatMan;

@Service
public class KatalogSatManServiceImpl implements KatalogSatManService{

	private String[] column = {"katalogSatMan.idKatalogSatMan","katalog.idKatalogAlihjenjang","katalog.nmKatalog","satMan.idSatMan","satMan.nmSatMan"};
	private Boolean[] searchable = {false,false,true,false,true};
	
	@Autowired
	KatalogSatManRepository katalogSatManRepository;
	
	@Override
	public List<KatalogSatMan> get() {
		return get("");
	}

	@Override
	public List<KatalogSatMan> get(String where) {
		return get(where,"");
	}

	@Override
	public List<KatalogSatMan> get(String where, String order) {
		return get(where,order,-1,-1);
	}

	@Override
	public List<KatalogSatMan> get(String where, String order, int limit,
			int offset) {
		return katalogSatManRepository.get(where,order,limit,offset);
	}

	@Override
	public KatalogSatMan getById(UUID idMKAlihjenjang) {
		return katalogSatManRepository.getById(idMKAlihjenjang);
	}

	@Override
	public String save(KatalogSatMan katalogSatMan) {
		if(katalogSatMan.getIdKatalogSatMan() != null)
		{
			//update
			katalogSatManRepository.update(katalogSatMan);
			return katalogSatMan.getIdKatalogSatMan().toString();
		}
		else
		{
			//insert
			return katalogSatManRepository.insert(katalogSatMan).toString();
		}
	}

	@Override
	public String delete(UUID idKatalogSatMan) {
		KatalogSatMan katalogSatMan = katalogSatManRepository.getById(idKatalogSatMan);
		if(katalogSatMan==null) return null;
		else{			
			katalogSatManRepository.delete(katalogSatMan);
			return "Ok";
		}
	}

	@Override
	public Datatable getdatatable(String sEcho, int iDisplayLength,
			int iDisplayStart, int iSortCol_0, String sSortDir_0,
			String sSearch, String filter) {
		DatatableExtractParams parameter = new DatatableExtractParams(sSearch, this.column, this.searchable, iSortCol_0, sSortDir_0);
		Datatable mkLuarDatatable= new Datatable();
		mkLuarDatatable.setsEcho(sEcho);
		String dbFilter = "";
		if(filter != null && !filter.equals("")) dbFilter+=" AND "+filter; 
		List<KatalogSatMan> queryResult = get("("+parameter.getWhere()+")"+dbFilter, parameter.getOrder(), iDisplayLength, iDisplayStart);
		List<String[]> aData = new ArrayList<String[]>();
		for (KatalogSatMan katalogSatMan : queryResult) {
			String[] mkAlihjenjangString = new String[5];
			mkAlihjenjangString[0] = katalogSatMan.getIdKatalogSatMan().toString();
			mkAlihjenjangString[1] = String.valueOf(katalogSatMan.getKatalog().getIdKatalogAlihjenjang());
			mkAlihjenjangString[2] = String.valueOf(katalogSatMan.getKatalog().getNmKatalog());
			mkAlihjenjangString[3] = String.valueOf(katalogSatMan.getSatMan().getIdSatMan());			
			mkAlihjenjangString[4] = String.valueOf(katalogSatMan.getSatMan().getNmSatMan());			
			aData.add(mkAlihjenjangString);
		}
		mkLuarDatatable.setAaData(aData);
		mkLuarDatatable.setiTotalRecords(katalogSatManRepository.count(""));
		mkLuarDatatable.setiTotalDisplayRecords(katalogSatManRepository.count("("+parameter.getWhere()+")"+dbFilter));

		return mkLuarDatatable;
	}
}
