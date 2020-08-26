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

import com.sia.modul.domain.Krs;
import com.sia.modul.domain.KrsHapus;

@Repository
@Transactional
public class KrsRepositoryImpl implements KrsRepository{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public List<Krs> get(String where, String order, int limit, int offset) {
		String dbWhere ="";
		String dbOrder ="";
		if(where != "") dbWhere = " WHERE "+where;
		if(order != "") dbOrder = " ORDER BY "+order;
				
		Query query = sessionFactory.getCurrentSession().createQuery("select krs from Krs krs left join "
				+ "krs.pd pd left join krs.pemb pemb left join pemb.mk mk"+
		dbWhere+dbOrder);
		
		if(limit != -1 && limit>0) {
			query.setFirstResult(offset);
			if(offset < 0) offset = 0;
			query.setMaxResults(limit);
		}
		
		return query.list();
	}

	@Override
	public Krs getById(UUID idMKAlihjenjang) {
		List<Krs> queryResult = sessionFactory.getCurrentSession().createQuery("select krs from Krs krs WHERE "
				+ "krs.idKrs = '"+idMKAlihjenjang.toString()+"'").list();
		if(queryResult.size()==0) return null;
		else
		{
			return queryResult.get(0);
		}
	}
	
	@Override
	public void update(Krs krs) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(krs);
		tx.commit();
		session.flush();
		session.close();
	}
}
