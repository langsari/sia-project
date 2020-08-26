package com.bustan.siakad.service;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.Pemb;

public interface PembService {
	public List<Pemb> get();
	public List<Pemb> get(String where);
	public List<Pemb> get(String where, String order);
	public List<Pemb> get(String where, String order, int limit, int offset);
    public Pemb getById(UUID idPemb);
}
