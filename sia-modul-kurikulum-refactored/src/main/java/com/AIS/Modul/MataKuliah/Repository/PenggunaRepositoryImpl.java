package com.AIS.Modul.MataKuliah.Repository;

import java.util.List;
import java.util.UUID;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sia.main.domain.Pengguna;

@Transactional
@Repository
public class PenggunaRepositoryImpl implements PenggunaRepository {
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<Pengguna> get(String where, String order, int limit, int offset) {
		String dbWhere ="";
		String dbOrder ="";
		if(where != "") dbWhere = " WHERE "+where;
		if(order != "") dbOrder = " ORDER BY "+order;
		
		Query query = sessionFactory.getCurrentSession().createQuery("from Pengguna "
				+dbWhere+dbOrder);
		
		if(limit != -1 && limit>0) {
			query.setFirstResult(offset);
			if(offset < 0) offset = 0;
			query.setMaxResults(limit);
		}
		
		return query.list();
	}

	@Override
	public Pengguna getById(UUID idAnggotaRombel) {
		List<Pengguna> queryResult = sessionFactory.getCurrentSession().createQuery("from Pengguna "
				+ "WHERE idPengguna = '"+idAnggotaRombel.toString()+"'").list();
		if(queryResult.size()==0) return null;
		return queryResult.get(0);
	}

	@Override
	public UUID insert(Pengguna pengguna) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		UUID insertId= (UUID)session.save(pengguna);
		tx.commit();
		session.flush();
		session.close();
		return insertId;
	}

	@Override
	public void update(Pengguna pengguna) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(pengguna);
		tx.commit();
		session.flush();
		session.close();
	}

	@Override
	public void delete(Pengguna pengguna) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.delete(pengguna);
		tx.commit();
		session.flush();
		session.close();
	}

	@Override
	public long count(String where) {
		String dbWhere ="";
		if(where != "") dbWhere = " WHERE "+where;
		Query query = sessionFactory.getCurrentSession().createQuery(
				"select count(*) from Pengguna "
				+ dbWhere);
		Long count = (Long)query.uniqueResult();
		return count;
	}

}
