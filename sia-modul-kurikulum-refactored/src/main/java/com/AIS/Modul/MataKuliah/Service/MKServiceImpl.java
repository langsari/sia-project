package com.AIS.Modul.MataKuliah.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.AIS.Modul.MataKuliah.Repository.MKRepository;
import com.sia.modul.domain.*;
 
@Service
public class MKServiceImpl implements MKService{

	@Autowired
	private MKRepository mkRepo;
	
	private String [] column = {"mk.idMK", "mk.kodeMK", "mk.namaMK", "kur.thnMulai", "rumpunMK.namaRumpunMK", "mk.tingkatPemb", 
			"mk.jumlahSKS", "mk.sifatMK", "kn.huruf", "mk.deskripsiMK", "mk.statusMK"};
	private Boolean[] searchable = {false,true,true,true,true,true,true,true,true,true,false};
	
	
	@Override
	public Datatable getdatatable(String sEcho, int iDisplayLength,
			int iDisplayStart, int iSortCol_0, String sSortDir_0,
			String sSearch, String filter) {
		// TODO Auto-generated method stub
			DatatableExtractParams parameter = new DatatableExtractParams(sSearch, this.column, this.searchable, iSortCol_0, sSortDir_0);
			Datatable mkDatatable= new Datatable();
			mkDatatable.setsEcho(sEcho);
			String dbFilter = "";
			if(filter != null && !filter.equals("")) dbFilter+=" AND "+filter; 
			List<MK> queryResult = get("("+parameter.getWhere()+")"+dbFilter, parameter.getOrder(), iDisplayLength, iDisplayStart);
			List<String[]> aData = new ArrayList<String[]>();
			for (MK mk : queryResult) {
				String[] mkString = new String[12];
				mkString[0] = mk.getIdMK().toString();
				mkString[1] = String.valueOf(mk.getKodeMK());
				mkString[2] = String.valueOf(mk.getNamaMK());
				mkString[3] = String.valueOf(mk.getKurikulum().getThnMulai());
				if(mk.getRumpunMK()!=null){

					mkString[4] = String.valueOf(mk.getRumpunMK().getNamaRumpunMK());
				}
				mkString[5] = String.valueOf(mk.getTingkatPemb());
				mkString[6] = String.valueOf(mk.getJumlahSKS());
				mkString[7] = String.valueOf(mk.getSifatMK());
				mkString[8] = String.valueOf(mk.getKonversiNilai().getHuruf());
				mkString[9] = String.valueOf(mk.getDeskripsiMK());
				mkString[10] = String.valueOf(mk.getStatusMK());
				mkString[11] = String.valueOf(mk.getStatusMK());
				aData.add(mkString);
			}                                                                        
			mkDatatable.setAaData(aData);
			mkDatatable.setiTotalRecords(mkRepo.count(""));
			mkDatatable.setiTotalDisplayRecords(mkRepo.count("("+parameter.getWhere()+") AND "+filter));

			return mkDatatable;
	}

	@Override
	public List<MK> get() {
		return get("");
	}

	@Override
	public List<MK> get(String where) {
		return get(where,"");
	}

	@Override
	public List<MK> get(String where, String order) {
		return get(where,order,-1,-1);
	}

	@Override
	public List<MK> get(String where, String order, int limit, int offset) {
		return mkRepo.get(where, order, limit, offset);
	}

	@Override
	public String save(MK mk) {
		// TODO Auto-generated method stub
		if(mk.getIdMK() != null)
		{
			//update
			mkRepo.update(mk);
			return mk.getIdMK().toString();
		}
		else
		{
			//insert
			return mkRepo.insert(mk).toString();
		}
	}

	@Override
	public MK findById(UUID idMK) {
		// TODO Auto-generated method stub
		return mkRepo.findById(idMK);
	}

	@Override
	public String delete(UUID idMK) {
		// TODO Auto-generated method stub
		MK mk = mkRepo.findById(idMK);
		if(mk==null) return null;
		else{
			mk.setStatusMK(true);
			mkRepo.update(mk);
			return "Ok";
		}
	}
	@Override
	public List<MK> findAll() {
		// TODO Auto-generated method stub
		return mkRepo.findAll();
	}

	@Override
	public MK findByIdString(String idMK) {
		// TODO Auto-generated method stub
		return mkRepo.findByIdString(idMK);
	}

}
