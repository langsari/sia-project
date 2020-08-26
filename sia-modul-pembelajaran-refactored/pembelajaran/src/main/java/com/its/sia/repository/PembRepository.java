package com.its.sia.repository;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.Pemb;
import com.sia.modul.domain.SatMan;

public interface PembRepository {
	public List<Pemb> get();
	public List<Pemb> get(String where);
	public List<Pemb> get(String where, String order);
	public List<Pemb> get(String where, String order, int limit, int offset);
    public Pemb getById(UUID idPemb);
    public UUID insert(Pemb pemb);
    public void update(Pemb pemb);
    public void delete(Pemb pemb);
    public long count(String where);
	public List<Pemb> getPembInSatMan();
	public List<Pemb> getPembInSatMan(String where);
	public List<Pemb> getPembInSatMan(String where, String order);
	public List<Pemb> getPembInSatMan(String where, String order, int limit, int offset);
	public List<SatMan> getSatManKurikulum(String where, String order, int limit, int offset);
}
