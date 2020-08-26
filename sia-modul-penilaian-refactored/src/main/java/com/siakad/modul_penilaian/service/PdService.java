package com.siakad.modul_penilaian.service;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.Pd;

public interface PdService {
	public List<Pd> ambilSemuaPd();
	public Pd ambilPd(UUID idPd);
}
