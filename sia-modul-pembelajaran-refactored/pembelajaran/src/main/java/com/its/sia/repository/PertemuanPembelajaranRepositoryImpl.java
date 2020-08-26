package com.its.sia.repository;

import java.util.List;
import java.util.UUID;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sia.modul.domain.AnggotaRombel;
import com.sia.modul.domain.PertemuanPembelajaran;
import com.sia.modul.domain.Rombel;

@Repository
@Transactional
public class PertemuanPembelajaranRepositoryImpl implements PertemuanPembelajaranRepository {
	

	private static final Logger logger = LoggerFactory.getLogger(PertemuanPembelajaranRepositoryImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<PertemuanPembelajaran> get(String where, String order, int limit, int offset) {
		String dbWhere ="";
		String dbOrder ="";
		if(where != "") dbWhere = " WHERE "+where;
		if(order != "") dbOrder = " ORDER BY "+order;
		
		Query query = sessionFactory.getCurrentSession().createQuery("select pertemuanPembelajaran from PertemuanPembelajaran "
				+ "pertemuanPembelajaran join pertemuanPembelajaran.pemb pemb join pemb.mk mk "
				+dbWhere+dbOrder);
		
		if(limit != -1 && limit>0) {
			query.setFirstResult(offset);
			if(offset < 0) offset = 0;
			query.setMaxResults(limit);
		}
		
		return query.list();
	}

	@Override
	public PertemuanPembelajaran getById(UUID idPertemuanPembelajaran) {
		List<PertemuanPembelajaran> queryResult = sessionFactory.getCurrentSession().createQuery("select pertemuanPembelajaran from PertemuanPembelajaran "
				+ "pertemuanPembelajaran join pertemuanPembelajaran.pemb pemb join pemb.mk mk "
				+ "WHERE pertemuanPembelajaran.idPertemuanPembelajaran = '"+idPertemuanPembelajaran.toString()+"'").list();
		if(queryResult.size()==0) return null;
		return queryResult.get(0);
	}

	@Override
	public UUID insert(PertemuanPembelajaran pertemuanPembelajaran) {
		logger.info("insert repository");
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		UUID insertId= (UUID)session.save(pertemuanPembelajaran);
		tx.commit();
		session.flush();
		session.close();
		return insertId;
	}

	@Override
	public void update(PertemuanPembelajaran pertemuanPembelajaran) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(pertemuanPembelajaran);
		tx.commit();
		session.flush();
		session.close();
	}

	@Override
	public void delete(PertemuanPembelajaran pertemuanPembelajaran) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.delete(pertemuanPembelajaran);
		tx.commit();
		session.flush();
		session.close();
	}

	@Override
	public long count(String where) {
		String dbWhere ="";
		if(where != "") dbWhere = " WHERE "+where;
		Query query = sessionFactory.getCurrentSession().createQuery(
				"select count(*) from PertemuanPembelajaran "
				+ "pertemuanPembelajaran join pertemuanPembelajaran.pemb pemb join pemb.mk mk "
				+ dbWhere);
		Long count = (Long)query.uniqueResult();
		return count;
	}

}
