package com.siakad.modul_penilaian.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sia.modul.domain.Krs;
import com.sia.modul.domain.MK;

@Transactional
@Repository
public class KrsRepositoryImpl implements KrsRepository{
	@Autowired
	private SessionFactory sessionFactory;

	private final String kondisiKrsOke = "krs.aKrsBatal = FALSE AND krs.aKrsTerhapus = FALSE AND krs.aKrsDisetujui = TRUE";
	
	@Override
	public List<Krs> getByPemb(UUID idPemb) {
		// TODO Auto-generated method stub
		Query query = sessionFactory.getCurrentSession().createQuery("SELECT krs FROM Krs as krs LEFT JOIN krs.pd LEFT JOIN krs.konversiNilai WHERE id_pemb='" + idPemb + "' AND " + kondisiKrsOke);
		return query.list();
	}

	@Override
	public Krs getById(UUID idKrs) {
		// TODO Auto-generated method stub
		return (Krs) sessionFactory.getCurrentSession().get(Krs.class, idKrs);
	}

	@Override
	public void update(Krs krs) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(krs);
		tx.commit();
		session.flush();
		session.close();
	}
	
	@Override
	public List<Krs> getByTglSmt(UUID idTglSmt) {
		// TODO Auto-generated method stub
		Query query = sessionFactory.getCurrentSession().createQuery("SELECT krs FROM Krs krs WHERE krs.pemb.tglSmt.idTglSmt = '" + idTglSmt + "' AND " + kondisiKrsOke);
		return query.list();
	}

	@Override
	public List<Krs> getAktifByPd(UUID idPd, UUID idTglSmt) {
		// TODO Auto-generated method stub
		Query query = sessionFactory.getCurrentSession().createQuery("SELECT krs FROM Krs krs WHERE krs.pd.idPd = '" + idPd + "' AND krs.pemb.tglSmt.idTglSmt = '" + idTglSmt + "' AND " + kondisiKrsOke);
		return query.list();
	}

	@Override
	public List<Krs> getAllByPd(UUID idPd, String specialCond) {
		// TODO Auto-generated method stub
		Query query = sessionFactory.getCurrentSession().createQuery("SELECT krs FROM Krs krs WHERE krs.pd.idPd = '" + idPd + "' AND " + kondisiKrsOke + specialCond);
		return query.list();
	}

	@Override
	public List<Krs> getTerakhirByPd(UUID idPd) {
		// TODO Auto-generated method stub
		//String queryString = "SELECT krs FROM Krs krs WHERE krs.pd.idPd = '" + idPd + "' GROUP BY krs.pd, krs.pemb.mk)";
		String queryString = "SELECT krs.pemb.mk FROM Krs krs WHERE krs.pd.idPd = '" + idPd + "' AND " + kondisiKrsOke;
		Query query = sessionFactory.getCurrentSession().createQuery(queryString);
		List<MK> listMk = (List<MK>) query.list();
		
		List<Krs> listKrs = new ArrayList<Krs>();
		for (MK mk : listMk) {
			System.out.println(mk.getNamaMK());
			queryString = "SELECT krs FROM Krs krs WHERE krs.pd.idPd = '" + idPd + "' AND krs.pemb.mk.idMK = '" + mk.getIdMK() + "' AND " + kondisiKrsOke + " ORDER BY krs.waktuAmbil DESC";
			query = sessionFactory.getCurrentSession().createQuery(queryString);
			if(!query.list().isEmpty()) {
				Krs krs = (Krs) query.list().get(0);
				listKrs.add(krs);
			}
		}
		return listKrs;
	}
	
}
