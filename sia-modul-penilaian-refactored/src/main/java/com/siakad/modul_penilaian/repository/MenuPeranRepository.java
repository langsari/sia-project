package com.siakad.modul_penilaian.repository;

import java.util.List;
import java.util.UUID;

import com.sia.main.domain.MenuPeran;

public interface MenuPeranRepository {
	public List<MenuPeran> get(String where, String order, int limit, int offset);
	public MenuPeran getById(UUID idMenuPeran);
	public UUID insert(MenuPeran menuPeran);
	public void update(MenuPeran menuPeran);
	public void delete(MenuPeran menuPeran);
	public long count(String where);
}
