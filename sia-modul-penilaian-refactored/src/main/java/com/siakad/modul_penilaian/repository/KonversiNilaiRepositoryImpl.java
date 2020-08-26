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

import com.sia.modul.domain.KonversiNilai;

@Transactional
@Repository
public class KonversiNilaiRepositoryImpl implements KonversiNilaiRepository {
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public List<KonversiNilai> getAll() {
		// TODO Auto-generated method stub
		Query query = sessionFactory.getCurrentSession().createQuery("FROM KonversiNilai WHERE aStatusKonversiAktif = TRUE ORDER BY nilaiHuruf DESC");
		return query.list();
	}

	@Override
	public UUID insert(KonversiNilai konversi) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		UUID insertId = (UUID)session.save(konversi);
		tx.commit();
		session.flush();
		session.close();
		return insertId;
	}

	@Override
	public void update(KonversiNilai konversi) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(konversi);
		tx.commit();
		session.flush();
		session.close();
	}

	@Override
	public void delete(UUID idKonversi) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		String delete = "UPDATE KonversiNilai SET aStatusKonversiAktif = FALSE WHERE idKonversi = :idKonversi";
		Query query = session.createQuery(delete);
		query.setParameter("idKonversi", idKonversi);
		query.executeUpdate();
		tx.commit();
		session.flush();
		session.close();
	}

}
