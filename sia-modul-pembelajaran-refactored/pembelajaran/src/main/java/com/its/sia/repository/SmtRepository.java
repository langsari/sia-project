package com.its.sia.repository;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.Smt;

public interface SmtRepository {
	public List<Smt> get(String where, String order, int limit, int offset);
    public Smt getById(UUID idSmt);
    public UUID insert(Smt smt);
    public void update(Smt smt);
    public void delete(UUID idSmt);
    public long count(String where);
}
