package com.AIS.Modul.MataKuliah.Repository;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.MateriSilabus;

public interface MateriSilabusRepository {

	public void update(MateriSilabus mp);

	public UUID insert(MateriSilabus mp);

	public MateriSilabus findById(UUID idMateriSilabus);

	public List<MateriSilabus> findByRPPerTemu(UUID idRPPerTemu);

}
