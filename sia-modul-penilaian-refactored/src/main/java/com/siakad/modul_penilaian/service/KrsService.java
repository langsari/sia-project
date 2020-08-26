package com.siakad.modul_penilaian.service;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.Krs;

public interface KrsService {	
	public void perbaruiNilaiAkhir(Krs krs);
	public double ambilNilaiMutu(UUID idKrs);
	public Krs ambilKrs(UUID idKrs);
	public List<Krs> ambilKrsBerdasarkanTglSmt(UUID idTglSmt);
	public List<Krs> ambilKrsBerdasarkanPemb(UUID idPemb);
	public List<Krs> ambilKrsAktifBerdasarkanPd(UUID idPd, UUID idTglSmt);
	public List<Krs> ambilKrsTerakhirBerdasarkanPd(UUID idPd);
	public List<Krs> ambilSemuaBerdasarkanPd(UUID idPd);
}
