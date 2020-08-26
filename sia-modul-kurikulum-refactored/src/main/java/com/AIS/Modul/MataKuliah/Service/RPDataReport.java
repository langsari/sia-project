package com.AIS.Modul.MataKuliah.Service;

import java.util.List;

import com.sia.modul.domain.DetailSilabus;

public class RPDataReport {
	private List<DetailSilabus> pokokBahasanReport;
	
	private List<RPDetailReport> rpDetailReport;

	public List<DetailSilabus> getPokokBahasanReport() {
		return pokokBahasanReport;
	}

	public void setPokokBahasanReport(List<DetailSilabus> pokokBahasanReport) {
		this.pokokBahasanReport = pokokBahasanReport;
	}

	public List<RPDetailReport> getRpDetailReport() {
		return rpDetailReport;
	}

	public void setRpDetailReport(List<RPDetailReport> rpDetailReport) {
		this.rpDetailReport = rpDetailReport;
	}
	
	
}
