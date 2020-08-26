package com.bustan.siakad.service;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.KonversiNilai;

public interface KonversiNilaiService {
	public List<KonversiNilai> get();
	public List<KonversiNilai> get(String where);
	public List<KonversiNilai> get(String where, String order);
	public List<KonversiNilai> get(String where, String order, int limit, int offset);
	public KonversiNilai getById(UUID idCalonPD);
}
