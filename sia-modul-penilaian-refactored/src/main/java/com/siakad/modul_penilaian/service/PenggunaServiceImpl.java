package com.siakad.modul_penilaian.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sia.main.domain.Pengguna;
import com.siakad.modul_penilaian.repository.PenggunaRepository;

@Service
public class PenggunaServiceImpl implements PenggunaService {

	@Autowired
	private PenggunaRepository penggunaRepository;

	@Override
	public List<Pengguna> get() {
		return get("");
	}

	@Override
	public List<Pengguna> get(String where) {
		return get(where,"");
	}

	@Override
	public List<Pengguna> get(String where, String order) {
		return get(where,order,-1,-1);
	}

	@Override
	public List<Pengguna> get(String where, String order, int limit, int offset) {
		return penggunaRepository.get(where, order, limit, offset);
	}

	@Override
	public Pengguna getById(UUID idAnggotaRombel) {
		return penggunaRepository.getById(idAnggotaRombel);
	}

	@Override
	public String save(Pengguna pengguna) {
		String where = "";
		if(pengguna.getIdPengguna() != null)
		{
			//update
			penggunaRepository.update(pengguna);
			return pengguna.getIdPengguna().toString();
		}
		else
		{
			//insert
			return penggunaRepository.insert(pengguna).toString();
		}
	}

	@Override
	public String delete(UUID idPengguna) {
		Pengguna pengguna = penggunaRepository.getById(idPengguna);
		if(pengguna==null) return null;
		else{
			penggunaRepository.delete(pengguna);
			return "Ok";
		}
	}

}
