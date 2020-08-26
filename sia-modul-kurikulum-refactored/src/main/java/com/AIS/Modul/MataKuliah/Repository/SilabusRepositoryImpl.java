package com.AIS.Modul.MataKuliah.Repository;

import java.util.List;
import java.util.UUID;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sia.modul.domain.Silabus;

@Transactional
@Repository
public class SilabusRepositoryImpl implements SilabusRepository {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void update(Silabus silabus) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(silabus);
		tx.commit();
		session.flush();
		session.close();
	}

	@Override
	public UUID insert(Silabus silabus) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		UUID insertId= (UUID)session.save(silabus);
		tx.commit();
		session.flush();
		session.close();
		return insertId;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Silabus findByMK(UUID idMK) {
		// TODO Auto-generated method stub
		List<Silabus> queryResult = sessionFactory.getCurrentSession().createQuery("select silabus from Silabus silabus "
				+ "join silabus.mk mk "
				+ "where mk.idMK='"+idMK.toString()+"'").list();
		if(queryResult.size()==0) return null;
		return queryResult.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Silabus findById(UUID idSilabus) {
		// TODO Auto-generated method stub
		List<Silabus> queryResult = sessionFactory.getCurrentSession().createQuery("select silabus from Silabus silabus "
				+ "join silabus.mk mk where silabus.idSilabus ='"+ idSilabus.toString() +"'").list();
		if(queryResult.size()==0) return null;
		return queryResult.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Silabus> findAll() {
		// TODO Auto-generated method stub
		List<Silabus> queryResult = sessionFactory.getCurrentSession().createQuery("from Silabus").list();
		if(queryResult.size()==0) return null;
		return queryResult;
	}

}
