package com.bustan.siakad.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sia.modul.domain.Ptk;
import com.sia.modul.domain.SatMan;

@Repository
@Transactional
public class SatManRepositoryImpl implements SatManRepository {
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<SatMan> get(String where, String order, int limit, int offset) {
		String dbWhere ="";
		String dbOrder ="";
		if(where != "") dbWhere = " WHERE "+where;
		if(order != "") dbOrder = " ORDER BY "+order;
		
		Query query = sessionFactory.getCurrentSession().createQuery("from SatMan satMan"+dbWhere+dbOrder);
		
		if(limit != -1 && limit>0) {
			query.setFirstResult(offset);
			if(offset < 0) offset = 0;
			query.setMaxResults(limit);
		}
		
		return query.list();
	}

	@Override
	public SatMan getById(UUID idSatMan) {
		List<SatMan> queryResult = sessionFactory.getCurrentSession().createQuery("from SatMan WHERE idSatMan = '"+idSatMan.toString()+"'").list();
		if(queryResult.size()==0) return null;
		return queryResult.get(0);
	}

	@Override
	public UUID insert(SatMan satMan) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		UUID insertId= (UUID)session.save(satMan);
		tx.commit();
		return insertId;
	}

	@Override
	public void update(SatMan satMan) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(satMan);
		tx.commit();
	}

	@Override
	public void delete(UUID idSatMan) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public long count(String where) {
		String dbWhere ="";
		if(where != "") dbWhere = " WHERE "+where;
		Query query = sessionFactory.getCurrentSession().createQuery(
		        "select count(*) from SatMan"+dbWhere);
		Long count = (Long)query.uniqueResult();
		return count;
	}
	
	@Override
	public List<SatMan> getChildOf(UUID idSatMan) {
		List<SatMan> queryResult = sessionFactory.getCurrentSession().createQuery("from SatMan WHERE idSatMan = '"+idSatMan.toString()+"'").list();
		List<SatMan> child = new ArrayList<SatMan>();
		if(queryResult.size()==0) return null;
		SatMan parent = queryResult.get(0);
		List<SatMan> queueSatMan = new ArrayList<SatMan>();
		queueSatMan.add(parent);
		while(queueSatMan.size()>0)
		{
			queryResult = sessionFactory.getCurrentSession().createQuery("from SatMan WHERE idSatManInduk = '"+queueSatMan.get(0).getIdSatMan()+"'").list();
			for (SatMan satMan : queryResult) {
				queueSatMan.add(satMan);
			}
			if(queryResult.size()==0)
			{
				child.add(queueSatMan.get(0));
			}
			queueSatMan.remove(0);
		}
		if(child.size() == 0) child.add(parent);
		return child;
	}

	@Override
	public boolean addChild(SatMan satMan,SatMan parent) {
		if(satMan.getIdSatMan().equals(parent.getIdSatMan()))
		{
			parent.setChild(satMan.getChild());
			return true;
		}
		for (SatMan anakSatMan : parent.getChild()) {
			if(addChild(anakSatMan, anakSatMan)) return true;
		}
		return false;
	}

}
