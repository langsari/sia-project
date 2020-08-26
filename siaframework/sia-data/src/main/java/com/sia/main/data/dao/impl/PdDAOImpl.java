package com.sia.main.data.dao.impl;

import java.util.List;
import java.util.UUID;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sia.main.data.dao.PdDAO;
import com.sia.main.domain.Pd;

@Repository
@Transactional
public class PdDAOImpl implements PdDAO {

	private SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void insert(Pd object) {
		this.sessionFactory.getCurrentSession().save(object);
	}

	@Override
	public void update(Pd object) {
		this.sessionFactory.getCurrentSession().update(object);
	}

	@Override
	public void delete(Pd object) {
		this.sessionFactory.getCurrentSession().delete(object);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Pd> getAll() {
		return this.sessionFactory.getCurrentSession().createQuery("from Pd").list();
	}

	@Override
	public Pd getById(UUID idObject) {
		return (Pd) this.sessionFactory.getCurrentSession().get(Pd.class, idObject);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Pd> getByParam(String queryParam) {
		return this.sessionFactory.getCurrentSession().createQuery("from Pd " + queryParam).list();
	}

}
