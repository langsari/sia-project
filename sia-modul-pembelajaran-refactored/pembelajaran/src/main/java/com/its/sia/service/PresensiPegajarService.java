package com.its.sia.service;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.AnggotaRombel;
import com.sia.modul.domain.Pd;
import com.sia.modul.domain.PresensiPd;
import com.sia.modul.domain.PresensiPengajar;
import com.sia.modul.domain.Ptk;;

public interface PresensiPegajarService {
	public List<PresensiPengajar> get();
	public List<PresensiPengajar> get(String where);
	public List<PresensiPengajar> get(String where, String order);
	public List<PresensiPengajar> get(String where, String order, int limit, int offset);
    public PresensiPengajar getById(UUID idPresensiPd);
    public String save(PresensiPengajar presensiPengajar);
    public String delete(UUID idPresensiPengajar);
 }
