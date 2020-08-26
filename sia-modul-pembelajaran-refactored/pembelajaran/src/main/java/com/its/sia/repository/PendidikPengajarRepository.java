package com.its.sia.repository;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.AnggotaRombel;
import com.sia.modul.domain.Pemb;
import com.sia.modul.domain.PendidikPengajar;

public interface PendidikPengajarRepository {
	public List<PendidikPengajar> get(String where, String order, int limit, int offset);
    public PendidikPengajar getById(UUID idPendidikPengajar);
    public UUID insert(PendidikPengajar pendidikPengajar);
    public void update(PendidikPengajar pendidikPengajar);
    public void delete(PendidikPengajar pendidikPengajar);
    public long count(String where);
    public long countPemb(String where);
    public List<Pemb> getPemb(String where, String order, int limit, int offset);
}
