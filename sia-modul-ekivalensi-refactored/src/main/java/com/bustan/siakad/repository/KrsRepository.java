package com.bustan.siakad.repository;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.Krs;
import com.sia.modul.domain.KrsHapus;

public interface KrsRepository {
	public List<Krs> get(String where, String order, int limit, int offset);
    public Krs getById(UUID idKrs);
    public void update(Krs krs);
}
