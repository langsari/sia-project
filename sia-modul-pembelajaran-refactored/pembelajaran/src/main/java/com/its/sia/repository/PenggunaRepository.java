package com.its.sia.repository;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.Pengguna;

public interface PenggunaRepository {
	public List<Pengguna> get(String where, String order, int limit, int offset);
    public Pengguna getById(UUID idPengguna);
    public UUID insert(Pengguna pengguna);
    public void update(Pengguna pengguna);
    public void delete(Pengguna pengguna);
    public long count(String where);
}
