package com.bustan.siakad.controller;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
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
import com.bustan.siakad.service.EkuivalensiMKAlihjenjangService;
import com.bustan.siakad.service.KatalogAlihjenjangService;
import com.bustan.siakad.service.KatalogSatManService;
import com.bustan.siakad.service.MKAlihjenjangService;
import com.bustan.siakad.service.MKService;
import com.bustan.siakad.service.PrasyaratMKService;
import com.bustan.siakad.service.RelasiMKAlihjenjangService;
import com.bustan.siakad.service.SatManService;
import com.bustan.siakad.service.SatManMKService;
import com.bustan.siakad.service.KurikulumService;
import com.sia.modul.domain.EkuivalenMK;
import com.sia.modul.domain.EkuivalenMKAlihjenjang;
import com.sia.modul.domain.EkuivalensiMK;
import com.sia.modul.domain.EkuivalensiMKAlihjenjang;
import com.sia.modul.domain.EkuivalensiMKPekuivalensi;
import com.sia.modul.domain.KatalogAlihjenjang;
import com.sia.modul.domain.KatalogSatMan;
import com.sia.modul.domain.Kurikulum;
import com.sia.modul.domain.MK;
import com.sia.modul.domain.MKAlihjenjang;
import com.sia.modul.domain.MKEkuivalen;
import com.sia.modul.domain.MKLuar;
import com.sia.modul.domain.PedomanEkuivalensi;
import com.sia.modul.domain.PrasyaratMK;
import com.sia.modul.domain.RelasiEkuivalensi;
import com.sia.modul.domain.RelasiMKAlihjenjang;
import com.sia.modul.domain.RelasiMKPekuivalensi;
import com.sia.modul.domain.SatMan;
import com.sia.modul.domain.SatManMK;

@Controller
@Secured(value = { "ROLE_Admin" })
@RequestMapping(value = "/katalogalihjenjang")
public class KatalogAlihjenjangController {
	
	@Autowired
	KatalogAlihjenjangService katalogAlihjenjangService;
	
	@Autowired
	SatManService satManService;
	
	@Autowired
	MKAlihjenjangService mkAlihjenjangService;
	
	@Autowired
	MKService mkService;
	
	@Autowired
	RelasiMKAlihjenjangService relasiMKAlihjenjangService;
	
	@Autowired
	EkuivalensiMKAlihjenjangService ekuivalensiMKAlihjenjangService;
	
	@Autowired
	PrasyaratMKService prasyaratMKService;
	
	@Autowired
	SatManMKService satManMKService;
	
	@Autowired
	KurikulumService kurikulumService;
	
	@Autowired
	KatalogSatManService katalogSatManService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView datatable(HttpSession session, Locale locale, Model model) {
		ModelAndView mav = new ModelAndView();
		
		KatalogAlihjenjang katalogAlihjenjang = new KatalogAlihjenjang();
		MKAlihjenjang mkAlihjenjang = new MKAlihjenjang();
		List<KatalogAlihjenjang> listKatalog = katalogAlihjenjangService.get("katalog.aTerhapus = false");
		List<SatMan> listSatMan = satManService.get("satMan.isSatManHasKurikulum = true");
		
		mav.addObject("listSatMan",listSatMan);
		mav.addObject("listKatalog",listKatalog);
		mav.addObject("katalogAlihjenjang",katalogAlihjenjang);
		mav.addObject("mkAlihjenjang",mkAlihjenjang);
		mav.setViewName("katalogalihjenjang/home");
		
		mav.addObject ("menuActive","Manajemen Katalog Alihjenjang"); 
		
		return mav;
	}
	
