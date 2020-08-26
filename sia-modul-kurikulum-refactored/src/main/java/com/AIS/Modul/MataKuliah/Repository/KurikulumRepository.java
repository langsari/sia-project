package com.AIS.Modul.MataKuliah.Repository;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.*;

public interface KurikulumRepository {
	public List<Kurikulum> findAll();
    public Kurikulum findById(UUID idKurikulum);
    public void addKurikulum(Kurikulum kurikulum);
    public void editKurikulum(Kurikulum kurikulum, UUID idKurikulum);
	public long count(String where);
	public List<Kurikulum> get(String where, String order, int limit, int offset);
	public void update(Kurikulum kurikulum);
	public UUID insert(Kurikulum kurikulum); 
}
