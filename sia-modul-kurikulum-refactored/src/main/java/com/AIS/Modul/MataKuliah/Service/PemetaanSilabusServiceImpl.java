package com.AIS.Modul.MataKuliah.Service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.AIS.Modul.MataKuliah.Repository.PemetaanSilabusRepository;
import com.sia.modul.domain.PemetaanSilabus;
import com.sia.modul.domain.RumpunMK;

@Service
public class PemetaanSilabusServiceImpl implements PemetaanSilabusService{

	@Autowired
	private PemetaanSilabusRepository pemetaanSilabusRepo;
	
	@Override
	public String save(PemetaanSilabus ps) {
		// TODO Auto-generated method stub
		if(ps.getIdPemetaanSilabus() != null)
		{
			//update
			pemetaanSilabusRepo.update(ps);
			return ps.getIdPemetaanSilabus().toString();
		}
		else
		{
			//insert
			return pemetaanSilabusRepo.insert(ps).toString();
		}
	}

	@Override
	public List<PemetaanSilabus> findByDetailSilabus(UUID idDetailSilabus) {
		// TODO Auto-generated method stub
		return pemetaanSilabusRepo.findByDetailSilabus(idDetailSilabus.toString());
	}

	@Override
	public String delete(UUID idPemetaanSilabus) {
		// TODO Auto-generated method stub
		PemetaanSilabus ps = pemetaanSilabusRepo.findById(idPemetaanSilabus);
		if(ps==null) return null;
		else{
			ps.setStatusPemetaanSilabus(true);
			pemetaanSilabusRepo.update(ps);
			return "Ok";
		}
	}

	@Override
	public List<PemetaanSilabus> findByMateriSilabus(UUID idMateriSilabus) {
		// TODO Auto-generated method stub
		return pemetaanSilabusRepo.findByMateriSilabus(idMateriSilabus);
	}

}
