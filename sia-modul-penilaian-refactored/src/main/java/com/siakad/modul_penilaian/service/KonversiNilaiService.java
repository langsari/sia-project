package com.siakad.modul_penilaian.service;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.KonversiNilai;

public interface KonversiNilaiService {
	public List<KonversiNilai> ambilSemuaKonversiNilai();
	public UUID tambahKonversiNilai(KonversiNilai konversi);
	public void hapusKonversiNilai(UUID idKonversi);
	public void simpanKonversiNilai(KonversiNilai[] listKonversi);
	public KonversiNilai ambilBerdasarkanBatas(double batas);
}
