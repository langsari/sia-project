package com.siakad.modul_penilaian.service;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.Ipk;

public interface IpkService {
	public List<Ipk> ambilSemuaIpk();
	public void masukkanIpk(Ipk ipk);
	public Ipk ambilIpkBerdasarkanPd(UUID idPd);
}
