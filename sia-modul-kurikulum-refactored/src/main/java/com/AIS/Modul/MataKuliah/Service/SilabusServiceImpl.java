package com.AIS.Modul.MataKuliah.Service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.AIS.Modul.MataKuliah.Repository.SilabusRepository;
import com.sia.modul.domain.Silabus;

@Service
public class SilabusServiceImpl implements SilabusService {

	@Autowired
	private SilabusRepository silabusRepo;
	
	@Override
	public String save(Silabus silabus) { 
		return silabusRepo.insert(silabus).toString(); 
	}

	@Override
	public Silabus findByMK(UUID idMK) {
		return silabusRepo.findByMK(idMK);
		// TODO Auto-generated method stub
		
	}

	@Override
	public Silabus findById(UUID idSilabus) {
		// TODO Auto-generated method stub
		return silabusRepo.findById(idSilabus);
	}

	@Override
	public List<Silabus> findAll() {
		// TODO Auto-generated method stub
		return silabusRepo.findAll();
	}
}
