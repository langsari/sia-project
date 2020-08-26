package com.AIS.Modul.MataKuliah.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.AIS.Modul.MataKuliah.Repository.PustakaRepository;
import com.sia.modul.domain.Pustaka;

@Service
public class PustakaServiceImpl implements PustakaService {

	@Autowired
	private PustakaRepository pustakaRepo;
	
	private String [] column = {"idPustaka","namaPustaka", "deskripsiPustaka", "sifatPustaka", "statusPustaka"};
	private Boolean[] searchable = {false,true,true,true,false};
	
	@Override
	public Datatable getdatatable(String sEcho, int iDisplayLength,
			int iDisplayStart, int iSortCol_0, String sSortDir_0,
			String sSearch, String filter) {
		// TODO Auto-generated method stub
		DatatableExtractParams parameter = new DatatableExtractParams(sSearch, this.column, this.searchable, iSortCol_0, sSortDir_0);
		Datatable pustakaDatatable= new Datatable();
		pustakaDatatable.setsEcho(sEcho);
		String dbFilter = "";
		if(filter != null && !filter.equals("")) dbFilter+=" AND "+filter; 
		List<Pustaka> queryResult = get("("+parameter.getWhere()+")"+dbFilter, parameter.getOrder(), iDisplayLength, iDisplayStart);
		List<String[]> aData = new ArrayList<String[]>();
		for (Pustaka pustaka : queryResult) {
			String[] pustakaString = new String[6];
			pustakaString[0] = pustaka.getIdPustaka().toString();
			pustakaString[1] = String.valueOf(pustaka.getNamaPustaka());
			pustakaString[2] = String.valueOf(pustaka.getDeskripsiPustaka());
			pustakaString[3] = String.valueOf(pustaka.getSifatPustaka());
			pustakaString[4] = String.valueOf(pustaka.isStatusPustaka());
			pustakaString[5] = String.valueOf(pustaka.isStatusPustaka()); 
			aData.add(pustakaString);
		}
		pustakaDatatable.setAaData(aData);
		pustakaDatatable.setiTotalRecords(pustakaRepo.count(""));
		pustakaDatatable.setiTotalDisplayRecords(pustakaRepo.count("("+parameter.getWhere()+") AND "+filter));

		return pustakaDatatable;
		
	}

	private List<Pustaka> get(String where, String order,
			int limit, int offset) {
		// TODO Auto-generated method stub
		return pustakaRepo.get(where, order, limit, offset);
	}

	@Override
	public String save(Pustaka pustaka) {
		// TODO Auto-generated method stub
		if(pustaka.getIdPustaka() != null)
		{
			//update 
			pustakaRepo.update(pustaka);
			return pustaka.getIdPustaka().toString();
		}
		else
		{
			//insert
			return pustakaRepo.insert(pustaka).toString();
		}
	}

	@Override
	public Pustaka findById(UUID idPustaka) {
		// TODO Auto-generated method stub
		return pustakaRepo.findById(idPustaka);
	}

	@Override
	public String delete(UUID idPustaka) {
		// TODO Auto-generated method stub
		Pustaka pustaka = pustakaRepo.findById(idPustaka);
		if(pustaka==null) return null;
		else{
			pustaka.setStatusPustaka(true);
			pustakaRepo.update(pustaka);
			return "Ok";
		}
	}
	
	@Override
	public List<Pustaka> findAll() {
		// TODO Auto-generated method stub
		return pustakaRepo.findAll();
	}

}
