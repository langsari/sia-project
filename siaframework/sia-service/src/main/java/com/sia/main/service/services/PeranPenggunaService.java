package com.sia.main.service.services;

import java.util.List;
import java.util.UUID;

import com.sia.main.domain.PeranPengguna;

public interface PeranPenggunaService {

	public PeranPengguna insertInto(PeranPengguna peranPengguna);

	public PeranPengguna update(PeranPengguna peranPengguna);

	public PeranPengguna delete(PeranPengguna peranPengguna);

	public List<PeranPengguna> getAll();

	public PeranPengguna getById(UUID idPeranPengguna);
	
	public List<PeranPengguna> getByParam(String queryParam);
	
}
