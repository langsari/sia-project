package com.sia.main.service.services;

import java.util.List;
import java.util.UUID;

import com.sia.main.domain.Pengguna;

public interface PenggunaService {

	public Pengguna insertInto(Pengguna pengguna);
	
	public Pengguna update(Pengguna pengguna);
	
	public Pengguna delete(Pengguna pengguna);
	
	public Pengguna getById(UUID idPengguna);
	
	public List<Pengguna> getAll();
	
	public List<Pengguna> getByParam(String queryParam);
	
	public Pengguna getByUsername(String username);
	
}
