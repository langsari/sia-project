package com.AIS.Modul.MataKuliah.Repository;

import java.util.List;
import java.util.UUID;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.hibernate.Transaction;

import com.sia.modul.domain.RP;

@Transactional
@Repository
public class RPRepositoryImpl implements RPRepository{

	@Autowired
	private SessionFactory sessionFactory;
	
	@SuppressWarnings("unchecked")
	@Override
	public RP findRP(String idMK) {
		// TODO Auto-generated method stub
		List<RP> queryResult = sessionFactory.getCurrentSession().createQuery("select rp from RP rp "
				+ "join rp.mk mk "
				+ "where mk.idMK = '"+idMK+"'").list();
		if(queryResult.size()==0) return null;
		return queryResult.get(0);
	}
 

	@SuppressWarnings("unchecked")
	@Override
	public RP findBySilabus(UUID idSilabus) {
		// TODO Auto-generated method stub
		List<RP> queryResult = sessionFactory.getCurrentSession().createQuery("select rp from RP rp "
				+ "join rp.silabus silabus "
				+ "where silabus.idSilabus = '"+idSilabus.toString()+"'").list();
		if(queryResult.size()==0) return null;
		return queryResult.get(0);
	}

	@Override
	public UUID insert(RP rp) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		UUID insertId= (UUID)session.save(rp);
		tx.commit();
		session.flush();
		session.close();
		return insertId;
	}


	@Override
	public void update(RP rpNew) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(rpNew);
		tx.commit();
		session.flush();
		session.close();
	}


	@SuppressWarnings("unchecked")
	@Override
	public RP findById(UUID idRP) {
		// TODO Auto-generated method stub
		List<RP> queryResult = sessionFactory.getCurrentSession().createQuery("from RP "
				+ "where idRP= '"+idRP.toString()+"'").list();
		if(queryResult.size()==0) return null;
		return queryResult.get(0);
	}

}
