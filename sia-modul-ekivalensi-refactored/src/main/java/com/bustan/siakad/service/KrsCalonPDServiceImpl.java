package com.bustan.siakad.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bustan.siakad.repository.KrsCalonPDRepository;
import com.sia.modul.domain.KrsCalonPD;

@Service
public class KrsCalonPDServiceImpl implements KrsCalonPDService{

	private String[] column = {"krsCalonPD.idKrsCalonPD","mkAlihjenjang.nmMKAlihjenjang","mkAlihjenjang.jumlahSKS", "konversiNilai.huruf", "krsCalonPD.aLulus"};
	private Boolean[] searchable = {false,true,true,true,true};
	
	@Autowired
	KrsCalonPDRepository krsCalonPDRepository;
	
	@Override
	public List<KrsCalonPD> get() {
		return get("");
	}

	@Override
	public List<KrsCalonPD> get(String where) {
		return get(where,"");
	}

	@Override
	public List<KrsCalonPD> get(String where, String order) {
		return get(where,order,-1,-1);
	}

	@Override
	public List<KrsCalonPD> get(String where, String order, int limit,
			int offset) {
		return krsCalonPDRepository.get(where,order,limit,offset);
	}

	@Override
	public KrsCalonPD getById(UUID idKrsCalonPDRepository) {
		return krsCalonPDRepository.getById(idKrsCalonPDRepository);
	}

	@Override
	public String save(KrsCalonPD krsCalonPD) {
		if(krsCalonPD.getIdKrsCalonPD() != null)
		{
			//update
			krsCalonPDRepository.update(krsCalonPD);
			return krsCalonPD.getIdKrsCalonPD().toString();
		}
		else
		{
			//insert
			return krsCalonPDRepository.insert(krsCalonPD).toString();
		}
	}

	@Override
	public String delete(UUID idKrsCalonPD) {
		KrsCalonPD calonPD = krsCalonPDRepository.getById(idKrsCalonPD);
		if(calonPD == null) return null;
		else
		{
			krsCalonPDRepository.delete(calonPD);
		}
		
		return "Ok";
	}

	@Override
	public Datatable getdatatable(String sEcho, int iDisplayLength,
			int iDisplayStart, int iSortCol_0, String sSortDir_0,
			String sSearch, String filter) {
		DatatableExtractParams parameter = new DatatableExtractParams(sSearch, this.column, this.searchable, iSortCol_0, sSortDir_0);
		Datatable smtDatatable= new Datatable();
		smtDatatable.setsEcho(sEcho);
		String dbFilter = "";
		if(filter != null && !filter.equals("")) dbFilter+=" AND "+filter; 
		List<KrsCalonPD> queryResult = get("("+parameter.getWhere()+")"+dbFilter, parameter.getOrder(), iDisplayLength, iDisplayStart);
		List<String[]> aData = new ArrayList<String[]>();
		for (KrsCalonPD krsCalonPD : queryResult) {
			String[] krsCalonPDString = new String[5];
			krsCalonPDString[0] = krsCalonPD.getIdKrsCalonPD().toString();
			krsCalonPDString[1] = String.valueOf(krsCalonPD.getMkAlihjenjang().getNmMKAlihjenjang());			
			krsCalonPDString[2] = String.valueOf(krsCalonPD.getMkAlihjenjang().getJumlahSKS());
			krsCalonPDString[3] = String.valueOf(krsCalonPD.getKonversiNilai().getHuruf());
			krsCalonPDString[4] = String.valueOf(krsCalonPD.isaLulus());
			aData.add(krsCalonPDString);
		}
		smtDatatable.setAaData(aData);
		smtDatatable.setiTotalRecords(krsCalonPDRepository.count(""));
		smtDatatable.setiTotalDisplayRecords(krsCalonPDRepository.count("("+parameter.getWhere()+")"+dbFilter));

		return smtDatatable;
	}
}
