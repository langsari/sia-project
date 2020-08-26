package com.bustan.siakad.service;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.Ptk;;

public interface PtkService {
	public List<Ptk> get();
	public List<Ptk> get(String where);
	public List<Ptk> get(String where, String order);
	public List<Ptk> get(String where, String order, int limit, int offset);
    public Ptk getById(UUID idPtk);
}
