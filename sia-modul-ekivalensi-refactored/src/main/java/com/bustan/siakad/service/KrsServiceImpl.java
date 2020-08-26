package com.bustan.siakad.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bustan.siakad.repository.KrsRepository;
import com.sia.modul.domain.Krs;

@Service
public class KrsServiceImpl implements KrsService{

	@Autowired
	KrsRepository krsRepository;
	
	@Override
	public List<Krs> get() {
		return get("");
	}

	@Override
	public List<Krs> get(String where) {
		return get(where,"");
	}

	@Override
	public List<Krs> get(String where, String order) {
		return get(where,order,-1,-1);
	}

	@Override
	public List<Krs> get(String where, String order, int limit,
			int offset) {
		return krsRepository.get(where,order,limit,offset);
	}

	@Override
	public Krs getById(UUID idKrs) {
		return krsRepository.getById(idKrs);
	}

	@Override
	public String update(Krs krs) {
		krsRepository.update(krs);
		return krs.getIdKrs().toString();
	}
}
