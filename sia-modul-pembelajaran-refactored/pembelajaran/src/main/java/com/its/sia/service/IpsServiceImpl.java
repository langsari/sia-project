package com.its.sia.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sia.modul.domain.AnggotaRombel;
import com.sia.modul.domain.Ips;
import com.sia.modul.domain.Rombel;
import com.its.sia.repository.AnggotaRombelRepository;
import com.its.sia.repository.IpsRepository;
import com.its.sia.repository.RombelRepository;

@Service
public class IpsServiceImpl implements IpsService {

	private String[] column = {"ips.idIps","thnAjaran.thnThnAjaran","smt.nmSmt","pd.nimPd","pd.nmPd","pd.angkatanPd","ips.nilaiIps","ips.tglBuatIps"};
	private Boolean[] searchable = {false,true,true,true,true,true,true,true,true};	

	@Autowired
	private IpsRepository ipsRepository;
	
	@Override
	public List<Ips> get() {
		return get("");
	}

	@Override
	public List<Ips> get(String where) {
		return get(where,"");
	}

	@Override
	public List<Ips> get(String where, String order) {
		return get(where,order,-1,-1);
	}

	@Override
	public List<Ips> get(String where, String order, int limit, int offset) {
		return ipsRepository.get(where, order, limit, offset);
	}

	@Override
	public Ips getById(UUID idAnggotaRombel) {
		return ipsRepository.getById(idAnggotaRombel);
	}

	@Override
	public String save(Ips ips) {
		String where = "";
		if(ips.getIdIps()!=null) where += "ips.idIps !='"+ips.getIdIps()+"' AND ";
		List<Ips> listIps =  get(where+"pd.idPd ='"+ips.getPd().getIdPd()+"' AND tglSmt.idTglSmt = '"+ips.getTglSmt().getIdTglSmt()+"'");
		if(listIps.size()>0)
		{
			return null;
		}
		else if(ips.getIdIps() != null)
		{
			//update
			ipsRepository.update(ips);
			return ips.getIdIps().toString();
		}
		else
		{
			//insert
			return ipsRepository.insert(ips).toString();
		}
	}

	@Override
	public String delete(UUID idIps) {
		Ips ips = ipsRepository.getById(idIps);
		if(ips==null) return null;
		else{
			ipsRepository.delete(ips);
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
		List<Ips> queryResult = get("("+parameter.getWhere()+")"+dbFilter, parameter.getOrder(), iDisplayLength, iDisplayStart);
		List<String[]> aData = new ArrayList<String[]>();
		for (Ips ips : queryResult) {
			String[] anggotaRombelString = new String[8];
			anggotaRombelString[0] = ips.getIdIps().toString();
			anggotaRombelString[1] = String.valueOf(ips.getTglSmt().getThnAjaran().getThnThnAjaran());
			anggotaRombelString[2] = String.valueOf(ips.getTglSmt().getSmt().getNmSmt());
			anggotaRombelString[3] = String.valueOf(ips.getPd().getNimPd());
			anggotaRombelString[4] = String.valueOf(ips.getPd().getNmPd());
			anggotaRombelString[5] = String.valueOf(ips.getPd().getAngkatanPd());
			anggotaRombelString[6] = String.valueOf(ips.getNilaiIps());
			anggotaRombelString[7] = String.valueOf(ips.getTglBuatIps().toString("yyyy-MM-dd HH:mm:ss"));
			aData.add(anggotaRombelString);
		}
		rombelDatatable.setAaData(aData);
		rombelDatatable.setiTotalRecords(ipsRepository.count(filter));
		rombelDatatable.setiTotalDisplayRecords(ipsRepository.count("("+parameter.getWhere()+")"+dbFilter));

		return rombelDatatable;
	}

	@Override
	public Ips getIpsTerakhir(UUID idPd) {
		List<Ips> listIps = get("pd.idPd = '"+idPd+"' and smt.jenisSmt < 2", "ips.tglBuatIps desc", 1, -1);
		Ips ipsTerakhir = listIps.size()>0?listIps.get(0):null;
		if(ipsTerakhir != null) 
		{
			System.out.println(ipsTerakhir.getNilaiIps());
			ipsTerakhir.setNilaiIps( Math.round( ipsTerakhir.getNilaiIps() * 100.0) / 100.0);	
		}
		return ipsTerakhir;
	}
}
