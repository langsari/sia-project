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

import com.sia.main.domain.PeranPengguna;

@Transactional
@Repository
public class PeranPenggunaRepositoryImpl implements PeranPenggunaRepository {
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<PeranPengguna> get(String where, String order, int limit, int offset) {
		String dbWhere ="";
		String dbOrder ="";
		if(where != "") dbWhere = " WHERE "+where;
		if(order != "") dbOrder = " ORDER BY "+order;
		
		Query query = sessionFactory.getCurrentSession().createQuery("select peranPengguna from PeranPengguna "
				+ "peranPengguna join peranPengguna.pengguna pengguna "
				+ " left join pengguna.pd pd left join pengguna.ptk ptk "
				+ " join peranPengguna.peran peran join peranPengguna.satMan satMan "
				+dbWhere+dbOrder);
		
		if(limit != -1 && limit>0) {
			query.setFirstResult(offset);
			if(offset < 0) offset = 0;
			query.setMaxResults(limit);
		}
		
		return query.list();
	}

	@Override
	public PeranPengguna getById(UUID idAnggotaRombel) {
		List<PeranPengguna> queryResult = sessionFactory.getCurrentSession().createQuery("from PeranPengguna "
				+ "WHERE idPeranPengguna = '"+idAnggotaRombel.toString()+"'").list();
		if(queryResult.size()==0) return null;
		return queryResult.get(0);
	}

	@Override
	public UUID insert(PeranPengguna peranPengguna) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		UUID insertId= (UUID)session.save(peranPengguna);
		tx.commit();
		session.flush();
		session.close();
		return insertId;
	}

	@Override
	public void update(PeranPengguna peranPengguna) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(peranPengguna);
		tx.commit();
		session.flush();
		session.close();
	}

	@Override
	public void delete(PeranPengguna peranPengguna) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.delete(peranPengguna);
		tx.commit();
		session.flush();
		session.close();
	}

	@Override
	public long count(String where) {
		String dbWhere ="";
		if(where != "") dbWhere = " WHERE "+where;
		Query query = sessionFactory.getCurrentSession().createQuery(
				"select count(*) from PeranPengguna "
				+ "peranPengguna join peranPengguna.pengguna pengguna "
				+ " left join pengguna.pd pd left join pengguna.ptk ptk"
				+ "join peranPengguna.peran peran join peranPengguna.satMan satMan "
				+ dbWhere);
		Long count = (Long)query.uniqueResult();
		return count;
	}

	@Override
	public PeranPengguna getByPenggunaPeran(UUID idPengguna, String namaPeran) {
		List<PeranPengguna> queryResult = sessionFactory.getCurrentSession().createQuery("select peranPengguna from PeranPengguna "
				+ "peranPengguna join peranPengguna.pengguna pengguna "
				+ " left join pengguna.pd pd left join pengguna.ptk ptk "
				+ " join peranPengguna.peran peran join peranPengguna.satMan satMan "
				+ "WHERE pengguna.idPengguna = '"+idPengguna.toString()+"' and peran.namaPeran ='"+namaPeran+"'").list();
		if(queryResult.size()==0) return null;
		return queryResult.get(0);
	}

}
