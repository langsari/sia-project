package com.siakad.modul_penilaian.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sia.modul.domain.NilaiKuisioner;
import com.siakad.modul_penilaian.repository.NilaiKuisionerRepository;

@Service
public class NilaiKuisionerServiceImpl implements NilaiKuisionerService {
	@Autowired
	private NilaiKuisionerRepository repositoryNilaiKuisioner;
	
	@Override
	public UUID simpanNilaiKuisioner(NilaiKuisioner nilaiKuisioner) {
		// TODO Auto-generated method stub
		return repositoryNilaiKuisioner.insert(nilaiKuisioner);
	}

	@Override
	public Double ambilBerdasarkanPembPertanyaan(UUID idPemb, UUID idPertanyaan) {
		// TODO Auto-generated method stub
		List<NilaiKuisioner> daftarNilai = repositoryNilaiKuisioner.getByPembPertanyaan(idPemb, idPertanyaan);
		double totalNilai = 0;
		double count = 0;
		for (NilaiKuisioner nilai : daftarNilai) {
			totalNilai += nilai.getNilaiPertanyaan();
			count++;
		}
		if(count == 0)
			return null;
		else
			return totalNilai/count;
	}

}
