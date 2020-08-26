package com.AIS.Modul.MataKuliah.Service;

import com.sia.modul.domain.Kurikulum;
import com.sia.modul.domain.*;

import java.util.List;
import java.util.UUID;

public interface KurikulumService {
	public List<Kurikulum> findAll();
    public Kurikulum findById(UUID idKurikulum);
 
    public UUID convertToUUID(String source);
    public void activateKurikulum(UUID idKurikulum);
    public void deleteKurikulum(UUID idKurikulum);
    public Datatable getdatatable(String sEcho,int iDisplayLength, int iDisplayStart, int iSortCol_0, String sSortDir_0, String sSearch, String filter);
	public List<Kurikulum> get(String where, String order, int limit, int offset);
	public List<Kurikulum> get();
	public List<Kurikulum> get(String where);
	public List<Kurikulum> get(String where, String order);
	public String save(Kurikulum kurikulum);
	public String delete(UUID uuid);

}
