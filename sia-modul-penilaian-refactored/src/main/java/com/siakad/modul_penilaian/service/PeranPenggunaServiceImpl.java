package com.siakad.modul_penilaian.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sia.main.domain.PeranPengguna;
import com.siakad.modul_penilaian.repository.PeranPenggunaRepository;

@Service
public class PeranPenggunaServiceImpl implements PeranPenggunaService {

	@Autowired
	private PeranPenggunaRepository peranPenggunaRepository;
	
	@Override
	public List<PeranPengguna> get() {
		return get("");
	}

	@Override
	public List<PeranPengguna> get(String where) {
		return get(where,"");
	}

	@Override
	public List<PeranPengguna> get(String where, String order) {
		return get(where,order,-1,-1);
	}

	@Override
	public List<PeranPengguna> get(String where, String order, int limit, int offset) {
		return peranPenggunaRepository.get(where, order, limit, offset);
	}

	@Override
	public PeranPengguna getById(UUID idPeranPengguna) {
		return peranPenggunaRepository.getById(idPeranPengguna);
	}
	
	@Override
	public PeranPengguna getByPenggunaPeran(UUID idPengguna, String namaPeran) {
		return peranPenggunaRepository.getByPenggunaPeran(idPengguna,namaPeran);
	}

	@Override
	public String save(PeranPengguna peranPengguna) {
		String where = "";
		if(peranPengguna.getIdPeranPengguna() != null)
		{
			//update
			peranPenggunaRepository.update(peranPengguna);
			return peranPengguna.getIdPeranPengguna().toString();
		}
		else
		{
			//insert
			return peranPenggunaRepository.insert(peranPengguna).toString();
		}
	}

	@Override
	public String delete(UUID idPeranPengguna) {
		PeranPengguna peranPengguna = peranPenggunaRepository.getById(idPeranPengguna);
		if(peranPengguna==null) return null;
		else{
			peranPenggunaRepository.delete(peranPengguna);
			return "Ok";
		}
	}

}
