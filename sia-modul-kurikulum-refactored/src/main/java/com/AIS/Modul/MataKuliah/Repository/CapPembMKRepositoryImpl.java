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

import com.sia.modul.domain.CapPemb;
import com.sia.modul.domain.CapPembMK; 

@Transactional
@Repository
public class CapPembMKRepositoryImpl implements CapPembMKRepository{

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public long count(String where) {
		// TODO Auto-generated method stub
		String dbWhere ="";
		if(where != "") dbWhere = " WHERE " +where;
		Query query = sessionFactory.getCurrentSession().createQuery("select count(*) from CapPembMK cpmk "  
				+ "join cpmk.mk mk" 
		        +dbWhere);
		Long count = (Long)query.uniqueResult();
		return count;
	}
 
	@SuppressWarnings("unchecked")
	@Override
	public List<CapPembMK> get(String where, String order, int limit, int offset) {
		// TODO Auto-generated method stub
		String dbWhere ="";
		String dbOrder ="";
		if(where != "") dbWhere = " WHERE "+where;
		if(order != "") dbOrder = " ORDER BY "+order; 
		Query query = sessionFactory.getCurrentSession().createQuery("select cpmk from CapPembMK cpmk "   
				+ "join cpmk.mk mk" 
				+dbWhere+dbOrder);
		if(limit != -1 && limit>0) {
			query.setFirstResult(offset);
			if(offset < 0) offset = 0;
			query.setMaxResults(limit);
		}
		
		return query.list();
	}

	@Override
	public void update(CapPembMK capPembMK) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(capPembMK);
		tx.commit();
		session.flush();
		session.close();
	}

	@Override
	public UUID insert(CapPembMK capPembMK) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		UUID insertId= (UUID)session.save(capPembMK);
		tx.commit();
		session.flush();
		session.close();
		return insertId;
	}
 
	@SuppressWarnings("unchecked")
	@Override
	public CapPembMK findById(UUID idCapPembMK) {
		// TODO Auto-generated method stub  
		List<CapPembMK> queryResult = sessionFactory.getCurrentSession().createQuery("select cpmk from CapPembMK cpmk "
				+ "join cpmk.mk mk WHERE cpmk.idCapPembMK='"+idCapPembMK.toString()+"'").list();
		if(queryResult.size()==0) return null;
		return queryResult.get(0);
	} 
	@SuppressWarnings("unchecked")
	@Override
	public List<CapPembMK> findAll() {
		// TODO Auto-generated method stub
		return sessionFactory.getCurrentSession().createQuery("select cpmk from CapPembMK cpmk WHERE cpmk.statusCapPembMK = false").list();

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CapPemb> findParent(CapPembMK capPembMK) {
		// TODO Auto-generated method stub
		List<CapPemb> cpList = sessionFactory.getCurrentSession().createQuery("select scpmk.capPemb from SubCapPembMK scpmk join scpmk.mk mk WHERE scpmk.capPembMK = '"+capPembMK+"' order by mk.namaMK").list();
		return cpList;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CapPembMK> findByMK(UUID idMK) {
		// TODO Auto-generated method stub
		List<CapPembMK> queryResult = sessionFactory.getCurrentSession().createQuery("select cpmk from CapPembMK cpmk "
				+ "join cpmk.mk mk "
				+ "WHERE mk.idMK='"+idMK.toString()+"' AND cpmk.statusCapPembMK=false").list();
		if(queryResult.size()==0) return null;
		return queryResult;
	}

}
