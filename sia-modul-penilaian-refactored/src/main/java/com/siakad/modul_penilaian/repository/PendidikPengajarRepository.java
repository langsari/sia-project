package com.siakad.modul_penilaian.repository;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.PendidikPengajar;

public interface PendidikPengajarRepository {
	public void updateNilaiIpd(UUID idPemb, double nilai);
	public PendidikPengajar getKetuaByPemb(UUID idPemb);
	public List<PendidikPengajar> getByPemb(UUID idPemb);
}
