package com.AIS.Modul.MataKuliah.Service;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.Silabus;

public interface SilabusService {
	
	public String save(Silabus silabus);

	public Silabus findByMK(UUID idMK);

	public Silabus findById(UUID idSilabus);

	public List<Silabus> findAll();
}
