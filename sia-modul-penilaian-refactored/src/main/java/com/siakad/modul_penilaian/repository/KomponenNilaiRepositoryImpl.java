package com.siakad.modul_penilaian.repository;

import java.util.List;
import java.util.UUID;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sia.modul.domain.KomponenNilai;

@Transactional
@Repository
public class KomponenNilaiRepositoryImpl implements KomponenNilaiRepository {
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<KomponenNilai> get(String where, String order, int limit, int offset) {
		// TODO Auto-generated method stub
		String dbWhere = "";
		String dbOrder = "";
		if(where != "")
			dbWhere += " WHERE " + where;
		if(order != "")
			dbOrder += " ORDER BY " + order;
		Query query = sessionFactory.getCurrentSession().createQuery("FROM KomponenNilai" + dbWhere + dbOrder);
		return query.list();
	}
	
	@Override
	public List<KomponenNilai> leftJoin(UUID idPemb) {
		Query query = sessionFactory.getCurrentSession().createQuery("SELECT komp FROM KomponenNilai as komp LEFT JOIN komp.pemb WHERE id_pemb='" + idPemb +"'");
		return query.list();
	}

	@Override
	public UUID insert(KomponenNilai komp) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		UUID insertId = (UUID)session.save(komp);
		tx.commit();
		session.flush();
		session.close();
		return insertId;
	}

	@Override
	public void update(KomponenNilai komp) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(komp);
		tx.commit();
		session.flush();
		session.close();
	}

	@Override
	public void delete(UUID idKomp) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		String delete = "UPDATE KomponenNilai SET aKompAktif = FALSE WHERE idKomponen = :idKomp";
		Query query = session.createQuery(delete);
		query.setParameter("idKomp", idKomp);
		query.executeUpdate();
		tx.commit();
		session.flush();
		session.close();
	}
	
	@Override
	public double totalPresentase(UUID idPemb) {
		// TODO Auto-generated method stub
		Query query = sessionFactory.getCurrentSession().createQuery("SELECT sum(k.persentase_komponen) as res "
				+ "FROM komponen_nilai k WHERE id_pemb='" + idPemb + "'");
		List<Object[]> results = (List<Object[]>)query.list();
		for (Object[] result : results) {
			double res = (Double) result[0];
			System.out.println(res);
		}
		return 0;
	}

	@Override
	public KomponenNilai getById(UUID idKomp) {
		// TODO Auto-generated method stub
		return (KomponenNilai) sessionFactory.getCurrentSession().get(KomponenNilai.class, idKomp);
	}

}
