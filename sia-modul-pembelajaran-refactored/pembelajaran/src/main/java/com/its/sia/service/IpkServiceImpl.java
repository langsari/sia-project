package com.its.sia.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sia.modul.domain.Ipk;
import com.sia.modul.domain.Pd;
import com.its.sia.repository.IpkRepository;

@Service
public class IpkServiceImpl implements IpkService {

	private String[] column = {"Ipk.idIpk","pd.nimPd","pd.nmPd","pd.angkatanPd","Ipk.nilaiIpk"};
	private Boolean[] searchable = {false,true,true,true,true};	

	@Autowired
	private IpkRepository IpkRepository;
	
	@Override
	public List<Ipk> get() {
		return get("");
	}

	@Override
	public List<Ipk> get(String where) {
		return get(where,"");
	}

	@Override
	public List<Ipk> get(String where, String order) {
		return get(where,order,-1,-1);
	}

	@Override
	public List<Ipk> get(String where, String order, int limit, int offset) {
		return IpkRepository.get(where, order, limit, offset);
	}

	@Override
	public Ipk getById(UUID idipk) {
		return IpkRepository.getById(idipk);
	}

	@Override
	public Ipk getByPd(Pd pd) {
		return IpkRepository.getByPd(pd);
	}

	@Override
	public String save(Ipk Ipk) {
		String where = "";
		if(Ipk.getIdIpk() != null)
		{
			//update
			IpkRepository.update(Ipk);
			return Ipk.getIdIpk().toString();
		}
		else
		{
			//insert
			return IpkRepository.insert(Ipk).toString();
		}
	}

	@Override
	public String delete(UUID idIpk) {
		Ipk Ipk = IpkRepository.getById(idIpk);
		if(Ipk==null) return null;
		else{
			IpkRepository.delete(Ipk);
			return "Ok";
		}
	}

	@Override
	public Datatable getdatatable(String sEcho, int iDisplayLength,
			int iDisplayStart, int iSortCol_0, String sSortDir_0,
			String sSearch, String filter) {
		DatatableExtractParams parameter = new DatatableExtractParams(sSearch, this.column, this.searchable, iSortCol_0, sSortDir_0);
		Datatable rombelDatatable= new Datatable();
		rombelDatatable.setsEcho(sEcho);
		String dbFilter = "";
		if(filter != null && !filter.equals("")) dbFilter+=" AND "+filter; 
		List<Ipk> queryResult = get("("+parameter.getWhere()+")"+dbFilter, parameter.getOrder(), iDisplayLength, iDisplayStart);
		List<String[]> aData = new ArrayList<String[]>();
		for (Ipk Ipk : queryResult) {
			String[] ipkString = new String[5];
			ipkString[0] = Ipk.getIdIpk().toString();
			ipkString[1] = String.valueOf(Ipk.getPd().getNimPd());
			ipkString[2] = String.valueOf(Ipk.getPd().getNmPd());
			ipkString[3] = String.valueOf(Ipk.getPd().getAngkatanPd());
			ipkString[4] = String.valueOf(Ipk.getNilaiIpk());
			aData.add(ipkString);
		}
		rombelDatatable.setAaData(aData);
		rombelDatatable.setiTotalRecords(IpkRepository.count(filter));
		rombelDatatable.setiTotalDisplayRecords(IpkRepository.count("("+parameter.getWhere()+")"+dbFilter));

		return rombelDatatable;
	}
}
