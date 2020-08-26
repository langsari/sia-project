package com.its.sia.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sia.modul.domain.ThnAjaran;
import com.its.sia.repository.ThnAjaranRepository;

@Service
public class ThnAjaranServiceImpl implements ThnAjaranService{
	
	private String[] column = {"idThnAjaran","thnThnAjaran","persenHadirMinimPd","persenMinimPertemuan","aThnAjaranTerhapus"};
	private Boolean[] searchable = {false,true,true,true,false};	
	
	@Autowired
	private ThnAjaranRepository thnAjaranRepository;
	
	@Override
	public List<ThnAjaran> get() {
		return get("");
	}

	@Override
	public List<ThnAjaran> get(String where) {
		return get(where,"");
	}

	@Override
	public List<ThnAjaran> get(String where, String order) {
		return get(where,order,-1,-1);
	}

	@Override
	public List<ThnAjaran> get(String where, String order, int limit, int offset) {
		return thnAjaranRepository.get(where, order, limit, offset);
	}

	@Override
	public ThnAjaran getById(UUID idThnAjaran) {
		return thnAjaranRepository.getById(idThnAjaran);
	}
	
	@Override
	public String save(ThnAjaran thnAjaran) {
		String where = "thnThnAjaran = "+thnAjaran.getThnThnAjaran();
		if(thnAjaran.getIdThnAjaran()!=null) 
			where +=" AND idThnAjaran !='"+thnAjaran.getIdThnAjaran().toString()+"'";
		if(get(where).size()>0)	return null;
		else if(thnAjaran.getIdThnAjaran() != null)
		{
			//update
			thnAjaranRepository.update(thnAjaran);
			return thnAjaran.getIdThnAjaran().toString();
		}
		else
		{
			//insert
			return thnAjaranRepository.insert(thnAjaran).toString();
		}
	}

	@Override
	public String delete(UUID idThnAjaran) {
		ThnAjaran thnAjaran = thnAjaranRepository.getById(idThnAjaran);
		if(thnAjaran==null) return null;
		else{
			thnAjaran.setaThnAjaranTerhapus(true);
			thnAjaranRepository.update(thnAjaran);
			return "Ok";
		}
	}

	@Override
	public Datatable getdatatable(String sEcho, int iDisplayLength,
			int iDisplayStart, int iSortCol_0, String sSortDir_0, String sSearch, String filter) {
		// TODO Auto-generated method stub
		DatatableExtractParams parameter = new DatatableExtractParams(sSearch, this.column, this.searchable, iSortCol_0, sSortDir_0);
		Datatable thnAjaranDatatable= new Datatable();
		thnAjaranDatatable.setsEcho(sEcho);
		String dbFilter = "";
		if(filter != null && !filter.equals("")) dbFilter+=" AND "+filter; 
		List<ThnAjaran> queryResult = get("("+parameter.getWhere()+")"+dbFilter, parameter.getOrder(), iDisplayLength, iDisplayStart);
		List<String[]> aData = new ArrayList<String[]>();
		for (ThnAjaran thnAjaran : queryResult) {
			String[] thnAjaranString = new String[6];
			thnAjaranString[0] = thnAjaran.getIdThnAjaran().toString();
			thnAjaranString[1] = String.valueOf(thnAjaran.getThnThnAjaran());
			thnAjaranString[2] = String.valueOf(thnAjaran.getPersenHadirMinimPd());
			thnAjaranString[3] = String.valueOf(thnAjaran.getPersenMinimPertemuan());
			thnAjaranString[4] = String.valueOf(thnAjaran.isaThnAjaranTerhapus());
			thnAjaranString[5] = String.valueOf(thnAjaran.isaThnAjaranTerhapus());
			aData.add(thnAjaranString);
		}
		thnAjaranDatatable.setAaData(aData);
		thnAjaranDatatable.setiTotalRecords(thnAjaranRepository.count(filter));
		thnAjaranDatatable.setiTotalDisplayRecords(thnAjaranRepository.count("("+parameter.getWhere()+")"+dbFilter));

		return thnAjaranDatatable;
	}

}
