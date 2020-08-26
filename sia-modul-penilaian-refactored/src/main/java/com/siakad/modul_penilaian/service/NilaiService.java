package com.siakad.modul_penilaian.service;

import java.util.List;

import com.sia.modul.domain.Krs;
import com.sia.modul.domain.Nilai;

public interface NilaiService {
	public void masukkanNilai(List<Nilai> listNilai);
	public List<Nilai> ambilNilaiKelas(List<Krs> listKrs);
	public double ambilNilaiAkhir(Krs krs);
}
