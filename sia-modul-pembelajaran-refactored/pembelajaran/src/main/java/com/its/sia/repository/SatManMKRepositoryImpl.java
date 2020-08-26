package com.its.sia.repository;

import java.util.List;
import java.util.UUID;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sia.modul.domain.SatManMK;

@Repository
@Transactional
public class SatManMKRepositoryImpl implements SatManMKRepository {
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<SatManMK> get(String where, String order, int limit, int offset) {
		String dbWhere ="";
		String dbOrder ="";
		if(where != "") dbWhere = " WHERE "+where;
		if(order != "") dbOrder = " ORDER BY "+order;
		
		Query query = sessionFactory.getCurrentSession().createQuery("select satManMK from SatManMK satManMK "
				+ "join satManMK.mk mk left join mk.rumpunMK rumpunMK join mk.kurikulum kurikulum "
				+ "join kurikulum.satMan satManMilik "
				+ "join satManMK.satMan satManUntuk "
				+dbWhere+dbOrder);
		
		if(limit != -1 && limit>0) {
			query.setFirstResult(offset);
			if(offset < 0) offset = 0;
			query.setMaxResults(limit);
		}
		
		return query.list();
	}

	@Override
	public SatManMK getById(UUID idSatManMK) {
		List<SatManMK> queryResult = sessionFactory.getCurrentSession().createQuery("from SatManMK "
				+ "WHERE idSatManMK = '"+idSatManMK.toString()+"'").list();
		if(queryResult.size()==0) return null;
		return queryResult.get(0);
	}

	@Override
	public UUID insert(SatManMK mk) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		UUID insertId= (UUID)session.save(mk);
		tx.commit();
		session.flush();
		session.close();
		return insertId;
	}

	@Override
	public void update(SatManMK mk) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(mk);
		tx.commit();
		session.flush();
		session.close();
	}

	@Override
	public void delete(SatManMK mk) {
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
				"select count(*) from SatManMK satManMK "
				+ "join satManMK.mk mk left join mk.rumpunMK rumpunMK join mk.kurikulum kurikulum "
				+ "join kurikulum.satMan satManMilik "
				+ "join satManMK.satMan satManUntuk "
				+ dbWhere);
		Long count = (Long)query.uniqueResult();
		return count;
	}

}
