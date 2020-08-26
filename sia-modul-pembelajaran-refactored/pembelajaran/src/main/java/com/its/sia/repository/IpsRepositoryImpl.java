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

import com.sia.modul.domain.AnggotaRombel;
import com.sia.modul.domain.Ips;
import com.sia.modul.domain.Rombel;

@Repository
@Transactional
public class IpsRepositoryImpl implements IpsRepository {
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<Ips> get(String where, String order, int limit, int offset) {
		String dbWhere ="";
		String dbOrder ="";
		if(where != "") dbWhere = " WHERE "+where;
		if(order != "") dbOrder = " ORDER BY "+order;
		
		Query query = sessionFactory.getCurrentSession().createQuery("select ips from Ips "
				+ "ips join ips.pd pd join ips.tglSmt tglSmt join tglSmt.thnAjaran thnAjaran join tglSmt.smt smt "
				+dbWhere+dbOrder);
		
		if(limit != -1 && limit>0) {
			query.setFirstResult(offset);
			if(offset < 0) offset = 0;
			query.setMaxResults(limit);
		}
		
		return query.list();
	}

	@Override
	public Ips getById(UUID idAnggotaRombel) {
		List<Ips> queryResult = sessionFactory.getCurrentSession().createQuery("from Ips "
				+ "WHERE idIps = '"+idAnggotaRombel.toString()+"'").list();
		if(queryResult.size()==0) return null;
		return queryResult.get(0);
	}

	@Override
	public UUID insert(Ips ips) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		UUID insertId= (UUID)session.save(ips);
		tx.commit();
		session.flush();
		session.close();
		return insertId;
	}

	@Override
	public void update(Ips ips) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(ips);
		tx.commit();
		session.flush();
		session.close();
	}

	@Override
	public void delete(Ips ips) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.delete(ips);
		tx.commit();
		session.flush();
		session.close();
	}

	@Override
	public long count(String where) {
		String dbWhere ="";
		if(where != "") dbWhere = " WHERE "+where;
		Query query = sessionFactory.getCurrentSession().createQuery(
				"select count(*) from Ips "
				+ "ips join ips.pd pd join ips.tglSmt tglSmt join tglSmt.thnAjaran thnAjaran join tglSmt.smt smt "
				+ dbWhere);
		Long count = (Long)query.uniqueResult();
		return count;
	}

}
