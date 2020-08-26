package com.bustan.siakad.controller;

import java.io.Console;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Stack;
import java.util.UUID;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
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
import com.bustan.siakad.service.MKWajibPdService;
import com.bustan.siakad.service.RelasiEkuivalensiService;
import com.bustan.siakad.service.PdService;
import com.bustan.siakad.service.SatManMKService;
import com.bustan.siakad.service.SatManService;
import com.bustan.siakad.service.EkuivalensiPdService;
import com.sia.main.domain.PeranPengguna;
import com.sia.modul.domain.AlihJenjangMKTerakui;
import com.sia.modul.domain.CalonPD;
import com.sia.modul.domain.CheckedMK;
import com.sia.modul.domain.EkuivalenMK;
import com.sia.modul.domain.EkuivalensiMK;
import com.sia.modul.domain.EkuivalensiMKPekuivalensi;
import com.sia.modul.domain.EkuivalensiPd;
import com.sia.modul.domain.HasilEkuivalensiPd;
import com.sia.modul.domain.Krs;
import com.sia.modul.domain.KrsCalonPD;
import com.sia.modul.domain.Kurikulum;
import com.sia.modul.domain.MK;
import com.sia.modul.domain.MKAlihjenjang;
import com.sia.modul.domain.MKEkuivalen;
import com.sia.modul.domain.MKLuar;
import com.sia.modul.domain.MKWajibAlihJenjang;
import com.sia.modul.domain.MKWajibCalonPD;
import com.sia.modul.domain.MKWajibEkuivalensi;
import com.sia.modul.domain.MKWajibPd;
import com.sia.modul.domain.Pd;
import com.sia.modul.domain.PedomanEkuivalensi;
import com.sia.modul.domain.PrasyaratMK;
import com.sia.modul.domain.RelasiEkuivalensi;
import com.sia.modul.domain.RelasiMKAlihjenjang;
import com.sia.modul.domain.RelasiMKPekuivalensi;
import com.sia.modul.domain.SatMan;

@Controller
@RequestMapping(value = "/ekuivalensi")
public class EkuivalensiMKController {
	
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
	MKWajibPdService mkWajibPdService;
	
	@Autowired
	SatManMKService satManMKService;
	
	/*
	 * URL Ekuivalensi Kurikulum
	 * Hak Akses : Tim Ekuivalensi
	 * return ModelAndView
	 */
	
	@Secured(value = { "ROLE_Tim Ekuivalensi" })
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView datatable(HttpSession session, Locale locale, Model model) {
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("ekuivalensi/home");
		
		mav.addObject ("menuActive","Ekuivalensi Kurikulum"); 
		
		return mav;
	}
	
	/* 
	 * Hak Akses : Tim Ekuivalensi
	 * Return datatable json relasi ekuivalensi
	 */

	@Secured(value = { "ROLE_Tim Ekuivalensi" })
	@RequestMapping(value = "/json", method = RequestMethod.POST)
	public @ResponseBody Datatable json(HttpSession session, 
			@RequestParam("sEcho") String sEcho, 
			@RequestParam("iDisplayLength") int iDisplayLength,
            @RequestParam("iSortCol_0") int iSortCol_0,
            @RequestParam("sSortDir_0") String sSortDir_0,
            @RequestParam("sSearch") String sSearch,
			@RequestParam("iDisplayStart") int iDisplayStart
            ) {
		String filter = "";//(kurikulumLama.statusKurikulum = false AND kurikulumBaru.statusKurikulum = false) AND ";
		
		//Cek validasi
		PeranPengguna peranPengguna = (PeranPengguna) session.getAttribute("userRoleSession");
		if(peranPengguna.getPeran().getNamaPeran().compareToIgnoreCase("admin") != 0)
		{
			filter += "(satManLama.idSatMan = '"+peranPengguna.getSatMan().getIdSatMan()+"'";
			filter += " AND satManBaru.idSatMan = '"+peranPengguna.getSatMan().getIdSatMan()+"')";
		}
		else
		{
//			List<SatMan> satManInduk = satManService.get("idSatManInduk = null");
//			List<SatMan> listSatManProdi = satManService.listChild(satManInduk.get(0).getIdSatMan());
//			String filterSatManLama = "";
//			String filterSatManBaru = "";
//			for(int i=0;i<listSatManProdi.size();i++) 
//			{
//				filterSatManLama += "satManLama.idSatMan = '"+listSatManProdi.get(i).getIdSatMan().toString()+"'";
//				filterSatManBaru += "satManBaru.idSatMan = '"+listSatManProdi.get(i).getIdSatMan().toString()+"'";
//				if(i+1<listSatManProdi.size())
//				{
//					filterSatManLama += " OR ";
//					filterSatManBaru += " OR ";
//				}
//			}
//			filter += "("+filterSatManLama+")"+" AND ("+filterSatManBaru+")";
		}
		
		Datatable relasiDatatable = relasiEkuivalensiService.getdatatable(sEcho, iDisplayLength, iDisplayStart, iSortCol_0, sSortDir_0, sSearch,filter);
		return relasiDatatable;
	}
	
	
	/*
	 * Ajax hapus data ekuivalensi kurikulum (hapus relasi antar kurikulum dan matakuliah yang saling berelasi)
	 * Hak Akses : Tim Ekuivalensi
	 * Return null
	 */

