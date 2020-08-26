package com.AIS.Modul.MataKuliah.Repository;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.EkuivalensiMK;

public interface EkuivalensiMKRepository {

	public long count(String where);

	public List<EkuivalensiMK> get(String where, String order, int limit, int offset);

	public void update(EkuivalensiMK ekuivalensiMK);

	public UUID insert(EkuivalensiMK ekuivalensiMK);

	public EkuivalensiMK findById(UUID idEkuivalensiMK);

	public List<EkuivalensiMK> findAll();

}
