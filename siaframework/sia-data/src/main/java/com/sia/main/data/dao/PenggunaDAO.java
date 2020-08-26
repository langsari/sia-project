package com.sia.main.data.dao;

import com.sia.main.domain.Pengguna;

public interface PenggunaDAO  extends GenericDAO<Pengguna> {

	public Pengguna getByUsername(String username);
	
}
