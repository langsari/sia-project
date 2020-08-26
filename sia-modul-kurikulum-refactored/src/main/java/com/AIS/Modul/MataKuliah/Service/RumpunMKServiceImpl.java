package com.AIS.Modul.MataKuliah.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
  
import org.springframework.stereotype.Service;
import com.sia.modul.domain.*;
import com.AIS.Modul.MataKuliah.Repository.RumpunMKRepository;
  
@Service
public class RumpunMKServiceImpl implements RumpunMKService {


	@Autowired
	private RumpunMKRepository rumpunMKRepo;
	
	private String [] column = {"idRumpunMK","namaRumpunMK", "statusRumpunMK"};
	private Boolean[] searchable = {false,true,false};
	
	@Override
	public Datatable getdatatable(String sEcho, int iDisplayLength,
			int iDisplayStart, int iSortCol_0, String sSortDir_0,
			String sSearch, String filter) {
		
		DatatableExtractParams parameter = new DatatableExtractParams(sSearch, this.column, this.searchable, iSortCol_0, sSortDir_0);
		Datatable rumpunMKDatatable= new Datatable();
		rumpunMKDatatable.setsEcho(sEcho);
		String dbFilter = "";
		if(filter != null && !filter.equals("")) dbFilter+=" AND "+filter; 
		List<RumpunMK> queryResult = get("("+parameter.getWhere()+")"+dbFilter, parameter.getOrder(), iDisplayLength, iDisplayStart);
		List<String[]> aData = new ArrayList<String[]>();
		for (RumpunMK rumpunMK : queryResult) {
			String[] rumpunMKString = new String[4];
			rumpunMKString[0] = rumpunMK.getIdRumpunMK().toString();
			rumpunMKString[1] = String.valueOf(rumpunMK.getNamaRumpunMK());
			rumpunMKString[2] = String.valueOf(rumpunMK.getStatusRumpunMK());
			rumpunMKString[3] = String.valueOf(rumpunMK.getStatusRumpunMK());
			aData.add(rumpunMKString);
		}
		rumpunMKDatatable.setAaData(aData);
		rumpunMKDatatable.setiTotalRecords(rumpunMKRepo.count(""));
		rumpunMKDatatable.setiTotalDisplayRecords(rumpunMKRepo.count("("+parameter.getWhere()+") AND "+filter));

		return rumpunMKDatatable;
		
	} 
	
	@Override
	public List<RumpunMK> get() {
		return get("");
	}

	@Override
	public List<RumpunMK> get(String where) {
		return get(where,"");
	}

	@Override
	public List<RumpunMK> get(String where, String order) {
		return get(where,order,-1,-1);
	}

	@Override
	public List<RumpunMK> get(String where, String order, int limit, int offset) {
		return rumpunMKRepo.get(where, order, limit, offset);
	}
	@Override
	public String save(RumpunMK rumpunMK) {
		if(rumpunMK.getIdRumpunMK() != null)
		{
			//update
			rumpunMKRepo.update(rumpunMK);
			return rumpunMK.getIdRumpunMK().toString();
		}
		else
		{
			//insert
			return rumpunMKRepo.insert(rumpunMK).toString();
		}
	}

	@Override
	public RumpunMK findById(UUID idRumpunMK) {
		// TODO Auto-generated method stub
		return rumpunMKRepo.findById(idRumpunMK);
	}

	@Override
	public String delete(UUID idRumpunMK) {
		// TODO Auto-generated method stub
		RumpunMK rumpunMK = rumpunMKRepo.findById(idRumpunMK);
		if(rumpunMK==null) return null;
		else{
			rumpunMK.setStatusRumpunMK(true);
			rumpunMKRepo.update(rumpunMK);
			return "Ok";
		}
	}

	@Override
	public List<RumpunMK> findAll() {
		// TODO Auto-generated method stub
		return rumpunMKRepo.findAll();
	}
}
