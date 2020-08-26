package com.its.sia.repository;

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
public class PdRepositoryImpl implements PdRepository {
	
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
		List<Pd> queryResult = sessionFactory.getCurrentSession().createQuery("from Pd WHERE idPd = '"+idPd.toString()+"'").list();
		if(queryResult.size()==0) return null;
		else
		{
			return queryResult.get(0);
		}
	}

	@Override
	public UUID insert(Pd pd) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		ThnAjaranRepository thnAjaranRepository;
		UUID insertId= (UUID)session.save(pd);
		tx.commit();
		session.flush();
		session.close();
		return insertId;
	}

	@Override
	public void update(Pd pd) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(pd);
		tx.commit();
		session.flush();
		session.close();
	}

	@Override
	public void delete(UUID idPd) {
		// TODO Auto-generated method stub
		
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

	@Override
	public List<Integer> getAngkatan(String where, String order, int limit,
			int offset) {
		String dbWhere =" WHERE peran.namaPeran='Peserta Didik'";
		String dbOrder ="";
		if(where != "") dbWhere += " AND ( "+where+" )";
		if(order != "") dbOrder = " ORDER BY "+order;
				
		Query query = sessionFactory.getCurrentSession().createQuery("select distinct pd.angkatanPd "
				+ "from PeranPengguna peranPengguna join "
				+ " peranPengguna.peran peran "
				+ " join peranPengguna.pengguna pengguna  join peranPengguna.satMan satMan "
				+ " join pengguna.pd pd left join pd.ptk ptk"+dbWhere+dbOrder);
		
		if(limit != -1 && limit>0) {
			query.setFirstResult(offset);
			if(offset < 0) offset = 0;
			query.setMaxResults(limit);
		}
		
		return query.list();
	}

}
