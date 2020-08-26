package com.AIS.Modul.MataKuliah.Repository;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.DetailPemetaan;

public interface DetailPemetaanRepository {

	public boolean findRP(UUID idMK);

	public long count(String string);

	public List<DetailPemetaan> get(String where, String order, int limit,
			int offset);

	public List<DetailPemetaan> findCapPembMK(String idRPPerTemu);

	public void update(DetailPemetaan detailPemetaanNew);

	public UUID insert(DetailPemetaan detailPemetaanNew); 

}
