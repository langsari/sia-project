package com.AIS.Modul.MataKuliah.Repository;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.PemetaanSilabus;

public interface PemetaanSilabusRepository {

	public void update(PemetaanSilabus ps);

	public UUID insert(PemetaanSilabus ps);

	public List<PemetaanSilabus> findByDetailSilabus(String string);

	public PemetaanSilabus findById(UUID idPemetaanSilabus);

	public List<PemetaanSilabus> findByMateriSilabus(UUID idMateriSilabus);

}
