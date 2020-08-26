package com.AIS.Modul.MataKuliah.Service;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.PemetaanSilabus;

public interface PemetaanSilabusService {

	public String save(PemetaanSilabus ps);

	public List<PemetaanSilabus> findByDetailSilabus(UUID idDetailSilabus);

	public String delete(UUID idPemetaanSilabus);

	public List<PemetaanSilabus> findByMateriSilabus(UUID idMateriSilabus);

}
