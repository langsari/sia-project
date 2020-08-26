package com.AIS.Modul.MataKuliah.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.AIS.Modul.MataKuliah.Repository.BentukPenilaianRepository;
import com.AIS.Modul.MataKuliah.Repository.MetodePembRepository;
import com.sia.modul.domain.BentukPenilaian;
import com.sia.modul.domain.MetodePemb;

@Service
public class BentukPenilaianServiceImpl implements BentukPenilaianService {

	private String [] column = {"idBentuk","namaBentuk", "deskripsiBentuk", "statusBentuk"};
	private Boolean[] searchable = {false,true,true,false};
	
	@Autowired
	public BentukPenilaianRepository bentukPenilaianRepo;
	
	@Override
	public Datatable getdatatable(String sEcho, int iDisplayLength,
			int iDisplayStart, int iSortCol_0, String sSortDir_0,
			String sSearch, String filter) {
		// TODO Auto-generated method stub
		DatatableExtractParams parameter = new DatatableExtractParams(sSearch, this.column, this.searchable, iSortCol_0, sSortDir_0);
		Datatable bentukDatatable= new Datatable();
		bentukDatatable.setsEcho(sEcho);
		String dbFilter = "";
		if(filter != null && !filter.equals("")) dbFilter+=" AND "+filter; 
		List<BentukPenilaian> queryResult = get("("+parameter.getWhere()+")"+dbFilter, parameter.getOrder(), iDisplayLength, iDisplayStart);
		List<String[]> aData = new ArrayList<String[]>();
		for (BentukPenilaian bp : queryResult) {
			String[] bentukString = new String[6];
			bentukString[0] = bp.getIdBentuk().toString();
			bentukString[1] = String.valueOf(bp.getNamaBentuk());
			bentukString[2] = String.valueOf(bp.getDeskripsiBentuk());
			bentukString[3] = String.valueOf(bp.isStatusBentuk());
			bentukString[4] = String.valueOf(bp.isStatusBentuk());
			aData.add(bentukString);
		}
		bentukDatatable.setAaData(aData);
		bentukDatatable.setiTotalRecords(bentukPenilaianRepo.count(""));
		bentukDatatable.setiTotalDisplayRecords(bentukPenilaianRepo.count("("+parameter.getWhere()+") AND "+filter));

		return bentukDatatable;
	}

	private List<BentukPenilaian> get(String where, String order,
			int limit, int offset) {
		// TODO Auto-generated method stub
		return bentukPenilaianRepo.get(where, order, limit, offset);
	}

	@Override
	public String save(BentukPenilaian bentukPenilaian) {
		// TODO Auto-generated method stub
		if(bentukPenilaian.getIdBentuk() != null)
		{
			//update
			bentukPenilaianRepo.update(bentukPenilaian);
			return bentukPenilaian.getIdBentuk().toString();
		}
		else
		{
			//insert
			return bentukPenilaianRepo.insert(bentukPenilaian).toString();
		}
	}

	@Override
	public BentukPenilaian findById(UUID idBentuk) {
		// TODO Auto-generated method stub
		return bentukPenilaianRepo.findById(idBentuk);
	}

	@Override
	public String delete(UUID uuid) {
		// TODO Auto-generated method stub
		BentukPenilaian bentukPenilaian = bentukPenilaianRepo.findById(uuid);
		if(bentukPenilaian==null) return null;
		else{ 
			bentukPenilaian.setStatusBentuk(true);
			bentukPenilaianRepo.update(bentukPenilaian);
			return "Ok";
		}
	}

	@Override
	public List<BentukPenilaian> findAll() {
		// TODO Auto-generated method stub
		return bentukPenilaianRepo.findAll();
	}

}
