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

import com.sia.modul.domain.BentukPenilaian;

@Transactional
@Repository
public class BentukPenilaianRepositoryImpl implements BentukPenilaianRepository {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public long count(String where) {
		// TODO Auto-generated method stub
		String dbWhere ="";
		if(where != "") dbWhere = " WHERE "+where;
		Query query = sessionFactory.getCurrentSession().createQuery(
		        "select count(*) from BentukPenilaian "+dbWhere);
		Long count = (Long)query.uniqueResult();
		return count;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BentukPenilaian> get(String where, String order, int limit,
			int offset) {
		// TODO Auto-generated method stub
		String dbWhere ="";
		String dbOrder ="";
		if(where != "") dbWhere = " WHERE "+where;
		if(order != "") dbOrder = " ORDER BY "+order;
		 
		Query query = sessionFactory.getCurrentSession().createQuery("from BentukPenilaian "+dbWhere+dbOrder);
		
		if(limit != -1 && limit>0) {
			query.setFirstResult(offset);
			if(offset < 0) offset = 0;
			query.setMaxResults(limit);
		}
		
		return query.list();
	}

	@Override
	public void update(BentukPenilaian bentukPenilaian) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(bentukPenilaian);
		tx.commit();
		session.flush();
		session.close();
	}

	@Override
	public UUID insert(BentukPenilaian bentukPenilaian) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		UUID insertId= (UUID)session.save(bentukPenilaian);
		tx.commit();
		session.flush();
		session.close();
		return insertId;
	}

	@SuppressWarnings("unchecked")
	@Override
	public BentukPenilaian findById(UUID idBentuk) {
		// TODO Auto-generated method stub
		List<BentukPenilaian> queryResult = sessionFactory.getCurrentSession().createQuery("from BentukPenilaian "
				+ "WHERE idBentuk='"+idBentuk.toString()+"'").list();
		if(queryResult.size()==0) return null;
		return queryResult.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BentukPenilaian> findAll() {
		// TODO Auto-generated method stub
		List<BentukPenilaian> queryResult = sessionFactory.getCurrentSession().createQuery("from BentukPenilaian "
				+ "WHERE statusBentuk=false order by namaBentuk").list();
		if(queryResult.size()==0) return null;
		return queryResult;
	}

}
