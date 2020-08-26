package com.AIS.Modul.MataKuliah.Service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.AIS.Modul.MataKuliah.Repository.MateriSilabusRepository;
import com.sia.modul.domain.MK;
import com.sia.modul.domain.MateriSilabus;

@Service
public class MateriSilabusServiceImpl implements MateriSilabusService{

	@Autowired
	private MateriSilabusRepository materiSilabusRepo;
	
	@Override
	public String save(MateriSilabus mp) {
		// TODO Auto-generated method stub
		if(mp.getIdMateriSilabus() != null)
		{
			//update
			materiSilabusRepo.update(mp);
			return mp.getIdMateriSilabus().toString();
		}
		else
		{
			//insert
			return materiSilabusRepo.insert(mp).toString();
		}
	}

	@Override
	public MateriSilabus findById(UUID idMateriSilabus) {
		// TODO Auto-generated method stub
		return materiSilabusRepo.findById(idMateriSilabus);
	}

	@Override
	public String delete(UUID idMateriSilabus) {
		// TODO Auto-generated method stub
		MateriSilabus mp = materiSilabusRepo.findById(idMateriSilabus);
		if(mp==null) return null;
		else{
			mp.setStatusMateriSilabus(true);
			materiSilabusRepo.update(mp);
			return "Ok";
		}
	}

	@Override
	public List<MateriSilabus> findByRPPerTemu(UUID idRPPerTemu) {
		// TODO Auto-generated method stub
		return materiSilabusRepo.findByRPPerTemu(idRPPerTemu);
	}

}
