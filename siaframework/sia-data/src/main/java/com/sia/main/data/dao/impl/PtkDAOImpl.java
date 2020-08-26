package com.sia.main.data.dao.impl;

import java.util.List;
import java.util.UUID;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sia.main.data.dao.PtkDAO;
import com.sia.main.domain.Ptk;

@Repository
@Transactional
public class PtkDAOImpl implements PtkDAO {

	private SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void insert(Ptk object) {
		this.sessionFactory.getCurrentSession().save(object);
	}

	@Override
	public void update(Ptk object) {
		this.sessionFactory.getCurrentSession().update(object);
	}

	@Override
	public void delete(Ptk object) {
		this.sessionFactory.getCurrentSession().delete(object);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Ptk> getAll() {
		return this.sessionFactory.getCurrentSession().createQuery("from Ptk").list();
	}

	@Override
	public Ptk getById(UUID idObject) {
		return (Ptk) this.sessionFactory.getCurrentSession().get(Ptk.class, idObject);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Ptk> getByParam(String queryParam) {
		return this.sessionFactory.getCurrentSession().createQuery("from Ptk " + queryParam).list();
	}
	
}