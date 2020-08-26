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

import com.sia.modul.domain.*;

@Transactional
@Repository
public class CapPembRepositoryImpl implements CapPembRepository{

	@Autowired
	private SessionFactory sessionFactory;
	 
	@Override
	public List<CapPemb> findBySatMan(UUID idSatMan) { 
		return null;
	}

	@Override
	public long count(String where) {
		// TODO Auto-generated method stub
		String dbWhere ="";
		if(where != "") dbWhere = " WHERE " +where;
		Query query = sessionFactory.getCurrentSession().createQuery(
		        "select count(*) from CapPemb cp "
		        + "join cp.kurikulum kur "
		        + "join cp.satMan satman "  +dbWhere);
		Long count = (Long)query.uniqueResult();
		return count;
	}
 
	@SuppressWarnings("unchecked")
	@Override
	public List<CapPemb> get(String where, String order, int limit, int offset) {
		// TODO Auto-generated method stub
		String dbWhere ="";
		String dbOrder ="";
		if(where != "") dbWhere = " WHERE "+where;
		if(order != "") dbOrder = " ORDER BY "+order;
		 
		Query query = sessionFactory.getCurrentSession().createQuery("select cp from CapPemb cp " 
				+ "join cp.satMan satman "
				+ "join cp.kurikulum kur" +dbWhere+dbOrder);
		if(limit != -1 && limit>0) {
			query.setFirstResult(offset);
			if(offset < 0) offset = 0;
			query.setMaxResults(limit);
		}
		
		return query.list();
	}

	@Override
	public void update(CapPemb capPemb) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(capPemb);
		tx.commit();
		session.flush();
		session.close();
	}

	@Override
	public UUID insert(CapPemb capPemb) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		UUID insertId= (UUID)session.save(capPemb);
		tx.commit();
		session.flush();
		session.close();
		return insertId;
	}
 
	@SuppressWarnings("unchecked")
	@Override
	public CapPemb findById(UUID idCapPemb) {
		// TODO Auto-generated method stub
		List<CapPemb> queryResult = sessionFactory.getCurrentSession().createQuery("select cp from CapPemb cp WHERE  cp.idCapPemb='"+idCapPemb.toString()+"'").list();
		if(queryResult.size()==0) return null;
		return queryResult.get(0);
	} 
	@SuppressWarnings("unchecked")
	@Override
	public List<CapPemb> findAll() {
		// TODO Auto-generated method stub
		return sessionFactory.getCurrentSession().createQuery("select cp from CapPemb cp WHERE cp.statusCapPemb = false").list();

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CapPemb> findByKurikulum(UUID idKurikulum) {
		// TODO Auto-generated method stub
		List<CapPemb> queryResult = sessionFactory.getCurrentSession().createQuery("select cp from CapPemb cp "
				+ "WHERE  cp.kurikulum.idKurikulum='"+idKurikulum.toString()+"'").list();
		if(queryResult.size()==0) return null;
		return queryResult;
	}

	@Override
	public List<CapPemb> findByParent(UUID idCapPemb) {
		// TODO Auto-generated method stub
		List<CapPemb> queryResult = sessionFactory.getCurrentSession().createQuery("select scp.childCapPemb from SubCapPemb scp "
				+ "WHERE  scp.parentCapPemb.idCapPemb='"+idCapPemb.toString()+"'").list();
		if(queryResult.size()==0) return null;
		return queryResult;
	}

}
