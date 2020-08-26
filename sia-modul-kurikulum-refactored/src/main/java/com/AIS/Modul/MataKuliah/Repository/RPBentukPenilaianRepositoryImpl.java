package com.AIS.Modul.MataKuliah.Repository;

import java.util.List;
import java.util.UUID;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sia.modul.domain.RPBentukPenilaian; 

@Transactional
@Repository
public class RPBentukPenilaianRepositoryImpl implements RPBentukPenilaianRepository{

	@Autowired
	private SessionFactory sessionFactory;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<RPBentukPenilaian> findByRPPerTemu(UUID idRPPerTemu) {
		// TODO Auto-generated method stub
		List<RPBentukPenilaian> queryResult = sessionFactory.getCurrentSession().createQuery("select rpbp from RPBentukPenilaian rpbp "
				+ "WHERE rpbp.rpPerTemu.idRPPerTemu='"+idRPPerTemu.toString()+"'").list();
		if(queryResult.size()==0) return null;
		return queryResult;
	}

	@Override
	public UUID insert(RPBentukPenilaian mp) {
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
	public List<RPBentukPenilaian> findAll() {
		// TODO Auto-generated method stub
		List<RPBentukPenilaian> queryResult = sessionFactory.getCurrentSession().createQuery("from RPBentukPenilaian").list();
		if(queryResult.size()==0) return null;
		return queryResult;
	}

}
