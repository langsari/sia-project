package com.its.sia.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sia.modul.domain.Pd;
import com.sia.modul.domain.TglSmt;
import com.its.sia.repository.PdRepository;

@Service
public class PdServiceImpl implements PdService {
	
	private String[] column = {"pd.idPd","pd.nimPd","pd.nmPd","pd.angkatanPd","ptk.nmPtk","pd.aPdTerhapus"};
	private Boolean[] searchable = {false,true,true,true,true,false};
	

	private String[] columnPerwalian = {"pd.idPd","pd.nimPd","pd.nmPd","pd.angkatanPd","pd.idPd"};
	private Boolean[] searchablePerwalian = {false,true,true,true,true,false};

	private String[] columnAnggotaRombel = {"pd.idPd","pd.nimPd","pd.nmPd","pd.angkatanPd","pd.aPdTerhapus"};
	private Boolean[] searchableAnggotaRombel = {false,true,true,true,false};

	private String[] columnPersetujuan = {"pd.idPd","pd.nimPd","pd.nmPd","pd.angkatanPd","ptk.nmPtk","pd.aPdTerhapus"};
	private Boolean[] searchablePersetujuan = {false,true,true,true,true,false};
	
	@Autowired
	private PdRepository pdRepository;

	@Autowired
	private KrsService krsService;

	@Autowired
	private TglSmtService tglSmtService;
	
	@Override
	public List<Pd> get() {
		return get("");
	}

	@Override
	public List<Pd> get(String where) {
		return get(where,"");
	}

	@Override
	public List<Pd> get(String where, String order) {
		return get(where,order,-1,-1);
	}

	@Override
	public List<Pd> get(String where, String order, int limit, int offset) {
		return pdRepository.get(where, order, limit, offset);
	}

	@Override
	public Pd getById(UUID idPd) {
		return pdRepository.getById(idPd);
	}

	@Override
	public String save(Pd pd) {
		String where = "";
		if(pd.getIdPd()!=null) where+=" AND pd.idPd !='"+pd.getIdPd()+"'";
		List<Pd> queryResult = get("pd.nimPd = '"+pd.getNimPd()+"'"+where);
		if(queryResult.size()>0) return null;
		else if(pd.getIdPd() != null)
		{
			//update
			pdRepository.update(pd);
			return pd.getIdPd().toString();
		}
		else
		{
			//insert
			return pdRepository.insert(pd).toString();
		}
	}

	@Override
	public String delete(UUID idTglSmt) {
		Pd pd = pdRepository.getById(idTglSmt);
		if(pd==null) return null;
		else{
			pd.setaPdTerhapus(true);
			pdRepository.update(pd);
			return "Ok";
		}
	}

	@Override
	public Datatable getdatatable(String sEcho, int iDisplayLength,
			int iDisplayStart, int iSortCol_0, String sSortDir_0,
			String sSearch, String filter) {
		DatatableExtractParams parameter = new DatatableExtractParams(sSearch, this.column, this.searchable, iSortCol_0, sSortDir_0);
		Datatable pdDatatable= new Datatable();
		pdDatatable.setsEcho(sEcho);
		String dbFilter = "";
		if(filter != null && !filter.equals("")) dbFilter+=" AND "+filter; 
		List<Pd> queryResult = get("("+parameter.getWhere()+")"+dbFilter, parameter.getOrder(), iDisplayLength, iDisplayStart);
		List<String[]> aData = new ArrayList<String[]>();
		for (Pd pd : queryResult) {
			String[] pdString = new String[7];
			pdString[0] = pd.getIdPd().toString();
			pdString[1] = pd.getNimPd();
			pdString[2] = pd.getNmPd();
			pdString[3] = pd.getAngkatanPd().toString();
			pdString[4] = String.valueOf(pd.getPtk()==null?"":pd.getPtk().getNmPtk());
			pdString[5] = String.valueOf(pd.isaPdTerhapus());
			pdString[6] = String.valueOf(pd.isaPdTerhapus());
			aData.add(pdString);
		}
		pdDatatable.setAaData(aData);
		pdDatatable.setiTotalRecords(pdRepository.count(filter));
		pdDatatable.setiTotalDisplayRecords(pdRepository.count("("+parameter.getWhere()+")"+dbFilter));

		return pdDatatable;
	}

	@Override
	public Datatable getAnakWali(String sEcho, int iDisplayLength,
			int iDisplayStart, int iSortCol_0, String sSortDir_0,
			String sSearch, String filter) {
		DatatableExtractParams parameter = new DatatableExtractParams(sSearch, this.columnPerwalian, this.searchablePerwalian, iSortCol_0, sSortDir_0);
		Datatable pdDatatable= new Datatable();
		pdDatatable.setsEcho(sEcho);
		String dbFilter = "";
		if(filter != null && !filter.equals("")) dbFilter+=" AND "+filter; 
		List<Pd> queryResult = get("("+parameter.getWhere()+")"+dbFilter, parameter.getOrder(), iDisplayLength, iDisplayStart);
		List<String[]> aData = new ArrayList<String[]>();
		for (Pd pd : queryResult) {
			Boolean aDisetujui = true;
			String[] pdString = new String[5];
			pdString[0] = pd.getIdPd().toString();
			pdString[1] = pd.getNimPd();
			pdString[2] = pd.getNmPd();
			pdString[3] = pd.getAngkatanPd().toString();
			pdString[4] = pd.getIdPd().toString();
			aData.add(pdString);
		}
		pdDatatable.setAaData(aData);
		pdDatatable.setiTotalRecords(pdRepository.count(filter));
		pdDatatable.setiTotalDisplayRecords(pdRepository.count("("+parameter.getWhere()+")"+dbFilter));

		return pdDatatable;
	}
	
