package com.siakad.modul_penilaian.repository;

import java.util.List;
import java.util.UUID;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sia.main.domain.Peran;

@Transactional
@Repository
public class PeranRepositoryImpl implements PeranRepository {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<Peran> get(String where, String order, int limit, int offset) {
		String dbWhere ="";
		String dbOrder ="";
		if(where != "") dbWhere = " WHERE "+where;
		if(order != "") dbOrder = " ORDER BY "+order;
		
		Query query = sessionFactory.getCurrentSession().createQuery("from Peran "
				+dbWhere+dbOrder);
		
		if(limit != -1 && limit>0) {
			query.setFirstResult(offset);
			if(offset < 0) offset = 0;
			query.setMaxResults(limit);
		}
		
		return query.list();
	}

	@Override
	public Peran getById(UUID idAnggotaRombel) {
		List<Peran> queryResult = sessionFactory.getCurrentSession().createQuery("from Peran "
				+ "WHERE idPeran = '"+idAnggotaRombel.toString()+"'").list();
		if(queryResult.size()==0) return null;
		return queryResult.get(0);
	}

	@Override
	public UUID insert(Peran peran) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		UUID insertId= (UUID)session.save(peran);
		tx.commit();
		session.flush();
		session.close();
		return insertId;
	}

	@Override
	public void update(Peran peran) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(peran);
		tx.commit();
		session.flush();
		session.close();
	}

	@Override
	public void delete(Peran peran) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.delete(peran);
		tx.commit();
		session.flush();
		session.close();
	}

	@Override
	public long count(String where) {
		String dbWhere ="";
		if(where != "") dbWhere = " WHERE "+where;
		Query query = sessionFactory.getCurrentSession().createQuery(
				"select count(*) from Peran "
				+ dbWhere);
		Long count = (Long)query.uniqueResult();
		return count;
	}

}
