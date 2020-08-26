package com.its.sia.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sia.modul.domain.Ptk;
import com.sia.modul.domain.Rombel;
import com.its.sia.repository.RombelRepository;

@Service
public class RombelServiceImpl implements RombelService {

	private String[] column = {"rombel.idRombel","rombel.nmRombel","rombel.tglBuatRombel","rombel.aRombelTerhapus"};
	private Boolean[] searchable = {false,true,true,false};	

	@Autowired
	private RombelRepository rombelRepository;
	
	@Override
	public List<Rombel> get() {
		return get("");
	}

	@Override
	public List<Rombel> get(String where) {
		return get(where,"");
	}

	@Override
	public List<Rombel> get(String where, String order) {
		return get(where,order,-1,-1);
	}

	@Override
	public List<Rombel> get(String where, String order, int limit, int offset) {
		return rombelRepository.get(where, order, limit, offset);
	}

	@Override
	public Rombel getById(UUID idSmt) {
		return rombelRepository.getById(idSmt);
	}

	@Override
	public String save(Rombel rombel) {
		String where ="";
		if(rombel.getIdRombel()!=null) where =" AND rombel.idRombel!='"+rombel.getIdRombel()+"'";
		List<Rombel> queryResult = get("rombel.nmRombel = '"+rombel.getNmRombel()+"' and satMan.idSatMan = '"+rombel.getSatMan().getIdSatMan()+"'"+where);
		if(queryResult.size()>0) return null;
		else if(rombel.getIdRombel() != null)
		{
			//update
			rombelRepository.update(rombel);
			return rombel.getIdRombel().toString();
		}
		else
		{
			//insert
			return rombelRepository.insert(rombel).toString();
		}
	}

	@Override
	public String delete(UUID idSmt) {
		Rombel rombel = rombelRepository.getById(idSmt);
		if(rombel==null) return null;
		else{
			rombel.setaRombelTerhapus(true);
			rombelRepository.update(rombel);
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
		List<Rombel> queryResult = get("("+parameter.getWhere()+")"+dbFilter, parameter.getOrder(), iDisplayLength, iDisplayStart);
		List<String[]> aData = new ArrayList<String[]>();
		for (Rombel rombel : queryResult) {
			String[] rombelString = new String[6];
			rombelString[0] = rombel.getIdRombel().toString();
			rombelString[1] = String.valueOf(rombel.getNmRombel());
			rombelString[2] = String.valueOf(rombel.getTglBuatRombel());
			rombelString[3] = String.valueOf(rombel.isaRombelTerhapus());
			rombelString[4] = String.valueOf(rombel.isaRombelTerhapus());
			aData.add(rombelString);
		}
		rombelDatatable.setAaData(aData);
		rombelDatatable.setiTotalRecords(rombelRepository.count(filter));
		rombelDatatable.setiTotalDisplayRecords(rombelRepository.count("("+parameter.getWhere()+")"+dbFilter));

		return rombelDatatable;
	}
}