	@RequestMapping(value = "/json", method = RequestMethod.POST)
	public @ResponseBody Datatable json(HttpSession session,
			@RequestParam("sEcho") String sEcho, 
			@RequestParam("iDisplayLength") int iDisplayLength,
            @RequestParam("iSortCol_0") int iSortCol_0,
            @RequestParam("sSortDir_0") String sSortDir_0,
            @RequestParam("sSearch") String sSearch,
			@RequestParam("iDisplayStart") int iDisplayStart
            ) {
		String filter = "katalog.aTerhapus = false";		
		Datatable katalogAlihjenjangDatatable = katalogAlihjenjangService.getdatatable(sEcho, iDisplayLength, iDisplayStart, iSortCol_0, sSortDir_0, sSearch,filter);
		return katalogAlihjenjangDatatable;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse edit(HttpSession session, @RequestParam("idKatalogAlihjenjang") UUID idKatalogAlihjenjang) {
		AjaxResponse response;
		KatalogAlihjenjang katalogAlihjenjang = katalogAlihjenjangService.getById(idKatalogAlihjenjang);
		if(katalogAlihjenjang == null) response = new AjaxResponse("error","Data tidak ditemukan",null);
		else response = new AjaxResponse("ok","Data ditemukan",katalogAlihjenjang);
        return response;
    }
	
	@RequestMapping(value = "/simpan", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse simpan(HttpSession session, @Valid @ModelAttribute("katalogAlihjenjang") KatalogAlihjenjang katalogAlihjenjang,
            BindingResult result, Map<String, Object> model){
		
		AjaxResponse response = new AjaxResponse();
		
        if (result.hasErrors()) {
        	response.setStatus("error");
        	List<FieldError> fieldError = result.getFieldErrors();
        	String message ="";
    		if(fieldError.get(0).isBindingFailure()) message += "Salah satu input tidak valid";
    		else message += fieldError.get(0).getDefaultMessage();
        	for(int i=1;i<fieldError.size();i++)
        	{
        		if(fieldError.get(i).isBindingFailure()) message += "Salah satu input tidak valid";
        		else message += "<br/>"+fieldError.get(i).getDefaultMessage();
        	}
        	response.setMessage(message);
        	response.setData(fieldError);
            return response;
        }
        response.setStatus("ok");
        response.setData(katalogAlihjenjangService.save(katalogAlihjenjang));
        if(response.getData()!=null) response.setMessage("Data berhasil disimpan");
        else 
        {
        	response.setStatus("error");
        	response.setMessage("");
        }
        return response;
    }
	
	@RequestMapping(value = "/deletemany", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse deleteMany(HttpSession session, @RequestParam("idKatalogAlihjenjang[]") UUID[] idKatalogAlihjenjang) {
		
		AjaxResponse response;
		for (UUID uuid : idKatalogAlihjenjang) {
			katalogAlihjenjangService.delete(uuid);
		}
		response = new AjaxResponse("ok","Data dihapus",null);
        return response;
    }
	
	
	
	
	
	/*

	@Secured(value = { "ROLE_Administrator" })
	@RequestMapping(value = "/jsonsatman", method = RequestMethod.POST)
	public @ResponseBody Datatable jsonsatman(HttpSession session,
			@RequestParam("sEcho") String sEcho, 
			@RequestParam("iDisplayLength") int iDisplayLength,
            @RequestParam("iSortCol_0") int iSortCol_0,
            @RequestParam("sSortDir_0") String sSortDir_0,
            @RequestParam("sSearch") String sSearch,
			@RequestParam("iDisplayStart") int iDisplayStart
            ) {
		//Add filter by
		if(!isLogin(session)){ return null;}
		if(!hasMenu(session, "Manajemen Katalog Alihjenjang")) { return null;}
		
		List<SatMan> listSatManProdi = new ArrayList<SatMan>();
		PeranPengguna peranPengguna = (PeranPengguna) session.getAttribute("peranPengguna");
		//if kepala satuan manajemen
		if(peranPengguna.getSatMan().getIdSatManInduk() != null)
		{
			listSatManProdi = satManService.listChild(peranPengguna.getSatMan().getIdSatMan());
			if(listSatManProdi.size() < 1)
				listSatManProdi = satManService.get("satMan.idSatMan = '"+peranPengguna.getSatMan().getIdSatMan()+"'");
		}
		else
		{
			List<SatMan> satManInduk = satManService.get("idSatManInduk = null");
			listSatManProdi = satManService.listChild(satManInduk.get(0).getIdSatManInduk());
		}
		String filter = "";
		for(int i=0;i<listSatManProdi.size();i++)
		{
			filter += "satMan.idSatMan = '"+listSatManProdi.get(i).getIdSatMan().toString()+"'";
			if(i+1 < listSatManProdi.size())
				filter += " OR ";
		}	
		Datatable katalogAlihjenjangDatatable = satManService.getdatatable(sEcho, iDisplayLength, iDisplayStart, iSortCol_0, sSortDir_0, sSearch,filter);
		return katalogAlihjenjangDatatable;
	}
	
	@Secured(value = { "ROLE_Administrator" })
	@RequestMapping(value = "/jsonmkalihjenjangbykatalog", method = RequestMethod.POST)
	public @ResponseBody Datatable jsonmkalihjenjangbykatalog(HttpSession session,
			@RequestParam("sEcho") String sEcho, 
			@RequestParam("iDisplayLength") int iDisplayLength,
            @RequestParam("iSortCol_0") int iSortCol_0,
            @RequestParam("sSortDir_0") String sSortDir_0,
            @RequestParam("sSearch") String sSearch,
			@RequestParam("iDisplayStart") int iDisplayStart,
			@RequestParam("katalogAlihjenjang") String katalogAlihjenjang
            ) {
		if(!isLogin(session)){ return null;}
		if(!hasMenu(session, "Manajemen Katalog Alihjenjang")) { return null;}
		
		String filter = "mkAlihjenjang.aMKAlihjenjangTerhapus = false";
		if(katalogAlihjenjang != "")
			filter = "katalog.idKatalogAlihjenjang = '"+katalogAlihjenjang.toString()+"' AND "+filter;
		Datatable mkAlihjenjangDatatable = mkAlihjenjangService.getdatatable(sEcho, iDisplayLength, iDisplayStart, iSortCol_0, sSortDir_0, sSearch,filter);
		return mkAlihjenjangDatatable;
	}
	
	
	
	@Secured(value = { "ROLE_Administrator" })
	@RequestMapping(value = "/getsatman", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse getsatman(HttpSession session, @RequestParam("idKatalogAlihjenjang") UUID idKatalogAlihjenjang) {
		if(!isLogin(session)){ return null;}
		if(!hasMenu(session, "Manajemen Katalog Alihjenjang")) { return null;}
		
		AjaxResponse response = new AjaxResponse();
		KatalogAlihjenjang katalogAlihjenjang = katalogAlihjenjangService.getById(idKatalogAlihjenjang);
		if(katalogAlihjenjang == null) return new AjaxResponse("error","Data tidak ditemukan", null);
		response.setStatus("ok");
		response.setData(katalogAlihjenjang.getSatMan().getIdSatMan().toString());
        return response;
    }
	
	
	@Secured(value = { "ROLE_Administrator" })
	@RequestMapping(value = "/jsonmkbykatalog", method = RequestMethod.POST)
	public @ResponseBody Datatable jsonmkbykatalog(HttpSession session,
			@RequestParam("sEcho") String sEcho, 
			@RequestParam("iDisplayLength") int iDisplayLength,
            @RequestParam("iSortCol_0") int iSortCol_0,
            @RequestParam("sSortDir_0") String sSortDir_0,
            @RequestParam("sSearch") String sSearch,
			@RequestParam("iDisplayStart") int iDisplayStart,
			@RequestParam("katalogAlihjenjang") String katalogAlihjenjang
            ) {
		if(!isLogin(session)){ return null;}
		if(!hasMenu(session, "Manajemen Katalog Alihjenjang")) { return null;}
		
		String filter = "";
		if(katalogAlihjenjang != "")
		{
			KatalogAlihjenjang katalog = katalogAlihjenjangService.getById(UUID.fromString(katalogAlihjenjang));			
			filter = "satMan.idSatMan = '"+katalog.getSatMan().getIdSatMan()+"'";
		}
		else{
			PeranPengguna peranPengguna = (PeranPengguna) session.getAttribute("peranPengguna");
			filter = "satMan.idSatMan = '"+peranPengguna.getSatMan().getIdSatMan()+"'";
		}
		Datatable mkDatatable = satManMKService.getdatatable(sEcho, iDisplayLength, iDisplayStart, iSortCol_0, sSortDir_0, sSearch,filter);
		return mkDatatable;
	}
	
	@Secured(value = { "ROLE_Administrator" })
	@RequestMapping(value = "saveekuivalen", method = RequestMethod.POST, produces="application/json")
    public @ResponseBody AjaxResponse saveekuivalen(HttpSession session, @RequestBody EkuivalenMKAlihjenjang[] ekuivalenMKAlihjenjang) {
		if(!isLogin(session)){ return null;}
		if(!hasMenu(session, "Manajemen Katalog Alihjenjang")) { return null;}
		
		KatalogAlihjenjang katalogAlihjenjang = katalogAlihjenjangService.getById(UUID.fromString(ekuivalenMKAlihjenjang[0].getIdKatalogAlihjenjang()));
		if(katalogAlihjenjang == null) return new AjaxResponse("error","Data tidak ditemukan",null);
		
		Kurikulum kurikulum = kurikulumService.getById(UUID.fromString(ekuivalenMKAlihjenjang[0].getIdKurikulum()));
		if(kurikulum == null) return new AjaxResponse("error","Data tidak ditemukan",null);
		
		//Delete semua relasi dan mk ekuivalensi
		List<RelasiMKAlihjenjang> listRelasiMKAlihjenjang = relasiMKAlihjenjangService.get("katalog.idKatalogAlihjenjang = '"
				+katalogAlihjenjang.getIdKatalogAlihjenjang().toString()+"' AND kurikulum.idKurikulum = '"+kurikulum.getIdKurikulum()+"'");
		for (RelasiMKAlihjenjang relasiMKAlihjenjang : listRelasiMKAlihjenjang) 
		{			
			List<EkuivalensiMKAlihjenjang> listEkuivalensiMKAlihjenjang = ekuivalensiMKAlihjenjangService.get("relasiMKAlihjenjang.idRelasiMKAlihjenjang = '"
				+relasiMKAlihjenjang.getIdRelasiMKAlihjenjang().toString()+"'");
			
			for (EkuivalensiMKAlihjenjang ekuivalensiMKAlihjenjang : listEkuivalensiMKAlihjenjang) {
				ekuivalensiMKAlihjenjangService.delete(ekuivalensiMKAlihjenjang.getIdEkuivalensiMKAlihjenjang());
			}
			relasiMKAlihjenjangService.delete(relasiMKAlihjenjang.getIdRelasiMKAlihjenjang());
		}
		
		for(EkuivalenMKAlihjenjang mk : ekuivalenMKAlihjenjang)
		{
			//Pedoman Ekuivalensi kosong
//			if(mk.getRelasi() == "") return new AjaxResponse("ok","Penyimpanan berhasil",null);
			
			int max = mk.getIdMKAlihjenjang().length;
			if(max < mk.getIdMKSatMan().length)
				max = mk.getIdMKSatMan().length;
			RelasiMKAlihjenjang relasiMKAlihjenjang = new RelasiMKAlihjenjang();
			relasiMKAlihjenjang.setKatalog(katalogAlihjenjang);
			relasiMKAlihjenjang.setKurikulum(kurikulum);
			String relasi = mk.getRelasiMKAlihjenjang();
			relasi = relasi.replaceAll("\\s+", "");
			String detailRelasi = null;
			mk.setRelasiMKAlihjenjang(relasi);
			if(relasi.length() > 2)
			{
				List<String> listIDMK = new ArrayList<String>();
				for(int i=0;i<mk.getIdMKAlihjenjang().length;i++)
				{
					String tmp = mk.getIdMKAlihjenjang()[i];
					listIDMK.add(mk.getIdMKAlihjenjang()[i]);
				}
				detailRelasi = generateDetailRelasi(relasi, listIDMK);
			}
			relasiMKAlihjenjang.setRelasiMKAlihjenjang(mk.getRelasiMKAlihjenjang());
			relasiMKAlihjenjang.setDetailRelasiMKAlihjenjang(detailRelasi);
			
			relasi = mk.getRelasiMKSatMan();
			relasi = relasi.replaceAll("\\s+", "");
			detailRelasi = null;
			mk.setRelasiMKSatMan(relasi);
			if(relasi.length() > 2)
			{
				List<String> listIDMK = new ArrayList<String>();
				for(int i=0;i<mk.getIdMKSatMan().length;i++)
				{
					String tmp = mk.getIdMKSatMan()[i];
					listIDMK.add(mk.getIdMKSatMan()[i]);
				}
				detailRelasi = generateDetailRelasi(relasi, listIDMK);
			}
			relasiMKAlihjenjang.setRelasiMK(mk.getRelasiMKSatMan());
			relasiMKAlihjenjang.setDetailRelasiMK(detailRelasi);
			
			relasiMKAlihjenjangService.save(relasiMKAlihjenjang);
			MKAlihjenjang mkAlihjenjang = new MKAlihjenjang();
			MK mkSatMan = new MK();
			for(int i=0;i<max;i++)
			{
				EkuivalensiMKAlihjenjang ekuivalensiMKAlihjenjang = new EkuivalensiMKAlihjenjang();
				
				if(i < mk.getIdMKAlihjenjang().length)
					mkAlihjenjang = mkAlihjenjangService.getById(UUID.fromString(mk.getIdMKAlihjenjang()[i]));
				if(i < mk.getIdMKSatMan().length)
					mkSatMan = mkService.getById(UUID.fromString(mk.getIdMKSatMan()[i]));
				ekuivalensiMKAlihjenjang.setMk(mkSatMan);
				ekuivalensiMKAlihjenjang.setMkAlihjenjang(mkAlihjenjang);
				ekuivalensiMKAlihjenjang.setRelasiMKAlihjenjang(relasiMKAlihjenjang);
				ekuivalensiMKAlihjenjangService.save(ekuivalensiMKAlihjenjang);
			}
		}
        return new AjaxResponse("ok","Penyimpanan berhasil",null);
    }
	
	@Secured(value = { "ROLE_Administrator" })
	@RequestMapping(value = "/getlistmkekuivalen", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse getlistmkekuivalen(HttpSession session, @RequestParam("idKatalogAlihjenjang") UUID idKatalogAlihjenjang, 
    		@RequestParam("idKurikulum") UUID idKurikulum) {
		if(!isLogin(session)){ return null;}
		if(!hasMenu(session, "Manajemen Katalog Alihjenjang")) { return null;}
		
		AjaxResponse response = new AjaxResponse();
		KatalogAlihjenjang katalogAlihjenjang = katalogAlihjenjangService.getById(idKatalogAlihjenjang);
		Kurikulum kurikulum = kurikulumService.getById(idKurikulum);
		if(katalogAlihjenjang==null || kurikulum == null) return new AjaxResponse("error","Data tidak diketahui",null);
		else
		{
			List<EkuivalenMKAlihjenjang> listEkuivalenMKAlihjenjang = new ArrayList<EkuivalenMKAlihjenjang>();
			
			List<RelasiMKAlihjenjang> listRelasiMKAlihjenjang = relasiMKAlihjenjangService.get("katalog.idKatalogAlihjenjang = '"
					+katalogAlihjenjang.getIdKatalogAlihjenjang().toString()+"' AND kurikulum.idKurikulum = '"+kurikulum.getIdKurikulum()+"'");
			for (RelasiMKAlihjenjang relasiMKAlihjenjang : listRelasiMKAlihjenjang) 
			{								
				String relasi = relasiMKAlihjenjang.getRelasiMKAlihjenjang();
				String relasiMK = relasiMKAlihjenjang.getRelasiMK();
				List<MK> mkSatMan = ekuivalensiMKAlihjenjangService.getMKDistinct(relasiMKAlihjenjang.getIdRelasiMKAlihjenjang());
				List<MKAlihjenjang> mkAlihjenjang = ekuivalensiMKAlihjenjangService.getMKAlihjenjangDistinct(relasiMKAlihjenjang.getIdRelasiMKAlihjenjang());
				
				String[] idMKAlihjenjang = new String[mkAlihjenjang.size()];
				String[] kodeMKAlihjenjang = new String[mkAlihjenjang.size()];
				String[] namaMKAlihjenjang = new String[mkAlihjenjang.size()];
				String[] sksMKAlihjenjang = new String[mkAlihjenjang.size()];
				
				String[] idMKSatMan = new String[mkSatMan.size()];
				String[] kodeMKSatMan = new String[mkSatMan.size()];
				String[] namaMKSatMan = new String[mkSatMan.size()];
				String[] sksMKSatMan = new String[mkSatMan.size()];
				String[] semesterMKSatMan = new String[mkSatMan.size()];
				String[] sifatMKSatMan = new String[mkSatMan.size()];
				
				//Mengurutkan mk lama dan baru
				if(relasi.length() > 2)
				{
					String[] tmp = relasiMKAlihjenjang.getDetailRelasiMKAlihjenjang().split(";");
					int size = mkAlihjenjang.size();
					for(int i=0;i<size;i++)
					{
						mkAlihjenjang.remove(0);
					}
						
					for(int i=0;i<tmp.length;i++)
					{
						String[] idMK = tmp[i].split("=");
						MKAlihjenjang newMKAlihjenjang = mkAlihjenjangService.getById(UUID.fromString(idMK[1]));
						mkAlihjenjang.add(newMKAlihjenjang);
					}
				}
				
				if(relasiMK.length() > 2)
				{
					String[] tmp = relasiMKAlihjenjang.getDetailRelasiMK().split(";");
					int size = mkSatMan.size();
					for(int i=0;i<size;i++)
					{
						mkSatMan.remove(0);
					}
						
					for(int i=0;i<tmp.length;i++)
					{
						String[] idMK = tmp[i].split("=");
						MK newMKSatMan = mkService.getById(UUID.fromString(idMK[1]));
						mkSatMan.add(newMKSatMan);
					}
				}
				
				int max = mkAlihjenjang.size();
				if(max < mkSatMan.size())
					max = mkSatMan.size();
				for(int i=0;i<max;i++)
				{
					if(i<mkAlihjenjang.size())
					{
						idMKAlihjenjang[i] = mkAlihjenjang.get(i).getIdMKAlihjenjang().toString();
						kodeMKAlihjenjang[i] = mkAlihjenjang.get(i).getKodeMKAlihjenjang();
						namaMKAlihjenjang[i] = mkAlihjenjang.get(i).getNmMKAlihjenjang();
						sksMKAlihjenjang[i] = mkAlihjenjang.get(i).getJumlahSKS().toString();
						
					}
					if(i<mkSatMan.size())
					{
						idMKSatMan[i] = mkSatMan.get(i).getIdMK().toString();
						kodeMKSatMan[i] = mkSatMan.get(i).getKodeMK().toString();
						namaMKSatMan[i] = mkSatMan.get(i).getNamaMK();
						sksMKSatMan[i] = mkSatMan.get(i).getJumlahSKS().toString();
						List<SatManMK> list = satManMKService.get("mk.idMK = '"+mkSatMan.get(i).getIdMK().toString()+"' "
								+ "AND satMan.idSatMan = '"+kurikulum.getSatMan().getIdSatMan()+"'");
						semesterMKSatMan[i] = list.get(0).getTingkatPemb().toString(); 
						if(mkSatMan.get(i).getSifatMK())
							sifatMKSatMan[i] = "Wajib";
						else sifatMKSatMan[i] = "Pilihan";						
					}
				}
				EkuivalenMKAlihjenjang ekuivalenMKAlihjenjang = new EkuivalenMKAlihjenjang();
				ekuivalenMKAlihjenjang.setIdMKAlihjenjang(idMKAlihjenjang);
				ekuivalenMKAlihjenjang.setKodeMKAlihjenjang(kodeMKAlihjenjang);
				ekuivalenMKAlihjenjang.setNamaMKAlihjenjang(namaMKAlihjenjang);
				ekuivalenMKAlihjenjang.setSksMKAlihjenjang(sksMKAlihjenjang);
				ekuivalenMKAlihjenjang.setRelasiMKAlihjenjang(relasi);
				
				ekuivalenMKAlihjenjang.setIdMKSatMan(idMKSatMan);
				ekuivalenMKAlihjenjang.setKodeMKSatMan(kodeMKSatMan);
				ekuivalenMKAlihjenjang.setNamaMKSatMan(namaMKSatMan);
				ekuivalenMKAlihjenjang.setSksMKSatMan(sksMKSatMan);
				ekuivalenMKAlihjenjang.setSemesterMKSatMan(semesterMKSatMan);
				ekuivalenMKAlihjenjang.setSifatMKSatMan(sifatMKSatMan);
				ekuivalenMKAlihjenjang.setRelasiMKSatMan(relasiMK);
				listEkuivalenMKAlihjenjang.add(ekuivalenMKAlihjenjang);
			}
			response.setData(listEkuivalenMKAlihjenjang);
		}
		response.setStatus("ok");
		return response;
    }
	
	@Secured(value = { "ROLE_Administrator" })
	@RequestMapping(value = "/cekprasyarat", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse cekprasyarat(HttpSession session, @RequestParam("idMK") UUID idMK) {
		if(!isLogin(session)){ return null;}
		if(!hasMenu(session, "Manajemen Katalog Alihjenjang")) { return null;}
		
		AjaxResponse response = new AjaxResponse();
		MK mk = mkService.getById(idMK);
		if(mk == null) response = new AjaxResponse("error","Data tidak ditemukan",null);
		List<PrasyaratMK> listPrasyaratMK = prasyaratMKService.get("prasyaratMK.parentMK = '"+mk.getIdMK().toString()+"' AND prasyaratMK.statusPrasyarat = true");
		if(listPrasyaratMK.size() > 0)
			response.setData("true");
		else response.setData("false");
		response.setMessage("ok");
			
        return response;
    }
	
/*-----------------------------------Manajemen Matakuliah Alihjenjang------------------------------------------*/
	/*
	@Secured(value = { "ROLE_Administrator" })
	@RequestMapping(value = "/jsonmkalihjenjang", method = RequestMethod.POST)
	public @ResponseBody Datatable jsonmkalihjenjang(HttpSession session,
			@RequestParam("sEcho") String sEcho, 
			@RequestParam("iDisplayLength") int iDisplayLength,
            @RequestParam("iSortCol_0") int iSortCol_0,
            @RequestParam("sSortDir_0") String sSortDir_0,
            @RequestParam("sSearch") String sSearch,
			@RequestParam("iDisplayStart") int iDisplayStart,
			@RequestParam("idKatalogAlihjenjang") UUID idKatalogAlihjenjang
            ) {
		if(!isLogin(session)){ return null;}
		if(!hasMenu(session, "Manajemen Matakuliah Alihjenjang")) { return null;}
		
		String filter = "mkAlihjenjang.aMKAlihjenjangTerhapus = false";
		if(idKatalogAlihjenjang!=null) filter = "katalog.idKatalogAlihjenjang = '"+idKatalogAlihjenjang.toString()+"' AND "+filter;
		Datatable mkAlihjenjangDatatable = mkAlihjenjangService.getdatatable(sEcho, iDisplayLength, iDisplayStart, iSortCol_0, sSortDir_0, sSearch,filter);
		return mkAlihjenjangDatatable;
	}
	
	@Secured(value = { "ROLE_Administrator" })
	@RequestMapping(value = "/deletemkalihjenjang", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse deletemkalihjenjang(HttpSession session, @RequestParam("idMKAlihjenjang[]") UUID[] idMKAlihjenjang) {
		if(!isLogin(session)){ return null;}
		if(!hasMenu(session, "Manajemen Matakuliah Alihjenjang")) { return null;}
		
		AjaxResponse response;
		for (UUID uuid : idMKAlihjenjang) {
			mkAlihjenjangService.delete(uuid);
		}
		response = new AjaxResponse("ok","Data dihapus",null);
        return response;
    }
	
	@Secured(value = { "ROLE_Administrator" })
	@RequestMapping(value = "/editmkalihjenjang", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse editmkalihjenjang(HttpSession session, @RequestParam("idMKAlihjenjang") UUID idMKAlihjenjang) {
		if(!isLogin(session)){ return null;}
		if(!hasMenu(session, "Manajemen Matakuliah Alihjenjang")) { return null;}
		AjaxResponse response;
		MKAlihjenjang mkAlihjenjang = mkAlihjenjangService.getById(idMKAlihjenjang);
		if(idMKAlihjenjang == null) response = new AjaxResponse("error","Data tidak ditemukan",null);
		else response = new AjaxResponse("ok","Data ditemukan",mkAlihjenjang);
        return response;
    }
	
	@Secured(value = { "ROLE_Administrator" })
	@RequestMapping(value = "/simpanmkalihjenjang", method = RequestMethod.POST)
	 public @ResponseBody AjaxResponse simpanmkalihjenjang(HttpSession session, @Valid @ModelAttribute("mkAlihjenjang") MKAlihjenjang mkAlihjenjang,
	            BindingResult result, Map<String, Object> model,
	            @RequestParam("idKatalogAlihjenjang") UUID idKatalogAlihjenjang){
		if(!isLogin(session)){ return null;}
		if(!hasMenu(session, "Manajemen Matakuliah Alihjenjang")) { return null;}
		
		AjaxResponse response = new AjaxResponse();
		KatalogAlihjenjang katalogAlihjenjang =  katalogAlihjenjangService.getById(idKatalogAlihjenjang);
		if(katalogAlihjenjang == null) return new AjaxResponse("error","Katalog tidak diketahui",null);
		mkAlihjenjang.setKatalogAlihjenjang(katalogAlihjenjang);
		if (result.hasErrors()) {
        	response.setStatus("error");
        	List<FieldError> fieldError = result.getFieldErrors();
        	String message ="";
    		if(fieldError.get(0).isBindingFailure()) message += "Salah satu input tidak valid";
    		else message += fieldError.get(0).getDefaultMessage();
        	for(int i=1;i<fieldError.size();i++)
        	{
        		if(fieldError.get(i).isBindingFailure()) message += "Salah satu input tidak valid";
        		else message += "<br/>"+fieldError.get(i).getDefaultMessage();
        	}
        	response.setMessage(message);
        	response.setData(fieldError);
            return response;
        }
		response.setData(mkAlihjenjangService.save(mkAlihjenjang));
		response.setMessage("Penambahan sukses");
		return response;
    }
	
	/*-----------------------------------Manajemen Relasi ke Kurikulum------------------------------------------*/
	/*
	@Secured(value = { "ROLE_Administrator" })
	@RequestMapping(value = "/jsonkurikulum", method = RequestMethod.POST)
	public @ResponseBody Datatable jsonkurikulum(HttpSession session,
			@RequestParam("sEcho") String sEcho, 
			@RequestParam("iDisplayLength") int iDisplayLength,
            @RequestParam("iSortCol_0") int iSortCol_0,
            @RequestParam("sSortDir_0") String sSortDir_0,
            @RequestParam("sSearch") String sSearch,
			@RequestParam("iDisplayStart") int iDisplayStart,
			@RequestParam("idKatalogAlihjenjang") UUID idKatalogAlihjenjang
            ) {
		if(!isLogin(session)){ return null;}
		if(!hasMenu(session, "Manajemen Katalog Alihjenjang")) { return null;}
		
		String filter = "";
		if(idKatalogAlihjenjang!=null) filter = "katalog.idKatalogAlihjenjang = '"+idKatalogAlihjenjang.toString()+"'";
		Datatable mkAlihjenjangDatatable = relasiMKAlihjenjangService.getdatatable(sEcho, iDisplayLength, iDisplayStart, iSortCol_0, sSortDir_0, sSearch,filter);
		return mkAlihjenjangDatatable;
	}
	
	@Secured(value = { "ROLE_Administrator" })
	@RequestMapping(value = "/deletekurikulum", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse deletekurikulum(HttpSession session, @RequestParam("idRelasi[]") String[] idRelasi) {
		if(!isLogin(session)){ return null;}
		if(!hasMenu(session, "Manajemen Matakuliah Alihjenjang")) { return null;}
		
		AjaxResponse response;
		for (String string : idRelasi) {
			String[] tmp = string.split(";");
			List<RelasiMKAlihjenjang> listRelasi = relasiMKAlihjenjangService.get("katalog.idKatalogAlihjenjang = '"+tmp[0]+"' AND kurikulum.idKurikulum = '"+tmp[1]+"'");
			if(listRelasi.size() < 1) return new AjaxResponse("error","Data tidak ditemukan", null);
			
			for (RelasiMKAlihjenjang relasiMKAlihjenjang : listRelasi) {
				List<EkuivalensiMKAlihjenjang> listEkuivalensi = ekuivalensiMKAlihjenjangService.get("relasiMKAlihjenjang = '"+relasiMKAlihjenjang.getIdRelasiMKAlihjenjang().toString()+"'");
				for (EkuivalensiMKAlihjenjang ekuivalensiMKAlihjenjang : listEkuivalensi) {
					ekuivalensiMKAlihjenjangService.delete(ekuivalensiMKAlihjenjang.getIdEkuivalensiMKAlihjenjang());
				}
				relasiMKAlihjenjangService.delete(relasiMKAlihjenjang.getIdRelasiMKAlihjenjang());
			}
		}
		response = new AjaxResponse("ok","Data dihapus",null);
        return response;
    }
	
	@Secured(value = { "ROLE_Administrator" })
	@RequestMapping(value = "/jsonmodalkurikulum", method = RequestMethod.POST)
	public @ResponseBody Datatable jsonmodalkurikulum(HttpSession session,
			@RequestParam("sEcho") String sEcho, 
			@RequestParam("iDisplayLength") int iDisplayLength,
            @RequestParam("iSortCol_0") int iSortCol_0,
            @RequestParam("sSortDir_0") String sSortDir_0,
            @RequestParam("sSearch") String sSearch,
			@RequestParam("iDisplayStart") int iDisplayStart,
			@RequestParam("idKatalogAlihjenjang") UUID idKatalogAlihjenjang
            ) {
		if(!isLogin(session)){ return null;}
		if(!hasMenu(session, "Manajemen Katalog Alihjenjang")) { return null;}
		PeranPengguna peranPengguna = (PeranPengguna) session.getAttribute("peranPengguna");
		//add filter status hapus kurikulum
		String filter = "";
		if(idKatalogAlihjenjang != null)
		{
			KatalogAlihjenjang katalog = katalogAlihjenjangService.getById(idKatalogAlihjenjang);			
			List<Object[]> listObject = relasiMKAlihjenjangService.getRelasi("katalog.idKatalogAlihjenjang = '"+katalog.getIdKatalogAlihjenjang()+"'", "", -1, -1);
			filter += "satMan.idSatMan = '"+katalog.getSatMan().getIdSatMan()+"'";
			for(int i=0;i<listObject.size();i++)
			{
				Kurikulum a = (Kurikulum)listObject.get(i)[1];
				filter += " AND kurikulum.idKurikulum != '"+a.getIdKurikulum()+"'";
//				if(i+1 < listObject.size())
//					filter += " AND ";
			}
		}
		Datatable kurikulumDatatable = kurikulumService.getdatatable(sEcho, iDisplayLength, iDisplayStart, iSortCol_0, sSortDir_0, sSearch,filter);
		return kurikulumDatatable;
	}
	
	/*-----------------------------------Private Function------------------------------------------*/
	/*
	private String generateDetailRelasi(String relasi, List<String> listIDMK)
	{
		relasi = relasi.replaceAll("[()/^]", "");
		char huruf[] = new char[relasi.length()];
		for(int i=0;i<relasi.length();i++)
		{
			huruf[i] = relasi.charAt(i);
		}
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
	*/
}
