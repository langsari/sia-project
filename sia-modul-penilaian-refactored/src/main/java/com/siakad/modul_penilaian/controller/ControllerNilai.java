package com.siakad.modul_penilaian.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import javax.servlet.http.HttpSession;

//import com.siakad.modul_penilaian.service.PembService;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sia.main.domain.PeranPengguna;
import com.sia.modul.domain.KomponenNilai;
import com.sia.modul.domain.KonversiNilai;
import com.sia.modul.domain.Krs;
import com.sia.modul.domain.Nilai;
import com.sia.modul.domain.Pemb;
import com.sia.modul.domain.TglSmt;
import com.siakad.modul_penilaian.service.AjaxResponse;
import com.siakad.modul_penilaian.service.JSONNilai;
import com.siakad.modul_penilaian.service.KomponenNilaiService;
import com.siakad.modul_penilaian.service.KonversiNilaiService;
import com.siakad.modul_penilaian.service.KrsService;
import com.siakad.modul_penilaian.service.NilaiService;
import com.siakad.modul_penilaian.service.PembService;
import com.siakad.modul_penilaian.service.TglSmtService;

import com.siakad.modul_penilaian.facadeservice.FacadeService;

@Controller
public class ControllerNilai {
	@Autowired
	private PembService servicePemb;

	@Autowired
	private KrsService serviceKrs;

	@Autowired
	private KomponenNilaiService serviceKomp;

	@Autowired
	private NilaiService serviceNilai;

	@Autowired
	private KonversiNilaiService serviceKonversi;

	@Autowired
	private TglSmtService serviceTglSmt;
	
	@Secured(value = { "ROLE_Pendidik" })
	@RequestMapping(value = "/kelola_nilai/", method = RequestMethod.GET)
	public ModelAndView tampilkanDaftarKelas(HttpSession session, Locale locale) {
		ModelAndView mav = new ModelAndView();
		
		PeranPengguna peranPengguna = (PeranPengguna) session.getAttribute("userRoleSession");
		
		Date date = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", locale);
		
		String formattedDate = simpleDateFormat.format(date);
		LocalDate localDate = LocalDate.parse(formattedDate);
		
		TglSmt tglSmtAktif = serviceTglSmt.ambilTglSmtAktif();
		System.out.println("Tanggal Akhir Penilaian: " + tglSmtAktif.getTglAkhirPenilaian());
		if(localDate.isBefore(tglSmtAktif.getTglAkhirPenilaian())) {
			
			List<Pemb> kelas = servicePemb.ambilBerdasarkanTglSmtPtk(tglSmtAktif.getIdTglSmt(), peranPengguna.getPengguna().getPtk().getIdPtk());
			mav.addObject("listKelas", kelas);
		}
		else {
			String pesan = "Anda sudah melewati batas waktu penilaian.";
			mav.addObject("pesan", pesan);
		}
		
		mav.setViewName("daftar_kelas_aktif");
		mav.addObject("menuActive", "Kelola Nilai");
		
		return mav;
	}
	
	@Secured(value = { "ROLE_Admin", "ROLE_Kepala", "ROLE_Pendidik", "ROLE_Tenaga Kependidikan", "ROLE_Peserta Didik" })
	@RequestMapping(value = "/lihat_nilai/", method = RequestMethod.GET)
	public ModelAndView tampilkanDaftarKelasPeriode(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		
		List<TglSmt> daftarTglSmt = serviceTglSmt.ambilSemuaTglSmt();
		List<Pemb> kelas = servicePemb.ambilSemuaPemb();		
		
		mav.setViewName("daftar_kelas_periode");
		mav.addObject("listKelas", kelas);
		mav.addObject("listTglSmt", daftarTglSmt);
		return mav;
	}

	@RequestMapping(value = "/kelola_nilai/", method = RequestMethod.POST)
	public ModelAndView tampilkanKelolaNilai(@RequestParam("idPemb") UUID idPemb, HttpSession session) {
		PeranPengguna peranPengguna  = (PeranPengguna) session.getAttribute("userRoleSession");
		
		TglSmt tglSmtAktif = serviceTglSmt.ambilTglSmtAktif();
		List<Pemb> kelas = servicePemb.ambilBerdasarkanTglSmtPtk(tglSmtAktif.getIdTglSmt(), peranPengguna.getPengguna().getPtk().getIdPtk());
		List<Krs> krsInfo = serviceKrs.ambilKrsBerdasarkanPemb(idPemb);
		List<KomponenNilai> komp = serviceKomp.ambilSemuaKomponen(idPemb);
		List<Nilai> listNilai = serviceNilai.ambilNilaiKelas(krsInfo);
		Pemb pemb = servicePemb.ambilPemb(idPemb);
		String namaKelas = pemb.getMk().getNamaMK() + " " + pemb.getNmPemb();
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("kelola_nilai");
		mav.addObject("krsInfo", krsInfo);
		mav.addObject("listKomponen", komp);
		mav.addObject("listNilai", listNilai);
		mav.addObject("namaKelas", namaKelas);
		mav.addObject("idPemb", idPemb);
		mav.addObject("listKelas", kelas);
		mav.addObject("menuActive", "Lihat Nilai Per Kelas");
		
		return mav;
	}
	
