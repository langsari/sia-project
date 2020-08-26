package com.its.sia.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sia.modul.domain.AnggotaRombel;
import com.sia.modul.domain.PertemuanPembelajaran;
import com.sia.modul.domain.PresensiPd;
import com.sia.modul.domain.Rombel;
import com.its.sia.repository.AnggotaRombelRepository;
import com.its.sia.repository.PresensiPdRepository;
import com.its.sia.repository.RombelRepository;

@Service
public class PresensiPdServiceImpl implements PresensiPdService {

	private String[] column = {"presensiPd.idPresensiPd","pertemuanPembelajaran.pertemuan","pd.nimPd","pd.nmPd","pd.angkatanPd","mk.namaMK","pemb.nmPemb"};
	private Boolean[] searchable = {false,true,true,true,true,true,true};	

	@Autowired
	private PresensiPdRepository presensiPdRepository;
	
	@Override
	public List<PresensiPd> get() {
		return get("");
	}

	@Override
	public List<PresensiPd> get(String where) {
		return get(where,"");
	}

	@Override
	public List<PresensiPd> get(String where, String order) {
		return get(where,order,-1,-1);
	}

	@Override
	public List<PresensiPd> get(String where, String order, int limit, int offset) {
		return presensiPdRepository.get(where, order, limit, offset);
	}

	@Override
	public PresensiPd getById(UUID idPresensiPd) {
		return presensiPdRepository.getById(idPresensiPd);
	}

	@Override
	public String save(PresensiPd presensiPd) {
		String where ="";
		if(presensiPd.getIdPresensiPd()!=null) where+="presensiPd.idPresensiPd !='"+presensiPd.getIdPresensiPd()+"' and ";
		List<PresensiPd> listPresensiPd = get(where+"pertemuanPembelajaran.pertemuan = "+presensiPd.getPertemuanPembelajaran().getPertemuan()+" and "
				+ "pemb.idPemb ='"+presensiPd.getPertemuanPembelajaran().getPemb().getIdPemb()+"' and pd.idPd='"+presensiPd.getPd().getIdPd()+"' ");
		if(listPresensiPd.size()>0)
		{
			return null;
		}
		else if(presensiPd.getIdPresensiPd() != null)
		{
			//update
			presensiPdRepository.update(presensiPd);
			return presensiPd.getIdPresensiPd().toString();
		}
		else
		{
			//insert
			return presensiPdRepository.insert(presensiPd).toString();
		}
	}

	@Override
	public String delete(UUID idPresensiPd) {
		PresensiPd presensiPd = presensiPdRepository.getById(idPresensiPd);
		if(presensiPd==null) return null;
		else{
			presensiPdRepository.delete(presensiPd);
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
		List<PresensiPd> queryResult = get("("+parameter.getWhere()+")"+dbFilter, parameter.getOrder(), iDisplayLength, iDisplayStart);
		List<String[]> aData = new ArrayList<String[]>();
		for (PresensiPd presensiPd : queryResult) {
			String[] presensiPdString = new String[7];
			presensiPdString[0] = presensiPd.getIdPresensiPd().toString();
			presensiPdString[1] = String.valueOf(presensiPd.getPertemuanPembelajaran().getPertemuan());
			presensiPdString[2] = String.valueOf(presensiPd.getPd().getNimPd());
			presensiPdString[3] = String.valueOf(presensiPd.getPd().getNmPd());
			presensiPdString[4] = String.valueOf(presensiPd.getPd().getAngkatanPd());
			presensiPdString[5] = String.valueOf(presensiPd.getPertemuanPembelajaran().getPemb().getMk().getNamaMK());
			presensiPdString[6] = String.valueOf(presensiPd.getPertemuanPembelajaran().getPemb().getNmPemb());
			aData.add(presensiPdString);
		}
		rombelDatatable.setAaData(aData);
		rombelDatatable.setiTotalRecords(presensiPdRepository.count(filter));
		rombelDatatable.setiTotalDisplayRecords(presensiPdRepository.count("("+parameter.getWhere()+")"+dbFilter));

		return rombelDatatable;
	}
}
