package com.bustan.siakad.repository;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.Pemb;

public interface PembRepository {
	public List<Pemb> get();
	public List<Pemb> get(String where);
	public List<Pemb> get(String where, String order);
	public List<Pemb> get(String where, String order, int limit, int offset);
    public Pemb getById(UUID idPemb);
}
