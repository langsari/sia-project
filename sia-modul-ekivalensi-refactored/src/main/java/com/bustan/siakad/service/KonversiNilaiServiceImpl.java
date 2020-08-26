package com.bustan.siakad.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bustan.siakad.repository.KonversiNilaiRepository;
import com.sia.modul.domain.KonversiNilai;

@Service
public class KonversiNilaiServiceImpl implements KonversiNilaiService{
	
	@Autowired
	KonversiNilaiRepository konversiNilaiRepository;
	
	@Override
	public List<KonversiNilai> get() {
		return get("");
	}

	@Override
	public List<KonversiNilai> get(String where) {
		return get(where,"");
	}

	@Override
	public List<KonversiNilai> get(String where, String order) {
		return get(where,order,-1,-1);
	}

	@Override
	public List<KonversiNilai> get(String where, String order, int limit,
			int offset) {
		return konversiNilaiRepository.get(where,order,limit,offset);
	}

	@Override
	public KonversiNilai getById(UUID idHuruf) {
		return konversiNilaiRepository.getById(idHuruf);
	}
}
