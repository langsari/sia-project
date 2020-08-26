package com.sia.main.service.services.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.sia.main.data.dao.PeranDAO;
import com.sia.main.domain.Peran;
import com.sia.main.service.services.PeranService;

@Service
public class PeranServiceImpl implements PeranService {
	
	private PeranDAO peranDAO;
	
	public PeranDAO getPeranDAO() {
		return peranDAO;
	}

	public void setPeranDAO(PeranDAO peranDAO) {
		this.peranDAO = peranDAO;
	}

	@Override
	public Peran insertInto(Peran peran) {
		List<Peran> peranList = this.getPeranWithParam(" where namaPeran = '" + peran.getNamaPeran() + "'");
		Peran result = null;
		if(peranList == null || peranList.size() == 0) {
			this.peranDAO.insert(peran);
			result = peran;
		}
		return result;
	}

	@Override
	public Peran update(Peran peran) {
		List<Peran> peranList = this.getPeranWithParam(" where namaPeran = '" + peran.getNamaPeran() + "' and idPeran != '" + peran.getIdPeran() + "'");
		Peran result = null;
		if(peranList == null || peranList.size() == 0) {
			this.peranDAO.update(peran);
			result = peran;
		}
		return result;
	}
	
	@Override
	public Peran delete(Peran peran) {
		try {
			this.peranDAO.delete(peran);
			return peran;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<Peran> getAll() {
		return this.peranDAO.getAll();
	}

	@Override
	public Peran getById(UUID idPeran) {
		return this.peranDAO.getById(idPeran);
	}

	@Override
	public List<Peran> getPeranWithParam(String param) {
		return this.peranDAO.getByParam(param);
	}
	
}
