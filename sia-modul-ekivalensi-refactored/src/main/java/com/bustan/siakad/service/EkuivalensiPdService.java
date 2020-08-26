package com.bustan.siakad.service;

import java.util.List;
import java.util.UUID;

import com.bustan.siakad.service.Datatable;
import com.sia.modul.domain.EkuivalensiPd;

public interface EkuivalensiPdService {
	public List<EkuivalensiPd> get();
	public List<EkuivalensiPd> get(String where);
	public List<EkuivalensiPd> get(String where, String order);
	public List<EkuivalensiPd> get(String where, String order, int limit, int offset);
	public EkuivalensiPd getById(UUID idEkuivalensiPd);
    public String save(EkuivalensiPd ekuivalensiPd);
    public String delete(UUID ekuivalensiPd);
    public Datatable getdatatable(String sEcho,int iDisplayLength, int iDisplayStart, int iSortCol_0, String sSortDir_0, String sSearch, String filter);
}
