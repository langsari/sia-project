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

import com.sia.modul.domain.KatalogSatMan;

@Repository
@Transactional
public class KatalogSatManRepositoryImpl implements KatalogSatManRepository{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public List<KatalogSatMan> get(String where, String order, int limit, int offset) {
		String dbWhere ="";
		String dbOrder ="";
		if(where != "") dbWhere = " WHERE "+where;
		if(order != "") dbOrder = " ORDER BY "+order;
				
		Query query = sessionFactory.getCurrentSession().createQuery("select katalogSatMan from KatalogSatMan katalogSatMan join "
				+ "katalogSatMan.katalog katalog join katalogSatMan.satMan satMan"+
		dbWhere+dbOrder);
		
		if(limit != -1 && limit>0) {
			query.setFirstResult(offset);
			if(offset < 0) offset = 0;
			query.setMaxResults(limit);
		}
		
		return query.list();
	}

	@Override
	public KatalogSatMan getById(UUID idKatalogSatMan) {
		List<KatalogSatMan> queryResult = sessionFactory.getCurrentSession().createQuery("select katalogSatMan from KatalogSatMan katalogSatMan WHERE "
				+ "katalogSatMan.idKatalogSatMan = '"+idKatalogSatMan.toString()+"'").list();
		if(queryResult.size()==0) return null;
		else
		{
			return queryResult.get(0);
		}
	}

	@Override
	public UUID insert(KatalogSatMan mkAlihjenjang) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		UUID insertId= (UUID)session.save(mkAlihjenjang);
		tx.commit();
		session.flush();
		session.close();
		return insertId;
	}

	@Override
	public void update(KatalogSatMan mkAlihjenjang) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(mkAlihjenjang);
		tx.commit();
		session.flush();
		session.close();
	}

	@Override
	public void delete(KatalogSatMan mkAlihjenjang) {
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
		        "select count(*) from KatalogSatMan katalogSatMan join "
				+ "katalogSatMan.katalog katalog join katalogSatMan.satMan satMan"+dbWhere);
		Long count = (Long)query.uniqueResult();
		return count;
	}

}
