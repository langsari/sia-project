package com.bustan.siakad.repository;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.KonversiNilai;

public interface KonversiNilaiRepository {
	public List<KonversiNilai> get(String where, String order, int limit, int offset);
    public KonversiNilai getById(UUID idCalonPD);
    public long count(String where);
}
