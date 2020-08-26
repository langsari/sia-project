package com.bustan.siakad.repository;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.Kurikulum;
import com.sia.modul.domain.MK;
import com.sia.modul.domain.SatMan;
import com.sia.modul.domain.SatManMK;

public interface SatManMKRepository {
	public List<SatManMK> get(String where, String order, int limit, int offset);
    public SatManMK getById(UUID idSatManMK);
    public List<Kurikulum> getKurikulumDistinct(String where);
    public List<MK> getMKDistinct(String where, String order, int limit, int offset);
    public List<SatMan> getSatManDistinct(String where);
    public long count(String where);
}
