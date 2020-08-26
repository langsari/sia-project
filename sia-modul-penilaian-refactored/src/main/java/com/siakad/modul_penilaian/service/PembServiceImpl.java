package com.siakad.modul_penilaian.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sia.modul.domain.Pemb;
import com.siakad.modul_penilaian.repository.PembRepository;

@Service
public class PembServiceImpl implements PembService {
	@Autowired
	private PembRepository repositoryPemb;
	
	@Override
	public List<Pemb> ambilSemuaPemb() {
		// TODO Auto-generated method stub
		return repositoryPemb.getAll();
	}
	
	@Override
	public List<Pemb> ambilBerdasarkanTglSmt(UUID idTglSmt) {
		// TODO Auto-generated method stub
		return repositoryPemb.getByTglSmt(idTglSmt);
	}
	
	@Override
	public List<Pemb> ambilBerdasarkanTglSmtPtk(UUID idTglSmt, UUID idPtk) {
		// TODO Auto-generated method stub
		return repositoryPemb.getByTglSmtPtk(idTglSmt, idPtk);
	}

	@Override
	public Pemb ambilPemb(UUID idPemb) {
		// TODO Auto-generated method stub
		return repositoryPemb.getById(idPemb);
	}

}
