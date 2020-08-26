package com.AIS.Modul.MataKuliah.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.AIS.Modul.MataKuliah.Repository.EkuivalensiMKRepository;
import com.sia.modul.domain.EkuivalensiMK;


@Service
public class EkuivalensiMKServiceImpl implements EkuivalensiMKService {
	
	@Autowired
	private EkuivalensiMKRepository ekuivalensiMKRepo;
	
	private String [] column = {"ekMK.idEkuivalensiMK","child.kodeMK", "child.namaMK", "parent.kodeMK", "parent.namaMK", "ekMK.statusEkuivalensi"};
	private Boolean[] searchable = {false,true,true,true,true,false};
	
	@Override
	public Datatable getdatatable(String sEcho, int iDisplayLength,
			int iDisplayStart, int iSortCol_0, String sSortDir_0,
			String sSearch, String filter) {
		DatatableExtractParams parameter = new DatatableExtractParams(sSearch, this.column, this.searchable, iSortCol_0, sSortDir_0);
		Datatable ekuivalensiMKDatatable= new Datatable();
		ekuivalensiMKDatatable.setsEcho(sEcho);
		String dbFilter = "";
		if(filter != null && !filter.equals("")) dbFilter+=" AND "+filter; 
		List<EkuivalensiMK> queryResult = (List<EkuivalensiMK>) get("("+parameter.getWhere()+")"+dbFilter, parameter.getOrder(), iDisplayLength, iDisplayStart);
		List<String[]> aData = new ArrayList<String[]>();
		for (EkuivalensiMK ekuivalensiMK : queryResult) {
			String[] ekuivalensiMKString = new String[7];
//			ekuivalensiMKString[0] = ekuivalensiMK.getIdEkuivalensiMK().toString();
//			ekuivalensiMKString[1] = String.valueOf(ekuivalensiMK.getChildMK().getKodeMK());
//			ekuivalensiMKString[2] = String.valueOf(ekuivalensiMK.getChildMK().getNamaMK());
//			ekuivalensiMKString[3] = String.valueOf(ekuivalensiMK.getParentMK().getKodeMK());
//			ekuivalensiMKString[4] = String.valueOf(ekuivalensiMK.getParentMK().getNamaMK());
//			ekuivalensiMKString[5] = String.valueOf(ekuivalensiMK.getStatusEkuivalensi());
//			ekuivalensiMKString[6] = String.valueOf(ekuivalensiMK.getStatusEkuivalensi());
			aData.add(ekuivalensiMKString);
		}
		ekuivalensiMKDatatable.setAaData(aData);
		ekuivalensiMKDatatable.setiTotalRecords(ekuivalensiMKRepo.count(""));
		ekuivalensiMKDatatable.setiTotalDisplayRecords(ekuivalensiMKRepo.count("("+parameter.getWhere()+") AND "+filter));

		return ekuivalensiMKDatatable;
		
	}


	@Override
	public List<EkuivalensiMK> get() {
		return get("");
	}

	@Override
	public List<EkuivalensiMK> get(String where) {
		return get(where,"");
	}

	@Override
	public List<EkuivalensiMK> get(String where, String order) {
		return get(where,order,-1,-1);
	}

	@Override
	public List<EkuivalensiMK> get(String where, String order, int limit, int offset) {
		return ekuivalensiMKRepo.get(where, order, limit, offset);
	}
	@Override
	public String save(EkuivalensiMK ekuivalensiMK) {
		if(ekuivalensiMK.getIdEkuivalensiMK() != null)
		{
			//update
			ekuivalensiMKRepo.update(ekuivalensiMK);
			return ekuivalensiMK.getIdEkuivalensiMK().toString();
		}
		else
		{
			//insert
			return ekuivalensiMKRepo.insert(ekuivalensiMK).toString();
		}
	}

	@Override
	public EkuivalensiMK findById(UUID idEkuivalensiMK) {
		// TODO Auto-generated method stub
		return ekuivalensiMKRepo.findById(idEkuivalensiMK);
	}

	@Override
	public String delete(UUID idEkuivalensiMK) {
		// TODO Auto-generated method stub
		EkuivalensiMK ekuivalensiMK = ekuivalensiMKRepo.findById(idEkuivalensiMK);
		if(ekuivalensiMK==null) return null;
		else{
			//ekuivalensiMK.setStatusEkuivalensi(true);
			ekuivalensiMKRepo.update(ekuivalensiMK);
			return "Ok";
		}
	}

	@Override
	public List<EkuivalensiMK> findAll() {
		// TODO Auto-generated method stub
		return ekuivalensiMKRepo.findAll();
	}
}
