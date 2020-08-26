package com.siakad.modul_penilaian.repository;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.Ipk;

public interface IpkRepository {
	public List<Ipk> getAll();
	public UUID insert(Ipk ipk);
	public void update(Ipk ipk);
	public Ipk find(UUID idPd);
}
