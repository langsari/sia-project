package com.bustan.siakad.controller;

import java.io.Console;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Stack;
import java.util.UUID;

import javax.persistence.MapKey;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.poi.hssf.record.formula.Ptg;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bustan.siakad.service.Datatable;
import com.bustan.siakad.service.AjaxResponse;
import com.bustan.siakad.service.EkuivalensiMKService;
import com.bustan.siakad.service.HasilEkuivalensiPdService;
import com.bustan.siakad.service.KrsService;
import com.bustan.siakad.service.KurikulumService;
import com.bustan.siakad.service.MKService;
import com.bustan.siakad.service.RelasiEkuivalensiService;
import com.bustan.siakad.service.PdService;
import com.bustan.siakad.service.SatManMKService;
import com.bustan.siakad.service.SatManService;
import com.bustan.siakad.service.EkuivalensiPdService;
import com.bustan.siakad.service.PtkService;
import com.bustan.siakad.service.KrsHapusService;
import com.bustan.siakad.service.MKWajibPdService;
import com.bustan.siakad.service.PembService;
import com.sia.main.domain.PeranPengguna;
import com.sia.modul.domain.AlihJenjangMKTerakui;
import com.sia.modul.domain.CalonPD;
import com.sia.modul.domain.CheckedMK;
import com.sia.modul.domain.EkuivalenMK;
import com.sia.modul.domain.EkuivalensiMK;
import com.sia.modul.domain.EkuivalensiMKPekuivalensi;
import com.sia.modul.domain.HasilEkuivalensiPd;
import com.sia.modul.domain.Krs;
import com.sia.modul.domain.KrsCalonPD;
import com.sia.modul.domain.Kurikulum;
import com.sia.modul.domain.MK;
import com.sia.modul.domain.MKAlihjenjang;
import com.sia.modul.domain.MKBaruPdf;
import com.sia.modul.domain.MKEkuivalen;
import com.sia.modul.domain.MKLamaPdf;
import com.sia.modul.domain.MKLuar;
import com.sia.modul.domain.MKWajibAlihJenjang;
import com.sia.modul.domain.MKWajibCalonPD;
import com.sia.modul.domain.MKWajibEkuivalensi;
import com.sia.modul.domain.Pd;
import com.sia.modul.domain.PedomanEkuivalensi;
import com.sia.modul.domain.PrasyaratMK;
import com.sia.modul.domain.Ptk;
import com.sia.modul.domain.RelasiEkuivalensi;
import com.sia.modul.domain.RelasiMKAlihjenjang;
import com.sia.modul.domain.RelasiMKPekuivalensi;
import com.sia.modul.domain.SatMan;
import com.sia.modul.domain.EkuivalensiPd;
import com.sia.modul.domain.MKWajibPd;
import com.sia.modul.domain.KrsHapus;
import com.sia.modul.domain.Pemb;

@Controller
@Secured(value = {"ROLE_Tim Ekuivalensi"})
@RequestMapping(value = "/ekuivalensi/pesertadidik")
public class EkuivalensiPdController {
	
	@Autowired
	KurikulumService kurikulumService;
	
	@Autowired
	MKService mkService;
	
	@Autowired
	RelasiEkuivalensiService relasiEkuivalensiService;
	
	@Autowired
	EkuivalensiMKService ekuivalensiMKService;
	
	@Autowired
	PdService pdService;
	
	@Autowired
	HasilEkuivalensiPdService hasilEkuivalensiPdService;
	
	@Autowired
	SatManService satManService;
	
	@Autowired
	KrsService krsService;
	
	@Autowired
	EkuivalensiPdService ekuivalensiPdService;
	
	@Autowired
	PtkService ptkService;
	
	@Autowired
	KrsHapusService krsHapusService;
	
	@Autowired
	MKWajibPdService mkWajibPdService;
	
	@Autowired
	PembService pembService;
	
	@Autowired
	SatManMKService satManMKService;
	
	/*
	 * URL Persetujuan Ekuivalensi
	 * Hak Akses : Tim Ekuivalensi
	 * return ModelAndView
	 */	
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView pesertadidik(HttpSession session, Locale locale, Model model) {
		ModelAndView mav = new ModelAndView();
		
		String filter = "";//kurikulum.statusKurikulum = false AND ";
		//add filter by satman
		//Cek validasi
		PeranPengguna peranPengguna = (PeranPengguna) session.getAttribute("userRoleSession");
		if(peranPengguna.getSatMan().getSatManInduk() != null)
		{
			filter = "satMan.idSatMan = '"+peranPengguna.getSatMan().getIdSatMan()+"'";
		}
		else
		{
			List<SatMan> satManInduk = satManService.get("idSatManInduk = null");
			List<SatMan> listSatManProdi = satManService.listChild(satManInduk.get(0).getIdSatMan());
			filter += "(";
			for(int i=0;i<listSatManProdi.size();i++) 
			{
				filter += "satMan.idSatMan = '"+listSatManProdi.get(i).getIdSatMan().toString()+"'";
				if(i+1<listSatManProdi.size())
					filter += " OR ";
					
			}
			filter += ")";
		}
		List<Kurikulum> listKurikulumLama = kurikulumService.get(filter);
		List<Kurikulum> listKurikulumBaru = kurikulumService.get(filter);
		mav.addObject("listKurikulumLama", listKurikulumLama);
		mav.addObject("listKurikulumBaru", listKurikulumBaru);
		mav.setViewName("ekuivalensi/pesertadidik");
		
		mav.addObject ("menuActive","Persetujuan Ekuivalensi"); 
		
		return mav;
	}
	
