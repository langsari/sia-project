package com.its.sia.repository;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.AturanPengganti;
import com.sia.modul.domain.TglSmt;

public interface AturanPenggantiRepository {
	public List<AturanPengganti> get(String where, String order, int limit, int offset);
    public AturanPengganti getById(UUID idAturanPengganti);
    public UUID insert(AturanPengganti aturanPengganti);
    public void update(AturanPengganti aturanPengganti);
    public void delete(AturanPengganti aturanPengganti);
    public long count(String where);
}
