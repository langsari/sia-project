package com.siakad.modul_penilaian.service;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.KomponenNilai;

public interface KomponenNilaiService {
	public List<KomponenNilai> ambilSemuaKomponen(UUID idPemb);
	public UUID tambahKomponen(KomponenNilai komp);
	public void simpanKomponen(List<KomponenNilai> listKomponen);
	public void hapusKomponen(UUID idKomp);
	public KomponenNilai ambilKomponen(UUID idKomp);
}
