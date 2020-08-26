package com.its.sia.service;

import java.util.List;

import com.sia.modul.domain.AturanPengganti;
import com.sia.modul.domain.BatasAmbilSks;
import com.sia.modul.domain.Ipk;
import com.sia.modul.domain.Ips;
import com.sia.modul.domain.Krs;
import com.sia.modul.domain.Pemb;
import com.sia.modul.domain.SatMan;
import com.sia.modul.domain.SatManMK;
import com.sia.modul.domain.TglSmt;

public class KrsTransaction {
	private List<Krs> listKrs;
	private List<SatManMK> listSatManMK;

	public KrsTransaction(){
		
	}

	public List<Krs> getListKrs() {
		return listKrs;
	}
	public void setListKrs(List<Krs> listKrs) {
		this.listKrs = listKrs;
	}

	public List<SatManMK> getListSatManMK() {
		return listSatManMK;
	}

	public void setListSatManMK(List<SatManMK> listSatManMK) {
		this.listSatManMK = listSatManMK;
	}
}
