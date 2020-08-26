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

import com.sia.modul.domain.EkuivalensiMK;
import com.sia.modul.domain.MK;
import com.sia.modul.domain.MKLuar;

@Repository
@Transactional
public class EkuivalensiMKRepositoryImpl implements EkuivalensiMKRepository{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public List<EkuivalensiMK> get(String where, String order, int limit, int offset) {
		String dbWhere ="";
		String dbOrder ="";
		if(where != "") dbWhere = " WHERE "+where;
		if(order != "") dbOrder = " ORDER BY "+order;
				
		Query query = sessionFactory.getCurrentSession().createQuery("select ekuivalensiMK from EkuivalensiMK ekuivalensiMK left join "
				+ "ekuivalensiMK.relasiEkuivalensi relasiEkuivalensi left join ekuivalensiMK.mkLama mkLama left join ekuivalensiMK.mkBaru mkBaru"+
		dbWhere+dbOrder);
		
		if(limit != -1 && limit>0) {
			query.setFirstResult(offset);
			if(offset < 0) offset = 0;
			query.setMaxResults(limit);
		}
		
		return query.list();
	}

	@Override
	public EkuivalensiMK getById(UUID idCalonPD) {
		List<EkuivalensiMK> queryResult = sessionFactory.getCurrentSession().createQuery("select ekuivalensiMK from EkuivalensiMK ekuivalensiMK WHERE "
				+ "ekuivalensiMK.idEkuivalensiMK = '"+idCalonPD.toString()+"'").list();
		if(queryResult.size()==0) return null;
		else
		{
			return queryResult.get(0);
		}
	}

	@Override
	public UUID insert(EkuivalensiMK ekuivalensiMK) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		UUID insertId= (UUID)session.save(ekuivalensiMK);
		tx.commit();
		session.flush();
		session.close();
		return insertId;
	}

	@Override
	public void update(EkuivalensiMK ekuivalensiMK) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(ekuivalensiMK);
		tx.commit();
		session.flush();
		session.close();
	}

	@Override
	public void delete(EkuivalensiMK ekuivalensiMK) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.delete(ekuivalensiMK);
		tx.commit();
		session.flush();
		session.close();
	}

	@Override
	public long count(String where) {
		String dbWhere ="";
		if(where != "") dbWhere = " WHERE "+where;
		Query query = sessionFactory.getCurrentSession().createQuery(
		        "select count(*) from EkuivalensiMK ekuivalensiMK left join ekuivalensiMK.relasiEkuivalensi relasiEkuivalensi"+dbWhere);
		Long count = (Long)query.uniqueResult();
		return count;
	}
	
	@Override
	public List<MK> getMKLamaDistinct(UUID idRelasiEkuivalensi) {
		List<MK> queryResult = (List<MK>) sessionFactory.getCurrentSession().createQuery("select DISTINCT mkLama from EkuivalensiMK "
				+ "ekuivalensiMK left join ekuivalensiMK.mkLama mkLama WHERE ekuivalensiMK.relasiEkuivalensi = '"+idRelasiEkuivalensi.toString()+"'").list();
		return queryResult;
	}
	
	public List<MK> getMKBaruDistinct(UUID idRelasiEkuivalensi) {
		List<MK> queryResult = (List<MK>) sessionFactory.getCurrentSession().createQuery("select DISTINCT mkBaru from EkuivalensiMK "
				+ "ekuivalensiMK left join ekuivalensiMK.mkBaru mkBaru WHERE ekuivalensiMK.relasiEkuivalensi = '"+idRelasiEkuivalensi.toString()+"'").list();
		return queryResult;
	}
	
	@Override
	public List<Object> getDistinctRelasiByMKLama(UUID idMKLama) {
		Query query = sessionFactory.getCurrentSession().createQuery("select distinct relasiEkuivalensi FROM EkuivalensiMK ekuivalensiMK"
				+ " left join ekuivalensiMK.relasiEkuivalensi relasiEkuivalensi left join ekuivalensiMK.mkLama mkLama WHERE mkLama.idMK = '"+idMKLama.toString()+"'");
		
		return query.list();
	}
	
	@Override
	public List<Object> getDistinctRelasiByMKBaru(UUID idMKBaru) {
		Query query = sessionFactory.getCurrentSession().createQuery("select distinct relasiEkuivalensi FROM EkuivalensiMK ekuivalensiMK"
				+ " left join ekuivalensiMK.relasiEkuivalensi relasiEkuivalensi left join ekuivalensiMK.mkBaru mkBaru WHERE mkBaru.idMK = '"+idMKBaru.toString()+"'");
		
		return query.list();
	}
}
