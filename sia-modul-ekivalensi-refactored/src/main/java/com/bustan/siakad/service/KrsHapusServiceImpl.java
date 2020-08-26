package com.bustan.siakad.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bustan.siakad.repository.KrsHapusRepository;
import com.sia.modul.domain.KrsHapus;

@Service
public class KrsHapusServiceImpl implements KrsHapusService{

	private String[] column = {"krsHapus.idKrsHapus","mk.kodeMK","mk.kodeMK","konversiNilai.huruf","krsHapus.aKrshapus"};
	private Boolean[] searchable = {false,true,true,true,false};
	
	@Autowired
	KrsHapusRepository krsHapusRepository;
	
	@Override
	public List<KrsHapus> get() {
		return get("");
	}

	@Override
	public List<KrsHapus> get(String where) {
		return get(where,"");
	}

	@Override
	public List<KrsHapus> get(String where, String order) {
		return get(where,order,-1,-1);
	}

	@Override
	public List<KrsHapus> get(String where, String order, int limit,
			int offset) {
		return krsHapusRepository.get(where,order,limit,offset);
	}

	@Override
	public KrsHapus getById(UUID idKrsHapus) {
		return krsHapusRepository.getById(idKrsHapus);
	}

	@Override
	public String save(KrsHapus krsHapus) {
		if(krsHapus.getIdKrsHapus() != null)
		{
			//update
			krsHapusRepository.update(krsHapus);
			return krsHapus.getIdKrsHapus().toString();
		}
		else
		{
			//insert
			return krsHapusRepository.insert(krsHapus).toString();
		}
	}

	@Override
	public String delete(UUID idKrsHapus) {
		KrsHapus krsHapus = krsHapusRepository.getById(idKrsHapus);
		if(krsHapus==null) return null;
		else{
			krsHapus.setaKrsHapus(true);
			krsHapusRepository.update(krsHapus);
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
		List<KrsHapus> queryResult = get("("+parameter.getWhere()+")"+dbFilter, parameter.getOrder(), iDisplayLength, iDisplayStart);
		List<String[]> aData = new ArrayList<String[]>();
		for (KrsHapus krsHapus : queryResult) {
			String[] mkAlihjenjangString = new String[7];
			mkAlihjenjangString[0] = krsHapus.getIdKrsHapus().toString();
			mkAlihjenjangString[1] = String.valueOf(krsHapus.getKrs().getPemb().getMk().getKodeMK());
			mkAlihjenjangString[2] = String.valueOf(krsHapus.getKrs().getPemb().getMk().getNamaMK());
			mkAlihjenjangString[3] = String.valueOf(krsHapus.getKrs().getKonversiNilai().getHuruf());
			mkAlihjenjangString[4] = String.valueOf(krsHapus.isaKrsHapus());
			aData.add(mkAlihjenjangString);
		}
		mkLuarDatatable.setAaData(aData);
		mkLuarDatatable.setiTotalRecords(krsHapusRepository.count(""));
		mkLuarDatatable.setiTotalDisplayRecords(krsHapusRepository.count("("+parameter.getWhere()+")"+dbFilter));

		return mkLuarDatatable;
	}
}
