package com.siakad.modul_penilaian.service;

import java.util.List;
import java.util.UUID;

import com.sia.main.domain.PeranPengguna;

public interface PeranPenggunaService {
	public List<PeranPengguna> get();
	public List<PeranPengguna> get(String where);
	public List<PeranPengguna> get(String where, String order);
	public List<PeranPengguna> get(String where, String order, int limit, int offset);
	public PeranPengguna getById(UUID idPeranPengguna);
	public PeranPengguna getByPenggunaPeran(UUID idPengguna, String namaPeran);
	public String save(PeranPengguna peranPengguna);
	public String delete(UUID idPeranPengguna);
}
