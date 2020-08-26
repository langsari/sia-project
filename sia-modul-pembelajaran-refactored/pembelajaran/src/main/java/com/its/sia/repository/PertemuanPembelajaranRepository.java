package com.its.sia.repository;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.Krs;
import com.sia.modul.domain.Pd;
import com.sia.modul.domain.PertemuanPembelajaran;

public interface PertemuanPembelajaranRepository {
	public List<PertemuanPembelajaran> get(String where, String order, int limit, int offset);
    public PertemuanPembelajaran getById(UUID idPertemuanPembelajaran);
    public UUID insert(PertemuanPembelajaran pertemuanPembelajaran);
    public void update(PertemuanPembelajaran pertemuanPembelajaran);
    public void delete(PertemuanPembelajaran pertemuanPembelajaran);
    public long count(String where);
}
