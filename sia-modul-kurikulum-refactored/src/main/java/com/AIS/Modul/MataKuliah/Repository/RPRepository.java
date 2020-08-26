package com.AIS.Modul.MataKuliah.Repository;

import java.util.UUID;

import com.sia.modul.domain.RP;

public interface RPRepository {
 
	public RP findRP(String string); 

	public RP findBySilabus(UUID idSilabus);

	public UUID insert(RP rp);

	public void update(RP rpNew);

	public RP findById(UUID idRP);

}
