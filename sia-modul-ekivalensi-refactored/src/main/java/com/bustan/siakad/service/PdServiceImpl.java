package com.bustan.siakad.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bustan.siakad.repository.PdRepository;
import com.sia.modul.domain.Pd;

@Service
public class PdServiceImpl implements PdService{

	private String[] column = {"pd.idPd","pd.nimPd","pd.nmPd","pd.angkatanPd"};
	private Boolean[] searchable = {false,true,true,true};
	
	@Autowired
	PdRepository pdRepository;
	
	@Override
	public List<Pd> get() {
		return get("");
	}

	@Override
	public List<Pd> get(String where) {
		return get(where,"");
	}

	@Override
	public List<Pd> get(String where, String order) {
		return get(where,order,-1,-1);
	}

	@Override
	public List<Pd> get(String where, String order, int limit,
			int offset) {
		return pdRepository.get(where,order,limit,offset);
	}

	@Override
	public Pd getById(UUID idPd) {
		return pdRepository.getById(idPd);
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
		List<Pd> queryResult = get("("+parameter.getWhere()+")"+dbFilter, parameter.getOrder(), iDisplayLength, iDisplayStart);
		List<String[]> aData = new ArrayList<String[]>();
		for (Pd pd : queryResult) {
			String[] pdString = new String[4];
			pdString[0] = pd.getIdPd().toString();
			pdString[1] = String.valueOf(pd.getNimPd());			
			pdString[2] = String.valueOf(pd.getNmPd());
			pdString[3] = String.valueOf(pd.getAngkatanPd().toString());
			aData.add(pdString);
		}
		mkLuarDatatable.setAaData(aData);
		mkLuarDatatable.setiTotalRecords(pdRepository.count(""));
		mkLuarDatatable.setiTotalDisplayRecords(pdRepository.count("("+parameter.getWhere()+")"+dbFilter));

		return mkLuarDatatable;
	}
}
