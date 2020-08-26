package com.its.sia.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sia.modul.domain.MK;
import com.sia.modul.domain.SatManMK;
import com.its.sia.repository.MKRepository;
import com.its.sia.repository.SatManMKRepository;

@Service
public class MKServiceImpl implements MKService {

	private String[] column = {"mk.idMK","mk.kodeMK","kurikulum.namaKurikulum","satMan.nmSatMan","rumpunMK.namaRumpunMK","mk.namaMK","mk.tingkatPemb","mk.deskripsiMK","mk.sifatMK","mk.statusMK"};
	private Boolean[] searchable = {false,true,true,true,true,true,false,true,true,false};

	@Autowired
	private MKRepository mkRepository;
	
	@Autowired
	private SatManMKService satManMKService;
	
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
		return mkRepository.get(where, order, limit, offset);
	}

	@Override
	public MK getById(UUID idMK) {
		return mkRepository.getById(idMK);
	}

	@Override
	public String save(MK mk) {
		if(mk.getIdMK() != null)
		{
			//update
			mkRepository.update(mk);
			return mk.getIdMK().toString();
		}
		else
		{
			//insert
			return mkRepository.insert(mk).toString();
		}
	}

	@Override
	public String delete(UUID idMK) {
		MK mk = mkRepository.getById(idMK);
		if(mk==null) return null;
		else{
			mk.setStatusMK(true);
			mkRepository.update(mk);
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
		List<MK> queryResult = get("("+parameter.getWhere()+")"+dbFilter, parameter.getOrder(), iDisplayLength, iDisplayStart);
		List<String[]> aData = new ArrayList<String[]>();
		DateFormat df = new SimpleDateFormat("dd-MM-yyy");
		for (MK mk : queryResult) {
			String[] mkString = new String[10];
			mkString[0] = mk.getIdMK().toString();
			mkString[1] = String.valueOf(mk.getKodeMK());
			mkString[2] = String.valueOf(mk.getKurikulum().getNamaKurikulum());
			mkString[3] = String.valueOf(mk.getKurikulum().getSatMan().getNmSatMan());
			mkString[4] = String.valueOf(mk.getRumpunMK()!=null?mk.getRumpunMK().getNamaRumpunMK():"");//rumpun matakuliah error
			mkString[5] = String.valueOf(mk.getNamaMK());
			List<SatManMK> query = satManMKService.get("mk.idMK='"+mk.getIdMK()+"'");
			String Tingkat = "";
			if(query.size()==1) Tingkat = query.get(0).getTingkatPemb().toString();
			else for (SatManMK satManMK : query) {
				Tingkat +=satManMK.getSatMan().getNmSatMan()+" <i class='fa fa-arrow-right'></i> Semester "+(satManMK.getTingkatPemb().toString())+"<br/>";
			}
			mkString[6] = Tingkat;
			mkString[7] = String.valueOf(mk.getDeskripsiMK());
			mkString[8] = String.valueOf(mk.getSifatMK());
			mkString[9] = String.valueOf(mk.getStatusMK());
			aData.add(mkString);
		}
		smtDatatable.setAaData(aData);
		smtDatatable.setiTotalRecords(mkRepository.count(filter));
		smtDatatable.setiTotalDisplayRecords(mkRepository.count("("+parameter.getWhere()+")"+dbFilter));

		return smtDatatable;
	}
}
