package com.bustan.siakad.service;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.Krs;

public interface KrsService {
	public List<Krs> get();
	public List<Krs> get(String where);
	public List<Krs> get(String where, String order);
	public List<Krs> get(String where, String order, int limit, int offset);
	public Krs getById(UUID idKrs);
	public String update(Krs krs);
}
