package com.AIS.Modul.MataKuliah.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.AIS.Modul.MataKuliah.Repository.SubCapPembMKRepository;
import com.sia.modul.domain.CapPemb;
import com.sia.modul.domain.CapPembMK;
import com.sia.modul.domain.SubCapPemb;
import com.sia.modul.domain.SubCapPembMK;

@Service
public class SubCapPembMKServiceImpl implements SubCapPembMKService {
	 
	@Autowired
	private SubCapPembMKRepository subCapPembMKRepo; 

	@Override
	public CapPemb findByCapPembMK(CapPembMK capPembMK) {
		// TODO Auto-generated method stub
		UUID idCapPembMK = capPembMK.getIdCapPembMK();
		return subCapPembMKRepo.findByCapPembMK(idCapPembMK);
	}

	private String [] column = {"cpmk.idCapPembMK","mk.namaMK", "cpmk.namaCapPembMK", "cpmk.deskripsiCapPembMK", 
			"cp.namaCapPemb", "cp.deskripsiCapPemb", "cpmk.statusCapPembMK"};
	private Boolean[] searchable = {false,true,true,true,true,true,false};
	
	@Override
	public Datatable getdatatable(String sEcho, int iDisplayLength,
			int iDisplayStart, int iSortCol_0, String sSortDir_0,
			String sSearch, String filter) {
		// TODO Auto-generated method stub
		DatatableExtractParams parameter = new DatatableExtractParams(sSearch, this.column, this.searchable, iSortCol_0, sSortDir_0);
		Datatable subCapPembDatatable= new Datatable();
		subCapPembDatatable.setsEcho(sEcho);
		String dbFilter = "";
		if(filter != null && !filter.equals("")) dbFilter+=" AND "+filter; 
		List<SubCapPembMK> queryResult = get("("+parameter.getWhere()+")"+dbFilter, parameter.getOrder(), iDisplayLength, iDisplayStart);
		List<String[]> aData = new ArrayList<String[]>();
		for (SubCapPembMK subCapPembMK : queryResult) {
			String[] subCapPembMKString = new String[9]; 
			subCapPembMKString[0] = subCapPembMK.getCapPembMK().getIdCapPembMK().toString();
			subCapPembMKString[1] = String.valueOf(subCapPembMK.getCapPembMK().getMk().getNamaMK());
			subCapPembMKString[2] = String.valueOf(subCapPembMK.getCapPembMK().getNamaCapPembMK());
			subCapPembMKString[3] = String.valueOf(subCapPembMK.getCapPembMK().getDeskripsiCapPembMK());
			if(subCapPembMK.getCapPembMK()!=null){ 
				subCapPembMKString[4] = String.valueOf(subCapPembMK.getCapPemb().getNamaCapPemb());
				subCapPembMKString[5] = String.valueOf(subCapPembMK.getCapPemb().getDeskripsiCapPemb());
			}   
			subCapPembMKString[6] = String.valueOf(subCapPembMK.getCapPembMK().isStatusCapPembMK());  
			subCapPembMKString[7] = String.valueOf(subCapPembMK.getCapPembMK().isStatusCapPembMK());  
			aData.add(subCapPembMKString);
		}
		subCapPembDatatable.setAaData(aData);
		subCapPembDatatable.setiTotalRecords(subCapPembMKRepo.count(""));
		subCapPembDatatable.setiTotalDisplayRecords(subCapPembMKRepo.count("("+parameter.getWhere()+") AND "+filter));

		return subCapPembDatatable;
	}

	private List<SubCapPembMK> get(String where, String order,
			int limit, int iDisplayStart) {
		// TODO Auto-generated method stub
		return subCapPembMKRepo.get(where, order, limit, iDisplayStart);
	}

	@Override
	public String save(SubCapPembMK subCapPembMKNew) {
		// TODO Auto-generated method stub
		if(subCapPembMKNew.getIdSubCapPembMK() != null)
		{
			//update
			subCapPembMKRepo.update(subCapPembMKNew);
			return subCapPembMKNew.getIdSubCapPembMK().toString();
		}
		else
		{
			//insert
			return subCapPembMKRepo.insert(subCapPembMKNew).toString();
		}
	}

	@Override
	public List<SubCapPembMK> findCapPemb(String idCapPembMK) {
		// TODO Auto-generated method stub
		return subCapPembMKRepo.findCapPemb(idCapPembMK);
	}

	@Override
	public void delete(UUID idSubCapPembMK) {
		// TODO Auto-generated method stub
		 subCapPembMKRepo.delete(idSubCapPembMK);
	}

	@Override
	public List<SubCapPembMK> findByCapPembMKList(UUID idCapPembMK) {
		// TODO Auto-generated method stub
		return subCapPembMKRepo.findCapPemb(idCapPembMK.toString());
	}

	@Override
	public List<CapPemb> findByMK(UUID idMK) {
		// TODO Auto-generated method stub
		return subCapPembMKRepo.findByMK(idMK);
	}
 
}
