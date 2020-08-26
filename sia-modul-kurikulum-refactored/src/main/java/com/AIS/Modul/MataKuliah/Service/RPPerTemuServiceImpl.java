package com.AIS.Modul.MataKuliah.Service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.AIS.Modul.MataKuliah.Repository.RPPerTemuRepository;
import com.sia.modul.domain.RPPerTemu;
import com.sia.modul.domain.RumpunMK;

@Service
public class RPPerTemuServiceImpl implements RPPerTemuService {

	@Autowired
	private RPPerTemuRepository rpPerTemuRepo;
	
	@Override
	public String save(RPPerTemu rpPerTemu) {
		// TODO Auto-generated method stub
		if(rpPerTemu.getIdRPPerTemu() != null)
		{
			//update
			rpPerTemuRepo.update(rpPerTemu);
			return rpPerTemu.getIdRPPerTemu().toString();
		}
		else
		{
			//insert
			return rpPerTemuRepo.insert(rpPerTemu).toString();
		}
	}

	@Override
	public String delete(UUID idRPPerTemu) {
		// TODO Auto-generated method stub
		RPPerTemu rpPerTemu = rpPerTemuRepo.findById(idRPPerTemu);
		if(rpPerTemu==null) return null;
		else{
			rpPerTemu.setStatusRPPerTemu(true);
			rpPerTemuRepo.update(rpPerTemu);
			return "Ok";
		}
	}

	@Override
	public List<RPPerTemu> findByRP(UUID idRP) {
		// TODO Auto-generated method stub
		return rpPerTemuRepo.findByRP(idRP);
	}

	@Override
	public RPPerTemu findById(UUID idRPPerTemu) {
		// TODO Auto-generated method stub
		return rpPerTemuRepo.findById(idRPPerTemu);
	}

}
