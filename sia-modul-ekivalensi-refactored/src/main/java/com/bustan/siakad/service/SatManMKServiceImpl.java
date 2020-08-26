package com.bustan.siakad.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bustan.siakad.repository.SatManMKRepository;
import com.sia.modul.domain.Kurikulum;
import com.sia.modul.domain.MK;
import com.sia.modul.domain.SatMan;
import com.sia.modul.domain.SatManMK;

@Service
public class SatManMKServiceImpl implements SatManMKService{

	private String[] column = {"mk.idMK","mk.kodeMK","mk.namaMK","mk.jumlahSKS", "mk.sifatMK" };
	private Boolean[] searchable = {false,true,true,true,true,true};
	
	@Autowired
	SatManMKRepository satManMKRepository;
	
	@Override
	public List<SatManMK> get() {
		return get("");
	}

	@Override
	public List<SatManMK> get(String where) {
		return get(where,"");
	}

	@Override
	public List<SatManMK> get(String where, String order) {
		return get(where,order,-1,-1);
	}

	@Override
	public List<SatManMK> get(String where, String order, int limit,
			int offset) {
		return satManMKRepository.get(where,order,limit,offset);
	}

	@Override
	public SatManMK getById(UUID idMKAlihjenjang) {
		return satManMKRepository.getById(idMKAlihjenjang);
	}

	@Override
	public Datatable getdatatable(String sEcho, int iDisplayLength,
			int iDisplayStart, int iSortCol_0, String sSortDir_0,
			String sSearch, String filter) {
		DatatableExtractParams parameter = new DatatableExtractParams(sSearch, this.column, this.searchable, iSortCol_0, sSortDir_0);
		Datatable mkLuarDatatable= new Datatable();
		mkLuarDatatable.setsEcho(sEcho);
		String idSatMan = filter;
		List<Kurikulum> listKurikulum = satManMKRepository.getKurikulumDistinct(filter+" AND kurikulum.statusBerlaku = true");
		filter = "";
		for(int i=0;i<listKurikulum.size();i++){
//			Kurikulum kurikulum = (Kurikulum) listKurikulum.get(i)[0];
			filter += "kurikulum.idKurikulum = '"+listKurikulum.get(i).getIdKurikulum().toString()+"'";
			if(i+1 < listKurikulum.size())
				filter += " OR ";
		}
		
		String dbFilter = "";
		if(filter != null && !filter.equals("")) dbFilter+=" AND "+filter;
		List<MK> queryResult = getMKDistinct("("+parameter.getWhere()+")"+dbFilter, parameter.getOrder(), iDisplayLength, iDisplayStart);
		List<String[]> aData = new ArrayList<String[]>();
		for (MK mk : queryResult) {
			List<SatManMK> listSatManMK = get("mk.idMK = '"+mk.getIdMK()+"' AND " +idSatMan);
			String[] satManMKString = new String[6];
			satManMKString[0] = mk.getIdMK().toString();
			satManMKString[1] = String.valueOf(mk.getKodeMK());
			satManMKString[2] = String.valueOf(mk.getNamaMK());
			satManMKString[3] = String.valueOf(mk.getJumlahSKS());
//			satManMKString[4] = String.valueOf(listSatManMK.get(0).getTingkatPemb());
			satManMKString[4] = String.valueOf(mk.getSifatMK());
			aData.add(satManMKString);
		}
		mkLuarDatatable.setAaData(aData);
		mkLuarDatatable.setiTotalRecords(satManMKRepository.count(""));
		mkLuarDatatable.setiTotalDisplayRecords(satManMKRepository.count("("+parameter.getWhere()+")"+dbFilter));
//
		return mkLuarDatatable;
	}

	@Override
	public List<Kurikulum> getKurikulumDistinct(String where) {
		return satManMKRepository.getKurikulumDistinct(where);
	}

	@Override
	public List<MK> getMKDistinct(String where, String order, int limit,
			int offset) {
		return satManMKRepository.getMKDistinct(where,order,limit,offset);
	}
//
//	@Override
//	public List<Object[]> getMKDistinct() {
//		return getMKDistinct("");
//	}
//
//	@Override
//	public List<Object[]> getMKDistinct(String where) {
//		return getMKDistinct(where,"");
//	}

	@Override
	public List<SatMan> getSatManDistinct(String where) {
		// TODO Auto-generated method stub
		return satManMKRepository.getSatManDistinct(where);
	}

//	@Override
//	public List<Object[]> getMKDistinct(String where, String order) {
//		return satManMKRepository.getMKDistinct(where, order, -1, -1);
//	}
}
