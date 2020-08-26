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

import com.sia.modul.domain.EkuivalensiMKPekuivalensi;
import com.sia.modul.domain.MK;
import com.sia.modul.domain.MKLuar;

@Repository
@Transactional
public class EkuivalensiMKPekuivalensiRepositoryImpl implements EkuivalensiMKPekuivalensiRepository{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public List<EkuivalensiMKPekuivalensi> get(String where, String order, int limit, int offset) {
		String dbWhere ="";
		String dbOrder ="";
		if(where != "") dbWhere = " WHERE "+where;
		if(order != "") dbOrder = " ORDER BY "+order;
				
		Query query = sessionFactory.getCurrentSession().createQuery("select ekuivalensiMKPekuivalensi from EkuivalensiMKPekuivalensi "
				+ "ekuivalensiMKPekuivalensi left join ekuivalensiMKPekuivalensi.mk mk left join ekuivalensiMKPekuivalensi.mkLuar mkLuar"
				+ "left join ekuivalensiMKPekuivalensi.relasiMKPekuivalensi relasiMKPekuivalensi"+
		dbWhere+dbOrder);
		
		if(limit != -1 && limit>0) {
			query.setFirstResult(offset);
			if(offset < 0) offset = 0;
			query.setMaxResults(limit);
		}
		
		return query.list();
	}

	@Override
	public EkuivalensiMKPekuivalensi getById(UUID idEkuivalensiMKPekuivalensi) {
		List<EkuivalensiMKPekuivalensi> queryResult = sessionFactory.getCurrentSession().createQuery("select ekuivalensiMKPekuivalensi from "
				+ "EkuivalensiMKPekuivalensi ekuivalensiMKPekuivalensi WHERE ekuivalensiMKPekuivalensi.idEkuivalensiMKPekuivalensi = '"
				+ idEkuivalensiMKPekuivalensi.toString()+"'").list();
		if(queryResult.size()==0) return null;
		else
		{
			return queryResult.get(0);
		}
	}

	@Override
	public UUID insert(EkuivalensiMKPekuivalensi ekuivalensiMKPekuivalensi) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		UUID insertId= (UUID)session.save(ekuivalensiMKPekuivalensi);
		tx.commit();
		session.flush();
		session.close();
		return insertId;
	}

	@Override
	public void update(EkuivalensiMKPekuivalensi ekuivalensiMKPekuivalensi) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(ekuivalensiMKPekuivalensi);
		tx.commit();
		session.flush();
		session.close();
	}

	@Override
	public void delete(EkuivalensiMKPekuivalensi ekuivalensiMKPekuivalensi) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.delete(ekuivalensiMKPekuivalensi);
		tx.commit();
		session.flush();
		session.close();
	}

	@Override
	public List<MK> getMKDistinct(UUID idRelasiMKPekuivalensi) {
		List<MK> queryResult = sessionFactory.getCurrentSession().createQuery("select DISTINCT mk from EkuivalensiMKPekuivalensi "
				+ "ekuivalensiMKPekuivalensi left join ekuivalensiMKPekuivalensi.mk mk WHERE ekuivalensiMKPekuivalensi.relasiMKPekuivalensi = '"+idRelasiMKPekuivalensi.toString()+"'").list();
		return queryResult;
	}

	@Override
	public List<MKLuar> getMKLuarDistinct(UUID idRelasiMKPekuivalensi) {
		List<MKLuar> queryResult = (List<MKLuar>) sessionFactory.getCurrentSession().createQuery("select DISTINCT mkLuar from EkuivalensiMKPekuivalensi "
				+ "ekuivalensiMKPekuivalensi left join ekuivalensiMKPekuivalensi.mkLuar mkLuar WHERE ekuivalensiMKPekuivalensi.relasiMKPekuivalensi = '"+idRelasiMKPekuivalensi.toString()+"'").list();
		return queryResult;
	}

//	@Override
//	public long count(String where) {
//		String dbWhere ="";
//		if(where != "") dbWhere = " WHERE "+where;
//		Query query = sessionFactory.getCurrentSession().createQuery(
//		        "select count(*) from RelasiMKPekuivalensi relasiMKPekuivalensi left join relasiMKPekuivalensi.pedomanEkuivalensi pEkuivalensi"+dbWhere);
//		Long count = (Long)query.uniqueResult();
//		return count;
//	}

}
