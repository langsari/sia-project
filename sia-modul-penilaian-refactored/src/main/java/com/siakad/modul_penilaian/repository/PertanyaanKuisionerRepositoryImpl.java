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

import com.sia.modul.domain.PertanyaanKuisioner;

@Transactional
@Repository
public class PertanyaanKuisionerRepositoryImpl implements
		PertanyaanKuisionerRepository {
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<PertanyaanKuisioner> getByIdKuisioner(UUID idKuisioner) {
		// TODO Auto-generated method stub
		Query query = sessionFactory.getCurrentSession().createQuery("FROM PertanyaanKuisioner WHERE id_kuisioner='" + idKuisioner + "' AND aPertanyaanAktif = TRUE");
		return query.list();
	}
	
	@Override
	public PertanyaanKuisioner getById(UUID idPertanyaan) {
		// TODO Auto-generated method stub
		return (PertanyaanKuisioner) sessionFactory.getCurrentSession().get(PertanyaanKuisioner.class, idPertanyaan);
	}
	
	@Override
	public UUID insert(PertanyaanKuisioner pertanyaan) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		UUID insertId = (UUID) session.save(pertanyaan);
		tx.commit();
		session.flush();
		session.close();
		return insertId;
	}

	@Override
	public void update(PertanyaanKuisioner pertanyaan) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(pertanyaan);
		tx.commit();
		session.flush();
		session.close();
	}

	@Override
	public void delete(UUID idPertanyaan) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		String delete = "UPDATE PertanyaanKuisioner SET aPertanyaanAktif = FALSE WHERE idPertanyaanKuisioner = :idPertanyaan";
		Query query = session.createQuery(delete);
		query.setParameter("idPertanyaan", idPertanyaan);
		query.executeUpdate();
		tx.commit();
		session.flush();
		session.close();
	}

}
