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

import com.sia.modul.domain.MetodePemb;

@Transactional
@Repository
public class MetodePembRepositoryImpl implements MetodePembRepository {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public long count(String string) {
		// TODO Auto-generated method stub
		String dbWhere ="";
		if(string != "") dbWhere = " WHERE "+string;
		Query query = sessionFactory.getCurrentSession().createQuery(
		        "select count(*) from MetodePemb "+dbWhere);
		Long count = (Long)query.uniqueResult();
		return count;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MetodePemb> get(String where, String order, int limit,
			int offset) {
		// TODO Auto-generated method stub
		String dbWhere ="";
		String dbOrder ="";
		if(where != "") dbWhere = " WHERE "+where;
		if(order != "") dbOrder = " ORDER BY "+order;
		 
		Query query = sessionFactory.getCurrentSession().createQuery("from MetodePemb "+dbWhere+dbOrder);
		
		if(limit != -1 && limit>0) {
			query.setFirstResult(offset);
			if(offset < 0) offset = 0;
			query.setMaxResults(limit);
		}
		
		return query.list();
	}

	@Override
	public void update(MetodePemb metodePemb) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(metodePemb);
		tx.commit();
		session.flush();
		session.close();
	}

	@Override
	public UUID insert(MetodePemb metodePemb) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		UUID insertId= (UUID)session.save(metodePemb);
		tx.commit();
		session.flush();
		session.close();
		return insertId;
	}

	@SuppressWarnings("unchecked")
	@Override
	public MetodePemb findById(UUID idMetodePemb) {
		// TODO Auto-generated method stub
		List<MetodePemb> queryResult = sessionFactory.getCurrentSession().createQuery("from MetodePemb "
				+ "WHERE idMetodePemb='"+idMetodePemb.toString()+"'").list();
		if(queryResult.size()==0) return null;
		return queryResult.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MetodePemb> findAll() {
		// TODO Auto-generated method stub
		return sessionFactory.getCurrentSession().createQuery("from MetodePemb where statusMetodePemb=false order by namaMetodePemb").list();
	}
}
