package com.bustan.siakad.repository;

import java.util.List;
import java.util.UUID;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sia.modul.domain.Ptk;

@Repository
@Transactional
public class PtkRepositoryImpl implements PtkRepository {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public List<Ptk> get() {
		return get("");
	}

	@Override
	public List<Ptk> get(String where) {
		return get(where,"");
	}

	@Override
	public List<Ptk> get(String where, String order) {
		return get(where,order,-1,-1);
	}
	
	@Override
	public List<Ptk> get(String where, String order, int limit, int offset) {
		String dbWhere ="";
		String dbOrder ="";
		if(where != "") dbWhere = " WHERE "+where;
		if(order != "") dbOrder = " ORDER BY "+order;
		
		Query query = sessionFactory.getCurrentSession().createQuery("from Ptk"+dbWhere+dbOrder);
		
		if(limit != -1 && limit>0) {
			query.setFirstResult(offset);
			if(offset < 0) offset = 0;
			query.setMaxResults(limit);
		}
		
		return query.list();
	}

	@Override
	public Ptk getById(UUID idPtk) {
		List<Ptk> queryResult = sessionFactory.getCurrentSession().createQuery("from Ptk WHERE idPtk = '"+idPtk.toString()+"'").list();
		if(queryResult.size()==0) return null;
		return queryResult.get(0);
	}

	@Override
	public UUID insert(Ptk ptk) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		UUID insertId= (UUID)session.save(ptk);
		tx.commit();
		session.flush();
		session.close();
		return insertId;
	}

	@Override
	public void update(Ptk ptk) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(ptk);
		tx.commit();
		session.flush();
		session.close();
	}

	@Override
	public void delete(UUID idPtk) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public long count(String where) {
		String dbWhere ="";
		if(where != "") dbWhere = " WHERE "+where;
		Query query = sessionFactory.getCurrentSession().createQuery(
		        "select count(*) from Ptk"+dbWhere);
		Long count = (Long)query.uniqueResult();
		return count;
	}

}
