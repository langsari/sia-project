package com.its.sia.repository;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.ThnAjaran;;

public interface ThnAjaranRepository {
	public List<ThnAjaran> get(String where, String order, int limit, int offset);
    public ThnAjaran getById(UUID idThnAjaran);
    public UUID insert(ThnAjaran thnAjaran);
    public void update(ThnAjaran thnAjaran);
    public void delete(UUID idThnAjaran);
    public long count(String where);
}
