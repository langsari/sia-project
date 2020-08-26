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
import com.sia.modul.domain.Pemb;
import com.sia.modul.domain.PembSatMan;
import com.sia.modul.domain.Rombel;

@Repository
@Transactional
public class PembSatManRepositoryImpl implements PembSatManRepository {
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<PembSatMan> get(String where, String order, int limit, int offset) {
		String dbWhere ="";
		String dbOrder ="";
		if(where != "") dbWhere = " WHERE "+where;
		if(order != "") dbOrder = " ORDER BY "+order;
		
		Query query = sessionFactory.getCurrentSession().createQuery("select pembSatMan from PembSatMan "
				+ "pembSatMan join pembSatMan.pemb pemb join pembSatMan.satMan satMan join pemb.mk mk "
				+dbWhere+dbOrder);
		
		if(limit != -1 && limit>0) {
			query.setFirstResult(offset);
			if(offset < 0) offset = 0;
			query.setMaxResults(limit);
		}
		
		return query.list();
	}

	@Override
	public PembSatMan getById(UUID idPembSatMan) {
		List<PembSatMan> queryResult = sessionFactory.getCurrentSession().createQuery("select pembSatMan from PembSatMan "
				+ "pembSatMan join pembSatMan.pemb pemb join pembSatMan.satMan satMan join pemb.mk mk "
				+ "WHERE pembSatMan.idPembSatMan = '"+idPembSatMan.toString()+"'").list();
		if(queryResult.size()==0) return null;
		return queryResult.get(0);
	}

	@Override
	public UUID insert(PembSatMan pembSatMan) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		UUID insertId= (UUID)session.save(pembSatMan);
		tx.commit();
		session.flush();
		session.close();
		return insertId;
	}

	@Override
	public void update(PembSatMan pembSatMan) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(pembSatMan);
		session.flush();
		session.close();
		tx.commit();
	}

	@Override
	public void delete(PembSatMan pembSatMan) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.delete(pembSatMan);
		tx.commit();
		session.flush();
		session.close();
	}

	@Override
	public long count(String where) {
		String dbWhere ="";
		if(where != "") dbWhere = " WHERE "+where;
		Query query = sessionFactory.getCurrentSession().createQuery(
				"select count(*) from PembSatMan "
				+ "pembSatMan join pembSatMan.pemb pemb join pembSatMan.satMan satMan join pemb.mk mk "
				+ dbWhere);
		Long count = (Long)query.uniqueResult();
		return count;
	}

	@Override
	public long countPemb(String where) {
		String dbWhere ="";
		if(where != "") dbWhere = " WHERE "+where;
		Query query = sessionFactory.getCurrentSession().createQuery(
				"select count(*) from PembSatMan "
				+ "pendidikPengajar join pembSatMan.satMan satMan join pembSatMan.pemb pemb join pemb.mk mk join pemb.tglSmt tglSmt "
				+ "join tglSmt.thnAjaran thnAjaran join tglSmt.smt smt "
				+ dbWhere);
		Long count = (Long)query.uniqueResult();
		return count;
	}
	
	@Override
	public List<Pemb> getPemb(String where, String order, int limit, int offset) {
		String dbWhere ="";
		String dbOrder ="";
		if(where != "") dbWhere = " WHERE "+where;
		if(order != "") dbOrder = " ORDER BY "+order;
		
		Query query = sessionFactory.getCurrentSession().createQuery("select pemb from PembSatMan "
				+ "pembSatMan join pembSatMan.satMan satMan join pembSatMan.pemb pemb join pemb.mk mk join pemb.tglSmt tglSmt "
				+ "join tglSmt.thnAjaran thnAjaran join tglSmt.smt smt "
				+dbWhere+dbOrder);
		
		if(limit != -1 && limit>0) {
			query.setFirstResult(offset);
			if(offset < 0) offset = 0;
			query.setMaxResults(limit);
		}
		
		return query.list();
	}

}
