package com.sia.main.service.services;

import java.util.List;
import java.util.UUID;

import com.sia.main.domain.Peran;

public interface PeranService {

	public Peran insertInto(Peran peran);

	public Peran update(Peran peran);

	public Peran delete(Peran peran);

	public List<Peran> getAll();

	public Peran getById(UUID idPeran);
	
	public List<Peran> getPeranWithParam(String param);
	
}
