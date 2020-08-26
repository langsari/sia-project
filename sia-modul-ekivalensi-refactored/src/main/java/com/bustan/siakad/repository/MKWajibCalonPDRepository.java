package com.bustan.siakad.repository;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.MKWajibCalonPD;

public interface MKWajibCalonPDRepository {
	public List<MKWajibCalonPD> get(String where, String order, int limit, int offset);
    public MKWajibCalonPD getById(UUID idEkuivalensiCalonPD);
    public UUID insert(MKWajibCalonPD ekuivalensiCalonPD);
    public void update(MKWajibCalonPD ekuivalensiCalonPD);
    public void delete(MKWajibCalonPD ekuivalensiCalonPD);
    public long count(String where);
}
