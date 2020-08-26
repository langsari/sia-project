package com.sia.main.data.dao.impl;

import java.util.List;
import java.util.UUID;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sia.main.data.dao.MenuPeranDAO;
import com.sia.main.domain.MenuPeran;

@Repository
@Transactional
public class MenuPeranDAOImpl implements MenuPeranDAO {

	private SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void insert(MenuPeran object) {
		this.sessionFactory.getCurrentSession().save(object);
	}

	@Override
	public void update(MenuPeran object) {
		this.sessionFactory.getCurrentSession().update(object);
	}

	@Override
	public void delete(MenuPeran object) {
		this.sessionFactory.getCurrentSession().delete(object);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MenuPeran> getAll() {
		return this.sessionFactory.getCurrentSession().createQuery("from MenuPeran").list();
	}

	@Override
	public MenuPeran getById(UUID idObject) {
		return (MenuPeran) this.sessionFactory.getCurrentSession().get(MenuPeran.class, idObject);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MenuPeran> getByParam(String queryParam) {
		return this.sessionFactory.getCurrentSession().createQuery("from MenuPeran " + queryParam).list();
	}

}
