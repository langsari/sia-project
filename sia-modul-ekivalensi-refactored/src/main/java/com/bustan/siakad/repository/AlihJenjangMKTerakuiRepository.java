package com.bustan.siakad.repository;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.AlihJenjangMKTerakui;

public interface AlihJenjangMKTerakuiRepository {
	public List<AlihJenjangMKTerakui> get(String where, String order, int limit, int offset);
    public AlihJenjangMKTerakui getById(UUID idAlihJenjangMKTerakui);
    public UUID insert(AlihJenjangMKTerakui alihJenjangMKTerakui);
    public void update(AlihJenjangMKTerakui alihJenjangMKTerakui);
    public void delete(AlihJenjangMKTerakui alihJenjangMKTerakui);
    public long count(String where);
}
