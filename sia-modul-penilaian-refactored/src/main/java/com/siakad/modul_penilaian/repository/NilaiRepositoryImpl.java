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

import com.sia.modul.domain.Nilai;

@Transactional
@Repository
public class NilaiRepositoryImpl implements NilaiRepository {
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void insertBulk(List<Nilai> listNilai) {
		// TODO Auto-generated method stub
		for (Nilai nilai : listNilai) {
			nilai = getId(nilai);
			if(nilai.getIdNilai() != null) {
				update(nilai);
			}
			else {
				insert(nilai);
			}
		}
	}

	@Override
	public UUID insert(Nilai nilai) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		UUID insertId = (UUID) session.save(nilai);
		tx.commit();
		session.flush();
		session.close();
		return insertId;
	}

	@Override
	public void update(Nilai nilai) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(nilai);
		tx.commit();
		session.flush();
		session.close();
	}

	@Override
	public Nilai getId(Nilai nilai) {
		// TODO Auto-generated method stub
		Query query = sessionFactory.getCurrentSession().createQuery("FROM Nilai WHERE id_krs = '" + nilai.getKrs().getIdKrs() 
				+ "' AND id_komponen = '" + nilai.getKomponenNilai().getIdKomponen() + "'");
		if(query.list().isEmpty())
			return nilai;
		else {
			Nilai existingNilai = (Nilai) query.list().get(0);
			existingNilai.setNilai(nilai.getNilai());
			return existingNilai;
		}
	}

	@Override
	public List<Nilai> getByKrs(List<UUID> listIdKrs) {
		// TODO Auto-generated method stub
		List<Nilai> listNilai = new ArrayList<Nilai>();
		for (UUID idKrs : listIdKrs) {
			Query query = sessionFactory.getCurrentSession().createQuery("FROM Nilai WHERE id_krs = '" + idKrs + "'");
			listNilai.addAll(query.list());
		}
		
		return listNilai;
	}

	@Override
	public double getNilaiAkhir(UUID idKrs) {
		// TODO Auto-generated method stub
		Query query = sessionFactory.getCurrentSession().createQuery("SELECT nilai FROM Nilai as nilai LEFT JOIN nilai.komponenNilai WHERE id_krs='" + idKrs + "' AND a_komp_aktif = TRUE");
		List<Nilai> listNilai = query.list();
		double result = 0;
		for (Nilai nilai : listNilai) {
			result += (nilai.getNilai()*nilai.getKomponenNilai().getPersentaseKomponen())/100;
		}
		return result;
	}

}
