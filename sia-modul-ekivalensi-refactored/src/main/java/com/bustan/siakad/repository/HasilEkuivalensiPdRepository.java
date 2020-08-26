package com.bustan.siakad.repository;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.HasilEkuivalensiPd;

public interface HasilEkuivalensiPdRepository {
	public List<HasilEkuivalensiPd> get(String where, String order, int limit, int offset);
    public HasilEkuivalensiPd getById(UUID idHasilEkuivalensiPd);
    public UUID insert(HasilEkuivalensiPd hasilEkuivalensiPd);
    public void update(HasilEkuivalensiPd hasilEkuivalensiPd);
    public void delete(HasilEkuivalensiPd hasilEkuivalensiPd);
    public long count(String where);
}
