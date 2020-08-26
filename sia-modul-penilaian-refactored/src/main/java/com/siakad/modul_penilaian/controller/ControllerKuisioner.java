package com.siakad.modul_penilaian.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sia.main.domain.PeranPengguna;
import com.sia.modul.domain.Krs;
import com.sia.modul.domain.Kuisioner;
import com.sia.modul.domain.NilaiKuisioner;
import com.sia.modul.domain.Pemb;
import com.sia.modul.domain.PendidikPengajar;
import com.sia.modul.domain.PertanyaanKuisioner;
import com.sia.modul.domain.StatusKuisioner;
import com.sia.modul.domain.TglSmt;
import com.siakad.modul_penilaian.service.AjaxResponse;
import com.siakad.modul_penilaian.service.JSONNilaiKuisioner;
import com.siakad.modul_penilaian.service.JSONPertanyaan;
import com.siakad.modul_penilaian.service.KrsService;
import com.siakad.modul_penilaian.service.KuisionerService;
import com.siakad.modul_penilaian.service.NilaiKuisionerPerPemb;
import com.siakad.modul_penilaian.service.NilaiKuisionerService;
import com.siakad.modul_penilaian.service.PembService;
import com.siakad.modul_penilaian.service.PendidikPengajarService;
import com.siakad.modul_penilaian.service.PertanyaanKuisionerService;
import com.siakad.modul_penilaian.service.StatusKuisionerService;
import com.siakad.modul_penilaian.service.TglSmtService;

@Controller
public class ControllerKuisioner {
	
	@Autowired
	private KuisionerService serviceKuisioner;
	
	@Autowired
	private PertanyaanKuisionerService servicePertanyaan;
	
	@Autowired
	private NilaiKuisionerService serviceNilaiKuisioner;
	
	@Autowired
	private KrsService serviceKrs;
	
	@Autowired
	private PembService servicePemb;
	
	@Autowired
	private PendidikPengajarService servicePendidikPengajar;
	
	@Autowired
	private StatusKuisionerService serviceStatus;
	
	@Autowired
	private TglSmtService serviceTglSmt;
	
	@Secured(value = { "ROLE_Peserta Didik" })
	@RequestMapping(value = "/isi_kuisioner/", method = RequestMethod.GET)
	public ModelAndView tampilkanDaftarIsiKuisioner(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		
		PeranPengguna peranPengguna = (PeranPengguna)session.getAttribute("userRoleSession");
		
		List<Krs> daftarKrs = serviceKrs.ambilSemuaBerdasarkanPd(peranPengguna.getPengguna().getPd().getIdPd());
		//List<Krs> daftarKrs = serviceKrs.ambilKrsAktifBerdasarkanPd(pd.getIdPd(), tglSmtAktif.getIdTglSmt());
		List<Kuisioner> daftarKuisioner = serviceKuisioner.ambilSemuaKuisioner();
		List<Boolean> daftarStatus = new ArrayList<Boolean>();
		
		for (Krs krs : daftarKrs) {
			for (Kuisioner kuisioner : daftarKuisioner) {
				daftarStatus.add(serviceStatus.apakahKuisionerTerisi(krs.getIdKrs(), kuisioner.getIdKuisioner()));
			}
		}
		
		mav.setViewName("daftar_kuisioner");
		mav.addObject("daftarKrs", daftarKrs);
		mav.addObject("daftarKuisioner", daftarKuisioner);
		mav.addObject("daftarStatus", daftarStatus);
		mav.addObject("menuActive", "Isi Kuisioner");
		
		return mav;
	}
	
	@RequestMapping(value = "/isi_kuisioner/", method = RequestMethod.POST)
	public ModelAndView tampilkanIsiKuisioner(@RequestParam("idKrs") UUID idKrs, @RequestParam("idKuisioner") UUID idKuisioner) {
		Kuisioner kuisioner = serviceKuisioner.ambilKuisioner(idKuisioner);
		List<PertanyaanKuisioner> daftarPertanyaan = servicePertanyaan.ambilBerdasarKuisioner(idKuisioner);
		Pemb pemb = serviceKrs.ambilKrs(idKrs).getPemb();
		
		ModelAndView isiKuisioner = new ModelAndView();
		isiKuisioner.setViewName("isi_kuisioner");
		isiKuisioner.addObject("kuisioner", kuisioner);
		isiKuisioner.addObject("daftarPertanyaan", daftarPertanyaan);
		isiKuisioner.addObject("idKrs", idKrs);
		isiKuisioner.addObject("pemb", pemb);
		
		return isiKuisioner;
	}
	
