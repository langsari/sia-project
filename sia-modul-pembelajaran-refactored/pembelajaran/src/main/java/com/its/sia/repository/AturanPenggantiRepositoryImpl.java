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

import com.sia.modul.domain.AturanPengganti;
import com.sia.modul.domain.Smt;
import com.sia.modul.domain.TglSmt;

@Repository
@Transactional
public class AturanPenggantiRepositoryImpl implements AturanPenggantiRepository {
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<AturanPengganti> get(String where, String order, int limit, int offset) {
		String dbWhere ="";
		String dbOrder ="";
		if(where != "") dbWhere = " WHERE "+where;
		if(order != "") dbOrder = " ORDER BY "+order;
		
		Query query = sessionFactory.getCurrentSession().createQuery("select aturanPengganti from AturanPengganti aturanPengganti join aturanPengganti.tglSmt tglSmt "
				+" join tglSmt.thnAjaran thnAjaran"
				+" join tglSmt.smt smt"
				+" join aturanPengganti.satMan satMan"+dbWhere+dbOrder);
		
		if(limit != -1 && limit>0) {
			query.setFirstResult(offset);
			if(offset < 0) offset = 0;
			query.setMaxResults(limit);
		}
		
		return query.list();
	}

	@Override
	public AturanPengganti getById(UUID idAturanPengganti) {
		List<AturanPengganti> queryResult = sessionFactory.getCurrentSession().createQuery("from AturanPengganti WHERE idAturanPengganti = '"+idAturanPengganti.toString()+"'").list();
		if(queryResult.size()==0) return null;
		else
		{
			return queryResult.get(0);
		}
	}

	@Override
	public UUID insert(AturanPengganti aturanPengganti) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		ThnAjaranRepository thnAjaranRepository;
		UUID insertId= (UUID)session.save(aturanPengganti);
		tx.commit();
		session.flush();
		session.close();
		return insertId;
	}

	@Override
	public void update(AturanPengganti aturanPengganti) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(aturanPengganti);
		tx.commit();
		session.flush();
		session.close();
	}

	@Override
	public void delete(AturanPengganti aturanPengganti) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.delete(aturanPengganti);
		tx.commit();
		session.flush();
		session.close();
	}

	@Override
	public long count(String where) {
		String dbWhere ="";
		if(where != "") dbWhere = " WHERE "+where;
		Query query = sessionFactory.getCurrentSession().createQuery(
		        "select count(*) from AturanPengganti aturanPengganti join aturanPengganti.tglSmt tglSmt "
				+" join tglSmt.thnAjaran thnAjaran"
				+" join tglSmt.smt smt"
				+" join aturanPengganti.satMan satMan"+dbWhere);
		Long count = (Long)query.uniqueResult();
		return count;
	}

}
