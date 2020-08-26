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

import com.sia.modul.domain.Pemb;
import com.sia.modul.domain.PendidikPengajar;

@Repository
@Transactional
public class PendidikPengajarRepositoryImpl implements PendidikPengajarRepository {
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<PendidikPengajar> get(String where, String order, int limit, int offset) {
		String dbWhere ="";
		String dbOrder ="";
		if(where != "") dbWhere = " WHERE "+where;
		if(order != "") dbOrder = " ORDER BY "+order;
		
		Query query = sessionFactory.getCurrentSession().createQuery("select pendidikPengajar from PendidikPengajar "
				+ "pendidikPengajar join pendidikPengajar.ptk ptk join pendidikPengajar.pemb pemb"
				+dbWhere+dbOrder);
		
		if(limit != -1 && limit>0) {
			query.setFirstResult(offset);
			if(offset < 0) offset = 0;
			query.setMaxResults(limit);
		}
		
		return query.list();
	}

	@Override
	public PendidikPengajar getById(UUID idAnggotaRombel) {
		List<PendidikPengajar> queryResult = sessionFactory.getCurrentSession().createQuery("select pendidikPengajar from PendidikPengajar "
				+ "pendidikPengajar join pendidikPengajar.ptk ptk join pendidikPengajar.pemb pemb "
				+ "WHERE pendidikPengajar.idPendidikPengajar = '"+idAnggotaRombel.toString()+"'").list();
		if(queryResult.size()==0) return null;
		return queryResult.get(0);
	}

	@Override
	public UUID insert(PendidikPengajar pendidikPengajar) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		UUID insertId= (UUID)session.save(pendidikPengajar);
		tx.commit();
		session.flush();
		session.close();
		return insertId;
	}

	@Override
	public void update(PendidikPengajar pendidikPengajar) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(pendidikPengajar);
		tx.commit();
		session.flush();
		session.close();
	}

	@Override
	public void delete(PendidikPengajar pendidikPengajar) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.delete(pendidikPengajar);
		tx.commit();
		session.flush();
		session.close();
	}
	
	@Override
	public long count(String where) {
		String dbWhere ="";
		if(where != "") dbWhere = " WHERE "+where;
		Query query = sessionFactory.getCurrentSession().createQuery(
				"select count(*) from PendidikPengajar "
				+ "pendidikPengajar join pendidikPengajar.ptk ptk join pendidikPengajar.pemb pemb "
				+ dbWhere);
		Long count = (Long)query.uniqueResult();
		return count;
	}
	
	@Override
	public long countPemb(String where) {
		String dbWhere ="";
		if(where != "") dbWhere = " WHERE "+where;
		Query query = sessionFactory.getCurrentSession().createQuery(
				"select count(*) from PendidikPengajar "
				+ "pendidikPengajar join pendidikPengajar.ptk ptk join pendidikPengajar.pemb pemb join pemb.mk mk join pemb.tglSmt tglSmt "
				+ "join tglSmt.thnAjaran thnAjaran join tglSmt.smt smt "
				+ dbWhere);
		Long count = (Long)query.uniqueResult();
		return count;
	}

	@Override
	public List<Pemb> getPemb(String where, String order, int limit, int offset) {
		String dbWhere ="";
		String dbOrder ="";
		if(where != "") dbWhere = " WHERE "+where;
		if(order != "") dbOrder = " ORDER BY "+order;
		
		Query query = sessionFactory.getCurrentSession().createQuery("select pemb from PendidikPengajar "
				+ "pendidikPengajar join pendidikPengajar.ptk ptk join pendidikPengajar.pemb pemb join pemb.mk mk join pemb.tglSmt tglSmt "
				+ "join tglSmt.thnAjaran thnAjaran join tglSmt.smt smt "
				+dbWhere+dbOrder);
		
		if(limit != -1 && limit>0) {
			query.setFirstResult(offset);
			if(offset < 0) offset = 0;
			query.setMaxResults(limit);
		}
		
		return query.list();
	}

}
