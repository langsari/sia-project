package com.its.sia.repository;

import java.util.List;
import java.util.UUID;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sia.modul.domain.AnggotaRombel;
import com.sia.modul.domain.BatasAmbilSks;
import com.sia.modul.domain.Ips;
import com.sia.modul.domain.Rombel;

@Repository
@Transactional
public class BatasAmbilSksRepositoryImpl implements BatasAmbilSksRepository {
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<BatasAmbilSks> get(String where, String order, int limit, int offset) {
		String dbWhere ="";
		String dbOrder ="";
		if(where != "") dbWhere = " WHERE "+where;
		if(order != "") dbOrder = " ORDER BY "+order;
		
		Query query = sessionFactory.getCurrentSession().createQuery("from BatasAmbilSks "
				+dbWhere+dbOrder);
		
		if(limit != -1 && limit>0) {
			query.setFirstResult(offset);
			if(offset < 0) offset = 0;
			query.setMaxResults(limit);
		}
		
		return query.list();
	}

	@Override
	public BatasAmbilSks getById(UUID idBatasAmbilSks) {
		List<BatasAmbilSks> queryResult = sessionFactory.getCurrentSession().createQuery("from BatasAmbilSks "
				+ "WHERE idBatasAmbilSks = '"+idBatasAmbilSks.toString()+"'").list();
		if(queryResult.size()==0) return null;
		return queryResult.get(0);
	}

	@Override
	public UUID insert(BatasAmbilSks batasAmbilSks) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		UUID insertId= (UUID)session.save(batasAmbilSks);
		tx.commit();
		session.flush();
		session.close();
		return insertId;
	}

	@Override
	public void update(BatasAmbilSks batasAmbilSks) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(batasAmbilSks);
		tx.commit();
		session.flush();
		session.close();
	}

	@Override
	public void delete(BatasAmbilSks batasAmbilSks) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.delete(batasAmbilSks);
		tx.commit();
		session.flush();
		session.close();
	}

	@Override
	public long count(String where) {
		String dbWhere ="";
		if(where != "") dbWhere = " WHERE "+where;
		Query query = sessionFactory.getCurrentSession().createQuery(
				"select count(*) from BatasAmbilSks "
				+ dbWhere);
		Long count = (Long)query.uniqueResult();
		return count;
	}

}
