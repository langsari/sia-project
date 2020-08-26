package com.bustan.siakad.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bustan.siakad.repository.MKAlihjenjangRepository;
import com.sia.modul.domain.MKAlihjenjang;

@Service
public class MKAlihjenjangServiceImpl implements MKAlihjenjangService{

	private String[] column = {"mkAlihjenjang.idMKAlihjenjang","katalog.nmKatalog","mkAlihjenjang.kodeMKAlihjenjang","mkAlihjenjang.nmMKAlihjenjang",
			"mkAlihjenjang.jumlahSKS","mkAlihjenjang.deskripsiMKAlihjenjang","mkAlihjenjang.aMKAlihjenjangTerhapus"};
	private Boolean[] searchable = {false,true,true,true,true,true,false};
	
	@Autowired
	MKAlihjenjangRepository mkAlihjenjangRepository;
	
	@Override
	public List<MKAlihjenjang> get() {
		return get("");
	}

	@Override
	public List<MKAlihjenjang> get(String where) {
		return get(where,"");
	}

	@Override
	public List<MKAlihjenjang> get(String where, String order) {
		return get(where,order,-1,-1);
	}

	@Override
	public List<MKAlihjenjang> get(String where, String order, int limit,
			int offset) {
		return mkAlihjenjangRepository.get(where,order,limit,offset);
	}

	@Override
	public MKAlihjenjang getById(UUID idMKAlihjenjang) {
		return mkAlihjenjangRepository.getById(idMKAlihjenjang);
	}

	@Override
	public String save(MKAlihjenjang mkAlihjenjang) {
		if(mkAlihjenjang.getIdMKAlihjenjang() != null)
		{
			//update
			mkAlihjenjangRepository.update(mkAlihjenjang);
			return mkAlihjenjang.getIdMKAlihjenjang().toString();
		}
		else
		{
			//insert
			return mkAlihjenjangRepository.insert(mkAlihjenjang).toString();
		}
	}

	@Override
	public String delete(UUID idMKAlihjenjang) {
		MKAlihjenjang mkAlihjenjang = mkAlihjenjangRepository.getById(idMKAlihjenjang);
		if(mkAlihjenjang==null) return null;
		else{
			mkAlihjenjang.setaMKAlihjenjangTerhapus(true);
			mkAlihjenjangRepository.update(mkAlihjenjang);
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
		List<MKAlihjenjang> queryResult = get("("+parameter.getWhere()+")"+dbFilter, parameter.getOrder(), iDisplayLength, iDisplayStart);
		List<String[]> aData = new ArrayList<String[]>();
		for (MKAlihjenjang mkAlihjenjang : queryResult) {
			String[] mkAlihjenjangString = new String[7];
			mkAlihjenjangString[0] = mkAlihjenjang.getIdMKAlihjenjang().toString();
			mkAlihjenjangString[1] = String.valueOf(mkAlihjenjang.getKatalogAlihjenjang().getNmKatalog());
			mkAlihjenjangString[2] = String.valueOf(mkAlihjenjang.getKodeMKAlihjenjang());
			mkAlihjenjangString[3] = String.valueOf(mkAlihjenjang.getNmMKAlihjenjang());
			mkAlihjenjangString[4] = String.valueOf(mkAlihjenjang.getJumlahSKS());
			mkAlihjenjangString[5] = String.valueOf(mkAlihjenjang.getDeskripsiMKAlihjenjang());
			mkAlihjenjangString[6] = String.valueOf(mkAlihjenjang.isaMKAlihjenjangTerhapus());
			aData.add(mkAlihjenjangString);
		}
		mkLuarDatatable.setAaData(aData);
		mkLuarDatatable.setiTotalRecords(mkAlihjenjangRepository.count(""));
		mkLuarDatatable.setiTotalDisplayRecords(mkAlihjenjangRepository.count("("+parameter.getWhere()+")"+dbFilter));

		return mkLuarDatatable;
	}
}
