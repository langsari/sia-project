package com.siakad.modul_penilaian.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sia.modul.domain.KonversiNilai;
import com.siakad.modul_penilaian.repository.KonversiNilaiRepository;

@Service
public class KonversiNilaiServiceImpl implements KonversiNilaiService {
	@Autowired
	private KonversiNilaiRepository repositoryKonversiNilai;
	
	@Override
	public List<KonversiNilai> ambilSemuaKonversiNilai() {
		// TODO Auto-generated method stub
		return repositoryKonversiNilai.getAll();
	}

	@Override
	public UUID tambahKonversiNilai(KonversiNilai konversi) {
		// TODO Auto-generated method stub
		return repositoryKonversiNilai.insert(konversi);
	}

	@Override
	public void hapusKonversiNilai(UUID idKonversi) {
		// TODO Auto-generated method stub
		repositoryKonversiNilai.delete(idKonversi);
	}

	@Override
	public void simpanKonversiNilai(KonversiNilai[] listKonversi) {
		// TODO Auto-generated method stub
		for (KonversiNilai konversi : listKonversi) {
			repositoryKonversiNilai.update(konversi);
		}
	}

	@Override
	public KonversiNilai ambilBerdasarkanBatas(double batas) {
		// TODO Auto-generated method stub
		List<KonversiNilai> listKonversi = repositoryKonversiNilai.getAll();
		for (KonversiNilai konversiNilai : listKonversi) {
			if(batas >= konversiNilai.getBatasBawah())
				return konversiNilai;
		}
		return null;
	}

}
