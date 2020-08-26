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

import com.sia.modul.domain.AnggotaRombel;
import com.sia.modul.domain.PresensiPd;
import com.sia.modul.domain.PresensiPengajar;
import com.sia.modul.domain.Rombel;

@Repository
@Transactional
public class PresensiPengajarRepositoryImpl implements PresensiPengajarRepository {
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<PresensiPengajar> get(String where, String order, int limit, int offset) {
		String dbWhere ="";
		String dbOrder ="";
		if(where != "") dbWhere = " WHERE "+where;
		if(order != "") dbOrder = " ORDER BY "+order;
		
		Query query = sessionFactory.getCurrentSession().createQuery("select presensiPengajar from PresensiPengajar "
				+ "presensiPengajar join presensiPengajar.pendidikPengajar pendidikPengajar join pendidikPengajar.ptk ptk join presensiPengajar.pertemuanPembelajaran pertemuanPembelajaran "
				+ "join pertemuanPembelajaran.pemb pemb "
				+dbWhere+dbOrder);
		
		if(limit != -1 && limit>0) {
			query.setFirstResult(offset);
			if(offset < 0) offset = 0;
			query.setMaxResults(limit);
		}
		
		return query.list();
	}

	@Override
	public PresensiPengajar getById(UUID idPresensiPengajar) {
		List<PresensiPengajar> queryResult = sessionFactory.getCurrentSession().createQuery("select presensiPengajar from PresensiPengajar "
				+ "WHERE presensiPengajar.idPresensiPengajar = '"+idPresensiPengajar.toString()+"'").list();
		if(queryResult.size()==0) return null;
		return queryResult.get(0);
	}

	@Override
	public UUID insert(PresensiPengajar presensiPengajar) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		UUID insertId= (UUID)session.save(presensiPengajar);
		tx.commit();
		session.flush();
		session.close();
		return insertId;
	}

	@Override
	public void update(PresensiPengajar presensiPengajar) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(presensiPengajar);
		tx.commit();
		session.flush();
		session.close();
	}

	@Override
	public void delete(PresensiPengajar presensiPengajar) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.delete(presensiPengajar);
		tx.commit();
		session.flush();
		session.close();
	}

	@Override
	public long count(String where) {
		String dbWhere ="";
		if(where != "") dbWhere = " WHERE "+where;
		Query query = sessionFactory.getCurrentSession().createQuery(
				"select count(*) from PresensiPengajar "
				+ "presensiPengajar join presensiPengajar.pendidikPengajar pendidikPengajar join pendidikPengajar.ptk ptk join presensiPengajar.pertemuanPembelajaran pertemuanPembelajaran "
				+ "join pertemuanPembelajaran.pemb pemb "
				+ dbWhere);
		Long count = (Long)query.uniqueResult();
		return count;
	}

}
