package com.bustan.siakad.repository;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.EkuivalensiMKPekuivalensi;
import com.sia.modul.domain.MK;
import com.sia.modul.domain.MKLuar;

public interface EkuivalensiMKPekuivalensiRepository {
	public List<EkuivalensiMKPekuivalensi> get(String where, String order, int limit, int offset);
	public List<MK> getMKDistinct(UUID idRelasiMKPekuivalensi);
	public List<MKLuar> getMKLuarDistinct(UUID idRelasiMKPekuivalensi);
    public EkuivalensiMKPekuivalensi getById(UUID ekuivalensiMKPekuivalensi);
    public UUID insert(EkuivalensiMKPekuivalensi ekuivalensiMKPekuivalensi);
    public void update(EkuivalensiMKPekuivalensi ekuivalensiMKPekuivalensi);
    public void delete(EkuivalensiMKPekuivalensi ekuivalensiMKPekuivalensi);
//    public long count(String where);
}
