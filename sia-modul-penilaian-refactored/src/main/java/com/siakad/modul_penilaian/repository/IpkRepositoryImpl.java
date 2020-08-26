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

import com.sia.modul.domain.Ipk;

@Transactional
@Repository
public class IpkRepositoryImpl implements IpkRepository {
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<Ipk> getAll() {
		// TODO Auto-generated method stub
		Query query = sessionFactory.getCurrentSession().createQuery("FROM Ipk");
		return query.list();
	}

	@Override
	public UUID insert(Ipk ipk) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		UUID insertId = (UUID) session.save(ipk);
		tx.commit();
		session.flush();
		session.close();
		return insertId;
	}

	@Override
	public void update(Ipk ipk) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(ipk);
		tx.commit();
		session.flush();
		session.close();
	}

	@Override
	public Ipk find(UUID idPd) {
		// TODO Auto-generated method stub
		Query query = sessionFactory.getCurrentSession().createQuery("SELECT ipk FROM Ipk as ipk WHERE id_pd = '" + idPd + "'");
		if (query.list().isEmpty())
			return null;
		else
			return (Ipk) query.list().get(0);
	}

}
