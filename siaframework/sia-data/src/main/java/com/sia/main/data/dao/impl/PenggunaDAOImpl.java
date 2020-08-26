package com.sia.main.data.dao.impl;

import java.util.List;
import java.util.UUID;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sia.main.data.dao.PenggunaDAO;
import com.sia.main.domain.Pengguna;

@Repository
@Transactional
public class PenggunaDAOImpl implements PenggunaDAO {

	private SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void insert(Pengguna object) {
		this.sessionFactory.getCurrentSession().save(object);
	}

	@Override
	public void update(Pengguna object) {
		this.sessionFactory.getCurrentSession().update(object);
	}

	@Override
	public void delete(Pengguna object) {
		this.sessionFactory.getCurrentSession().delete(object);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Pengguna> getAll() {
		return this.sessionFactory.getCurrentSession().createQuery("from Pengguna").list();
	}

	@Override
	public Pengguna getById(UUID idObject) {
		return (Pengguna) this.sessionFactory.getCurrentSession().get(Pengguna.class, idObject);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Pengguna> getByParam(String queryParam) {
		return this.sessionFactory.getCurrentSession().createQuery("from Pengguna " + queryParam).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Pengguna getByUsername(String username) {
		try {
			List<Pengguna> users = this.sessionFactory.getCurrentSession().createQuery("from Pengguna where username = '" + username + "'").list();
			return users.get(0);
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
