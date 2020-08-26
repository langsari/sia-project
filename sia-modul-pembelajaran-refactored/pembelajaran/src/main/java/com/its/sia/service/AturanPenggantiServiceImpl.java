package com.its.sia.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sia.modul.domain.AnggotaRombel;
import com.sia.modul.domain.AturanPengganti;
import com.sia.modul.domain.SatMan;
import com.sia.modul.domain.Smt;
import com.sia.modul.domain.TglSmt;
import com.sia.modul.domain.ThnAjaran;
import com.its.sia.repository.AturanPenggantiRepository;
import com.its.sia.repository.TglSmtRepository;
import com.its.sia.repository.ThnAjaranRepository;

@Service
public class AturanPenggantiServiceImpl implements AturanPenggantiService {

	private String[] column = {"tglSmt.idTglSmt","thnAjaran.thnThnAjaran","smt.nmSmt","tglSmt.tglAkhirBayar","tglSmt.tglAwalSusunKrs","tglSmt.tglAkhirSusunKrs","tglSmt.tglAkhirUbahKrs","tglSmt.tglAkhirBatalMk","tglSmt.tglAkhirPenilaian"};
	private Boolean[] searchable = {false,true,true,true,true,true,true,true,true};

	@Autowired
	private AturanPenggantiRepository aturanPenggantiRepository;
		
	@Override
	public List<AturanPengganti> get() {
		return get("");
	}

	@Override
	public List<AturanPengganti> get(String where) {
		return get(where,"");
	}

	@Override
	public List<AturanPengganti> get(String where, String order) {
		return get(where,order,-1,-1);
	}

	@Override
	public List<AturanPengganti> get(String where, String order, int limit, int offset) {
		return aturanPenggantiRepository.get(where, order, limit, offset);
	}

	@Override
	public AturanPengganti getById(UUID idAturanPengganti) {
		return aturanPenggantiRepository.getById(idAturanPengganti);
	}

	@Override
	public String save(AturanPengganti aturanPengganti) {
		String where = "";
		List<AturanPengganti> queryresult;
		if(aturanPengganti.getIdAturanPengganti() != null) where ="aturanPengganti.idAturanPengganti != '"+aturanPengganti.getIdAturanPengganti().toString()+"' and ";
		queryresult = get(where+"tglSmt.idTglSmt = '"+aturanPengganti.getTglSmt().getIdTglSmt()+"'");
		if(queryresult.size()>0) return null;
		else if(aturanPengganti.getIdAturanPengganti() != null)
		{
			//update
			aturanPenggantiRepository.update(aturanPengganti);
			return aturanPengganti.getIdAturanPengganti().toString();
		}
		else
		{
			//insert
			return aturanPenggantiRepository.insert(aturanPengganti).toString();
		}
	}

	@Override
	public String delete(UUID idAturanPengganti) {
		AturanPengganti aturanPengganti = aturanPenggantiRepository.getById(idAturanPengganti);
		if(aturanPengganti==null) return null;
		else{
			aturanPenggantiRepository.delete(aturanPengganti);
			return "Ok";
		}
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
		List<AturanPengganti> queryResult = get("("+parameter.getWhere()+")"+dbFilter, parameter.getOrder(), iDisplayLength, iDisplayStart);
		List<String[]> aData = new ArrayList<String[]>();
		DateFormat df = new SimpleDateFormat("dd-MM-yyy");
		for (AturanPengganti aturanPengganti : queryResult) {
			String[] aturanPenggantiString = new String[9];
			aturanPenggantiString[0] = aturanPengganti.getIdAturanPengganti().toString();
			aturanPenggantiString[1] = String.valueOf(aturanPengganti.getTglSmt().getThnAjaran().getThnThnAjaran()+" "+aturanPengganti.getTglSmt().getSmt().getNmSmt());
			aturanPenggantiString[2] = String.valueOf(aturanPengganti.getSatMan().getNmSatMan());
			aturanPenggantiString[3] = aturanPengganti.getTglAkhirBayar()!=null?aturanPengganti.getTglAkhirBayar().toString("dd-MM-yyy"):"";
			aturanPenggantiString[4] = aturanPengganti.getTglAwalSusunKrs()!=null?aturanPengganti.getTglAwalSusunKrs().toString("dd-MM-yyy"):"";
			aturanPenggantiString[5] = aturanPengganti.getTglAkhirSusunKrs()!=null?aturanPengganti.getTglAkhirSusunKrs().toString("dd-MM-yyy"):"";
			aturanPenggantiString[6] = aturanPengganti.getTglAkhirUbahKrs()!=null?aturanPengganti.getTglAkhirUbahKrs().toString("dd-MM-yyy"):"";
			aturanPenggantiString[7] = aturanPengganti.getTglAkhirBatalMk()!=null?aturanPengganti.getTglAkhirBatalMk().toString("dd-MM-yyy"):"";
			aturanPenggantiString[8] = aturanPengganti.getTglAkhirPenilaian()!=null?aturanPengganti.getTglAkhirPenilaian().toString("dd-MM-yyy"):"";
			aData.add(aturanPenggantiString);
		}
		smtDatatable.setAaData(aData);
		smtDatatable.setiTotalRecords(aturanPenggantiRepository.count(""));
		smtDatatable.setiTotalDisplayRecords(aturanPenggantiRepository.count("("+parameter.getWhere()+") "+dbFilter));

		return smtDatatable;
	}

	@Override
	public AturanPengganti getAktif(SatMan satMan) {
		List<AturanPengganti> listAturanPengganti = get("tglSmt.aTglSmtAktif = true and satMan.idSatMan = '"+satMan.getIdSatMan()+"'");
		if(listAturanPengganti.size()>0) return listAturanPengganti.get(0);
		else return null;
	}
}
