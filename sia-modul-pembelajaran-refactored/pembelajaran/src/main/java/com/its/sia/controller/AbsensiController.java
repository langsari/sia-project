package com.its.sia.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import javax.servlet.http.HttpSession;

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
import com.sia.modul.domain.Pd;
import com.sia.modul.domain.Pemb;
import com.sia.modul.domain.PendidikPengajar;
import com.sia.modul.domain.PertemuanPembelajaran;
import com.sia.modul.domain.PresensiPd;
import com.sia.modul.domain.PresensiPengajar;
import com.sia.modul.domain.SatMan;
import com.sia.modul.domain.Smt;
import com.sia.modul.domain.StsKehadiran;
import com.sia.modul.domain.TglSmt;
import com.sia.modul.domain.ThnAjaran;
import com.its.sia.service.AbsensiPendidik;
import com.its.sia.service.AbsensiPesertadidik;
import com.its.sia.service.AjaxResponse;
import com.its.sia.service.Datatable;
import com.its.sia.service.KrsService;
import com.its.sia.service.PdService;
import com.its.sia.service.PembSatManService;
import com.its.sia.service.PembService;
import com.its.sia.service.PendidikPengajarService;
import com.its.sia.service.PertemuanPembelajaranService;
import com.its.sia.service.PresensiPdService;
import com.its.sia.service.PresensiPegajarService;
import com.its.sia.service.PtkService;
import com.its.sia.service.SatManService;
import com.its.sia.service.SmtService;
import com.its.sia.service.StsKehadiranService;
import com.its.sia.service.TglSmtService;
import com.its.sia.service.ThnAjaranService;

@Controller
@RequestMapping(value = "/absensi")
public class AbsensiController {

	@Autowired
	private PembService pembService;
		
	@Autowired
	private TglSmtService tglSmtService;
	
	@Autowired
	private ThnAjaranService thnAjaranService;
	
	@Autowired
	private SmtService smtService;
		
	@Autowired
	private PendidikPengajarService pendidikPengajarService;
	
	@Autowired
	private PtkService ptkService;
	
	@Autowired
	private KrsService krsService;
	
	@Autowired
	private SatManService satManService;

	@Autowired
	private StsKehadiranService StsKehadiranService;
	
	@Autowired
	private PembSatManService pembSatManService;
	
	@Autowired
	private PertemuanPembelajaranService pertemuanPembelajaranService;
	
	@Autowired
	private PresensiPegajarService presensiPegajarService;
	
	@Autowired
	private PdService pdService;
	
	@Autowired
	private PresensiPdService presensiPdService;
	
	@Secured(value = { "ROLE_Tenaga Kependidikan" })
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView datatable(HttpSession session, Locale locale, Model model) {
		Pemb pemb = new Pemb();
		ModelAndView mav = new ModelAndView();
		
		List<TglSmt> listTglSmt = tglSmtService.get("tglSmt.aTglSmtTerhapus = false","thnAjaran.thnThnAjaran asc, smt.nmSmt asc");
		
		mav.setViewName("AbsensiPembelajaran");
		mav.addObject("pemb", pemb);
		mav.addObject("listTglSmt", listTglSmt);
		
		return mav;
	}
	
