package com.AIS.Modul.MataKuliah.Repository;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.SubCapPemb;

public interface SubCapPembRepository {

	public long count(String where);

	public List<SubCapPemb> get(String where, String order, int limit, int offset);

	public void update(SubCapPemb subCapPemb);

	public UUID insert(SubCapPemb subCapPemb);

	public SubCapPemb findById(UUID idSubCapPemb);

	public List<SubCapPemb> findAll(); 

	public List<SubCapPemb> findParent(String idCapPemb);

	public void delete(UUID idSubCapPemb);
}