	@RequestMapping(value = "/isi_kuisioner/{idKrs}/{idKuisioner}/simpan_kuisioner/", method = RequestMethod.POST)
	public @ResponseBody AjaxResponse submitKuisioner(@RequestBody JSONNilaiKuisioner[] daftarNilai, @PathVariable("idKrs") UUID idKrs, @PathVariable("idKuisioner") UUID idKuisioner) {
		double totalNilai = 0;
		for (JSONNilaiKuisioner nilai : daftarNilai) {
			NilaiKuisioner nilaiKuisioner = new NilaiKuisioner();
			nilaiKuisioner.setKrs(serviceKrs.ambilKrs(idKrs));
			nilaiKuisioner.setPertanyaanKuisioner(servicePertanyaan.getById(nilai.getIdPertanyaan()));
			nilaiKuisioner.setNilaiPertanyaan(nilai.getNilaiPertanyaan());
			
			totalNilai += nilai.getNilaiPertanyaan();
			serviceNilaiKuisioner.simpanNilaiKuisioner(nilaiKuisioner);
		}
		
		double nilaiAkhir = totalNilai / (double) daftarNilai.length;
		
		StatusKuisioner status = new StatusKuisioner();
		status.setaKuisionerTerisi(true);
		status.setKrs(serviceKrs.ambilKrs(idKrs));
		status.setKuisioner(serviceKuisioner.ambilKuisioner(idKuisioner));
		status.setNilaiKuisioner(nilaiAkhir);
		serviceStatus.masukkanStatus(status);
		
		return new AjaxResponse("ok", "Kuisioner berhasil disimpan", null);
	}
	
	@Secured(value = { "ROLE_Admin" })
	@RequestMapping(value = "/kelola_kuisioner/", method = RequestMethod.GET)
	public ModelAndView tampilkanDaftarKelolaKuisioner(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		
		List<Kuisioner> listKuisionerAktif = serviceKuisioner.ambilSemuaKuisioner();
		
		mav.setViewName("kelola_kuisioner");
		mav.addObject("listKuisioner", listKuisionerAktif);
		mav.addObject("menuActive","Kelola Kuisioner");
		
		return mav;
	}
	
	@RequestMapping(value = "/kelola_kuisioner/tambah_kuisioner/", method = RequestMethod.POST)
	public @ResponseBody AjaxResponse tambahKuisioner(@RequestBody Kuisioner kuisionerBaru) {
		UUID idKuisionerBaru = serviceKuisioner.tambahKuisioner(kuisionerBaru);
		return new AjaxResponse("ok", "Kuisioner berhasil ditambahkan", idKuisionerBaru);
	}
	
	@RequestMapping(value = "/kelola_kuisioner/hapus_kuisioner/", method = RequestMethod.POST)
	public @ResponseBody AjaxResponse hapusKuisioner(@RequestParam("idKuisioner") UUID idKuisioner) {
		serviceKuisioner.hapusKuisioner(idKuisioner);
		return new AjaxResponse("ok", "Kuisioner berhasil dihapus", null);
	}
	
	@RequestMapping(value = "/kelola_kuisioner/ambil_pertanyaan/", method = RequestMethod.POST)
	public @ResponseBody AjaxResponse ambilSemuaPertanyaan(@RequestParam("idKuisioner") UUID idKuisioner) {
		List<PertanyaanKuisioner> listPertanyaan = servicePertanyaan.ambilBerdasarKuisioner(idKuisioner);
		return new AjaxResponse("ok", "Pertanyaan dari Kuisioner", listPertanyaan);
	}
	
	@RequestMapping(value = "/kelola_kuisioner/tambah_pertanyaan/", method = RequestMethod.POST)
	public @ResponseBody AjaxResponse tambahPertanyaan(@RequestBody JSONPertanyaan pertanyaanJSON) {
		PertanyaanKuisioner pertanyaanKuisioner = new PertanyaanKuisioner();
		pertanyaanKuisioner.setPertanyaan(pertanyaanJSON.getPertanyaan());
		pertanyaanKuisioner.setaPertanyaanAktif(true);
		pertanyaanKuisioner.setKuisioner(serviceKuisioner.ambilKuisioner(pertanyaanJSON.getIdKuisioner()));
		
		UUID idPertanyaanBaru = servicePertanyaan.tambahPertanyaan(pertanyaanKuisioner);
		return new AjaxResponse("ok", "Pertanyaan berhasil ditambahkan", idPertanyaanBaru);
	}
	
