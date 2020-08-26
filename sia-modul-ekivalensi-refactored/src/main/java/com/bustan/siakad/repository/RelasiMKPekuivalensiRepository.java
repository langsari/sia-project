package com.bustan.siakad.repository;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.RelasiMKPekuivalensi;

public interface RelasiMKPekuivalensiRepository {
	public List<RelasiMKPekuivalensi> get(String where, String order, int limit, int offset);
    public RelasiMKPekuivalensi getById(UUID idRelasiMKPekuivalensi);
    public UUID insert(RelasiMKPekuivalensi relasiMKPekuivalensi);
    public void update(RelasiMKPekuivalensi relasiMKPekuivalensi);
    public void delete(RelasiMKPekuivalensi relasiMKPekuivalensi);
    public long count(String where);
}
