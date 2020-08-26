package com.AIS.Modul.MataKuliah.Service;

import java.util.List;

import com.sia.modul.domain.BentukPenilaian;
import com.sia.modul.domain.CapPembMK;
import com.sia.modul.domain.DetailSilabus; 
import com.sia.modul.domain.MetodePemb;
import com.sia.modul.domain.RPPerTemu;

public class RPDetailReport {

	private Integer mingguKe;
	
	private List<CapPembMK> capPembMKReport;

	private List<DetailSilabus> detailSilabusReport;
	
	private List<MetodePemb> metodePembReport;
	 
	private List<BentukPenilaian> bentukPenilaianReport;
	
	private String indikatorPenilaian;
	 
	private Double bobotPenilaian;
 
	

	public Integer getMingguKe() {
		return mingguKe;
	}

	public void setMingguKe(Integer mingguKe) {
		this.mingguKe = mingguKe;
	}

	public List<CapPembMK> getCapPembMKReport() {
		return capPembMKReport;
	}

	public void setCapPembMKReport(List<CapPembMK> capPembMKReport) {
		this.capPembMKReport = capPembMKReport;
	}

	public List<DetailSilabus> getDetailSilabusReport() {
		return detailSilabusReport;
	}

	public void setDetailSilabusReport(List<DetailSilabus> detailSilabusReport) {
		this.detailSilabusReport = detailSilabusReport;
	}

	public List<MetodePemb> getMetodePembReport() {
		return metodePembReport;
	}

	public void setMetodePembReport(List<MetodePemb> metodePembReport) {
		this.metodePembReport = metodePembReport;
	}

	public List<BentukPenilaian> getBentukPenilaianReport() {
		return bentukPenilaianReport;
	}

	public void setBentukPenilaianReport(List<BentukPenilaian> bentukPenilaianReport) {
		this.bentukPenilaianReport = bentukPenilaianReport;
	}

	public String getIndikatorPenilaian() {
		return indikatorPenilaian;
	}

	public void setIndikatorPenilaian(String indikatorPenilaianReport) {
		this.indikatorPenilaian = indikatorPenilaianReport;
	}

	public Double getBobotPenilaian() {
		return bobotPenilaian;
	}

	public void setBobotPenilaian(Double bobotPenilaianReport) {
		this.bobotPenilaian = bobotPenilaianReport;
	}
	
	
}
