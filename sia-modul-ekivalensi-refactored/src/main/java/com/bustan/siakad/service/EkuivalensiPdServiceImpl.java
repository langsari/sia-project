package com.bustan.siakad.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bustan.siakad.repository.EkuivalensiPdRepository;
import com.sia.modul.domain.EkuivalensiPd;

@Service
public class EkuivalensiPdServiceImpl implements EkuivalensiPdService{

	private String[] column = {"ekuivalensiPd.idEkuivalensiPd","pd.nimPd","pd.nmPd","ptk.nmPtk",
			"kurikulumLama.namaKurikulum","kurikulumBaru.namaKurikulum","ekuivalensiPd.tglPembuatan", "kurikulumLama.idKurikulum", "kurikulumBaru.idKurikulum", "pd.idPd", "ekuivalensiPd.aEkuivalensi"};
	private Boolean[] searchable = {false,true,true,true,true,true,false,false,false,false,false};
	
	@Autowired
	EkuivalensiPdRepository ekuivalensiPdRepository;
	
	@Override
	public List<EkuivalensiPd> get() {
		return get("");
	}

	@Override
	public List<EkuivalensiPd> get(String where) {
		return get(where,"");
	}

	@Override
	public List<EkuivalensiPd> get(String where, String order) {
		return get(where,order,-1,-1);
	}

	@Override
	public List<EkuivalensiPd> get(String where, String order, int limit,
			int offset) {
		return ekuivalensiPdRepository.get(where,order,limit,offset);
	}

	@Override
	public EkuivalensiPd getById(UUID idEkuivalensiPd) {
		return ekuivalensiPdRepository.getById(idEkuivalensiPd);
	}

	@Override
	public String save(EkuivalensiPd ekuivalensiPd) {
		if(ekuivalensiPd.getIdEkuivalensiPd() != null)
		{
			//update
			ekuivalensiPdRepository.update(ekuivalensiPd);
			return ekuivalensiPd.getIdEkuivalensiPd().toString();
		}
		else
		{
			//insert
			return ekuivalensiPdRepository.insert(ekuivalensiPd).toString();
		}
	}

	@Override
	public String delete(UUID idEkuivalensiPd) {
		EkuivalensiPd ekuivalensiPd = ekuivalensiPdRepository.getById(idEkuivalensiPd);
		if(ekuivalensiPd==null) return null;
		else{
			ekuivalensiPdRepository.delete(ekuivalensiPd);
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
		List<EkuivalensiPd> queryResult = get("("+parameter.getWhere()+")"+dbFilter, parameter.getOrder(), iDisplayLength, iDisplayStart);
		List<String[]> aData = new ArrayList<String[]>();
		for (EkuivalensiPd ekuivalensiPd : queryResult) {
			String[] mkAlihjenjangString = new String[11];
			mkAlihjenjangString[0] = ekuivalensiPd.getIdEkuivalensiPd().toString();
			mkAlihjenjangString[1] = String.valueOf(ekuivalensiPd.getPd().getNimPd());
			mkAlihjenjangString[2] = String.valueOf(ekuivalensiPd.getPd().getNmPd());
			mkAlihjenjangString[3] = String.valueOf(ekuivalensiPd.getPtk().getNmPtk());
			mkAlihjenjangString[4] = String.valueOf(ekuivalensiPd.getKurikulumLama().getNamaKurikulum());
			mkAlihjenjangString[5] = String.valueOf(ekuivalensiPd.getKurikulumBaru().getNamaKurikulum());
			mkAlihjenjangString[6] = String.valueOf(ekuivalensiPd.getTglPembuatan());
			mkAlihjenjangString[7] = String.valueOf(ekuivalensiPd.getKurikulumLama().getIdKurikulum());
			mkAlihjenjangString[8] = String.valueOf(ekuivalensiPd.getKurikulumBaru().getIdKurikulum());
			mkAlihjenjangString[9] = String.valueOf(ekuivalensiPd.getPd().getIdPd());
			mkAlihjenjangString[10] = String.valueOf(ekuivalensiPd.isaEkuivalensi());
			aData.add(mkAlihjenjangString);
		}
		mkLuarDatatable.setAaData(aData);
		mkLuarDatatable.setiTotalRecords(ekuivalensiPdRepository.count(""));
		mkLuarDatatable.setiTotalDisplayRecords(ekuivalensiPdRepository.count("("+parameter.getWhere()+")"+dbFilter));

		return mkLuarDatatable;
	}
}
