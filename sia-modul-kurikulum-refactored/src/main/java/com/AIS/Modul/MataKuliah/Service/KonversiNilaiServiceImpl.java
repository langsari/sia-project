package com.AIS.Modul.MataKuliah.Service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.AIS.Modul.MataKuliah.Repository.KonversiNilaiRepository;
import com.sia.modul.domain.KonversiNilai;

@Service
public class KonversiNilaiServiceImpl implements KonversiNilaiService{

	@Autowired
	private KonversiNilaiRepository konversiNilaiRepo;
	
	@Override
	public List<KonversiNilai> findAll() {
		// TODO Auto-generated method stub
		return konversiNilaiRepo.findAll();
	}

	@Override
	public KonversiNilai findById(UUID idKonversi) {
		// TODO Auto-generated method stub
		return konversiNilaiRepo.findById(idKonversi);
	}

}
