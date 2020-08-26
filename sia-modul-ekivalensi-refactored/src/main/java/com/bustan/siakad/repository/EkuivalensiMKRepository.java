package com.bustan.siakad.repository;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.EkuivalensiMK;
import com.sia.modul.domain.MK;
import com.sia.modul.domain.MKLuar;

public interface EkuivalensiMKRepository {
	public List<EkuivalensiMK> get(String where, String order, int limit, int offset);
    public EkuivalensiMK getById(UUID idEkuivalensiMK);
    public List<MK> getMKLamaDistinct(UUID idRelasiEkuivalensi);
    public List<MK> getMKBaruDistinct(UUID idRelasiEkuivalensi);
    public UUID insert(EkuivalensiMK ekuivalensiMK);
    public void update(EkuivalensiMK ekuivalensiMK);
    public void delete(EkuivalensiMK ekuivalensiMK);
    public long count(String where);
    public List<Object> getDistinctRelasiByMKLama(UUID idMKLama);
    public List<Object> getDistinctRelasiByMKBaru(UUID idMKBaru);
}
