package com.siakad.modul_penilaian.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sia.modul.domain.Pd;
import com.siakad.modul_penilaian.repository.PdRepository;

@Service
public class PdServiceImpl implements PdService {
	@Autowired
	private PdRepository repositoryPd;
	
	@Override
	public List<Pd> ambilSemuaPd() {
		// TODO Auto-generated method stub
		return repositoryPd.get("aPdTerhapus = FALSE", "nimPd ASC", -1, -1);
	}

	@Override
	public Pd ambilPd(UUID idPd) {
		// TODO Auto-generated method stub
		return repositoryPd.getById(idPd);
	}

}
