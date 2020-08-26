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

import com.sia.modul.domain.StsKehadiran;
import com.its.sia.service.StsKehadiranService;

@Repository
@Transactional
public class StsKehadiranRepositoryImpl implements StsKehadiranRepository {
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<StsKehadiran> get(String where, String order, int limit, int offset) {
		String dbWhere ="";
		String dbOrder ="";
		if(where != "") dbWhere = " WHERE "+where;
		if(order != "") dbOrder = " ORDER BY "+order;
		
		Query query = sessionFactory.getCurrentSession().createQuery("from StsKehadiran"+dbWhere+dbOrder);
		
		if(limit != -1 && limit>0) {
			query.setFirstResult(offset);
			if(offset < 0) offset = 0;
			query.setMaxResults(limit);
		}
		
		return query.list();
	}

	@Override
	public StsKehadiran getById(UUID idStsKehadiran) {
		List<StsKehadiran> queryResult = sessionFactory.getCurrentSession().createQuery("from StsKehadiran WHERE idStsKehadiran = '"+idStsKehadiran.toString()+"'").list();
		if(queryResult.size()==0) return null;
		return queryResult.get(0);
	}

	@Override
	public UUID insert(StsKehadiran smt) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		UUID insertId= (UUID)session.save(smt);
		tx.commit();
		session.flush();
		session.close();
		return insertId;
	}

	@Override
	public void update(StsKehadiran smt) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(smt);
		tx.commit();
		session.flush();
		session.close();
	}

	@Override
	public void delete(UUID idStsKehadiran) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public long count(String where) {
		String dbWhere ="";
		if(where != "") dbWhere = " WHERE "+where;
		Query query = sessionFactory.getCurrentSession().createQuery(
		        "select count(*) from StsKehadiran"+dbWhere);
		Long count = (Long)query.uniqueResult();
		return count;
	}

}
