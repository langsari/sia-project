package com.sia.main.data.dao.impl;

import java.util.List;
import java.util.UUID;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sia.main.data.dao.PeranDAO;
import com.sia.main.domain.Peran;

@Repository
@Transactional
public class PeranDAOImpl implements PeranDAO {

	private SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void insert(Peran object) {
		this.sessionFactory.getCurrentSession().save(object);
	}

	@Override
	public void update(Peran object) {
		this.sessionFactory.getCurrentSession().update(object);
	}

	@Override
	public void delete(Peran object) {
		this.sessionFactory.getCurrentSession().delete(object);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Peran> getAll() {
		return this.sessionFactory.getCurrentSession().createQuery("from Peran").list();
	}

	@Override
	public Peran getById(UUID idObject) {
		return (Peran) this.sessionFactory.getCurrentSession().get(Peran.class, idObject);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Peran> getByParam(String queryParam) {
		return this.sessionFactory.getCurrentSession().createQuery("from Peran " + queryParam).list();
	}
	
}
