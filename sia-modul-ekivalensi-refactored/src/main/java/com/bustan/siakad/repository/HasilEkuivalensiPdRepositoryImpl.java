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

import com.sia.modul.domain.HasilEkuivalensiPd;

@Repository
@Transactional
public class HasilEkuivalensiPdRepositoryImpl implements HasilEkuivalensiPdRepository{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public List<HasilEkuivalensiPd> get(String where, String order, int limit, int offset) {
		String dbWhere ="";
		String dbOrder ="";
		if(where != "") dbWhere = " WHERE "+where;
		if(order != "") dbOrder = " ORDER BY "+order;
				
		Query query = sessionFactory.getCurrentSession().createQuery("select hasilEkuivalensiPd from HasilEkuivalensiPd hasilEkuivalensiPd left join "
				+ "hasilEkuivalensiPd.pd pd left join hasilEkuivalensiPd.mk mk"+
		dbWhere+dbOrder);
		
		if(limit != -1 && limit>0) {
			query.setFirstResult(offset);
			if(offset < 0) offset = 0;
			query.setMaxResults(limit);
		}
		
		return query.list();
	}

	@Override
	public HasilEkuivalensiPd getById(UUID idHasilEkuivalensiPd) {
		List<HasilEkuivalensiPd> queryResult = sessionFactory.getCurrentSession().createQuery("select hasilEkuivalensiPd from HasilEkuivalensiPd hasilEkuivalensiPd WHERE "
				+ "hasilEkuivalensiPd.idHasilEkuivalensiPd = '"+idHasilEkuivalensiPd.toString()+"'").list();
		if(queryResult.size()==0) return null;
		else
		{
			return queryResult.get(0);
		}
	}

	@Override
	public UUID insert(HasilEkuivalensiPd hasilEkuivalensiPd) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		UUID insertId= (UUID)session.save(hasilEkuivalensiPd);
		tx.commit();
		session.flush();
		session.close();
		return insertId;
	}

	@Override
	public void update(HasilEkuivalensiPd hasilEkuivalensiPd) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(hasilEkuivalensiPd);
		tx.commit();
		session.flush();
		session.close();
	}

	@Override
	public void delete(HasilEkuivalensiPd hasilEkuivalensiPd) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.delete(hasilEkuivalensiPd);
		tx.commit();
		session.flush();
		session.close();
	}

	@Override
	public long count(String where) {
		String dbWhere ="";
		if(where != "") dbWhere = " WHERE "+where;
		Query query = sessionFactory.getCurrentSession().createQuery(
		        "select count(*) from HasilEkuivalensiPd hasilEkuivalensiPd left join hasilEkuivalensiPd.pd pd left join hasilEkuivalensiPd.mk mk"+dbWhere);
		Long count = (Long)query.uniqueResult();
		return count;
	}

}
