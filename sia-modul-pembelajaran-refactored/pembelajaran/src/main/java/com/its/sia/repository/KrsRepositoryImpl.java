package com.its.sia.repository;

import java.util.List;
import java.util.UUID;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sia.modul.domain.AnggotaRombel;
import com.sia.modul.domain.Krs;
import com.sia.modul.domain.Pd;
import com.sia.modul.domain.Rombel;

@Repository
@Transactional
public class KrsRepositoryImpl implements KrsRepository {
		
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public List<Krs> get() {
		return get("");
	}

	@Override
	public List<Krs> get(String where) {
		return get(where,"");
	}

	@Override
	public List<Krs> get(String where, String order) {
		return get(where,order,-1,-1);
	}
	
	@Override
	public List<Krs> get(String where, String order, int limit, int offset) {
		String dbWhere ="";
		String dbOrder ="";
		if(where != "") dbWhere = " WHERE "+where;
		if(order != "") dbOrder = " ORDER BY "+order;
		
		Query query = sessionFactory.getCurrentSession().createQuery("select krs from Krs "
				+ "krs join krs.pd pd left join pd.ptk ptk join krs.pemb pemb join pemb.tglSmt tglSmt "
				+ "join pemb.mk mk "
				+dbWhere+dbOrder);
		
		if(limit != -1 && limit>0) {
			query.setFirstResult(offset);
			if(offset < 0) offset = 0;
			query.setMaxResults(limit);
		}
		
		return query.list();
	}

	@Override
	public Krs getById(UUID idKrs) {
		List<Krs> queryResult = sessionFactory.getCurrentSession().createQuery("select krs from Krs "
				+ "krs join krs.pd pd left join pd.ptk ptk join krs.pemb pemb join pemb.tglSmt tglSmt "
				+ "join pemb.mk mk "
				+ "WHERE krs.idKrs = '"+idKrs.toString()+"'").list();
		if(queryResult.size()==0) return null;
		return queryResult.get(0);
	}

	@Override
	public UUID insert(Krs krs) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		UUID insertId= (UUID)session.save(krs);
		tx.commit();
		session.flush();
		session.close();
		return insertId;
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

	@Override
	public void delete(Krs krs) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.delete(krs);
		tx.commit();
		session.flush();
		session.close();
	}

	@Override
	public long count(String where) {
		String dbWhere ="";
		if(where != "") dbWhere = " WHERE "+where;
		Query query = sessionFactory.getCurrentSession().createQuery(
				"select count(*) from Krs "
				+ "krs join krs.pd pd left join pd.ptk ptk join krs.pemb pemb join pemb.tglSmt tglSmt "
				+ "join pemb.mk mk "
				+ dbWhere);
		Long count = (Long)query.uniqueResult();
		return count;
	}

	@Override
	public List<Pd> getPeserta(String where, String order, int limit, int offset) {
		String dbWhere ="";
		String dbOrder ="";
		if(where != "") dbWhere = " WHERE "+where;
		if(order != "") dbOrder = " ORDER BY "+order;
		
		Query query = sessionFactory.getCurrentSession().createQuery("select distinct pd from Krs "
				+ "krs join krs.pd pd left join pd.ptk ptk join krs.pemb pemb join pemb.tglSmt tglSmt "
				+ "join pemb.mk mk "
				+dbWhere+dbOrder);
		
		if(limit != -1 && limit>0) {
			query.setFirstResult(offset);
			if(offset < 0) offset = 0;
			query.setMaxResults(limit);
		}
		
		return query.list();
	}

	@Override
	public long countPeserta(String where) {
		String dbWhere ="";
		if(where != "") dbWhere = " WHERE "+where;
		Query query = sessionFactory.getCurrentSession().createQuery(
				"select count( distinct pd) from Krs "
				+ "krs join krs.pd pd left join pd.ptk ptk join krs.pemb pemb join pemb.tglSmt tglSmt "
				+ "join pemb.mk mk "
				+ dbWhere);
		Long count = (Long)query.uniqueResult();
		return count;
	}

}
