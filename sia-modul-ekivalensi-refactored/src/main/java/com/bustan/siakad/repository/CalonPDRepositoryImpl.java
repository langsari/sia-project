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

import com.sia.modul.domain.CalonPD;

@Repository
@Transactional
public class CalonPDRepositoryImpl implements CalonPDRepository{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public List<CalonPD> get(String where, String order, int limit, int offset) {
		String dbWhere ="";
		String dbOrder ="";
		if(where != "") dbWhere = " WHERE "+where;
		if(order != "") dbOrder = " ORDER BY "+order;
				
		Query query = sessionFactory.getCurrentSession().createQuery("select calonPD from CalonPD calonPD join "
				+ "calonPD.satMan satMan left join calonPD.katalogAlihjenjang katalog"+
		dbWhere+dbOrder);
		
		if(limit != -1 && limit>0) {
			query.setFirstResult(offset);
			if(offset < 0) offset = 0;
			query.setMaxResults(limit);
		}
		
		return query.list();
	}

	@Override
	public CalonPD getById(UUID idCalonPD) {
		List<CalonPD> queryResult = sessionFactory.getCurrentSession().createQuery("select calonPD from CalonPD calonPD WHERE "
				+ "calonPD.idCalonPD = '"+idCalonPD.toString()+"'").list();
		if(queryResult.size()==0) return null;
		else
		{
			return queryResult.get(0);
		}
	}

	@Override
	public UUID insert(CalonPD calonPD) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		UUID insertId= (UUID)session.save(calonPD);
		tx.commit();
		session.flush();
		session.close();
		return insertId;
	}

	@Override
	public void update(CalonPD calonPD) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(calonPD);
		tx.commit();
		session.flush();
		session.close();
	}

	@Override
	public void delete(CalonPD calonPD) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.delete(calonPD);
		tx.commit();
		session.flush();
		session.close();
	}

	@Override
	public long count(String where) {
		String dbWhere ="";
		if(where != "") dbWhere = " WHERE "+where;
		Query query = sessionFactory.getCurrentSession().createQuery(
		        "select count(*) from CalonPD calonPD join calonPD.satMan satMan left join calonPD.katalogAlihjenjang katalog"+dbWhere);
		Long count = (Long)query.uniqueResult();
		return count;
	}

}