	@RequestMapping(value = "/kelola_kuisioner/hapus_pertanyaan/", method = RequestMethod.POST)
	public @ResponseBody AjaxResponse hapusPertanyaan(@RequestParam("idPertanyaan") UUID idPertanyaan) {
		servicePertanyaan.hapusPertanyaan(idPertanyaan);
		return new AjaxResponse("ok", "Pertanyaan berhasil dihapus", null);
	}
	
	@RequestMapping(value = "/kelola_kuisioner/simpan_pertanyaan/", method = RequestMethod.POST)
	public @ResponseBody AjaxResponse simpanPertanyaan(@RequestBody JSONPertanyaan[] listPertanyaanJSON) {
		PertanyaanKuisioner pertanyaan = new PertanyaanKuisioner();
		for (JSONPertanyaan pertanyaanJSON : listPertanyaanJSON) {
			pertanyaan.setIdPertanyaanKuisioner(pertanyaanJSON.getIdPertanyaan());
			pertanyaan.setPertanyaan(pertanyaanJSON.getPertanyaan());
			pertanyaan.setaPertanyaanAktif(true);
			pertanyaan.setKuisioner(serviceKuisioner.ambilKuisioner(pertanyaanJSON.getIdKuisioner()));
			
			servicePertanyaan.simpanPertanyaan(pertanyaan);
		}
		
		return new AjaxResponse("ok", "Pertanyaan berhasil disimpan", null);
	}
	
	@Secured(value = { "ROLE_Admin", "ROLE_Kepala" })
	@RequestMapping(value = "/laporan_kuisioner_periode/", method = RequestMethod.GET)
	public ModelAndView tampilkanDaftarPeriodeKuisioner(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		
		List<TglSmt> daftarTglSmt = serviceTglSmt.ambilSemuaTglSmt();
		
		mav.setViewName("daftar_laporan_kuisioner_periode");
		mav.addObject("daftarTglSmt", daftarTglSmt);
		mav.addObject("menuActive", "Laporan Kuisioner Periodik");
		
		return mav;
	}
	
	@RequestMapping(value = "/laporan_kuisioner_periode/", method = RequestMethod.POST)
	public ModelAndView tampilkanLaporanKuisionerPeriode(@RequestParam("idTglSmt") UUID idTglSmt) {
		List<TglSmt> daftarTglSmt = serviceTglSmt.ambilSemuaTglSmt();
		List<Pemb> daftarPemb = servicePemb.ambilBerdasarkanTglSmt(idTglSmt);
		List<Kuisioner> daftarKuisioner = serviceKuisioner.ambilSemuaKuisioner();
		List<PendidikPengajar> daftarKetuaPendidik = new ArrayList<PendidikPengajar>();
		List<NilaiKuisionerPerPemb> daftarNilai = new ArrayList<NilaiKuisionerPerPemb>();
		
		for (Pemb pemb : daftarPemb) {
			if(servicePendidikPengajar.ambilKetuaPemb(pemb.getIdPemb()) != null)
				daftarKetuaPendidik.add(servicePendidikPengajar.ambilKetuaPemb(pemb.getIdPemb()));
			
			for (Kuisioner kuisioner : daftarKuisioner) {
				List<StatusKuisioner> daftarStatus = serviceStatus.ambilBerdasarkanPembKuisioner(pemb.getIdPemb(), kuisioner.getIdKuisioner());
				double totalNilai = 0;
				double count = 0;
				for (StatusKuisioner status : daftarStatus) {
					totalNilai += status.getNilaiKuisioner();
					count++;
				}
				
				double nilaiAkhir;
				if(count>0) {
					nilaiAkhir = totalNilai/count;
				}
				else {
					nilaiAkhir = 0;
				}
				
				daftarNilai.add(new NilaiKuisionerPerPemb(pemb.getIdPemb(), kuisioner.getIdKuisioner(), nilaiAkhir));
			}
		}
		
		ModelAndView laporanPerPeriode = new ModelAndView();
		laporanPerPeriode.setViewName("laporan_kuisioner_per_periode");
		laporanPerPeriode.addObject("daftarTglSmt", daftarTglSmt);
		laporanPerPeriode.addObject("daftarPemb", daftarPemb);
		laporanPerPeriode.addObject("daftarKuisioner", daftarKuisioner);
		laporanPerPeriode.addObject("daftarKetuaPendidik", daftarKetuaPendidik);
		laporanPerPeriode.addObject("daftarNilai", daftarNilai);
		
		return laporanPerPeriode;
	}
	
