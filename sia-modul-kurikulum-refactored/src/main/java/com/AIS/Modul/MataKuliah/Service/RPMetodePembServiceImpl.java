package com.AIS.Modul.MataKuliah.Service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.AIS.Modul.MataKuliah.Repository.RPMetodePembRepository;
import com.sia.modul.domain.MateriSilabus;
import com.sia.modul.domain.RPMetodePemb;

@Service
public class RPMetodePembServiceImpl implements RPMetodePembService {

	@Autowired
	private RPMetodePembRepository rpMetodePembRepo;
	
	@Override
	public List<RPMetodePemb> findByRPPerTemu(UUID idRPPerTemu) {
		// TODO Auto-generated method stub
		return rpMetodePembRepo.findByRPPerTemu(idRPPerTemu);
	}

	@Override
	public String save(RPMetodePemb mp) { 
		//insert
		return rpMetodePembRepo.insert(mp).toString(); 
	}

	@Override
	public String delete(UUID idRPMetodePemb) {
		// TODO Auto-generated method stub
		RPMetodePemb mp = rpMetodePembRepo.findById(idRPMetodePemb);
		if(mp==null) return null;
		else{ 
			return "Ok";
		}
	}

	@Override
	public List<RPMetodePemb> findAll() {
		// TODO Auto-generated method stub
		return rpMetodePembRepo.findAll();
	}

}
