package com.its.sia.service;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.AturanPengganti;
import com.sia.modul.domain.SatMan;
import com.sia.modul.domain.TglSmt;

public interface AturanPenggantiService {
	public List<AturanPengganti> get();
	public List<AturanPengganti> get(String where);
	public List<AturanPengganti> get(String where, String order);
	public List<AturanPengganti> get(String where, String order, int limit, int offset);
    public AturanPengganti getById(UUID idAturanPengganti);
    public AturanPengganti getAktif(SatMan satMan);
    public String save(AturanPengganti aturanPengganti);
    public String delete(UUID idAturanPengganti);
    public Datatable getdatatable(String sEcho,int iDisplayLength, int iDisplayStart, int iSortCol_0, String sSortDir_0, String sSearch, String filter);
}
