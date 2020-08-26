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

import com.sia.modul.domain.Pemb;

@Repository
@Transactional
public class PembRepositoryImpl implements PembRepository {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public List<Pemb> get() {
		return get("");
	}

	@Override
	public List<Pemb> get(String where) {
		return get(where,"");
	}

	@Override
	public List<Pemb> get(String where, String order) {
		return get(where,order,-1,-1);
	}
	
	@Override
	public List<Pemb> get(String where, String order, int limit, int offset) {
		String dbWhere ="";
		String dbOrder ="";
		if(where != "") dbWhere = " WHERE "+where;
		if(order != "") dbOrder = " ORDER BY "+order;
		
		Query query = sessionFactory.getCurrentSession().createQuery("select pemb from Pemb "
				+ "pemb left join pemb.mk mk"
				+dbWhere+dbOrder);
		
		if(limit != -1 && limit>0) {
			query.setFirstResult(offset);
			if(offset < 0) offset = 0;
			query.setMaxResults(limit);
		}
		
		return query.list();
	}

	@Override
	public Pemb getById(UUID idPemb) {
		List<Pemb> queryResult = sessionFactory.getCurrentSession().createQuery("select pemb from Pemb "
				+ "pemb left join pemb.mk mk"
				+ "WHERE pemb.idPemb = '"+idPemb.toString()+"'").list();
		if(queryResult.size()==0) return null;
		return queryResult.get(0);
	}
}
