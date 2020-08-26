package com.siakad.modul_penilaian.service;

import java.util.List;
import java.util.UUID;

import com.sia.main.domain.Peran;

public interface PeranService {
	public List<Peran> get();
	public List<Peran> get(String where);
	public List<Peran> get(String where, String order);
	public List<Peran> get(String where, String order, int limit, int offset);
	public Peran getById(UUID idPeran);
	public String save(Peran peran);
	public String delete(UUID idPeran);
}
