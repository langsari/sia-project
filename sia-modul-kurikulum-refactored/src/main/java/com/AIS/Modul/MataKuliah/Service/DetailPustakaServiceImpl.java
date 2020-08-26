package com.AIS.Modul.MataKuliah.Service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.AIS.Modul.MataKuliah.Repository.DetailPustakaRepository;
import com.sia.modul.domain.DetailPustaka;
import com.sia.modul.domain.PemetaanSilabus;

@Service
public class DetailPustakaServiceImpl implements DetailPustakaService {

	@Autowired
	private DetailPustakaRepository detailPustakaRepo;
	
	@Override
	public String save(DetailPustaka dp) {
		// TODO Auto-generated method stub
		if(dp.getIdDetailPustaka() != null)
		{
			//update
			detailPustakaRepo.update(dp);
			return dp.getIdDetailPustaka().toString();
		}
		else
		{
			//insert
			return detailPustakaRepo.insert(dp).toString();
		}
	}

	@Override
	public String delete(UUID idDetailPustaka) {
		// TODO Auto-generated method stub
		DetailPustaka dp = detailPustakaRepo.findById(idDetailPustaka);
		if(dp==null) return null;
		else{
			dp.setStatusPustaka(true);
			detailPustakaRepo.update(dp);
			return "Ok";
		}
	}

	@Override
	public List<DetailPustaka> findBySilabus(UUID idSilabus) {
		// TODO Auto-generated method stub
		return detailPustakaRepo.findBySilabus(idSilabus);
	}

}
