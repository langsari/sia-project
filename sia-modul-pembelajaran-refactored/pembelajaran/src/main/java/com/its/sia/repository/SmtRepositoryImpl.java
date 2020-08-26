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

import com.sia.modul.domain.Smt;

@Repository
@Transactional
public class SmtRepositoryImpl implements SmtRepository {
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<Smt> get(String where, String order, int limit, int offset) {
		String dbWhere ="";
		String dbOrder ="";
		if(where != "") dbWhere = " WHERE "+where;
		if(order != "") dbOrder = " ORDER BY "+order;
		
		Query query = sessionFactory.getCurrentSession().createQuery("from Smt"+dbWhere+dbOrder);
		
		if(limit != -1 && limit>0) {
			query.setFirstResult(offset);
			if(offset < 0) offset = 0;
			query.setMaxResults(limit);
		}
		
		return query.list();
	}

	@Override
	public Smt getById(UUID idSmt) {
		List<Smt> queryResult = sessionFactory.getCurrentSession().createQuery("from Smt WHERE idSmt = '"+idSmt.toString()+"'").list();
		if(queryResult.size()==0) return null;
		return queryResult.get(0);
	}

	@Override
	public UUID insert(Smt smt) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		UUID insertId= (UUID)session.save(smt);
		tx.commit();
		session.flush();
		session.close();
		return insertId;
	}

	@Override
	public void update(Smt smt) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(smt);
		tx.commit();
		session.flush();
		session.close();
	}

	@Override
	public void delete(UUID idSmt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public long count(String where) {
		String dbWhere ="";
		if(where != "") dbWhere = " WHERE "+where;
		Query query = sessionFactory.getCurrentSession().createQuery(
		        "select count(*) from Smt"+dbWhere);
		Long count = (Long)query.uniqueResult();
		return count;
	}

}
