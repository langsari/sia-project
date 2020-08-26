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

import com.sia.modul.domain.StatusKuisioner;

@Transactional
@Repository
public class StatusKuisionerRepositoryImpl implements StatusKuisionerRepository {
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public StatusKuisioner getById(UUID idStatus) {
		// TODO Auto-generated method stub
		return (StatusKuisioner) sessionFactory.getCurrentSession().get(StatusKuisioner.class, idStatus);
	}
	
	@Override
	public List<StatusKuisioner> getByKrsKuisioner(UUID idKrs, UUID idKuisioner) {
		// TODO Auto-generated method stub
		Query query = sessionFactory.getCurrentSession().createQuery("FROM StatusKuisioner WHERE id_krs='" + idKrs + "' AND id_kuisioner='" + idKuisioner + "'");
		return query.list();
	}
	
	@Override
	public List<StatusKuisioner> getByKrs(UUID idKrs) {
		// TODO Auto-generated method stub
		Query query = sessionFactory.getCurrentSession().createQuery("FROM StatusKuisioner WHERE id_krs='" + idKrs + "'");
		return query.list();
	}

	@Override
	public UUID insert(StatusKuisioner status) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		UUID insertId = (UUID) session.save(status);
		tx.commit();
		session.flush();
		session.close();
		return insertId;
	}
	
	@Override
	public void update(StatusKuisioner status) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(status);
		tx.commit();
		session.flush();
		session.close();
	}

	@Override
	public List<StatusKuisioner> getByPemb(UUID idPemb) {
		// TODO Auto-generated method stub
		String queryString = "SELECT sk FROM StatusKuisioner sk WHERE sk.krs.pemb.idPemb='" + idPemb + "'";
		Query query = sessionFactory.getCurrentSession().createQuery(queryString);
		return query.list();
	}

	@Override
	public List<StatusKuisioner> getByPembKuisioner(UUID idPemb,
			UUID idKuisioner) {
		// TODO Auto-generated method stub
		String queryString = "SELECT sk FROM StatusKuisioner sk WHERE sk.krs.pemb.idPemb='" + idPemb + "' AND sk.kuisioner.idKuisioner='" + idKuisioner + "'";
		Query query = sessionFactory.getCurrentSession().createQuery(queryString);
		return query.list();
	}

}
