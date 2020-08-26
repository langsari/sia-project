package com.bustan.siakad.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bustan.siakad.repository.AlihJenjangMKTerakuiRepository;
import com.bustan.siakad.repository.CalonPDRepository;
import com.bustan.siakad.repository.KrsCalonPDRepository;
import com.sia.modul.domain.AlihJenjangMKTerakui;

@Service
public class AlihJenjangMKTerakuiServiceImpl implements AlihJenjangMKTerakuiService{

	private String[] column = {"alihJenjangMKTerakui.idAlihJenjangMKTerakui","calonPD.nmCalonPD","mk.namaMK", "mk.jumlahSKS"};
	private Boolean[] searchable = {false,true,true,true};
	
	@Autowired
	AlihJenjangMKTerakuiRepository alihJenjangMKTerakuiRepository;
	
	@Override
	public List<AlihJenjangMKTerakui> get() {
		return get("");
	}

	@Override
	public List<AlihJenjangMKTerakui> get(String where) {
		return get(where,"");
	}

	@Override
	public List<AlihJenjangMKTerakui> get(String where, String order) {
		return get(where,order,-1,-1);
	}

	@Override
	public List<AlihJenjangMKTerakui> get(String where, String order, int limit,
			int offset) {
		return alihJenjangMKTerakuiRepository.get(where,order,limit,offset);
	}

	@Override
	public AlihJenjangMKTerakui getById(UUID idAlihJenjangMKTerakui) {
		return alihJenjangMKTerakuiRepository.getById(idAlihJenjangMKTerakui);
	}

	@Override
	public String save(AlihJenjangMKTerakui alihJenjangMKTerakui) {
		if(alihJenjangMKTerakui.getIdAlihJenjangMKTerakui() != null)
		{
			//update
			alihJenjangMKTerakuiRepository.update(alihJenjangMKTerakui);
			return alihJenjangMKTerakui.getIdAlihJenjangMKTerakui().toString();
		}
		else
		{
			//insert
			return alihJenjangMKTerakuiRepository.insert(alihJenjangMKTerakui).toString();
		}
	}

	@Override
	public String delete(UUID idAlihJenjangMKTerakui) {
		AlihJenjangMKTerakui alihJenjangMKTerakui = alihJenjangMKTerakuiRepository.getById(idAlihJenjangMKTerakui);
		if(alihJenjangMKTerakui == null) return null;
		else
		{
			alihJenjangMKTerakuiRepository.delete(alihJenjangMKTerakui);
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
		List<AlihJenjangMKTerakui> queryResult = get("("+parameter.getWhere()+")"+dbFilter, parameter.getOrder(), iDisplayLength, iDisplayStart);
		List<String[]> aData = new ArrayList<String[]>();
		for (AlihJenjangMKTerakui alihJenjangMKTerakui : queryResult) {
			String[] alihJenjangMKTerakuiString = new String[4];
			alihJenjangMKTerakuiString[0] = alihJenjangMKTerakui.getIdAlihJenjangMKTerakui().toString();
			alihJenjangMKTerakuiString[1] = String.valueOf(alihJenjangMKTerakui.getCalonPD().getNmCalonPD());			
			alihJenjangMKTerakuiString[2] = String.valueOf(alihJenjangMKTerakui.getMk().getNamaMK());
			alihJenjangMKTerakuiString[3] = String.valueOf(alihJenjangMKTerakui.getMk().getJumlahSKS());
			aData.add(alihJenjangMKTerakuiString);
		}
		smtDatatable.setAaData(aData);
		smtDatatable.setiTotalRecords(alihJenjangMKTerakuiRepository.count(""));
		smtDatatable.setiTotalDisplayRecords(alihJenjangMKTerakuiRepository.count("("+parameter.getWhere()+")"+dbFilter));

		return smtDatatable;
	}

}
