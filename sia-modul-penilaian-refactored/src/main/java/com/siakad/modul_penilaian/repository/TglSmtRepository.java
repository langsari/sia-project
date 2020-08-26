package com.siakad.modul_penilaian.repository;

import java.util.List;

import com.sia.modul.domain.TglSmt;

public interface TglSmtRepository {
	public TglSmt getAktif();
	public List<TglSmt> getAll();
}
