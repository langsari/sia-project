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

import com.sia.modul.domain.KonversiNilai;

@Repository
@Transactional
public class KonversiNilaiRepositoryImpl implements KonversiNilaiRepository{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public List<KonversiNilai> get(String where, String order, int limit, int offset) {
		String dbWhere ="";
		String dbOrder ="";
		if(where != "") dbWhere = " WHERE "+where;
		if(order != "") dbOrder = " ORDER BY "+order;
				
		Query query = sessionFactory.getCurrentSession().createQuery("select konversiNilai from KonversiNilai konversiNilai"+
		dbWhere+dbOrder);
		
		if(limit != -1 && limit>0) {
			query.setFirstResult(offset);
			if(offset < 0) offset = 0;
			query.setMaxResults(limit);
		}
		
		return query.list();
	}

	@Override
	public KonversiNilai getById(UUID idKonversi) {
		List<KonversiNilai> queryResult = sessionFactory.getCurrentSession().createQuery("select konversiNilai from KonversiNilai konversiNilai WHERE "
				+ "konversiNilai.idKonversi = '"+idKonversi.toString()+"'").list();
		if(queryResult.size()==0) return null;
		else
		{
			return queryResult.get(0);
		}
	}

	@Override
	public long count(String where) {
		String dbWhere ="";
		if(where != "") dbWhere = " WHERE "+where;
		Query query = sessionFactory.getCurrentSession().createQuery(
		        "select count(*) from KonversiNilai konversiNilai"+dbWhere);
		Long count = (Long)query.uniqueResult();
		return count;
	}

}
