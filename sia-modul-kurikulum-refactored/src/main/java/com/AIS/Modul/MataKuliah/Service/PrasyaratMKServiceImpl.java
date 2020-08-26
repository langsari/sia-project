package com.AIS.Modul.MataKuliah.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.AIS.Modul.MataKuliah.Repository.PrasyaratMKRepository;
import com.sia.modul.domain.PrasyaratMK;
import com.sia.modul.domain.RumpunMK;

@Service
public class PrasyaratMKServiceImpl implements PrasyaratMKService {

	@Autowired
	private PrasyaratMKRepository prasyaratMKRepo;
	
	private String [] column = {"pMK.idPrasyaratMK","child.kodeMK", "child.namaMK", "parent.kodeMK", "parent.namaMK", "pMK.statusPrasyarat"};
	private Boolean[] searchable = {false,true,true,true,true,false};
	
	@Override
	public Datatable getdatatable(String sEcho, int iDisplayLength,
			int iDisplayStart, int iSortCol_0, String sSortDir_0,
			String sSearch, String filter) {
		DatatableExtractParams parameter = new DatatableExtractParams(sSearch, this.column, this.searchable, iSortCol_0, sSortDir_0);
		Datatable prasyaratMKDatatable= new Datatable();
		prasyaratMKDatatable.setsEcho(sEcho);
		String dbFilter = "";
		if(filter != null && !filter.equals("")) dbFilter+=" AND "+filter; 
		List<PrasyaratMK> queryResult = (List<PrasyaratMK>) get("("+parameter.getWhere()+")"+dbFilter, parameter.getOrder(), iDisplayLength, iDisplayStart);
		List<String[]> aData = new ArrayList<String[]>();
		for (PrasyaratMK prasyaratMK : queryResult) {
			String[] prasyaratMKString = new String[7];
			prasyaratMKString[0] = prasyaratMK.getIdPrasyaratMK().toString();
			prasyaratMKString[1] = String.valueOf(prasyaratMK.getChildMK().getKodeMK());
			prasyaratMKString[2] = String.valueOf(prasyaratMK.getChildMK().getNamaMK());
			prasyaratMKString[3] = String.valueOf(prasyaratMK.getParentMK().getKodeMK());
			prasyaratMKString[4] = String.valueOf(prasyaratMK.getParentMK().getNamaMK());
			prasyaratMKString[5] = String.valueOf(prasyaratMK.getStatusPrasyarat());
			prasyaratMKString[6] = String.valueOf(prasyaratMK.getStatusPrasyarat());
			aData.add(prasyaratMKString);
		}
		prasyaratMKDatatable.setAaData(aData);
		prasyaratMKDatatable.setiTotalRecords(prasyaratMKRepo.count(""));
		prasyaratMKDatatable.setiTotalDisplayRecords(prasyaratMKRepo.count("("+parameter.getWhere()+") AND "+filter));

		return prasyaratMKDatatable;
		
	}


	@Override
	public List<PrasyaratMK> get() {
		return get("");
	}

	@Override
	public List<PrasyaratMK> get(String where) {
		return get(where,"");
	}

	@Override
	public List<PrasyaratMK> get(String where, String order) {
		return get(where,order,-1,-1);
	}

	@Override
	public List<PrasyaratMK> get(String where, String order, int limit, int offset) {
		return prasyaratMKRepo.get(where, order, limit, offset);
	}
	@Override
	public String save(PrasyaratMK prasyaratMK) {
		if(prasyaratMK.getIdPrasyaratMK() != null)
		{
			//update
			prasyaratMKRepo.update(prasyaratMK);
			return prasyaratMK.getIdPrasyaratMK().toString();
		}
		else
		{
			//insert
			return prasyaratMKRepo.insert(prasyaratMK).toString();
		}
	}

	@Override
	public PrasyaratMK findById(UUID idPrasyaratMK) {
		// TODO Auto-generated method stub
		return prasyaratMKRepo.findById(idPrasyaratMK);
	}

	@Override
	public String delete(UUID idPrasyaratMK) {
		// TODO Auto-generated method stub
		PrasyaratMK prasyaratMK = prasyaratMKRepo.findById(idPrasyaratMK);
		if(prasyaratMK==null) return null;
		else{
			prasyaratMK.setStatusPrasyarat(true);
			prasyaratMKRepo.update(prasyaratMK);
			return "Ok";
		}
	}

	@Override
	public List<PrasyaratMK> findAll() {
		// TODO Auto-generated method stub
		return prasyaratMKRepo.findAll();
	}


	@Override
	public List<PrasyaratMK> findParentMK(UUID idMK) {
		// TODO Auto-generated method stub
		return prasyaratMKRepo.findParent(idMK);
	}
	
}
