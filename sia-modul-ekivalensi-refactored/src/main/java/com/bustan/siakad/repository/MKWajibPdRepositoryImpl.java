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

import com.sia.modul.domain.MKWajibPd;

@Repository
@Transactional
public class MKWajibPdRepositoryImpl implements MKWajibPdRepository{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public List<MKWajibPd> get(String where, String order, int limit, int offset) {
		String dbWhere ="";
		String dbOrder ="";
		if(where != "") dbWhere = " WHERE "+where;
		if(order != "") dbOrder = " ORDER BY "+order;
				
		Query query = sessionFactory.getCurrentSession().createQuery("select mkWajibPd from MKWajibPd mkWajibPd left join "
				+ "mkWajibPd.ekuivalensiPd ekuivalensiPd left join mkWajibPd.mk mk"+
		dbWhere+dbOrder);
		
		if(limit != -1 && limit>0) {
			query.setFirstResult(offset);
			if(offset < 0) offset = 0;
			query.setMaxResults(limit);
		}
		
		return query.list();
	}

	@Override
	public MKWajibPd getById(UUID idMKWajibPd) {
		List<MKWajibPd> queryResult = sessionFactory.getCurrentSession().createQuery("select mkWajibPd from MKWajibPd mkWajibPd WHERE "
				+ "mkWajibPd.idMKWajibPd = '"+idMKWajibPd.toString()+"'").list();
		if(queryResult.size()==0) return null;
		else
		{
			return queryResult.get(0);
		}
	}

	@Override
	public UUID insert(MKWajibPd mkWajibPd) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		UUID insertId= (UUID)session.save(mkWajibPd);
		tx.commit();
		session.flush();
		session.close();
		return insertId;
	}

	@Override
	public void update(MKWajibPd mkWajibPd) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(mkWajibPd);
		tx.commit();
		session.flush();
		session.close();
	}

	@Override
	public void delete(MKWajibPd mkWajibPd) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.delete(mkWajibPd);
		tx.commit();
		session.flush();
		session.close();
	}

	@Override
	public long count(String where) {
		String dbWhere ="";
		if(where != "") dbWhere = " WHERE "+where;
		Query query = sessionFactory.getCurrentSession().createQuery(
		        "select count(*) from MKWajibPd mkWajibPd left join "
				+ "mkWajibPd.ekuivalensiPd ekuivalensiPd left join mkWajibPd.mk mk"+dbWhere);
		Long count = (Long)query.uniqueResult();
		return count;
	}

}
