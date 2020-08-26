package com.its.sia.repository;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.AnggotaRombel;
import com.sia.modul.domain.Pemb;
import com.sia.modul.domain.PembSatMan;

public interface PembSatManRepository {
	public List<PembSatMan> get(String where, String order, int limit, int offset);
    public PembSatMan getById(UUID idAnggotaRombel);
    public UUID insert(PembSatMan anggotaRombel);
    public void update(PembSatMan anggotaRombel);
    public void delete(PembSatMan anggotaRombel);
    public long count(String where);
    public long countPemb(String where);
    public List<Pemb> getPemb(String where, String order, int limit, int offset);
}
