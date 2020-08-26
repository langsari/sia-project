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

import com.sia.modul.domain.DetailPemetaan;

@Transactional
@Repository
public class DetailPemetaanRepositoryImpl implements DetailPemetaanRepository{

	@Autowired
	private SessionFactory sessionFactory;
	
	@SuppressWarnings("unused")
	@Override
	public boolean findRP(UUID idMK) {
		// TODO Auto-generated method stub
	DetailPemetaan queryResult = (DetailPemetaan) sessionFactory.getCurrentSession().createQuery(
				"select dp from DetailPemetaan dp "
				+ "join dp.capPembMK cpmk "
				+ "join dp.rpPerTemu rppt "
				+ "join cpmk.mk mk "
				+ "where mk.idMK = '"+idMK.toString()+"'"); 
		return false;
	}

	@Override
	public long count(String where) {
		// TODO Auto-generated method stub
		String dbWhere ="";
		if(where != "") dbWhere = " WHERE "+where;
		Query query = sessionFactory.getCurrentSession().createQuery(
		        "select count(*) from DetailPemetaan dp "
				+ "join dp.capPembMK cpmk "
				+ "join dp.rpPerTemu rppt "
				+ "join cpmk.mk mk"+dbWhere);
		Long count = (Long)query.uniqueResult();
		return count;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DetailPemetaan> get(String where, String order, int limit,
			int offset) {
		// TODO Auto-generated method stub
		String dbWhere ="";
		String dbOrder ="";
		if(where != "") dbWhere = " WHERE "+where;
		if(order != "") dbOrder = " ORDER BY "+order;
		 
		Query query = sessionFactory.getCurrentSession().createQuery("select dp from DetailPemetaan dp "
				+ "join dp.capPembMK cpmk "
				+ "join dp.rpPerTemu rppt "
				+ "join cpmk.mk mk" +dbWhere+dbOrder);
		
		if(limit != -1 && limit>0) {
			query.setFirstResult(offset);
			if(offset < 0) offset = 0;
			query.setMaxResults(limit);
		}
		
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DetailPemetaan> findCapPembMK(String idRPPerTemu) {
		// TODO Auto-generated method stub
		List<DetailPemetaan> queryResult = sessionFactory.getCurrentSession().createQuery("select dp from DetailPemetaan dp " 
				+ "join dp.capPembMK cpmk "
				+ "join dp.rpPerTemu rppt "
				+ "join cpmk.mk mk "
				+ "where rppt.idRPPerTemu = '"+ idRPPerTemu +"'").list();
		if(queryResult.size()==0) return null;
		return queryResult;
	}

	@Override
	public void update(DetailPemetaan detailPemetaanNew) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(detailPemetaanNew);
		tx.commit();
		session.flush();
		session.close();
	}

	@Override
	public UUID insert(DetailPemetaan detailPemetaanNew) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		UUID insertId= (UUID)session.save(detailPemetaanNew);
		tx.commit();
		session.flush();
		session.close();
		return insertId;
	}

}
