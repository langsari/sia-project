package com.sia.main.service.services.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.sia.main.data.dao.SatuanManajemenDAO;
import com.sia.main.domain.SatMan;
import com.sia.main.service.services.SatuanManajemenService;

@Service
public class SatuanManajemenServiceImpl implements SatuanManajemenService {

	private SatuanManajemenDAO satuanManajemenDAO;
	
	public SatuanManajemenDAO getSatuanManajemenDAO() {
		return satuanManajemenDAO;
	}

	public void setSatuanManajemenDAO(
			SatuanManajemenDAO satuanManajemenDAO) {
		this.satuanManajemenDAO = satuanManajemenDAO;
	}

	@Override
	public SatMan insertInto(SatMan satuanManajemen) {
		this.satuanManajemenDAO.insert(satuanManajemen);
		return satuanManajemen;
	}

	@Override
	public SatMan update(SatMan satuanManajemen) {
		this.satuanManajemenDAO.update(satuanManajemen);
		return satuanManajemen;
	}

	@Override
	public SatMan delete(SatMan satuanManajemen) {
		this.satuanManajemenDAO.delete(satuanManajemen);
		return satuanManajemen;
	}

	@Override
	public List<SatMan> getAll() {
		return this.satuanManajemenDAO.getAll();
	}

	@Override
	public SatMan getById(UUID idSatuanManajemen) {
		return this.satuanManajemenDAO.getById(idSatuanManajemen);
	}

}
