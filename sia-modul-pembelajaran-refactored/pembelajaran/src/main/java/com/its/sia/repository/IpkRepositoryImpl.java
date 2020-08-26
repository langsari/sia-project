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
import com.sia.modul.domain.Ipk;
import com.sia.modul.domain.Pd;
import com.sia.modul.domain.Rombel;

@Repository
@Transactional
public class IpkRepositoryImpl implements IpkRepository {
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<Ipk> get(String where, String order, int limit, int offset) {
		String dbWhere ="";
		String dbOrder ="";
		if(where != "") dbWhere = " WHERE "+where;
		if(order != "") dbOrder = " ORDER BY "+order;
		
		Query query = sessionFactory.getCurrentSession().createQuery("select ipk from Ipk "
				+ "Ipk join ipk.pd pd "
				+dbWhere+dbOrder);
		
		if(limit != -1 && limit>0) {
			query.setFirstResult(offset);
			if(offset < 0) offset = 0;
			query.setMaxResults(limit);
		}
		
		return query.list();
	}

	@Override
	public Ipk getById(UUID idIpk) {
		List<Ipk> queryResult = sessionFactory.getCurrentSession().createQuery("from Ipk "
				+ "WHERE idIpk = '"+idIpk.toString()+"'").list();
		if(queryResult.size()==0) return null;
		return queryResult.get(0);
	}
	
	@Override
	public Ipk getByPd(Pd pd) {
		List<Ipk> queryResult = sessionFactory.getCurrentSession().createQuery("select ipk from Ipk ipk join ipk.pd pd "
				+ "WHERE pd.nimPd = '"+pd.getNimPd()+"'").list();
		if(queryResult.size()==0) return null;
		return queryResult.get(0);
	}

	@Override
	public UUID insert(Ipk Ipk) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		UUID insertId= (UUID)session.save(Ipk);
		tx.commit();
		session.flush();
		session.close();
		return insertId;
	}

	@Override
	public void update(Ipk Ipk) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(Ipk);
		tx.commit();
		session.flush();
		session.close();
	}

	@Override
	public void delete(Ipk Ipk) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.delete(Ipk);
		tx.commit();
		session.flush();
		session.close();
	}

	@Override
	public long count(String where) {
		String dbWhere ="";
		if(where != "") dbWhere = " WHERE "+where;
		Query query = sessionFactory.getCurrentSession().createQuery(
				"select count(*) from ipk "
				+ "Ipk join Ipk.pd pd "
				+ dbWhere);
		Long count = (Long)query.uniqueResult();
		return count;
	}

}
