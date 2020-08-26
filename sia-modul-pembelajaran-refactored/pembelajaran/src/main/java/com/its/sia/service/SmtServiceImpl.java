package com.its.sia.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sia.modul.domain.Smt;
import com.sia.modul.domain.ThnAjaran;
import com.its.sia.repository.SmtRepository;;

@Service
public class SmtServiceImpl implements SmtService {

	private String[] column = {"idSmt","nmSmt","jmlPertemuan","jenisSmt","aSmthapus"};
	private Boolean[] searchable = {false,true,true,false,false};	

	@Autowired
	private SmtRepository smtRepository;
	
	@Override
	public List<Smt> get() {
		return get("");
	}

	@Override
	public List<Smt> get(String where) {
		return get(where,"");
	}

	@Override
	public List<Smt> get(String where, String order) {
		return get(where,order,-1,-1);
	}

	@Override
	public List<Smt> get(String where, String order, int limit, int offset) {
		return smtRepository.get(where, order, limit, offset);
	}

	@Override
	public Smt getById(UUID idSmt) {
		return smtRepository.getById(idSmt);
	}

	@Override
	public String save(Smt smt) {
		String where = smt.getIdSmt()==null?"":"idSmt != '"+smt.getIdSmt()+"' and ";
		Long smtTerhitung = (long) 0;
		if(smt.getJenisSmt() ==  0) where += "jenisSmt = 0 and aSmthapus = false";
		if(smt.getJenisSmt() ==  1)  where += "jenisSmt = 1 and aSmthapus = false";
		if(smt.getJenisSmt()!=2) smtTerhitung = smtRepository.count(where);
		if(smtTerhitung>0) return null;
		if(smt.getIdSmt() != null)
		{
			//update
			smtRepository.update(smt);
			return smt.getIdSmt().toString();
		}
		else
		{
			//insert
			return smtRepository.insert(smt).toString();
		}
	}

	@Override
	public String delete(UUID idSmt) {
		Smt smt = smtRepository.getById(idSmt);
		if(smt==null) return null;
		else{
			smt.setaSmthapus(true);
			smtRepository.update(smt);
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
		List<Smt> queryResult = get("("+parameter.getWhere()+")"+dbFilter, parameter.getOrder(), iDisplayLength, iDisplayStart);
		List<String[]> aData = new ArrayList<String[]>();
		for (Smt smt : queryResult) {
			String[] smtString = new String[6];
			smtString[0] = smt.getIdSmt().toString();
			smtString[1] = String.valueOf(smt.getNmSmt());
			smtString[2] = String.valueOf(smt.getJmlPertemuan());
			smtString[3] = String.valueOf(smt.getJenisSmt());
			smtString[4] = String.valueOf(smt.isaSmthapus());
			smtString[5] = String.valueOf(smt.isaSmthapus());
			aData.add(smtString);
		}
		smtDatatable.setAaData(aData);
		smtDatatable.setiTotalRecords(smtRepository.count(filter));
		smtDatatable.setiTotalDisplayRecords(smtRepository.count("("+parameter.getWhere()+")"+dbFilter));

		return smtDatatable;
	}
}
