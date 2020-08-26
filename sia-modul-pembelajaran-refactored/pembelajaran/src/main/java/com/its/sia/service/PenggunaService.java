package com.its.sia.service;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.Pengguna;

public interface PenggunaService {
	public List<Pengguna> get();
	public List<Pengguna> get(String where);
	public List<Pengguna> get(String where, String order);
	public List<Pengguna> get(String where, String order, int limit, int offset);
    public Pengguna getById(UUID idPengguna);
    public String save(Pengguna pengguna);
    public String delete(UUID idPengguna);
    
}
