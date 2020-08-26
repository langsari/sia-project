package com.its.sia.repository;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.Ipk;
import com.sia.modul.domain.Pd;

public interface IpkRepository {
	public List<Ipk> get(String where, String order, int limit, int offset);
    public Ipk getById(UUID idIpk);
    public Ipk getByPd(Pd pd);
    public UUID insert(Ipk ipk);
    public void update(Ipk ipk);
    public void delete(Ipk ipk);
    public long count(String where);
}
