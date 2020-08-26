package com.bustan.siakad.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bustan.siakad.repository.MKWajibPdRepository;
import com.sia.modul.domain.MKWajibPd;

@Service
public class MKWajibPdServiceImpl implements MKWajibPdService{

	private String[] column = {"mkWajibPd.idMKAlihjenjang","mk.kodeMK","mk.namaMK","mk.jumlahSKS","mkWajibPd.aMKAmbil"};
	private Boolean[] searchable = {false,true,true,true,false};
	
	@Autowired
	MKWajibPdRepository mkWajibPdRepository;
	
	@Override
	public List<MKWajibPd> get() {
		return get("");
	}

	@Override
	public List<MKWajibPd> get(String where) {
		return get(where,"");
	}

	@Override
	public List<MKWajibPd> get(String where, String order) {
		return get(where,order,-1,-1);
	}

	@Override
	public List<MKWajibPd> get(String where, String order, int limit,
			int offset) {
		return mkWajibPdRepository.get(where,order,limit,offset);
	}

	@Override
	public MKWajibPd getById(UUID idMKWajibPd) {
		return mkWajibPdRepository.getById(idMKWajibPd);
	}

	@Override
	public String save(MKWajibPd mkWajibPd) {
		if(mkWajibPd.getIdMKWajibPd() != null)
		{
			//update
			mkWajibPdRepository.update(mkWajibPd);
			return mkWajibPd.getIdMKWajibPd().toString();
		}
		else
		{
			//insert
			return mkWajibPdRepository.insert(mkWajibPd).toString();
		}
	}

	@Override
	public String delete(UUID idMKWajibPd) {
		MKWajibPd mkWajibPd = mkWajibPdRepository.getById(idMKWajibPd);
		if(mkWajibPd==null) return null;
		else{
			mkWajibPdRepository.delete(mkWajibPd);
			return "Ok";
		}
	}

	@Override
	public Datatable getdatatable(String sEcho, int iDisplayLength,
			int iDisplayStart, int iSortCol_0, String sSortDir_0,
			String sSearch, String filter) {
		DatatableExtractParams parameter = new DatatableExtractParams(sSearch, this.column, this.searchable, iSortCol_0, sSortDir_0);
		Datatable mkLuarDatatable= new Datatable();
		mkLuarDatatable.setsEcho(sEcho);
		String dbFilter = "";
		if(filter != null && !filter.equals("")) dbFilter+=" AND "+filter; 
		List<MKWajibPd> queryResult = get("("+parameter.getWhere()+")"+dbFilter, parameter.getOrder(), iDisplayLength, iDisplayStart);
		List<String[]> aData = new ArrayList<String[]>();
		for (MKWajibPd mkWajibPd : queryResult) {
			String[] mkAlihjenjangString = new String[5];
			mkAlihjenjangString[0] = mkWajibPd.getIdMKWajibPd().toString();
			mkAlihjenjangString[1] = String.valueOf(mkWajibPd.getMk().getKodeMK());
			mkAlihjenjangString[2] = String.valueOf(mkWajibPd.getMk().getNamaMK());
			mkAlihjenjangString[3] = String.valueOf(mkWajibPd.getMk().getJumlahSKS());
			mkAlihjenjangString[4] = String.valueOf(mkWajibPd.isaMKAmbil());
			aData.add(mkAlihjenjangString);
		}
		mkLuarDatatable.setAaData(aData);
		mkLuarDatatable.setiTotalRecords(mkWajibPdRepository.count(""));
		mkLuarDatatable.setiTotalDisplayRecords(mkWajibPdRepository.count("("+parameter.getWhere()+")"+dbFilter));

		return mkLuarDatatable;
	}
}
