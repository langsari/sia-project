package com.AIS.Modul.MataKuliah.Repository;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.*;

public interface SatManRepository {
	public List<SatMan> findAll();
    public SatMan findById(UUID idSatMan);
    public void addSatMan(SatMan satMan);
    public void editSatMan(SatMan satMan, UUID idSatMan); 
}
