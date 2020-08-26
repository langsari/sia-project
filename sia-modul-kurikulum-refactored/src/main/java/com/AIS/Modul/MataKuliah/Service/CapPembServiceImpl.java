
package com.AIS.Modul.MataKuliah.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.AIS.Modul.MataKuliah.Repository.CapPembRepository;
import com.sia.modul.domain.*;

@Service
public class CapPembServiceImpl implements CapPembService {

	@Autowired
	private CapPembRepository capPembRepo;
	
	@Autowired
	private SubCapPembService subCapPembServ;
	 
	@Override
	public List<CapPemb> findBySatMan(UUID idSatMan) {
		// TODO Auto-generated method stub
		return capPembRepo.findBySatMan(idSatMan);
	}
	@Override
	public Datatable getdatatable(String sEcho, int iDisplayLength,
			int iDisplayStart, int iSortCol_0, String sSortDir_0,
			String sSearch, String filter) { 
		return null;
	}
	@Override
	public List<CapPemb> get() {
		return get("");
	}

	@Override
	public List<CapPemb> get(String where) {
		return get(where,"");
	}

	@Override
	public List<CapPemb> get(String where, String order) {
		return get(where,order,-1,-1);
	}

	@Override
	public List<CapPemb> get(String where, String order, int limit, int offset) {
		return capPembRepo.get(where, order, limit, offset);
	}
	@Override
	public String save(CapPemb capPemb) {
		if(capPemb.getIdCapPemb() != null)
		{
			//update
//			java.util.Date currentTime = new java.util.Date(); 
//			capPemb.setWaktuTambah(new java.sql.Timestamp(currentTime.getTime()));
			capPembRepo.update(capPemb);
			return capPemb.getIdCapPemb().toString();
		}
		else
		{
			//insert 
			java.util.Date currentTime = new java.util.Date(); 
			capPemb.setWaktuTambah(new java.sql.Timestamp(currentTime.getTime()));
			return capPembRepo.insert(capPemb).toString();
		}
	}
	@Override
	public CapPemb findById(UUID idCapPemb) {
		// TODO Auto-generated method stub
		return capPembRepo.findById(idCapPemb);
	}

	@Override
	public String delete(UUID idCapPemb) {
		// TODO Auto-generated method stub
		CapPemb capPemb = capPembRepo.findById(idCapPemb);
		if(capPemb==null) return null;
		else{
			capPemb.setStatusCapPemb(true);  
			java.util.Date currentTime = new java.util.Date(); 
			capPemb.setWaktuHapus(new java.sql.Timestamp(currentTime.getTime()));
			capPembRepo.update(capPemb);
			return "Ok";
		}
	}
	@Override
	public List<CapPemb> findAll() {
		// TODO Auto-generated method stub
		return capPembRepo.findAll();
	}
	
	private String [] column1 = {"cp.idCapPemb","kur.thnMulai", "kur.namaKurikulum", "satman.nmSatMan", 
			"cp.namaCapPemb", "cp.deskripsiCapPemb"};
	private Boolean[] searchable1 = {false,true,true,true,true,true};

	
	@Override
	public Datatable getdatatable1(String sEcho, int iDisplayLength,
			int iDisplayStart, int iSortCol_0, String sSortDir_0,
			String sSearch) {
		// TODO Auto-generated method stub
		DatatableExtractParams parameter = new DatatableExtractParams(sSearch, this.column1, this.searchable1, iSortCol_0, sSortDir_0);
		Datatable capPembDatatable= new Datatable();
		capPembDatatable.setsEcho(sEcho);
		String dbFilter = "AND cp.statusCapPemb=false";
//		if(filter != null && !filter.equals("")) dbFilter+=" AND "+filter; 
		List<CapPemb> queryResult = get("("+parameter.getWhere()+")"+dbFilter, parameter.getOrder(), iDisplayLength, iDisplayStart);
		List<String[]> aData = new ArrayList<String[]>();
		for (CapPemb capPemb : queryResult) {
			String[] capPembString = new String[9];
			capPembString[0] = capPemb.getIdCapPemb().toString();
			capPembString[1] = String.valueOf(capPemb.getKurikulum().getThnMulai());
			capPembString[2] = String.valueOf(capPemb.getKurikulum().getNamaKurikulum());
			capPembString[3] = String.valueOf(capPemb.getSatMan().getNmSatMan()); 
			capPembString[4] = String.valueOf(capPemb.getNamaCapPemb());
			capPembString[5] = String.valueOf(capPemb.getDeskripsiCapPemb());
			aData.add(capPembString);
		}
		capPembDatatable.setAaData(aData);
		capPembDatatable.setiTotalRecords(capPembRepo.count(""));
		capPembDatatable.setiTotalDisplayRecords(capPembRepo.count(parameter.getWhere()));

		return capPembDatatable;
	}
	@Override
	public List<CapPemb> findByKurikulum(UUID idKurikulum) {
		// TODO Auto-generated method stub
		return capPembRepo.findByKurikulum(idKurikulum);
	}
	@Override
	public List<CapPemb> findByParent(UUID idCapPemb) {
		//return null;
		// TODO Auto-generated method stub
		return capPembRepo.findByParent(idCapPemb);
	} 
}
