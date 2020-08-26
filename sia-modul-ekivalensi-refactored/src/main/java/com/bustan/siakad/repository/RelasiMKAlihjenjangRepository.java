package com.bustan.siakad.repository;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.RelasiMKAlihjenjang;

public interface RelasiMKAlihjenjangRepository {
	public List<RelasiMKAlihjenjang> get(String where, String order, int limit, int offset);
    public RelasiMKAlihjenjang getById(UUID idRelasiMKAlihjenjang);
    public List<Object[]> getRelasi(String where, String order, int limit, int offset);
    public UUID insert(RelasiMKAlihjenjang relasiMKAlihjenjang);
    public void update(RelasiMKAlihjenjang relasiMKAlihjenjang);
    public void delete(RelasiMKAlihjenjang relasiMKAlihjenjang);
    public long count(String where);
}
