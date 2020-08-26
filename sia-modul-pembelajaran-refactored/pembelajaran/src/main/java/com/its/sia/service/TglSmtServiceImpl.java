package com.its.sia.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sia.modul.domain.Smt;
import com.sia.modul.domain.TglSmt;
import com.sia.modul.domain.ThnAjaran;
import com.its.sia.repository.TglSmtRepository;
import com.its.sia.repository.ThnAjaranRepository;

@Service
public class TglSmtServiceImpl implements TglSmtService {

	private String[] column = {"tglSmt.idTglSmt","thnAjaran.thnThnAjaran","smt.nmSmt","tglSmt.tglAkhirBayar","tglSmt.tglAwalSusunKrs","tglSmt.tglAkhirSusunKrs","tglSmt.tglAkhirUbahKrs","tglSmt.tglAkhirBatalMk","tglSmt.tglAkhirPenilaian","tglSmt.aTglSmtAktif","tglSmt.aTglSmtTerhapus"};
	private Boolean[] searchable = {false,true,true,true,true,true,true,true,true,true,false};

	@Autowired
	private TglSmtRepository tglSmtRepository;
	
	@Autowired
	private ThnAjaranRepository thnAjaranRepository;
	
	@Override
	public List<TglSmt> get() {
		return get("");
	}

	@Override
	public List<TglSmt> get(String where) {
		return get(where,"");
	}

	@Override
	public List<TglSmt> get(String where, String order) {
		return get(where,order,-1,-1);
	}

	@Override
	public List<TglSmt> get(String where, String order, int limit, int offset) {
		return tglSmtRepository.get(where, order, limit, offset);
	}

	@Override
	public TglSmt getById(UUID idTglSmt) {
		return tglSmtRepository.getById(idTglSmt);
	}

	@Override
	public String save(TglSmt tglSmt) {
		String where = "";
		List<TglSmt> queryresult;
		if(tglSmt.getIdTglSmt() != null) where ="tglSmt.idTglSmt != '"+tglSmt.getIdTglSmt().toString()+"'";
		if(tglSmt.getaTglSmtAktif() == true) {
			queryresult = get(where+(where.equals("")?"":" AND")+" tglSmt.aTglSmtAktif = true");
			if(queryresult.size()>0) return "tglAmtAktifException";
		}
		queryresult = get("thnAjaran.idThnAjaran = '"+tglSmt.getThnAjaran().getIdThnAjaran().toString()+"' AND smt.idSmt = '"+tglSmt.getSmt().getIdSmt()+"'"+(where.equals("")?"":" AND "+where));
		if(queryresult.size()>0) return null;
		else if(tglSmt.getIdTglSmt() != null)
		{
			//update
			tglSmtRepository.update(tglSmt);
			return tglSmt.getIdTglSmt().toString();
		}
		else
		{
			//insert
			return tglSmtRepository.insert(tglSmt).toString();
		}
	}

	@Override
	public String delete(UUID idTglSmt) {
		TglSmt smt = tglSmtRepository.getById(idTglSmt);
		if(smt==null) return null;
		else{
			smt.setaTglSmtTerhapus(true);
			tglSmtRepository.update(smt);
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
		List<TglSmt> queryResult = get("("+parameter.getWhere()+")"+dbFilter, parameter.getOrder(), iDisplayLength, iDisplayStart);
		List<String[]> aData = new ArrayList<String[]>();
		DateFormat df = new SimpleDateFormat("dd-MM-yyy");
		for (TglSmt tglSmt : queryResult) {
			String[] tglSmtString = new String[12];
			tglSmtString[0] = tglSmt.getIdTglSmt().toString();
			tglSmtString[1] = String.valueOf(tglSmt.getThnAjaran().getThnThnAjaran());
			tglSmtString[2] = String.valueOf(tglSmt.getSmt().getNmSmt());
			tglSmtString[3] = tglSmt.getTglAkhirBayar()!=null?tglSmt.getTglAkhirBayar().toString("dd-MM-yyy"):"";
			tglSmtString[4] = tglSmt.getTglAwalSusunKrs()!=null?tglSmt.getTglAwalSusunKrs().toString("dd-MM-yyy"):"";
			tglSmtString[5] = tglSmt.getTglAkhirSusunKrs()!=null?tglSmt.getTglAkhirSusunKrs().toString("dd-MM-yyy"):"";
			tglSmtString[6] = tglSmt.getTglAkhirUbahKrs()!=null?tglSmt.getTglAkhirUbahKrs().toString("dd-MM-yyy"):"";
			tglSmtString[7] = tglSmt.getTglAkhirBatalMk()!=null?tglSmt.getTglAkhirBatalMk().toString("dd-MM-yyy"):"";
			tglSmtString[8] = tglSmt.getTglAkhirPenilaian()!=null?tglSmt.getTglAkhirPenilaian().toString("dd-MM-yyy"):"";
			tglSmtString[9] = String.valueOf(tglSmt.getaTglSmtAktif());
			tglSmtString[10] = String.valueOf(tglSmt.isaTglSmtTerhapus());
			tglSmtString[11] = String.valueOf(tglSmt.isaTglSmtTerhapus());
			aData.add(tglSmtString);
		}
		smtDatatable.setAaData(aData);
		smtDatatable.setiTotalRecords(tglSmtRepository.count(filter));
		smtDatatable.setiTotalDisplayRecords(tglSmtRepository.count("("+parameter.getWhere()+")"+dbFilter));

		return smtDatatable;
	}

	@Override
	public TglSmt getAktif() {
		List<TglSmt> listTglSmt = get("tglSmt.aTglSmtAktif = true");
		if(listTglSmt.size()>0) return listTglSmt.get(0);
		else return null;
	}
}
