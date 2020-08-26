package com.bustan.siakad.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sia.modul.domain.Ptk;
import com.bustan.siakad.repository.PtkRepository;;

@Service
public class PtkServiceImpl implements PtkService {

	@Autowired
	private PtkRepository ptkRepository;

	@Autowired
	private PdService pdService;
	
	@Override
	public List<Ptk> get() {
		return get("");
	}

	@Override
	public List<Ptk> get(String where) {
		return get(where,"");
	}

	@Override
	public List<Ptk> get(String where, String order) {
		return get(where,order,-1,-1);
	}

	@Override
	public List<Ptk> get(String where, String order, int limit, int offset) {
		return ptkRepository.get(where, order, limit, offset);
	}

	@Override
	public Ptk getById(UUID idPtk) {
		return ptkRepository.getById(idPtk);
	}
}
