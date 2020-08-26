package com.siakad.modul_penilaian.service;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.PendidikPengajar;

public interface PendidikPengajarService {
	public PendidikPengajar ambilKetuaPemb(UUID idPemb);
	public void masukkanNilaiIpd(UUID idPemb, double nilai);
	public List<PendidikPengajar> ambilBerdasarkanPemb(UUID idPemb);
}
