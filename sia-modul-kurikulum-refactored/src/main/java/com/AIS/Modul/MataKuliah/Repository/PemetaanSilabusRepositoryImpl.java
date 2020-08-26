package com.AIS.Modul.MataKuliah.Repository;

import java.util.List;
import java.util.UUID;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sia.modul.domain.PemetaanSilabus;

@Transactional
@Repository
public class PemetaanSilabusRepositoryImpl implements PemetaanSilabusRepository{
 
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void update(PemetaanSilabus ps) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(ps);
		tx.commit(); 
		session.flush();
		session.close();
	}

	@Override
	public UUID insert(PemetaanSilabus ps) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		UUID insertId= (UUID)session.save(ps);
		tx.commit();
		session.flush();
		session.close();
		return insertId;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PemetaanSilabus> findByDetailSilabus(String idDetailSilabus) {
		// TODO Auto-generated method stub
		List<PemetaanSilabus> queryResult = sessionFactory.getCurrentSession().createQuery("select ps from PemetaanSilabus ps "
				+ "where ps.detailSilabus.idDetailSilabus='"+ idDetailSilabus +"' and ps.statusPemetaanSilabus = false").list();
		if(queryResult.size()==0) return null;
		return queryResult;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public PemetaanSilabus findById(UUID idPemetaanSilabus) {
		// TODO Auto-generated method stub
		List<PemetaanSilabus> queryResult = sessionFactory.getCurrentSession().createQuery("from PemetaanSilabus WHERE idPemetaanSilabus='"+idPemetaanSilabus.toString()+"'").list();
		if(queryResult.size()==0) return null;
		return queryResult.get(0);
	}

	@Override
	public List<PemetaanSilabus> findByMateriSilabus(UUID idMateriSilabus) {
		// TODO Auto-generated method stub
		return null;
	}
 

}
