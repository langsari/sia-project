package com.its.sia.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sia.modul.domain.SatManMK;
import com.its.sia.repository.SatManMKRepository;

@Service
public class SatManMKServiceImpl implements SatManMKService {

	@Autowired
	private SatManMKRepository satManMkRepository;
	
	@Override
	public List<SatManMK> get() {
		return get("");
	}

	@Override
	public List<SatManMK> get(String where) {
		return get(where,"");
	}

	@Override
	public List<SatManMK> get(String where, String order) {
		return get(where,order,-1,-1);
	}

	@Override
	public List<SatManMK> get(String where, String order, int limit, int offset) {
		return satManMkRepository.get(where, order, limit, offset);
	}

	@Override
	public SatManMK getById(UUID idSatManMK) {
		return satManMkRepository.getById(idSatManMK);
	}

	@Override
	public String save(SatManMK mk) {
		if(mk.getIdSatManMK() != null)
		{
			//update
			satManMkRepository.update(mk);
			return mk.getIdSatManMK().toString();
		}
		else
		{
			//insert
			return satManMkRepository.insert(mk).toString();
		}
	}

	@Override
	public String delete(UUID idSatManMK) {
		SatManMK mk = satManMkRepository.getById(idSatManMK);
		if(mk==null) return null;
		else{
			mk.setStatusSatManMK(true);
			satManMkRepository.update(mk);
			return "Ok";
		}
	}
}
