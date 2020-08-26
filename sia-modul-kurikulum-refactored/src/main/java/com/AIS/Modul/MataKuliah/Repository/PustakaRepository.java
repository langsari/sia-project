package com.AIS.Modul.MataKuliah.Repository;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.Pustaka;

public interface PustakaRepository {

	public long count(String string);

	public List<Pustaka> get(String where, String order, int limit, int offset);

	public void update(Pustaka pustaka);

	public UUID insert(Pustaka pustaka);

	public Pustaka findById(UUID idPustaka);

	public List<Pustaka> findAll();

}
