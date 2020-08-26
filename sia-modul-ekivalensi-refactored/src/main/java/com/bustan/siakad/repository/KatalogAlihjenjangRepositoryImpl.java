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

import com.sia.modul.domain.KatalogAlihjenjang;

@Repository
@Transactional
public class KatalogAlihjenjangRepositoryImpl implements KatalogAlihjenjangRepository{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public List<KatalogAlihjenjang> get(String where, String order, int limit, int offset) {
		String dbWhere ="";
		String dbOrder ="";
		if(where != "") dbWhere = " WHERE "+where;
		if(order != "") dbOrder = " ORDER BY "+order;
				
		Query query = sessionFactory.getCurrentSession().createQuery("select katalog from KatalogAlihjenjang katalog"+
		dbWhere+dbOrder);
		
		if(limit != -1 && limit>0) {
			query.setFirstResult(offset);
			if(offset < 0) offset = 0;
			query.setMaxResults(limit);
		}
		
		return query.list();
	}

	@Override
	public KatalogAlihjenjang getById(UUID idKatalogAlihjenjang) {
		List<KatalogAlihjenjang> queryResult = sessionFactory.getCurrentSession().createQuery("select katalog from KatalogAlihjenjang katalog WHERE "
				+ "katalog.idKatalogAlihjenjang = '"+idKatalogAlihjenjang.toString()+"'").list();
		if(queryResult.size()==0) return null;
		else
		{
			return queryResult.get(0);
		}
	}

	@Override
	public UUID insert(KatalogAlihjenjang katalogAlihjenjang) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		UUID insertId= (UUID)session.save(katalogAlihjenjang);
		tx.commit();
		session.flush();
		session.close();
		return insertId;
	}

	@Override
	public void update(KatalogAlihjenjang katalogAlihjenjang) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(katalogAlihjenjang);
		tx.commit();
		session.flush();
		session.close();
	}

	@Override
	public void delete(KatalogAlihjenjang katalogAlihjenjang) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.delete(katalogAlihjenjang);
		tx.commit();
		session.flush();
		session.close();
	}

	@Override
	public long count(String where) {
		String dbWhere ="";
		if(where != "") dbWhere = " WHERE "+where;
		Query query = sessionFactory.getCurrentSession().createQuery(
		        "select count(*) from KatalogAlihjenjang katalog"+dbWhere);
		Long count = (Long)query.uniqueResult();
		return count;
	}

}
