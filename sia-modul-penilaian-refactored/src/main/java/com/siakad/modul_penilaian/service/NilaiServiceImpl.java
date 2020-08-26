package com.siakad.modul_penilaian.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sia.modul.domain.Krs;
import com.sia.modul.domain.Nilai;
import com.siakad.modul_penilaian.repository.NilaiRepository;

@Service
public class NilaiServiceImpl implements NilaiService {
	@Autowired
	private NilaiRepository repositoryNilai;
	
	@Override
	public void masukkanNilai(List<Nilai> listNilai) {
		// TODO Auto-generated method stub
		repositoryNilai.insertBulk(listNilai);
	}

	@Override
	public List<Nilai> ambilNilaiKelas(List<Krs> listKrs) {
		// TODO Auto-generated method stub
		List<UUID> listIdKrs = new ArrayList<UUID>();
		for (Krs krs : listKrs) {
			listIdKrs.add(krs.getIdKrs());
		}
		return repositoryNilai.getByKrs(listIdKrs);
	}

	@Override
	public double ambilNilaiAkhir(Krs krs) {
		// TODO Auto-generated method stub
		return repositoryNilai.getNilaiAkhir(krs.getIdKrs());
	}

}