	@RequestMapping(value = "/getidekuivalensipd", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse getidekuivalensipd(HttpSession session, @RequestParam("idPd") UUID idPd,@RequestParam("idKurikulum") UUID idKurikulum) {
		AjaxResponse response = new AjaxResponse();
		List<EkuivalensiPd> ekuivalensiPD = ekuivalensiPdService.get("pd.idPd = '"+idPd+"' AND kurikulumBaru.idKurikulum = '"+idKurikulum+"'");
		if(ekuivalensiPD.size() < 1)
			return new AjaxResponse("error","Data tidak ditemukan", null);
		response.setData(ekuivalensiPD.get(0).getIdEkuivalensiPd());
		response.setStatus("ok");
		return response;
	}
	
	@RequestMapping(value = "/cetak/{idEkuivalensiPd}", method = RequestMethod.GET)
    public ModelAndView generatePdfReport(ModelAndView modelAndView,
    		@PathVariable("idEkuivalensiPd") UUID idEkuivalensiPd,
    		HttpSession session){
 
		Map<String,Object> parameterMap = new HashMap<String,Object>();
        /*Memuat peserta didik*/
        EkuivalensiPd ekuivalensiPd = null;
        if(idEkuivalensiPd != null)
        	ekuivalensiPd = ekuivalensiPdService.getById(idEkuivalensiPd);
        else { modelAndView.setViewName("redirect:/");return modelAndView;}
        
        if(!ekuivalensiPd.isaEkuivalensi())
        	{ modelAndView.setViewName("redirect:/");return modelAndView;}
        
        String filter = "mk.statusMK = false";
		
		List<KrsHapus> listKrsHapus = krsHapusService.get("ekuivalensiPd.idEkuivalensiPd = '"+ekuivalensiPd.getIdEkuivalensiPd().toString()+"'");
		List<MKWajibPd> listMKWajibPd = mkWajibPdService.get("ekuivalensiPd.idEkuivalensiPd = '"+ekuivalensiPd.getIdEkuivalensiPd().toString()+"'");
		
		HashMap<UUID, Boolean> hashMapMKWajibPd = new HashMap<UUID, Boolean>();
		HashMap<UUID, Boolean> hashMapKrsHapus = new HashMap<UUID, Boolean>();
		
		for (KrsHapus krsHapus : listKrsHapus) {
			hashMapKrsHapus.put(krsHapus.getKrs().getPemb().getMk().getIdMK(), krsHapus.isaKrsHapus());
		}
		
		for (MKWajibPd mkWajibPd : listMKWajibPd) {
			hashMapMKWajibPd.put(mkWajibPd.getMk().getIdMK(), mkWajibPd.isaMKAmbil());
		}
		
		List<MK> listMKLama = mkService.get(filter + " AND kurikulum.idKurikulum = '"+ekuivalensiPd.getKurikulumLama().getIdKurikulum().toString()+"'");
		List<MK> listMKBaru = mkService.get(filter + " AND kurikulum.idKurikulum = '"+ekuivalensiPd.getKurikulumBaru().getIdKurikulum().toString()+"'");
		
		List<MKLamaPdf> listMKLamaPdf = new ArrayList<MKLamaPdf>();
		List<MKBaruPdf> listMKBaruPdf = new ArrayList<MKBaruPdf>();
		
		int max = listMKLama.size();
		if(max < listMKBaru.size())
			max = listMKBaru.size();
		for(int i=0;i<max;i++)
		{
			if(i<listMKLama.size())
			{
				MKLamaPdf mkLamaPdf = new MKLamaPdf();
				mkLamaPdf.setKodeMKLama(listMKLama.get(i).getKodeMK().toString());
				mkLamaPdf.setNamaMKLama(listMKLama.get(i).getNamaMK());
				mkLamaPdf.setSksMKLama(listMKLama.get(i).getJumlahSKS().toString());
				if(listMKLama.get(i).getSifatMK())
					mkLamaPdf.setSifatMKLama("Wajib");
				if(hashMapKrsHapus.containsKey(listMKLama.get(i).getIdMK()))
				{
					if(hashMapKrsHapus.get(listMKLama.get(i).getIdMK()))
						mkLamaPdf.setSksMKLama("4");
					else mkLamaPdf.setSksMKLama("1");				
				}
				else
				{
					List<Krs> listKrs = krsService.get("pd.idPd = '"+ekuivalensiPd.getPd().getIdPd().toString()+"' AND "
							+ "mk.idMK = '"+listMKLama.get(i).getIdMK().toString()+"' AND krs.aKrsDiulang = false");
					if(listKrs.size() > 0)
					{
						if(listKrs.get(0).isaKrsLulus())
							mkLamaPdf.setSksMKLama("1");
						else mkLamaPdf.setSksMKLama("2");
					}
					else mkLamaPdf.setSksMKLama("3");
				}
				listMKLamaPdf.add(mkLamaPdf);
			}
			
			if(i<listMKBaru.size())
			{

				MKBaruPdf mkBaruPdf = new MKBaruPdf();
				mkBaruPdf.setKodeMKBaru(listMKBaru.get(i).getKodeMK());
				mkBaruPdf.setNamaMKBaru(listMKBaru.get(i).getNamaMK());
				mkBaruPdf.setSksMKBaru(listMKBaru.get(i).getJumlahSKS().toString());
				if(listMKBaru.get(i).getSifatMK())
					mkBaruPdf.setSifatMKBaru("Wajib");
				else mkBaruPdf.setSifatMKBaru("Pilihan");
				if(hashMapMKWajibPd.containsKey(listMKBaru.get(i).getIdMK()))
				{
					if(hashMapMKWajibPd.get(listMKBaru.get(i).getIdMK()))
						mkBaruPdf.setStatusMKBaru("Ambil");
					else mkBaruPdf.setStatusMKBaru("Bebas");
				}
				else
				{
					if(listMKBaru.get(i).getSifatMK())
						mkBaruPdf.setStatusMKBaru("Bebas");
					else mkBaruPdf.setStatusMKBaru("Pilihan");					
				}
				listMKBaruPdf.add(mkBaruPdf);
			}
		}
		
//		JRDataSource dsKodeMKLama = new JRBeanCollectionDataSource(listMKLamaPdf);
		JRDataSource dsKodeMKBaru = new JRBeanCollectionDataSource(listMKBaruPdf);

		parameterMap.put("pd", ekuivalensiPd.getPd());
		parameterMap.put("ptk", ekuivalensiPd.getPtk());
		parameterMap.put("kurikulum", ekuivalensiPd.getKurikulumBaru());
        parameterMap.put("datasource", dsKodeMKBaru);

        modelAndView = new ModelAndView("pdfEkuivalensi", parameterMap);
 
        return modelAndView;
 
    }//generatePdfReport
	
	/* 
	 * Hak Akses : Tim Ekuivalensi
	 * Return datatable json relasi ekuivalensi
	 */
	
	@RequestMapping(value = "/json", method = RequestMethod.POST)
	public @ResponseBody Datatable json(HttpSession session,
			@RequestParam("sEcho") String sEcho, 
			@RequestParam("iDisplayLength") int iDisplayLength,
            @RequestParam("iSortCol_0") int iSortCol_0,
            @RequestParam("sSortDir_0") String sSortDir_0,
            @RequestParam("sSearch") String sSearch,
			@RequestParam("iDisplayStart") int iDisplayStart
            ) {
		
		String filter = "";//kurikulumLama.statusKurikulum = false AND kurikulumBaru.statusKurikulum = false AND ";
		PeranPengguna peranPengguna = (PeranPengguna) session.getAttribute("userRoleSession");
		if(peranPengguna.getPeran().getNamaPeran().compareToIgnoreCase("admin") != 0)
		{
			filter += "satManLama.idSatMan = '"+peranPengguna.getSatMan().getIdSatMan()+"' AND satManBaru.idSatMan = '"+peranPengguna.getSatMan().getIdSatMan()+"'";
		}
		else
		{
//			List<SatMan> satManInduk = satManService.get("idSatManInduk = null");
//			List<SatMan> listSatManProdi = satManService.listChild(satManInduk.get(0).getIdSatMan());
//			String filterLama = "";
//			String filterBaru = "";
//			for(int i=0;i<listSatManProdi.size();i++) 
//			{
//				filterLama += "satManLama.idSatMan = '"+listSatManProdi.get(i).getIdSatMan().toString()+"'";
//				filterBaru += "satManBaru.idSatMan = '"+listSatManProdi.get(i).getIdSatMan().toString()+"'";
//				if(i+1<listSatManProdi.size())
//				{
//					filterLama += " OR ";
//					filterBaru += " OR ";
//				}
//					
//			}
//			filter += "("+filterLama+" OR "+filterBaru+")";
		}
		Datatable relasiDatatable = relasiEkuivalensiService.getdatatable(sEcho, iDisplayLength, iDisplayStart, iSortCol_0, sSortDir_0, sSearch,filter);
		return relasiDatatable;
	}
	
	/* 
	 * Json get detail peserta didik
	 * Hak Akses : Tim Ekuivalensi
	 * Return AjaxResponse class Peserta Didik
	 */

	@RequestMapping(value = "/getpddetail", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse getpddetail(HttpSession session, @RequestParam("idPd") UUID idPd) {
		AjaxResponse response;
		Pd pd = pdService.getById(idPd);
		if(pd == null) response = new AjaxResponse("error","Data tidak ditemukan",null);
		else
			response = new AjaxResponse("ok","Data ditemukan",pd);
			
        return response;
    }
	
	/* 
	 * Datatable Ekuivalensi Peserta Didik
	 * Hak Akses : Tim Ekuivalensi
	 * Return Datatable
	 */

	@RequestMapping(value = "/jsonekuivalensipd", method = RequestMethod.POST)
	public @ResponseBody Datatable jsonekuivalensipd(
			HttpSession session,
			@RequestParam("sEcho") String sEcho, 
			@RequestParam("iDisplayLength") int iDisplayLength,
            @RequestParam("iSortCol_0") int iSortCol_0,
            @RequestParam("sSortDir_0") String sSortDir_0,
            @RequestParam("sSearch") String sSearch,
			@RequestParam("iDisplayStart") int iDisplayStart,
			@RequestParam("idKurikulumLama") UUID idKurikulumLama,
			@RequestParam("idKurikulumBaru") UUID idKurikulumBaru
            ) {
		//add filter belum lulus
		String filter = "";
		PeranPengguna peranPengguna = (PeranPengguna) session.getAttribute("userRoleSession");

		//add filter status ekuivalensi
		if(peranPengguna.getPeran().getNamaPeran().compareToIgnoreCase("admin") != 0)
		{
			filter = "ptk.idPtk = '"+peranPengguna.getPengguna().getPtk().getIdPtk()+"'";
		}		
		
		if(idKurikulumLama != null)
		{
			if(filter.length() > 1)
				filter += " AND ";
//			filter += "kurikulumLama.statusKurikulum = false AND kurikulumLama.idKurikulum = '"+idKurikulumLama.toString()+"'";
			filter += "kurikulumLama.idKurikulum = '"+idKurikulumLama.toString()+"'";
		}
		if(idKurikulumBaru != null)
		{
			if(filter.length() > 1)
				filter += " AND ";
//			filter += "kurikulumBaru.statusKurikulum = false AND kurikulumBaru.idKurikulum = '"+idKurikulumBaru.toString()+"'";
			filter += "kurikulumBaru.idKurikulum = '"+idKurikulumBaru.toString()+"'";
		}

		Datatable relasiDatatable = ekuivalensiPdService.getdatatable(sEcho, iDisplayLength, iDisplayStart, iSortCol_0, sSortDir_0, sSearch,filter);
		return relasiDatatable;
	}
	
	/* 
	 * Datatable Peserta Didik
	 * Hak Akses : Tim Ekuivalensi
	 * Return Datatable
	 */

	@RequestMapping(value = "/jsonpd", method = RequestMethod.POST)
	public @ResponseBody Datatable jsonpd(HttpSession session,
			@RequestParam("sEcho") String sEcho, 
			@RequestParam("iDisplayLength") int iDisplayLength,
            @RequestParam("iSortCol_0") int iSortCol_0,
            @RequestParam("sSortDir_0") String sSortDir_0,
            @RequestParam("sSearch") String sSearch,
			@RequestParam("iDisplayStart") int iDisplayStart,
			@RequestParam("idRelasi") String idRelasi
            ) {
		//add filter belum lulus
		String filter = "";
		
		PeranPengguna peranPengguna = (PeranPengguna) session.getAttribute("userRoleSession");
		//Add filter peserta didik yang belum ekuivalensi
		if(idRelasi != "")
		{
			String[] tmp = idRelasi.split(";");
			if(tmp.length != 2)
				return null;
			Kurikulum kurikulumLama = kurikulumService.getById(UUID.fromString(tmp[0]));
			Kurikulum kurikulumBaru = kurikulumService.getById(UUID.fromString(tmp[1]));
			if(kurikulumLama == null || kurikulumBaru == null || !kurikulumBaru.getSatMan().getIdSatMan().equals(kurikulumLama.getSatMan().getIdSatMan()))
				return null;
			List<EkuivalensiPd> listEkuivalensiPd = ekuivalensiPdService.get("kurikulumBaru.idKurikulum='"+kurikulumBaru.getIdKurikulum()+"' AND "
					+ "kurikulumLama.idKurikulum='"+kurikulumLama.getIdKurikulum()+"'");
			for (EkuivalensiPd ekuivalensiPd : listEkuivalensiPd) {
				filter += "pd.idPd != '"+ekuivalensiPd.getPd().getIdPd()+"' AND ";
			}
			
			
			List<SatMan> listSatMan = satManMKService.getSatManDistinct("kurikulum.idKurikulum = '"+tmp[1]+"'"); 
			
			if(listSatMan.size()>0)
				filter += "(";
			for(int i=0;i<listSatMan.size();i++) 
			{
				filter += "satMan.idSatMan = '"+listSatMan.get(i).getIdSatMan().toString()+"'";
				if(i+1<listSatMan.size())
					filter += " OR ";					
			}
			if(listSatMan.size()>0)
				filter += ")";
			
			if(listSatMan.size() < 1)
				filter += "satMan.idSatMan = '"+peranPengguna.getSatMan().getIdSatMan()+"'";
		}
	
		Datatable pdDatatable = pdService.getdatatable(sEcho, iDisplayLength, iDisplayStart, iSortCol_0, sSortDir_0, sSearch,filter);
		return pdDatatable;
	}
	
	/* 
	 * Json Generate Ekuivalensi Matakuliah dari Peserta Didik (Create)
	 * Hak Akses : Tim Ekuivalensi
	 * Return AjaxResponse daftar matakuliah di kurikulum baru yang wajib diambil / dibebaskan
	 */	

	@RequestMapping(value = "/getmkekuivalensi", method = RequestMethod.POST)
	public @ResponseBody AjaxResponse getmkekuivalensi(HttpSession session, @RequestParam("idPd") UUID idPd, @RequestParam("idRelasi") String idRelasi) {
		
		AjaxResponse response = new AjaxResponse();		
		String filter = "mk.statusMK = false";
		Pd pd = pdService.getById(idPd);
		if(pd == null) return new AjaxResponse("error","Data tidak ditemukan1",null);
		String[] tmp = idRelasi.split(";");
		
		Kurikulum kurikulumLama = new Kurikulum();
		Kurikulum kurikulumBaru = new Kurikulum();
		if(tmp.length > 1)
		{
			kurikulumLama = kurikulumService.getById(UUID.fromString(tmp[0]));
			kurikulumBaru = kurikulumService.getById(UUID.fromString(tmp[1]));
		}
		else return new AjaxResponse("error","Data tidak ditemukan2",null);
		
		//Jika kurikulum memiliki satman yang berbeda
//		if(!kurikulumLama.getSatMan().getIdSatMan().equals(kurikulumBaru.getSatMan().getIdSatMan()))
//			return new AjaxResponse("error","Data tidak valid",null);
		
		//Cek satman pd dengan satman kurikulum
		//sesuaikan satman dengan pd
		
//		SatMan satManPD = satManService.getById(peran.get(0).getSatMan().getIdSatMan());
//		if(!satManPD.getIdSatMan().equals(kurikulumLama.getSatMan().getIdSatMan()))
//			return new AjaxResponse("error","Data tidak valid",null);
		
		PeranPengguna peranPengguna = (PeranPengguna) session.getAttribute("userRoleSession");
		//Jika Edit mode
		String tmpFilter = "pd.idPd = '"+idPd.toString()+"' AND kurikulumLama.idKurikulum = '"+tmp[0]+"' AND kurikulumBaru.idKurikulum = '"+tmp[1]+"'";
		if(peranPengguna.getSatMan().getSatManInduk() != null)
			tmpFilter += " AND ptk.idPtk = '"+peranPengguna.getPengguna().getPtk().getIdPtk().toString()+"'";
		List<EkuivalensiPd> ekuivalensiPd = ekuivalensiPdService.get(tmpFilter);
		if(ekuivalensiPd.size() == 1)
		{
			return getmkmkekuivalensi_edit(ekuivalensiPd.get(0));
		}	
		
		MKWajibEkuivalensi mkWajibEkuivalensi = new MKWajibEkuivalensi();		
		//Add filter krs lulus
		List<Krs> listKrsLulusPd = krsService.get("pd.idPd = '"+pd.getIdPd().toString()+"' AND krs.aKrsDiulang = false AND krs.aKrsLulus = true");
		HashMap<String, Boolean> hasMapKrs = new HashMap<String, Boolean>();
		for (Krs krs : listKrsLulusPd) 
		{
			if(krs.isaKrsLulus())
				hasMapKrs.put(krs.getPemb().getMk().getIdMK().toString(), true);
			else hasMapKrs.put(krs.getPemb().getMk().getIdMK().toString(), false);
		}
		
		//ORDER BY semester asc
		List<MK> listMKLama = mkService.get("kurikulum.idKurikulum = '"+kurikulumLama.getIdKurikulum().toString()+"' AND "+filter);
		String[] idMKLama = new String[listMKLama.size()];
		String[] kodeMKLama = new String[listMKLama.size()];
		String[] namaMKLama = new String[listMKLama.size()];
		String[] sksMKLama = new String[listMKLama.size()];
		String[] sifatMKLama = new String[listMKLama.size()];
		String[] statusMKLama = new String[listMKLama.size()];
		
		//Isi data mk di kurikulum lama
		for(int i=0;i<listMKLama.size();i++)
		{
			idMKLama[i] = listMKLama.get(i).getIdMK().toString();
			kodeMKLama[i] = listMKLama.get(i).getKodeMK();
			namaMKLama[i] = listMKLama.get(i).getNamaMK();
			sksMKLama[i] = listMKLama.get(i).getJumlahSKS().toString();
			sifatMKLama[i] = String.valueOf(listMKLama.get(i).getSifatMK());
			if(hasMapKrs.containsKey(listMKLama.get(i).getIdMK().toString()))
			{
				if(hasMapKrs.get(listMKLama.get(i).getIdMK().toString()))
					statusMKLama[i] = "1";
				else statusMKLama[i] = "2";
			}
				
			else statusMKLama[i] = "3";
		}		
		
		List<MK> listMKBaru = mkService.get("kurikulum.idKurikulum = '"+kurikulumBaru.getIdKurikulum().toString()+"' AND "+filter);
		String[] idMKBaru = new String[listMKBaru.size()];
		String[] kodeMKBaru = new String[listMKBaru.size()];
		String[] namaMKBaru = new String[listMKBaru.size()];
		String[] sksMKBaru = new String[listMKBaru.size()];
		String[] sifatMKBaru = new String[listMKBaru.size()];
		String[] statusMKBaru = new String[listMKBaru.size()];
		
//		List<MKWajibPd> listMKWajibPd = mkWajibPdService.get("pd.idPd = '"+pd.getIdPd().toString()+"'");
//		if(listMKWajibPd.size()==0)
//		{	
			List<RelasiEkuivalensi> listRelasi = relasiEkuivalensiService.get("kurikulumLama.idKurikulum = '"
			+kurikulumLama.getIdKurikulum().toString()+"' AND kurikulumBaru.idKurikulum = '"+kurikulumBaru.getIdKurikulum().toString()+"'");
			HashMap<String, Boolean> hasMapMKWajib = new HashMap<String, Boolean>();
			for (RelasiEkuivalensi relasiEkuivalensi : listRelasi) {
				//Jika true, lolos aturan relasi
				//Jika false, diwajibkan ambil mk baru
				if(!CekRelasi(relasiEkuivalensi, hasMapKrs))
				{
					List<MK> listMK = ekuivalensiMKService.getMKBaruDistinct(relasiEkuivalensi.getIdRelasiEkuivalensi());					
					for (MK mk : listMK) 
					{
						//Jika True (mk wajib), maka wajib ambil
						if(mk.getSifatMK())
							hasMapMKWajib.put(mk.getIdMK().toString(), true);
						else hasMapMKWajib.put(mk.getIdMK().toString(), false);
					}
				}
				else
				{
					List<MK> listMK = ekuivalensiMKService.getMKBaruDistinct(relasiEkuivalensi.getIdRelasiEkuivalensi());					
					for (MK mk : listMK) 
					{
						hasMapMKWajib.put(mk.getIdMK().toString(), false);						
					}
				}
			}			
			
			//Isi data mk di kurikulum baru
			for(int i=0;i<listMKBaru.size();i++)
			{
				idMKBaru[i] = listMKBaru.get(i).getIdMK().toString();
				kodeMKBaru[i] = listMKBaru.get(i).getKodeMK();
				namaMKBaru[i] = listMKBaru.get(i).getNamaMK();
				sksMKBaru[i] = listMKBaru.get(i).getJumlahSKS().toString();
				sifatMKBaru[i] = String.valueOf(listMKBaru.get(i).getSifatMK());
				
				//True untuk wajib ambil mk
				if(hasMapMKWajib.containsKey(listMKBaru.get(i).getIdMK().toString()))
				{
					if(hasMapMKWajib.get(listMKBaru.get(i).getIdMK().toString()))
						statusMKBaru[i] = "true";
					else statusMKBaru[i] = "false";
				}					
				else
				{
					if(listMKBaru.get(i).getSifatMK())
						statusMKBaru[i] = "true";
					else statusMKBaru[i] = "false";
				}	
			}	
//		}
//		else
//		{		
//			for(int i=0;i<listMKWajibPd.size();i++)
//			{
//				idMKBaru[i] = listMKWajibPd.get(i).getMk().getIdMK().toString();
//				kodeMKBaru[i] = listMKWajibPd.get(i).getMk().getKodeMK();
//				namaMKBaru[i] = listMKWajibPd.get(i).getMk().getNamaMK();
//				sksMKBaru[i] = listMKWajibPd.get(i).getMk().getJumlahSKS().toString();
//				sifatMKBaru[i] = String.valueOf(listMKWajibPd.get(i).getMk().getSifatMK());
//				statusMKBaru[i] = String.valueOf(listMKWajibPd.get(i).getMk().getStatusMK());
//			}
//		}
		mkWajibEkuivalensi.setIdKurikulumLama(kurikulumLama.getIdKurikulum().toString());
		mkWajibEkuivalensi.setIdKurikulumBaru(kurikulumBaru.getIdKurikulum().toString());
		mkWajibEkuivalensi.setIdPd(idPd.toString());
		mkWajibEkuivalensi.setIdMKLama(idMKLama);
		mkWajibEkuivalensi.setIdMKBaru(idMKBaru);
		mkWajibEkuivalensi.setKodeMKLama(kodeMKLama);
		mkWajibEkuivalensi.setKodeMKBaru(kodeMKBaru);
		mkWajibEkuivalensi.setNamaMKLama(namaMKLama);
		mkWajibEkuivalensi.setNamaMKBaru(namaMKBaru);
		mkWajibEkuivalensi.setSksMKLama(sksMKLama);
		mkWajibEkuivalensi.setSksMKBaru(sksMKBaru);
		mkWajibEkuivalensi.setSifatMKLama(sifatMKLama);
		mkWajibEkuivalensi.setSifatMKBaru(sifatMKBaru);
		mkWajibEkuivalensi.setStatusMKLama(statusMKLama);
		mkWajibEkuivalensi.setStatusMKBaru(statusMKBaru);
		
		response.setStatus("ok");
		response.setMessage("ok");
		response.setData(mkWajibEkuivalensi);
		return response;
	}
	
	/* 
	 * Json Generate Ekuivalensi Matakuliah dari Peserta Didik (Edit)
	 * Hak Akses : Tim Ekuivalensi
	 * Return AjaxResponse daftar matakuliah di kurikulum baru yang wajib diambil / dibebaskan
	 */	
	private AjaxResponse getmkmkekuivalensi_edit(EkuivalensiPd ekuivalensiPd)
	{
		String filter = "mk.statusMK = false";
		
		List<KrsHapus> listKrsHapus = krsHapusService.get("ekuivalensiPd.idEkuivalensiPd = '"+ekuivalensiPd.getIdEkuivalensiPd().toString()+"'");
		List<MKWajibPd> listMKWajibPd = mkWajibPdService.get("ekuivalensiPd.idEkuivalensiPd = '"+ekuivalensiPd.getIdEkuivalensiPd().toString()+"'");
		
		HashMap<UUID, Boolean> hashMapMKWajibPd = new HashMap<UUID, Boolean>();
		HashMap<UUID, Boolean> hashMapKrsHapus = new HashMap<UUID, Boolean>();
		
		for (KrsHapus krsHapus : listKrsHapus) {
			hashMapKrsHapus.put(krsHapus.getKrs().getPemb().getMk().getIdMK(), krsHapus.isaKrsHapus());
		}
		
		for (MKWajibPd mkWajibPd : listMKWajibPd) {
			hashMapMKWajibPd.put(mkWajibPd.getMk().getIdMK(), mkWajibPd.isaMKAmbil());
		}
		
		List<MK> listMKLama = mkService.get(filter + " AND kurikulum.idKurikulum = '"+ekuivalensiPd.getKurikulumLama().getIdKurikulum().toString()+"'");
		List<MK> listMKBaru = mkService.get(filter + " AND kurikulum.idKurikulum = '"+ekuivalensiPd.getKurikulumBaru().getIdKurikulum().toString()+"'");
		MKWajibEkuivalensi mkWajibEkuivalensi = new MKWajibEkuivalensi();
		
		String[] idMKLama = new String[listMKLama.size()];
		String[] kodeMKLama = new String[listMKLama.size()];
		String[] namaMKLama = new String[listMKLama.size()];
		String[] sksMKLama = new String[listMKLama.size()];
		String[] sifatMKLama = new String[listMKLama.size()];
		String[] statusMKLama = new String[listMKLama.size()];
		
		String[] idMKBaru = new String[listMKBaru.size()];
		String[] kodeMKBaru = new String[listMKBaru.size()];
		String[] namaMKBaru = new String[listMKBaru.size()];
		String[] sksMKBaru = new String[listMKBaru.size()];
		String[] sifatMKBaru = new String[listMKBaru.size()];
		String[] statusMKBaru = new String[listMKBaru.size()];
		
		int max = listMKLama.size();
		if(max < listMKBaru.size())
			max = listMKBaru.size();
		for(int i=0;i<max;i++)
		{
			if(i<listMKLama.size())
			{
				idMKLama[i] = listMKLama.get(i).getIdMK().toString();
				kodeMKLama[i] = listMKLama.get(i).getKodeMK();
				namaMKLama[i] = listMKLama.get(i).getNamaMK();
				sksMKLama[i] = listMKLama.get(i).getJumlahSKS().toString();
				sifatMKLama[i] = String.valueOf(listMKLama.get(i).getSifatMK());
				if(hashMapKrsHapus.containsKey(listMKLama.get(i).getIdMK()))
				{
					if(hashMapKrsHapus.get(listMKLama.get(i).getIdMK()))
						statusMKLama[i] = "4";
					else statusMKLama[i] = "1";					
				}
				else
				{
					List<Krs> listKrs = krsService.get("pd.idPd = '"+ekuivalensiPd.getPd().getIdPd().toString()+"' AND "
							+ "mk.idMK = '"+listMKLama.get(i).getIdMK().toString()+"' AND krs.aKrsDiulang = false");
					if(listKrs.size() > 0)
					{
						if(listKrs.get(0).isaKrsLulus())
							statusMKLama[i] = "1";
						else statusMKLama[i] = "2";
					}
					else statusMKLama[i] = "3";
				}
				
			}
			
			if(i<listMKBaru.size())
			{
				idMKBaru[i] = listMKBaru.get(i).getIdMK().toString();
				kodeMKBaru[i] = listMKBaru.get(i).getKodeMK();
				namaMKBaru[i] = listMKBaru.get(i).getNamaMK();
				sksMKBaru[i] = listMKBaru.get(i).getJumlahSKS().toString();
				sifatMKBaru[i] = String.valueOf(listMKBaru.get(i).getSifatMK());
				if(hashMapMKWajibPd.containsKey(listMKBaru.get(i).getIdMK()))
				{
					statusMKBaru[i] = String.valueOf(hashMapMKWajibPd.get(listMKBaru.get(i).getIdMK()));
				}
				else statusMKBaru[i] = "false";
			}
		}
		
		mkWajibEkuivalensi.setIdKurikulumLama(ekuivalensiPd.getKurikulumLama().getIdKurikulum().toString());
		mkWajibEkuivalensi.setIdKurikulumBaru(ekuivalensiPd.getKurikulumBaru().getIdKurikulum().toString());
		mkWajibEkuivalensi.setIdPd(ekuivalensiPd.getPd().getIdPd().toString());
		mkWajibEkuivalensi.setIdMKLama(idMKLama);
		mkWajibEkuivalensi.setIdMKBaru(idMKBaru);
		mkWajibEkuivalensi.setKodeMKLama(kodeMKLama);
		mkWajibEkuivalensi.setKodeMKBaru(kodeMKBaru);
		mkWajibEkuivalensi.setNamaMKLama(namaMKLama);
		mkWajibEkuivalensi.setNamaMKBaru(namaMKBaru);
		mkWajibEkuivalensi.setSksMKLama(sksMKLama);
		mkWajibEkuivalensi.setSksMKBaru(sksMKBaru);
		mkWajibEkuivalensi.setSifatMKLama(sifatMKLama);
		mkWajibEkuivalensi.setSifatMKBaru(sifatMKBaru);
		mkWajibEkuivalensi.setStatusMKLama(statusMKLama);
		mkWajibEkuivalensi.setStatusMKBaru(statusMKBaru);
		
		return new AjaxResponse("ok","Data ditemukan",mkWajibEkuivalensi);
	}
	
	/* 
	 * Ajax simpan persetujuan ekuivalensi
	 * Hak Akses : Tim Ekuivalensi
	 * Return null
	 */	

	@RequestMapping(value = "saveekuivalensi", method = RequestMethod.POST, produces="application/json")
    public @ResponseBody AjaxResponse saveekuivalensi(HttpSession session, @RequestBody CheckedMK checkedMK) {
		
		AjaxResponse response = new AjaxResponse();
		Pd pd = pdService.getById(UUID.fromString(checkedMK.getIdPd()));
		if(pd == null) return new AjaxResponse("error","Data tidak ditemukan",null);
		String[] tmp = checkedMK.getIdRelasi().split(";");
		if(tmp.length != 2) return new AjaxResponse("error","Data tidak ditemukan",null);			
		Kurikulum kurikulumLama = kurikulumService.getById(UUID.fromString(tmp[0]));
		if(kurikulumLama == null) return new AjaxResponse("error","Data tidak ditemukan",null);
		Kurikulum kurikulumBaru = kurikulumService.getById(UUID.fromString(tmp[1]));
		if(kurikulumBaru == null) return new AjaxResponse("error","Data tidak ditemukan",null);
		
		//Cek validasi
		if(!kurikulumLama.getSatMan().getIdSatMan().equals(kurikulumBaru.getSatMan().getIdSatMan()))
			return new AjaxResponse("error","Data tidak valid",null);
		PeranPengguna peranPengguna = (PeranPengguna) session.getAttribute("userRoleSession");
		if(peranPengguna.getSatMan().getSatManInduk() != null && !peranPengguna.getSatMan().getIdSatMan().equals(kurikulumBaru.getSatMan().getIdSatMan()))
			return new AjaxResponse("error","Data tidak valid",null);
		
		List<EkuivalensiPd> listEkuivalensiPd = ekuivalensiPdService.get("pd.idPd = '"+pd.getIdPd().toString()+"' AND kurikulumLama.idKurikulum"
				+ " = '"+kurikulumLama.getIdKurikulum().toString()+"' AND kurikulumBaru.idKurikulum = '"+kurikulumBaru.getIdKurikulum().toString()+"'");
		if(listEkuivalensiPd.size() > 0)
		{
			listEkuivalensiPd.get(0).setTglPembuatan(org.joda.time.LocalDateTime.now());
			ekuivalensiPdService.save(listEkuivalensiPd.get(0));
			
			List<KrsHapus> savedKrsHapus = krsHapusService.get("ekuivalensiPd.idEkuivalensiPd = '"+listEkuivalensiPd.get(0).getIdEkuivalensiPd()+"'");
			
			List<MKWajibPd> savedMKWajib = mkWajibPdService.get("ekuivalensiPd.idEkuivalensiPd = '"+listEkuivalensiPd.get(0).getIdEkuivalensiPd()+"'");
			
			for(int i=0;i<checkedMK.getIdMK().length;i++)
			{
				//KrsHapus
				if(checkedMK.getStatusMK()[i].compareTo("true") == 0)
				{
					List<Krs> krs = krsService.get("mk.idMK = '"+checkedMK.getIdMK()[i]+"' AND pd.idPd = '"+checkedMK.getIdPd()+"'"
							+ " AND krs.aKrsLulus = true AND krs.aKrsDiulang = false");
					System.out.print("id-mk"+krs.get(0).getPemb().getMk().getIdMK().toString()+"-krs"+krs.get(0).getIdKrs());
					List<KrsHapus> listKrsHapus = krsHapusService.get("krs.idKrs = '"+krs.get(0).getIdKrs().toString()+"'");
					
					if(listKrsHapus.size() > 0)
					{
						listKrsHapus.get(0).setaKrsHapus(true);
						krsHapusService.save(listKrsHapus.get(0));
						savedKrsHapus.remove(listKrsHapus.get(0));
					}
					else
					{
						KrsHapus krsHapus = new KrsHapus();
						
						krsHapus.setEkuivalensiPd(listEkuivalensiPd.get(0));
						krsHapus.setKrs(krs.get(0));
						krsHapus.setaKrsHapus(true);
						krsHapusService.save(krsHapus);
					}
					//Hapus Krs
					krs.get(0).setaKrsTerhapus(true);
					krsService.update(krs.get(0));
				}
				else
				{
					List<MKWajibPd> listMKWajibPd = mkWajibPdService.get("mk.idMK = '"+checkedMK.getIdMK()[i]+"' AND ekuivalensiPd.idEkuivalensiPd = '"+listEkuivalensiPd.get(0).getIdEkuivalensiPd()+"'");
					
					if(listMKWajibPd.size() > 0)
					{
						listMKWajibPd.get(0).setaMKAmbil(true);
						mkWajibPdService.save(listMKWajibPd.get(0));
						savedMKWajib.remove(listMKWajibPd.get(0));
					}
					else
					{
						MKWajibPd mkWajibPd = new MKWajibPd();
						MK mk = mkService.getById(UUID.fromString(checkedMK.getIdMK()[i]));
						mkWajibPd.setMk(mk);
						mkWajibPd.setEkuivalensiPd(listEkuivalensiPd.get(0));
						mkWajibPd.setaMKAmbil(true);
						mkWajibPdService.save(mkWajibPd);
					}
				}
			}
			
			int max = Math.max(savedKrsHapus.size(), savedMKWajib.size());
			for(int i=0;i<max;i++)
			{
				if(i<savedKrsHapus.size())
				{
					savedKrsHapus.get(i).setaKrsHapus(false);
					krsHapusService.save(savedKrsHapus.get(i));
					//Restore krs
					Krs krs = savedKrsHapus.get(i).getKrs();
					krs.setaKrsTerhapus(false);
					krsService.update(krs);
				}
				if(i<savedMKWajib.size())
				{
					savedMKWajib.get(i).setaMKAmbil(false);;
					mkWajibPdService.save(savedMKWajib.get(i));
				}
			}
			return new AjaxResponse("ok","Data berhasil disimpan",null);
		}
		
		Ptk ptk = new Ptk();
		ptk.setIdPtk(peranPengguna.getPengguna().getPtk().getIdPtk());
		ptk.setaPtkTerhapus(peranPengguna.getPengguna().getPtk().isPtkTerhapus());
		ptk.setNipPtk(peranPengguna.getPengguna().getPtk().getNiPtk());
		ptk.setNmPtk(peranPengguna.getPengguna().getPtk().getNamaPtk());
		ptk.setStatusPtk(peranPengguna.getPengguna().getPtk().isStatusPtk());
		
		EkuivalensiPd ekuivalensiPd = new EkuivalensiPd();
		ekuivalensiPd.setPd(pd);
		ekuivalensiPd.setPtk(ptk);
		ekuivalensiPd.setKurikulumLama(kurikulumLama);
		ekuivalensiPd.setKurikulumBaru(kurikulumBaru);
		ekuivalensiPd.setTglPembuatan(org.joda.time.LocalDateTime.now());
		ekuivalensiPdService.save(ekuivalensiPd);
		for(int i=0;i<checkedMK.getIdMK().length;i++)
		{
			//Krs Hapus
			if(checkedMK.getStatusMK()[i].compareTo("true") == 0)
			{
				KrsHapus krsHapus = new KrsHapus();
				List<Krs> krs = krsService.get("mk.idMK = '"+checkedMK.getIdMK()[i]+"' AND pd.idPd = '"+checkedMK.getIdPd()+"'"
						+ " AND krs.aKrsLulus = true AND krs.aKrsDiulang = false");
				System.out.print("nilai-"+krs.get(0).getNilaiAkhir());
				krsHapus.setEkuivalensiPd(ekuivalensiPd);
				krsHapus.setKrs(krs.get(0));
				krsHapus.setaKrsHapus(true);
				krsHapusService.save(krsHapus);
				//Hapus krs
			}
			else
			{
				MKWajibPd mkWajibPd = new MKWajibPd();
				MK mk = mkService.getById(UUID.fromString(checkedMK.getIdMK()[i]));
				mkWajibPd.setMk(mk);
				mkWajibPd.setEkuivalensiPd(ekuivalensiPd);
				mkWajibPd.setaMKAmbil(true);
				mkWajibPdService.save(mkWajibPd);
			}
		}
		response.setStatus("ok");
		response.setMessage("Data berhasil disimpan");
		return response;
	}

	@RequestMapping(value = "/checkedmklama", method = RequestMethod.POST, produces="application/json")
    public @ResponseBody AjaxResponse checkedmklama(@RequestBody CheckedMK checkedMK) {
		AjaxResponse response = new AjaxResponse();
		HashMap<String, String> hashMapMKLama = new HashMap<String, String>();
		String checkedMKLama = "";
		for(int i=0;i<checkedMK.getIdMK().length;i++)
		{
			if(checkedMK.getStatusMK()[i].compareToIgnoreCase("3") == 0 || checkedMK.getStatusMK()[i].compareToIgnoreCase("4") == 0)
				checkedMKLama = checkedMK.getIdMK()[i];
			hashMapMKLama.put(checkedMK.getIdMK()[i], checkedMK.getStatusMK()[i]);
		}
		if(checkedMKLama == "") return new AjaxResponse("error","Data tidak valid",null);
		List<Object> listRelasi = ekuivalensiMKService.getDistinctRelasiByMKLama(UUID.fromString(checkedMKLama));
		CheckedMK hasilMK = new CheckedMK();
		if(listRelasi.size() > 0)
		{
			for (Object object : listRelasi) {
				RelasiEkuivalensi relasiEkuivalensi = (RelasiEkuivalensi) object;				
				if(!CrosscheckRelasi(hashMapMKLama, relasiEkuivalensi))
				{
					List<MK> listMKBaru = ekuivalensiMKService.getMKBaruDistinct(relasiEkuivalensi.getIdRelasiEkuivalensi());
					String[] idMK = new String[listMKBaru.size()];
					for(int i=0;i<listMKBaru.size();i++)
					{
						idMK[i] = listMKBaru.get(i).getIdMK().toString();
					}
					hasilMK.setIdMK(idMK);
					response.setStatus("ok");
					response.setData(hasilMK);
					return response;
				}
			}
		}
		response.setStatus("ok");
		response.setData(hasilMK);
		return response;
	}
	
	/* 
	 * Ajax batalkan persetujuan ekuivalensi
	 * Hak Akses : Admin
	 * Return null
	 */

	@RequestMapping(value = "/bukaekuivalensi", method = RequestMethod.POST)
	public @ResponseBody AjaxResponse bukaekuivalensi(HttpSession session,@RequestParam("idPd") UUID idPd,@RequestParam("idRelasi") String idRelasi) {
		
		PeranPengguna peranPengguna = (PeranPengguna) session.getAttribute("userRoleSession");
//		if(peranPengguna.getSatMan().getIdSatManInduk() != null)
//			return new AjaxResponse("error","Data tidak valid",null);
		
		AjaxResponse response = new AjaxResponse();
		Pd pd = pdService.getById(idPd);
		String[] tmp = idRelasi.split(";");
		if(pd == null) return new AjaxResponse("error","Data tidak ditemukan",null);
		if(tmp.length < 2) return new AjaxResponse("error","Data tidak ditemukan",null);
		List<EkuivalensiPd> listEkuivalensiPd = ekuivalensiPdService.get("kurikulumLama.idKurikulum = '"+tmp[0]+"' AND kurikulumBaru.idKurikulum = '"
		+tmp[1]+"' AND pd.idPd = '"+idPd.toString()+"'");
		if(listEkuivalensiPd.size() < 1) return new AjaxResponse("error","Data tidak ditemukan",null);
		listEkuivalensiPd.get(0).setaEkuivalensi(false);
		response.setData(ekuivalensiPdService.save(listEkuivalensiPd.get(0)));
		response.setMessage("ok");
		return response;
	}

	@RequestMapping(value = "/simpanpermanen", method = RequestMethod.POST)
	public @ResponseBody AjaxResponse simpanpermanen(HttpSession session,@RequestParam("idPd") UUID idPd,@RequestParam("idRelasi") String idRelasi) {
		AjaxResponse response = new AjaxResponse();
		Pd pd = pdService.getById(idPd);
		String[] tmp = idRelasi.split(";");
		if(pd == null) return new AjaxResponse("error","Data tidak ditemukan",null);
		if(tmp.length < 2) return new AjaxResponse("error","Data tidak ditemukan",null);
		List<EkuivalensiPd> listEkuivalensiPd = ekuivalensiPdService.get("kurikulumLama.idKurikulum = '"+tmp[0]+"' AND kurikulumBaru.idKurikulum = '"
		+tmp[1]+"' AND pd.idPd = '"+idPd.toString()+"'");
		if(listEkuivalensiPd.size() < 1) return new AjaxResponse("error","Data tidak ditemukan",null);
		listEkuivalensiPd.get(0).setaEkuivalensi(true);
		response.setData(ekuivalensiPdService.save(listEkuivalensiPd.get(0)));
		response.setMessage("ok");
		return response;
	}
	
	private boolean CrosscheckRelasi(HashMap<String, String> hashMapMKLama, RelasiEkuivalensi relasiEkuivalensi)
	{		
		String relasi = relasiEkuivalensi.getRelasiMKLama();
		boolean status = true;
		if(relasi.length() > 2)
		{
			HashMap<Character, String> simbolMK = new HashMap<Character, String>();		
			relasi = createpostfix(relasi);
			String[] temp = relasiEkuivalensi.getDetailRelasiMKLama().split(";");
			for (String string : temp) {
				String[] tmp = string.split("=");
				simbolMK.put(tmp[0].charAt(0), tmp[1]);
			}
			Stack<Boolean> hasil = new Stack<Boolean>();
			for(int i=0;i<relasi.length();i++)
			{
				if(Character.isLetter(relasi.charAt(i)))
				{
					//Jika mk pilihan diabaikan, dan dianggap true
					MK tmp = mkService.getById(UUID.fromString(simbolMK.get(relasi.charAt(i))));
					if(tmp.getSifatMK() == false)
						hasil.push(true);
					else if(hashMapMKLama.get(simbolMK.get(relasi.charAt(i))) != null)							
					{
						if(hashMapMKLama.get(simbolMK.get(relasi.charAt(i))).compareTo("2") == 0)
							hasil.push(true);
						else hasil.push(false);
					}
					else hasil.push(false);
				}
				else
				{
					Boolean a,b;
					a=hasil.pop();
					b=hasil.pop();
					if(relasi.charAt(i) == '^')
					{
						if(a && b)
							hasil.push(true);
						else hasil.push(false);
					}
					else
					{
						if(a || b)
							hasil.push(true);
						else hasil.push(false);
					}
				}
			}
			return hasil.pop();
		}
		else
		{
			List<MK> listMK = ekuivalensiMKService.getMKLamaDistinct(relasiEkuivalensi.getIdRelasiEkuivalensi());
			if(listMK.size() < 1) return false;
			if(relasi.compareToIgnoreCase("-") == 0)
			{
				String temp = listMK.get(0).getIdMK().toString();
				//True jika sudah lulus mk dan mk bersifat pilihan
				if(listMK.get(0).getSifatMK() == false)
					return true;
				else if(hashMapMKLama.get(temp).compareTo("2") == 0)
					return true;
				else return false;
			}
			else
			{
				status = true;
				if(relasi.compareToIgnoreCase("^") == 0)
				{
					for (MK mk : listMK) 
					{
						//False, jika mk tidak lulus / belum ambil dan mk bersifat wajib
						if(hashMapMKLama.get(mk.getIdMK().toString()) != null)
							if(hashMapMKLama.get(mk.getIdMK().toString()).compareTo("2") != 0 && mk.getSifatMK() == true )
								status = false;
					}
				}
				else if(relasi.compareToIgnoreCase("/") == 0)
				{
					for (MK mk : listMK) 
					{
						//True, jika mk sudah lulus atau mk pilihan
						if(mk.getSifatMK() == false)
							return true;
						else if(hashMapMKLama.get(mk.getIdMK().toString()).compareTo("2") == 0)
							return true;
					}
				}
			}
			return status;
		}
	}
	
	private boolean CekRelasi(RelasiEkuivalensi relasiEkuivalensi, HashMap<String, Boolean> hasMapKRS)
	{
		String relasi = relasiEkuivalensi.getRelasiMKLama();
		boolean status = true;
		
		if(relasi.length() > 2)
		{
			HashMap<Character, String> simbolMK = new HashMap<Character, String>();		
			relasi = createpostfix(relasi);
			String[] temp = relasiEkuivalensi.getDetailRelasiMKLama().split(";");
			for (String string : temp) {
				String[] tmp = string.split("=");
				simbolMK.put(tmp[0].charAt(0), tmp[1]);
			}
			Stack<Boolean> hasil = new Stack<Boolean>();
			for(int i=0;i<relasi.length();i++)
			{
				if(Character.isLetter(relasi.charAt(i)))
				{
					//Jika mk pilihan diabaikan, dan dianggap true
					MK tmp = mkService.getById(UUID.fromString(simbolMK.get(relasi.charAt(i))));
					if(hasMapKRS.containsKey(simbolMK.get(relasi.charAt(i))) || tmp.getSifatMK() == false)
					{
						hasil.push(true);
					}
					else hasil.push(false);
				}
				else
				{
					Boolean a,b;
					a=hasil.pop();
					b=hasil.pop();
					if(relasi.charAt(i) == '^')
					{
						if(a && b)
							hasil.push(true);
						else hasil.push(false);
					}
					else
					{
						if(a || b)
							hasil.push(true);
						else hasil.push(false);
					}
				}
			}
			return hasil.pop();
		}
		else
		{
			List<MK> listMK = ekuivalensiMKService.getMKLamaDistinct(relasiEkuivalensi.getIdRelasiEkuivalensi());
			if(listMK.size() < 1) return false;
			if(relasi.compareToIgnoreCase("-") == 0)
			{
				String temp = listMK.get(0).getIdMK().toString();
				//True jika sudah lulus mk dan mk bersifat pilihan
				if(hasMapKRS.containsKey(temp) || listMK.get(0).getSifatMK() == false)
					return true;
				else return false;
			}
			else
			{
				status = true;
				if(relasi.compareToIgnoreCase("^") == 0)
				{
					for (MK mk : listMK) 
					{
						//False, jika mk tidak lulus / belum ambil dan mk bersifat wajib
						if((!hasMapKRS.containsKey(mk.getIdMK().toString())) && mk.getSifatMK() == true )
							status = false;
					}
				}
				else if(relasi.compareToIgnoreCase("/") == 0)
				{
					for (MK mk : listMK) 
					{
						//True, jika mk sudah lulus atau mk pilihan
						if(hasMapKRS.containsKey(mk.getIdMK().toString()) || mk.getSifatMK() == false)
							return true;
					}
				}
			}
			return status;
		}
	}
	
	private boolean isoperator(char cek_op)
	{
	    if(cek_op=='^' || cek_op=='/')
	    {
	        return true;
	    }
	    else{
	        return false;
	    }
	}
	
	private int priority(char pri)
	{
	    if(pri=='^'){
	        return 1;
	    }
	    else if(pri=='/'){
	        return 1;
	    }
	    else{
	        return 0;
	    }
	}
	
	private String createpostfix(String data)
	{
		Stack<Character> stack = new Stack<Character>();
	    Character cek=' ';
	    data = data.replaceAll("\\s+" , "");
	    Character[] input = new Character[data.length()];
	    Character[] output = new Character[data.length()];
	    
	    for(int i=0;i<data.length();i++)
	    {
	    	input[i] = new Character(data.charAt(i));
	    }
	    
	    int k = 0;
	    for(int i=0; i<data.length(); i++){
	        if(Character.isLetter(input[i])){
	            output[k]=input[i];
	            k++;
	        }
	        else if(isoperator(input[i]))
	        {
	            if(stack.size()==0){
	                stack.push(input[i]);
	            }
	            else
	            {
	                if(priority(input[i]) <= priority(stack.lastElement()) && cek!='(')
	                {
	                    for(int j=stack.size()-1; j>-1; j--)
	                    {
	                        if(!(stack.get(j) == '('))
	                        {
	                            output[k]=stack.pop();
	                            k++;
	                        }
	                    }
	                    stack.push(input[i]);
	                }
	                else
	                {
	                	stack.push(input[i]);
	                }
	            }
	        }
	        else if(input[i]=='(')
	        {
	        	stack.push(input[i]);
	            cek=input[i];
	        }
	        else if(input[i]==')')
	        {
	            cek=input[i];
	            while(stack.lastElement() !='(')
	            {
	                output[k]=stack.pop();
	                k++;
	            }
	            if(stack.lastElement() == '('){
	                output[k]=stack.pop();
	            }
	        }
	    }
	    
	    for(int i=stack.size()-1; i>-1; i--){
	        if(!(stack.lastElement()=='(')){
	            output[k]=stack.pop();
	            k++;
	        }
	    }
	    
	    String hasil = "";
	    for(int i=0;i<output.length;i++)
	    {
	    	if(output[i] != null)
	    	{
	    		hasil += ""+output[i];
	    	}
	    }
		return hasil;
	}
}
