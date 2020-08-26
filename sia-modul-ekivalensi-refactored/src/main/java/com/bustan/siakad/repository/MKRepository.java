package com.bustan.siakad.repository;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.MK;

public interface MKRepository {
	public List<MK> get(String where, String order, int limit, int offset);
    public MK getById(UUID idMK);
    public UUID insert(MK mk);
    public void update(MK mk);
    public void delete(MK mk);
    public long count(String where);
}
