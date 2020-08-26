package com.siakad.modul_penilaian.repository;

import java.util.List;
import java.util.UUID;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sia.modul.domain.PendidikPengajar;

@Transactional
@Repository
public class PendidikPengajarRepositoryImpl implements
		PendidikPengajarRepository {
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void updateNilaiIpd(UUID idPemb, double nilai) {
		// TODO Auto-generated method stub
		String queryString = "UPDATE PendidikPengajar SET nilaiIpd = " + nilai + " WHERE id_pemb = '" + idPemb + "'";
		Query query = sessionFactory.getCurrentSession().createQuery(queryString);
		query.executeUpdate();
	}

	@Override
	public PendidikPengajar getKetuaByPemb(UUID idPemb) {
		// TODO Auto-generated method stub
		String queryString = "SELECT pp FROM PendidikPengajar pp WHERE pp.pemb.idPemb='" + idPemb + "' AND aPendidikPengajarKetua = TRUE";
		Query query = sessionFactory.getCurrentSession().createQuery(queryString);
		if(!query.list().isEmpty())
			return (PendidikPengajar) query.list().get(0);
		else
			return null;
	}

	@Override
	public List<PendidikPengajar> getByPemb(UUID idPemb) {
		// TODO Auto-generated method stub
		String queryString = "SELECT pp FROM PendidikPengajar pp WHERE pp.pemb.idPemb='" + idPemb + "'";
		Query query = sessionFactory.getCurrentSession().createQuery(queryString);
		return query.list();
	}

}
