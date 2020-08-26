package com.bustan.siakad.service;

import java.util.List;
import java.util.UUID;

import com.bustan.siakad.service.Datatable;
import com.sia.modul.domain.Kurikulum;
import com.sia.modul.domain.MK;
import com.sia.modul.domain.SatMan;
import com.sia.modul.domain.SatManMK;

public interface SatManMKService {
	public List<SatManMK> get();
	public List<SatManMK> get(String where);
	public List<SatManMK> get(String where, String order);
	public List<SatManMK> get(String where, String order, int limit, int offset);
	public SatManMK getById(UUID idSatManMK);
	public List<Kurikulum> getKurikulumDistinct(String where);
	public List<SatMan> getSatManDistinct(String where);
//	public List<MK> getMKDistinct();
//	public List<MK> getMKDistinct(String where);
//	public List<MK> getMKDistinct(String where, String order);
    public List<MK> getMKDistinct(String where, String order, int limit, int offset);
    public Datatable getdatatable(String sEcho,int iDisplayLength, int iDisplayStart, int iSortCol_0, String sSortDir_0, String sSearch, String filter);
}
