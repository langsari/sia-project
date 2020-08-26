package com.sia.main.data.dao.impl;

import java.util.List;
import java.util.UUID;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sia.main.data.dao.TipePenggunaDAO;
import com.sia.main.domain.TipePengguna;

@Repository
@Transactional
public class TipePenggunaDAOImpl implements TipePenggunaDAO {

	private SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void insert(TipePengguna object) {
		this.sessionFactory.getCurrentSession().save(object);
	}

	@Override
	public void update(TipePengguna object) {
		this.sessionFactory.getCurrentSession().update(object);
	}

	@Override
	public void delete(TipePengguna object) {
		this.sessionFactory.getCurrentSession().delete(object);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TipePengguna> getAll() {
		return this.sessionFactory.getCurrentSession().createQuery("from TipePengguna").list();
	}

	@Override
	public TipePengguna getById(UUID idObject) {
		return (TipePengguna) this.sessionFactory.getCurrentSession().get(TipePengguna.class, idObject);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TipePengguna> getByParam(String queryParam) {
		return this.sessionFactory.getCurrentSession().createQuery("from TipePengguna " + queryParam).list();
	}
	
}