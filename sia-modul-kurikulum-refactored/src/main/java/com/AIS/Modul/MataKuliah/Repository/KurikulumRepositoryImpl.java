package com.AIS.Modul.MataKuliah.Repository;

import java.util.List;
import java.util.UUID;

import com.sia.modul.domain.*;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class KurikulumRepositoryImpl implements KurikulumRepository {

	@Autowired
	private SessionFactory sessionFactory;
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Kurikulum> findAll() {
		 return sessionFactory.getCurrentSession().createQuery("from Kurikulum k where k.statusKurikulum = false order by k.thnMulai asc").list();
	}
	
	@Override
	@Transactional
	public Kurikulum findById(UUID idKurikulum) {
		//return (Kurikulum) sessionFactory.getCurrentSession().get(Kurikulum.class, idKurikulum);
		List<Kurikulum> queryResult = sessionFactory.getCurrentSession().createQuery("select k from Kurikulum k join k.satMan satMan WHERE k.idKurikulum='"+idKurikulum.toString()+"'").list();
		if(queryResult.size()==0) return null;
		return queryResult.get(0);
	}

	@Override
	@Transactional
	public void addKurikulum(Kurikulum kurikulum) {
		sessionFactory.getCurrentSession().save(kurikulum);
		
	}

	@Override
	@Transactional
	public void editKurikulum(Kurikulum kurikulum, UUID idKurikulum) {
		 
	}
	@Override
	public long count(String where) {
		// TODO Auto-generated method stub
		String dbWhere ="";
		if(where != "") dbWhere = " WHERE "+where;
		Query query = sessionFactory.getCurrentSession().createQuery(
		        "select count(*) from Kurikulum k join k.satMan satMan"+dbWhere);
		Long count = (Long)query.uniqueResult();
		return count;
	}
	
	@Override
	public List<Kurikulum> get(String where, String order, int limit, int offset) {
		String dbWhere ="";
		String dbOrder ="";
		if(where != "") dbWhere = " WHERE "+where;
		if(order != "") dbOrder = " ORDER BY "+order;
		 
		Query query = sessionFactory.getCurrentSession().createQuery("select k from Kurikulum k join k.satMan satMan "+dbWhere+dbOrder);
		
		if(limit != -1 && limit>0) {
			query.setFirstResult(offset);
			if(offset < 0) offset = 0;
			query.setMaxResults(limit);
		}
		
		return query.list();
	}

	@Override
	public void update(Kurikulum kurikulum) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(kurikulum);
		tx.commit();
		session.flush();
		session.close();
	}

	@Override
	public UUID insert(Kurikulum kurikulum) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		UUID insertId= (UUID)session.save(kurikulum);
		tx.commit();
		session.flush();
		session.close();
		return insertId;
	}
	
}
