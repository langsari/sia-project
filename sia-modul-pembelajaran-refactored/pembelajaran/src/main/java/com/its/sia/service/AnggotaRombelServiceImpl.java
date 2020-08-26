package com.its.sia.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sia.modul.domain.AnggotaRombel;
import com.sia.modul.domain.Rombel;
import com.its.sia.repository.AnggotaRombelRepository;
import com.its.sia.repository.RombelRepository;

@Service
public class AnggotaRombelServiceImpl implements AnggotaRombelService {

	private String[] column = {"anggotaRombel.idAnggotaRombel","pd.nimPd","pd.nmPd","pd.angkatanPd"};
	private Boolean[] searchable = {false,true,true,true,false};	

	@Autowired
	private AnggotaRombelRepository anggotaRombelRepository;
	
	@Override
	public List<AnggotaRombel> get() {
		return get("");
	}

	@Override
	public List<AnggotaRombel> get(String where) {
		return get(where,"");
	}

	@Override
	public List<AnggotaRombel> get(String where, String order) {
		return get(where,order,-1,-1);
	}

	@Override
	public List<AnggotaRombel> get(String where, String order, int limit, int offset) {
		return anggotaRombelRepository.get(where, order, limit, offset);
	}

	@Override
	public AnggotaRombel getById(UUID idAnggotaRombel) {
		return anggotaRombelRepository.getById(idAnggotaRombel);
	}

	@Override
	public String save(AnggotaRombel anggotaRombel) {
		List<AnggotaRombel> listAnggotaRombel =  get("pd.idPd ='"+anggotaRombel.getPd().getIdPd()+"' AND rombel.idRombel = '"+anggotaRombel.getRombel().getIdRombel()+"'");
		if(listAnggotaRombel.size()>0)
		{
			return null;
		}
		else if(anggotaRombel.getIdAnggotaRombel() != null)
		{
			//update
			anggotaRombelRepository.update(anggotaRombel);
			return anggotaRombel.getIdAnggotaRombel().toString();
		}
		else
		{
			//insert
			return anggotaRombelRepository.insert(anggotaRombel).toString();
		}
	}

	@Override
	public String delete(UUID idAnggotaRombel) {
		AnggotaRombel anggotaRombel = anggotaRombelRepository.getById(idAnggotaRombel);
		if(anggotaRombel==null) return null;
		else{
			anggotaRombelRepository.delete(anggotaRombel);
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
		List<AnggotaRombel> queryResult = get("("+parameter.getWhere()+")"+dbFilter, parameter.getOrder(), iDisplayLength, iDisplayStart);
		List<String[]> aData = new ArrayList<String[]>();
		for (AnggotaRombel anggotaRombel : queryResult) {
			String[] anggotaRombelString = new String[5];
			anggotaRombelString[0] = anggotaRombel.getIdAnggotaRombel().toString();
			anggotaRombelString[1] = String.valueOf(anggotaRombel.getPd().getNimPd());
			anggotaRombelString[2] = String.valueOf(anggotaRombel.getPd().getNmPd());
			anggotaRombelString[3] = String.valueOf(anggotaRombel.getPd().getAngkatanPd());
			anggotaRombelString[4] = String.valueOf(anggotaRombel.getIdAnggotaRombel());
			aData.add(anggotaRombelString);
		}
		rombelDatatable.setAaData(aData);
		rombelDatatable.setiTotalRecords(anggotaRombelRepository.count(filter));
		rombelDatatable.setiTotalDisplayRecords(anggotaRombelRepository.count("("+parameter.getWhere()+") AND "+filter));

		return rombelDatatable;
	}

	@Override
	public Long countAnggota(String where) {
		return anggotaRombelRepository.count(where);
	}
}