	@RequestMapping(value = "/lihat_nilai/", method = RequestMethod.POST)
	public ModelAndView tampilkanLihatNilai(@RequestParam("idPemb") UUID idPemb, Locale locale, Model model) {
		List<TglSmt> daftarTglSmt = serviceTglSmt.ambilSemuaTglSmt();
		List<Pemb> kelas = servicePemb.ambilSemuaPemb();
		List<Krs> krsInfo = serviceKrs.ambilKrsBerdasarkanPemb(idPemb);
		Pemb pemb = servicePemb.ambilPemb(idPemb);
		String namaKelas = pemb.getMk().getNamaMK() + " " + pemb.getNmPemb();
		
		ModelAndView lihatNilai = new ModelAndView();
		lihatNilai.setViewName("laporan_nilai_per_kelas");
		lihatNilai.addObject("krsInfo", krsInfo);
		lihatNilai.addObject("namaKelas", namaKelas);
		lihatNilai.addObject("listKelas", kelas);
		lihatNilai.addObject("listTglSmt", daftarTglSmt);
		
		return lihatNilai;
	}
	
	@RequestMapping(value = "/kelola_nilai/{idPemb}/tambah_komponen/", method = RequestMethod.POST)
	public @ResponseBody AjaxResponse tambahKomponenNilai(@RequestBody KomponenNilai komponen, @PathVariable UUID idPemb) {
		Pemb foreignPemb = servicePemb.ambilPemb(idPemb);
		komponen.setPemb(foreignPemb);
		UUID idNew = serviceKomp.tambahKomponen(komponen);
		return new AjaxResponse("ok", "Komponen berhasil ditambah", idNew);
	}
	
	@RequestMapping(value = "/kelola_nilai/hapus_komponen/", method = RequestMethod.POST)
	public @ResponseBody AjaxResponse hapusKomponenNilai(@RequestParam("idKomp") UUID idKomp) {
		serviceKomp.hapusKomponen(idKomp);
		return new AjaxResponse("ok", "Komponen berhasil dihapus", null);
	}
	
	@RequestMapping(value = "/kelola_nilai/{idPemb}/simpan_komponen/", method = RequestMethod.POST)
	public @ResponseBody AjaxResponse simpanKomponenNilai(@RequestBody KomponenNilai[] listKomponen, @PathVariable UUID idPemb) {
		List<KomponenNilai> listKomponenNilai = new ArrayList<KomponenNilai>();
		for (KomponenNilai komponen : listKomponen) {
			Pemb foreignPemb = servicePemb.ambilPemb(idPemb);
			komponen.setPemb(foreignPemb);
			
			listKomponenNilai.add(komponen);
		}
		serviceKomp.simpanKomponen(listKomponenNilai);
		return new AjaxResponse("ok", "Komponen berhasil disimpan", null);
	}
	
	@RequestMapping(value = "/kelola_nilai/{idPemb}/simpan_nilai/", method = RequestMethod.POST)
	public @ResponseBody AjaxResponse simpanNilai(@RequestBody JSONNilai[] listNilaiJSON, @PathVariable UUID idPemb) {
		List<Nilai> listNilai = new ArrayList<Nilai>();
		for (JSONNilai nilaiJSON : listNilaiJSON) {
			Nilai nilai = new Nilai();
			nilai.setKrs(serviceKrs.ambilKrs(nilaiJSON.getIdKrs()));
			nilai.setKomponenNilai(serviceKomp.ambilKomponen(nilaiJSON.getIdKomp()));
			nilai.setNilai(nilaiJSON.getNilai());
			
			listNilai.add(nilai);
		}
		serviceNilai.masukkanNilai(listNilai);
		List<Krs> listKrs = serviceKrs.ambilKrsBerdasarkanPemb(idPemb);
		for (Krs krs : listKrs) {
			krs.setNilaiAkhir(serviceNilai.ambilNilaiAkhir(krs));
			krs.setKonversiNilai(serviceKonversi.ambilBerdasarkanBatas(krs.getNilaiAkhir()));
			if(krs.getKonversiNilai().getBatasBawah() < krs.getPemb().getMk().getKonversiNilai().getBatasBawah())
				krs.setaKrsLulus(false);
			else
				krs.setaKrsLulus(true);
			serviceKrs.perbaruiNilaiAkhir(krs);
		}
		return new AjaxResponse("ok", "Nilai berhasil disimpan", null);
	}
	
