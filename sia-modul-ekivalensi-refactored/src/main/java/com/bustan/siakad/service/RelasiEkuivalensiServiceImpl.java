package com.bustan.siakad.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bustan.siakad.repository.RelasiEkuivalensiRepository;
import com.sia.modul.domain.Kurikulum;
import com.sia.modul.domain.MKWajibCalonPD;
import com.sia.modul.domain.RelasiEkuivalensi;

@Service
public class RelasiEkuivalensiServiceImpl implements RelasiEkuivalensiService{

	private String[] column = {"kurikulumLama.namaKurikulum","kurikulumBaru.namaKurikulum"};
	private Boolean[] searchable = {true,true};	
	
	@Autowired
	RelasiEkuivalensiRepository relasiEkuivalensiRepository;
	
	@Override
	public List<RelasiEkuivalensi> get() {
		return get("");
	}

	@Override
	public List<RelasiEkuivalensi> get(String where) {
		return get(where,"");
	}

	@Override
	public List<RelasiEkuivalensi> get(String where, String order) {
		return get(where,order,-1,-1);
	}

	@Override
	public List<RelasiEkuivalensi> get(String where, String order, int limit,
			int offset) {
		return relasiEkuivalensiRepository.get(where,order,"",limit,offset);
	}

	@Override
	public List<RelasiEkuivalensi> get(String where, String order, String groupby, int limit,
			int offset) {
		return relasiEkuivalensiRepository.get(where,order,groupby,limit,offset);
	}
	
	@Override
	public RelasiEkuivalensi getById(UUID idRelasiEkuivalensi) {
		return relasiEkuivalensiRepository.getById(idRelasiEkuivalensi);
	}

	@Override
	public String save(RelasiEkuivalensi ekuivalensiMK) {
		if(ekuivalensiMK.getIdRelasiEkuivalensi() != null)
		{
			//update
			relasiEkuivalensiRepository.update(ekuivalensiMK);
			return ekuivalensiMK.getIdRelasiEkuivalensi().toString();
		}
		else
		{
			//insert
			return relasiEkuivalensiRepository.insert(ekuivalensiMK).toString();
		}
	}

	@Override
	public String delete(UUID idRelasiEkuivalensi) {
		RelasiEkuivalensi ekuivalensiMK = relasiEkuivalensiRepository.getById(idRelasiEkuivalensi);
		if(ekuivalensiMK == null) return null;
		else
		{
			relasiEkuivalensiRepository.delete(ekuivalensiMK);
		}
		
		return "Ok";
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
		List<Object[]> query = getA("("+parameter.getWhere()+")"+dbFilter, parameter.getOrder(), iDisplayLength, iDisplayStart);
		
		List<String[]> aData = new ArrayList<String[]>();
		
		for (Object[] uuid : query) {
			String[] calonPDString = new String[3];
			Kurikulum a = (Kurikulum)uuid[0];
			Kurikulum b = (Kurikulum)uuid[1];
			calonPDString[0] = a.getIdKurikulum().toString()+";"+b.getIdKurikulum().toString();				
			calonPDString[1] = a.getNamaKurikulum();			
			calonPDString[2] = b.getNamaKurikulum();			
			aData.add(calonPDString);
		}
		relasiEkuivalensiDatatable.setAaData(aData);
		relasiEkuivalensiDatatable.setiTotalRecords(relasiEkuivalensiRepository.count(filter));
		relasiEkuivalensiDatatable.setiTotalDisplayRecords(relasiEkuivalensiRepository.count("("+parameter.getWhere()+")"+dbFilter));
//		relasiEkuivalensiDatatable.setiTotalRecords(2);
//		relasiEkuivalensiDatatable.setiTotalDisplayRecords(3);

		return relasiEkuivalensiDatatable;
	}

	@Override
	public List<Object[]> getUUID(String where, String order, int limit, int offset) {
		// TODO Auto-generated method stub
		return relasiEkuivalensiRepository.getUUID(where, order, limit, offset);
	}

	@Override
	public List<Object[]> getA(String where, String order, int limit, int offset) {
		// TODO Auto-generated method stub
		return relasiEkuivalensiRepository.getA(where, order, limit, offset);
	}
	
}
