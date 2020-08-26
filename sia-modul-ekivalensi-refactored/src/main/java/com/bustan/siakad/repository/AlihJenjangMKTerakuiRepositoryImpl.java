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

import com.sia.modul.domain.AlihJenjangMKTerakui;

@Repository
@Transactional
public class AlihJenjangMKTerakuiRepositoryImpl implements AlihJenjangMKTerakuiRepository{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public List<AlihJenjangMKTerakui> get(String where, String order, int limit, int offset) {
		String dbWhere ="";
		String dbOrder ="";
		if(where != "") dbWhere = " WHERE "+where;
		if(order != "") dbOrder = " ORDER BY "+order;
				
		Query query = sessionFactory.getCurrentSession().createQuery("select alihJenjangMKTerakui from AlihJenjangMKTerakui alihJenjangMKTerakui join "
				+ "alihJenjangMKTerakui.calonPD calonPD join alihJenjangMKTerakui.mk mk join mk.kurikulum kurikulum"+
		dbWhere+dbOrder);
		
		if(limit != -1 && limit>0) {
			query.setFirstResult(offset);
			if(offset < 0) offset = 0;
			query.setMaxResults(limit);
		}
		
		return query.list();
	}

	@Override
	public AlihJenjangMKTerakui getById(UUID idAlihJenjangMKTerakui) {
		List<AlihJenjangMKTerakui> queryResult = sessionFactory.getCurrentSession().createQuery("select alihJenjangMKTerakui from AlihJenjangMKTerakui alihJenjangMKTerakui WHERE "
				+ "alihJenjangMKTerakui.idAlihJenjangMKTerakui = '"+idAlihJenjangMKTerakui.toString()+"'").list();
		if(queryResult.size()==0) return null;
		else
		{
			return queryResult.get(0);
		}
	}

	@Override
	public UUID insert(AlihJenjangMKTerakui alihJenjangMKTerakui) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		UUID insertId= (UUID)session.save(alihJenjangMKTerakui);
		tx.commit();
		session.flush();
		session.close();
		return insertId;
	}

	@Override
	public void update(AlihJenjangMKTerakui alihJenjangMKTerakui) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(alihJenjangMKTerakui);
		tx.commit();
		session.flush();
		session.close();
	}

	@Override
	public void delete(AlihJenjangMKTerakui alihJenjangMKTerakui) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.delete(alihJenjangMKTerakui);
		tx.commit();
		session.flush();
		session.close();
	}

	@Override
	public long count(String where) {
		String dbWhere ="";
		if(where != "") dbWhere = " WHERE "+where;
		Query query = sessionFactory.getCurrentSession().createQuery(
		        "select count(*) from AlihJenjangMKTerakui alihJenjangMKTerakui join "
				+ "alihJenjangMKTerakui.calonPD calonPD join alihJenjangMKTerakui.mk mk join mk.kurikulum kurikulum"+dbWhere);
		Long count = (Long)query.uniqueResult();
		return count;
	}

}
