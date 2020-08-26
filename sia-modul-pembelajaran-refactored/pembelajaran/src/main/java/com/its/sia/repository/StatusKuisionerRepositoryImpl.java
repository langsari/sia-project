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

import com.sia.modul.domain.StatusKuisioner;

@Repository
@Transactional
public class StatusKuisionerRepositoryImpl implements StatusKuisionerRepository {
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<StatusKuisioner> get(String where, String order, int limit, int offset) {
		String dbWhere ="";
		String dbOrder ="";
		if(where != "") dbWhere = " WHERE "+where;
		if(order != "") dbOrder = " ORDER BY "+order;
		
		Query query = sessionFactory.getCurrentSession().createQuery("select statusKuisioner from StatusKuisioner "
				+ "statusKuisioner join statusKuisioner.krs krs "
				+dbWhere+dbOrder);
		
		if(limit != -1 && limit>0) {
			query.setFirstResult(offset);
			if(offset < 0) offset = 0;
			query.setMaxResults(limit);
		}
		
		return query.list();
	}

	@Override
	public StatusKuisioner getById(UUID idAnggotaRombel) {
		List<StatusKuisioner> queryResult = sessionFactory.getCurrentSession().createQuery("from StatusKuisioner "
				+ "WHERE idStatusKuisioner = '"+idAnggotaRombel.toString()+"'").list();
		if(queryResult.size()==0) return null;
		return queryResult.get(0);
	}

	@Override
	public UUID insert(StatusKuisioner statusKuisioner) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		UUID insertId= (UUID)session.save(statusKuisioner);
		tx.commit();
		session.flush();
		session.close();
		return insertId;
	}

	@Override
	public void update(StatusKuisioner statusKuisioner) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(statusKuisioner);
		tx.commit();
		session.flush();
		session.close();
	}

	@Override
	public void delete(StatusKuisioner statusKuisioner) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.delete(statusKuisioner);
		tx.commit();
		session.flush();
		session.close();
	}

	@Override
	public long count(String where) {
		String dbWhere ="";
		if(where != "") dbWhere = " WHERE "+where;
		Query query = sessionFactory.getCurrentSession().createQuery(
				"select count(*) from StatusKuisioner "
				+ "statusKuisioner join statusKuisioner.krs krs "
				+ dbWhere);
		Long count = (Long)query.uniqueResult();
		return count;
	}

}
