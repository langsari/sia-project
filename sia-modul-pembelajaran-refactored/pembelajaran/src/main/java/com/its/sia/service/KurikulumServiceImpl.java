package com.its.sia.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sia.modul.domain.Kurikulum;
import com.sia.modul.domain.Smt;
import com.sia.modul.domain.ThnAjaran;
import com.its.sia.repository.KurikulumRepository;
import com.its.sia.repository.SmtRepository;;

@Service
public class KurikulumServiceImpl implements KurikulumService {

	private String[] column = {"kurikulum.idKurikulum","satMan.nmSatMan","kurikulum.nmKurikulum","kurikulum.thnMulai","kurikulum.thnAkhir","kurikulum.aStatusKuriukulum"};
	private Boolean[] searchable = {false,true,true,true,true,false};	

	@Autowired
	private KurikulumRepository kurikulumRepository;
	
	@Override
	public List<Kurikulum> get() {
		return get("");
	}

	@Override
	public List<Kurikulum> get(String where) {
		return get(where,"");
	}

	@Override
	public List<Kurikulum> get(String where, String order) {
		return get(where,order,-1,-1);
	}

	@Override
	public List<Kurikulum> get(String where, String order, int limit, int offset) {
		return kurikulumRepository.get(where, order, limit, offset);
	}

	@Override
	public Kurikulum getById(UUID idSmt) {
		return kurikulumRepository.getById(idSmt);
	}

	@Override
	public String save(Kurikulum kurikulum) {
		if(kurikulum.getIdKurikulum() != null)
		{
			//update
			kurikulumRepository.update(kurikulum);
			return kurikulum.getIdKurikulum().toString();
		}
		else
		{
			//insert
			return kurikulumRepository.insert(kurikulum).toString();
		}
	}

	@Override
	public String delete(UUID idSmt) {
		Kurikulum kurikulum = kurikulumRepository.getById(idSmt);
		if(kurikulum==null) return null;
		else{
			kurikulumRepository.delete(kurikulum);
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
		List<Kurikulum> queryResult = get("("+parameter.getWhere()+")"+dbFilter, parameter.getOrder(), iDisplayLength, iDisplayStart);
		List<String[]> aData = new ArrayList<String[]>();
		for (Kurikulum kurikulum : queryResult) {
			String[] kurikulumString = new String[6];
			kurikulumString[0] = kurikulum.getIdKurikulum().toString();
			kurikulumString[1] = String.valueOf(kurikulum.getSatMan().getNmSatMan());
			kurikulumString[2] = String.valueOf(kurikulum.getNamaKurikulum());
			kurikulumString[3] = String.valueOf(kurikulum.getThnMulai());
			kurikulumString[4] = String.valueOf(kurikulum.getThnAkhir());
			kurikulumString[5] = String.valueOf(kurikulum.getStatusKurikulum());
			aData.add(kurikulumString);
		}
		smtDatatable.setAaData(aData);
		smtDatatable.setiTotalRecords(kurikulumRepository.count(""));
		smtDatatable.setiTotalDisplayRecords(kurikulumRepository.count("("+parameter.getWhere()+")"+dbFilter));

		return smtDatatable;
	}
}
