package com.its.sia.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sia.modul.domain.Pd;
import com.sia.modul.domain.Ptk;
import com.its.sia.repository.PtkRepository;;

@Service
public class PtkServiceImpl implements PtkService {

	private String[] column = {"ptk.idPtk","ptk.nipPtk","ptk.nmPtk","ptk.statusPtk","ptk.aPtkTerhapus"};
	private Boolean[] searchable = {false,true,true,false,false};	
	private String[] columnPerwalian = {"ptk.nipPtk","ptk.nmPtk","ptk.idPtk"};
	private Boolean[] searchablePerwalian = {true,true,false};	

	@Autowired
	private PtkRepository ptkRepository;

	@Autowired
	private PdService pdService;
	
	@Override
	public List<Ptk> get() {
		return get("");
	}

	@Override
	public List<Ptk> get(String where) {
		return get(where,"");
	}

	@Override
	public List<Ptk> get(String where, String order) {
		return get(where,order,-1,-1);
	}

	@Override
	public List<Ptk> get(String where, String order, int limit, int offset) {
		return ptkRepository.get(where, order, limit, offset);
	}

	@Override
	public Ptk getById(UUID idPtk) {
		return ptkRepository.getById(idPtk);
	}

	@Override
	public String save(Ptk ptk) {
		String where ="";
		if(ptk.getIdPtk()!=null) where =" AND idPtk!='"+ptk.getIdPtk()+"'";
		List<Ptk> queryResult = get("nipPtk = '"+ptk.getNipPtk()+"'"+where);
		if(queryResult.size()>0) return null;
		else if(ptk.getIdPtk() != null)
		{
			//update
			ptkRepository.update(ptk);
			return ptk.getIdPtk().toString();
		}
		else
		{
			//insert
			return ptkRepository.insert(ptk).toString();
		}
	}

	@Override
	public String delete(UUID idPtk) {
		Ptk ptk = ptkRepository.getById(idPtk);
		if(ptk==null) return null;
		else{
			ptk.setaPtkTerhapus(true);
			ptkRepository.update(ptk);
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
		List<Ptk> queryResult = get("("+parameter.getWhere()+")"+dbFilter, parameter.getOrder(), iDisplayLength, iDisplayStart);
		List<String[]> aData = new ArrayList<String[]>();
		for (Ptk ptk : queryResult) {
			String[] ptkString = new String[5];
			ptkString[0] = ptk.getIdPtk().toString();
			ptkString[1] = String.valueOf(ptk.getNipPtk());
			ptkString[2] = String.valueOf(ptk.getNmPtk());
			ptkString[3] = String.valueOf(ptk.getStatusPtk());
			ptkString[4] = String.valueOf(ptk.isaPtkTerhapus());
			aData.add(ptkString);
		}
		ptkDataTable.setAaData(aData);
		ptkDataTable.setiTotalRecords(ptkRepository.count(filter));
		ptkDataTable.setiTotalDisplayRecords(ptkRepository.count("("+parameter.getWhere()+")"+dbFilter));

		return ptkDataTable;
	}
	
	@Override
	public Datatable getPerwalianDatatable(String sEcho, int iDisplayLength,
			int iDisplayStart, int iSortCol_0, String sSortDir_0,
			String sSearch, String filter) {
		DatatableExtractParams parameter = new DatatableExtractParams(sSearch, this.columnPerwalian, this.searchablePerwalian, iSortCol_0, sSortDir_0);
		Datatable ptkDataTable= new Datatable();
		ptkDataTable.setsEcho(sEcho);
		String dbFilter = "";
		if(filter != null && !filter.equals("")) dbFilter+=" AND "+filter; 
		List<Ptk> queryResult = get("("+parameter.getWhere()+")"+dbFilter, parameter.getOrder(), iDisplayLength, iDisplayStart);
		List<String[]> aData = new ArrayList<String[]>();
		for (Ptk ptk : queryResult) {
			String[] ptkString = new String[3];
			ptkString[0] = String.valueOf(ptk.getNipPtk());
			ptkString[1] = String.valueOf(ptk.getNmPtk());
			ptkString[2] = ptk.getIdPtk().toString();
			aData.add(ptkString);
		}
		ptkDataTable.setAaData(aData);
		ptkDataTable.setiTotalRecords(ptkRepository.count(filter));
		ptkDataTable.setiTotalDisplayRecords(ptkRepository.count("("+parameter.getWhere()+")"+dbFilter));

		return ptkDataTable;
	}

	@Override
	public List<Pd> listPerwalian(UUID idPtk){
		return pdService.get("ptk.idPtk = '"+idPtk.toString()+"'");
	}
}
