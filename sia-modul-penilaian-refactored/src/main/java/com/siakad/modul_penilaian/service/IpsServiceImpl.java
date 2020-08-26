package com.siakad.modul_penilaian.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sia.modul.domain.Ips;
import com.siakad.modul_penilaian.repository.IpsRepository;

@Service
public class IpsServiceImpl implements IpsService {
	@Autowired
	private IpsRepository repositoryIps;

	@Override
	public void masukkanIps(Ips ips) {
		// TODO Auto-generated method stub
		if(repositoryIps.find(ips.getPd().getIdPd(), ips.getTglSmt().getIdTglSmt()) == null) {
			repositoryIps.insert(ips);
		}
		else {
			Ips existingIps = repositoryIps.find(ips.getPd().getIdPd(), ips.getTglSmt().getIdTglSmt());
			ips.setIdIps(existingIps.getIdIps());
			repositoryIps.update(ips);
		}
	}

	@Override
	public List<Ips> ambilSemuaIps() {
		// TODO Auto-generated method stub
		return repositoryIps.getAll();
	}

	@Override
	public List<Ips> ambilBerdasarkanPd(UUID idPd) {
		// TODO Auto-generated method stub
		return repositoryIps.getByPd(idPd);
	}

}
