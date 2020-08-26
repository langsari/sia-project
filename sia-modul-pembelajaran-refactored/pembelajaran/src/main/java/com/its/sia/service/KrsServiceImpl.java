package com.its.sia.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.jadira.usertype.dateandtime.joda.PersistentDateTime;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sia.modul.domain.AnggotaRombel;
import com.sia.modul.domain.Krs;
import com.sia.modul.domain.Pd;
import com.sia.modul.domain.Pemb;
import com.sia.modul.domain.Rombel;
import com.its.sia.repository.AnggotaRombelRepository;
import com.its.sia.repository.KrsRepository;
import com.its.sia.repository.RombelRepository;

@Service
public class KrsServiceImpl implements KrsService {

	private static final Logger logger = LoggerFactory.getLogger(KrsServiceImpl.class);
	
	private String[] column = {"krs.idKrs","pd.nimPd","pd.nmPd","pd.angkatanPd","mk.namaMK","pemb.nmPemb","mk.jumlahSKS","krs.waktuAmbil","krs.aKrsTerhapus","krs.waktuHapus","krs.aKrsDisetujui","krs.aKrsBatal","krs.waktuBatal"};
	private Boolean[] searchable = {false,true,true,true,true,true,true,true,false,true,false,false,true};	

	private String[] columnPeserta = {"pd.idPd","pd.nimPd","pd.nmPd","pd.angkatanPd","pd.idPd"};
	private Boolean[] searchablePeserta = {false,true,true,true,false};
	
	private String[] columnPersetujuan = {"pd.idPd","pd.nimPd","pd.nmPd","pd.angkatanPd","ptk.nmPtk","pd.aPdTerhapus"};
	private Boolean[] searchablePersetujuan = {false,true,true,true,true,false};
	
	@Autowired
	private KrsRepository krsRepository;
	
	@Autowired
	private AnggotaRombelService anggotaRombelService;
	
	@Autowired
	private PembService pembService;
	
	@Override
	public List<Krs> get() {
		return get("");
	}

	@Override
	public List<Krs> get(String where) {
		return get(where,"");
	}

	@Override
	public List<Krs> get(String where, String order) {
		return get(where,order,-1,-1);
	}

	@Override
	public List<Krs> get(String where, String order, int limit, int offset) {
		return krsRepository.get(where, order, limit, offset);
	}

	@Override
	public Krs getById(UUID idKrs) {
		return krsRepository.getById(idKrs);
	}

	@Override
	public String save(Krs krs) {
		logger.info("save service");
		String where="";
		if(krs.getIdKrs()!=null) where +="krs.idKrs!= '"+krs.getIdKrs().toString()+"' and "; 
		List<Krs> listKrsSementara =  get(where+"krs.aKrsBatal = false and krs.aKrsTerhapus = false and pd.idPd ='"+krs.getPd().getIdPd()+"' AND mk.idMK = '"+krs.getPemb().getMk().getIdMK()+"' AND tglSmt.idTglSmt ='"+krs.getPemb().getTglSmt().getIdTglSmt()+"'");
		Integer kuota = krs.getPemb().getKuota();
		if(listKrsSementara.size()>0)
		{
			return null;
		}
		else if(krs.getIdKrs() != null)
		{
			//update
			krsRepository.update(krs);
			batasiKrs(krs.getPemb());
			return krs.getIdKrs().toString();
		}
		else
		{
			logger.info("insert service");
			//insert
			krs.setWaktuAmbil(LocalDateTime.now());
			batasiKrs(krs.getPemb());
			return krsRepository.insert(krs).toString();
		}
	}

	@Override
	public String delete(UUID idKrs) {
		Krs krs = krsRepository.getById(idKrs);
		if(krs==null) return null;
		else{
			krs.setWaktuHapus(LocalDateTime.now());
			krs.setaKrsTerhapus(true);
			krsRepository.update(krs);
			return "Ok";
		}
	}
	
