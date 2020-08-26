package com.siakad.modul_penilaian.repository;

import java.util.List;
import java.util.UUID;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sia.modul.domain.Pd;

@Transactional
@Repository
public class PdRepositoryImpl implements PdRepository {
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public List<Pd> get(String where, String order, int limit, int offset) {
		// TODO Auto-generated method stub
		String dbWhere = "";
		String dbOrder = "";
		if(where != "")
			dbWhere += " WHERE " + where;
		if(order != "")
			dbOrder += " ORDER BY " + order;
		Query query = sessionFactory.getCurrentSession().createQuery("FROM Pd" + dbWhere + dbOrder);
		return query.list();
	}

	@Override
	public Pd getById(UUID idPd) {
		// TODO Auto-generated method stub
		return (Pd) sessionFactory.getCurrentSession().get(Pd.class, idPd);
	}

}
