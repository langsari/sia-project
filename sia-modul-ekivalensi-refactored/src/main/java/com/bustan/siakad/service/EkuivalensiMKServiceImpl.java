package com.bustan.siakad.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bustan.siakad.repository.EkuivalensiMKRepository;
import com.sia.modul.domain.EkuivalensiMK;
import com.sia.modul.domain.MK;

@Service
public class EkuivalensiMKServiceImpl implements EkuivalensiMKService{

	@Autowired
	EkuivalensiMKRepository ekuivalensiMKRepository;
	
	@Override
	public List<EkuivalensiMK> get() {
		return get("");
	}

	@Override
	public List<EkuivalensiMK> get(String where) {
		return get(where,"");
	}

	@Override
	public List<EkuivalensiMK> get(String where, String order) {
		return get(where,order,-1,-1);
	}

	@Override
	public List<EkuivalensiMK> get(String where, String order, int limit,
			int offset) {
		return ekuivalensiMKRepository.get(where,order,limit,offset);
	}

	@Override
	public EkuivalensiMK getById(UUID idCalonPD) {
		return ekuivalensiMKRepository.getById(idCalonPD);
	}

	@Override
	public String save(EkuivalensiMK ekuivalensiMK) {
		if(ekuivalensiMK.getIdEkuivalensiMK() != null)
		{
			//update
			ekuivalensiMKRepository.update(ekuivalensiMK);
			return ekuivalensiMK.getIdEkuivalensiMK().toString();
		}
		else
		{
			//insert
			return ekuivalensiMKRepository.insert(ekuivalensiMK).toString();
		}
	}

	@Override
	public String delete(UUID idEkuivalensiMK) {
		EkuivalensiMK ekuivalensiMK = ekuivalensiMKRepository.getById(idEkuivalensiMK);
		if(ekuivalensiMK == null) return null;
		else
		{
			ekuivalensiMKRepository.delete(ekuivalensiMK);
		}
		
		return "Ok";
	}

	@Override
	public List<MK> getMKLamaDistinct(UUID idRelasiEkuivalensi) {
		return ekuivalensiMKRepository.getMKLamaDistinct(idRelasiEkuivalensi);
	}

	@Override
	public List<MK> getMKBaruDistinct(UUID idRelasiEkuivalensi) {
		return ekuivalensiMKRepository.getMKBaruDistinct(idRelasiEkuivalensi);
	}
	
	@Override
	public List<Object> getDistinctRelasiByMKLama(UUID idMKLama){
		return ekuivalensiMKRepository.getDistinctRelasiByMKLama(idMKLama);
	}
	
	@Override
	public List<Object> getDistinctRelasiByMKBaru(UUID idMKBaru){
		return ekuivalensiMKRepository.getDistinctRelasiByMKBaru(idMKBaru);
	}
}
