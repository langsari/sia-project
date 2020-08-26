package com.AIS.Modul.MataKuliah.Service;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.EkuivalensiMK;

public interface EkuivalensiMKService {

	Datatable getdatatable(String sEcho, int iDisplayLength, int iDisplayStart,
			int iSortCol_0, String sSortDir_0, String sSearch, String filter);

	public List<EkuivalensiMK> get();

	public List<EkuivalensiMK> get(String where);

	public List<EkuivalensiMK> get(String where, String order);

	public List<EkuivalensiMK> get(String where, String order, int limit, int offset);

	public String save(EkuivalensiMK ekuivalensiMK);

	public EkuivalensiMK findById(UUID idEkuivalensiMK);

	public String delete(UUID idEkuivalensiMK);

	public List<EkuivalensiMK> findAll();

}
