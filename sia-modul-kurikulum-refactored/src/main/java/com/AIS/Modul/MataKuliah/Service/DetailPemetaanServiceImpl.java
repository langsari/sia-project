package com.AIS.Modul.MataKuliah.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.AIS.Modul.MataKuliah.Repository.DetailPemetaanRepository;
import com.sia.modul.domain.DetailPemetaan;
import com.sia.modul.domain.MK;
import com.sia.modul.domain.RP;
import com.sia.modul.domain.SubCapPemb;

@Service
public class DetailPemetaanServiceImpl implements DetailPemetaanService {

	@Autowired
	public DetailPemetaanRepository detailPemetaanRepo;
	
	@Autowired
	public CapPembMKService capPembMKServ;
	
	@Autowired
	public MKService mkServ;
	
	@Override
	public boolean findRP(UUID idMK){
		boolean idRP = detailPemetaanRepo.findRP(idMK);
		return idRP;
	}

	private String [] column = {"rppt.idRPPerTemu", "mk.namaMK", "rppt.mingguPembKe", "rppt.materiPemb", "rppt.target", "cpmk.namaCapPembMK", "rppt.bahanKajian", 
			"rppt.metodePemb", "rppt.waktuPemb", "rppt.bentukPenilaian", "rppt.bobotPenilaian", "rppt.referensiPemb", "rppt.statusRPPerTemu"};
	private Boolean[] searchable = {false, true,true,true,true,false,true,false,true,true,true,false, false};
	
	@Override
	public Datatable getdatatable(String sEcho, int iDisplayLength,
			int iDisplayStart, int iSortCol_0, String sSortDir_0,
			String sSearch, String filter) {
		// TODO Auto-generated method stub
//		DatatableExtractParams parameter = new DatatableExtractParams(sSearch, this.column, this.searchable, iSortCol_0, sSortDir_0);
//		Datatable detailPemetaanDatatable= new Datatable();
//		detailPemetaanDatatable.setsEcho(sEcho);
//		String dbFilter = "";
//		if(filter != null && !filter.equals("")) dbFilter+=" AND "+filter; 
//		List<DetailPemetaan> queryResult = get("("+parameter.getWhere()+")"+dbFilter, parameter.getOrder(), iDisplayLength, iDisplayStart);
//		List<String[]> aData = new ArrayList<String[]>();
//		for (DetailPemetaan detailPemetaan : queryResult) {
//			String[] detailPemetaanString = new String[14]; 
//			detailPemetaanString[0] = detailPemetaan.getRpPerTemu().getIdRPPerTemu().toString();
//			detailPemetaanString[1] = String.valueOf(detailPemetaan.getCapPembMK().getMk().getNamaMK());
////			detailPemetaanString[1] = String.valueOf(mkObj.getNamaMK());
//			detailPemetaanString[2] = String.valueOf(detailPemetaan.getRpPerTemu().getMingguPembKe());
//			detailPemetaanString[3] = String.valueOf(detailPemetaan.getRpPerTemu().getMateriPemb());
//			detailPemetaanString[4] = String.valueOf(detailPemetaan.getRpPerTemu().getTarget());
//			if(detailPemetaan.getCapPembMK()!=null){
//				detailPemetaanString[5] = String.valueOf(detailPemetaan.getCapPembMK().getNamaCapPembMK());
//			} 
//			detailPemetaanString[6] = String.valueOf(detailPemetaan.getRpPerTemu().getBahanKajian());
//			detailPemetaanString[7] = String.valueOf(detailPemetaan.getRpPerTemu().getMetodePemb()); 
//			detailPemetaanString[8] = String.valueOf(detailPemetaan.getRpPerTemu().getWaktuPemb());
//			detailPemetaanString[9] = String.valueOf(detailPemetaan.getRpPerTemu().getBentukPenilaian());
//			detailPemetaanString[10] = String.valueOf(detailPemetaan.getRpPerTemu().getBobotPenilaian());
//			detailPemetaanString[11] = String.valueOf(detailPemetaan.getRpPerTemu().getReferensiPemb());
//			detailPemetaanString[12] = String.valueOf(detailPemetaan.getRpPerTemu().isStatusRPPerTemu());
//			detailPemetaanString[13] = String.valueOf(detailPemetaan.getRpPerTemu().isStatusRPPerTemu());
//			aData.add(detailPemetaanString);
//		}
//		detailPemetaanDatatable.setAaData(aData);
//		detailPemetaanDatatable.setiTotalRecords(detailPemetaanRepo.count(""));
//		detailPemetaanDatatable.setiTotalDisplayRecords(detailPemetaanRepo.count("("+parameter.getWhere()+") AND "+filter));

//		return detailPemetaanDatatable;
		return null;
	}

	private List<DetailPemetaan> get(String where, String order,
			int limit, int offset) {
		// TODO Auto-generated method stub
		return detailPemetaanRepo.get(where, order, limit, offset);
	}

	@Override
	public String save(DetailPemetaan detailPemetaanNew) {
		// TODO Auto-generated method stub
		if(detailPemetaanNew.getIdDetailPemetaan() != null)
		{
			//update
			detailPemetaanRepo.update(detailPemetaanNew);
			return detailPemetaanNew.getIdDetailPemetaan().toString();
		}
		else
		{
			//insert 
			return detailPemetaanRepo.insert(detailPemetaanNew).toString();
		}
	}

	@Override
	public List<DetailPemetaan> findCapPembMK(String idRPPerTemu) {
		// TODO Auto-generated method stub
		return detailPemetaanRepo.findCapPembMK(idRPPerTemu);
	}

	@Override
	public void delete(UUID idDetailPemetaan) {
		// TODO Auto-generated method stub
		
	}
 
	
}
