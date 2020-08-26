package com.siakad.modul_penilaian.repository;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sia.modul.domain.TglSmt;

@Transactional
@Repository
public class TglSmtRepositoryImpl implements TglSmtRepository {
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public TglSmt getAktif() {
		// TODO Auto-generated method stub
		Query query = sessionFactory.getCurrentSession().createQuery("FROM TglSmt WHERE aTglSmtAktif = TRUE AND aTglSmtTerhapus = FALSE");
		return (TglSmt) query.list().get(0);
	}

	@Override
	public List<TglSmt> getAll() {
		// TODO Auto-generated method stub
		Query query = sessionFactory.getCurrentSession().createQuery("FROM TglSmt WHERE aTglSmtTerhapus = FALSE ORDER BY tglAwalSusunKrs DESC");
		return query.list();
	}

}
