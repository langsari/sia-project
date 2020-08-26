package com.siakad.modul_penilaian.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sia.modul.domain.KomponenNilai;
import com.siakad.modul_penilaian.repository.KomponenNilaiRepository;

@Service
public class KomponenNilaiServiceImpl implements KomponenNilaiService {
	@Autowired
	private KomponenNilaiRepository repositoryKompNilai;
	
	@Override
	public List<KomponenNilai> ambilSemuaKomponen(UUID idPemb) {
		// TODO Auto-generated method stub
		return repositoryKompNilai.get("id_pemb='" + idPemb + "' AND a_komp_aktif=TRUE", "", -1, -1);
	}

	@Override
	public UUID tambahKomponen(KomponenNilai komp) {
		// TODO Auto-generated method stub
		return repositoryKompNilai.insert(komp);
	}

	@Override
	public void simpanKomponen(List<KomponenNilai> listKomponen) {
		// TODO Auto-generated method stub
		for (KomponenNilai komponen : listKomponen) {
			repositoryKompNilai.update(komponen);
		}
	}

	@Override
	public void hapusKomponen(UUID idKomp) {
		// TODO Auto-generated method stub
		repositoryKompNilai.delete(idKomp);
	}

	@Override
	public KomponenNilai ambilKomponen(UUID idKomp) {
		// TODO Auto-generated method stub
		return repositoryKompNilai.getById(idKomp);
	}

}
