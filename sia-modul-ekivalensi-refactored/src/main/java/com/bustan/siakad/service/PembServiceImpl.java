package com.bustan.siakad.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sia.modul.domain.Pemb;
import com.bustan.siakad.repository.PembRepository;

@Service
public class PembServiceImpl implements PembService {
	@Autowired
	private PembRepository pembRepository;
	
	@Override
	public List<Pemb> get() {
		return get("");
	}

	@Override
	public List<Pemb> get(String where) {
		return get(where,"");
	}

	@Override
	public List<Pemb> get(String where, String order) {
		return get(where,order,-1,-1);
	}

	@Override
	public List<Pemb> get(String where, String order, int limit, int offset) {
		return pembRepository.get(where, order, limit, offset);
	}

	@Override
	public Pemb getById(UUID idPemb) {
		return pembRepository.getById(idPemb);
	}
}
