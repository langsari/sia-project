package com.its.sia.repository;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.TglSmt;

public interface TglSmtRepository {
	public List<TglSmt> get(String where, String order, int limit, int offset);
    public TglSmt getById(UUID idTglSmt);
    public UUID insert(TglSmt tglSmt);
    public void update(TglSmt tglSmt);
    public void delete(UUID idTglSmt);
    public long count(String where);
}
