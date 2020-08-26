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

import com.sia.modul.domain.Pemb;
import com.sia.modul.domain.SatMan;

@Repository
@Transactional
public class PembRepositoryImpl implements PembRepository {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public List<Pemb> get() {
		return get("");
	}

	@Override
	public List<Pemb> get(String where) {
		return get(where,"");
	}

	@Override
	public List<Pemb> get(String where, String order) {
		return get(where,order,-1,-1);
	}
	
	@Override
	public List<Pemb> get(String where, String order, int limit, int offset) {
		String dbWhere ="";
		String dbOrder ="";
		if(where != "") dbWhere = " WHERE "+where;
		if(order != "") dbOrder = " ORDER BY "+order;
		
		Query query = sessionFactory.getCurrentSession().createQuery("select pemb from Pemb "
				+ "pemb join pemb.mk mk join pemb.tglSmt tglSmt "
				+ "join mk.kurikulum kurikulum join kurikulum.satMan satMan "
				+ "join tglSmt.thnAjaran thnAjaran join tglSmt.smt smt"
				+dbWhere+dbOrder);
		
		if(limit != -1 && limit>0) {
			query.setFirstResult(offset);
			if(offset < 0) offset = 0;
			query.setMaxResults(limit);
		}
		
		return query.list();
	}

	@Override
	public Pemb getById(UUID idPemb) {
		List<Pemb> queryResult = sessionFactory.getCurrentSession().createQuery("select pemb from Pemb "
				+ "pemb join pemb.mk mk join pemb.tglSmt tglSmt "
				+ "join mk.kurikulum kurikulum join kurikulum.satMan satMan "
				+ "join tglSmt.thnAjaran thnAjaran join tglSmt.smt smt "
				+ "WHERE pemb.idPemb = '"+idPemb.toString()+"'").list();
		if(queryResult.size()==0) return null;
		return queryResult.get(0);
	}

	@Override
	public UUID insert(Pemb pemb) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		UUID insertId= (UUID)session.save(pemb);
		tx.commit();
		session.flush();
		session.close();
		return insertId;
	}

	@Override
	public void update(Pemb pemb) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(pemb);
		tx.commit();
		session.flush();
		session.close();
	}

	@Override
	public void delete(Pemb pemb) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.delete(pemb);
		tx.commit();
		session.flush();
		session.close();
	}

	@Override
	public long count(String where) {
		String dbWhere ="";
		if(where != "") dbWhere = " WHERE "+where;
		Query query = sessionFactory.getCurrentSession().createQuery(
				"select count(*) from Pemb "
				+ "pemb join pemb.mk mk join pemb.tglSmt tglSmt "
				+ "join mk.kurikulum kurikulum join kurikulum.satMan satMan "
				+ "join tglSmt.thnAjaran thnAjaran join tglSmt.smt smt"
				+ dbWhere);
		Long count = (Long)query.uniqueResult();
		return count;
	}
	
	@Override
	public List<Pemb> getPembInSatMan() {
		return getPembInSatMan("");
	}

	@Override
	public List<Pemb> getPembInSatMan(String where) {
		return getPembInSatMan(where,"");
	}

	@Override
	public List<Pemb> getPembInSatMan(String where, String order) {
		return getPembInSatMan(where,order,-1,-1);
	}
	
	@Override
	public List<Pemb> getPembInSatMan(String where, String order, int limit, int offset) {
		String dbWhere ="";
		String dbOrder ="";
		if(where != "") dbWhere = " WHERE "+where;
		if(order != "") dbOrder = " ORDER BY "+order;
		
		Query query = sessionFactory.getCurrentSession().createQuery("select pemb from PembSatMan pembSatMan left join pembSatMan.pemb "
				+ "pemb join pemb.mk mk join pemb.tglSmt tglSmt "
				+ "join mk.kurikulum kurikulum join pembSatMan.satMan satMan "
				+ "join tglSmt.thnAjaran thnAjaran join tglSmt.smt smt "
				+ "join kurikulum.satMan milikSatMan "
				+dbWhere+dbOrder);
		
		if(limit != -1 && limit>0) {
			query.setFirstResult(offset);
			if(offset < 0) offset = 0;
			query.setMaxResults(limit);
		}
		
		return query.list();
	}
	
	@Override
	public List<SatMan> getSatManKurikulum(String where, String order, int limit, int offset) {
		String dbWhere ="";
		String dbOrder ="";
		if(where != "") dbWhere = " WHERE "+where;
		if(order != "") dbOrder = " ORDER BY "+order;
		
		Query query = sessionFactory.getCurrentSession().createQuery("select distinct milikSatMan from PembSatMan pembSatMan left join pembSatMan.pemb "
				+ "pemb join pemb.mk mk join pemb.tglSmt tglSmt "
				+ "join mk.kurikulum kurikulum join pembSatMan.satMan satMan "
				+ "join tglSmt.thnAjaran thnAjaran join tglSmt.smt smt "
				+ "join kurikulum.satMan milikSatMan "
				+dbWhere+dbOrder);
		
		if(limit != -1 && limit>0) {
			query.setFirstResult(offset);
			if(offset < 0) offset = 0;
			query.setMaxResults(limit);
		}
		
		return query.list();
	}

}