	@Secured(value = { "ROLE_Kepala" })
	@RequestMapping(value = "/laporanpertemuan/", method = RequestMethod.GET)
	public ModelAndView laporanpertemuan(HttpSession session, Locale locale, Model model) {
		ModelAndView mav = new ModelAndView();
		
		PeranPengguna peranPengguna = (PeranPengguna) session.getAttribute("userRoleSession");
		
		TglSmt smtAktif = tglSmtService.getAktif();
		List<ThnAjaran> listThnAjaran = thnAjaranService.get("aThnAjaranTerhapus = false","thnThnAjaran desc");
		List<Smt> listSmt = smtService.get("aSmthapus = false","jenisSmt asc");
		UUID idSatMan = peranPengguna.getSatMan().getIdSatMan();
		List<SatMan> listSatMan = satManService.listChild(idSatMan);
		
		mav.setViewName("LaporanPertemuan");
		mav.addObject("smtAktif", smtAktif);
		mav.addObject("listThnAjaran", listThnAjaran);
		mav.addObject("listSmt", listSmt);
		mav.addObject("listSatMan", listSatMan);
		
		mav.addObject ("menuActive","Laporan Pertemuan");
		
		return mav;
	}
	
	
	@RequestMapping(value = "pendidik/{idPemb}", method = RequestMethod.GET)
	public ModelAndView pendidik(HttpSession session, Locale locale, @PathVariable("idPemb") String idPemb) {
		ModelAndView mav = new ModelAndView();
		
		Pemb pemb = pembService.getById(UUID.fromString(idPemb));
		/*if not hak akses throw 404*/
		List<PertemuanPembelajaran> listPertemuanPembelajaran = pertemuanPembelajaranService.get("pemb.idPemb ='"+pemb.getIdPemb()+"'","pertemuanPembelajaran.pertemuan asc");
		Integer jumlahPertemuan = pemb.getTglSmt().getSmt().getJmlPertemuan() * pemb.getTemuDalamSeminggu();
		Integer pertemuanTerlaksana = listPertemuanPembelajaran.size();
		List<PendidikPengajar> listPendidikPengajar = pendidikPengajarService.get("pemb.idPemb ='"+pemb.getIdPemb()+"' and pendidikPengajar.aPendidikPengajarTerhapus = false","ptk.nmPtk asc");
		List<StsKehadiran> listStsKehadiran = StsKehadiranService.get("aKehadiranTerhapus = false","kodeStsKehadiran asc");
		List<List<PresensiPengajar>> listListPresensiPengajar = new ArrayList<List<PresensiPengajar>>();
		for (PendidikPengajar pendidikPengajar : listPendidikPengajar) {
			List<PresensiPengajar> listPresensiPengajar = new ArrayList<PresensiPengajar>();
			List<PresensiPengajar> queryResult = presensiPegajarService.get("pendidikPengajar.idPendidikPengajar ='"+pendidikPengajar.getIdPendidikPengajar()+"'","pertemuanPembelajaran.pertemuan asc");
			int iterasi = 1;
			for(int i=0;i<queryResult.size();i++)
			{
				while(queryResult.get(i).getPertemuanPembelajaran().getPertemuan()>iterasi)
				{
					listPresensiPengajar.add(null);
					iterasi++;
				}
				listPresensiPengajar.add(queryResult.get(i));
				iterasi++;
			}
			while(iterasi<=pertemuanTerlaksana)
			{
				listPresensiPengajar.add(null);
				iterasi++;
			}
			listListPresensiPengajar.add(listPresensiPengajar);
		}
		mav.setViewName("AbsensiPendidik");
		mav.addObject("pemb", pemb);
		mav.addObject("listPertemuanPembelajaran", listPertemuanPembelajaran);
		mav.addObject("jumlahPertemuan", jumlahPertemuan);
		mav.addObject("listPendidikPengajar", listPendidikPengajar);
		mav.addObject("listStsKehadiran", listStsKehadiran);
		mav.addObject("listListPresensiPengajar", listListPresensiPengajar);
		
		return mav;
	}
	
	
	@RequestMapping(value = "rekappendidik/{idPemb}", method = RequestMethod.GET)
	public ModelAndView rekappendidik(HttpSession session, Locale locale, @PathVariable("idPemb") String idPemb) {
		ModelAndView mav = new ModelAndView();
		
		Pemb pemb = pembService.getById(UUID.fromString(idPemb));
		/*if not hak akses throw 404*/
		List<PertemuanPembelajaran> listPertemuanPembelajaran = pertemuanPembelajaranService.get("pemb.idPemb ='"+pemb.getIdPemb()+"'","pertemuanPembelajaran.pertemuan asc");
		Integer jumlahPertemuan = pemb.getTglSmt().getSmt().getJmlPertemuan() * pemb.getTemuDalamSeminggu();
		Integer pertemuanTerlaksana = listPertemuanPembelajaran.size();
		List<PendidikPengajar> listPendidikPengajar = pendidikPengajarService.get("pemb.idPemb ='"+pemb.getIdPemb()+"' and pendidikPengajar.aPendidikPengajarTerhapus = false","ptk.nmPtk asc");
		List<StsKehadiran> listStsKehadiran = StsKehadiranService.get("aKehadiranTerhapus = false","kodeStsKehadiran asc");
		List<List<PresensiPengajar>> listListPresensiPengajar = new ArrayList<List<PresensiPengajar>>();
		for (PendidikPengajar pendidikPengajar : listPendidikPengajar) {
			List<PresensiPengajar> listPresensiPengajar = new ArrayList<PresensiPengajar>();
			List<PresensiPengajar> queryResult = presensiPegajarService.get("pendidikPengajar.idPendidikPengajar ='"+pendidikPengajar.getIdPendidikPengajar()+"'","pertemuanPembelajaran.pertemuan asc");
			int iterasi = 1;
			for(int i=0;i<queryResult.size();i++)
			{
				while(queryResult.get(i).getPertemuanPembelajaran().getPertemuan()>iterasi)
				{
					listPresensiPengajar.add(null);
					iterasi++;
				}
				listPresensiPengajar.add(queryResult.get(i));
				iterasi++;
			}
			while(iterasi<=pertemuanTerlaksana)
			{
				listPresensiPengajar.add(null);
				iterasi++;
			}
			listListPresensiPengajar.add(listPresensiPengajar);
		}
		mav.setViewName("RekapAbsensiPendidik");
		mav.addObject("pemb", pemb);
		mav.addObject("listPertemuanPembelajaran", listPertemuanPembelajaran);
		mav.addObject("jumlahPertemuan", jumlahPertemuan);
		mav.addObject("listPendidikPengajar", listPendidikPengajar);
		mav.addObject("listStsKehadiran", listStsKehadiran);
		mav.addObject("listListPresensiPengajar", listListPresensiPengajar);
		
		return mav;
	}
	
	
	@RequestMapping(value = "pesertadidik/{idPemb}", method = RequestMethod.GET)
	public ModelAndView pesertadidik(HttpSession session, Locale locale, @PathVariable("idPemb") String idPemb) {
		ModelAndView mav = new ModelAndView();
		
		Pemb pemb = pembService.getById(UUID.fromString(idPemb));
		/*if not hak akses throw 404*/
		List<PertemuanPembelajaran> listPertemuanPembelajaran = pertemuanPembelajaranService.get("pemb.idPemb ='"+pemb.getIdPemb()+"'","pertemuanPembelajaran.pertemuan asc");
		Integer jumlahPertemuan = pemb.getTglSmt().getSmt().getJmlPertemuan() * pemb.getTemuDalamSeminggu();
		Integer pertemuanTerlaksana = listPertemuanPembelajaran.size();
		List<Pd> listPd = krsService.getPeserta("pemb.idPemb ='"+pemb.getIdPemb()+"' and krs.aKrsTerhapus = false and krs.aKrsBatal = false","pd.nimPd asc");
		List<StsKehadiran> listStsKehadiran = StsKehadiranService.get("aKehadiranTerhapus = false","kodeStsKehadiran asc");
		List<List<PresensiPd>> listListPresensiPd = new ArrayList<List<PresensiPd>>();
		for (Pd pd : listPd) {
			List<PresensiPd> listPresensiPd = new ArrayList<PresensiPd>();
			List<PresensiPd> queryResult = presensiPdService.get("pd.idPd ='"+pd.getIdPd()+"' and pemb.idPemb='"+idPemb+"'","pertemuanPembelajaran.pertemuan asc");
			int iterasi = 1;
			for(int i=0;i<queryResult.size();i++)
			{
				while(queryResult.get(i).getPertemuanPembelajaran().getPertemuan()>iterasi)
				{
					listPresensiPd.add(null);
					iterasi++;
				}
				listPresensiPd.add(queryResult.get(i));
				iterasi++;
			}
			while(iterasi<=pertemuanTerlaksana)
			{
				listPresensiPd.add(null);
				iterasi++;
			}
			listListPresensiPd.add(listPresensiPd);
		}
		mav.setViewName("AbsensiPesertadidik");
		mav.addObject("pemb", pemb);
		mav.addObject("listPertemuanPembelajaran", listPertemuanPembelajaran);
		mav.addObject("jumlahPertemuan", jumlahPertemuan);
		mav.addObject("listPd", listPd);
		mav.addObject("listStsKehadiran", listStsKehadiran);
		mav.addObject("listListPresensiPd", listListPresensiPd);
		
		return mav;
	}
	
	
	@RequestMapping(value = "rekappesertadidik/{idPemb}", method = RequestMethod.GET)
	public ModelAndView rekapPesertadidik(HttpSession session, Locale locale, @PathVariable("idPemb") String idPemb) {
		ModelAndView mav = new ModelAndView();
		
		Pemb pemb = pembService.getById(UUID.fromString(idPemb));
		/*if not hak akses throw 404*/
		List<PertemuanPembelajaran> listPertemuanPembelajaran = pertemuanPembelajaranService.get("pemb.idPemb ='"+pemb.getIdPemb()+"'","pertemuanPembelajaran.pertemuan asc");
		Integer jumlahPertemuan = pemb.getTglSmt().getSmt().getJmlPertemuan() * pemb.getTemuDalamSeminggu();
		Float minimalKehadiran = pemb.getTglSmt().getThnAjaran().getPersenHadirMinimPd();
		Integer pertemuanTerlaksana = listPertemuanPembelajaran.size();
		List<Pd> listPd = krsService.getPeserta("pemb.idPemb ='"+pemb.getIdPemb()+"' and krs.aKrsTerhapus = false and krs.aKrsBatal = false","pd.nimPd asc");
		List<List<PresensiPd>> listListPresensiPd = new ArrayList<List<PresensiPd>>();
		for (Pd pd : listPd) {
			List<PresensiPd> listPresensiPd = new ArrayList<PresensiPd>();
			List<PresensiPd> queryResult = presensiPdService.get("pd.idPd ='"+pd.getIdPd()+"' and pemb.idPemb='"+idPemb+"'","pertemuanPembelajaran.pertemuan asc");
			int iterasi = 1;
			for(int i=0;i<queryResult.size();i++)
			{
				while(queryResult.get(i).getPertemuanPembelajaran().getPertemuan()>iterasi)
				{
					listPresensiPd.add(null);
					iterasi++;
				}
				listPresensiPd.add(queryResult.get(i));
				iterasi++;
			}
			while(iterasi<=pertemuanTerlaksana)
			{
				listPresensiPd.add(null);
				iterasi++;
			}
			listListPresensiPd.add(listPresensiPd);
		}
		mav.setViewName("RekapAbsensiPesertadidik");
		mav.addObject("pemb", pemb);
		mav.addObject("listPertemuanPembelajaran", listPertemuanPembelajaran);
		mav.addObject("jumlahPertemuan", jumlahPertemuan);
		mav.addObject("minimalKehadiran", minimalKehadiran);
		mav.addObject("listPd", listPd);
		mav.addObject("listListPresensiPd", listListPresensiPd);
		
		return mav;
	}
	
	
	@RequestMapping(value = "pendidik/saveabsensi", method = RequestMethod.POST, produces="application/json")
    public @ResponseBody AjaxResponse saveabsensiPendidik(HttpSession session, @RequestBody AbsensiPendidik[] absensiPendidiks) {
		
		if(absensiPendidiks[0].getIdPemb() == null) return new AjaxResponse("error","Pembelajaran Tidak Diketahui",null);
		for (AbsensiPendidik absensiPendidik : absensiPendidiks) {
			if(absensiPendidik.getIdPemb()!=null && absensiPendidik.getIdPendidikPengajar()!=null 
					&& absensiPendidik.getIdPertemuanPembelajaran()!=null && absensiPendidik.getIdStsKehadiran()!=null)
			{
					PresensiPengajar presensiPengajar = new PresensiPengajar();
					if(absensiPendidik.getIdPresensiPengajar()!=null)
						presensiPengajar.setIdPresensiPengajar(UUID.fromString(absensiPendidik.getIdPresensiPengajar()));
					PendidikPengajar pendidikPengajar = pendidikPengajarService.getById(UUID.fromString(absensiPendidik.getIdPendidikPengajar()));
					if(pendidikPengajar==null) continue;
					else presensiPengajar.setPendidikPengajar(pendidikPengajar);
					PertemuanPembelajaran pertemuanPembelajaran = pertemuanPembelajaranService.getById(UUID.fromString(absensiPendidik.getIdPertemuanPembelajaran()));
					if(pertemuanPembelajaran == null) continue;
					else presensiPengajar.setPertemuanPembelajaran(pertemuanPembelajaran);
					StsKehadiran stsKehadiran = StsKehadiranService.getById(UUID.fromString(absensiPendidik.getIdStsKehadiran()));
					if(stsKehadiran==null) continue;
					else presensiPengajar.setStsKehadiran(stsKehadiran);
					presensiPegajarService.save(presensiPengajar);
			}
		}
        return new AjaxResponse("ok","Penyimpanan berhasil",null);
    }
	
	
	@RequestMapping(value = "pesertadidik/saveabsensi", method = RequestMethod.POST, produces="application/json")
    public @ResponseBody AjaxResponse saveabsensiPd(HttpSession session, @RequestBody AbsensiPesertadidik[] absensiPendidiks) {
		
		if(absensiPendidiks[0].getIdPemb() == null) return new AjaxResponse("error","Pembelajaran Tidak Diketahui",null);
		for (AbsensiPesertadidik absensiPesertadidik : absensiPendidiks) {
			if(absensiPesertadidik.getIdPemb()!=null && absensiPesertadidik.getIdPd()!=null 
					&& absensiPesertadidik.getIdPertemuanPembelajaran()!=null && absensiPesertadidik.getIdStsKehadiran()!=null)
			{
					PresensiPd presensiPd = new PresensiPd();
					if(absensiPesertadidik.getIdPresensiPd()!=null)
						presensiPd.setIdPresensiPd(UUID.fromString(absensiPesertadidik.getIdPresensiPd()));
					Pd pd = pdService.getById(UUID.fromString(absensiPesertadidik.getIdPd()));
					if(pd==null) 
					{
						System.out.println(absensiPesertadidik.getIdPd());
						continue;
					}
					else presensiPd.setPd(pd);
					PertemuanPembelajaran pertemuanPembelajaran = pertemuanPembelajaranService.getById(UUID.fromString(absensiPesertadidik.getIdPertemuanPembelajaran()));
					if(pertemuanPembelajaran == null) 
					{
						System.out.println(absensiPesertadidik.getIdPertemuanPembelajaran());
						continue;
					}
					else presensiPd.setPertemuanPembelajaran(pertemuanPembelajaran);
					StsKehadiran stsKehadiran = StsKehadiranService.getById(UUID.fromString(absensiPesertadidik.getIdStsKehadiran()));
					if(stsKehadiran==null) 
					{
						System.out.println(absensiPesertadidik.getIdStsKehadiran());
						continue;
					}
					else presensiPd.setStsKehadiran(stsKehadiran);
					presensiPdService.save(presensiPd);
			}
		}
        return new AjaxResponse("ok","Penyimpanan berhasil",null);
    }

	
	@RequestMapping(value = "/json", method = RequestMethod.POST)
	public @ResponseBody Datatable json(
			HttpSession session,
			@RequestParam("sEcho") String sEcho, 
			@RequestParam("iDisplayLength") int iDisplayLength,
            @RequestParam("iSortCol_0") int iSortCol_0,
            @RequestParam("sSortDir_0") String sSortDir_0,
            @RequestParam("sSearch") String sSearch,
			@RequestParam("iDisplayStart") int iDisplayStart,
			@RequestParam("id_tgl_smt") UUID id_tgl_smt
            ) {
		
		PeranPengguna peranPengguna = (PeranPengguna) session.getAttribute("userRoleSession");
		
		UUID idSatMan = peranPengguna.getSatMan().getIdSatMan();
		String filter = "pemb.aPembTerhapus = false and satMan.idSatMan ='"+idSatMan+"'";
		if(id_tgl_smt!=null) filter+= " AND tglSmt.idTglSmt = '"+id_tgl_smt+"'";
		Datatable thnAjaranDatatable = pembService.getdatatable(sEcho, iDisplayLength, iDisplayStart, iSortCol_0, sSortDir_0, sSearch,filter);
		return thnAjaranDatatable;
	}
	
	
	@RequestMapping(value = "/pembabsensi", method = RequestMethod.POST)
	public @ResponseBody AjaxResponse pembabsensi(
			HttpSession session,
			@RequestParam("idThnAjaran") UUID idThnAjaran,
			@RequestParam("idSmt") UUID idSmt,
			@RequestParam("idSatMan") UUID idSatMan
            ) {
		
		List<Pemb> listPemb = pembService.get("thnAjaran.idThnAjaran='"+idThnAjaran+"' and smt.idSmt = '"+idSmt+"' and satMan.idSatMan ='"+idSatMan+"'");
		List<ThnAjaran> listThnAjaran = thnAjaranService.get("idThnAjaran='"+idThnAjaran+"'");
		List<String[]> aData = new ArrayList<String[]>();
		for (Pemb pemb : listPemb) {
			String[] pembString = new String[9]; 
			List<PendidikPengajar> listPendidikPengajar = pendidikPengajarService.get("pemb.idPemb ='"+pemb.getIdPemb()+"' and pendidikPengajar.aPendidikPengajarTerhapus = false and pendidikPengajar.aPendidikPengajarKetua = true");
			List<PertemuanPembelajaran> listPertemuanPembelajarans = pertemuanPembelajaranService.get("pemb.idPemb = '"+pemb.getIdPemb()+"'");
			Double persentase = listPertemuanPembelajarans.size() * 100.0 /pemb.getTglSmt().getSmt().getJmlPertemuan();
			persentase = Math.round(persentase * 100) / 100.0;
			pembString[0] = String.valueOf(pemb.getMk().getKodeMK());
			pembString[1] = String.valueOf(pemb.getMk().getNamaMK());
			pembString[2] = String.valueOf(pemb.getNmPemb());
			pembString[3] = String.valueOf(listPendidikPengajar.size()>0?listPendidikPengajar.get(0).getPtk().getNmPtk():"");
			pembString[4] = String.valueOf(pemb.getTglSmt().getSmt().getJmlPertemuan());
			pembString[5] = String.valueOf(listPertemuanPembelajarans.size()>pemb.getTglSmt().getSmt().getJmlPertemuan()?pemb.getTglSmt().getSmt().getJmlPertemuan():listPertemuanPembelajarans.size());
			pembString[6] = String.valueOf(persentase>100?100.00:persentase)+" %"+(listThnAjaran.get(0).getPersenMinimPertemuan()>persentase?"<br/><br/><label class='label label-danger'>Pertemuan kurang</label>":"");
			pembString[7] = String.valueOf(listThnAjaran.get(0).getPersenMinimPertemuan())+" %";
			pembString[8] = String.valueOf(pemb.getIdPemb());
			aData.add(pembString);
		}
		return new AjaxResponse("ok","Memuat data",aData);
	}
	
}
