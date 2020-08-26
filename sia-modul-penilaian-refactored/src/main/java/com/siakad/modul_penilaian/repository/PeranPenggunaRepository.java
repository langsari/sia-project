package com.siakad.modul_penilaian.repository;

import java.util.List;
import java.util.UUID;

import com.sia.main.domain.PeranPengguna;

public interface PeranPenggunaRepository {
	public List<PeranPengguna> get(String where, String order, int limit, int offset);
	public PeranPengguna getById(UUID idPeranPengguna);
	public PeranPengguna getByPenggunaPeran(UUID idPengguna,String namaPeran);
	public UUID insert(PeranPengguna peranPengguna);
	public void update(PeranPengguna peranPengguna);
	public void delete(PeranPengguna peranPengguna);
	public long count(String where);
}
