package com.siakad.modul_penilaian.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sia.modul.domain.Krs;
import com.siakad.modul_penilaian.repository.KrsRepository;

@Service
public class KrsServiceImpl implements KrsService {
	@Autowired
	private KrsRepository repositoryKrs;

	@Override
	public List<Krs> ambilKrsBerdasarkanPemb(UUID idPemb) {
		// TODO Auto-generated method stub
		return repositoryKrs.getByPemb(idPemb);
	}

	@Override
	public Krs ambilKrs(UUID idKrs) {
		// TODO Auto-generated method stub
		return repositoryKrs.getById(idKrs);
	}

	@Override
	public void perbaruiNilaiAkhir(Krs krs) {
		// TODO Auto-generated method stub
		repositoryKrs.update(krs);
	}

	@Override
	public double ambilNilaiMutu(UUID idKrs) {
		// TODO Auto-generated method stub
		Krs krs = repositoryKrs.getById(idKrs);
		if(krs.getKonversiNilai() != null)
			return krs.getKonversiNilai().getNilaiHuruf() * krs.getPemb().getMk().getJumlahSKS();
		else
			return 0;
	}
	
	@Override
	public List<Krs> ambilKrsBerdasarkanTglSmt(UUID idTglSmt) {
		// TODO Auto-generated method stub
		return repositoryKrs.getByTglSmt(idTglSmt);
	}

	@Override
	public List<Krs> ambilKrsAktifBerdasarkanPd(UUID idPd, UUID idTglSmt) {
		// TODO Auto-generated method stub
		return repositoryKrs.getAktifByPd(idPd, idTglSmt);
	}
	
	@Override
	public List<Krs> ambilKrsTerakhirBerdasarkanPd(UUID idPd) {
		// TODO Auto-generated method stub
		return repositoryKrs.getTerakhirByPd(idPd);
	}
	
	@Override
	public List<Krs> ambilSemuaBerdasarkanPd(UUID idPd) {
		return repositoryKrs.getAllByPd(idPd, "");
	}
	
}
