package com.AIS.Modul.MataKuliah.Service;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.KonversiNilai;

public interface KonversiNilaiService {

	public List<KonversiNilai> findAll();

	public KonversiNilai findById(UUID idKonversi);

}
