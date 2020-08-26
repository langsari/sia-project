package com.AIS.Modul.MataKuliah.Service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.AIS.Modul.MataKuliah.Repository.RPBentukPenilaianRepository;
import com.sia.modul.domain.RPBentukPenilaian;

@Service
public class RPBentukPenilaianServiceImpl implements RPBentukPenilaianService{

	@Autowired
	private RPBentukPenilaianRepository rpBentukPenilaianRepo;
	
	@Override
	public List<RPBentukPenilaian> findByRPPerTemu(UUID idRPPerTemu) {
		// TODO Auto-generated method stub
		return rpBentukPenilaianRepo.findByRPPerTemu(idRPPerTemu);
	}

	@Override
	public String save(RPBentukPenilaian mp) {
		// TODO Auto-generated method stub
		return rpBentukPenilaianRepo.insert(mp).toString(); 
	}

	@Override
	public List<RPBentukPenilaian> findAll() {
		// TODO Auto-generated method stub
		return rpBentukPenilaianRepo.findAll();
	}

}
