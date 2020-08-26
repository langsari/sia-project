package com.bustan.siakad.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bustan.siakad.repository.EkuivalensiMKAlihjenjangRepository;
import com.sia.modul.domain.EkuivalensiMKAlihjenjang;
import com.sia.modul.domain.MK;
import com.sia.modul.domain.MKAlihjenjang;

@Service
public class EkuivalensiMKAlihjenjangServiceImpl implements EkuivalensiMKAlihjenjangService{

	@Autowired
	EkuivalensiMKAlihjenjangRepository ekuivalensiMKAlihjenjangRepository;
	
	@Override
	public List<EkuivalensiMKAlihjenjang> get() {
		return get("");
	}

	@Override
	public List<EkuivalensiMKAlihjenjang> get(String where) {
		return get(where,"");
	}

	@Override
	public List<EkuivalensiMKAlihjenjang> get(String where, String order) {
		return get(where,order,-1,-1);
	}

	@Override
	public List<EkuivalensiMKAlihjenjang> get(String where, String order, int limit,
			int offset) {
		return ekuivalensiMKAlihjenjangRepository.get(where,order,limit,offset);
	}

	@Override
	public EkuivalensiMKAlihjenjang getById(UUID idEkuivalensiMKAlihjenjang) {
		return ekuivalensiMKAlihjenjangRepository.getById(idEkuivalensiMKAlihjenjang);
	}

	@Override
	public String save(EkuivalensiMKAlihjenjang ekuivalensiMKAlihjenjang) {
		if(ekuivalensiMKAlihjenjang.getIdEkuivalensiMKAlihjenjang() != null)
		{
			//update
			ekuivalensiMKAlihjenjangRepository.update(ekuivalensiMKAlihjenjang);
			return ekuivalensiMKAlihjenjang.getIdEkuivalensiMKAlihjenjang().toString();
		}
		else
		{
			//insert
			return ekuivalensiMKAlihjenjangRepository.insert(ekuivalensiMKAlihjenjang).toString();
		}
	}

	@Override
	public String delete(UUID idEkuivalensiMKAlihjenjang) {
		EkuivalensiMKAlihjenjang ekuivalensiMKAlihjenjang = ekuivalensiMKAlihjenjangRepository.getById(idEkuivalensiMKAlihjenjang);
		if(ekuivalensiMKAlihjenjang == null) return null;
		ekuivalensiMKAlihjenjangRepository.delete(ekuivalensiMKAlihjenjang);
		return "ok";
	}

	@Override
	public List<MKAlihjenjang> getMKAlihjenjangDistinct(UUID idRelasiMKAlihjenjang) {
		return ekuivalensiMKAlihjenjangRepository.getMKAlihjenjangDistinct(idRelasiMKAlihjenjang);
	}

	@Override
	public List<MK> getMKDistinct(UUID idRelasiMKAlihjenjang) {
		return ekuivalensiMKAlihjenjangRepository.getMKDistinct(idRelasiMKAlihjenjang);
	}
}
