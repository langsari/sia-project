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

import com.sia.modul.domain.EkuivalensiMK;

@Transactional
@Repository
public class EkuivalensiMKRepositoryImpl implements EkuivalensiMKRepository {
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public long count(String where) {
		// TODO Auto-generated method stub
		String dbWhere ="";
		if(where != "") dbWhere = " WHERE "+where;
		Query query = sessionFactory.getCurrentSession().createQuery(
		        "select count(*) from EkuivalensiMK ekMK join ekMK.parentMK parent join ekMK.childMK child "+dbWhere);
		Long count = (Long)query.uniqueResult();
		return count;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EkuivalensiMK> get(String where, String order, int limit,
			int offset) {
		// TODO Auto-generated method stub
		String dbWhere ="";
		String dbOrder ="";
		if(where != "") dbWhere = " WHERE "+where;
		if(order != "") dbOrder = " ORDER BY "+order;
		 
		Query query = sessionFactory.getCurrentSession().createQuery("select ekMK from EkuivalensiMK ekMK join ekMK.parentMK parent join ekMK.childMK child"+dbWhere+dbOrder);
		
		if(limit != -1 && limit>0) {
			query.setFirstResult(offset);
			if(offset < 0) offset = 0;
			query.setMaxResults(limit);
		}
		
		return query.list();
	}

	@Override
	public void update(EkuivalensiMK ekuivalensiMK) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(ekuivalensiMK);
		tx.commit();
		session.flush();
		session.close();
	}

	@Override
	public UUID insert(EkuivalensiMK ekuivalensiMK) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		UUID insertId= (UUID)session.save(ekuivalensiMK);
		tx.commit();
		session.flush();
		session.close();
		return insertId;
	}

	@Override
	@SuppressWarnings("unchecked")
	public EkuivalensiMK findById(UUID idEkuivalensiMK) {
		// TODO Auto-generated method stub
		List<EkuivalensiMK> queryResult = sessionFactory.getCurrentSession().createQuery("select ekMK from EkuivalensiMK ekMK join ekMK.parentMK parent join ekMK.childMK child where ekMK.idEkuivalensiMK='"+idEkuivalensiMK.toString()+"'").list();
		if(queryResult.size()==0) return null;
		return queryResult.get(0);
	}

	@Override
	public List<EkuivalensiMK> findAll() {
		// TODO Auto-generated method stub
		return null;
	} 
	
}