	@Secured(value = { "ROLE_Admin" })
	@RequestMapping(value = "/konversi_nilai/", method = RequestMethod.GET)
	public ModelAndView tampilkanKonversiNilai(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		
		List<KonversiNilai> listKonversi = serviceKonversi.ambilSemuaKonversiNilai();
		mav.setViewName("daftar_konversi_nilai");
		mav.addObject("listKonversi", listKonversi);
		mav.addObject("menuActive", "Konversi Nilai");
		
		return mav;
	}
	
	@RequestMapping(value = "/konversi_nilai/tambah_konversi/", method = RequestMethod.POST)
	public @ResponseBody AjaxResponse tambahKonversiNilai(@RequestBody KonversiNilai konversi) {
		List<KonversiNilai> listKonversi = serviceKonversi.ambilSemuaKonversiNilai();
		HashMap<String, Integer> hashHuruf = new HashMap<String, Integer>();
		HashMap<Double, Integer> hashNilai = new HashMap<Double, Integer>();
		HashMap<Double, Integer> hashBatas = new HashMap<Double, Integer>();
		
		boolean aKonversiUnik = true;
		String messageError = "Konversi gagal ditambah:\n";
		
		for (KonversiNilai konversiNilai : listKonversi) {
			Integer i;
			i = hashHuruf.get(konversiNilai.getHuruf());
			if(i == null)
				hashHuruf.put(konversiNilai.getHuruf(), 1);
			else
				hashHuruf.put(konversiNilai.getHuruf(), i + 1);
			
			i = hashNilai.get(konversiNilai.getNilaiHuruf());
			if(i == null)
				hashNilai.put(konversiNilai.getNilaiHuruf(), 1);
			else
				hashNilai.put(konversiNilai.getNilaiHuruf(), i + 1);
			
			i = hashBatas.get(konversiNilai.getBatasBawah());
			if(i == null)
				hashBatas.put(konversiNilai.getBatasBawah(), 1);
			else
				hashBatas.put(konversiNilai.getBatasBawah(), i + 1);
		}
		
		if(hashHuruf.get(konversi.getHuruf()) != null) {
			messageError += "Huruf harus unik\n";
			aKonversiUnik = false;
		}
		if(hashNilai.get(konversi.getNilaiHuruf()) != null) {
			messageError += "Nilai harus unik\n";
			aKonversiUnik = false;
		}
		if(hashBatas.get(konversi.getBatasBawah()) != null) {
			messageError += "Batas harus unik\n";
			aKonversiUnik = false;
		}
		
		if(aKonversiUnik) {
			UUID idKonversi = serviceKonversi.tambahKonversiNilai(konversi);
			return new AjaxResponse("ok", "Konversi berhasil ditambah", idKonversi);
		}
		else
			return new AjaxResponse("fail", messageError, null);
	}
	
	@RequestMapping(value = "/konversi_nilai/hapus_konversi/", method = RequestMethod.POST)
	public @ResponseBody AjaxResponse hapusKonversiNilai(@RequestParam("idKonversi") UUID idKonversi) {
		serviceKonversi.hapusKonversiNilai(idKonversi);
		return new AjaxResponse("ok", "Konversi berhasil dihapus", null);
	}
	
	@RequestMapping(value = "/konversi_nilai/simpan_konversi/", method = RequestMethod.POST)
	public @ResponseBody AjaxResponse simpanKonversiNilai(@RequestBody KonversiNilai[] listKonversi) {
		HashMap<String, Integer> hashHuruf = new HashMap<String, Integer>();
		HashMap<Double, Integer> hashNilai = new HashMap<Double, Integer>();
		HashMap<Double, Integer> hashBatas = new HashMap<Double, Integer>();
		
		boolean aKonversiUnik = true;
		
		for (KonversiNilai konversiNilai : listKonversi) {
			Integer i;
			i = hashHuruf.get(konversiNilai.getHuruf());
			if(i == null)
				hashHuruf.put(konversiNilai.getHuruf(), 1);
			else {
				hashHuruf.put(konversiNilai.getHuruf(), i + 1);
				aKonversiUnik = false;
			}
			
			i = hashNilai.get(konversiNilai.getNilaiHuruf());
			if(i == null)
				hashNilai.put(konversiNilai.getNilaiHuruf(), 1);
			else {
				hashNilai.put(konversiNilai.getNilaiHuruf(), i + 1);
				aKonversiUnik = false;
			}
			
			i = hashBatas.get(konversiNilai.getBatasBawah());
			if(i == null)
				hashBatas.put(konversiNilai.getBatasBawah(), 1);
			else {
				hashBatas.put(konversiNilai.getBatasBawah(), i + 1);
				aKonversiUnik = false;
			}
		}
		
		if(aKonversiUnik == true) {
			serviceKonversi.simpanKonversiNilai(listKonversi);
			return new AjaxResponse("ok", "Konversi berhasil disimpan", null);
		}
		else {
			return new AjaxResponse("fail", "Masing-masing konversi harus unik", null);
		}
	}
}
