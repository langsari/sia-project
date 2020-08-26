package com.sia.main.data.dao.impl;

import java.util.List;
import java.util.UUID;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sia.main.data.dao.ModulDAO;
import com.sia.main.domain.Modul;

@Repository
@Transactional
public class ModulDAOImpl implements ModulDAO {

	private SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void insert(Modul object) {
		this.sessionFactory.getCurrentSession().save(object);
	}

	@Override
	public void update(Modul object) {
		this.sessionFactory.getCurrentSession().update(object);
	}

	@Override
	public void delete(Modul object) {
		this.sessionFactory.getCurrentSession().delete(object);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Modul> getAll() {
		return this.sessionFactory.getCurrentSession().createQuery("from Modul").list();
	}

	@Override
	public Modul getById(UUID idObject) {
		return (Modul) this.sessionFactory.getCurrentSession().get(Modul.class, idObject);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Modul> getByParam(String queryParam) {
		return this.sessionFactory.getCurrentSession().createQuery("from Modul " + queryParam).list();
	}
	
}
