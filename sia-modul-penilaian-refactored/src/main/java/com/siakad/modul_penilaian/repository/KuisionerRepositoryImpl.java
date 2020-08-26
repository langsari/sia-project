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

import com.sia.modul.domain.Kuisioner;

@Transactional
@Repository
public class KuisionerRepositoryImpl implements KuisionerRepository {
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<Kuisioner> get(String where, String order, int limit, int offset) {
		// TODO Auto-generated method stub
		String dbWhere = "";
		String dbOrder = "";
		if(where != "")
			dbWhere += " WHERE " + where;
		if(order != "")
			dbOrder += " ORDER BY " + order;
		Query query = sessionFactory.getCurrentSession().createQuery("FROM Kuisioner" + dbWhere + dbOrder);
		return query.list();
	}

	@Override
	public UUID insert(Kuisioner kuisioner) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		UUID insertId = (UUID) session.save(kuisioner);
		tx.commit();
		session.flush();
		session.close();
		return insertId;
	}
	
	@Override
	public void update(Kuisioner kuisioner) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(kuisioner);
		tx.commit();
		session.flush();
		session.close();
	}
	
	@Override
	public void delete(UUID idKuisioner) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		String delete = "UPDATE Kuisioner SET aKuisionerAktif = FALSE WHERE idKuisioner = :idKuisioner";
		Query query = session.createQuery(delete);
		query.setParameter("idKuisioner", idKuisioner);
		query.executeUpdate();
		tx.commit();
		session.flush();
		session.close();
	}

	@Override
	public Kuisioner getById(UUID idKuisioner) {
		// TODO Auto-generated method stub
		return (Kuisioner) sessionFactory.getCurrentSession().get(Kuisioner.class, idKuisioner);
	}

}