	@Override
	public String batal(UUID idKrs) {
		Krs krs = krsRepository.getById(idKrs);
		if(krs==null) return null;
		else{
			krs.setWaktuBatal(LocalDateTime.now());
			krs.setaKrsBatal(true);
			krsRepository.update(krs);
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
		List<Krs> queryResult = get("("+parameter.getWhere()+")"+dbFilter, parameter.getOrder(), iDisplayLength, iDisplayStart);
		List<String[]> aData = new ArrayList<String[]>();
		for (Krs krs : queryResult) {
			String[] krsString = new String[13];
			krsString[0] = krs.getIdKrs().toString();
			krsString[1] = String.valueOf(krs.getPd().getNimPd());
			krsString[2] = String.valueOf(krs.getPd().getNmPd());
			krsString[3] = String.valueOf(krs.getPd().getAngkatanPd());
			krsString[4] = String.valueOf(krs.getPemb().getMk().getNamaMK());
			krsString[5] = String.valueOf(krs.getPemb().getNmPemb());
			krsString[6] = String.valueOf(krs.getPemb().getMk().getJumlahSKS());
			krsString[7] = String.valueOf(krs.getWaktuAmbil()!=null?krs.getWaktuAmbil().toString("yyyy-MM-dd HH:mm:ss"):"");
			krsString[8] = String.valueOf(krs.isaKrsTerhapus());
			krsString[9] = String.valueOf(krs.getWaktuHapus()!=null?krs.getWaktuHapus().toString("yyyy-MM-dd HH:mm:ss"):"");
			krsString[10] = String.valueOf(krs.isaKrsDisetujui());
			krsString[11] = String.valueOf(krs.isaKrsBatal());
			krsString[12] = String.valueOf(krs.getWaktuBatal()!=null?krs.getWaktuBatal().toString("yyyy-MM-dd HH:mm:ss"):"");
			aData.add(krsString);
		}
		rombelDatatable.setAaData(aData);
		rombelDatatable.setiTotalRecords(krsRepository.count(filter));
		rombelDatatable.setiTotalDisplayRecords(krsRepository.count("("+parameter.getWhere()+")"+dbFilter));

		return rombelDatatable;
	}

	@Override
	public Long count(String where) {
		// TODO Auto-generated method stub
		Long jumlah =krsRepository.count(where);
		return jumlah;
	}

	@Override
	public List<Pd> getPeserta() {
		return getPeserta("");
	}

	@Override
	public List<Pd> getPeserta(String where) {
		return getPeserta(where, "");
	}

	@Override
	public List<Pd> getPeserta(String where, String order) {
		return getPeserta(where, order, -1, -1);
	}

	@Override
	public List<Pd> getPeserta(String where, String order, int limit, int offset) {
		return krsRepository.getPeserta(where, order, limit, offset);
	}
	
	@Override
	public Datatable getPesertaDatatable(String sEcho, int iDisplayLength,
			int iDisplayStart, int iSortCol_0, String sSortDir_0,
			String sSearch, String filter) {
		DatatableExtractParams parameter = new DatatableExtractParams(sSearch, this.columnPeserta, this.searchablePeserta, iSortCol_0, sSortDir_0);
		Datatable pdDatatable= new Datatable();
		pdDatatable.setsEcho(sEcho);
		String dbFilter = "";
		if(filter != null && !filter.equals("")) dbFilter+=" AND "+filter; 
		List<Pd> queryResult = getPeserta("("+parameter.getWhere()+")"+dbFilter, parameter.getOrder(), iDisplayLength, iDisplayStart);
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
		pdDatatable.setiTotalRecords(krsRepository.countPeserta(filter));
		pdDatatable.setiTotalDisplayRecords(krsRepository.countPeserta("("+parameter.getWhere()+")"+dbFilter));

		return pdDatatable;
	}

	@Override
	public String deletePdInKrs(UUID idPd, UUID idPemb) {
		List<Krs> listKrs = get("pd.idPd ='"+idPd+"' and pemb.idPemb ='"+idPemb+"'");
		for (Krs krs : listKrs) {
			delete(krs.getIdKrs());
		}
		return "ok";
	}

	@Override
	public String addFromRombel(UUID idRombel, UUID idPemb) {
		List<AnggotaRombel> listAnggotaRombel = anggotaRombelService.get("rombel.idRombel ='"+idRombel+"'");
		Pemb pemb = pembService.getById(idPemb);
		if(pemb!=null)
		{
			for (AnggotaRombel anggotaRombel : listAnggotaRombel) {
				Krs krs = new Krs();
				krs.setPd(anggotaRombel.getPd());
				krs.setPemb(pemb);
				krs.setaKrsDisetujui(true);
				save(krs);
			}
		}
		return "ok";
	}

	@Override
	public void batasiKrs(Pemb pemb) {
		Integer kuota = pemb.getKuota();
		List<Krs> listKrs = get("krs.aKrsBatal = false and krs.aKrsTerhapus = false AND pemb.idPemb = '"+pemb.getIdPemb()+"'","krs.waktuAmbil asc",1000,kuota);
		for (Krs krs : listKrs) {
			krsRepository.delete(krs);
		}
	}

	@Override
	public Datatable getPdKrsDatatable(String sEcho, int iDisplayLength,
			int iDisplayStart, int iSortCol_0, String sSortDir_0,
			String sSearch, String filter) {
		DatatableExtractParams parameter = new DatatableExtractParams(sSearch, this.columnPersetujuan, this.searchablePersetujuan, iSortCol_0, sSortDir_0);
		Datatable pdDatatable= new Datatable();
		pdDatatable.setsEcho(sEcho);
		String dbFilter = "";
		if(filter != null && !filter.equals("")) dbFilter+=" AND "+filter; 
		List<Pd> queryResult = getPeserta("("+parameter.getWhere()+")"+dbFilter, parameter.getOrder(), iDisplayLength, iDisplayStart);
		List<String[]> aData = new ArrayList<String[]>();
		for (Pd pd : queryResult) {
			String[] pdString = new String[7];
			pdString[0] = pd.getIdPd().toString();
			pdString[1] = pd.getNimPd();
			pdString[2] = pd.getNmPd();
			pdString[3] = pd.getAngkatanPd().toString();
			pdString[4] = String.valueOf(pd.getPtk()==null?"":pd.getPtk().getNmPtk());
			pdString[5] = "false";
			pdString[6] = String.valueOf(pd.isaPdTerhapus());
			aData.add(pdString);
		}
		pdDatatable.setAaData(aData);
		pdDatatable.setiTotalRecords(krsRepository.countPeserta(filter));
		pdDatatable.setiTotalDisplayRecords(krsRepository.countPeserta("("+parameter.getWhere()+")"+dbFilter));

		return pdDatatable;
	}
}
