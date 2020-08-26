package com.AIS.Modul.MataKuliah.Repository;

import java.util.List;
import java.util.UUID;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sia.modul.domain.RPMetodePemb;

@Transactional
@Repository
public class RPMetodePembRepositoyImpl implements RPMetodePembRepository {

	@Autowired
	private SessionFactory sessionFactory;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<RPMetodePemb> findByRPPerTemu(UUID idRPPerTemu) {
		 
		// TODO Auto-generated method stub
		List<RPMetodePemb> queryResult = sessionFactory.getCurrentSession().createQuery("select rpmb from RPMetodePemb rpmb "
				+ "WHERE rpmb.rpPerTemu.idRPPerTemu='"+idRPPerTemu.toString()+"'").list();
		if(queryResult.size()==0) return null;
		return queryResult;
	}

	@Override
	public UUID insert(RPMetodePemb mp) {
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
	public RPMetodePemb findById(UUID idRPMetodePemb) {
		// TODO Auto-generated method stub
		List<RPMetodePemb> queryResult = sessionFactory.getCurrentSession().createQuery(""
				+ "select rpmb from RPMetodePemb rpmb "
				+ "WHERE rpmb.idRPMetodePemb='"+idRPMetodePemb.toString()+"'").list();
		if(queryResult.size()==0) return null;
		return queryResult.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RPMetodePemb> findAll() {
		// TODO Auto-generated method stub
		List<RPMetodePemb> queryResult = sessionFactory.getCurrentSession().createQuery("from RPMetodePemb").list();
		if(queryResult.size()==0) return null;
		return queryResult;
	}

}
