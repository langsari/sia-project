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

import com.sia.modul.domain.MKWajibCalonPD;

@Repository
@Transactional
public class MKWajibCalonPDRepositoryImpl implements MKWajibCalonPDRepository{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public List<MKWajibCalonPD> get(String where, String order, int limit, int offset) {
		String dbWhere ="";
		String dbOrder ="";
		if(where != "") dbWhere = " WHERE "+where;
		if(order != "") dbOrder = " ORDER BY "+order;
				
		Query query = sessionFactory.getCurrentSession().createQuery("select mkWajibCalonPD from MKWajibCalonPD mkWajibCalonPD join "
				+ "mkWajibCalonPD.calonPD calonPD join mkWajibCalonPD.mk mk join mk.kurikulum kurikulum"+
		dbWhere+dbOrder);
		
		if(limit != -1 && limit>0) {
			query.setFirstResult(offset);
			if(offset < 0) offset = 0;
			query.setMaxResults(limit);
		}
		
		return query.list();
	}

	@Override
	public MKWajibCalonPD getById(UUID idMKWajibCalonPD) {
		List<MKWajibCalonPD> queryResult = sessionFactory.getCurrentSession().createQuery("select mkWajibCalonPD from MKWajibCalonPD mkWajibCalonPD WHERE "
				+ "mkWajibCalonPD.idMKWajibCalonPD = '"+idMKWajibCalonPD.toString()+"'").list();
		if(queryResult.size()==0) return null;
		else
		{
			return queryResult.get(0);
		}
	}

	@Override
	public UUID insert(MKWajibCalonPD mkWajibCalonPD) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		UUID insertId= (UUID)session.save(mkWajibCalonPD);
		tx.commit();
		session.flush();
		session.close();
		return insertId;
	}

	@Override
	public void update(MKWajibCalonPD mkWajibCalonPD) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(mkWajibCalonPD);
		tx.commit();
		session.flush();
		session.close();
	}

	@Override
	public void delete(MKWajibCalonPD mkWajibCalonPD) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.delete(mkWajibCalonPD);
		tx.commit();
		session.flush();
		session.close();
	}

	@Override
	public long count(String where) {
		String dbWhere ="";
		if(where != "") dbWhere = " WHERE "+where;
		Query query = sessionFactory.getCurrentSession().createQuery(
		        "select count(*) from MKWajibCalonPD mkWajibCalonPD join "
				+ "mkWajibCalonPD.calonPD calonPD join mkWajibCalonPD.mk mk join mk.kurikulum kurikulum"+dbWhere);
		Long count = (Long)query.uniqueResult();
		return count;
	}

}