	@Secured(value = { "ROLE_Tim Ekuivalensi" })
	@RequestMapping(value = "/deletemany", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse deleteMany(HttpSession session, @RequestParam("idRelasi[]") String[] idRelasi) {
		AjaxResponse response;
		for (String string : idRelasi) {
			String[] tmp = string.split(";");
			List<RelasiEkuivalensi> listRelasi = relasiEkuivalensiService.get("kurikulumLama.idKurikulum = '"+tmp[0]+"' AND kurikulumBaru.idKurikulum = '"+tmp[1]+"'");
			if(listRelasi.size() < 1) return new AjaxResponse("error","Data tidak ditemukan", null);
			
			//Cek validasi data yang akan dihapus
			PeranPengguna peranPengguna = (PeranPengguna) session.getAttribute("userRoleSession");
			if(peranPengguna.getSatMan().getSatManInduk()!= null && !peranPengguna.getSatMan().getIdSatMan().equals(listRelasi.get(0).getKurikulumLama().getSatMan().getIdSatMan()))
				new AjaxResponse("error","Data tidak valid", null);
			
			
			for (RelasiEkuivalensi relasiEkuivalensi : listRelasi) {
				List<EkuivalensiMK> listEkuivalensi = ekuivalensiMKService.get("relasiEkuivalensi = '"+relasiEkuivalensi.getIdRelasiEkuivalensi().toString()+"'");
				for (EkuivalensiMK ekuivalensiMK : listEkuivalensi) {
					ekuivalensiMKService.delete(ekuivalensiMK.getIdEkuivalensiMK());
				}
				relasiEkuivalensiService.delete(relasiEkuivalensi.getIdRelasiEkuivalensi());
			}
		}
		response = new AjaxResponse("ok","Data dihapus",null);
        return response;
    }	
	
	/*
	 * Json datatable kurikulum
	 * Hak Akses : Tim Ekuivalensi
	 * Return datatable
	 */	

	@Secured(value = { "ROLE_Tim Ekuivalensi" })
	@RequestMapping(value = "/jsonkurikulum", method = RequestMethod.POST)
	public @ResponseBody Datatable jsonkurikulum(HttpSession session,
			@RequestParam("sEcho") String sEcho, 
			@RequestParam("iDisplayLength") int iDisplayLength,
            @RequestParam("iSortCol_0") int iSortCol_0,
            @RequestParam("sSortDir_0") String sSortDir_0,
            @RequestParam("sSearch") String sSearch,
			@RequestParam("iDisplayStart") int iDisplayStart
            ) {
		PeranPengguna peranPengguna = (PeranPengguna) session.getAttribute("userRoleSession");
		//add filter status hapus kurikulum
		String filter = "kurikulum.statusKurikulum = false";
		//add filter satman
		if(peranPengguna.getPeran().getNamaPeran().compareToIgnoreCase("admin") != 0)
		{
			filter += " AND satMan.idSatMan = '"+peranPengguna.getSatMan().getIdSatMan().toString()+"'";
		}
		else
		{
//			List<SatMan> satManInduk = satManService.get("idSatManInduk = null");
//			List<SatMan> listSatManProdi = satManService.listChild(satManInduk.get(0).getIdSatMan());
//			filter += "(";
//			for(int i=0;i<listSatManProdi.size();i++)
//			{
//				filter += "satMan.idSatMan = '"+listSatManProdi.get(i).getIdSatMan().toString()+"'";
//				if(i+1<listSatManProdi.size())
//					filter += " OR ";
//			}
//			filter += ")";
		}
		Datatable kurikulumDatatable = kurikulumService.getdatatable(sEcho, iDisplayLength, iDisplayStart, iSortCol_0, sSortDir_0, sSearch,filter);
		return kurikulumDatatable;
	}
	
	/*
	 * Json datatable matakuliah
	 * Hak Akses : Tim Ekuivalensi
	 * Return datatable
	 */	

	@Secured(value = { "ROLE_Tim Ekuivalensi" })
	@RequestMapping(value = "/getmk", method = RequestMethod.POST)
	public @ResponseBody Datatable getmk(HttpSession session,
			@RequestParam("sEcho") String sEcho, 
			@RequestParam("iDisplayLength") int iDisplayLength,
            @RequestParam("iSortCol_0") int iSortCol_0,
            @RequestParam("sSortDir_0") String sSortDir_0,
            @RequestParam("sSearch") String sSearch,
			@RequestParam("iDisplayStart") int iDisplayStart,
			@RequestParam("idKurikulum") UUID idKurikulum
            ) {
		
		String filter = "mk.statusMK = false";
		if(idKurikulum != null) 
		{
			//Cek validasi
			Kurikulum kurikulum = kurikulumService.getById(idKurikulum);
			if(kurikulum == null) return null;
			PeranPengguna peranPengguna = (PeranPengguna) session.getAttribute("userRoleSession");
			if(peranPengguna.getSatMan().getSatManInduk() != null && !peranPengguna.getSatMan().getIdSatMan().equals(kurikulum.getSatMan().getIdSatMan()))
				return null;
			filter = "kurikulum.idKurikulum = '"+idKurikulum.toString()+"' AND "+filter;
		}
			
		Datatable mkDatatable = mkService.getdatatable(sEcho, iDisplayLength, iDisplayStart, iSortCol_0, sSortDir_0, sSearch,filter);
		return mkDatatable;
	}
	
	/*
	 * Ajax data ekuivalensi (mapping matakuliah lama pada matakuliah baru)
	 * Hak Akses : Tim Ekuivalensi
	 * Return null
	 */	

	@Secured(value = { "ROLE_Tim Ekuivalensi" })
	@RequestMapping(value = "saveekuivalen", method = RequestMethod.POST, produces="application/json")
    public @ResponseBody AjaxResponse saveekuivalen(HttpSession session, @RequestBody EkuivalenMK[] ekuivalenMK) {
		
		Kurikulum kurikulumLama = kurikulumService.getById(UUID.fromString(ekuivalenMK[0].getIdKurikulumLama()));
		Kurikulum kurikulumBaru = kurikulumService.getById(UUID.fromString(ekuivalenMK[0].getIdKurikulumBaru()));
		if(kurikulumLama == null) return new AjaxResponse("error","Data tidak ditemukan",null);
		if(kurikulumBaru == null) return new AjaxResponse("error","Data tidak ditemukan",null);
		
		//Cek validasi
		if(!kurikulumLama.getSatMan().getIdSatMan().equals(kurikulumBaru.getSatMan().getIdSatMan()))
			return new AjaxResponse("error","Data tidak valid",null);
		PeranPengguna peranPengguna = (PeranPengguna) session.getAttribute("userRoleSession");
		if(peranPengguna.getSatMan().getSatManInduk() != null && !peranPengguna.getSatMan().getIdSatMan().equals(kurikulumLama.getSatMan().getIdSatMan()))
			return new AjaxResponse("error","Data tidak valid",null);
		
		List<RelasiEkuivalensi> listRelasi = relasiEkuivalensiService.get("kurikulumLama.idKurikulum = '"+kurikulumLama.getIdKurikulum().toString()+"'"
				+ " AND kurikulumBaru.idKurikulum = '"+kurikulumBaru.getIdKurikulum().toString()+"'");
		for (RelasiEkuivalensi relasiEkuivalensi : listRelasi) {
			List<EkuivalensiMK> listEkuivalenMK = ekuivalensiMKService.get("relasiEkuivalensi.idRelasiEkuivalensi ='"+relasiEkuivalensi.getIdRelasiEkuivalensi().toString()+"'");
			for (EkuivalensiMK ekuivalensiMK : listEkuivalenMK) {
				ekuivalensiMKService.delete(ekuivalensiMK.getIdEkuivalensiMK());
			}
			relasiEkuivalensiService.delete(relasiEkuivalensi.getIdRelasiEkuivalensi());
		}
		
		for (EkuivalenMK mk : ekuivalenMK) {
			RelasiEkuivalensi relasiEkuivalensi = new RelasiEkuivalensi();
			relasiEkuivalensi.setKurikulumLama(kurikulumLama);
			relasiEkuivalensi.setKurikulumBaru(kurikulumBaru);
			String relasi = mk.getRelasiMKBaru();
			String relasiID = null;
			relasi = relasi.replaceAll("\\s+", "");
			mk.setRelasiMKBaru(relasi);
			if(relasi.length() > 2)
			{
				List<String> listIDMK = new ArrayList<String>();
				for(int i=0;i<mk.getIdMKBaru().length;i++)
				{
					listIDMK.add(mk.getIdMKBaru()[i]);
				}	
				relasiID = generateRelasiID(relasi, listIDMK);
			}
			relasiEkuivalensi.setDetailRelasiMKBaru(relasiID);
			relasiEkuivalensi.setRelasiMKBaru(mk.getRelasiMKBaru());
			
			relasiID = null;
			relasi = mk.getRelasiMKLama();
			relasi = relasi.replaceAll("\\s+", "");
			mk.setRelasiMKLama(relasi);
			if(relasi.length() > 2)
			{
				List<String> listIDMK = new ArrayList<String>();
				for(int i=0;i<mk.getIdMKLama().length;i++)
				{
					listIDMK.add(mk.getIdMKLama()[i]);
				}
				relasiID = generateRelasiID(relasi, listIDMK);
			}
			relasiEkuivalensi.setDetailRelasiMKLama(relasiID);
			relasiEkuivalensi.setRelasiMKLama(mk.getRelasiMKLama());
			
			relasiEkuivalensiService.save(relasiEkuivalensi);
			
			MK mkLama = new MK();
			MK mkBaru = new MK();
			
			int max = mk.getIdMKBaru().length;
			if(max < mk.getIdMKLama().length)
				max = mk.getIdMKLama().length;
			System.out.print("huhu-"+max);
			for(int i=0;i<max;i++)
			{
				EkuivalensiMK ekuivalensiMK = new EkuivalensiMK();
				if(i < mk.getIdMKLama().length)
					mkLama = mkService.getById(UUID.fromString(mk.getIdMKLama()[i]));
				if(i < mk.getIdMKBaru().length)
					mkBaru = mkService.getById(UUID.fromString(mk.getIdMKBaru()[i]));
				ekuivalensiMK.setMkLama(mkLama);
				ekuivalensiMK.setMkBaru(mkBaru);
				ekuivalensiMK.setRelasiEkuivalensi(relasiEkuivalensi);
				ekuivalensiMKService.save(ekuivalensiMK);
			}
		}		
        return new AjaxResponse("ok","Penyimpanan berhasil",null);
    }
	
	/*
	 * Json data matakuliah yang berelasi
	 * Hak Akses : Tim Ekuivalensi
	 * Return class EkuivalenMK (bukan class model)
	 */	

	@Secured(value = { "ROLE_Tim Ekuivalensi" })
	@RequestMapping(value = "/getlistmkekuivalen", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse getlistmkekuivalen(HttpSession session, @RequestParam("idKurikulumLama") UUID idKurikulumLama,
    		@RequestParam("idKurikulumBaru") UUID idKurikulumBaru) {
		AjaxResponse response = new AjaxResponse();
		Kurikulum kurikulumLama = kurikulumService.getById(idKurikulumLama);
		Kurikulum kurikulumBaru = kurikulumService.getById(idKurikulumBaru);
		response.setData(null);
		if(!kurikulumLama.getSatMan().getIdSatMan().equals(kurikulumBaru.getSatMan().getIdSatMan()))
			return new AjaxResponse("error","Data tidak valid",null);
		if(kurikulumLama==null || kurikulumBaru==null) return new AjaxResponse("error","Kurikulum tidak diketahui",null);
		else
		{
			//Cek validasi			
			PeranPengguna peranPengguna = (PeranPengguna) session.getAttribute("userRoleSession");
			if(peranPengguna.getSatMan().getSatManInduk() != null && !peranPengguna.getSatMan().getIdSatMan().equals(kurikulumBaru.getSatMan().getIdSatMan()))
				return new AjaxResponse("error","Data tidak valid",null);
			
			List<EkuivalenMK> listEkuivalenMK = new ArrayList<EkuivalenMK>();
			
			List<RelasiEkuivalensi> listRelasiEkuivalensi = relasiEkuivalensiService.get("kurikulumLama.idKurikulum = '"
					+kurikulumLama.getIdKurikulum().toString()+"' AND kurikulumBaru.idKurikulum = '"+kurikulumBaru.getIdKurikulum().toString()+"'");
			for (RelasiEkuivalensi relasiEkuivalensi : listRelasiEkuivalensi) 
			{
				EkuivalenMK ekuivalenMK = new EkuivalenMK();				
				String relasiMKLama = relasiEkuivalensi.getRelasiMKLama();
				String relasiMKBaru = relasiEkuivalensi.getRelasiMKBaru();
				List<MK> mkLama = ekuivalensiMKService.getMKLamaDistinct(relasiEkuivalensi.getIdRelasiEkuivalensi());
				List<MK> mkBaru = ekuivalensiMKService.getMKBaruDistinct(relasiEkuivalensi.getIdRelasiEkuivalensi());
				
				String[] idMKLama = new String[mkLama.size()];
				String[] kodeMKLama = new String[mkLama.size()];
				String[] namaMKLama = new String[mkLama.size()];
				String[] sksMKLama = new String[mkLama.size()];
				String[] sifatMKLama = new String[mkLama.size()];
				
				String[] idMKBaru = new String[mkBaru.size()];
				String[] kodeMKBaru = new String[mkBaru.size()];
				String[] namaMKBaru = new String[mkBaru.size()];
				String[] sksMKBaru = new String[mkBaru.size()];
				String[] sifatMKBaru = new String[mkBaru.size()];
				
				//Mengurutkan mk lama dan baru
				if(relasiMKLama.length() > 2)
				{
					String[] tmp = relasiEkuivalensi.getDetailRelasiMKLama().split(";");
					int size = mkLama.size();
					for(int i=0;i<size;i++)
					{
						mkLama.remove(0);
					}
						
					for(int i=0;i<tmp.length;i++)
					{
						String[] idMK = tmp[i].split("=");
						MK newMKLama = mkService.getById(UUID.fromString(idMK[1]));
						mkLama.add(newMKLama);
					}
				}
				if(relasiMKBaru.length() > 2)
				{
					String[] tmp = relasiEkuivalensi.getDetailRelasiMKBaru().split(";");
					int size = mkBaru.size();
					for(int i=0;i<size;i++)
					{
						mkBaru.remove(0);
					}
						
					for(int i=0;i<tmp.length;i++)
					{
						String[] idMK = tmp[i].split("=");
						MK newMKBaru = mkService.getById(UUID.fromString(idMK[1]));
						mkBaru.add(newMKBaru);
					}
				}
				
				
				int max = mkLama.size();
				if(max < mkBaru.size())
					max = mkBaru.size();
				System.out.print("max-"+max+"-mklama-"+mkLama.size()+"-mkbaru-"+mkBaru.size());
				for(int i=0;i<max;i++)
				{
					if(i<mkLama.size())
					{
						idMKLama[i] = mkLama.get(i).getIdMK().toString();
						kodeMKLama[i] = mkLama.get(i).getKodeMK().toString();
						namaMKLama[i] = mkLama.get(i).getNamaMK();
						sksMKLama[i] = mkLama.get(i).getJumlahSKS().toString();
						if(mkLama.get(i).getSifatMK())
							sifatMKLama[i] = "Wajib";
						else sifatMKLama[i] = "Pilihan";
						
						
					}
					if(i<mkBaru.size())
					{
						idMKBaru[i] = mkBaru.get(i).getIdMK().toString();
						kodeMKBaru[i] = mkBaru.get(i).getKodeMK().toString();
						namaMKBaru[i] = mkBaru.get(i).getNamaMK();
						sksMKBaru[i] = mkBaru.get(i).getJumlahSKS().toString();
						if(mkBaru.get(i).getSifatMK())
							sifatMKBaru[i] = "Wajib";
						else sifatMKBaru[i] = "Pilihan";
						
					}
				}
				ekuivalenMK.setIdMKLama(idMKLama);
				ekuivalenMK.setKodeMKLama(kodeMKLama);
				ekuivalenMK.setNamaMKLama(namaMKLama);
				ekuivalenMK.setSksMKLama(sksMKLama);
				ekuivalenMK.setSifatMKLama(sifatMKLama);
				ekuivalenMK.setRelasiMKLama(relasiMKLama);
				
				ekuivalenMK.setIdMKBaru(idMKBaru);
				ekuivalenMK.setKodeMKBaru(kodeMKBaru);
				ekuivalenMK.setNamaMKBaru(namaMKBaru);
				ekuivalenMK.setSksMKBaru(sksMKBaru);
				ekuivalenMK.setSifatMKBaru(sifatMKBaru);
				ekuivalenMK.setRelasiMKBaru(relasiMKBaru);
				
				listEkuivalenMK.add(ekuivalenMK);
			}
			response.setData(listEkuivalenMK);
		}
		response.setStatus("ok");
		return response;
    }

	@Secured(value = { "ROLE_Tim Ekuivalensi" })
	@RequestMapping(value = "saveekuivalensi", method = RequestMethod.POST, produces="application/json")
    public @ResponseBody AjaxResponse saveekuivalensi(@RequestBody MKWajibAlihJenjang mkWajibAlihJenjang) {
		AjaxResponse response = new AjaxResponse();
//		Pd pd = pdService.getById(UUID.fromString(mkWajibAlihJenjang.getIdCalonPD()));
//		if(pd == null) return new AjaxResponse("error","Data tidak ditemukan",null);
//		List<MKWajibCalonPD> listMKWajibCalonPD = mkWajibCalonPDService.get("calonPD.idCalonPD = '"+pd.getIdPd().toString()+"'");
//		for (MKWajibCalonPD mkWajibCalonPD : listMKWajibCalonPD) {
//			mkWajibCalonPDService.delete(mkWajibCalonPD.getIdMKWajibCalonPD());
//		}
//		for(int i=0;i<mkWajibAlihJenjang.getIdMK().length;i++) {
//			MKWajibCalonPD mkWajibCalonPD = new MKWajibCalonPD();
//			mkWajibCalonPD.setCalonPD(calonPD);
//			mkWajibCalonPD.setMk(mkService.getById(UUID.fromString(mkWajibAlihJenjang.getIdMK()[i])));
//			if(mkWajibAlihJenjang.getStatus()[i].compareToIgnoreCase("true") == 0)
//			{
//				mkWajibCalonPD.setaStatusAmbil(true);
//			}				
//			else mkWajibCalonPD.setaStatusAmbil(false);
//			mkWajibCalonPDService.save(mkWajibCalonPD);
//		}
//		response.setStatus("ok");
//		response.setData(mkWajibAlihJenjang);
		return response;
	}

	@Secured(value = { "ROLE_Tim Ekuivalensi" })
	@RequestMapping(value = "/checkedmklama", method = RequestMethod.POST, produces="application/json")
    public @ResponseBody AjaxResponse checkedmklama(HttpSession session, @RequestBody CheckedMK checkedMK) {
		
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
	
	/*-----------------------------------------------------BEGIN /ekuivalensi/laporan---------------------------------------------------------------*/
	
	/* 
	 * URL Laporan Ekuivalensi
	 * Hak akses : Tim Ekuivalensi, Peserta Didik
	 * Return ModelAndView
	 */
	
	@Secured(value = { "ROLE_Peserta Didik", "ROLE_Tenaga Kependidikan" })
	@RequestMapping(value = "/laporan", method = RequestMethod.GET)
	public ModelAndView laporan(HttpSession session, Locale locale, Model model) {
		ModelAndView mav = new ModelAndView();
		
		String filter = "kurikulum.statusKurikulum = false";
		//add filter satman
		PeranPengguna peranPengguna = (PeranPengguna) session.getAttribute("userRoleSession");
		
		//if non admin
		if(peranPengguna.getPeran().getNamaPeran().compareToIgnoreCase("admin") != 0)
		{
			filter += " AND satMan.idSatMan = '"+peranPengguna.getSatMan().getIdSatMan()+"'";
		}
//		else
//		{
//			filter += "(";
//			List<SatMan> satManInduk = satManService.get("idSatManInduk = null");
//			List<SatMan> listSatManProdi = satManService.listChild(satManInduk.get(0).getIdSatMan());
//			for(int i=0;i<listSatManProdi.size();i++)
//			{
//				filter += "satMan.idSatMan = '"+listSatManProdi.get(i).getIdSatMan().toString()+"'";
//				if(i+1<listSatManProdi.size())
//					filter += " OR ";
//			}
//			filter += ")";
//		}
		List<Kurikulum> listKurikulum = kurikulumService.get(filter);
		mav.addObject("listKurikulum", listKurikulum);
		mav.setViewName("ekuivalensi/laporan");
		
		mav.addObject ("menuActive","Laporan Ekuivalensi"); 
		
		return mav;
	}
	
	/* 
	 * Datatable modal Peserta Didik
	 * Hak Akses : Tim Ekuivalensi, Peserta Didik
	 * Return datatable peserta didik
	 */

	@Secured(value = { "ROLE_Peserta Didik", "ROLE_Tenaga Kependidikan" })
	@RequestMapping(value = "/laporan/jsonpd", method = RequestMethod.POST)
	public @ResponseBody Datatable laporanjsonpd(HttpSession session, 
			@RequestParam("sEcho") String sEcho, 
			@RequestParam("iDisplayLength") int iDisplayLength,
            @RequestParam("iSortCol_0") int iSortCol_0,
            @RequestParam("sSortDir_0") String sSortDir_0,
            @RequestParam("sSearch") String sSearch,
			@RequestParam("iDisplayStart") int iDisplayStart,
			@RequestParam("idKurikulum") UUID idKurikulum			
            ) {
		//add filter pd belum lulus
		String filter = "";
		
		PeranPengguna peranPengguna = (PeranPengguna) session.getAttribute("userRoleSession");
		//if non admin
		if(peranPengguna.getPeran().getNamaPeran().compareToIgnoreCase("admin") != 0)
		{
			if(idKurikulum ==null)
			{
				List<Kurikulum> listKurikulum = kurikulumService.get("satMan.idSatMan = '"+peranPengguna.getSatMan().getIdSatMan()+"'");
				for(int i=0;i<listKurikulum.size();i++)
				{
					filter += "kurikulum.idKurikulum ='"+listKurikulum.get(i).getIdKurikulum()+"'";
					if(i+1 < listKurikulum.size())
						filter += " OR ";
				}
			}
			else
			{
				filter += "kurikulum.idKurikulum = '"+idKurikulum+"'";
			}
			List<SatMan> listSatMan = satManMKService.getSatManDistinct(filter);
			filter = "";
			for(int i=0;i<listSatMan.size();i++)
			{
				System.out.print(listSatMan.get(i).getNmSatMan());
				filter += "satMan.idSatMan = '"+listSatMan.get(i).getIdSatMan()+"'";
				if(i+1<listSatMan.size())
					filter += " OR ";
			}
			if(listSatMan.size() < 1)
				filter = "satMan.idSatMan = '"+peranPengguna.getSatMan().getIdSatMan()+"'";
		}
		
		System.out.print("sadsda"+filter);
//		else
//		{
//			filter += "(";
//			List<SatMan> satManInduk = satManService.get("idSatManInduk = null");
//			List<SatMan> listSatManProdi = satManService.listChild(satManInduk.get(0).getIdSatMan());
//			for(int i=0;i<listSatManProdi.size();i++)
//			{
//				filter += "satMan.idSatMan = '"+listSatManProdi.get(i).getIdSatMan().toString()+"'";
//				if(i+1<listSatManProdi.size())
//					filter += " OR ";
//			}
//			filter += ")";
//		}
		Datatable relasiDatatable = pdService.getdatatable(sEcho, iDisplayLength, iDisplayStart, iSortCol_0, sSortDir_0, sSearch,filter);
		return relasiDatatable;
	}
	
	/* 
	 * Json Get Matakuliah
	 * Hak Akses : Tim Ekuivalensi, Peserta Didik
	 * Return AjaxResponse daftar matakuliah di kurikulum baru yang wajib diambil / dibebaskan
	 */	

	@Secured(value = { "ROLE_Peserta Didik", "ROLE_Tenaga Kependidikan" })
	@RequestMapping(value = "/laporan/getmkbykurikulum", method = RequestMethod.POST)
	public @ResponseBody AjaxResponse laporangetmk(HttpSession session, @RequestParam("idPd") String idPd, @RequestParam("idKurikulum") String idKurikulum) {
		
		AjaxResponse response = new AjaxResponse();
		MKWajibAlihJenjang mkWajib = new MKWajibAlihJenjang();
		Pd pd = pdService.getById(UUID.fromString(idPd));
		if(pd == null) return new AjaxResponse("ok","Data tidak ditemukan",null);
		Kurikulum kurikulum = kurikulumService.getById(UUID.fromString(idKurikulum));
		if(kurikulum == null) return new AjaxResponse("ok","Data tidak ditemukan",null);
		
		//Cek validasi
		PeranPengguna peranPengguna = (PeranPengguna) session.getAttribute("userRoleSession");
		if(peranPengguna.getSatMan().getSatManInduk()!=null && !peranPengguna.getSatMan().getIdSatMan().equals(kurikulum.getSatMan().getIdSatMan()))
			return new AjaxResponse("error","Data tidak valid",null);
		
		//return jika belom simpan permanen
		List<EkuivalensiPd> listEkuivalensiPd = ekuivalensiPdService.get("pd.idPd = '"+idPd+"' AND kurikulumBaru.idKurikulum='"+idKurikulum+"' AND "
				+ "ekuivalensiPd.aEkuivalensi = true");
		if(listEkuivalensiPd.size() < 1)
			return new AjaxResponse("ok","Data tidak ditemukan",null);
		
		String filter = "";
		
		for(int i=0;i<listEkuivalensiPd.size();i++)
		{
			filter += "ekuivalensiPd.idEkuivalensiPd = '"+listEkuivalensiPd.get(i).getIdEkuivalensiPd().toString()+"'";
			if(i+1 < listEkuivalensiPd.size())
				filter += " AND ";
		}
		List<MKWajibPd> listMKWajibPd = mkWajibPdService.get(filter);
		HashMap<UUID, Boolean> hashMapMKWajib = new HashMap<UUID, Boolean>(); 
		for (MKWajibPd mkWajibPd : listMKWajibPd) {
			hashMapMKWajib.put(mkWajibPd.getMk().getIdMK(), mkWajibPd.isaMKAmbil());
		}
		List<MK> listMK = mkService.get("kurikulum.idKurikulum = '"+idKurikulum+"' AND mk.statusMK = false");
		String[] idMK = new String[listMK.size()];
		String[] kodeMK = new String[listMK.size()];
		String[] namaMK = new String[listMK.size()];
		String[] jumlahSKS = new String[listMK.size()];
		String[] statusMK = new String[listMK.size()];
		for(int i=0;i<listMK.size();i++)
		{
			idMK[i] = listMK.get(i).getIdMK().toString();
			kodeMK[i] = listMK.get(i).getKodeMK();
			namaMK[i] = listMK.get(i).getNamaMK();
			jumlahSKS[i] = listMK.get(i).getJumlahSKS().toString();
			
			if(hashMapMKWajib.containsKey(listMK.get(i).getIdMK()))
			{
				statusMK[i] = String.valueOf(hashMapMKWajib.get(listMK.get(i).getIdMK()));
			}
			else statusMK[i] = "false";
			if(listMK.get(i).getSifatMK())
				statusMK[i] += ";true";
			else statusMK[i] += ";false";
		}
		mkWajib.setIdMK(idMK);
		mkWajib.setJumlahSKS(jumlahSKS);
		mkWajib.setKodeMK(kodeMK);
		mkWajib.setNamaMK(namaMK);
		mkWajib.setStatus(statusMK);
		
		response.setMessage("Data berhasil ditemukan");
		response.setData(mkWajib);
		return response;
	}
	
	/*-----------------------------------------------------BEGIN private function---------------------------------------------------------------*/
	
	private String generateRelasiID(String relasi, List<String> listIDMK)
	{
		relasi = relasi.replaceAll("[()/^]", "");
		List<Character> listChar = new ArrayList<Character>();
		for(int i=0;i<relasi.length();i++)
		{
			if(!listChar.contains(relasi.charAt(i)))
				listChar.add(relasi.charAt(i));
		}
		
		
		char huruf[] = new char[listChar.size()];
		for(int i=0;i<listChar.size();i++)
		{
			huruf[i] = listChar.get(i);
		}
//		for(int i=0;i<relasi.length();i++)
//		{
//			huruf[i] = relasi.charAt(i);
//		}
		Arrays.sort(huruf);
		String relasiID = "";
		if(huruf.length != listIDMK.size())
			return null;
		for(int i=0;i<huruf.length;i++)
		{
			relasiID += huruf[i] + "=" + listIDMK.get(i);
			if(i+1 < huruf.length)
				relasiID+= ";";
		}
		return relasiID;
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
					status = false;
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
	        return 2;
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
