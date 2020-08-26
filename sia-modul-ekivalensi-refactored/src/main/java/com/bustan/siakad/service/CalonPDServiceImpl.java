package com.bustan.siakad.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bustan.siakad.repository.CalonPDRepository;
import com.sia.modul.domain.CalonPD;

@Service
public class CalonPDServiceImpl implements CalonPDService{

	private String[] column = {"calonPD.idCalonPD","satMan.nmSatMan","calonPD.nmCalonPD", "calonPD.ptAsal", "katalog.nmKatalog","calonPD.aEkuivalensi"};
	private Boolean[] searchable = {false,true,true,true,true,true};
	
	@Autowired
	CalonPDRepository calonPDRepository;
	
	@Override
	public List<CalonPD> get() {
		return get("");
	}

	@Override
	public List<CalonPD> get(String where) {
		return get(where,"");
	}

	@Override
	public List<CalonPD> get(String where, String order) {
		return get(where,order,-1,-1);
	}

	@Override
	public List<CalonPD> get(String where, String order, int limit,
			int offset) {
		return calonPDRepository.get(where,order,limit,offset);
	}

	@Override
	public CalonPD getById(UUID idCalonPD) {
		return calonPDRepository.getById(idCalonPD);
	}

	@Override
	public String save(CalonPD calonPD) {
		if(calonPD.getIdCalonPD() != null)
		{
			//update
			calonPDRepository.update(calonPD);
			return calonPD.getIdCalonPD().toString();
		}
		else
		{
			//insert
			return calonPDRepository.insert(calonPD).toString();
		}
	}

	@Override
	public String delete(UUID idCalonPD) {
		CalonPD calonPD = calonPDRepository.getById(idCalonPD);
		if(calonPD == null) return null;
		else
		{
			calonPDRepository.delete(calonPD);
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
		List<CalonPD> queryResult = get("("+parameter.getWhere()+")"+dbFilter, parameter.getOrder(), iDisplayLength, iDisplayStart);
		List<String[]> aData = new ArrayList<String[]>();
		for (CalonPD calonPD : queryResult) {
			String[] calonPDString = new String[6];
			calonPDString[0] = calonPD.getIdCalonPD().toString();
			calonPDString[1] = String.valueOf(calonPD.getSatMan().getNmSatMan());			
			calonPDString[2] = String.valueOf(calonPD.getNmCalonPD());
			calonPDString[3] = String.valueOf(calonPD.getPtAsal());
			if(calonPD.getKatalogAlihjenjang() != null)
			{
				calonPDString[4] = String.valueOf(calonPD.getKatalogAlihjenjang().getNmKatalog());
			}
			else
			{
				calonPDString[4] = "";
			}			
			calonPDString[5] = String.valueOf(calonPD.isaEkuivalensi());
			aData.add(calonPDString);
		}
		smtDatatable.setAaData(aData);
		smtDatatable.setiTotalRecords(calonPDRepository.count(""));
		smtDatatable.setiTotalDisplayRecords(calonPDRepository.count("("+parameter.getWhere()+")"+dbFilter));

		return smtDatatable;
	}

}
