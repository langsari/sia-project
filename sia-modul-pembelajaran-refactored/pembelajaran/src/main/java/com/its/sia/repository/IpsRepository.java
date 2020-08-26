package com.its.sia.repository;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.AnggotaRombel;
import com.sia.modul.domain.Ips;

public interface IpsRepository {
	public List<Ips> get(String where, String order, int limit, int offset);
    public Ips getById(UUID idIps);
    public UUID insert(Ips ips);
    public void update(Ips ips);
    public void delete(Ips ips);
    public long count(String where);
}
