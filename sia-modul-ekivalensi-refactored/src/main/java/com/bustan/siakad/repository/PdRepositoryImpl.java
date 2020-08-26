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

import com.sia.modul.domain.Pd;

@Repository
@Transactional
public class PdRepositoryImpl implements PdRepository{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public List<Pd> get(String where, String order, int limit, int offset) {
		String dbWhere =" WHERE peran.namaPeran='Peserta Didik'";
		String dbOrder ="";
		if(where != "") dbWhere += " AND ( "+where+" )";
		if(order != "") dbOrder = " ORDER BY "+order;
				
		Query query = sessionFactory.getCurrentSession().createQuery("select pd from PeranPengguna peranPengguna join "
				+ " peranPengguna.peran peran "
				+ " join peranPengguna.pengguna pengguna join peranPengguna.satMan satMan "
				+ " join pengguna.pd pd left join pd.ptk ptk"+dbWhere+dbOrder);
		
		if(limit != -1 && limit>0) {
			query.setFirstResult(offset);
			if(offset < 0) offset = 0;
			query.setMaxResults(limit);
		}
		
		return query.list();
	}

	@Override
	public Pd getById(UUID idPd) {
		List<Pd> queryResult = sessionFactory.getCurrentSession().createQuery("select pd from Pd pd WHERE "
				+ "pd.idPd = '"+idPd.toString()+"'").list();
		if(queryResult.size()==0) return null;
		else
		{
			return queryResult.get(0);
		}
	}

	@Override
	public long count(String where) {
		String dbWhere =" WHERE peran.namaPeran='Peserta Didik'";
		if(where != "") dbWhere += " AND ( "+where+" )";
		Query query = sessionFactory.getCurrentSession().createQuery(
		        "select count(pd) from PeranPengguna peranPengguna "
        		+ " join peranPengguna.peran peran "
				+ " join peranPengguna.pengguna pengguna  join peranPengguna.satMan satMan "
				+ " join pengguna.pd pd left join pd.ptk ptk"+dbWhere);
		Long count = (Long)query.uniqueResult();
		return count;
	}

}
