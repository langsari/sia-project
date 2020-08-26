package com.AIS.Modul.MataKuliah.Service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.AIS.Modul.MataKuliah.Repository.DetailSilabusRepository;
import com.sia.modul.domain.DetailSilabus;
import com.sia.modul.domain.Pustaka;

@Service
public class DetailSilabusServiceImpl implements DetailSilabusService {

	@Autowired
	private DetailSilabusRepository detailSilabusRepo;
	
	@Override
	public String save(DetailSilabus detailSilabus) {
		// TODO Auto-generated method stub
		if(detailSilabus.getIdDetailSilabus() != null)
		{
			//update
			detailSilabusRepo.update(detailSilabus);
			return detailSilabus.getIdDetailSilabus().toString();
		}
		else
		{
			//insert
			return detailSilabusRepo.insert(detailSilabus).toString();
		}
	}

	@Override
	public DetailSilabus findById(UUID idDetailSilabus) {
		// TODO Auto-generated method stub
		return detailSilabusRepo.findById(idDetailSilabus);
	}

	@Override
	public List<DetailSilabus> findBySilabus(UUID idSilabus) {
		// TODO Auto-generated method stub
		return detailSilabusRepo.findBySilabus(idSilabus);
	}

	@Override
	public String delete(UUID idDetailSilabus) {
		// TODO Auto-generated method stub
		DetailSilabus detailSilabus = detailSilabusRepo.findById(idDetailSilabus);
		if(detailSilabus==null) return null;
		else{
			detailSilabus.setStatusDetailSilabus(true);
			detailSilabusRepo.update(detailSilabus);
			return "Ok";
		}
	}

	@Override
	public List<DetailSilabus> findAll() {
		// TODO Auto-generated method stub
		return detailSilabusRepo.findAll();
	}

	@Override
	public List<DetailSilabus> findByMK(UUID idMK) {
		// TODO Auto-generated method stub
		return detailSilabusRepo.findByMK(idMK);
	}

}
