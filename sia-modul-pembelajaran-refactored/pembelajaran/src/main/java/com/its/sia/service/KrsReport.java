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

public class KrsReport {
	private TglSmt tglSmt;
	private AturanPengganti aturanPengganti;
	private boolean dapatDisusun;
	private boolean dapatDiubah;
	private boolean dapatDibatalkan;
	private boolean statusPembayaran;
	private Integer tingkat;
	private String awalPenyusunan;
	private String akhirPenyusunan;
	private String awalPerubahan;
	private String akhirPerubahan;
	private String awalPembatalan;
	private String akhirPembatalan;
	private List<Krs> listKrs;
	private List<SatManMK> listSatManMK;
	private List<Boolean> listKuisioner;
	private Ips ips;
	private Ipk ipk;
	private BatasAmbilSks batasPengambilan;
	private List<SatMan> listSatMan;
	private List<List<Pemb>> listListPemb;
	
	public List<SatMan> getListSatMan() {
		return listSatMan;
	}

	public void setListSatMan(List<SatMan> listSatMan) {
		this.listSatMan = listSatMan;
	}

	public List<List<Pemb>> getListListPemb() {
		return listListPemb;
	}

	public void setListListPemb(List<List<Pemb>> listListPemb) {
		this.listListPemb = listListPemb;
	}

	public KrsReport(){
		
	}

	public TglSmt getTglSmt() {
		return tglSmt;
	}
	public void setTglSmt(TglSmt tglSmt) {
		this.tglSmt = tglSmt;
	}
	public boolean isDapatDisusun() {
		return dapatDisusun;
	}
	public void setDapatDisusun(boolean dapatDisusun) {
		this.dapatDisusun = dapatDisusun;
	}
	public List<Krs> getListKrs() {
		return listKrs;
	}
	public void setListKrs(List<Krs> listKrs) {
		this.listKrs = listKrs;
	}
	public Ips getIps() {
		return ips;
	}
	public void setIps(Ips ips) {
		this.ips = ips;
	}
	public Ipk getIpk() {
		return ipk;
	}
	public void setIpk(Ipk ipk) {
		this.ipk = ipk;
	}
	public BatasAmbilSks getBatasPengambilan() {
		return batasPengambilan;
	}
	public void setBatasPengambilan(BatasAmbilSks batasPengambilan) {
		this.batasPengambilan = batasPengambilan;
	}

	public String getAwalPenyusunan() {
		return awalPenyusunan;
	}

	public void setAwalPenyusunan(String awalPenyusunan) {
		this.awalPenyusunan = awalPenyusunan;
	}

	public String getAkhirPenyusunan() {
		return akhirPenyusunan;
	}

	public void setAkhirPenyusunan(String akhirPenyusunan) {
		this.akhirPenyusunan = akhirPenyusunan;
	}

	public String getAwalPerubahan() {
		return awalPerubahan;
	}

	public void setAwalPerubahan(String awalPerubahan) {
		this.awalPerubahan = awalPerubahan;
	}

	public String getAkhirPerubahan() {
		return akhirPerubahan;
	}

	public void setAkhirPerubahan(String akhirPerubahan) {
		this.akhirPerubahan = akhirPerubahan;
	}

	public String getAwalPembatalan() {
		return awalPembatalan;
	}

	public void setAwalPembatalan(String awalPembatalan) {
		this.awalPembatalan = awalPembatalan;
	}

	public String getAkhirPembatalan() {
		return akhirPembatalan;
	}

	public void setAkhirPembatalan(String akhirPembatalan) {
		this.akhirPembatalan = akhirPembatalan;
	}
	
	public AturanPengganti getAturanPengganti() {
		return aturanPengganti;
	}

	public void setAturanPengganti(AturanPengganti aturanPengganti) {
		this.aturanPengganti = aturanPengganti;
	}

	public boolean isStatusPembayaran() {
		return statusPembayaran;
	}

	public void setStatusPembayaran(boolean statusPembayaran) {
		this.statusPembayaran = statusPembayaran;
	}

	public Integer getTingkat() {
		return tingkat;
	}

	public void setTingkat(Integer tingkat) {
		this.tingkat = tingkat;
	}

	public List<Boolean> getListKuisioner() {
		return listKuisioner;
	}

	public void setListKuisioner(List<Boolean> listKuisioner) {
		this.listKuisioner = listKuisioner;
	}

	public boolean isDapatDiubah() {
		return dapatDiubah;
	}

	public void setDapatDiubah(boolean dapatDirubah) {
		this.dapatDiubah = dapatDirubah;
	}

	public boolean isDapatDibatalkan() {
		return dapatDibatalkan;
	}

	public void setDapatDibatalkan(boolean dapatDibatalkan) {
		this.dapatDibatalkan = dapatDibatalkan;
	}

	public List<SatManMK> getListSatManMK() {
		return listSatManMK;
	}

	public void setListSatManMK(List<SatManMK> listSatManMK) {
		this.listSatManMK = listSatManMK;
	}
}
