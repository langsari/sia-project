package com.sia.main.data.dao.impl;

import java.util.List;
import java.util.UUID;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sia.main.data.dao.SatuanManajemenDAO;
import com.sia.main.domain.SatMan;

@Repository
@Transactional
public class SatuanManajemenDAOImpl implements SatuanManajemenDAO {

	private SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void insert(SatMan object) {
		this.sessionFactory.getCurrentSession().save(object);
	}

	@Override
	public void update(SatMan object) {
		this.sessionFactory.getCurrentSession().update(object);
	}

	@Override
	public void delete(SatMan object) {
		this.sessionFactory.getCurrentSession().delete(object);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SatMan> getAll() {
		return this.sessionFactory.getCurrentSession().createQuery("from SatMan").list();
	}

	@Override
	public SatMan getById(UUID idObject) {
		return (SatMan) this.sessionFactory.getCurrentSession().get(SatMan.class, idObject);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SatMan> getByParam(String queryParam) {
		return this.sessionFactory.getCurrentSession().createQuery("from SatMan " + queryParam).list();
	}
	
}