package com.sia.main.service.services.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.sia.main.data.dao.MenuPeranDAO;
import com.sia.main.domain.MenuPeran;
import com.sia.main.plugin.common.Response;
import com.sia.main.service.services.MenuPeranService;

@Service
public class MenuPeranServiceImpl implements MenuPeranService {

	private MenuPeranDAO menuPeranDAO;

	public void setMenuPeranDAO(MenuPeranDAO menuPeranDAO) {
		this.menuPeranDAO = menuPeranDAO;
	}

	public MenuPeranDAO getMenuPeranDAO() {
		return this.menuPeranDAO;
	}

	@Override
	public Response insertInto(MenuPeran menuPeran) {
		try {
			List<MenuPeran> daftarMenuPeran = this.getByParam("where peran.idPeran = '" + menuPeran.getPeran().getIdPeran() + "' and menu.idMenu = '" + menuPeran.getMenu().getIdMenu() + "'");
			if (daftarMenuPeran != null && daftarMenuPeran.size() > 0) {
				return new Response(Response.error, "Peran " + menuPeran.getPeran().getNamaPeran() + " sudah hak akses untuk menu " + menuPeran.getMenu().getNamaMenu() + ".", null);
			}
			this.menuPeranDAO.insert(menuPeran);
			return new Response(Response.ok, "Penambahan Hak akses peran " + menuPeran.getPeran().getNamaPeran() + " untuk menu " + menuPeran.getMenu().getNamaMenu() + " berhasil.", menuPeran);
		} catch(Exception e) {
			e.printStackTrace();
			return new Response(Response.error, "Terdapat Java Exception. Pesan Exception: " + e.getMessage() + ".", null);
		}
		
	}

	@Override
	public Response update(MenuPeran menuPeran) {
		try {
			List<MenuPeran> daftarMenuPeran = this.getByParam("where peran.idPeran = '" + menuPeran.getPeran().getIdPeran() + "' and menu.idMenu = '" + menuPeran.getMenu().getIdMenu() + "' and idMenuPeran != '" + menuPeran.getIdMenuPeran() + "'");
			if (daftarMenuPeran != null && daftarMenuPeran.size() > 0) {
				return new Response(Response.error, "Peran " + menuPeran.getPeran().getNamaPeran() + " sudah hak akses untuk menu " + menuPeran.getMenu().getNamaMenu() + ".", null);
			}
			this.menuPeranDAO.update(menuPeran);
			return new Response(Response.ok, "Penambahan Hak akses peran " + menuPeran.getPeran().getNamaPeran() + " untuk menu " + menuPeran.getMenu().getNamaMenu() + " berhasil.", menuPeran);
		} catch(Exception e) {
			e.printStackTrace();
			return new Response(Response.error, "Terdapat Java Exception. Pesan Exception: " + e.getMessage() + ".", null);
		}
	}

	@Override
	public Response delete(MenuPeran menuPeran) {
		try {
			this.menuPeranDAO.delete(menuPeran);
			return new Response(Response.ok, "Hak akses peran " + menuPeran.getPeran().getNamaPeran() + " untuk menu " + menuPeran.getMenu().getNamaMenu() + " berhasil dihapus", menuPeran);
		} catch(Exception e) {
			e.printStackTrace();
			return new Response(Response.error, "Terdapat Java Exception. Pesan Exception: " + e.getMessage() + ".", null);
		}
	}

	@Override
	public List<MenuPeran> getAll() {
		return this.menuPeranDAO.getAll();
	}

	@Override
	public MenuPeran getById(UUID idMenuPeran) {
		return this.menuPeranDAO.getById(idMenuPeran);
	}

	@Override
	public List<MenuPeran> getByParam(String queryParam) {
		return this.menuPeranDAO.getByParam(queryParam);
	}

}
