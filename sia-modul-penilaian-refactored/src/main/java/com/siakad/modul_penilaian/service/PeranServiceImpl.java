package com.siakad.modul_penilaian.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sia.main.domain.Peran;
import com.siakad.modul_penilaian.repository.PeranRepository;

@Service
public class PeranServiceImpl implements PeranService {

	@Autowired
	private PeranRepository peranRepository;
	
	@Override
	public List<Peran> get() {
		return get("");
	}

	@Override
	public List<Peran> get(String where) {
		return get(where,"");
	}

	@Override
	public List<Peran> get(String where, String order) {
		return get(where,order,-1,-1);
	}

	@Override
	public List<Peran> get(String where, String order, int limit, int offset) {
		return peranRepository.get(where, order, limit, offset);
	}

	@Override
	public Peran getById(UUID idAnggotaRombel) {
		return peranRepository.getById(idAnggotaRombel);
	}

	@Override
	public String save(Peran peran) {
		String where = "";
		if(peran.getIdPeran() != null)
		{
			//update
			peranRepository.update(peran);
			return peran.getIdPeran().toString();
		}
		else
		{
			//insert
			return peranRepository.insert(peran).toString();
		}
	}

	@Override
	public String delete(UUID idPeran) {
		Peran peran = peranRepository.getById(idPeran);
		if(peran==null) return null;
		else{
			peranRepository.delete(peran);
			return "Ok";
		}
	}

}
