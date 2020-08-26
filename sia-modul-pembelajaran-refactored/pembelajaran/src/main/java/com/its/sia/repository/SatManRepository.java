package com.its.sia.repository;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.Ptk;
import com.sia.modul.domain.SatMan;

public interface SatManRepository {
	public List<SatMan> get(String where, String order, int limit, int offset);
    public SatMan getById(UUID idSatMan);
    public UUID insert(SatMan satMan);
    public void update(SatMan satMan);
    public void delete(UUID idSatMan);
    public long count(String where);
    public List<SatMan> getChildOf(UUID idSatMan);
    public List<SatMan> getSatManPtk(UUID idSatMan);
    public List<SatMan> getSatManInMK(UUID idMK);
    public boolean addChild(SatMan satMan,SatMan parent);
}
