package com.siakad.modul_penilaian.service;

import java.util.List;
import java.util.UUID;

import com.sia.main.domain.MenuPeran;

public interface MenuPeranService {
	public List<MenuPeran> get();
	public List<MenuPeran> get(String where);
	public List<MenuPeran> get(String where, String order);
	public List<MenuPeran> get(String where, String order, int limit, int offset);
	public MenuPeran getById(UUID idMenuPeran);
	public String save(MenuPeran menuPeran);
	public String delete(UUID idMenuPeran);
}
