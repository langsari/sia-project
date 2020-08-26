package com.siakad.modul_penilaian.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sia.modul.domain.StatusKuisioner;
import com.siakad.modul_penilaian.repository.StatusKuisionerRepository;

@Service
public class StatusKuisionerServiceImpl implements StatusKuisionerService {
	@Autowired
	private StatusKuisionerRepository repositoryStatus;
	
	@Override
	public boolean apakahKuisionerTerisi(UUID idKrs, UUID idKuisioner) {
		// TODO Auto-generated method stub
		List<StatusKuisioner> daftarStatus = repositoryStatus.getByKrsKuisioner(idKrs, idKuisioner);
		if(daftarStatus.isEmpty()) {
			return false;
		}
		else {
			return daftarStatus.get(0).isaKuisionerTerisi();
		}
	}
	
	@Override
	public List<StatusKuisioner> ambilBerdasarkanPembKuisioner(UUID idPemb, UUID idKuisioner) {
		// TODO Auto-generated method stub
		return repositoryStatus.getByPembKuisioner(idPemb, idKuisioner);
	}
	
	@Override
	public List<StatusKuisioner> ambilBerdasarkanPemb(UUID idPemb) {
		// TODO Auto-generated method stub
		return repositoryStatus.getByPemb(idPemb);
	}

	@Override
	public UUID masukkanStatus(StatusKuisioner status) {
		// TODO Auto-generated method stub
		return repositoryStatus.insert(status);
	}

	@Override
	public List<StatusKuisioner> ambilBerdasarkanKrs(UUID idKrs) {
		// TODO Auto-generated method stub
		return repositoryStatus.getByKrs(idKrs);
	}

}
