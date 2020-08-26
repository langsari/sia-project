package com.sia.main.data.dao.impl;

import java.util.List;
import java.util.UUID;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sia.main.data.dao.MenuDAO;
import com.sia.main.domain.Menu;

@Repository
@Transactional
public class MenuDAOImpl implements MenuDAO {

	private SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public void insert(Menu object) {
		this.sessionFactory.getCurrentSession().save(object);
	}

	@Override
	public void update(Menu object) {
		this.sessionFactory.getCurrentSession().update(object);
	}

	@Override
	public void delete(Menu object) {
		this.sessionFactory.getCurrentSession().delete(object);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Menu> getAll() {
		return this.sessionFactory.getCurrentSession().createQuery("from Menu").list();
	}

	@Override
	public Menu getById(UUID idObject) {
		return (Menu) this.sessionFactory.getCurrentSession().get(Menu.class, idObject);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Menu> getByParam(String queryParam) {
		return this.sessionFactory.getCurrentSession().createQuery("from Menu " + queryParam).list();
	}

}
