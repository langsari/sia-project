package com.its.sia.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sia.modul.domain.StsKehadiran;
import com.sia.modul.domain.ThnAjaran;
import com.its.sia.repository.StsKehadiranRepository;
import com.its.sia.repository.ThnAjaranRepository;

@Service
public class StsKehadiranServiceImpl implements StsKehadiranService{
	
	private String[] column = {"idStsKehadiran","kodeStsKehadiran","nmStsKehadiran","aKehadiranAwal","aAbsen","aKehadiranTerhapus"};
	private Boolean[] searchable = {false,true,true,true,true,false};	
	
	@Autowired
	private StsKehadiranRepository stsKehadiranRepository;
	
	@Override
	public List<StsKehadiran> get() {
		return get("");
	}

	@Override
	public List<StsKehadiran> get(String where) {
		return get(where,"");
	}

	@Override
	public List<StsKehadiran> get(String where, String order) {
		return get(where,order,-1,-1);
	}

	@Override
	public List<StsKehadiran> get(String where, String order, int limit, int offset) {
		return stsKehadiranRepository.get(where, order, limit, offset);
	}

	@Override
	public StsKehadiran getById(UUID idThnAjaran) {
		return stsKehadiranRepository.getById(idThnAjaran);
	}
	
	@Override
	public String save(StsKehadiran stsKehadiran) {
		if(stsKehadiran.getIdStsKehadiran() != null)
		{
			//update
			stsKehadiranRepository.update(stsKehadiran);
			return stsKehadiran.getIdStsKehadiran().toString();
		}
		else
		{
			//insert
			return stsKehadiranRepository.insert(stsKehadiran).toString();
		}
	}

	@Override
	public String delete(UUID idStsKehadiran) {
		StsKehadiran stsKehadiran = stsKehadiranRepository.getById(idStsKehadiran);
		if(stsKehadiran==null) return null;
		else{
			stsKehadiran.setaKehadiranTerhapus(true);
			stsKehadiranRepository.update(stsKehadiran);
			return "Ok";
		}
	}

	@Override
	public Datatable getdatatable(String sEcho, int iDisplayLength,
			int iDisplayStart, int iSortCol_0, String sSortDir_0, String sSearch, String filter) {
		// TODO Auto-generated method stub
		DatatableExtractParams parameter = new DatatableExtractParams(sSearch, this.column, this.searchable, iSortCol_0, sSortDir_0);
		Datatable thnAjaranDatatable= new Datatable();
		thnAjaranDatatable.setsEcho(sEcho);
		String dbFilter = "";
		if(filter != null && !filter.equals("")) dbFilter+=" AND "+filter; 
		List<StsKehadiran> queryResult = get("("+parameter.getWhere()+")"+dbFilter, parameter.getOrder(), iDisplayLength, iDisplayStart);
		List<String[]> aData = new ArrayList<String[]>();
		for (StsKehadiran stsKehadiran : queryResult) {
			String[] stsKehadiranString = new String[7];
			stsKehadiranString[0] = stsKehadiran.getIdStsKehadiran().toString();
			stsKehadiranString[1] = String.valueOf(stsKehadiran.getKodeStsKehadiran());
			stsKehadiranString[2] = String.valueOf(stsKehadiran.getNmStsKehadiran());
			stsKehadiranString[3] = String.valueOf(stsKehadiran.isaKehadiranAwal());
			stsKehadiranString[4] = String.valueOf(stsKehadiran.getaAbsen());
			stsKehadiranString[5] = String.valueOf(stsKehadiran.isaKehadiranTerhapus());
			stsKehadiranString[6] = String.valueOf(stsKehadiran.isaKehadiranTerhapus());
			aData.add(stsKehadiranString);
		}
		thnAjaranDatatable.setAaData(aData);
		thnAjaranDatatable.setiTotalRecords(stsKehadiranRepository.count(filter));
		thnAjaranDatatable.setiTotalDisplayRecords(stsKehadiranRepository.count("("+parameter.getWhere()+")"+dbFilter));

		return thnAjaranDatatable;
	}

}
