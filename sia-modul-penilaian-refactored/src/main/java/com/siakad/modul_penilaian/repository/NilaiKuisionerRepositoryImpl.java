package com.siakad.modul_penilaian.repository;

import java.util.List;
import java.util.UUID;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sia.modul.domain.NilaiKuisioner;

@Transactional
@Repository
public class NilaiKuisionerRepositoryImpl implements NilaiKuisionerRepository {
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public UUID insert(NilaiKuisioner nilaiKuisioner) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		UUID insertId = (UUID) session.save(nilaiKuisioner);
		tx.commit();
		session.flush();
		session.close();
		return insertId;
	}

	@Override
	public List<NilaiKuisioner> getByPembPertanyaan(UUID idPemb,
			UUID idPertanyaan) {
		// TODO Auto-generated method stub
		String queryString = "SELECT nk FROM NilaiKuisioner nk WHERE nk.krs.pemb.idPemb = '" + idPemb + "' AND nk.pertanyaanKuisioner.idPertanyaanKuisioner = '" + idPertanyaan + "'";
		Query query = sessionFactory.getCurrentSession().createQuery(queryString);
		return query.list();
	}

}
