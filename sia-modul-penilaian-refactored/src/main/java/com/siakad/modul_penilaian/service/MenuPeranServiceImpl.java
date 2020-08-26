package com.siakad.modul_penilaian.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sia.main.domain.MenuPeran;
import com.siakad.modul_penilaian.repository.MenuPeranRepository;

@Service
public class MenuPeranServiceImpl implements MenuPeranService {

	@Autowired
	private MenuPeranRepository menuPeranRepository;
	
	@Override
	public List<MenuPeran> get() {
		return get("");
	}

	@Override
	public List<MenuPeran> get(String where) {
		return get(where,"");
	}

	@Override
	public List<MenuPeran> get(String where, String order) {
		return get(where,order,-1,-1);
	}

	@Override
	public List<MenuPeran> get(String where, String order, int limit, int offset) {
		return menuPeranRepository.get(where, order, limit, offset);
	}

	@Override
	public MenuPeran getById(UUID idAnggotaRombel) {
		return menuPeranRepository.getById(idAnggotaRombel);
	}

	@Override
	public String save(MenuPeran menuPeran) {
		String where = "";
		if(menuPeran.getIdMenuPeran() != null)
		{
			//update
			menuPeranRepository.update(menuPeran);
			return menuPeran.getIdMenuPeran().toString();
		}
		else
		{
			//insert
			return menuPeranRepository.insert(menuPeran).toString();
		}
	}

	@Override
	public String delete(UUID idMenuPeran) {
		MenuPeran menuPeran = menuPeranRepository.getById(idMenuPeran);
		if(menuPeran==null) return null;
		else{
			menuPeranRepository.delete(menuPeran);
			return "Ok";
		}
	}

}
