package com.AIS.Modul.MataKuliah.Service;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.DetailPemetaan;

public interface DetailPemetaanService {

	public Datatable getdatatable(String sEcho, int iDisplayLength, int iDisplayStart,
			int iSortCol_0, String sSortDir_0, String sSearch, String filter);

	public boolean findRP(UUID idMK);

	public String save(DetailPemetaan detailPemetaanNew);

	public List<DetailPemetaan> findCapPembMK(String idRPPerTemu);

	public void delete(UUID idDetailPemetaan);

}
