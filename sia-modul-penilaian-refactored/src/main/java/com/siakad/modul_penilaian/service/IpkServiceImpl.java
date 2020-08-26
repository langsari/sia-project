package com.siakad.modul_penilaian.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sia.modul.domain.Ipk;
import com.siakad.modul_penilaian.repository.IpkRepository;

@Service
public class IpkServiceImpl implements IpkService {
	@Autowired
	private IpkRepository repositoryIpk;
	
	@Override
	public List<Ipk> ambilSemuaIpk() {
		// TODO Auto-generated method stub
		return repositoryIpk.getAll();
	}

	@Override
	public void masukkanIpk(Ipk ipk) {
		// TODO Auto-generated method stub
		if(repositoryIpk.find(ipk.getPd().getIdPd()) == null) {
			repositoryIpk.insert(ipk);
		}
		else {
			Ipk existingIpk = repositoryIpk.find(ipk.getPd().getIdPd());
			ipk.setIdIpk(existingIpk.getIdIpk());
			repositoryIpk.update(ipk);
		}
	}

	@Override
	public Ipk ambilIpkBerdasarkanPd(UUID idPd) {
		// TODO Auto-generated method stub
		return repositoryIpk.find(idPd);
	}

}
