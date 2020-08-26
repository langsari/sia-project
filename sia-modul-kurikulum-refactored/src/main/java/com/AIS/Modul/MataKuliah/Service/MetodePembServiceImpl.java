package com.AIS.Modul.MataKuliah.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.AIS.Modul.MataKuliah.Repository.MetodePembRepository;
import com.sia.modul.domain.MetodePemb;
import com.sia.modul.domain.RumpunMK;
 

@Service
public class MetodePembServiceImpl implements MetodePembService{

	private String [] column = {"idMetodePemb","namaMetodePemb", "deskripsiMetodePemb"};
	private Boolean[] searchable = {false,true,true};
	
	@Autowired
	private MetodePembRepository metodePembRepo;

	@Override
	public Datatable getdatatable(String sEcho, int iDisplayLength,
			int iDisplayStart, int iSortCol_0, String sSortDir_0,
			String sSearch, String filter) {
		// TODO Auto-generated method stub
		DatatableExtractParams parameter = new DatatableExtractParams(sSearch, this.column, this.searchable, iSortCol_0, sSortDir_0);
		Datatable metodePembDatatable= new Datatable();
		metodePembDatatable.setsEcho(sEcho);
		String dbFilter = "";
		if(filter != null && !filter.equals("")) dbFilter+=" AND "+filter; 
		List<MetodePemb> queryResult = get("("+parameter.getWhere()+")"+dbFilter, parameter.getOrder(), iDisplayLength, iDisplayStart);
		List<String[]> aData = new ArrayList<String[]>();
		for (MetodePemb mp : queryResult) {
			String[] metodePembString = new String[5];
			metodePembString[0] = mp.getIdMetodePemb().toString();
			metodePembString[1] = String.valueOf(mp.getNamaMetodePemb());
			metodePembString[2] = String.valueOf(mp.getDeskripsiMetodePemb());
			metodePembString[3] = String.valueOf(mp.isStatusMetodePemb());
			metodePembString[4] = String.valueOf(mp.isStatusMetodePemb());
			aData.add(metodePembString);
		}
		metodePembDatatable.setAaData(aData);
		metodePembDatatable.setiTotalRecords(metodePembRepo.count(""));
		metodePembDatatable.setiTotalDisplayRecords(metodePembRepo.count("("+parameter.getWhere()+") AND "+filter));

		return metodePembDatatable;
	}

	private List<MetodePemb> get(String where, String order,
			int limit, int offset) {
		// TODO Auto-generated method stub
		return metodePembRepo.get(where, order, limit, offset);
	}

	@Override
	public String save(MetodePemb metodePemb) {
		// TODO Auto-generated method stub
		if(metodePemb.getIdMetodePemb() != null)
		{
			//update
			metodePembRepo.update(metodePemb);
			return metodePemb.getIdMetodePemb().toString();
		}
		else
		{
			//insert
			return metodePembRepo.insert(metodePemb).toString();
		}
	}

	@Override
	public MetodePemb findById(UUID idMetodePemb) {
		// TODO Auto-generated method stub
		return metodePembRepo.findById(idMetodePemb);
	}

	@Override
	public String delete(UUID uuid) {
		// TODO Auto-generated method stub
		MetodePemb metodePemb = metodePembRepo.findById(uuid);
		if(metodePemb==null) return null;
		else{ 
			metodePemb.setStatusMetodePemb(true);
			metodePembRepo.update(metodePemb);
			return "Ok";
		}
	}

	@Override
	public List<MetodePemb> findAll() {
		// TODO Auto-generated method stub
		return metodePembRepo.findAll();
	}
}
