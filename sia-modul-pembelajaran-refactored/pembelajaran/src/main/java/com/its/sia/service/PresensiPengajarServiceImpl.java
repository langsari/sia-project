package com.its.sia.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sia.modul.domain.AnggotaRombel;
import com.sia.modul.domain.PertemuanPembelajaran;
import com.sia.modul.domain.PresensiPd;
import com.sia.modul.domain.PresensiPengajar;
import com.sia.modul.domain.Rombel;
import com.its.sia.repository.AnggotaRombelRepository;
import com.its.sia.repository.PresensiPdRepository;
import com.its.sia.repository.PresensiPengajarRepository;
import com.its.sia.repository.RombelRepository;

@Service
public class PresensiPengajarServiceImpl implements PresensiPegajarService {

	@Autowired
	private PresensiPengajarRepository presensiPengajarRepository;
	
	@Override
	public List<PresensiPengajar> get() {
		return get("");
	}

	@Override
	public List<PresensiPengajar> get(String where) {
		return get(where,"");
	}

	@Override
	public List<PresensiPengajar> get(String where, String order) {
		return get(where,order,-1,-1);
	}

	@Override
	public List<PresensiPengajar> get(String where, String order, int limit, int offset) {
		return presensiPengajarRepository.get(where, order, limit, offset);
	}

	@Override
	public PresensiPengajar getById(UUID idPresensiPengajar) {
		return presensiPengajarRepository.getById(idPresensiPengajar);
	}

	@Override
	public String save(PresensiPengajar presensiPengajar) {
		String where ="";
		if(presensiPengajar.getIdPresensiPengajar()!=null) where+="presensiPengajar.idPresensiPengajar !='"+presensiPengajar.getIdPresensiPengajar()+"' and ";
		List<PresensiPengajar> listPresensiPengajar = get(where+"pertemuanPembelajaran.pertemuan = "+presensiPengajar.getPertemuanPembelajaran().getPertemuan()+" and pendidikPengajar.idPendidikPengajar='"+presensiPengajar.getPendidikPengajar().getIdPendidikPengajar()+"' and pemb.idPemb ='"+presensiPengajar.getPertemuanPembelajaran().getPemb().getIdPemb()+"' ");
		if(listPresensiPengajar.size()>0)
		{
			return null;
		}
		else if(presensiPengajar.getIdPresensiPengajar() != null)
		{
			//update
			presensiPengajarRepository.update(presensiPengajar);
			return presensiPengajar.getIdPresensiPengajar().toString();
		}
		else
		{
			//insert
			return presensiPengajarRepository.insert(presensiPengajar).toString();
		}
	}

	@Override
	public String delete(UUID idPresensiPengajar) {
		PresensiPengajar presensiPengajar = presensiPengajarRepository.getById(idPresensiPengajar);
		if(presensiPengajar==null) return null;
		else{
			presensiPengajarRepository.delete(presensiPengajar);
			return "Ok";
		}
	}

}
