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

import com.sia.modul.domain.MKLuar;
import com.sia.modul.domain.RelasiMKPekuivalensi;

@Repository
@Transactional
public class RelasiMKPekuivalensiRepositoryImpl implements RelasiMKPekuivalensiRepository{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public List<RelasiMKPekuivalensi> get(String where, String order, int limit, int offset) {
		String dbWhere ="";
		String dbOrder ="";
		if(where != "") dbWhere = " WHERE "+where;
		if(order != "") dbOrder = " ORDER BY "+order;
				
		Query query = sessionFactory.getCurrentSession().createQuery("select relasiMKPekuivalensi from RelasiMKPekuivalensi "
				+ "relasiMKPekuivalensi left join relasiMKPekuivalensi.pedomanEkuivalensi pEkuivalensi"+
		dbWhere+dbOrder);
		
		if(limit != -1 && limit>0) {
			query.setFirstResult(offset);
			if(offset < 0) offset = 0;
			query.setMaxResults(limit);
		}
		
		return query.list();
	}

	@Override
	public RelasiMKPekuivalensi getById(UUID idRelasiMKPekuivalensi) {
		List<RelasiMKPekuivalensi> queryResult = sessionFactory.getCurrentSession().createQuery("select relasiMKPekuivalensi from "
				+ "RelasiMKPekuivalensi relasiMKPekuivalensi WHERE relasiMKPekuivalensi.idRelasiMKPekuivalensi = '"
				+idRelasiMKPekuivalensi.toString()+"'").list();
		if(queryResult.size()==0) return null;
		else
		{
			return queryResult.get(0);
		}
	}

	@Override
	public UUID insert(RelasiMKPekuivalensi relasiMKPekuivalensi) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		UUID insertId= (UUID)session.save(relasiMKPekuivalensi);
		tx.commit();
		session.flush();
		session.close();
		return insertId;
	}

	@Override
	public void update(RelasiMKPekuivalensi relasiMKPekuivalensi) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(relasiMKPekuivalensi);
		tx.commit();
		session.flush();
		session.close();
	}

	@Override
	public void delete(RelasiMKPekuivalensi relasiMKPekuivalensi) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.delete(relasiMKPekuivalensi);
		tx.commit();
		session.flush();
		session.close();
	}

	@Override
	public long count(String where) {
		String dbWhere ="";
		if(where != "") dbWhere = " WHERE "+where;
		Query query = sessionFactory.getCurrentSession().createQuery(
		        "select count(*) from RelasiMKPekuivalensi relasiMKPekuivalensi left join relasiMKPekuivalensi.pedomanEkuivalensi pEkuivalensi"+dbWhere);
		Long count = (Long)query.uniqueResult();
		return count;
	}

}
