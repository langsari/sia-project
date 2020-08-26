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

import com.sia.modul.domain.EkuivalensiPd;

@Repository
@Transactional
public class EkuivalensiPdRepositoryImpl implements EkuivalensiPdRepository{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public List<EkuivalensiPd> get(String where, String order, int limit, int offset) {
		String dbWhere ="";
		String dbOrder ="";
		if(where != "") dbWhere = " WHERE "+where;
		if(order != "") dbOrder = " ORDER BY "+order;
				
		Query query = sessionFactory.getCurrentSession().createQuery("select ekuivalensiPd from EkuivalensiPd ekuivalensiPd left join "
				+ "ekuivalensiPd.pd pd left join ekuivalensiPd.ptk ptk left join ekuivalensiPd.kurikulumLama kurikulumLama left join "
				+ "ekuivalensiPd.kurikulumBaru kurikulumBaru"+
		dbWhere+dbOrder);
		
		if(limit != -1 && limit>0) {
			query.setFirstResult(offset);
			if(offset < 0) offset = 0;
			query.setMaxResults(limit);
		}
		
		return query.list();
	}

	@Override
	public EkuivalensiPd getById(UUID idEkuivalensiPd) {
		List<EkuivalensiPd> queryResult = sessionFactory.getCurrentSession().createQuery("select ekuivalensiPd from EkuivalensiPd ekuivalensiPd WHERE "
				+ "ekuivalensiPd.idEkuivalensiPd = '"+idEkuivalensiPd.toString()+"'").list();
		if(queryResult.size()==0) return null;
		else
		{
			return queryResult.get(0);
		}
	}

	@Override
	public UUID insert(EkuivalensiPd ekuivalensiPd) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		UUID insertId= (UUID)session.save(ekuivalensiPd);
		tx.commit();
		session.flush();
		session.close();
		return insertId;
	}

	@Override
	public void update(EkuivalensiPd ekuivalensiPd) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(ekuivalensiPd);
		tx.commit();
		session.flush();
		session.close();
	}

	@Override
	public void delete(EkuivalensiPd mkAlihjenjang) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.delete(mkAlihjenjang);
		tx.commit();
		session.flush();
		session.close();
	}

	@Override
	public long count(String where) {
		String dbWhere ="";
		if(where != "") dbWhere = " WHERE "+where;
		Query query = sessionFactory.getCurrentSession().createQuery(
		        "select count(*) from EkuivalensiPd ekuivalensiPd left join "
				+ "ekuivalensiPd.pd pd left join ekuivalensiPd.ptk ptk left join ekuivalensiPd.kurikulumLama kurikulumLama left join "
				+ "ekuivalensiPd.kurikulumBaru kurikulumBaru"+dbWhere);
		Long count = (Long)query.uniqueResult();
		return count;
	}

}
