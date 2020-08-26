package com.bustan.siakad.repository;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.EkuivalensiMKAlihjenjang;
import com.sia.modul.domain.MK;
import com.sia.modul.domain.MKAlihjenjang;

public interface EkuivalensiMKAlihjenjangRepository {
	public List<EkuivalensiMKAlihjenjang> get(String where, String order, int limit, int offset);
	public List<MK> getMKDistinct(UUID idRelasiMKAlihjenjang);
	public List<MKAlihjenjang> getMKAlihjenjangDistinct(UUID idRelasiMKAlihjenjang);
    public EkuivalensiMKAlihjenjang getById(UUID idEkuivalenMKAlihjenjang);
    public UUID insert(EkuivalensiMKAlihjenjang ekuivalensiMKAlihjenjang);
    public void update(EkuivalensiMKAlihjenjang ekuivalensiMKAlihjenjang);
    public void delete(EkuivalensiMKAlihjenjang ekuivalensiMKAlihjenjang);
}
