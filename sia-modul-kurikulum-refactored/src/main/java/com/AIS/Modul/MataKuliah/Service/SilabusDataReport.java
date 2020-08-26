package com.AIS.Modul.MataKuliah.Service;

import java.util.List;

import com.sia.modul.domain.CapPemb;
import com.sia.modul.domain.DetailSilabus;
import com.sia.modul.domain.MK;
import com.sia.modul.domain.Pustaka;
import com.sia.modul.domain.RumpunMK;
import com.sia.modul.domain.SatManMK;

public class SilabusDataReport {
	   
	private List<CapPembMKTemporary> capPembMKReport;
	
	private List<CapPemb> capPembReport;
	
	private List<DetailSilabus> pokokBahasanReport;
	
	private List<MK> prasyaratMKReport;
	
	private List<Pustaka> pustakaUtamaReport;
	
	private List<Pustaka> pustakaPendukungReport; 
	
	private List<SatManMKTemporary> satManMKReport; 

	private RumpunMK rumpunMK; 
	
	public List<CapPembMKTemporary> getCapPembMKReport() {
		return capPembMKReport;
	}

	public void setCapPembMKReport(List<CapPembMKTemporary> capPembMKReport) {
		this.capPembMKReport = capPembMKReport;
	}

	public List<CapPemb> getCapPembReport() {
		return capPembReport;
	}

	public void setCapPembReport(List<CapPemb> capPembReport) {
		this.capPembReport = capPembReport;
	}

	public List<DetailSilabus> getPokokBahasanReport() {
		return pokokBahasanReport;
	}

	public void setPokokBahasanReport(List<DetailSilabus> pokokBahasanReport) {
		this.pokokBahasanReport = pokokBahasanReport;
	}

	public List<MK> getPrasyaratMKReport() {
		return prasyaratMKReport;
	}

	public void setPrasyaratMKReport(List<MK> prasyaratMKReport) {
		this.prasyaratMKReport = prasyaratMKReport;
	}

	public List<Pustaka> getPustakaUtamaReport() {
		return pustakaUtamaReport;
	}

	public void setPustakaUtamaReport(List<Pustaka> pustakaUtamaReport) {
		this.pustakaUtamaReport = pustakaUtamaReport;
	}

	public List<Pustaka> getPustakaPendukungReport() {
		return pustakaPendukungReport;
	}

	public void setPustakaPendukungReport(List<Pustaka> pustakaPendukungReport) {
		this.pustakaPendukungReport = pustakaPendukungReport;
	}

	public List<SatManMKTemporary> getSatManMKReport() {
		return satManMKReport;
	}

	public void setSatManMKReport(List<SatManMKTemporary> satManMKReport) {
		this.satManMKReport = satManMKReport;
	}

	public RumpunMK getRumpunMK() {
		return rumpunMK;
	}

	public void setRumpunMK(RumpunMK rumpunMK) {
		this.rumpunMK = rumpunMK;
	} 
	
	
}
