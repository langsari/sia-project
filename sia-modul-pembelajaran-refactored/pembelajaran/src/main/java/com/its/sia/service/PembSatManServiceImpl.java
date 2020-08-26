package com.its.sia.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sia.modul.domain.AnggotaRombel;
import com.sia.modul.domain.Pemb;
import com.sia.modul.domain.PembSatMan;
import com.sia.modul.domain.Rombel;
import com.its.sia.repository.AnggotaRombelRepository;
import com.its.sia.repository.PembSatManRepository;
import com.its.sia.repository.RombelRepository;

@Service
public class PembSatManServiceImpl implements PembSatManService {

	private String[] column = {"pembSatMan.idPembSatMan","mk.namaMK","pemb.nmPemb","satMan.nmSatMan"};
	private Boolean[] searchable = {false,true,true,true};	
	
	private String[] columnPemb = {"pemb.idPemb","mk.namaMK","pemb.nmPemb","thnAjaran.thnThnAjaran","smt.nmSmt","pemb.kuota","pemb.temuDalamSeminggu"};
	private Boolean[] searchablePemb = {false,true,true,true,true,true,true};

	@Autowired
	private PembSatManRepository pembSatManRepository;
	
	@Override
	public List<PembSatMan> get() {
		return get("");
	}

	@Override
	public List<PembSatMan> get(String where) {
		return get(where,"");
	}

	@Override
	public List<PembSatMan> get(String where, String order) {
		return get(where,order,-1,-1);
	}

	@Override
	public List<PembSatMan> get(String where, String order, int limit, int offset) {
		return pembSatManRepository.get(where, order, limit, offset);
	}

	@Override
	public PembSatMan getById(UUID idAnggotaRombel) {
		return pembSatManRepository.getById(idAnggotaRombel);
	}

	@Override
	public String save(PembSatMan pembSatMan) {
		List<PembSatMan> listAnggotaRombel =  get("pemb.idPemb ='"+pembSatMan.getPemb().getIdPemb()+"' AND satMan.idSatMan = '"+pembSatMan.getSatMan().getIdSatMan()+"'");
		if(listAnggotaRombel.size()>0)
		{
			return null;
		}
		else if(pembSatMan.getIdPembSatMan() != null)
		{
			//update
			pembSatManRepository.update(pembSatMan);
			return pembSatMan.getIdPembSatMan().toString();
		}
		else
		{
			//insert
			return pembSatManRepository.insert(pembSatMan).toString();
		}
	}

	@Override
	public String delete(UUID idPembSatMan) {
		PembSatMan pembSatMan = pembSatManRepository.getById(idPembSatMan);
		if(pembSatMan==null) return null;
		else{
			pembSatManRepository.delete(pembSatMan);
			return "Ok";
		}
	}

	@Override
	public List<Pemb> getPemb() {
		return getPemb("");
	}

	@Override
	public List<Pemb> getPemb(String where) {
		return getPemb(where,"");
	}

	@Override
	public List<Pemb> getPemb(String where, String order) {
		return getPemb(where,order,-1,-1);
	}

	@Override
	public List<Pemb> getPemb(String where, String order, int limit, int offset) {
		return pembSatManRepository.getPemb(where, order, limit, offset);
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
		List<PembSatMan> queryResult = get("("+parameter.getWhere()+")"+dbFilter, parameter.getOrder(), iDisplayLength, iDisplayStart);
		List<String[]> aData = new ArrayList<String[]>();
		for (PembSatMan pembSatMan : queryResult) {
			String[] anggotaRombelString = new String[5];
			anggotaRombelString[0] = pembSatMan.getIdPembSatMan().toString();
			anggotaRombelString[1] = String.valueOf(pembSatMan.getPemb().getMk().getNamaMK());
			anggotaRombelString[2] = String.valueOf(pembSatMan.getPemb().getNmPemb());
			anggotaRombelString[3] = String.valueOf(pembSatMan.getSatMan().getNmSatMan());
			anggotaRombelString[4] = String.valueOf(pembSatMan.getIdPembSatMan());
			aData.add(anggotaRombelString);
		}
		rombelDatatable.setAaData(aData);
		rombelDatatable.setiTotalRecords(pembSatManRepository.count(filter));
		rombelDatatable.setiTotalDisplayRecords(pembSatManRepository.count("("+parameter.getWhere()+") "+dbFilter));

		return rombelDatatable;
	}
	

	@Override
	public Datatable getPembDatatable(String sEcho, int iDisplayLength,
			int iDisplayStart, int iSortCol_0, String sSortDir_0,
			String sSearch, String filter) {
		DatatableExtractParams parameter = new DatatableExtractParams(sSearch, this.columnPemb, this.searchablePemb, iSortCol_0, sSortDir_0);
		Datatable rombelDatatable= new Datatable();
		rombelDatatable.setsEcho(sEcho);
		String dbFilter = "";
		if(filter != null && !filter.equals("")) dbFilter+=" AND "+filter; 
		List<Pemb> queryResult = getPemb("("+parameter.getWhere()+")"+dbFilter, parameter.getOrder(), iDisplayLength, iDisplayStart);
		List<String[]> aData = new ArrayList<String[]>();
		for (Pemb pemb : queryResult) {
			String[] pembString = new String[8];
			pembString[0] = pemb.getIdPemb().toString();
			pembString[1] = String.valueOf(pemb.getMk().getNamaMK());
			pembString[2] = String.valueOf(pemb.getNmPemb());
			pembString[3] = String.valueOf(pemb.getTglSmt().getThnAjaran().getThnThnAjaran());
			pembString[4] = String.valueOf(pemb.getTglSmt().getSmt().getNmSmt());
			pembString[5] = String.valueOf(pemb.getKuota());
			pembString[6] = String.valueOf(pemb.getTemuDalamSeminggu());
			pembString[7] = String.valueOf(pemb.getTglSmt().getaTglSmtAktif());
			aData.add(pembString);
		}
		rombelDatatable.setAaData(aData);
		rombelDatatable.setiTotalRecords(pembSatManRepository.countPemb(filter));
		rombelDatatable.setiTotalDisplayRecords(pembSatManRepository.countPemb("("+parameter.getWhere()+")"+dbFilter));

		return rombelDatatable;
	}
}
