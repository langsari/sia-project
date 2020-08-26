package com.its.sia.repository;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.AnggotaRombel;

public interface AnggotaRombelRepository {
	public List<AnggotaRombel> get(String where, String order, int limit, int offset);
    public AnggotaRombel getById(UUID idAnggotaRombel);
    public UUID insert(AnggotaRombel anggotaRombel);
    public void update(AnggotaRombel anggotaRombel);
    public void delete(AnggotaRombel anggotaRombel);
    public long count(String where);
}
