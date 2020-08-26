package com.AIS.Modul.MataKuliah.Service;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.*;

public interface SatManService {

	public List<SatMan> findAll();
    public SatMan findById(UUID idSatMan);
 
    public void addSatMan(SatMan satMan);
    public void editSatMan(SatMan satMan, UUID idSatMan);
    public UUID convertToUUID(String source);
}
