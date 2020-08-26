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

import com.sia.modul.domain.Kurikulum;
import com.sia.modul.domain.MK;
import com.sia.modul.domain.SatMan;
import com.sia.modul.domain.SatManMK;

@Repository
@Transactional
public class SatManMKRepositoryImpl implements SatManMKRepository{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public List<SatManMK> get(String where, String order, int limit, int offset) {
		String dbWhere ="";
		String dbOrder ="";
		if(where != "") dbWhere = " WHERE "+where;
		if(order != "") dbOrder = " ORDER BY "+order;
				
		Query query = sessionFactory.getCurrentSession().createQuery("select satManMK from SatManMK satManMK join "
				+ "satManMK.satMan satMan join satManMK.mk mk"+
		dbWhere+dbOrder);
		
		if(limit != -1 && limit>0) {
			query.setFirstResult(offset);
			if(offset < 0) offset = 0;
			query.setMaxResults(limit);
		}
		
		return query.list();
	}

	@Override
	public SatManMK getById(UUID idKatalogAlihjenjang) {
		List<SatManMK> queryResult = sessionFactory.getCurrentSession().createQuery("select satManMK from SatManMK satManMK WHERE "
				+ "satManMK.idSatManMK = '"+idKatalogAlihjenjang.toString()+"'").list();
		if(queryResult.size()==0) return null;
		else
		{
			return queryResult.get(0);
		}
	}
	@Override
	public long count(String where) {
		String dbWhere ="";
		if(where != "") dbWhere = " WHERE "+where;
		Query query = sessionFactory.getCurrentSession().createQuery(
		        "select count(*) from SatManMK satManMK join "
				+ "satManMK.satMan satMan join satManMK.mk mk join mk.kurikulum kurikulum"+dbWhere +" GROUP BY mk");
		Long count = new Long(query.list().size());
		return count;
	}

	@Override
	public List<Kurikulum> getKurikulumDistinct(String where) {
		String dbWhere ="";
		if(where != "") dbWhere = " WHERE "+where;
		
		Query query = sessionFactory.getCurrentSession().createQuery("select DISTINCT kurikulum from SatManMK satManMK join "
				+ "satManMK.satMan satMan join satManMK.mk mk join mk.kurikulum kurikulum "+
				dbWhere);
		return query.list();
	}

	@Override
	public List<MK> getMKDistinct(String where, String order, int limit, int offset) {
		String dbWhere ="";
		String dbOrder ="";
		if(where != "") dbWhere = " WHERE "+where;
		if(order != "") dbOrder = " ORDER BY "+order;
		
		Query query = sessionFactory.getCurrentSession().createQuery("select DISTINCT mk from SatManMK satManMK join "
				+ "satManMK.mk mk join mk.kurikulum kurikulum"+dbWhere);
		
		if(limit != -1 && limit>0) {
			query.setFirstResult(offset);
			if(offset < 0) offset = 0;
			query.setMaxResults(limit);
		}
		
		return query.list();
	}

	@Override
	public List<SatMan> getSatManDistinct(String where) {
		String dbWhere ="";
		if(where != "") dbWhere = " WHERE "+where;
		
		Query query = sessionFactory.getCurrentSession().createQuery("select DISTINCT satMan from SatManMK satManMK join "
				+ "satManMK.satMan satMan join satManMK.mk mk join mk.kurikulum kurikulum"+
				dbWhere);
		return query.list();
	}

}
