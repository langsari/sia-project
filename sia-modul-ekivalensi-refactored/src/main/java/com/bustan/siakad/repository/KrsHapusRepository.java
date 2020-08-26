package com.bustan.siakad.repository;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.KrsHapus;

public interface KrsHapusRepository {
	public List<KrsHapus> get(String where, String order, int limit, int offset);
    public KrsHapus getById(UUID idKrsHapus);
    public UUID insert(KrsHapus krsHapus);
    public void update(KrsHapus krsHapus);
    public void delete(KrsHapus krsHapus);
    public long count(String where);
}
