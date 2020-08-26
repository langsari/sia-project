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

import com.sia.modul.domain.EkuivalensiMKAlihjenjang;
import com.sia.modul.domain.MK;
import com.sia.modul.domain.MKAlihjenjang;

@Repository
@Transactional
public class EkuivalensiMKAlihjenjangRepositoryImpl implements EkuivalensiMKAlihjenjangRepository{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public List<EkuivalensiMKAlihjenjang> get(String where, String order, int limit, int offset) {
		String dbWhere ="";
		String dbOrder ="";
		if(where != "") dbWhere = " WHERE "+where;
		if(order != "") dbOrder = " ORDER BY "+order;
				
		Query query = sessionFactory.getCurrentSession().createQuery("select ekuivalensiMKAlihjenjang from EkuivalensiMKAlihjenjang "
				+ "ekuivalensiMKAlihjenjang left join ekuivalensiMKAlihjenjang.mk mk left join ekuivalensiMKAlihjenjang.mkAlihjenjang mkAlihjenjang"
				+ "left join ekuivalensiMKAlihjenjang.relasiMKAlihjenjang relasiMKAlihjenjang"+
		dbWhere+dbOrder);
		
		if(limit != -1 && limit>0) {
			query.setFirstResult(offset);
			if(offset < 0) offset = 0;
			query.setMaxResults(limit);
		}
		
		return query.list();
	}

	@Override
	public EkuivalensiMKAlihjenjang getById(UUID idEkuivalensiMKAlihjenjang) {
		List<EkuivalensiMKAlihjenjang> queryResult = sessionFactory.getCurrentSession().createQuery("select ekuivalensiMKAlihjenjang from "
				+ "EkuivalensiMKAlihjenjang ekuivalensiMKAlihjenjang WHERE ekuivalensiMKAlihjenjang.idEkuivalensiMKAlihjenjang = '"
				+ idEkuivalensiMKAlihjenjang.toString()+"'").list();
		if(queryResult.size()==0) return null;
		else
		{
			return queryResult.get(0);
		}
	}

	@Override
	public UUID insert(EkuivalensiMKAlihjenjang ekuivalensiMKAlihjenjang) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		UUID insertId= (UUID)session.save(ekuivalensiMKAlihjenjang);
		tx.commit();
		session.flush();
		session.close();
		return insertId;
	}

	@Override
	public void update(EkuivalensiMKAlihjenjang ekuivalensiMKAlihjenjang) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(ekuivalensiMKAlihjenjang);
		tx.commit();
		session.flush();
		session.close();
	}

	@Override
	public void delete(EkuivalensiMKAlihjenjang ekuivalensiMKAlihjenjang) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.delete(ekuivalensiMKAlihjenjang);
		tx.commit();
		session.flush();
		session.close();
	}

	@Override
	public List<MK> getMKDistinct(UUID idRelasiMKAlihjenjang) {
		List<MK> queryResult = sessionFactory.getCurrentSession().createQuery("select DISTINCT mk from EkuivalensiMKAlihjenjang "
				+ "ekuivalensiMKAlihjenjang left join ekuivalensiMKAlihjenjang.mk mk WHERE ekuivalensiMKAlihjenjang.relasiMKAlihjenjang = '"+idRelasiMKAlihjenjang.toString()+"'").list();
		return queryResult;
	}

	@Override
	public List<MKAlihjenjang> getMKAlihjenjangDistinct(UUID idRelasiMKAlihjenjang) {
		List<MKAlihjenjang> queryResult = (List<MKAlihjenjang>) sessionFactory.getCurrentSession().createQuery("select DISTINCT mkAlihjenjang from EkuivalensiMKAlihjenjang "
				+ "ekuivalensiMKAlihjenjang left join ekuivalensiMKAlihjenjang.mkAlihjenjang mkAlihjenjang WHERE ekuivalensiMKAlihjenjang.relasiMKAlihjenjang = '"+idRelasiMKAlihjenjang.toString()+"'").list();
		return queryResult;
	}
}
