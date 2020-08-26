package com.bustan.siakad.repository;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.MKAlihjenjang;

public interface MKAlihjenjangRepository {
	public List<MKAlihjenjang> get(String where, String order, int limit, int offset);
    public MKAlihjenjang getById(UUID idMKAlihjenjang);
    public UUID insert(MKAlihjenjang mkAlihjenjang);
    public void update(MKAlihjenjang mkAlihjenjang);
    public void delete(MKAlihjenjang mkAlihjenjang);
    public long count(String where);
}
