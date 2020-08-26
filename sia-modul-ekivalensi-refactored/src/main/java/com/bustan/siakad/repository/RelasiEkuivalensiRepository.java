package com.bustan.siakad.repository;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.RelasiEkuivalensi;

public interface RelasiEkuivalensiRepository {
	public List<RelasiEkuivalensi> get(String where, String order, String groupby, int limit, int offset);
	public List<Object[]> getA(String where, String order, int limit, int offset);
	public List<Object[]> getUUID(String where, String order, int limit, int offset);
    public RelasiEkuivalensi getById(UUID idRelasiEkuivalensi);
    public UUID insert(RelasiEkuivalensi relasiEkuivalensi);
    public void update(RelasiEkuivalensi relasiEkuivalensi);
    public void delete(RelasiEkuivalensi relasiEkuivalensi);
    public long count(String where);
}
