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

import com.sia.modul.domain.SubCapPemb;

@Transactional
@Repository
public class SubCapPembRepositoryImpl implements SubCapPembRepository {

	@Autowired
	private SessionFactory sessionFactory;
	
	 
	@Override
	public long count(String where) {
		// TODO Auto-generated method stub
		String dbWhere ="";
		if(where != "") dbWhere = " WHERE " +where;
		Query query = sessionFactory.getCurrentSession().createQuery(
		        "select count(*) from SubCapPemb scp " 
				+ "inner join scp.childCapPemb child "
		        + "left join scp.parentCapPemb parent "
				+ "inner join child.kurikulum kur "
		        + "inner join child.satMan satman " +dbWhere);
		Long count = (Long)query.uniqueResult();
		return count;
	}
 
	@SuppressWarnings("unchecked")
	@Override
	public List<SubCapPemb> get(String where, String order, int limit, int offset) {
		// TODO Auto-generated method stub
		String dbWhere ="";
		String dbOrder ="";
		if(where != "") dbWhere = " WHERE "+where;
		if(order != "") dbOrder = " ORDER BY "+order;
		 
		Query query = sessionFactory.getCurrentSession().createQuery("select scp from SubCapPemb scp " 
				+ "inner join scp.childCapPemb child "
		        + "left join scp.parentCapPemb parent "
				+ "inner join child.kurikulum kur "
		        + "inner join child.satMan satman "
		        +dbWhere+dbOrder);
		if(limit != -1 && limit>0) {
			query.setFirstResult(offset);
			if(offset < 0) offset = 0;
			query.setMaxResults(limit);
		}
		
		return query.list();
	}

	@Override
	public void update(SubCapPemb subCapPemb) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(subCapPemb);
		tx.commit();
		session.flush();
		session.close();
	}

	@Override
	public UUID insert(SubCapPemb subCapPemb) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		UUID insertId= (UUID)session.save(subCapPemb);
		tx.commit();
		session.flush();
		session.close();
		return insertId;
	}
 
	@SuppressWarnings("unchecked")
	@Override
	public SubCapPemb findById(UUID idSubCapPemb) {
		// TODO Auto-generated method stub
		List<SubCapPemb> queryResult = sessionFactory.getCurrentSession().createQuery("select scp from SubCapPemb scp " 
				+ "inner join scp.childCapPemb child "
		        + "left join scp.parentCapPemb parent "
				+ "inner join child.kurikulum kur "
		        + "inner join child.satMan satman  WHERE  scp.idSubCapPemb='"+idSubCapPemb.toString()+"'").list();
		if(queryResult.size()==0) return null;
		return queryResult.get(0);
	} 
	@SuppressWarnings("unchecked")
	@Override
	public List<SubCapPemb> findAll() {
		// TODO Auto-generated method stub
		return sessionFactory.getCurrentSession().createQuery("select scp from SubCapPemb scp WHERE scp.statusSubCapPemb = false").list();

	}
	
	@SuppressWarnings("unchecked")
	public List<SubCapPemb> findParent(String idCapPemb) {
		// TODO Auto-generated method stub
		List<SubCapPemb> queryResult = sessionFactory.getCurrentSession().createQuery("select scp from SubCapPemb scp " 
				+ "inner join scp.childCapPemb child "
		        + "left join scp.parentCapPemb parent "
				+ "inner join child.kurikulum kur "
		        + "inner join child.satMan satman  WHERE child.idCapPemb = '"+ idCapPemb.toString()+"'").list();
		if(queryResult.size()==0) return null;
		return queryResult;
	}
 

	@Override
	public void delete(UUID idSubCapPemb) {
		// TODO Auto-generated method stub
		SubCapPemb scpObj = findById(idSubCapPemb);
		if(scpObj!=null){ 
			sessionFactory.getCurrentSession().delete(scpObj);
        	sessionFactory.getCurrentSession().flush();
		} 
        System.out.println("sub capaian sudah dihapus dari repository");
	} 
}
