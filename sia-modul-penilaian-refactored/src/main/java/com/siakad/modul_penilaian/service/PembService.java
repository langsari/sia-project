package com.siakad.modul_penilaian.service;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.Pemb;

public interface PembService {
	public List<Pemb> ambilSemuaPemb();
	public List<Pemb> ambilBerdasarkanTglSmt(UUID idTglSmt);
	public List<Pemb> ambilBerdasarkanTglSmtPtk(UUID idTglSmt, UUID idPtk);
	public Pemb ambilPemb(UUID idPemb);
}
