package com.its.sia.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sia.modul.domain.AnggotaRombel;
import com.sia.modul.domain.BatasAmbilSks;
import com.sia.modul.domain.Ips;
import com.sia.modul.domain.Rombel;
import com.its.sia.repository.AnggotaRombelRepository;
import com.its.sia.repository.BatasAmbilSksRepository;
import com.its.sia.repository.IpsRepository;
import com.its.sia.repository.RombelRepository;

@Service
public class BatasAmbilSksServiceImpl implements BatasAmbilSksService {

	private String[] column = {"idBatasAmbilSks","batasBawahIps","batasPengambilanSks"};
	private Boolean[] searchable = {false,true,true};	

	@Autowired
	private BatasAmbilSksRepository batasAmbilSksRepository;
	
	@Override
	public List<BatasAmbilSks> get() {
		return get("");
	}

	@Override
	public List<BatasAmbilSks> get(String where) {
		return get(where,"");
	}

	@Override
	public List<BatasAmbilSks> get(String where, String order) {
		return get(where,order,-1,-1);
	}

	@Override
	public List<BatasAmbilSks> get(String where, String order, int limit, int offset) {
		return batasAmbilSksRepository.get(where, order, limit, offset);
	}

	@Override
	public BatasAmbilSks getById(UUID idBatasAmbilSks) {
		return batasAmbilSksRepository.getById(idBatasAmbilSks);
	}

	@Override
	public String save(BatasAmbilSks batasAmbilSks) {
		String where = "";
		if(batasAmbilSks.getIdBatasAmbilSks()!=null) where += "idBatasAmbilSks !='"+batasAmbilSks.getIdBatasAmbilSks()+"' AND ";
		List<BatasAmbilSks> listBatasAmbilSks =  get(where+"batasBawahIps ="+batasAmbilSks.getBatasBawahIps());
		if(listBatasAmbilSks.size()>0)
		{
			return null;
		}
		else if(batasAmbilSks.getIdBatasAmbilSks()!= null)
		{
			//update
			batasAmbilSksRepository.update(batasAmbilSks);
			return batasAmbilSks.getIdBatasAmbilSks().toString();
		}
		else
		{
			//insert
			return batasAmbilSksRepository.insert(batasAmbilSks).toString();
		}
	}

	@Override
	public String delete(UUID idBatasAmbilSks) {
		BatasAmbilSks batasAmbilSks = batasAmbilSksRepository.getById(idBatasAmbilSks);
		if(batasAmbilSks==null) return null;
		else{
			batasAmbilSksRepository.delete(batasAmbilSks);
			return "Ok";
		}
	}

	@Override
	public Datatable getdatatable(String sEcho, int iDisplayLength,
			int iDisplayStart, int iSortCol_0, String sSortDir_0,
			String sSearch, String filter) {
		DatatableExtractParams parameter = new DatatableExtractParams(sSearch, this.column, this.searchable, iSortCol_0, sSortDir_0);
		Datatable rombelDatatable= new Datatable();
		rombelDatatable.setsEcho(sEcho);
		String dbFilter = "";
		if(filter != null && !filter.equals("")) dbFilter+=" AND "+filter; 
		List<BatasAmbilSks> queryResult = get("("+parameter.getWhere()+")"+dbFilter, parameter.getOrder(), iDisplayLength, iDisplayStart);
		List<String[]> aData = new ArrayList<String[]>();
		for (BatasAmbilSks batasAmbilSks : queryResult) {
			String[] batasAmbilSksString = new String[4];
			batasAmbilSksString[0] = batasAmbilSks.getIdBatasAmbilSks().toString();
			batasAmbilSksString[1] = String.valueOf(batasAmbilSks.getBatasBawahIps());
			batasAmbilSksString[2] = String.valueOf(batasAmbilSks.getBatasPengambilanSks());
			batasAmbilSksString[3] = String.valueOf(batasAmbilSks.getBatasPengambilanSks());
			aData.add(batasAmbilSksString);
		}
		rombelDatatable.setAaData(aData);
		rombelDatatable.setiTotalRecords(batasAmbilSksRepository.count(filter));
		rombelDatatable.setiTotalDisplayRecords(batasAmbilSksRepository.count("("+parameter.getWhere()+")"+dbFilter));

		return rombelDatatable;
	}
}
