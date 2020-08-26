package com.sia.main.service.services;

import java.util.List;
import java.util.UUID;

import com.sia.main.domain.SatMan;

public interface SatuanManajemenService {

	public SatMan insertInto(SatMan satuanManajemen);

	public SatMan update(SatMan satuanManajemen);

	public SatMan delete(SatMan satuanManajemen);

	public List<SatMan> getAll();

	public SatMan getById(UUID idSatuanManajemen);

}
