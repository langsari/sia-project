package com.siakad.modul_penilaian.repository;

import java.util.List;
import java.util.UUID;

import com.sia.main.domain.Peran;

public interface PeranRepository {
	public List<Peran> get(String where, String order, int limit, int offset);
	public Peran getById(UUID idPeran);
	public UUID insert(Peran peran);
	public void update(Peran peran);
	public void delete(Peran peran);
	public long count(String where);
}
