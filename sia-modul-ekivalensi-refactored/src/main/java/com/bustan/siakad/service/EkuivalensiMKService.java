package com.bustan.siakad.service;

import java.util.List;
import java.util.UUID;

import com.bustan.siakad.service.Datatable;
import com.sia.modul.domain.EkuivalensiMK;
import com.sia.modul.domain.MK;

public interface EkuivalensiMKService {
	public List<EkuivalensiMK> get();
	public List<EkuivalensiMK> get(String where);
	public List<EkuivalensiMK> get(String where, String order);
	public List<EkuivalensiMK> get(String where, String order, int limit, int offset);
	public List<MK> getMKLamaDistinct(UUID idRelasiEkuivalensi);
	public List<MK> getMKBaruDistinct(UUID idRelasiEkuivalensi);
	public EkuivalensiMK getById(UUID idEkuivalensiMK);
    public String save(EkuivalensiMK ekuivalensiMK);
    public String delete(UUID idEkuivalensiMK);
    public List<Object> getDistinctRelasiByMKLama(UUID idMKLama);
    public List<Object> getDistinctRelasiByMKBaru(UUID idMKBaru);
}
