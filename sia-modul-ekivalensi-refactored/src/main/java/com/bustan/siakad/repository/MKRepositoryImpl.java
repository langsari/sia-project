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

import com.sia.modul.domain.MK;

@Repository
@Transactional
public class MKRepositoryImpl implements MKRepository {
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<MK> get(String where, String order, int limit, int offset) {
		String dbWhere ="";
		String dbOrder ="";
		if(where != "") dbWhere = " WHERE "+where;
		if(order != "") dbOrder = " ORDER BY "+order;
		
		Query query = sessionFactory.getCurrentSession().createQuery("select mk from MK "
				+ "mk join mk.kurikulum kurikulum "
				+ "join kurikulum.satMan satMan"
				+dbWhere+dbOrder);
		
		if(limit != -1 && limit>0) {
			query.setFirstResult(offset);
			if(offset < 0) offset = 0;
			query.setMaxResults(limit);
		}
		
		return query.list();
	}

	@Override
	public MK getById(UUID idMK) {
		List<MK> queryResult = sessionFactory.getCurrentSession().createQuery("select mk from MK "
				+ "mk join mk.kurikulum kurikulum "
				+ "WHERE mk.idMK = '"+idMK.toString()+"'").list();
		if(queryResult.size()==0) return null;
		return queryResult.get(0);
	}

	@Override
	public UUID insert(MK mk) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		UUID insertId= (UUID)session.save(mk);
		tx.commit();
		session.flush();
		session.close();
		return insertId;
	}

	@Override
	public void update(MK mk) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(mk);
		tx.commit();
		session.flush();
		session.close();
	}

	@Override
	public void delete(MK mk) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.delete(mk);
		tx.commit();
		session.flush();
		session.close();
	}

	@Override
	public long count(String where) {
		String dbWhere ="";
		if(where != "") dbWhere = " WHERE "+where;
		Query query = sessionFactory.getCurrentSession().createQuery(
				"select count(*) from MK "
				+ "mk left join mk.rumpunMK rumpunMK join mk.kurikulum kurikulum "
				+ "join kurikulum.satMan satMan"
				+ dbWhere);
		Long count = (Long)query.uniqueResult();
		return count;
	}

}