	@Override
	public Datatable getKrsSetuju(String sEcho, int iDisplayLength,
			int iDisplayStart, int iSortCol_0, String sSortDir_0,
			String sSearch, String filter) {
		DatatableExtractParams parameter = new DatatableExtractParams(sSearch, this.columnPersetujuan, this.searchablePersetujuan, iSortCol_0, sSortDir_0);
		Datatable pdDatatable= new Datatable();
		TglSmt smtAktif = tglSmtService.getAktif();
		pdDatatable.setsEcho(sEcho);
		String dbFilter = "";
		if(filter != null && !filter.equals("")) dbFilter+=" AND "+filter; 
		List<Pd> queryResult = get("("+parameter.getWhere()+")"+dbFilter, parameter.getOrder(), iDisplayLength, iDisplayStart);
		List<String[]> aData = new ArrayList<String[]>();
		for (Pd pd : queryResult) {
			Boolean aDisetujui = null;
			if(smtAktif !=null)
			{
				Long jumlahAmbil = krsService.count("tglSmt.idTglSmt ='"+smtAktif.getIdTglSmt()+"' and pd.idPd = '"+pd.getIdPd()+"' "
						+ " and krs.aKrsTerhapus = false and krs.aKrsBatal = false");
				if(jumlahAmbil==0) aDisetujui = null;
				else
				{
					Long jumlahBelumDisetujui = krsService.count("tglSmt.idTglSmt ='"+smtAktif.getIdTglSmt()+"' and pd.idPd = '"+pd.getIdPd()+"' "
							+ "and krs.aKrsDisetujui = false and krs.aKrsTerhapus = false and krs.aKrsBatal = false");
					if(jumlahBelumDisetujui>0) aDisetujui = false;
					else aDisetujui = true;
				}
			}
			String[] pdString = new String[7];
			pdString[0] = pd.getIdPd().toString();
			pdString[1] = pd.getNimPd();
			pdString[2] = pd.getNmPd();
			pdString[3] = pd.getAngkatanPd().toString();
			pdString[4] = String.valueOf(pd.getPtk()==null?"":pd.getPtk().getNmPtk());
			pdString[5] = String.valueOf(aDisetujui);
			pdString[6] = String.valueOf(pd.isaPdTerhapus());
			aData.add(pdString);
		}
		pdDatatable.setAaData(aData);
		pdDatatable.setiTotalRecords(pdRepository.count(filter));
		pdDatatable.setiTotalDisplayRecords(pdRepository.count("("+parameter.getWhere()+")"+dbFilter));

		return pdDatatable;
	}
	
	@Override
	public Datatable getAnggotaRombel(String sEcho, int iDisplayLength,
			int iDisplayStart, int iSortCol_0, String sSortDir_0,
			String sSearch, String filter) {
		DatatableExtractParams parameter = new DatatableExtractParams(sSearch, this.columnAnggotaRombel, this.searchableAnggotaRombel, iSortCol_0, sSortDir_0);
		Datatable pdDatatable= new Datatable();
		pdDatatable.setsEcho(sEcho);
		String dbFilter = "";
		if(filter != null && !filter.equals("")) dbFilter+=" AND "+filter; 
		List<Pd> queryResult = get("("+parameter.getWhere()+")"+dbFilter, parameter.getOrder(), iDisplayLength, iDisplayStart);
		List<String[]> aData = new ArrayList<String[]>();
		for (Pd pd : queryResult) {
			String[] pdString = new String[5];
			pdString[0] = pd.getIdPd().toString();
			pdString[1] = pd.getNimPd();
			pdString[2] = pd.getNmPd();
			pdString[3] = pd.getAngkatanPd().toString();
			pdString[4] = pd.getIdPd().toString();
			aData.add(pdString);
		}
		pdDatatable.setAaData(aData);
		pdDatatable.setiTotalRecords(pdRepository.count(filter));
		pdDatatable.setiTotalDisplayRecords(pdRepository.count("("+parameter.getWhere()+")"+dbFilter));

		return pdDatatable;
	}

	@Override
	public List<Integer> getAngkatan() {
		return getAngkatan("");
	}

	@Override
	public List<Integer> getAngkatan(String where) {
		return getAngkatan(where, "");
	}

	@Override
	public List<Integer> getAngkatan(String where, String order) {
		return getAngkatan(where, order, -1, -1);
	}

	@Override
	public List<Integer> getAngkatan(String where, String order, int limit,
			int offset) {
		return pdRepository.getAngkatan(where, order, limit, offset);
	}
}
