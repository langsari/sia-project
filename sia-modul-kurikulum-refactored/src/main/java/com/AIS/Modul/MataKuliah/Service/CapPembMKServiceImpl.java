package com.AIS.Modul.MataKuliah.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.AIS.Modul.MataKuliah.Repository.CapPembMKRepository;
import com.sia.modul.domain.CapPemb;
import com.sia.modul.domain.CapPembMK;

@Service
public class CapPembMKServiceImpl implements CapPembMKService {
	
	@Autowired
	private CapPembMKRepository capPembMKRepo;
	
	@Autowired
	private SubCapPembMKService subCapPembMKServ;
	
	@Autowired
	private CapPembService capPembServ;
	 
	
	@Override
	public Datatable getdatatable(String sEcho, int iDisplayLength,
			int iDisplayStart, int iSortCol_0, String sSortDir_0,
			String sSearch, String filter) {
		 

		return null;
	}

	private List<CapPembMK> get(String where, String order,
			int limit, int iDisplayStart) {
		// TODO Auto-generated method stub
		return capPembMKRepo.get(where, order, limit, iDisplayStart);
	}
	
	@Override
	public String save(CapPembMK capPembMK) {
		if(capPembMK.getIdCapPembMK() != null)
		{
			//update
//			java.util.Date currentTime = new java.util.Date(); 
//			capPembMK.setWaktuTambah(new java.sql.Timestamp(currentTime.getTime()));
			capPembMKRepo.update(capPembMK);
			return capPembMK.getIdCapPembMK().toString();
		}
		else
		{
			//insert
			java.util.Date currentTime = new java.util.Date(); 
			capPembMK.setWaktuTambah(new java.sql.Timestamp(currentTime.getTime()));
			return capPembMKRepo.insert(capPembMK).toString();
		}
	}

	@Override
	public CapPembMK findById(UUID idCapPembMK) {
		// TODO Auto-generated method stub
		return capPembMKRepo.findById(idCapPembMK);
	}

	@Override
	public List<CapPemb> findParent(CapPembMK capPembMK) {
		// TODO Auto-generated method stub 
		return capPembMKRepo.findParent(capPembMK);
	}

	@Override
	public String delete(UUID idCapPembMK) {
		// TODO Auto-generated method stub
		CapPembMK capPembMK = capPembMKRepo.findById(idCapPembMK);
		if(capPembMK==null) return null;
		else{
			capPembMK.setStatusCapPembMK(true);  
			java.util.Date currentTime = new java.util.Date(); 
			capPembMK.setWaktuHapus(new java.sql.Timestamp(currentTime.getTime()));
			capPembMKRepo.update(capPembMK);
			return "Ok";
		}
	}

	private String [] column = {"cpmk.idCapPembMK","mk.namaMK", "cpmk.namaCapPembMK", "cpmk.deskripsiCapPembMK"};
	private Boolean[] searchable = {false,true,true,true};

	
	@Override
	public Datatable getdatatable(String sEcho, int iDisplayLength,
			int iDisplayStart, int iSortCol_0, String sSortDir_0, String sSearch) {
		// TODO Auto-generated method stub
		DatatableExtractParams parameter = new DatatableExtractParams(sSearch, this.column, this.searchable, iSortCol_0, sSortDir_0);
		Datatable capPembmkDatatable= new Datatable();
		capPembmkDatatable.setsEcho(sEcho);
		//String dbFilter = "AND cpmk.statusCapPembMK=false"; 
		List<CapPembMK> queryResult = get("("+parameter.getWhere()+")", parameter.getOrder(), iDisplayLength, iDisplayStart);
		List<String[]> aData = new ArrayList<String[]>();
		for (CapPembMK capPembMK : queryResult) {
			String[] capPembMKString = new String[9];
			capPembMKString[0] = capPembMK.getIdCapPembMK().toString();
			capPembMKString[1] = String.valueOf(capPembMK.getMk().getNamaMK());
			capPembMKString[2] = String.valueOf(capPembMK.getNamaCapPembMK());
			capPembMKString[3] = String.valueOf(capPembMK.getDeskripsiCapPembMK());
			aData.add(capPembMKString);
		}
		capPembmkDatatable.setAaData(aData);
		capPembmkDatatable.setiTotalRecords(capPembMKRepo.count(""));
		capPembmkDatatable.setiTotalDisplayRecords(capPembMKRepo.count(parameter.getWhere()));

		return capPembmkDatatable;
	}

	@Override
	public List<CapPembMK> findAll() {
		// TODO Auto-generated method stub
		return capPembMKRepo.findAll();
	}

	@Override
	public List<CapPembMK> findByMK(UUID idMK) {
		// TODO Auto-generated method stub
		return capPembMKRepo.findByMK(idMK);
	}  
}
