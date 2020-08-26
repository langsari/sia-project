package com.AIS.Modul.MataKuliah.Repository;

import java.util.List;
import java.util.UUID;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sia.modul.domain.MateriSilabus;

@Transactional
@Repository
public class MateriSilabusRepositoryImpl implements MateriSilabusRepository{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void update(MateriSilabus mp) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(mp);
		tx.commit();
		session.flush();
		session.close();
	}

	@Override
	public UUID insert(MateriSilabus mp) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		UUID insertId= (UUID)session.save(mp);
		tx.commit();
		session.flush();
		session.close();
		return insertId;
	}

	@SuppressWarnings("unchecked")
	@Override
	public MateriSilabus findById(UUID idMateriSilabus) {
		// TODO Auto-generated method stub
		List<MateriSilabus> queryResult = sessionFactory.getCurrentSession().createQuery("from MateriSilabus "
				+ "WHERE idMateriSilabus='"+idMateriSilabus.toString()+"'").list();
		if(queryResult.size()==0) return null;
		return queryResult.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MateriSilabus> findByRPPerTemu(UUID idRPPerTemu) {
		// TODO Auto-generated method stub
		List<MateriSilabus> queryResult = sessionFactory.getCurrentSession().createQuery("select ms "
				+ "from MateriSilabus ms "
				+ "WHERE ms.rpPerTemu.idRPPerTemu='"+idRPPerTemu.toString()+"' AND ms.statusMateriSilabus=false").list();
		if(queryResult.size()==0) return null;
		return queryResult;
	}

}
