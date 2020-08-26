package com.AIS.Modul.MataKuliah.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.AIS.Modul.MataKuliah.Repository.SubCapPembRepository;
import com.sia.modul.domain.CapPemb;
import com.sia.modul.domain.SubCapPemb;

@Service
public class SubCapPembServiceImpl implements SubCapPembService {

	@Autowired
	private SubCapPembRepository subCapPembRepo;
	 

	@Override
	public String save(SubCapPemb subCapPembNew) {
		// TODO Auto-generated method stub
		if(subCapPembNew.getIdSubCapPemb() != null)
		{
			//update
			subCapPembRepo.update(subCapPembNew);
			return subCapPembNew.getIdSubCapPemb().toString();
		}
		else
		{
			//insert 
			return subCapPembRepo.insert(subCapPembNew).toString();
		}
	}
	
	private String [] column = {"child.idCapPemb","kur.thnMulai", "kur.namaKurikulum", 
			"child.namaCapPemb", "child.deskripsiCapPemb", "parent.namaCapPemb", "parent.deskripsiCapPemb", "child.statusCapPemb"};
	private Boolean[] searchable = {false,true,true,true,true,true,true,false};
	@Override
	public Datatable getdatatable(String sEcho, int iDisplayLength,
			int iDisplayStart, int iSortCol_0, String sSortDir_0,
			String sSearch, String filter) {
		// TODO Auto-generated method stub
		DatatableExtractParams parameter = new DatatableExtractParams(sSearch, this.column, this.searchable, iSortCol_0, sSortDir_0);
		Datatable subCapPembDatatable= new Datatable();
		subCapPembDatatable.setsEcho(sEcho);
		String dbFilter = "";
		if(filter != null && !filter.equals("")) dbFilter+=" AND "+filter; 
		List<SubCapPemb> queryResult = get("("+parameter.getWhere()+")"+dbFilter, parameter.getOrder(), iDisplayLength, iDisplayStart);
		List<String[]> aData = new ArrayList<String[]>();
		for (SubCapPemb subCapPemb : queryResult) {
			String[] subCapPembString = new String[9]; 
			subCapPembString[0] = subCapPemb.getChildCapPemb().getIdCapPemb().toString();
			subCapPembString[1] = String.valueOf(subCapPemb.getChildCapPemb().getKurikulum().getThnMulai());
			subCapPembString[2] = String.valueOf(subCapPemb.getChildCapPemb().getKurikulum().getNamaKurikulum()); 
			subCapPembString[3] = String.valueOf(subCapPemb.getChildCapPemb().getNamaCapPemb());
			subCapPembString[4] = String.valueOf(subCapPemb.getChildCapPemb().getDeskripsiCapPemb());
			if(subCapPemb.getParentCapPemb()!=null){ 
				subCapPembString[5] = String.valueOf(subCapPemb.getParentCapPemb().getNamaCapPemb());//disini harusnya capaian induk 
				subCapPembString[6] = String.valueOf(subCapPemb.getParentCapPemb().getDeskripsiCapPemb());
			}
			subCapPembString[7] = String.valueOf(subCapPemb.getChildCapPemb().isStatusCapPemb()); 
			aData.add(subCapPembString);
		}
		subCapPembDatatable.setAaData(aData);
		subCapPembDatatable.setiTotalRecords(subCapPembRepo.count(""));
		subCapPembDatatable.setiTotalDisplayRecords(subCapPembRepo.count("("+parameter.getWhere()+") AND "+filter));

		return subCapPembDatatable;
	}

	@Override
	public  List<SubCapPemb> get(String where, String order,
			int limit, int offset) {
		// TODO Auto-generated method stub
		return subCapPembRepo.get(where, order, limit, offset);
	}

	@Override
	public List<SubCapPemb> findParent(String idCapPemb) {
		// TODO Auto-generated method stub
		return subCapPembRepo.findParent(idCapPemb);
	}
	
	@Override
	public void delete(UUID idSubCapPemb) {
		// TODO Auto-generated method stub 
		 subCapPembRepo.delete(idSubCapPemb);
	}
 
 
}
