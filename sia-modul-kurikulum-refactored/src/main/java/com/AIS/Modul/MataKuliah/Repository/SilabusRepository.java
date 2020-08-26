package com.AIS.Modul.MataKuliah.Repository;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.Silabus;

public interface SilabusRepository {

	public void update(Silabus silabus);

	public UUID insert(Silabus silabus);

	public Silabus findByMK(UUID idMK);

	public Silabus findById(UUID idSilabus);

	public List<Silabus> findAll();

}
