package com.siakad.modul_penilaian.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sia.modul.domain.PendidikPengajar;
import com.siakad.modul_penilaian.repository.PendidikPengajarRepository;

@Service
public class PendidikPengajarServiceImpl implements PendidikPengajarService {
	@Autowired
	private PendidikPengajarRepository repositoryPendidikPengajar;

	@Override
	public PendidikPengajar ambilKetuaPemb(UUID idPemb) {
		// TODO Auto-generated method stub
		return repositoryPendidikPengajar.getKetuaByPemb(idPemb);
	}
	
	@Override
	public void masukkanNilaiIpd(UUID idPemb, double nilai) {
		// TODO Auto-generated method stub
		repositoryPendidikPengajar.updateNilaiIpd(idPemb, nilai);
	}

	@Override
	public List<PendidikPengajar> ambilBerdasarkanPemb(UUID idPemb) {
		// TODO Auto-generated method stub
		return repositoryPendidikPengajar.getByPemb(idPemb);
	}
}
