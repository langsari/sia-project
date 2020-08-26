package com.bustan.siakad.service;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.EkuivalensiMKAlihjenjang;
import com.sia.modul.domain.MK;
import com.sia.modul.domain.MKAlihjenjang;

public interface EkuivalensiMKAlihjenjangService {
	public List<EkuivalensiMKAlihjenjang> get();
	public List<EkuivalensiMKAlihjenjang> get(String where);
	public List<EkuivalensiMKAlihjenjang> get(String where, String order);
	public List<EkuivalensiMKAlihjenjang> get(String where, String order, int limit, int offset);
	public List<MK> getMKDistinct(UUID idRelasiMKAlihjenjang);
	public List<MKAlihjenjang> getMKAlihjenjangDistinct(UUID idRelasiMKAlihjenjang);
	public EkuivalensiMKAlihjenjang getById(UUID idEkuivalensiMKAlihjenjang);
    public String save(EkuivalensiMKAlihjenjang ekuivalensiMKAlihjenjang);
    public String delete(UUID idEkuivalensiMKAlihjenjang);
}
