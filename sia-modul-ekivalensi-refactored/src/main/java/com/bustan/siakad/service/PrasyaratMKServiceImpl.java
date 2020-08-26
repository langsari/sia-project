package com.bustan.siakad.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bustan.siakad.repository.PrasyaratMKRepository;
import com.sia.modul.domain.PrasyaratMK;

@Service
public class PrasyaratMKServiceImpl implements PrasyaratMKService{
	
	@Autowired
	PrasyaratMKRepository prasyaratMKRepository;
	
	@Override
	public List<PrasyaratMK> get() {
		return get("");
	}

	@Override
	public List<PrasyaratMK> get(String where) {
		return get(where,"");
	}

	@Override
	public List<PrasyaratMK> get(String where, String order) {
		return get(where,order,-1,-1);
	}

	@Override
	public List<PrasyaratMK> get(String where, String order, int limit,
			int offset) {
		return prasyaratMKRepository.get(where,order,limit,offset);
	}

	@Override
	public PrasyaratMK getById(UUID idPrasyaratMK) {
		return prasyaratMKRepository.getById(idPrasyaratMK);
	}
}
