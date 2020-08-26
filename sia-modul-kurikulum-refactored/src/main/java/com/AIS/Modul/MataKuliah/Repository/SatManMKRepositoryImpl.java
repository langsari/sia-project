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

import com.sia.modul.domain.SatManMK;

@Transactional
@Repository
public class SatManMKRepositoryImpl implements SatManMKRepository {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void update(SatManMK satManMK) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(satManMK);
		tx.commit();
		session.flush();
		session.close();
	}

	@Override
	public UUID insert(SatManMK satManMK) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		UUID insertId= (UUID)session.save(satManMK);
		tx.commit();
		session.flush();
		session.close();
		return insertId;
	}

	@SuppressWarnings("unchecked")
	@Override
	public SatManMK findById(UUID idSatManMK) {
		// TODO Auto-generated method stub
		List<SatManMK> queryResult = sessionFactory.getCurrentSession().createQuery(
				"select sMMK from SatManMK sMMK join sMMK.mk mk "  
		        + "join sMMK.satMan satman where sMMK.idSatManMK='"+idSatManMK.toString()+"'").list();
		if(queryResult.size()==0) return null;
		return queryResult.get(0);
	}

	@Override
	public long count(String where) {
		// TODO Auto-generated method stub
		String dbWhere ="";
		if(where != "") dbWhere = " WHERE "+where;
		Query query = sessionFactory.getCurrentSession().createQuery(
		        "select count(sMMK) from SatManMK sMMK join sMMK.mk mk "  
		        + "join sMMK.satMan satman "+dbWhere);
		Long count = (Long)query.uniqueResult();
		return count;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SatManMK> get(String where, String order, int limit, int offset) {
		// TODO Auto-generated method stub
		String dbWhere ="";
		String dbOrder ="";
		if(where != "") dbWhere = " WHERE "+where;
		if(order != "") dbOrder = " ORDER BY "+order;
		 
		Query query = sessionFactory.getCurrentSession().createQuery(
				"select sMMK from SatManMK sMMK join sMMK.mk mk "  
				        + "join sMMK.satMan satman" +dbWhere+dbOrder);
		
		if(limit != -1 && limit>0) {
			query.setFirstResult(offset);
			if(offset < 0) offset = 0;
			query.setMaxResults(limit);
		}
		
		return query.list();
	}
 
	@Override
	public List<SatManMK> findByMK(UUID idMK) {
		// TODO Auto-generated method stub
		List<SatManMK> queryResult = sessionFactory.getCurrentSession().createQuery(
				"select sMMK from SatManMK sMMK join sMMK.satMan sm join sMMK.mk mk where mk.idMK='"+idMK.toString()+"'").list();
		if(queryResult.size()==0) return null;
		return queryResult;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SatManMK> findAll() {
		// TODO Auto-generated method stub
		List<SatManMK> queryResult = sessionFactory.getCurrentSession().createQuery(
				"from SatManMK where statusSatManMK=false").list();
		if(queryResult.size()==0) return null;
		return queryResult;
	}

}
