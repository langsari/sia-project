package com.sia.main.data.dao;

import java.util.List;
import java.util.UUID;

public interface GenericDAO<T> {
	
	public void insert(T object);

	public void update(T object);

	public void delete(T object);

	public List<T> getAll();

	public T getById(UUID idObject);
	
	public List<T> getByParam(String queryParam);

}
