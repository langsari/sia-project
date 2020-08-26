package com.siakad.modul_penilaian.service;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.Ips;

public interface IpsService {
	public void masukkanIps(Ips ips);
	public List<Ips> ambilSemuaIps();
	public List<Ips> ambilBerdasarkanPd(UUID idPd);
}
