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

import com.sia.modul.domain.KrsHapus;

@Repository
@Transactional
public class KrsHapusRepositoryImpl implements KrsHapusRepository{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public List<KrsHapus> get(String where, String order, int limit, int offset) {
		String dbWhere ="";
		String dbOrder ="";
		if(where != "") dbWhere = " WHERE "+where;
		if(order != "") dbOrder = " ORDER BY "+order;
				
		Query query = sessionFactory.getCurrentSession().createQuery("select krsHapus from KrsHapus krsHapus left join "
				+ "krsHapus.ekuivalensiPd ekuivalensiPd left join krsHapus.krs krs"+
		dbWhere+dbOrder);
		
		if(limit != -1 && limit>0) {
			query.setFirstResult(offset);
			if(offset < 0) offset = 0;
			query.setMaxResults(limit);
		}
		
		return query.list();
	}

	@Override
	public KrsHapus getById(UUID idKrsHapus) {
		List<KrsHapus> queryResult = sessionFactory.getCurrentSession().createQuery("select krsHapus from KrsHapus krsHapus WHERE "
				+ "krsHapus.idMKKrsHapus = '"+idKrsHapus.toString()+"'").list();
		if(queryResult.size()==0) return null;
		else
		{
			return queryResult.get(0);
		}
	}

	@Override
	public UUID insert(KrsHapus krsHapus) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		UUID insertId= (UUID)session.save(krsHapus);
		tx.commit();
		session.flush();
		session.close();
		return insertId;
	}

	@Override
	public void update(KrsHapus krsHapus) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(krsHapus);
		tx.commit();
		session.flush();
		session.close();
	}

	@Override
	public void delete(KrsHapus krsHapus) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.delete(krsHapus);
		tx.commit();
		session.flush();
		session.close();
	}

	@Override
	public long count(String where) {
		String dbWhere ="";
		if(where != "") dbWhere = " WHERE "+where;
		Query query = sessionFactory.getCurrentSession().createQuery(
		        "select count(*) from KrsHapus krsHapus left join "
				+ "krsHapus.ekuivalensiPd ekuivalensiPd left join krsHapus.krs krs"+dbWhere);
		Long count = (Long)query.uniqueResult();
		return count;
	}

}
