package com.bustan.siakad.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bustan.siakad.repository.RelasiMKAlihjenjangRepository;
import com.sia.modul.domain.KatalogAlihjenjang;
import com.sia.modul.domain.Kurikulum;
import com.sia.modul.domain.RelasiMKAlihjenjang;

@Service
public class RelasiMKAlihjenjangServiceImpl implements RelasiMKAlihjenjangService{

	private String[] column = {"katalog.nmKatalog","kurikulum.namaKurikulum"};
	private Boolean[] searchable = {true,true};	
	
	@Autowired
	RelasiMKAlihjenjangRepository relasiMKAlihjenjangRepository;
	
	@Override
	public List<RelasiMKAlihjenjang> get() {
		return get("");
	}

	@Override
	public List<RelasiMKAlihjenjang> get(String where) {
		return get(where,"");
	}

	@Override
	public List<RelasiMKAlihjenjang> get(String where, String order) {
		return get(where,order,-1,-1);
	}

	@Override
	public List<RelasiMKAlihjenjang> get(String where, String order, int limit,
			int offset) {
		return relasiMKAlihjenjangRepository.get(where,order,limit,offset);
	}

	@Override
	public RelasiMKAlihjenjang getById(UUID idRelasiMKAlihjenjang) {
		return relasiMKAlihjenjangRepository.getById(idRelasiMKAlihjenjang);
	}

	@Override
	public String save(RelasiMKAlihjenjang relasiMKAlihjenjang) {
		if(relasiMKAlihjenjang.getIdRelasiMKAlihjenjang() != null)
		{
			//update
			relasiMKAlihjenjangRepository.update(relasiMKAlihjenjang);
			return relasiMKAlihjenjang.getIdRelasiMKAlihjenjang().toString();
		}
		else
		{
			//insert
			return relasiMKAlihjenjangRepository.insert(relasiMKAlihjenjang).toString();
		}
	}

	@Override
	public String delete(UUID idRelasiMKAlihjenjang) {
		RelasiMKAlihjenjang relasiMKAlihjenjang = relasiMKAlihjenjangRepository.getById(idRelasiMKAlihjenjang);
		if(relasiMKAlihjenjang == null) return null;
		relasiMKAlihjenjangRepository.delete(relasiMKAlihjenjang);
		return "ok";
	}

	@Override
	public List<Object[]> getRelasi(String where, String order, int limit,
			int offset) {
		return relasiMKAlihjenjangRepository.getRelasi(where,order,limit,offset);
	}
	
	@Override
	public Datatable getdatatable(String sEcho, int iDisplayLength,
			int iDisplayStart, int iSortCol_0, String sSortDir_0,
			String sSearch, String filter) {
		DatatableExtractParams parameter = new DatatableExtractParams(sSearch, this.column, this.searchable, iSortCol_0, sSortDir_0);
		Datatable relasiEkuivalensiDatatable= new Datatable();
		relasiEkuivalensiDatatable.setsEcho(sEcho);
		String dbFilter = "";
		if(filter != null && !filter.equals("")) dbFilter+=" AND "+filter; 
//		List<RelasiEkuivalensi> queryResult = get("("+parameter.getWhere()+")"+dbFilter, parameter.getOrder(), dbGroupby, iDisplayLength, iDisplayStart);
//		List<String[]> aData = new ArrayList<String[]>();
//		for (RelasiEkuivalensi relasiEkuivalensi : queryResult) {
//			String[] calonPDString = new String[2];
//			calonPDString[0] = String.valueOf(relasiEkuivalensi.getKurikulumLama().getNamaKurikulum());			
//			calonPDString[1] = String.valueOf(relasiEkuivalensi.getKurikulumBaru().getNamaKurikulum());			
//			aData.add(calonPDString);
//		}
		List<Object[]> query = getRelasi("("+parameter.getWhere()+")"+dbFilter, parameter.getOrder(), iDisplayLength, iDisplayStart);
		
		List<String[]> aData = new ArrayList<String[]>();
		
		for (Object[] uuid : query) {
			String[] calonPDString = new String[3];
			KatalogAlihjenjang a = (KatalogAlihjenjang)uuid[0];
			Kurikulum b = (Kurikulum)uuid[1];
			calonPDString[0] = a.getIdKatalogAlihjenjang().toString()+";"+b.getIdKurikulum().toString();				
			calonPDString[1] = a.getNmKatalog();			
			calonPDString[2] = b.getNamaKurikulum();			
			aData.add(calonPDString);
		}
		relasiEkuivalensiDatatable.setAaData(aData);
		relasiEkuivalensiDatatable.setiTotalRecords(relasiMKAlihjenjangRepository.count(filter));
		relasiEkuivalensiDatatable.setiTotalDisplayRecords(relasiMKAlihjenjangRepository.count("("+parameter.getWhere()+")"+dbFilter));
//		relasiEkuivalensiDatatable.setiTotalRecords(2);
//		relasiEkuivalensiDatatable.setiTotalDisplayRecords(3);

		return relasiEkuivalensiDatatable;
	}
}
