package com.bustan.siakad.repository;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.KatalogAlihjenjang;

public interface KatalogAlihjenjangRepository {
	public List<KatalogAlihjenjang> get(String where, String order, int limit, int offset);
    public KatalogAlihjenjang getById(UUID idKatalogAlihjenjang);
    public UUID insert(KatalogAlihjenjang katalogAlihjenjang);
    public void update(KatalogAlihjenjang katalogAlihjenjang);
    public void delete(KatalogAlihjenjang katalogAlihjenjang);
    public long count(String where);

}
