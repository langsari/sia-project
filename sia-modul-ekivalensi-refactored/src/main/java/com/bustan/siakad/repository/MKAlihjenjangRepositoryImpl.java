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

import com.sia.modul.domain.MKAlihjenjang;

@Repository
@Transactional
public class MKAlihjenjangRepositoryImpl implements MKAlihjenjangRepository{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public List<MKAlihjenjang> get(String where, String order, int limit, int offset) {
		String dbWhere ="";
		String dbOrder ="";
		if(where != "") dbWhere = " WHERE "+where;
		if(order != "") dbOrder = " ORDER BY "+order;
				
		Query query = sessionFactory.getCurrentSession().createQuery("select mkAlihjenjang from MKAlihjenjang mkAlihjenjang join "
				+ "mkAlihjenjang.katalogAlihjenjang katalog"+
		dbWhere+dbOrder);
		
		if(limit != -1 && limit>0) {
			query.setFirstResult(offset);
			if(offset < 0) offset = 0;
			query.setMaxResults(limit);
		}
		
		return query.list();
	}

	@Override
	public MKAlihjenjang getById(UUID idMKAlihjenjang) {
		List<MKAlihjenjang> queryResult = sessionFactory.getCurrentSession().createQuery("select mkAlihjenjang from MKAlihjenjang mkAlihjenjang WHERE "
				+ "mkAlihjenjang.idMKAlihjenjang = '"+idMKAlihjenjang.toString()+"'").list();
		if(queryResult.size()==0) return null;
		else
		{
			return queryResult.get(0);
		}
	}

	@Override
	public UUID insert(MKAlihjenjang mkAlihjenjang) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		UUID insertId= (UUID)session.save(mkAlihjenjang);
		tx.commit();
		session.flush();
		session.close();
		return insertId;
	}

	@Override
	public void update(MKAlihjenjang mkAlihjenjang) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(mkAlihjenjang);
		tx.commit();
		session.flush();
		session.close();
	}

	@Override
	public void delete(MKAlihjenjang mkAlihjenjang) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.delete(mkAlihjenjang);
		tx.commit();
		session.flush();
		session.close();
	}

	@Override
	public long count(String where) {
		String dbWhere ="";
		if(where != "") dbWhere = " WHERE "+where;
		Query query = sessionFactory.getCurrentSession().createQuery(
		        "select count(*) from MKAlihjenjang mkAlihjenjang join mkAlihjenjang.katalogAlihjenjang katalog"+dbWhere);
		Long count = (Long)query.uniqueResult();
		return count;
	}

}
