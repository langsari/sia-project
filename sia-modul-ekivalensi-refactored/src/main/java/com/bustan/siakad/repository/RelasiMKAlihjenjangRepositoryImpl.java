package com.bustan.siakad.repository;

import java.util.List;
import java.util.UUID;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sia.modul.domain.RelasiMKAlihjenjang;

@Repository
@Transactional
public class RelasiMKAlihjenjangRepositoryImpl implements RelasiMKAlihjenjangRepository{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public List<RelasiMKAlihjenjang> get(String where, String order, int limit, int offset) {
		String dbWhere ="";
		String dbOrder ="";
		if(where != "") dbWhere = " WHERE "+where;
		if(order != "") dbOrder = " ORDER BY "+order;
				
		Query query = sessionFactory.getCurrentSession().createQuery("select relasiMKAlihjenjang from RelasiMKAlihjenjang "
				+ "relasiMKAlihjenjang join relasiMKAlihjenjang.katalog katalog join relasiMKAlihjenjang.kurikulum kurikulum"+
		dbWhere+dbOrder);
		
		if(limit != -1 && limit>0) {
			query.setFirstResult(offset);
			if(offset < 0) offset = 0;
			query.setMaxResults(limit);
		}
		
		return query.list();
	}

	@Override
	public RelasiMKAlihjenjang getById(UUID idRelasiMKPekuivalensi) {
		List<RelasiMKAlihjenjang> queryResult = sessionFactory.getCurrentSession().createQuery("select relasiMKAlihjenjang from "
				+ "RelasiMKAlihjenjang relasiMKAlihjenjang WHERE relasiMKAlihjenjang.idRelasiMKAlihjenjang = '"
				+idRelasiMKPekuivalensi.toString()+"'").list();
		if(queryResult.size()==0) return null;
		else
		{
			return queryResult.get(0);
		}
	}

	@Override
	public UUID insert(RelasiMKAlihjenjang relasiMKAlihjenjang) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		UUID insertId= (UUID)session.save(relasiMKAlihjenjang);
		tx.commit();
		session.flush();
		session.close();
		return insertId;
	}

	@Override
	public void update(RelasiMKAlihjenjang relasiMKAlihjenjang) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(relasiMKAlihjenjang);
		tx.commit();
		session.flush();
		session.close();
	}

	@Override
	public void delete(RelasiMKAlihjenjang relasiMKAlihjenjang) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.delete(relasiMKAlihjenjang);
		tx.commit();
		session.flush();
		session.close();
	}

	@Override
	public List<Object[]> getRelasi(String where, String order, int limit, int offset) {
		String dbWhere ="";
		String dbOrder ="";
		if(where != "") dbWhere = " WHERE "+where;
		if(order != "") dbOrder = " ORDER BY "+order;
				
		Query query = sessionFactory.getCurrentSession().createQuery("select DISTINCT katalog, kurikulum from RelasiMKAlihjenjang relasiMKAlihjenjang join "
				+ "relasiMKAlihjenjang.kurikulum kurikulum join relasiMKAlihjenjang.katalog katalog"+
		dbWhere+dbOrder);
		
		if(limit != -1 && limit>0) {
			query.setFirstResult(offset);
			if(offset < 0) offset = 0;
			query.setMaxResults(limit);
		}
		
		return query.list();
	}
	
	@Override
	public long count(String where) {
		String dbWhere ="";
		if(where != "") dbWhere = " WHERE "+where;
		Query query = sessionFactory.getCurrentSession().createQuery(
				"select count(*) from RelasiMKAlihjenjang relasiMKAlihjenjang join "
				+ "relasiMKAlihjenjang.kurikulum kurikulum join relasiMKAlihjenjang.katalog katalog "
				+dbWhere+" GROUP BY katalog_id, kurikulum_id");
		
		Long count = new Long(query.list().size());
		return count;
	}

}
