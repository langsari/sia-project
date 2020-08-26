package com.AIS.Modul.MataKuliah.Repository;

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

@Transactional
@Repository
public class PrasyaratMKRepositoryImpl implements PrasyaratMKRepository {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public long count(String where) {
		// TODO Auto-generated method stub
		String dbWhere ="";
		if(where != "") dbWhere = " WHERE "+where;
		Query query = sessionFactory.getCurrentSession().createQuery(
		        "select count(*) from PrasyaratMK pMK join pMK.parentMK parent join pMK.childMK child "+dbWhere);
		Long count = (Long)query.uniqueResult();
		return count;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PrasyaratMK> get(String where, String order, int limit,
			int offset) {
		// TODO Auto-generated method stub
		String dbWhere ="";
		String dbOrder ="";
		if(where != "") dbWhere = " WHERE "+where;
		if(order != "") dbOrder = " ORDER BY "+order;
		 
		Query query = sessionFactory.getCurrentSession().createQuery("select pMK from PrasyaratMK pMK join pMK.parentMK parent join pMK.childMK child"+dbWhere+dbOrder);
		
		if(limit != -1 && limit>0) {
			query.setFirstResult(offset);
			if(offset < 0) offset = 0;
			query.setMaxResults(limit);
		}
		
		return query.list();
	}

	@Override
	public void update(PrasyaratMK prasyaratMK) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(prasyaratMK);
		tx.commit();
		session.flush();
		session.close();
	}

	@Override
	public UUID insert(PrasyaratMK prasyaratMK) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		UUID insertId= (UUID)session.save(prasyaratMK);
		tx.commit();
		session.flush();
		session.close();
		return insertId;
	}

	@Override
	@SuppressWarnings("unchecked")
	public PrasyaratMK findById(UUID idPrasyaratMK) {
		// TODO Auto-generated method stub
		List<PrasyaratMK> queryResult = sessionFactory.getCurrentSession().createQuery("select pMK from PrasyaratMK pMK join pMK.parentMK parent join pMK.childMK child WHERE pMK.idPrasyaratMK='"+idPrasyaratMK.toString()+"'").list();
		if(queryResult.size()==0) return null;
		return queryResult.get(0);
	}

	@Override
	public List<PrasyaratMK> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PrasyaratMK> findParent(UUID idMK) {
		// TODO Auto-generated method stub
		List<PrasyaratMK> queryResult = sessionFactory.getCurrentSession().createQuery("select pMK from PrasyaratMK pMK "
				+ "join pMK.parentMK parent "
				+ "join pMK.childMK child WHERE child.idMK='"+idMK.toString()+"'").list();
		if(queryResult.size()==0) return null;
		return queryResult;
	} 
	
}
