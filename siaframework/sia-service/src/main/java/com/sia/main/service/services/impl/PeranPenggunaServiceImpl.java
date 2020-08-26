package com.sia.main.service.services.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.sia.main.data.dao.PeranPenggunaDAO;
import com.sia.main.domain.PeranPengguna;
import com.sia.main.service.services.PeranPenggunaService;

@Service
public class PeranPenggunaServiceImpl implements PeranPenggunaService {
	
	private PeranPenggunaDAO peranPenggunaDAO;
	
	public PeranPenggunaDAO getPeranPenggunaDAO() {
		return peranPenggunaDAO;
	}

	public void setPeranPenggunaDAO(PeranPenggunaDAO peranPenggunaDAO) {
		this.peranPenggunaDAO = peranPenggunaDAO;
	}

	@Override
	public PeranPengguna insertInto(PeranPengguna peranPengguna) {
		String query = "where peran.idPeran = '" + peranPengguna.getPeran().getIdPeran() + "' and pengguna.idPengguna = '" + peranPengguna.getPengguna().getIdPengguna() + "' and satMan.idSatMan = '" + peranPengguna.getSatMan().getIdSatMan() + "'";
		List<PeranPengguna> result = this.peranPenggunaDAO.getByParam(query);
		if(result != null && result.size() > 0) {
			return null;	
		}
		this.peranPenggunaDAO.insert(peranPengguna);
		return peranPengguna;
	}

	@Override
	public PeranPengguna update(PeranPengguna peranPengguna) {
		String query = "where peran.idPeran = '" + peranPengguna.getPeran().getIdPeran() + "' and pengguna.idPengguna = '" + peranPengguna.getPengguna().getIdPengguna() + "' and satMan.idSatMan = '" + peranPengguna.getSatMan().getIdSatMan() + "' and idPeranPengguna != '" + peranPengguna.getIdPeranPengguna() + "'";
		List<PeranPengguna> result = this.peranPenggunaDAO.getByParam(query);
		if(result != null && result.size() > 0) {
			return null;	
		}
		this.peranPenggunaDAO.update(peranPengguna);
		return peranPengguna;
	}

	@Override
	public PeranPengguna delete(PeranPengguna peranPengguna) {
		this.peranPenggunaDAO.delete(peranPengguna);
		return peranPengguna;
	}

	@Override
	public List<PeranPengguna> getAll() {
		return this.peranPenggunaDAO.getAll();
	}

	@Override
	public PeranPengguna getById(UUID idPeranPengguna) {
		return this.peranPenggunaDAO.getById(idPeranPengguna);
	}

	@Override
	public List<PeranPengguna> getByParam(String queryParam) {
		return this.peranPenggunaDAO.getByParam(queryParam);
	}

}
