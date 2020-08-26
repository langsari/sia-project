package com.bustan.siakad.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bustan.siakad.repository.HasilEkuivalensiPdRepository;
import com.sia.modul.domain.HasilEkuivalensiPd;

@Service
public class HasilEkuivalensiPdServiceImpl implements HasilEkuivalensiPdService{

	@Autowired
	HasilEkuivalensiPdRepository hasilEkuivalensiPdRepository;
	
	@Override
	public List<HasilEkuivalensiPd> get() {
		return get("");
	}

	@Override
	public List<HasilEkuivalensiPd> get(String where) {
		return get(where,"");
	}

	@Override
	public List<HasilEkuivalensiPd> get(String where, String order) {
		return get(where,order,-1,-1);
	}

	@Override
	public List<HasilEkuivalensiPd> get(String where, String order, int limit,
			int offset) {
		return hasilEkuivalensiPdRepository.get(where,order,limit,offset);
	}

	@Override
	public HasilEkuivalensiPd getById(UUID idMKAlihjenjang) {
		return hasilEkuivalensiPdRepository.getById(idMKAlihjenjang);
	}

	@Override
	public String save(HasilEkuivalensiPd hasilEkuivalensiPd) {
		if(hasilEkuivalensiPd.getIdHasilEkuivalensiPd() != null)
		{
			//update
			hasilEkuivalensiPdRepository.update(hasilEkuivalensiPd);
			return hasilEkuivalensiPd.getIdHasilEkuivalensiPd().toString();
		}
		else
		{
			//insert
			return hasilEkuivalensiPdRepository.insert(hasilEkuivalensiPd).toString();
		}
	}

	@Override
	public String delete(UUID idHasilEkuivalensiPd) {
		HasilEkuivalensiPd hasilEkuivalensiPd = hasilEkuivalensiPdRepository.getById(idHasilEkuivalensiPd);
		if(hasilEkuivalensiPd==null) return null;
		else{
			hasilEkuivalensiPdRepository.delete(hasilEkuivalensiPd);
			return "Ok";
		}
	}
}
