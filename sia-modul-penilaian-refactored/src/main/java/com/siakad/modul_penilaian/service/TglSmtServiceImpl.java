package com.siakad.modul_penilaian.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sia.modul.domain.TglSmt;
import com.siakad.modul_penilaian.repository.TglSmtRepository;

@Service
public class TglSmtServiceImpl implements TglSmtService {
	@Autowired
	private TglSmtRepository repositoryTglSmt;
	
	@Override
	public TglSmt ambilTglSmtAktif() {
		// TODO Auto-generated method stub
		return repositoryTglSmt.getAktif();
	}

	@Override
	public List<TglSmt> ambilSemuaTglSmt() {
		// TODO Auto-generated method stub
		return repositoryTglSmt.getAll();
	}

}
