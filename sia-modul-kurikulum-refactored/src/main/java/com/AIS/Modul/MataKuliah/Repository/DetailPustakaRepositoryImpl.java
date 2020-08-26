package com.AIS.Modul.MataKuliah.Repository;

import java.util.List;
import java.util.UUID;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sia.modul.domain.DetailPustaka;

@Transactional
@Repository
public class DetailPustakaRepositoryImpl implements DetailPustakaRepository {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void update(DetailPustaka dp) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(dp);
		tx.commit(); 
		session.flush();
		session.close();
	}

	@Override
	public UUID insert(DetailPustaka dp) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		UUID insertId= (UUID)session.save(dp);
		tx.commit();
		session.flush();
		session.close();
		return insertId;
	}

	@SuppressWarnings("unchecked")
	@Override
	public DetailPustaka findById(UUID idDetailPustaka) {
		// TODO Auto-generated method stub
		List<DetailPustaka> queryResult = sessionFactory.getCurrentSession().createQuery("from DetailPustaka WHERE idDetailPustaka='"+idDetailPustaka.toString()+"'").list();
		if(queryResult.size()==0) return null;
		return queryResult.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DetailPustaka> findBySilabus(UUID idSilabus) {
		// TODO Auto-generated method stub
		List<DetailPustaka> queryResult = sessionFactory.getCurrentSession().createQuery("select dp from DetailPustaka dp "
				+ "WHERE dp.silabus.idSilabus='"+idSilabus.toString()+"'").list();
		if(queryResult.size()==0) return null;
		return queryResult;
	}

}
