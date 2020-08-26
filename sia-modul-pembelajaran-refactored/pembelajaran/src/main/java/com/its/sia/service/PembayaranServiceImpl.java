package com.its.sia.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sia.modul.domain.AnggotaRombel;
import com.sia.modul.domain.Ips;
import com.sia.modul.domain.Pembayaran;
import com.sia.modul.domain.Rombel;
import com.its.sia.repository.AnggotaRombelRepository;
import com.its.sia.repository.IpsRepository;
import com.its.sia.repository.PembayaranRepository;
import com.its.sia.repository.RombelRepository;

@Service
public class PembayaranServiceImpl implements PembayaranService {

	private String[] column = {"thnAjaran.thnThnAjaran","smt.nmSmt","pd.nimPd","pd.nmPd","pd.angkatanPd","pembayaran.tglPembayaran"};
	private Boolean[] searchable = {true,true,true,true,true,true};	

	@Autowired
	private PembayaranRepository pembayaranRepository;
	
	@Override
	public List<Pembayaran> get() {
		return get("");
	}

	@Override
	public List<Pembayaran> get(String where) {
		return get(where,"");
	}

	@Override
	public List<Pembayaran> get(String where, String order) {
		return get(where,order,-1,-1);
	}

	@Override
	public List<Pembayaran> get(String where, String order, int limit, int offset) {
		return pembayaranRepository.get(where, order, limit, offset);
	}

	@Override
	public Pembayaran getById(UUID idPembayaran) {
		return pembayaranRepository.getById(idPembayaran);
	}

	@Override
	public String save(Pembayaran pembayaran) {
		if(pembayaran.getIdPembayaran() != null)
		{
			//update
			pembayaranRepository.update(pembayaran);
			return pembayaran.getIdPembayaran().toString();
		}
		else
		{
			//insert
			return pembayaranRepository.insert(pembayaran).toString();
		}
	}

	@Override
	public String delete(UUID idPembayaran) {
		Pembayaran pembayaran = pembayaranRepository.getById(idPembayaran);
		if(pembayaran==null) return null;
		else{
			pembayaranRepository.delete(pembayaran);
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
		List<Pembayaran> queryResult = get("("+parameter.getWhere()+")"+dbFilter, parameter.getOrder(), iDisplayLength, iDisplayStart);
		List<String[]> aData = new ArrayList<String[]>();
		for (Pembayaran pembayaran : queryResult) {
			String[] anggotaRombelString = new String[6];
			anggotaRombelString[0] = String.valueOf(pembayaran.getTglSmt().getThnAjaran().getThnThnAjaran());
			anggotaRombelString[1] = String.valueOf(pembayaran.getTglSmt().getSmt().getNmSmt());
			anggotaRombelString[2] = String.valueOf(pembayaran.getPd().getNimPd());
			anggotaRombelString[3] = String.valueOf(pembayaran.getPd().getNmPd());
			anggotaRombelString[4] = String.valueOf(pembayaran.getPd().getAngkatanPd());
			anggotaRombelString[5] = String.valueOf(pembayaran.getTglPembayaran().toString("yyyy-MM-dd HH:mm:ss"));
			aData.add(anggotaRombelString);
		}
		rombelDatatable.setAaData(aData);
		rombelDatatable.setiTotalRecords(pembayaranRepository.count(filter));
		rombelDatatable.setiTotalDisplayRecords(pembayaranRepository.count("("+parameter.getWhere()+")"+dbFilter));

		return rombelDatatable;
	}
}
