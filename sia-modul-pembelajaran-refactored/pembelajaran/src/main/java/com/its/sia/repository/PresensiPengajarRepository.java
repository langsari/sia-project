package com.its.sia.repository;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.AnggotaRombel;
import com.sia.modul.domain.PresensiPd;
import com.sia.modul.domain.PresensiPengajar;

public interface PresensiPengajarRepository {
	public List<PresensiPengajar> get(String where, String order, int limit, int offset);
    public PresensiPengajar getById(UUID idPresensiPengajar);
    public UUID insert(PresensiPengajar presensiPengajar);
    public void update(PresensiPengajar presensiPengajar);
    public void delete(PresensiPengajar presensiPengajar);
    public long count(String where);
}
