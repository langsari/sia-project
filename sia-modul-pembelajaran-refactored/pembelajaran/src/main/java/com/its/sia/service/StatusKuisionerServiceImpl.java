package com.its.sia.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sia.modul.domain.AnggotaRombel;
import com.sia.modul.domain.StatusKuisioner;
import com.sia.modul.domain.Rombel;
import com.its.sia.repository.AnggotaRombelRepository;
import com.its.sia.repository.StatusKuisionerRepository;
import com.its.sia.repository.RombelRepository;

@Service
public class StatusKuisionerServiceImpl implements StatusKuisionerService {

	@Autowired
	private StatusKuisionerRepository statusKuisionerRepository;
	
	@Override
	public List<StatusKuisioner> get() {
		return get("");
	}

	@Override
	public List<StatusKuisioner> get(String where) {
		return get(where,"");
	}

	@Override
	public List<StatusKuisioner> get(String where, String order) {
		return get(where,order,-1,-1);
	}

	@Override
	public List<StatusKuisioner> get(String where, String order, int limit, int offset) {
		return statusKuisionerRepository.get(where, order, limit, offset);
	}

	@Override
	public StatusKuisioner getById(UUID idAnggotaRombel) {
		return statusKuisionerRepository.getById(idAnggotaRombel);
	}

	@Override
	public String save(StatusKuisioner statusKuisioner) {
		String where = "";
		if(statusKuisioner.getIdStatusKuisioner() != null)
		{
			//update
			statusKuisionerRepository.update(statusKuisioner);
			return statusKuisioner.getIdStatusKuisioner().toString();
		}
		else
		{
			//insert
			return statusKuisionerRepository.insert(statusKuisioner).toString();
		}
	}

	@Override
	public String delete(UUID idStatusKuisioner) {
		StatusKuisioner statusKuisioner = statusKuisionerRepository.getById(idStatusKuisioner);
		if(statusKuisioner==null) return null;
		else{
			statusKuisionerRepository.delete(statusKuisioner);
			return "Ok";
		}
	}

}
