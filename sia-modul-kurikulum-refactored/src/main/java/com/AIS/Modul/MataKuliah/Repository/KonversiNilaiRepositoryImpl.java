package com.AIS.Modul.MataKuliah.Repository;

import java.util.List;
import java.util.UUID;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.sia.modul.domain.KonversiNilai;

@Transactional
@Repository
public class KonversiNilaiRepositoryImpl implements KonversiNilaiRepository{

	@Autowired
	private SessionFactory sessionFactory;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<KonversiNilai> findAll() {
		// TODO Auto-generated method stub
		return sessionFactory.getCurrentSession().createQuery("from KonversiNilai where aStatusKonversiAktif = true").list();
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public KonversiNilai findById(UUID idKonversi) {
		// TODO Auto-generated method stub
		List<KonversiNilai> queryResult = sessionFactory.getCurrentSession().createQuery("from KonversiNilai where idKonversi = '"+idKonversi.toString()+"'").list();
		if(queryResult.size()==0) return null;
		return queryResult.get(0);
	}

}
