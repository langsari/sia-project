package com.sia.main.data.dao.impl;

import java.util.List;
import java.util.UUID;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sia.main.data.dao.PeranPenggunaDAO;
import com.sia.main.domain.PeranPengguna;

@Repository
@Transactional
public class PeranPenggunaDAOImpl implements PeranPenggunaDAO {

	private SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void insert(PeranPengguna object) {
		this.sessionFactory.getCurrentSession().save(object);
	}

	@Override
	public void update(PeranPengguna object) {
		this.sessionFactory.getCurrentSession().update(object);
	}

	@Override
	public void delete(PeranPengguna object) {
		this.sessionFactory.getCurrentSession().delete(object);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PeranPengguna> getAll() {
		return this.sessionFactory.getCurrentSession().createQuery("from PeranPengguna").list();
	}

	@Override
	public PeranPengguna getById(UUID idObject) {
		return (PeranPengguna) this.sessionFactory.getCurrentSession().get(PeranPengguna.class, idObject);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PeranPengguna> getByParam(String queryParam) {
		return this.sessionFactory.getCurrentSession().createQuery("from PeranPengguna " + queryParam).list();
	}
	
}
