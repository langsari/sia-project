package com.its.sia.repository;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.AnggotaRombel;
import com.sia.modul.domain.Ips;
import com.sia.modul.domain.Pembayaran;

public interface PembayaranRepository {
	public List<Pembayaran> get(String where, String order, int limit, int offset);
    public Pembayaran getById(UUID idPembayaran);
    public UUID insert(Pembayaran pembayaran);
    public void update(Pembayaran pembayaran);
    public void delete(Pembayaran pembayaran);
    public long count(String where);
}
