package com.its.sia.service;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.AnggotaRombel;
import com.sia.modul.domain.Ips;
import com.sia.modul.domain.Pd;
import com.sia.modul.domain.Pembayaran;
import com.sia.modul.domain.Ptk;;

public interface PembayaranService {
	public List<Pembayaran> get();
	public List<Pembayaran> get(String where);
	public List<Pembayaran> get(String where, String order);
	public List<Pembayaran> get(String where, String order, int limit, int offset);
    public Pembayaran getById(UUID Pembayaran);
    public String save(Pembayaran pembayaran);
    public String delete(UUID Pembayaran);
    public Datatable getdatatable(String sEcho,int iDisplayLength, int iDisplayStart, int iSortCol_0, String sSortDir_0, String sSearch, String filter);
}
