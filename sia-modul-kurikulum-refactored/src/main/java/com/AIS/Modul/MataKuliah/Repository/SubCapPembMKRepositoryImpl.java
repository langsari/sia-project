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

import com.sia.modul.domain.CapPemb;
import com.sia.modul.domain.SubCapPembMK;

@Transactional
@Repository
public class SubCapPembMKRepositoryImpl implements SubCapPembMKRepository {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SubCapPembMK> get(String where, String order, int limit, int offset) {
		// TODO Auto-generated method stub
		String dbWhere ="";
		String dbOrder ="";
		if(where != "") dbWhere = " WHERE "+where;
		if(order != "") dbOrder = " ORDER BY "+order;
		 
		Query query = sessionFactory.getCurrentSession().createQuery("select scpmk from SubCapPembMK scpmk " 
				+ "join scpmk.capPemb cp "
				+ "join scpmk.capPembMK cpmk "
				+ "join cpmk.mk mk " 
		        + "join cp.satMan satman " 
		        +dbWhere+dbOrder);
		if(limit != -1 && limit>0) {
			query.setFirstResult(offset);
			if(offset < 0) offset = 0;
			query.setMaxResults(limit);
		}
		
		return query.list();
	}
	
	@Override
	public void update(SubCapPembMK subCapPembMK) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(subCapPembMK);
		tx.commit();
		session.flush();
		session.close();
	}

	@Override
	public UUID insert(SubCapPembMK subCapPembMK) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		UUID insertId= (UUID)session.save(subCapPembMK);
		tx.commit();
		session.flush();
		session.close();
		return insertId;
	}

	@Override
	public CapPemb findByCapPembMK(UUID idCapPembMK) {
		// TODO Auto-generated method stub
		List<SubCapPembMK> scpMKList = findAll();
		for (SubCapPembMK scpmk : scpMKList) {
			if( scpmk.getCapPembMK().getIdCapPembMK().equals(idCapPembMK)){
				return scpmk.getCapPemb();
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	private List<SubCapPembMK> findAll() {
		// TODO Auto-generated method stub
		return sessionFactory.getCurrentSession().createQuery("select scpmk from SubCapPembMK scpmk WHERE scpmk.statusSubCapPembMK = false").list();

	}

	@Override
	public long count(String where) {
		// TODO Auto-generated method stub
		String dbWhere ="";
		if(where != "") dbWhere = " WHERE " +where;
		Query query = sessionFactory.getCurrentSession().createQuery(
		        "select count(*) from SubCapPembMK scpmk "
				+ "join scpmk.capPemb cp "
				+ "join scpmk.capPembMK cpmk "
				+ "join cpmk.mk mk " 
		        + "join cp.satMan satman "  +dbWhere);
		Long count = (Long)query.uniqueResult();
		return count;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SubCapPembMK> findCapPemb(String idCapPembMK) {
		// TODO Auto-generated method stub
		List<SubCapPembMK> queryResult = sessionFactory.getCurrentSession().createQuery( "select scpmk from SubCapPembMK scpmk "
				+ "join scpmk.capPemb cp "
				+ "join scpmk.capPembMK cpmk "
				+ "join cpmk.mk mk " 
		        + "join cp.satMan satman WHERE cpmk.idCapPembMK='"+ idCapPembMK+"'").list(); 
		
		if(queryResult.size()==0) return null;
		else {
			return queryResult;
		}
	}

	@Override
	public void delete(UUID idSubCapPembMK) {
		// TODO Auto-generated method stub
		SubCapPembMK scpMKObj = findById(idSubCapPembMK);
		if(scpMKObj!=null){ 
			sessionFactory.getCurrentSession().delete(scpMKObj);
        	sessionFactory.getCurrentSession().flush();
		} 
        System.out.println("data sudah terhapus");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public SubCapPembMK findById(UUID idSubCapPembMK) {
		// TODO Auto-generated method stub
		List<SubCapPembMK> queryResult = sessionFactory.getCurrentSession().createQuery("select scpmk from SubCapPembMK scpmk "
				+ "join scpmk.capPemb cp "
				+ "join scpmk.capPembMK cpmk "
				+ "join cpmk.mk mk " 
		        + "join cp.satMan satman WHERE scpmk.idSubCapPembMK='"+ idSubCapPembMK.toString() +"'").list();
		if(queryResult.size()==0) return null;
		return queryResult.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SubCapPembMK> findByCapPembMKList(UUID idCapPembMK) {
		// TODO Auto-generated method stub
		List<SubCapPembMK> queryResult = sessionFactory.getCurrentSession().createQuery("select scpmk from SubCapPembMK scpmk " 
				+ "join scpmk.capPembMK cpmk WHERE cpmk.idCapPembMK='"+ idCapPembMK.toString() +"' "
						+ "AND scpmk.statusSubCapPembMK = false").list();
		if(queryResult.size()==0) return null;
		return queryResult;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CapPemb> findByMK(UUID idMK) {
		// TODO Auto-generated method stub
		List<CapPemb> queryResult = sessionFactory.getCurrentSession().createQuery("select distinct scpmk.capPemb "
				+ "from SubCapPembMK scpmk "  
				+ "WHERE scpmk.capPembMK.mk.idMK='"+ idMK.toString() +"' "
				+ "AND scpmk.statusSubCapPembMK = false").list();
		for(CapPemb cp : queryResult){
			System.out.println(cp.getDeskripsiCapPemb());
		}
		if(queryResult.size()==0) return null;
		return queryResult;
	}
 
}
