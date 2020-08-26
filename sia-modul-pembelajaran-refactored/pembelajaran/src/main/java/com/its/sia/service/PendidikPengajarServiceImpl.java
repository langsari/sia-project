package com.its.sia.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sia.modul.domain.AnggotaRombel;
import com.sia.modul.domain.Pemb;
import com.sia.modul.domain.Rombel;
import com.its.sia.repository.AnggotaRombelRepository;
import com.its.sia.repository.PendidikPengajarRepository;
import com.its.sia.repository.RombelRepository;
import com.sia.modul.domain.PendidikPengajar;

@Service
public class PendidikPengajarServiceImpl implements PendidikPengajarService {

	private String[] column = {"pendidikPengajar.idPendidikPengajar","ptk.nipPtk","ptk.nmPtk","pendidikPengajar.aPendidikPengajarKetua","pendidikPengajar.aPendidikPengajarTerhapus"};
	private Boolean[] searchable = {false,true,true,false,false};	
	

	private String[] columnPemb = {"pemb.idPemb","mk.namaMK","pemb.nmPemb","thnAjaran.thnThnAjaran","smt.nmSmt","pemb.kuota","pemb.temuDalamSeminggu"};
	private Boolean[] searchablePemb = {false,true,true,true,true,true,true};

	@Autowired
	private PendidikPengajarRepository pendidikPengajarRepository;
	
	@Override
	public List<PendidikPengajar> get() {
		return get("");
	}

	@Override
	public List<PendidikPengajar> get(String where) {
		return get(where,"");
	}

	@Override
	public List<PendidikPengajar> get(String where, String order) {
		return get(where,order,-1,-1);
	}

	@Override
	public List<PendidikPengajar> get(String where, String order, int limit, int offset) {
		return pendidikPengajarRepository.get(where, order, limit, offset);
	}

	@Override
	public PendidikPengajar getById(UUID idPendidikPengajar) {
		return pendidikPengajarRepository.getById(idPendidikPengajar);
	}

	@Override
	public String save(PendidikPengajar pendidikPengajar) {
		String where ="";
		if(pendidikPengajar.getIdPendidikPengajar()!=null) where ="pendidikPengajar.idPendidikPengajar !='"+pendidikPengajar.getIdPendidikPengajar()+"' and "; 
		List<PendidikPengajar> listPendidikPengajar =  get(where+"ptk.idPtk ='"+pendidikPengajar.getPtk().getIdPtk()+"' AND pemb.idPemb = '"+pendidikPengajar.getPemb().getIdPemb()+"' AND pendidikPengajar.aPendidikPengajarTerhapus = false");
		if(listPendidikPengajar.size()>0)
		{
			return null;
		}
		else if(pendidikPengajar.getIdPendidikPengajar() != null)
		{
			//update
			pendidikPengajarRepository.update(pendidikPengajar);
			return pendidikPengajar.getIdPendidikPengajar().toString();
		}
		else
		{
			//insert
			return pendidikPengajarRepository.insert(pendidikPengajar).toString();
		}
	}

	@Override
	public String delete(UUID idPendidikPengajar) {
		PendidikPengajar pendidikPengajar = pendidikPengajarRepository.getById(idPendidikPengajar);
		if(pendidikPengajar==null) return null;
		else{
			pendidikPengajar.setaPendidikPengajarTerhapus(true);
			pendidikPengajarRepository.update(pendidikPengajar);
			return "Ok";
		}
	}
	
	@Override
	public String deleteInPembelajaran(Pemb pemb) {
		List<PendidikPengajar> listPendidikPengajar = get("pemb.idPemb = '"+pemb.getIdPemb().toString()+"'");
		for (PendidikPengajar pendidikPengajar : listPendidikPengajar) {
			pendidikPengajarRepository.delete(pendidikPengajar);
		}
		return "Ok";
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
		List<PendidikPengajar> queryResult = get("("+parameter.getWhere()+")"+dbFilter, parameter.getOrder(), iDisplayLength, iDisplayStart);
		List<String[]> aData = new ArrayList<String[]>();
		for (PendidikPengajar pendidikPengajar : queryResult) {
			String[] pendidikPengajarString = new String[5];
			pendidikPengajarString[0] = pendidikPengajar.getIdPendidikPengajar().toString();
			pendidikPengajarString[1] = String.valueOf(pendidikPengajar.getPtk().getNipPtk());
			pendidikPengajarString[2] = String.valueOf(pendidikPengajar.getPtk().getNmPtk());
			pendidikPengajarString[3] = String.valueOf(pendidikPengajar.isaPendidikPengajarKetua());
			pendidikPengajarString[4] = String.valueOf(pendidikPengajar.isaPendidikPengajarTerhapus());
			aData.add(pendidikPengajarString);
		}
		rombelDatatable.setAaData(aData);
		rombelDatatable.setiTotalRecords(pendidikPengajarRepository.count(filter));
		rombelDatatable.setiTotalDisplayRecords(pendidikPengajarRepository.count("("+parameter.getWhere()+")"+dbFilter));

		return rombelDatatable;
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
		return pendidikPengajarRepository.getPemb(where, order, limit, offset);
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
		rombelDatatable.setiTotalRecords(pendidikPengajarRepository.countPemb(filter));
		rombelDatatable.setiTotalDisplayRecords(pendidikPengajarRepository.countPemb("("+parameter.getWhere()+")"+dbFilter));

		return rombelDatatable;
	}
}
