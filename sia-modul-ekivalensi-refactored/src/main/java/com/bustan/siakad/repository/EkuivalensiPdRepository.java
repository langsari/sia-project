package com.bustan.siakad.repository;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.EkuivalensiPd;

public interface EkuivalensiPdRepository {
	public List<EkuivalensiPd> get(String where, String order, int limit, int offset);
    public EkuivalensiPd getById(UUID idEkuivalensiPd);
    public UUID insert(EkuivalensiPd ekuivalensiPd);
    public void update(EkuivalensiPd ekuivalensiPd);
    public void delete(EkuivalensiPd ekuivalensiPd);
    public long count(String where);
}
