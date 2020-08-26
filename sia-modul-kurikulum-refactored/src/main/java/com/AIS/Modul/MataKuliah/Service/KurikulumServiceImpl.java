package com.AIS.Modul.MataKuliah.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.*;
import com.AIS.Modul.MataKuliah.Repository.KurikulumRepository;
import com.AIS.Modul.MataKuliah.Service.KurikulumService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Service
public class KurikulumServiceImpl implements KurikulumService {

	
	@Autowired
	private KurikulumRepository kurikulumRepo;
	
	@Autowired
	private SatManService satManServ;
	
	
	@Override
	public List<Kurikulum> findAll() {
		return kurikulumRepo.findAll();
	}

	@Override
	public Kurikulum findById(UUID idKurikulum) {
		return kurikulumRepo.findById(idKurikulum);
	
	}
  
	@Override
	public UUID convertToUUID(String source) {
		return UUID.fromString(source);
	}
	
	private String [] column = {"k.idKurikulum","k.namaKurikulum", "satMan.nmSatMan","k.thnMulai","k.thnAkhir","k.statusKurikulum"};
	private Boolean[] searchable = {false,true,true,true,true,false};
	
	@Override
	public List<Kurikulum> get(String where, String order, int limit, int offset) {
		return kurikulumRepo.get(where, order, limit, offset);
	}
	
	@Override
	public Datatable getdatatable(String sEcho, int iDisplayLength,
			int iDisplayStart, int iSortCol_0, String sSortDir_0,
			String sSearch, String filter) {
		// TODO Auto-generated method stub
		DatatableExtractParams parameter = new DatatableExtractParams(sSearch, this.column, this.searchable, iSortCol_0, sSortDir_0);
		Datatable kurikulumDatatable= new Datatable();
		kurikulumDatatable.setsEcho(sEcho);
		String dbFilter = "";
		if(filter != null && !filter.equals("")) dbFilter+=" AND "+filter; 
		List<Kurikulum> queryResult = get("("+parameter.getWhere()+")"+dbFilter, parameter.getOrder(), iDisplayLength, iDisplayStart);
		List<String[]> aData = new ArrayList<String[]>();
		for (Kurikulum kurikulum : queryResult) {
			String[] kurikulumString = new String[7];
			kurikulumString[0] = kurikulum.getIdKurikulum().toString();
			kurikulumString[1] = String.valueOf(kurikulum.getNamaKurikulum());
			kurikulumString[2] = String.valueOf(kurikulum.getSatMan().getNmSatMan());
			kurikulumString[3] = String.valueOf(kurikulum.getThnMulai());
			kurikulumString[4] = String.valueOf(kurikulum.getThnAkhir());
			kurikulumString[5] = String.valueOf(kurikulum.getStatusKurikulum());
			kurikulumString[6] = String.valueOf(kurikulum.getStatusKurikulum());
			aData.add(kurikulumString);
		}
		kurikulumDatatable.setAaData(aData);
		kurikulumDatatable.setiTotalRecords(kurikulumRepo.count(""));
		kurikulumDatatable.setiTotalDisplayRecords(kurikulumRepo.count("("+parameter.getWhere()+") AND "+filter));

		return kurikulumDatatable;
	}
	
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
	public String save(Kurikulum kurikulum) {
		if(kurikulum.getIdKurikulum() != null )
		{
			//update 
			kurikulumRepo.update(kurikulum);
			return kurikulum.getIdKurikulum().toString();
		}
		else
		{
			List<Kurikulum> kurikulumList = findAll();
			for (Kurikulum kurikulum2 : kurikulumList) {
				if(kurikulum.getSatMan().getIdSatMan()==kurikulum2.getSatMan().getIdSatMan() &&  
						kurikulum.getThnMulai().equals(kurikulum2.getThnMulai()) &&
							kurikulum.getThnAkhir().equals(kurikulum2.getThnAkhir())){  
							String message = null;
							return message;
				}
			} 
			//insert
//	        kurikulum.setaStatusKurikulum(true);
			return kurikulumRepo.insert(kurikulum).toString();
		}
	}

	@Override
	public String delete(UUID idKurikulum) {
		// TODO Auto-generated method stub
		Kurikulum kurikulum = kurikulumRepo.findById(idKurikulum);
		if(kurikulum==null) return null;
		else{
			kurikulum.setStatusKurikulum(true);
			kurikulumRepo.update(kurikulum);
			return "Ok";
		}
	}

	@Override
	public void activateKurikulum(UUID idKurikulum) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteKurikulum(UUID idKurikulum) {
		// TODO Auto-generated method stub
		
	}
}
