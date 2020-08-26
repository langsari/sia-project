package com.bustan.siakad.repository;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.MKWajibPd;

public interface MKWajibPdRepository {
	public List<MKWajibPd> get(String where, String order, int limit, int offset);
    public MKWajibPd getById(UUID idMKWajibPd);
    public UUID insert(MKWajibPd mkWajibPd);
    public void update(MKWajibPd mkWajibPd);
    public void delete(MKWajibPd mkWajibPd);
    public long count(String where);
}
