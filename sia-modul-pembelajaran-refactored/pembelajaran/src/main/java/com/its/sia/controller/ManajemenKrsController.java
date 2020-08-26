package com.its.sia.controller;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.PageContext;
import javax.validation.Valid;
import javax.validation.Validator;

import net.sf.cglib.core.Local;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import scala.annotation.meta.getter;

import com.sia.modul.domain.AnggotaRombel;
import com.sia.modul.domain.AturanPengganti;
import com.sia.modul.domain.BatasAmbilSks;
import com.sia.modul.domain.Ipk;
import com.sia.modul.domain.Ips;
import com.sia.modul.domain.Krs;
import com.sia.modul.domain.Kurikulum;
import com.sia.modul.domain.MK;
import com.sia.modul.domain.Pd;
import com.sia.modul.domain.Pemb;
import com.sia.modul.domain.Pembayaran;
import com.sia.modul.domain.PendidikPengajar;
import com.sia.main.domain.Pengguna;
import com.sia.main.domain.PeranPengguna;
import com.sia.modul.domain.PrasyaratMK;
import com.sia.modul.domain.Ptk;
import com.sia.modul.domain.Rombel;
import com.sia.modul.domain.SatMan;
import com.sia.modul.domain.SatManMK;
import com.sia.modul.domain.Smt;
import com.sia.modul.domain.TglSmt;
import com.sia.modul.domain.ThnAjaran;
import com.its.sia.service.AjaxResponse;
import com.its.sia.service.AturanPenggantiService;
import com.its.sia.service.BatasAmbilSksService;
import com.its.sia.service.Datatable;
import com.its.sia.service.IpkService;
import com.its.sia.service.IpsService;
import com.its.sia.service.IpsServiceImpl;
import com.its.sia.service.KrsService;
import com.its.sia.service.KurikulumService;
import com.its.sia.service.MKService;
import com.its.sia.service.ManajemenKrsService;
import com.its.sia.service.PdService;
import com.its.sia.service.PembService;
import com.its.sia.service.PembayaranService;
import com.its.sia.service.PendidikPengajarService;
import com.its.sia.service.PeranPenggunaService;
import com.its.sia.service.PrasyaratMKService;
import com.its.sia.service.PtkService;
import com.its.sia.service.SatManMKService;
import com.its.sia.service.SatManService;
import com.its.sia.service.SmtService;
import com.its.sia.service.TglSmtService;
import com.its.sia.service.ThnAjaranService;

@Controller
@RequestMapping(value = "/karturencanastudi")
public class ManajemenKrsController {

	private static final Logger logger = LoggerFactory.getLogger(ManajemenKrsController.class);
		
	@Autowired
	private KrsService krsService;
	
	@Autowired
	private MKService mkService;
	
	@Autowired
	private TglSmtService tglSmtService;

	@Autowired
	private ThnAjaranService thnAjaranService;
	
	@Autowired
	private SmtService smtService;
	
	@Autowired
	private KurikulumService kurikulumService;
	
	@Autowired
	private PendidikPengajarService pendidikPengajarService;
	
	@Autowired
	private PembService pembService;
	
	@Autowired
	private SatManService satManService;
	
	@Autowired
	private PdService pdService;
	
	@Autowired
	private PtkService ptkService;
	
	@Autowired
	private ManajemenKrsService manajemenKrsService;

	@Autowired
	private AturanPenggantiService aturanPenggantiService;
	
	@Autowired
	private IpsService ipsService;
	
	@Autowired
	private IpkService ipkService;
	
	@Autowired
	private BatasAmbilSksService batasAmbilSksService;

	@Autowired
	private PembayaranService pembayaranService;
	
	@Autowired
	private PrasyaratMKService prasyaratMKService;

	@Autowired
	private SatManMKService satManMKService;
	
	@Autowired
	private PeranPenggunaService peranPenggunaService;
		
