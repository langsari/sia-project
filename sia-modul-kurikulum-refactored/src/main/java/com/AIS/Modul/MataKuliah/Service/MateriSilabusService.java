package com.AIS.Modul.MataKuliah.Service;

import java.util.List;
import java.util.UUID;
 


import com.sia.modul.domain.MateriSilabus;

public interface MateriSilabusService {

	public String save(MateriSilabus mp);

	public MateriSilabus findById(UUID idMateriSilabus);

	public String delete(UUID idMateriSilabus);

	public List<MateriSilabus> findByRPPerTemu(UUID idRPPerTemu);

}
