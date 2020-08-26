package com.siakad.modul_penilaian.service;

import java.util.UUID;

import com.sia.modul.domain.NilaiKuisioner;

public interface NilaiKuisionerService {
	public UUID simpanNilaiKuisioner(NilaiKuisioner nilaiKuisioner);
	public Double ambilBerdasarkanPembPertanyaan(UUID idPemb, UUID idPertanyaan);
}
