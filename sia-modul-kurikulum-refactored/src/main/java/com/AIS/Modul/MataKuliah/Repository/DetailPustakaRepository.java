package com.AIS.Modul.MataKuliah.Repository;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.DetailPustaka;

public interface DetailPustakaRepository {

	public void update(DetailPustaka dp);

	public UUID insert(DetailPustaka dp);

	public DetailPustaka findById(UUID idDetailPustaka);

	public List<DetailPustaka> findBySilabus(UUID idSilabus);

}