	@Secured(value = { "ROLE_Pendidik" })
	@RequestMapping(value = "/penyetujuan/", method = RequestMethod.GET)
	public ModelAndView penyetujuan(HttpSession session, Locale locale, Model model) {
		ModelAndView mav = new ModelAndView();

		PeranPengguna peranPengguna = (PeranPengguna) session.getAttribute("userRoleSession");
		Ptk ptk = new Ptk();
		ptk.setaPtkTerhapus(peranPengguna.getPengguna().getPtk().isPtkTerhapus());
		ptk.setIdPtk(peranPengguna.getPengguna().getPtk().getIdPtk());
		ptk.setNipPtk(peranPengguna.getPengguna().getPtk().getNiPtk());
		ptk.setNmPtk(peranPengguna.getPengguna().getPtk().getNamaPtk());
		ptk.setStatusPtk(peranPengguna.getPengguna().getPtk().isStatusPtk());
		
		TglSmt smtAktif = manajemenKrsService.getPeriodeAktif();
		UUID idSatMan = peranPengguna.getSatMan().getIdSatMan();

		List<SatMan> listSatman = pembService.getSatManKurikulum("satMan.idSatMan ='"+idSatMan+"' and tglSmt.aTglSmtAktif = true");
		
		Boolean dibatalkan = manajemenKrsService.dapatDibatalkan(idSatMan);
		Boolean disetujui = manajemenKrsService.dapatDirubah(idSatMan);
		
		/*Memuat Pembelajaran*/
		List<List<Pemb>> listListPemb = new ArrayList<List<Pemb>>();
		List<List<SatManMK>> listListSatManMK = new ArrayList<List<SatManMK>>();
		for (SatMan satManleaf : listSatman) {
			List<SatManMK> listSatManMK = new ArrayList<SatManMK>();
			List<Pemb> listPemb = pembService.getPembInSatMan("tglSmt.aTglSmtAktif = true and satMan.idSatMan ='"+idSatMan+"' "
					+ "and milikSatMan.idSatMan = '"+satManleaf.getIdSatMan()+"' and pemb.aPembTerhapus = false ","mk.namaMK asc, pemb.nmPemb asc");
			for (Pemb pemb : listPemb) {
				List<SatManMK> query = satManMKService.get("mk.idMK = '"+pemb.getMk().getIdMK()+"' and satManUntuk.idSatMan = '"+idSatMan+"'");
				if(query.size()>0) listSatManMK.add(query.get(0));
				else listSatManMK.add(null);
			}
			listListSatManMK.add(listSatManMK);
			listListPemb.add(listPemb);
		}
		/*memuat tahun ajaran*/
		List<ThnAjaran>  semuaThnAjaran= thnAjaranService.get("aThnAjaranTerhapus = false","thnThnAjaran desc");
	
		/*memuat semester*/
		List<Smt>  semuaSmt= smtService.get("aSmthapus = false","jenisSmt desc");
		
		mav.setViewName("PenyetujuanKrs");
		List<Integer> listAngkatanPd = pdService.getAngkatan();
		mav.addObject("listAngkatanPd",listAngkatanPd);
		mav.addObject("dibatalkan",dibatalkan);
		mav.addObject("disetujui",disetujui);
		mav.addObject("listListPemb", listListPemb);
		mav.addObject("listListSatManMK", listListSatManMK);
		mav.addObject("listSatman", listSatman);
		mav.addObject("semuaThnAjaran", semuaThnAjaran);
		mav.addObject("semuaSmt", semuaSmt);
		mav.addObject("smtAktif", smtAktif);
		mav.addObject("ptk", ptk);
		mav.addObject ("menuActive","Persetujuan Kartu Rencana Studi");
		return mav;
	}
	
	
	@RequestMapping(value = "/mkharusdiulang/{idPd}", method = RequestMethod.GET)
	public ModelAndView mkharusdiulang(HttpSession session,Locale locale, @PathVariable("idPd") String idPd) {
		ModelAndView mav = new ModelAndView();
		
		Pd pd = pdService.getById(UUID.fromString(idPd));
		com.sia.modul.domain.PeranPengguna peranPengguna = peranPenggunaService.getByPenggunaPeran(pd.getIdPd(),"Peserta Didik");
		List<Krs> listKrs = krsService.get("pd.idPd = '"+pd.getIdPd()+"' and krs.aKrsLulus = false and krs.aKrsTerhapus = false "
				+ "and krs.aKrsBatal = false and krs.konversiNilai != null ");
				//+ "and tglSmt.tglAkhirPenilaian < '"+LocalDate.now().toString("yyyy-MM-dd")+"' ");
		List<Long> listTempuh = new ArrayList<Long>();
		List<SatManMK> listSatManMK = new ArrayList<SatManMK>();
		for (Krs krs : listKrs) {
			List<SatManMK> query = satManMKService.get("mk.idMK= '"+krs.getPemb().getMk().getIdMK()+"' and satManUntuk.idSatMan='"+peranPengguna.getSatMan().getIdSatMan()+"'");
			if(query.size()>0) listSatManMK.add(query.get(0));
			else listSatManMK.add(null);
			Long tempuh = krsService.count("pd.idPd = '"+pd.getIdPd()+"' and mk.idMK = '"+krs.getPemb().getMk().getIdMK()+"' and krs.aKrsTerhapus = false and krs.aKrsBatal = false "
					+ "and krs.waktuAmbil < '"+krs.getWaktuAmbil().toString("yyyy-MM-dd HH:mm:ss")+"'");
			listTempuh.add(tempuh+1);
		}
		mav.setViewName("MKHarusDiulang");
		mav.addObject("listKrs",listKrs);
		mav.addObject("listTempuh",listTempuh);
		mav.addObject("listSatManMK",listSatManMK);
		return mav;
	}
	
	
	@RequestMapping(value = "/mkmelanggarprasyarat/{idPd}", method = RequestMethod.GET)
	public ModelAndView mkmelanggarprasyarat(HttpSession session,Locale locale, @PathVariable("idPd") String idPd) {
		ModelAndView mav = new ModelAndView();
		
		Pd pd = pdService.getById(UUID.fromString(idPd));
		List<Krs> listKrs = krsService.get("pd.idPd = '"+pd.getIdPd()+"' and krs.aKrsTerhapus = false "
				+ "and krs.aKrsBatal = false ");
		List<MK> listMK = new ArrayList<MK>();
		List<MK> listMKPrasyarat = new ArrayList<MK>();
		for (Krs krs : listKrs) {
			List<PrasyaratMK> listPrasyarat = prasyaratMKService.get("childMK.idMK='"+krs.getPemb().getMk().getIdMK()+"'");
			for (PrasyaratMK prasyaratMK : listPrasyarat) {
				List<Krs> listPrasyaratMK = krsService.get("pd.idPd = '"+pd.getIdPd()+"' and krs.aKrsTerhapus = false "
						+ "and krs.aKrsBatal = false and mk.idMK='"+prasyaratMK.getParentMK().getIdMK()+"' "
						+ "and tglSmt.tglAkhirPenilaian < '"+LocalDate.now().toString("yyyy-MM-dd")+"' ","krs.waktuAmbil desc");
				if(listPrasyaratMK.size()==0 || listPrasyaratMK.get(0).isaKrsLulus()==false)
				{
					Integer index=0;
					while(index<listMK.size() && (listMK.get(index).getIdMK()!= krs.getPemb().getMk().getIdMK() || listMKPrasyarat.get(index).getIdMK()!=prasyaratMK.getIdPrasyaratMK()))
					{
						index++;
					}
					if(index==listMK.size())
					{
						listMK.add(krs.getPemb().getMk());
						listMKPrasyarat.add(prasyaratMK.getParentMK());
					}
				}
			}
		}
		
		mav.setViewName("MKMelanggarPrasyarat");
		mav.addObject("listMK",listMK);
		mav.addObject("listMKPrasyarat",listMKPrasyarat);
		return mav;
	}
	
