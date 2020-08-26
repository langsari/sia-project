package com.bustan.siakad.service;

import java.util.List;
import java.util.UUID;

import com.bustan.siakad.service.Datatable;
import com.sia.modul.domain.HasilEkuivalensiPd;

public interface HasilEkuivalensiPdService {
	public List<HasilEkuivalensiPd> get();
	public List<HasilEkuivalensiPd> get(String where);
	public List<HasilEkuivalensiPd> get(String where, String order);
	public List<HasilEkuivalensiPd> get(String where, String order, int limit, int offset);
	public HasilEkuivalensiPd getById(UUID idHasilEkuivalensiPd);
    public String save(HasilEkuivalensiPd hasilEkuivalensiPd);
    public String delete(UUID idHasilEkuivalensiPd);
}
