package com.AIS.Modul.MataKuliah.Repository;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.*;

public interface RumpunMKRepository {
 
	public long count(String string);

	public List<RumpunMK> get(String where, String order, int limit, int offset);

	public void update(RumpunMK rumpunMK);

	public UUID insert(RumpunMK rumpunMK);

	public RumpunMK findById(UUID idRumpunMK);
	
	public List<RumpunMK> findAll(); 
}
