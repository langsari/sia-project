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
import com.sia.modul.domain.PertemuanPembelajaran;
import com.sia.modul.domain.Rombel;
import com.its.sia.repository.AnggotaRombelRepository;
import com.its.sia.repository.PertemuanPembelajaranRepository;
import com.its.sia.repository.RombelRepository;

@Service
public class PertemuanPembelajaranServiceImpl implements PertemuanPembelajaranService {

	private static final Logger logger = LoggerFactory.getLogger(PertemuanPembelajaranServiceImpl.class);
	
	private String[] column = {"krsSementara.idKrsSementara","pd.nimPd","pd.nmPd","pd.angkatanPd","mk.namaMK","pemb.nmPemb","mk.jumlahSKS","krsSementara.waktuAmbil","krsSementara.aKrsSementaraTerhapus","krsSementara.waktuHapus"};
	private Boolean[] searchable = {false,true,true,true,true,true,true,true,false,true};	

	@Autowired
	private PertemuanPembelajaranRepository pertemuanPembelajaranRepository;
	
	@Override
	public List<PertemuanPembelajaran> get() {
		return get("");
	}

	@Override
	public List<PertemuanPembelajaran> get(String where) {
		return get(where,"");
	}

	@Override
	public List<PertemuanPembelajaran> get(String where, String order) {
		return get(where,order,-1,-1);
	}

	@Override
	public List<PertemuanPembelajaran> get(String where, String order, int limit, int offset) {
		return pertemuanPembelajaranRepository.get(where, order, limit, offset);
	}

	@Override
	public PertemuanPembelajaran getById(UUID idPertemuanPembelajaran) {
		return pertemuanPembelajaranRepository.getById(idPertemuanPembelajaran);
	}

	@Override
	public String save(PertemuanPembelajaran pertemuanPembelajaran) {
		String where ="";
		if(pertemuanPembelajaran.getIdPertemuanPembelajaran()!=null) where+="pertemuanPembelajaran.idPertemuanPembelajaran !='"+pertemuanPembelajaran.getIdPertemuanPembelajaran()+"' and ";
		List<PertemuanPembelajaran> listPertemuanPembelajaran = get(where+"pertemuanPembelajaran.pertemuan = "+pertemuanPembelajaran.getPertemuan()+" and pemb.idPemb ='"+pertemuanPembelajaran.getPemb().getIdPemb()+"' ");
		if(listPertemuanPembelajaran.size()>0)
		{
			return null;
		}
		else if(pertemuanPembelajaran.getIdPertemuanPembelajaran() != null)
		{
			//update
			pertemuanPembelajaranRepository.update(pertemuanPembelajaran);
			return pertemuanPembelajaran.getIdPertemuanPembelajaran().toString();
		}
		else
		{
			logger.info("insert service");
			//insert
			return pertemuanPembelajaranRepository.insert(pertemuanPembelajaran).toString();
		}
	}

	@Override
	public String delete(UUID idPertemuanPembelajaran) {
		PertemuanPembelajaran pertemuanPembelajaran = pertemuanPembelajaranRepository.getById(idPertemuanPembelajaran);
		if(pertemuanPembelajaran==null) return null;
		else{
			pertemuanPembelajaranRepository.delete(pertemuanPembelajaran);
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
		List<PertemuanPembelajaran> queryResult = get("("+parameter.getWhere()+")"+dbFilter, parameter.getOrder(), iDisplayLength, iDisplayStart);
		List<String[]> aData = new ArrayList<String[]>();
		for (PertemuanPembelajaran pertemuanPembelajaran : queryResult) {
			String[] krsSementaraString = new String[8];
			krsSementaraString[0] = pertemuanPembelajaran.getIdPertemuanPembelajaran().toString();
			krsSementaraString[1] = String.valueOf(pertemuanPembelajaran.getPemb().getMk().getNamaMK());
			krsSementaraString[2] = String.valueOf(pertemuanPembelajaran.getPemb().getNmPemb());
			krsSementaraString[3] = String.valueOf(pertemuanPembelajaran.getPertemuan());
			krsSementaraString[4] = String.valueOf(pertemuanPembelajaran.getMateri());
			krsSementaraString[5] = String.valueOf(pertemuanPembelajaran.getKendalaPerkuliahan());
			krsSementaraString[6] = String.valueOf(pertemuanPembelajaran.getTanggapanPd());
			krsSementaraString[7] = String.valueOf(pertemuanPembelajaran.getPemb().getNmPemb());
			aData.add(krsSementaraString);
		}
		rombelDatatable.setAaData(aData);
		rombelDatatable.setiTotalRecords(pertemuanPembelajaranRepository.count(filter));
		rombelDatatable.setiTotalDisplayRecords(pertemuanPembelajaranRepository.count("("+parameter.getWhere()+")"+dbFilter));

		return rombelDatatable;
	}

	@Override
	public Long count(String where) {
		return pertemuanPembelajaranRepository.count(where);
	}
}
