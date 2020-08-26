package com.its.sia.repository;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.AnggotaRombel;
import com.sia.modul.domain.PresensiPd;

public interface PresensiPdRepository {
	public List<PresensiPd> get(String where, String order, int limit, int offset);
    public PresensiPd getById(UUID idPresensiPd);
    public UUID insert(PresensiPd presensiPd);
    public void update(PresensiPd presensiPd);
    public void delete(PresensiPd presensiPd);
    public long count(String where);
}
