package com.its.sia.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sia.modul.domain.PrasyaratMK;
import com.its.sia.repository.PrasyaratMKRepository;

@Service
public class PrasyaratMKServiceImpl implements PrasyaratMKService {
	@Autowired
	private PrasyaratMKRepository prasyaratMKRepository;
	
	@Autowired
	private AnggotaRombelService anggotaRombelService;
	
	@Autowired
	private PembService pembService;
	
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
	public List<PrasyaratMK> get(String where, String order, int limit, int offset) {
		return prasyaratMKRepository.get(where, order, limit, offset);
	}

	@Override
	public PrasyaratMK getById(UUID idPrasyaratMK) {
		return prasyaratMKRepository.getById(idPrasyaratMK);
	}

	@Override
	public String save(PrasyaratMK prasyaratMK) {
		if(prasyaratMK.getIdPrasyaratMK() != null)
		{
			//update
			return prasyaratMK.getIdPrasyaratMK().toString();
		}
		else
		{
			//insert
			return prasyaratMKRepository.insert(prasyaratMK).toString();
		}
	}

	@Override
	public String delete(UUID idPrasyaratMK) {
		PrasyaratMK prasyaratMK = prasyaratMKRepository.getById(idPrasyaratMK);
		if(prasyaratMK==null) return null;
		else{
			prasyaratMKRepository.update(prasyaratMK);
			return "Ok";
		}
	}
	
	@Override
	public Long count(String where) {
		// TODO Auto-generated method stub
		Long jumlah =prasyaratMKRepository.count(where);
		return jumlah;
	}

}