	@Secured(value = { "ROLE_Admin", "ROLE_Kepala" })
	@RequestMapping(value = "/laporan_kuisioner_kelas/", method = RequestMethod.GET)
	public ModelAndView tampilkanDaftarKelasKuisioner(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		
		List<TglSmt> daftarTglSmt = serviceTglSmt.ambilSemuaTglSmt();
		List<Pemb> daftarPemb = servicePemb.ambilSemuaPemb();
		
		mav.setViewName("daftar_laporan_kuisioner_kelas");
		mav.addObject("daftarTglSmt", daftarTglSmt);
		mav.addObject("daftarPemb", daftarPemb);
		mav.addObject("menuActive", "Laporan Kuisioner Per Kelas");
		
		return mav;
	}
	
	@RequestMapping(value = "/laporan_kuisioner_kelas/", method = RequestMethod.POST)
	public ModelAndView tampilkanLaporanKuisionerKelas(@RequestParam("idPemb") UUID idPemb) {
		List<TglSmt> daftarTglSmt = serviceTglSmt.ambilSemuaTglSmt();
		List<Pemb> daftarPemb = servicePemb.ambilSemuaPemb();
		
		List<Kuisioner> daftarKuisioner = serviceKuisioner.ambilSemuaKuisioner();
		List<PertanyaanKuisioner> daftarPertanyaan = new ArrayList<PertanyaanKuisioner>();
		for (Kuisioner kuisioner : daftarKuisioner) {
			daftarPertanyaan.addAll(servicePertanyaan.ambilBerdasarKuisioner(kuisioner.getIdKuisioner()));
		}
		HashMap<UUID, Double> daftarNilai = new HashMap<UUID, Double>();
		for (PertanyaanKuisioner pertanyaan : daftarPertanyaan) {
			daftarNilai.put(pertanyaan.getIdPertanyaanKuisioner(), serviceNilaiKuisioner.ambilBerdasarkanPembPertanyaan(idPemb, pertanyaan.getIdPertanyaanKuisioner()));
		}
		List<PendidikPengajar> daftarPendidik = servicePendidikPengajar.ambilBerdasarkanPemb(idPemb);
		Pemb pemb = servicePemb.ambilPemb(idPemb);
		
		ModelAndView laporanKuisionerKelas = new ModelAndView();
		laporanKuisionerKelas.setViewName("laporan_kuisioner_per_kelas");
		laporanKuisionerKelas.addObject("daftarTglSmt", daftarTglSmt);
		laporanKuisionerKelas.addObject("daftarPemb", daftarPemb);
		laporanKuisionerKelas.addObject("daftarKuisioner", daftarKuisioner);
		laporanKuisionerKelas.addObject("daftarPertanyaan", daftarPertanyaan);
		laporanKuisionerKelas.addObject("daftarNilai", daftarNilai);
		laporanKuisionerKelas.addObject("daftarPendidik", daftarPendidik);
		laporanKuisionerKelas.addObject("infoPemb", pemb);
		
		return laporanKuisionerKelas;
	}
	
	@RequestMapping(value = "/update_nilai_dosen/", method = RequestMethod.POST)
	public @ResponseBody AjaxResponse updateRekapKuisioner() {
		TglSmt tglSmtAktif = serviceTglSmt.ambilTglSmtAktif();
		List<Pemb> pembAktif = servicePemb.ambilBerdasarkanTglSmt(tglSmtAktif.getIdTglSmt());
		
		for (Pemb pemb : pembAktif) {
			System.out.println(pemb.getMk().getNamaMK() + " " + pemb.getNmPemb());
			List<StatusKuisioner> statusPemb = serviceStatus.ambilBerdasarkanPemb(pemb.getIdPemb());
			double totalNilai = 0;
			double count = 0;
			for (StatusKuisioner status : statusPemb) {
				totalNilai += status.getNilaiKuisioner();
				count++;
			}
			
			double nilaiAkhir;
			if(count>0) {
				nilaiAkhir = totalNilai/statusPemb.size();
			}
			else {
				nilaiAkhir = 0;
			}
			
			servicePendidikPengajar.masukkanNilaiIpd(pemb.getIdPemb(), nilaiAkhir);
		}
		
		return new AjaxResponse("ok", "Nilai dosen berhasil diperbaharui", null);
	}
}
