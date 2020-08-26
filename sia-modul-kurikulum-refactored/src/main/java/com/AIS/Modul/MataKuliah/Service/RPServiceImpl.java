package com.AIS.Modul.MataKuliah.Service;

import java.util.UUID;

import javax.transaction.Transaction;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.AIS.Modul.MataKuliah.Repository.RPRepository;
import com.sia.modul.domain.RP;

@Service
public class RPServiceImpl implements RPService {

	@Autowired
	private RPRepository rpRepo;
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public RP findRP(String idMK) {
		// TODO Auto-generated method stub
		RP rp = rpRepo.findRP(idMK.toString());
		if(rp!=null) return rp;
		return null;
	}

	@Override
	public String save(RP rpNew) {
		// TODO Auto-generated method stub
		if(rpNew.getIdRP() != null)
		{
			//update
			rpRepo.update(rpNew);
			return rpNew.getIdRP().toString();
		}
		else
		{
			//insert 
			return rpRepo.insert(rpNew).toString();
		}
	}

	@Override
	public RP findBySilabus(UUID idSilabus) {
		// TODO Auto-generated method stub
		return rpRepo.findBySilabus(idSilabus);
	}

	@Override
	public RP findById(UUID idRP) {
		// TODO Auto-generated method stub
		return rpRepo.findById(idRP);
	}

}
