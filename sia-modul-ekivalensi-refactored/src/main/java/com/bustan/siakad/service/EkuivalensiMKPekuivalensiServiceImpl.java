package com.bustan.siakad.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bustan.siakad.repository.EkuivalensiMKPekuivalensiRepository;
import com.bustan.siakad.repository.RelasiMKPekuivalensiRepository;
import com.sia.modul.domain.EkuivalensiMKPekuivalensi;
import com.sia.modul.domain.MK;
import com.sia.modul.domain.MKLuar;

@Service
public class EkuivalensiMKPekuivalensiServiceImpl implements EkuivalensiMKPekuivalensiService{

	private String[] column = {"mkLuar.idMKLuar","pEkuivalensi.nmProdi","mkLuar.nmMKLuar",
			"mkLuar.sks","mkLuar.aMKLuarTerhapus"};
	private Boolean[] searchable = {false,true,true,true,false};
	
	@Autowired
	EkuivalensiMKPekuivalensiRepository ekuivalensiMKPekuivalensiRepository;
	
	@Override
	public List<EkuivalensiMKPekuivalensi> get() {
		return get("");
	}

	@Override
	public List<EkuivalensiMKPekuivalensi> get(String where) {
		return get(where,"");
	}

	@Override
	public List<EkuivalensiMKPekuivalensi> get(String where, String order) {
		return get(where,order,-1,-1);
	}

	@Override
	public List<EkuivalensiMKPekuivalensi> get(String where, String order, int limit,
			int offset) {
		return ekuivalensiMKPekuivalensiRepository.get(where,order,limit,offset);
	}

	@Override
	public EkuivalensiMKPekuivalensi getById(UUID idMKLuar) {
		return ekuivalensiMKPekuivalensiRepository.getById(idMKLuar);
	}

	@Override
	public String save(EkuivalensiMKPekuivalensi ekuivalensiMKPekuivalensi) {
		if(ekuivalensiMKPekuivalensi.getIdEkuivalensiMKPekuivalensi() != null)
		{
			//update
			ekuivalensiMKPekuivalensiRepository.update(ekuivalensiMKPekuivalensi);
			return ekuivalensiMKPekuivalensi.getIdEkuivalensiMKPekuivalensi().toString();
		}
		else
		{
			//insert
			return ekuivalensiMKPekuivalensiRepository.insert(ekuivalensiMKPekuivalensi).toString();
		}
	}

	@Override
	public void delete(EkuivalensiMKPekuivalensi ekuivalensiMKPekuivalensi) {
		ekuivalensiMKPekuivalensiRepository.delete(ekuivalensiMKPekuivalensi);
	}

	@Override
	public List<MKLuar> getMKLuarDistinct(UUID idRelasiMKPekuivalensi) {
		return ekuivalensiMKPekuivalensiRepository.getMKLuarDistinct(idRelasiMKPekuivalensi);
	}

	@Override
	public List<MK> getMKDistinct(UUID idRelasiMKPekuivalensi) {
		return ekuivalensiMKPekuivalensiRepository.getMKDistinct(idRelasiMKPekuivalensi);
	}

//	@Override
//	public Datatable getdatatable(String sEcho, int iDisplayLength,
//			int iDisplayStart, int iSortCol_0, String sSortDir_0,
//			String sSearch, String filter) {
//		DatatableExtractParams parameter = new DatatableExtractParams(sSearch, this.column, this.searchable, iSortCol_0, sSortDir_0);
//		Datatable smtDatatable= new Datatable();
//		smtDatatable.setsEcho(sEcho);
//		String dbFilter = "";
//		if(filter != null && !filter.equals("")) dbFilter+=" AND "+filter; 
//		List<MKLuar> queryResult = get("("+parameter.getWhere()+")"+dbFilter, parameter.getOrder(), iDisplayLength, iDisplayStart);
//		List<String[]> aData = new ArrayList<String[]>();
//		for (MKLuar mkLuar : queryResult) {
//			String[] pedomanEkuivalensiString = new String[5];
//			pedomanEkuivalensiString[0] = mkLuar.getIdMKLuar().toString();
//			pedomanEkuivalensiString[1] = String.valueOf(mkLuar.getPedomanEkuivalensi().getNmProdi());			
//			pedomanEkuivalensiString[2] = String.valueOf(mkLuar.getNmMKLuar());
//			pedomanEkuivalensiString[3] = String.valueOf(mkLuar.getSks());
//			pedomanEkuivalensiString[4] = String.valueOf(mkLuar.isaMKLuarTerhapus());
//			aData.add(pedomanEkuivalensiString);
//		}
//		smtDatatable.setAaData(aData);
//		smtDatatable.setiTotalRecords(mkLuarRepository.count(""));
//		smtDatatable.setiTotalDisplayRecords(mkLuarRepository.count("("+parameter.getWhere()+")"+dbFilter));
//
//		return smtDatatable;
//	}

}