	@Secured(value = { "ROLE_Peserta Didik" })
	@RequestMapping(value = "/susun/", method = RequestMethod.GET)
	public ModelAndView susun(HttpSession session, Locale locale, Model model) {
		ModelAndView mav = new ModelAndView();
		
		Boolean dapatDisusun = true;
		Boolean disetujui = false;
		/*Memuat peserta didik*/
		PeranPengguna peranPengguna = (PeranPengguna)session.getAttribute("userRoleSession");
		Pd pd = new Pd();
		pd.setAngkatanPd(peranPengguna.getPengguna().getPd().getAngkatanPd());
		pd.setaPdTerhapus(peranPengguna.getPengguna().getPd().isStatusKeaktifanPd());
		pd.setIdPd(peranPengguna.getPengguna().getPd().getIdPd());
		pd.setNimPd(peranPengguna.getPengguna().getPd().getNiPd());
		pd.setNmPd(peranPengguna.getPengguna().getPd().getNamaPd());
		Ptk ptk = new Ptk();
		ptk.setaPtkTerhapus(peranPengguna.getPengguna().getPd().getPtk().isPtkTerhapus());
		ptk.setIdPtk(peranPengguna.getPengguna().getPd().getPtk().getIdPtk());
		ptk.setNipPtk(peranPengguna.getPengguna().getPd().getPtk().getNiPtk());
		ptk.setNmPtk(peranPengguna.getPengguna().getPd().getPtk().getNamaPtk());
		ptk.setStatusPtk(peranPengguna.getPengguna().getPd().getPtk().isStatusPtk());
		pd.setPtk(ptk);
		
		SatMan satMan2 = new SatMan();
		satMan2.setIdSatMan(peranPengguna.getSatMan().getIdSatMan());
		satMan2.setaSatManAktif(peranPengguna.getSatMan().isaSatManAktif());
		satMan2.setIdSatManInduk(peranPengguna.getSatMan().getSatManInduk().getIdSatMan());
		satMan2.setNmSatMan(peranPengguna.getSatMan().getNmSatMan());
		satMan2.setSatManHasKurikulum(peranPengguna.getSatMan().isSatManHasKurikulum());
				
		/*memuat satuan manajemen dan satuan manajemen diatasnya*/
		SatMan satMan = satMan2;
		UUID idSatMan = satMan.getIdSatMan();
		List<SatMan> listSatman = pembService.getSatManKurikulum("satMan.idSatMan ='"+idSatMan+"' and tglSmt.aTglSmtAktif = true");
		
		/*Memuat KRS*/
		List<Krs> listKrs = krsService.get("krs.aKrsTerhapus = false and krs.aKrsBatal = false and tglSmt.aTglSmtAktif = true and pd.idPd ='"+pd.getIdPd()+"' and pemb.aPembTerhapus = false","mk.namaMK asc, pemb.nmPemb asc");
		
		/*Memuat jumalh sks semetara*/
		Integer sksTerambil = 0;
		for (Krs krs : listKrs) {
			sksTerambil+=krs.getPemb().getMk().getJumlahSKS();
		}
		
		/*Melihat KRS dapat disusun*/
		dapatDisusun = manajemenKrsService.dapatDisusun(idSatMan, pd.getIdPd());
		
		/*Melihat KRS telah di setujui*/
		List<Krs> queryResult = krsService.get("krs.aKrsDisetujui = true and krs.aKrsTerhapus = false and tglSmt.aTglSmtAktif = true and pd.idPd ='"+pd.getIdPd()+"'");
		
		if(queryResult.size()>0) 
		{
			dapatDisusun = false;
			disetujui = true;
		}
		/*melihat apakah peserta didik dapat menyusun KRS dan pada tanggal yang dialokasikan*/
		List<List<Pemb>> listListPemb = new ArrayList<List<Pemb>>();
		List<List<SatManMK>> listListSatManMK = new ArrayList<List<SatManMK>>();
		TglSmt smtAktif = manajemenKrsService.getPeriodeAktif();
		AturanPengganti aturanPengganti = null;
		if(smtAktif!=null)
		{
			List<AturanPengganti> listAturanPengganti = aturanPenggantiService.get("tglSmt.idTglSmt = '"+smtAktif.getIdTglSmt()+"' and satMan.idSatMan ='"+idSatMan+"'");
			if(listAturanPengganti.size()>0)
			{
				aturanPengganti = listAturanPengganti.get(0);
			}
		}
		
		/*Memuat Pembelajaran*/
		for (SatMan satManleaf : listSatman) {
			List<SatManMK> listSatManMK = new ArrayList<SatManMK>();
			List<Pemb> listPemb = pembService.getPembInSatMan("tglSmt.aTglSmtAktif = true and satMan.idSatMan ='"+idSatMan+"' "
					+ "and milikSatMan.idSatMan = '"+satManleaf.getIdSatMan()+"' and pemb.aPembTerhapus = false ","mk.namaMK asc, pemb.nmPemb asc");
			for (Pemb pemb : listPemb) {
				List<SatManMK> query = satManMKService.get("mk.idMK = '"+pemb.getMk().getIdMK()+"' and satManUntuk.idSatMan = '"+idSatMan+"'");
				if(query.size()>0) listSatManMK.add(query.get(0));
				else listSatManMK.add(null);
			}
			listListSatManMK.add(listSatManMK);
			//System.out.println(satManleaf.getNmSatMan()+" "+listPemb.get(0).getNmPemb());
			listListPemb.add(listPemb);
		}
		
		/*memuat ips terakhir*/
		List<Ips> listIps = ipsService.get("pd.idPd = '"+pd.getIdPd()+"' and smt.jenisSmt <2", "ips.tglBuatIps desc", 1, -1);
		Ips ipsTerakhir = listIps.size()>0?listIps.get(0):null;
		if(ipsTerakhir != null) 
		{
			ipsTerakhir.setNilaiIps( Math.round( ipsTerakhir.getNilaiIps() * 100.0) / 100.0);	
		}
		
		/*memuat ipk*/
		Ipk ipk = ipkService.getByPd(pd);
		if(ipk != null) 
		{
			ipk.setNilaiIpk( Math.round( ipk.getNilaiIpk() * 100.0) / 100.0);	
		}
		
		/*memuat batas pengambilan*/
		List<BatasAmbilSks> listBatas = batasAmbilSksService.get("batasBawahIps <= "+(ipsTerakhir==null?"0":ipsTerakhir.getNilaiIps().toString()),"batasPengambilanSks desc");
		
		/*memuat tahun ajaran*/
		List<ThnAjaran>  semuaThnAjaran= thnAjaranService.get("aThnAjaranTerhapus = false","thnThnAjaran desc");
	
		/*memuat semester*/
		List<Smt>  semuaSmt= smtService.get("aSmthapus = false","jenisSmt desc");
		
		/*Memuat status pembayaran*/
		Boolean statusPembayaran = false;
		if(smtAktif!=null)
		{
			List<Pembayaran> listPembayaran = pembayaranService.get("tglSmt.idTglSmt = '"+smtAktif.getIdTglSmt()+"'");
			statusPembayaran = listPembayaran.size()>0?true:false;
		}
		/*Menghitung Tingkat Mhs*/
		Integer tingkat = smtAktif!=null?(smtAktif.getThnAjaran().getThnThnAjaran()-pd.getAngkatanPd())*2:0;
		if(smtAktif!=null && smtAktif.getSmt().getJenisSmt()==0)
			tingkat+=1;
		else if(smtAktif!=null && smtAktif.getSmt().getJenisSmt()==1)
			tingkat+=2;
		else tingkat = null;
		
		mav.setViewName("KRS");
		mav.addObject("listSatman", listSatman);
		mav.addObject("dapatDisusun", dapatDisusun);
		mav.addObject("statusPembayaran", statusPembayaran);
		mav.addObject("disetujui", disetujui);
		mav.addObject("pd", pd);
		mav.addObject("listListPemb", listListPemb);
		mav.addObject("listListSatManMK", listListSatManMK);
		mav.addObject("smtAktif", smtAktif);
		mav.addObject("aturanPengganti", aturanPengganti);
		mav.addObject("listKrs", listKrs);
		mav.addObject("ipsTerakhir", ipsTerakhir);
		mav.addObject("ipk", ipk);
		mav.addObject("tingkat", tingkat);
		mav.addObject("batasAmbil", listBatas.size()>0?listBatas.get(0):null);
		mav.addObject("sksTerambil", sksTerambil);
		mav.addObject("semuaThnAjaran", semuaThnAjaran);
		mav.addObject("semuaSmt", semuaSmt);
		
		mav.addObject ("menuActive","Kartu Rencana Studi");
		
		return mav;
	}
	
	
	@RequestMapping(value = "/getkrsperiode", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse getkrsperiode(HttpSession session, @RequestParam("idThnAjaran") UUID idThnAjaran,@RequestParam("idSmt") UUID idSmt) {
		
		PeranPengguna peranPengguna = (PeranPengguna) session.getAttribute("userRoleSession");
		
		UUID idSatMan = peranPengguna.getSatMan().getIdSatMan();
		return manajemenKrsService.getKrsPeriode(idThnAjaran, idSmt, peranPengguna.getPengguna().getPd().getIdPd(), idSatMan);
    }
	
	
	@RequestMapping(value = "/ptkgetkrsperiode", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse getkrsperiode(HttpSession session,@RequestParam("idPd") UUID idPd,@RequestParam("idThnAjaran") UUID idThnAjaran,@RequestParam("idSmt") UUID idSmt) {
		PeranPengguna peranPengguna = (PeranPengguna) session.getAttribute("userRoleSession");
				
		return manajemenKrsService.ptkGetKrsPeriode(idThnAjaran, idSmt, idPd, peranPengguna.getPengguna().getPtk().getIdPtk());
    }
	
	
	@RequestMapping(value = "/pdjson", method = RequestMethod.POST)
	public @ResponseBody Datatable pdjson(
			HttpSession session,
			@RequestParam("sEcho") String sEcho, 
			@RequestParam("iDisplayLength") int iDisplayLength,
            @RequestParam("iSortCol_0") int iSortCol_0,
            @RequestParam("sSortDir_0") String sSortDir_0,
            @RequestParam("sSearch") String sSearch,
			@RequestParam("iDisplayStart") int iDisplayStart,
			@RequestParam("angkatanPd") String angkatanPd,
			@RequestParam("belumSetuju") boolean belumSetuju
            ) {
		
		PeranPengguna peranPengguna = (PeranPengguna) session.getAttribute("userRoleSession");
		
		
		String filter = "CAST (pd.angkatanPd as string) LIKE '%"+angkatanPd+"%' and pd.aPdTerhapus = false";
		filter+= " AND ptk.idPtk = '"+peranPengguna.getPengguna().getPtk().getIdPtk()+"' ";
		Datatable pdDatatable;
		if(belumSetuju == false)
		{
			filter+= " and satMan.idSatMan = '"+peranPengguna.getSatMan().getIdSatMan()+"'";
			pdDatatable = pdService.getKrsSetuju(sEcho, iDisplayLength, iDisplayStart, iSortCol_0, sSortDir_0, sSearch,filter);
		}
		else 
		{
			filter+= " and krs.aKrsTerhapus = false and krs.aKrsBatal = false and krs.aKrsDisetujui = false and"
					+ " pd in (select pd from PeranPengguna peranPengguna join "
				+ " peranPengguna.peran peran "
				+ " join peranPengguna.pengguna pengguna join peranPengguna.satMan satMan "
				+ " where satMan.idSatMan = '"+peranPengguna.getSatMan().getIdSatMan()+"' and pd.ptk='"+peranPengguna.getPengguna().getPtk().getIdPtk()+"'"
				+ " )";
			pdDatatable = krsService.getPdKrsDatatable(sEcho, iDisplayLength, iDisplayStart, iSortCol_0, sSortDir_0, sSearch, filter); 
		}
		return pdDatatable;
	}
	
	
	@RequestMapping(value = "/ambilmk", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse ambilmk(HttpSession session,@RequestParam("idPemb") UUID idPemb) {
		
		PeranPengguna peranPengguna = (PeranPengguna) session.getAttribute("userRoleSession");
		
		return manajemenKrsService.ambilmk(idPemb, peranPengguna.getPengguna().getPd().getIdPd(), peranPengguna.getSatMan().getIdSatMan());
    }
	
	
	@RequestMapping(value = "/pendidikambilmk", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse pendidikambilmk(HttpSession session,@RequestParam("idPemb") UUID idPemb,@RequestParam("idPd") UUID idPd) {
		
		PeranPengguna peranPengguna = (PeranPengguna) session.getAttribute("userRoleSession");
		
		return manajemenKrsService.pendidikambilmk(idPemb, peranPengguna.getPengguna().getPtk().getIdPtk(),idPd);
    }
	
	
	@RequestMapping(value = "/hapusmk", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse hapusmk(HttpSession session,@RequestParam("idKrs") UUID idKrs) {
		
		PeranPengguna peranPengguna = (PeranPengguna) session.getAttribute("userRoleSession");
		
		return manajemenKrsService.hapusmk(idKrs, peranPengguna.getPengguna().getPd().getIdPd(), peranPengguna.getSatMan().getIdSatMan());
    }
	
	
	@RequestMapping(value = "/dosenhapusmk", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse dosenhapusmk(HttpSession session,@RequestParam("idKrs") UUID idKrs,@RequestParam("idPd") UUID idPd) {
		
		PeranPengguna peranPengguna = (PeranPengguna) session.getAttribute("userRoleSession");
		
		return manajemenKrsService.dosenhapusmk(idKrs, peranPengguna.getPengguna().getPtk().getIdPtk(), idPd);
    }
	
	
	@RequestMapping(value = "/batalmk", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse batalmk(HttpSession session,@RequestParam("idKrs") UUID idKrs,@RequestParam("idPd") UUID idPd) {
		
		PeranPengguna peranPengguna = (PeranPengguna) session.getAttribute("userRoleSession");
		
		return manajemenKrsService.batalmk(idKrs, peranPengguna.getPengguna().getPtk().getIdPtk(), idPd);
    }
	
	
	@RequestMapping(value = "/getkrs", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse getkrs(HttpSession session,@RequestParam("idPd") UUID idPd) {
		
		PeranPengguna peranPengguna = (PeranPengguna) session.getAttribute("userRoleSession");
		
		return manajemenKrsService.getkrs(idPd, peranPengguna.getPengguna().getPtk().getIdPtk());
    }
		
	
	@RequestMapping(value = "/setujuikrs", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse setujuikrs(HttpSession session,@RequestParam("idPd") UUID idPd) {
		
		PeranPengguna peranPengguna = (PeranPengguna) session.getAttribute("userRoleSession");
		
		return manajemenKrsService.setujuikrs(idPd, peranPengguna.getPengguna().getPtk().getIdPtk());
    }
	
	
	@RequestMapping(value = "/batalsetuju", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse batalsetuju(HttpSession session,
    		@RequestParam("idPd") UUID idPd) {
		
		PeranPengguna peranPengguna = (PeranPengguna) session.getAttribute("userRoleSession");
		
		return manajemenKrsService.batalsetuju(idPd, peranPengguna.getPengguna().getPtk().getIdPtk());
    }
	
	
	@RequestMapping(value = "/cetak/{idPd}/{idThnAjaran}/{idSmt}", method = RequestMethod.GET)
    public ModelAndView generatePdfReport(ModelAndView modelAndView,
    		@PathVariable("idPd") UUID idPd,
    		@PathVariable("idThnAjaran") UUID idThnAjaran,
    		@PathVariable("idSmt") UUID idSmt,
    		HttpSession session){
 
		
        Map<String,Object> parameterMap = new HashMap<String,Object>();
        /*Memuat peserta didik*/
        Pd pd = null;
        if(idPd!=null) pd = pdService.getById(idPd);
        
        PeranPengguna peranPengguna = (PeranPengguna)session.getAttribute("userRoleSession");
        if(peranPengguna != null) {
			pd = new Pd();
			pd.setAngkatanPd(peranPengguna.getPengguna().getPd().getAngkatanPd());
			pd.setaPdTerhapus(peranPengguna.getPengguna().getPd().isStatusKeaktifanPd());
			pd.setIdPd(peranPengguna.getPengguna().getPd().getIdPd());
			pd.setNimPd(peranPengguna.getPengguna().getPd().getNiPd());
			pd.setNmPd(peranPengguna.getPengguna().getPd().getNamaPd());
			Ptk ptk = new Ptk();
			ptk.setaPtkTerhapus(peranPengguna.getPengguna().getPtk().isPtkTerhapus());
			ptk.setIdPtk(peranPengguna.getPengguna().getPtk().getIdPtk());
			ptk.setNipPtk(peranPengguna.getPengguna().getPtk().getNiPtk());
			ptk.setNmPtk(peranPengguna.getPengguna().getPtk().getNamaPtk());
			ptk.setStatusPtk(peranPengguna.getPengguna().getPtk().isStatusPtk());
			pd.setPtk(ptk);
        }
		
		SatMan satMan2 = new SatMan();
		satMan2.setIdSatMan(peranPengguna.getSatMan().getIdSatMan());
		satMan2.setaSatManAktif(peranPengguna.getSatMan().isaSatManAktif());
		satMan2.setIdSatManInduk(peranPengguna.getSatMan().getSatManInduk().getIdSatMan());
		satMan2.setNmSatMan(peranPengguna.getSatMan().getNmSatMan());
		satMan2.setSatManHasKurikulum(peranPengguna.getSatMan().isSatManHasKurikulum());
				
		SatMan satMan = satMan2;
		
		List<TglSmt> listTglSmt = tglSmtService.get("smt.idSmt='"+idSmt+"' and thnAjaran.idThnAjaran = '"+idThnAjaran+"'");
		if(listTglSmt.size()==0) { modelAndView.setViewName("redirect:/");return modelAndView;}
		TglSmt tglSmt = listTglSmt.get(0);
		
        List<Krs> listKrs = krsService.get("krs.aKrsTerhapus = false and krs.aKrsBatal = false and tglSmt.idTglSmt='"+tglSmt.getIdTglSmt()+"' "
        		+ " and pd.idPd ='"+pd.getIdPd()+"' and pemb.aPembTerhapus = false","mk.namaMK asc, pemb.nmPemb asc");
		
        Integer totalSks = 0;
		for (Krs krs : listKrs) {
			totalSks += krs.getPemb().getMk().getJumlahSKS();
		}
        
        System.out.println(listKrs.size());
        JRDataSource jRdataSource = new JRBeanCollectionDataSource(listKrs);
        
        parameterMap.put("pd", pd);
        parameterMap.put("satMan", satMan);
        parameterMap.put("tglSmt", tglSmt);
        parameterMap.put("totalSks", totalSks);
        parameterMap.put("datasource", jRdataSource);
 
        //pdfReport bean has ben declared in the jasper-views.xml file
        modelAndView = new ModelAndView("pdfKrs", parameterMap);
 
        return modelAndView;
 
    }//generatePdfReport
}
