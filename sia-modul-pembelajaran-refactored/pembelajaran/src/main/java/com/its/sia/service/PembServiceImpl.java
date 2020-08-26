package com.its.sia.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sia.modul.domain.Pemb;
import com.sia.modul.domain.Rombel;
import com.sia.modul.domain.SatMan;
import com.its.sia.repository.PembRepository;
import com.its.sia.repository.RombelRepository;

@Service
public class PembServiceImpl implements PembService {

	private String[] column = {"pemb.idPemb","mk.kodeMK","mk.namaMK","pemb.nmPemb","thnAjaran.thnThnAjaran","smt.nmSmt","pemb.kuota","pemb.temuDalamSeminggu"};
	private Boolean[] searchable = {false,true,true,true,true,true,true,true};
			
	@Autowired
	private PembRepository pembRepository;
	
	@Override
	public List<Pemb> get() {
		return get("");
	}

	@Override
	public List<Pemb> get(String where) {
		return get(where,"");
	}

	@Override
	public List<Pemb> get(String where, String order) {
		return get(where,order,-1,-1);
	}

	@Override
	public List<Pemb> get(String where, String order, int limit, int offset) {
		return pembRepository.get(where, order, limit, offset);
	}

	@Override
	public Pemb getById(UUID idPemb) {
		return pembRepository.getById(idPemb);
	}

	@Override
	public String save(Pemb pemb) {
		if(pemb.getIdPemb() != null)
		{
			//update
			pembRepository.update(pemb);
			return pemb.getIdPemb().toString();
		}
		else
		{
			//insert
			return pembRepository.insert(pemb).toString();
		}
	}

	@Override
	public String delete(UUID idPemb) {
		List<Pemb> listPemb = get("pemb.idPemb='"+idPemb.toString()+"' AND tglSmt.aTglSmtAktif=true");
		if(listPemb.size()==0) return null;
		else{
			listPemb.get(0).setaPembTerhapus(true);
			pembRepository.update(listPemb.get(0));
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
		List<Pemb> queryResult = get("("+parameter.getWhere()+")"+dbFilter, parameter.getOrder(), iDisplayLength, iDisplayStart);
		List<String[]> aData = new ArrayList<String[]>();
		for (Pemb pemb : queryResult) {
			String[] pembString = new String[9];
			pembString[0] = pemb.getIdPemb().toString();
			pembString[1] = String.valueOf(pemb.getMk().getKodeMK());
			pembString[2] = String.valueOf(pemb.getMk().getNamaMK());
			pembString[3] = String.valueOf(pemb.getNmPemb());
			pembString[4] = String.valueOf(pemb.getTglSmt().getThnAjaran().getThnThnAjaran());
			pembString[5] = String.valueOf(pemb.getTglSmt().getSmt().getNmSmt());
			pembString[6] = String.valueOf(pemb.getKuota());
			pembString[7] = String.valueOf(pemb.getTemuDalamSeminggu());
			pembString[8] = String.valueOf(pemb.getTglSmt().getaTglSmtAktif());
			aData.add(pembString);
		}
		rombelDatatable.setAaData(aData);
		rombelDatatable.setiTotalRecords(pembRepository.count(filter));
		rombelDatatable.setiTotalDisplayRecords(pembRepository.count("("+parameter.getWhere()+")"+dbFilter));

		return rombelDatatable;
	}
	
	@Override
	public List<Pemb> getPembInSatMan() {
		return getPembInSatMan("");
	}

	@Override
	public List<Pemb> getPembInSatMan(String where) {
		return getPembInSatMan(where,"");
	}

	@Override
	public List<Pemb> getPembInSatMan(String where, String order) {
		return getPembInSatMan(where,order,-1,-1);
	}

	@Override
	public List<Pemb> getPembInSatMan(String where, String order, int limit, int offset) {
		return pembRepository.getPembInSatMan(where, order, limit, offset);
	}
	
	@Override
	public List<SatMan> getSatManKurikulum() {
		return getSatManKurikulum("");
	}

	@Override
	public List<SatMan> getSatManKurikulum(String where) {
		return getSatManKurikulum(where,"");
	}

	@Override
	public List<SatMan> getSatManKurikulum(String where, String order) {
		return getSatManKurikulum(where,order,-1,-1);
	}
	
	@Override
	public List<SatMan> getSatManKurikulum(String where, String order, int limit, int offset) {
		return pembRepository.getSatManKurikulum(where, order, limit, offset);
	}
}
