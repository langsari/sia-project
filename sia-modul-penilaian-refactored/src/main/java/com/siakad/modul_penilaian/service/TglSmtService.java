package com.siakad.modul_penilaian.service;

import java.util.List;

import com.sia.modul.domain.TglSmt;

public interface TglSmtService {
	public TglSmt ambilTglSmtAktif();
	public List<TglSmt> ambilSemuaTglSmt();
}
