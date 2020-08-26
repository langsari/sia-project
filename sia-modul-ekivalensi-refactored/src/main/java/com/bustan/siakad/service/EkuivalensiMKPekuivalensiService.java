package com.bustan.siakad.service;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.EkuivalensiMKPekuivalensi;
import com.sia.modul.domain.MK;
import com.sia.modul.domain.MKLuar;

public interface EkuivalensiMKPekuivalensiService {
	public List<EkuivalensiMKPekuivalensi> get();
	public List<EkuivalensiMKPekuivalensi> get(String where);
	public List<EkuivalensiMKPekuivalensi> get(String where, String order);
	public List<EkuivalensiMKPekuivalensi> get(String where, String order, int limit, int offset);
	public List<MK> getMKDistinct(UUID idRelasiMKPekuivalensi);
	public List<MKLuar> getMKLuarDistinct(UUID idRelasiMKPekuivalensi);
	public EkuivalensiMKPekuivalensi getById(UUID idEkuivalensiMKPekuivalensi);
    public String save(EkuivalensiMKPekuivalensi ekuivalensiMKPekuivalensi);
    public void delete(EkuivalensiMKPekuivalensi ekuivalensiMKPekuivalensi);
}
