package com.sia.main.service.services.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.sia.main.data.dao.PenggunaDAO;
import com.sia.main.domain.Pengguna;
import com.sia.main.service.services.PenggunaService;

@Service
public class PenggunaServiceImpl implements PenggunaService {

	private PenggunaDAO penggunaDAO;
	
	public PenggunaDAO getPenggunaDAO() {
		return penggunaDAO;
	}

	public void setPenggunaDAO(PenggunaDAO penggunaDAO) {
		this.penggunaDAO = penggunaDAO;
	}

	@Override
	public Pengguna insertInto(Pengguna pengguna) {
		Pengguna user = this.getByUsername(pengguna.getUsername());
		if(user != null) {
			return null;
		}
		this.penggunaDAO.insert(pengguna);
		return pengguna;
	}

	@Override
	public Pengguna update(Pengguna pengguna) {
		List<Pengguna> penggunaList = this.getByParam("where username = '" + pengguna.getUsername() + "' and idPengguna != '" + pengguna.getIdPengguna() + "'");
		if(penggunaList != null && penggunaList.size() > 0) {
			return null;
		}
		this.penggunaDAO.update(pengguna);
		return pengguna;
	}

	@Override
	public Pengguna delete(Pengguna pengguna) {
		pengguna.setStatusKeaktifan(false);
		return this.update(pengguna);
	}

	@Override
	public List<Pengguna> getAll() {
		return this.penggunaDAO.getAll();
	}

	@Override
	public Pengguna getById(UUID idPengguna) {
		return this.penggunaDAO.getById(idPengguna);
	}

	@Override
	public List<Pengguna> getByParam(String queryParam) {
		return this.penggunaDAO.getByParam(queryParam);
	}
	
	@Override
	public Pengguna getByUsername(String username) {
		List<Pengguna> users = this.penggunaDAO.getByParam("where username = '" + username + "'");
		if(users.size() > 0) {
			return users.get(0);
		} else {
			return null;
		}
	}

}
