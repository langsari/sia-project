package com.AIS.Modul.MataKuliah.Service;

import java.util.List;
import java.util.UUID;
 
import com.sia.modul.domain.DetailSilabus;

public interface DetailSilabusService {

	public String save(DetailSilabus detailSilabus);

	public DetailSilabus findById(UUID idDetailSilabus);

	public List<DetailSilabus> findBySilabus(UUID idSilabus);

	public String delete(UUID idDetailSilabus);

	public List<DetailSilabus> findAll();

	public List<DetailSilabus> findByMK(UUID idMK);

}
