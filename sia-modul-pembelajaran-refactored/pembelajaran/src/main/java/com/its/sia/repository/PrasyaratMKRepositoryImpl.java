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

import com.sia.modul.domain.PrasyaratMK;

@Repository
@Transactional
public class PrasyaratMKRepositoryImpl implements PrasyaratMKRepository {
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<PrasyaratMK> get(String where, String order, int limit, int offset) {
		String dbWhere ="";
		String dbOrder ="";
		if(where != "") dbWhere = " WHERE "+where;
		if(order != "") dbOrder = " ORDER BY "+order;
				
		Query query = sessionFactory.getCurrentSession().createQuery("select prasyaratMK from PrasyaratMK prasyaratMK join prasyaratMK.parentMK parentMK join prasyaratMK.childMK childMK "+dbWhere+dbOrder);
		
		if(limit != -1 && limit>0) {
			query.setFirstResult(offset);
			if(offset < 0) offset = 0;
			query.setMaxResults(limit);
		}
		
		return query.list();
	}

	@Override
	public PrasyaratMK getById(UUID idPrasyaratMK) {
		List<PrasyaratMK> queryResult = sessionFactory.getCurrentSession().createQuery("from PrasyaratMK WHERE idPrasyaratMK = '"+idPrasyaratMK.toString()+"'").list();
		if(queryResult.size()==0) return null;
		else
		{
			return queryResult.get(0);
		}
	}

	@Override
	public UUID insert(PrasyaratMK prasyaratMK) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		ThnAjaranRepository thnAjaranRepository;
		UUID insertId= (UUID)session.save(prasyaratMK);
		tx.commit();
		session.flush();
		session.close();
		return insertId;
	}

	@Override
	public void update(PrasyaratMK prasyaratMK) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(prasyaratMK);
		tx.commit();
		session.flush();
		session.close();
	}

	@Override
	public void delete(PrasyaratMK prasyaratMK) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public long count(String where) {
		String dbWhere ="";
		if(where != "") dbWhere = " WHERE "+where;
		Query query = sessionFactory.getCurrentSession().createQuery(
		        "select count(*) from PrasyaratMK prasyaratMK join prasyaratMK.parentMK parentMK join prasyaratMK.childMK childMK "+dbWhere);
		Long count = (Long)query.uniqueResult();
		return count;
	}

}
