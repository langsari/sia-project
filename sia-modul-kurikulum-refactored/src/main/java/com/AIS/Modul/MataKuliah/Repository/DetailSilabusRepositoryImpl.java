package com.AIS.Modul.MataKuliah.Repository;

import java.util.List;
import java.util.UUID;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sia.modul.domain.DetailSilabus;

@Transactional
@Repository
public class DetailSilabusRepositoryImpl implements DetailSilabusRepository{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public UUID insert(DetailSilabus detailSilabus) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		UUID insertId= (UUID)session.save(detailSilabus);
		tx.commit();
		System.out.println("Data detail tersimpan");
		session.flush();
		session.close();
		return insertId;
	}

	@SuppressWarnings("unchecked")
	@Override
	public DetailSilabus findById(UUID idDetailSilabus) {
		// TODO Auto-generated method stub
		List<DetailSilabus> queryResult = sessionFactory.getCurrentSession().createQuery("select ds from DetailSilabus ds " 
				+ "where ds.idDetailSilabus = '"+idDetailSilabus.toString()+"'").list();
		if(queryResult.size()==0) return null;
		return queryResult.get(0);
	}

	@Override
	public void update(DetailSilabus detailSilabus) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(detailSilabus);
		tx.commit();
		session.flush();
		session.close();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DetailSilabus> findBySilabus(UUID idSilabus) {
		// TODO Auto-generated method stub
		List<DetailSilabus> queryResult = sessionFactory.getCurrentSession().createQuery("select ds from DetailSilabus ds " 
				+ "join ds.silabus silabus "
				+ "where ds.statusDetailSilabus = false and silabus.idSilabus = '"+idSilabus.toString()+"'").list();
		if(queryResult.size()==0) return null;
		return queryResult;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DetailSilabus> findAll() {
		// TODO Auto-generated method stub
		List<DetailSilabus> queryResult = sessionFactory.getCurrentSession().createQuery("select ds from DetailSilabus ds " 
				+ "join ds.silabus silabus "
				+ "where ds.statusDetailSilabus = false").list();
		if(queryResult.size()==0) return null;
		return queryResult;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DetailSilabus> findByMK(UUID idMK) {
		// TODO Auto-generated method stub
		List<DetailSilabus> queryResult = sessionFactory.getCurrentSession().createQuery("select ds from DetailSilabus ds " 
				+ "join ds.silabus silabus "
				+ "join silabus.mk mk "
				+ "where mk.idMK = '"+ idMK.toString() +"' AND ds.statusDetailSilabus = false").list();
		if(queryResult.size()==0) return null;
		return queryResult;
	}

}
