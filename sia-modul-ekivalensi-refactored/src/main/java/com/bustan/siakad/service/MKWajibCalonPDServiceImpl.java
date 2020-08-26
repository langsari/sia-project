package com.bustan.siakad.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bustan.siakad.repository.MKWajibCalonPDRepository;
import com.sia.modul.domain.MKWajibCalonPD;

@Service
public class MKWajibCalonPDServiceImpl implements MKWajibCalonPDService{

	private String[] column = {"mkWajibCalonPD.idMKWajibCalonPD","mk.namaMK", "mk.kodeMK", "mk.jumlahSKS", "mk.sifatMK"};
	private Boolean[] searchable = {false,true,true,true,true};
	
	@Autowired
	MKWajibCalonPDRepository mkWajibCalonPDRepository;
	
	@Override
	public List<MKWajibCalonPD> get() {
		return get("");
	}

	@Override
	public List<MKWajibCalonPD> get(String where) {
		return get(where,"");
	}

	@Override
	public List<MKWajibCalonPD> get(String where, String order) {
		return get(where,order,-1,-1);
	}

	@Override
	public List<MKWajibCalonPD> get(String where, String order, int limit,
			int offset) {
		return mkWajibCalonPDRepository.get(where,order,limit,offset);
	}

	@Override
	public MKWajibCalonPD getById(UUID idMKWajibCalonPD) {
		return mkWajibCalonPDRepository.getById(idMKWajibCalonPD);
	}

	@Override
	public String save(MKWajibCalonPD mkWajibCalonPD) {
		if(mkWajibCalonPD.getIdMKWajibCalonPD() != null)
		{
			//update
			mkWajibCalonPDRepository.update(mkWajibCalonPD);
			return mkWajibCalonPD.getIdMKWajibCalonPD().toString();
		}
		else
		{
			//insert
			return mkWajibCalonPDRepository.insert(mkWajibCalonPD).toString();
		}
	}

	@Override
	public String delete(UUID idMKWajibCalonPD) {
		MKWajibCalonPD ekuivalensiCalonPD = mkWajibCalonPDRepository.getById(idMKWajibCalonPD);
		if(ekuivalensiCalonPD == null) return null;
		else
		{
			mkWajibCalonPDRepository.delete(ekuivalensiCalonPD);
		}
		
		return "Ok";
	}

	@Override
	public Datatable getdatatable(String sEcho, int iDisplayLength,
			int iDisplayStart, int iSortCol_0, String sSortDir_0,
			String sSearch, String filter) {
		DatatableExtractParams parameter = new DatatableExtractParams(sSearch, this.column, this.searchable, iSortCol_0, sSortDir_0);
		Datatable ecpdDatatable= new Datatable();
		ecpdDatatable.setsEcho(sEcho);
		String dbFilter = "";
		if(filter != null && !filter.equals("")) dbFilter+=" AND "+filter; 
		List<MKWajibCalonPD> queryResult = get("("+parameter.getWhere()+")"+dbFilter, parameter.getOrder(), iDisplayLength, iDisplayStart);
		List<String[]> aData = new ArrayList<String[]>();
		for (MKWajibCalonPD mkWajibCalonPD : queryResult) {
			String[] calonPDString = new String[5];
			calonPDString[0] = mkWajibCalonPD.getIdMKWajibCalonPD().toString();
			calonPDString[1] = String.valueOf(mkWajibCalonPD.getMk().getNamaMK());			
			calonPDString[2] = String.valueOf(mkWajibCalonPD.getMk().getKodeMK());
			calonPDString[3] = String.valueOf(mkWajibCalonPD.getMk().getJumlahSKS());
			calonPDString[4] = String.valueOf(mkWajibCalonPD.getMk().getSifatMK());
			aData.add(calonPDString);
		}
		ecpdDatatable.setAaData(aData);
		ecpdDatatable.setiTotalRecords(mkWajibCalonPDRepository.count(""));
		ecpdDatatable.setiTotalDisplayRecords(mkWajibCalonPDRepository.count("("+parameter.getWhere()+")"+dbFilter));

		return ecpdDatatable;
	}

}
