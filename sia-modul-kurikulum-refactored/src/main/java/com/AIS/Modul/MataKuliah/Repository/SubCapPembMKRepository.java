package com.AIS.Modul.MataKuliah.Repository;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.CapPemb;
import com.sia.modul.domain.SubCapPembMK;

public interface SubCapPembMKRepository {

	public List<SubCapPembMK> get(String where, String order, int limit, int offset);

	public void update(SubCapPembMK subCapPembMK);

	public UUID insert(SubCapPembMK subCapPembMK);

	public CapPemb findByCapPembMK(UUID idCapPembMK);

	public long count(String string);

	public List<SubCapPembMK> findCapPemb(String idCapPembMK);

	public void delete(UUID idSubCapPembMK);

	public SubCapPembMK findById(UUID idSubCapPembMK);

 
	public List<SubCapPembMK> findByCapPembMKList(UUID idCapPembMK);

	public List<CapPemb> findByMK(UUID idMK);
}
