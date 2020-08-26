package com.its.sia.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sia.modul.domain.SatMan;
import com.its.sia.repository.SatManRepository;

@Service
public class SatManServiceImpl implements SatManService {

	private String[] column = {"idSatMan","nmSatMan","idSatManInduk","aSatManAktif"};
	private Boolean[] searchable = {false,true,false,false};

	@Autowired
	private SatManRepository satManRepository;
	
	@Override
	public List<SatMan> get() {
		return get("");
	}

	@Override
	public List<SatMan> get(String where) {
		return get(where,"");
	}

	@Override
	public List<SatMan> get(String where, String order) {
		return get(where,order,-1,-1);
	}

	@Override
	public List<SatMan> get(String where, String order, int limit, int offset) {
		return satManRepository.get(where, order, limit, offset);
	}

	@Override
	public SatMan getById(UUID idSatMan) {
		return satManRepository.getById(idSatMan);
	}

	@Override
	public String save(SatMan satMan) {
		String where ="";
		if(satMan.getIdSatMan()!=null) where =" AND idSatMan!='"+satMan.getIdSatMan()+"'";
		List<SatMan> queryResult = get("nmSatMan = '"+satMan.getNmSatMan()+"'"+where);
		if(queryResult.size()>0) return null;
		else if(satMan.getIdSatMan() != null)
		{
			//update
			satManRepository.update(satMan);
			return satMan.getIdSatMan().toString();
		}
		else
		{
			//insert
			return satManRepository.insert(satMan).toString();
		}
	}

	@Override
	public String delete(UUID idSatMan) {
		SatMan satMan = satManRepository.getById(idSatMan);
		if(satMan==null) return null;
		else{
			satMan.setaSatManAktif(true);
			satManRepository.update(satMan);
			return "Ok";
		}
	}

	@Override
	public Datatable getdatatable(String sEcho, int iDisplayLength,
			int iDisplayStart, int iSortCol_0, String sSortDir_0,
			String sSearch, String filter) {
		DatatableExtractParams parameter = new DatatableExtractParams(sSearch, this.column, this.searchable, iSortCol_0, sSortDir_0);
		Datatable ptkDataTable= new Datatable();
		ptkDataTable.setsEcho(sEcho);
		String dbFilter = "";
		if(filter != null && !filter.equals("")) dbFilter+=" AND "+filter; 
		List<SatMan> queryResult = get("("+parameter.getWhere()+")"+dbFilter, parameter.getOrder(), iDisplayLength, iDisplayStart);
		List<String[]> aData = new ArrayList<String[]>();
		for (SatMan satMan : queryResult) {
			String[] ptkString = new String[4];
			ptkString[0] = satMan.getIdSatMan().toString();
			ptkString[1] = String.valueOf(satMan.getNmSatMan());
			ptkString[2] = String.valueOf(satMan.getIdSatManInduk());
			ptkString[3] = String.valueOf(satMan.getaSatManAktif());
			aData.add(ptkString);
		}
		ptkDataTable.setAaData(aData);
		ptkDataTable.setiTotalRecords(satManRepository.count(filter));
		ptkDataTable.setiTotalDisplayRecords(satManRepository.count("("+parameter.getWhere()+")"+dbFilter));

		return ptkDataTable;
	}

	@Override
	public List<SatMan> listChild(UUID idSatMan) {
		return satManRepository.getChildOf(idSatMan);
	}
	
	@Override
	public List<SatMan> listSatManPtk(UUID idSatMan) {
		return satManRepository.getSatManPtk(idSatMan);
	}

	@Override
	public List<SatMan> listSatManInMK(UUID idMK) {
		return satManRepository.getSatManInMK(idMK);
	}
}
