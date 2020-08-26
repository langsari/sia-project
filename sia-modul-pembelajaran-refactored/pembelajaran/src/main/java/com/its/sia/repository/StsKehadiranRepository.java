package com.its.sia.repository;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.StsKehadiran;

public interface StsKehadiranRepository {
	public List<StsKehadiran> get(String where, String order, int limit, int offset);
    public StsKehadiran getById(UUID idStsKehadiran);
    public UUID insert(StsKehadiran stsKehadiran);
    public void update(StsKehadiran stsKehadiran);
    public void delete(UUID idStsKehadiran);
    public long count(String where);
}
