package com.AIS.Modul.MataKuliah.Repository;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.CapPemb;
import com.sia.modul.domain.CapPembMK;

public interface CapPembMKRepository {

	public long count(String where);

	public List<CapPembMK> get(String where, String order, int limit, int offset); 

	public CapPembMK findById(UUID idCapPembMK);

	public UUID insert(CapPembMK capPembMK);

	public void update(CapPembMK capPembMK);

	public List<CapPemb> findParent(CapPembMK capPembMK);

	public List<CapPembMK> findAll();

	public List<CapPembMK> findByMK(UUID idMK);
}
