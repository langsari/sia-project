package com.siakad.modul_penilaian.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sia.modul.domain.PertanyaanKuisioner;
import com.siakad.modul_penilaian.repository.PertanyaanKuisionerRepository;

@Service
public class PertanyaanKuisionerServiceImpl implements
		PertanyaanKuisionerService {
	@Autowired
	private PertanyaanKuisionerRepository repositoryPertanyaan;

	@Override
	public List<PertanyaanKuisioner> ambilBerdasarKuisioner(UUID idKuisioner) {
		// TODO Auto-generated method stub
		return repositoryPertanyaan.getByIdKuisioner(idKuisioner);
	}
	
	@Override
	public PertanyaanKuisioner getById(UUID idPertanyaan) {
		// TODO Auto-generated method stub
		return repositoryPertanyaan.getById(idPertanyaan);
	}
	
	@Override
	public UUID tambahPertanyaan(PertanyaanKuisioner pertanyaan) {
		// TODO Auto-generated method stub
		return repositoryPertanyaan.insert(pertanyaan);
	}
	
	@Override
	public void simpanPertanyaan(PertanyaanKuisioner pertanyaan) {
		// TODO Auto-generated method stub
		repositoryPertanyaan.update(pertanyaan);
	}

	@Override
	public void hapusPertanyaan(UUID idPertanyaan) {
		// TODO Auto-generated method stub
		repositoryPertanyaan.delete(idPertanyaan);
	}

}
