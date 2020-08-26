package com.bustan.siakad.repository;

import java.math.BigInteger;
import java.util.List;
import java.util.UUID;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sia.modul.domain.RelasiEkuivalensi;

@Repository
@Transactional
public class RelasiEkuivalensiRepositoryImpl implements RelasiEkuivalensiRepository{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public List<RelasiEkuivalensi> get(String where, String order, String groupby, int limit, int offset) {
		String dbWhere ="";
		String dbOrder ="";
		String dbGroupBy = "";
		if(where != "") dbWhere = " WHERE "+where;
		if(order != "") dbOrder = " ORDER BY "+order;
		if(groupby != "") dbGroupBy = " GROUP BY "+groupby;
				
		Query query = sessionFactory.getCurrentSession().createQuery("select relasiEkuivalensi from RelasiEkuivalensi relasiEkuivalensi left join "
				+ "relasiEkuivalensi.kurikulumLama kurikulumLama left join relasiEkuivalensi.kurikulumBaru kurikulumBaru"+
		dbWhere+dbGroupBy+dbOrder);
		
		if(limit != -1 && limit>0) {
			query.setFirstResult(offset);
			if(offset < 0) offset = 0;
			query.setMaxResults(limit);
		}
		
		return query.list();
	}
	
	@Override
	public List<Object[]> getA(String where, String order, int limit, int offset) {
		String dbWhere ="";
		String dbOrder ="";
		String dbGroupBy = "";
		if(where != "") dbWhere = " WHERE "+where;
		if(order != "") dbOrder = " ORDER BY "+order;
				
		Query query = sessionFactory.getCurrentSession().createQuery("select DISTINCT kurikulumLama, kurikulumBaru from RelasiEkuivalensi relasiEkuivalensi left join "
				+ "relasiEkuivalensi.kurikulumLama kurikulumLama left join relasiEkuivalensi.kurikulumBaru kurikulumBaru left join "
				+ "kurikulumLama.satMan satManLama left join kurikulumBaru.satMan satManBaru"+
		dbWhere+dbOrder);
		
		if(limit != -1 && limit>0) {
			query.setFirstResult(offset);
			if(offset < 0) offset = 0;
			query.setMaxResults(limit);
		}
		
		return query.list();
	}

	@Override
	public RelasiEkuivalensi getById(UUID idRelasiEkuivalensi) {
		List<RelasiEkuivalensi> queryResult = sessionFactory.getCurrentSession().createQuery("select relasiEkuivalensi from RelasiEkuivalensi relasiEkuivalensi WHERE "
				+ "relasiEkuivalensi.idRelasiEkuivalensi = '"+idRelasiEkuivalensi.toString()+"'").list();
		if(queryResult.size()==0) return null;
		else
		{
			return queryResult.get(0);
		}
	}

	@Override
	public UUID insert(RelasiEkuivalensi relasiEkuivalensi) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		UUID insertId= (UUID)session.save(relasiEkuivalensi);
		tx.commit();
		session.flush();
		session.close();
		return insertId;
	}

	@Override
	public void update(RelasiEkuivalensi relasiEkuivalensi) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(relasiEkuivalensi);
		tx.commit();
		session.flush();
		session.close();
	}

	@Override
	public void delete(RelasiEkuivalensi relasiEkuivalensi) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.delete(relasiEkuivalensi);
		tx.commit();
		session.flush();
		session.close();
	}

	@Override
	public long count(String where) {
		String dbWhere ="";
		if(where != "") dbWhere = " WHERE "+where;
		Query query = sessionFactory.getCurrentSession().createQuery(
//		        "select count(DISTINCT id_kurikulum_baru) from RelasiEkuivalensi relasiEkuivalensi left join "
//		        + "relasiEkuivalensi.kurikulumLama kurikulumLama left join relasiEkuivalensi.kurikulumBaru kurikulumBaru"+dbWhere);
				"select count(*) from RelasiEkuivalensi relasiEkuivalensi left join "
				+ "relasiEkuivalensi.kurikulumLama kurikulumLama left join relasiEkuivalensi.kurikulumBaru kurikulumBaru "
				+ "left join kurikulumLama.satMan satManLama left join kurikulumBaru.satMan satManBaru"+dbWhere+" GROUP BY id_kurikulum_lama, id_kurikulum_baru)");
//		String sQuery = "select count(*) from (select id_kurikulum_lama, id_kurikulum_baru from relasi_ekuivalensi "+dbWhere+" GROUP BY id_kurikulum_lama, id_kurikulum_baru) as a";
//		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sQuery);
//		List<BigInteger> list = query.list();
		
		Long count = new Long(query.list().size());
		return count;
	}

	@Override
	public List<Object[]> getUUID(String where, String order, int limit, int offset) {
		String dbWhere ="";
		String dbOrder ="";
		if(where != "") dbWhere = " WHERE "+where;
		if(order != "") dbOrder = " ORDER BY "+order;
				
		Query query = sessionFactory.getCurrentSession().createQuery("select distinct idKurikulumLama, idKurikulumBaru FROM RelasiEkuivalensi"+
		dbWhere+dbOrder);
		
		if(limit != -1 && limit>0) {
			query.setFirstResult(offset);
			if(offset < 0) offset = 0;
			query.setMaxResults(limit);
		}
		
		return query.list();
	}

}
