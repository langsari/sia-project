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

import com.sia.modul.domain.KrsCalonPD;
import com.sia.modul.domain.MKLuar;

@Repository
@Transactional
public class KrsCalonPDRepositoryImpl implements KrsCalonPDRepository{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public List<KrsCalonPD> get(String where, String order, int limit, int offset) {
		String dbWhere ="";
		String dbOrder ="";
		if(where != "") dbWhere = " WHERE "+where;
		if(order != "") dbOrder = " ORDER BY "+order;
				
		Query query = sessionFactory.getCurrentSession().createQuery("select krsCalonPD from KrsCalonPD krsCalonPD left join "
				+ "krsCalonPD.calonPD calonPD left join krsCalonPD.mkAlihjenjang mkAlihjenjang left join krsCalonPD.konversiNilai konversiNilai"+
		dbWhere+dbOrder);
		
		if(limit != -1 && limit>0) {
			query.setFirstResult(offset);
			if(offset < 0) offset = 0;
			query.setMaxResults(limit);
		}
		
		return query.list();
	}

	@Override
	public KrsCalonPD getById(UUID idKrsCalonPD) {
		List<KrsCalonPD> queryResult = sessionFactory.getCurrentSession().createQuery("select krsCalonPD from KrsCalonPD krsCalonPD WHERE "
				+ "krsCalonPD.idKrsCalonPD = '"+idKrsCalonPD.toString()+"'").list();
		if(queryResult.size()==0) return null;
		else
		{
			return queryResult.get(0);
		}
	}

	@Override
	public UUID insert(KrsCalonPD krsCalonPD) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		UUID insertId= (UUID)session.save(krsCalonPD);
		tx.commit();
		session.flush();
		session.close();
		return insertId;
	}

	@Override
	public void update(KrsCalonPD krsCalonPD) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(krsCalonPD);
		tx.commit();
		session.flush();
		session.close();
	}

	@Override
	public void delete(KrsCalonPD krsCalonPD) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.delete(krsCalonPD);
		tx.commit();
		session.flush();
		session.close();
	}

	@Override
	public long count(String where) {
		String dbWhere ="";
		if(where != "") dbWhere = " WHERE "+where;
		Query query = sessionFactory.getCurrentSession().createQuery(
		        "select count(*) from KrsCalonPD krsCalonPD left join krsCalonPD.mkAlihjenjang mkAlihjenjang left join "
		        + "krsCalonPD.calonPD calonPD left join krsCalonPD.konversiNilai konversiNilai"+dbWhere);
		Long count = (Long)query.uniqueResult();
		return count;
	}

}
