package com.bustan.siakad.controller;

import java.io.Console;
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
import com.bustan.siakad.service.EkuivalensiMKService;
import com.bustan.siakad.service.KatalogSatManService;
import com.bustan.siakad.service.KurikulumService;
import com.bustan.siakad.service.MKAlihjenjangService;
import com.bustan.siakad.service.KatalogAlihjenjangService;
import com.bustan.siakad.service.MKService;
import com.bustan.siakad.service.PrasyaratMKService;
import com.bustan.siakad.service.RelasiMKAlihjenjangService;
import com.bustan.siakad.service.SatManMKService;
import com.bustan.siakad.service.SatManService;
import com.sia.main.domain.PeranPengguna;
import com.sia.modul.domain.EkuivalenMKAlihjenjang;
import com.sia.modul.domain.EkuivalensiMKAlihjenjang;
import com.sia.modul.domain.KatalogSatMan;
import com.sia.modul.domain.Kurikulum;
import com.sia.modul.domain.MK;
import com.sia.modul.domain.MKAlihjenjang;
import com.sia.modul.domain.KatalogAlihjenjang;
import com.sia.modul.domain.PrasyaratMK;
import com.sia.modul.domain.RelasiMKAlihjenjang;
import com.sia.modul.domain.SatMan;
import com.sia.modul.domain.SatManMK;

@Controller
@Secured(value = { "ROLE_Admin" })
@RequestMapping(value = "/katalogsatman")
public class KatalogSatManController {
	
	@Autowired
	KatalogAlihjenjangService katalogAlihjenjangService;
	
	@Autowired
	MKAlihjenjangService mkAlihjenjangService;
	
	@Autowired
	SatManService satManService;
	
	@Autowired
	KatalogSatManService katalogSatManService;
	
	@Autowired
	KatalogAlihjenjangService KatalogAlihjenjangService;
	
	@Autowired
	KurikulumService kurikulumService;
	
	@Autowired
	RelasiMKAlihjenjangService relasiMKAlihjenjangService;
	
	@Autowired
	EkuivalensiMKAlihjenjangService ekuivalensiMKAlihjenjangService;
	
	@Autowired
	MKService mkService;
	
	@Autowired
	SatManMKService satManMKService;
	
	@Autowired
	PrasyaratMKService prasyaratMKService;
	
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView datatable(HttpSession session, Locale locale, Model model) {
		ModelAndView mav = new ModelAndView();
		
		KatalogSatMan katalogSatMan = new KatalogSatMan();
		List<KatalogAlihjenjang> listKatalog = new ArrayList<KatalogAlihjenjang>();
		List<SatMan> listSatMan = new ArrayList<SatMan>();
		PeranPengguna peranPengguna = (PeranPengguna) session.getAttribute("userRoleSession");
		if(peranPengguna.getPeran().getNamaPeran().compareToIgnoreCase("admin") != 0)
		{
			mav.addObject("admin",false);
			List<KatalogSatMan> listKatalogSatMan = katalogSatManService.get("satMan.idSatMan = '"+peranPengguna.getSatMan().getIdSatMan()+"'");
			String filter = "";
			if(listKatalogSatMan.size() > 0)
				filter += " AND (";
			for (int i =0;i<listKatalogSatMan.size();i++) {
				filter += "katalog.idKatalogAlihjenjang = '"+listKatalogSatMan.get(i).getKatalog().getIdKatalogAlihjenjang()+"'";
				if(i+1 < listKatalogSatMan.size())
					filter += " OR ";
			}
			if(listKatalogSatMan.size() > 0)
				filter += ")";
			
			listKatalog = katalogAlihjenjangService.get("katalog.aTerhapus = false"+filter);
			listSatMan = satManService.get("satMan.isSatManHasKurikulum = true AND satMan.idSatMan = '"+peranPengguna.getSatMan().getIdSatMan()+"'");
		}			
		else
		{
			listKatalog = katalogAlihjenjangService.get("katalog.aTerhapus = false");
			listSatMan = satManService.get("satMan.isSatManHasKurikulum = true");
			mav.addObject("admin",true);
		}
		mav.addObject("katalogSatMan",katalogSatMan);
		mav.addObject("listSatMan",listSatMan);
		mav.addObject("listKatalog",listKatalog);
		mav.setViewName("katalogsatman/home");
		
		return mav;
	}
	
	
	@RequestMapping(value = "/json", method = RequestMethod.POST)
	public @ResponseBody Datatable jsonkatalogsatman(HttpSession session,
			@RequestParam("sEcho") String sEcho, 
			@RequestParam("iDisplayLength") int iDisplayLength,
            @RequestParam("iSortCol_0") int iSortCol_0,
            @RequestParam("sSortDir_0") String sSortDir_0,
            @RequestParam("sSearch") String sSearch,
			@RequestParam("iDisplayStart") int iDisplayStart,
			@RequestParam("idKatalog") UUID idKatalog,
			@RequestParam("idSatMan") UUID idSatMan
            ) {
		
		String filter = "katalog.aTerhapus = false";
		if(idKatalog != null)
			filter += " AND katalog.idKatalogAlihjenjang = '"+idKatalog+"'";
		if(idSatMan != null)
			filter += " AND satMan.idSatMan = '"+idSatMan+"'";
		Datatable katalogAlihjenjangDatatable = katalogSatManService.getdatatable(sEcho, iDisplayLength, iDisplayStart, iSortCol_0, sSortDir_0, sSearch,filter);
		return katalogAlihjenjangDatatable;
	}
	
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse editkatalogsatman(HttpSession session, @RequestParam("idKatalogSatMan") UUID idKatalogSatMan) {
		AjaxResponse response;
		KatalogSatMan katalogSatMan = katalogSatManService.getById(idKatalogSatMan);
		if(katalogSatMan == null) response = new AjaxResponse("error","Data tidak ditemukan",null);
		else response = new AjaxResponse("ok","Data ditemukan",katalogSatMan);
        return response;
    }
	
	
	@RequestMapping(value = "/simpan", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse simpankatalogsatman(HttpSession session, @Valid @ModelAttribute("katalogSatMan") KatalogSatMan katalogSatMan,
            BindingResult result, Map<String, Object> model,
            @RequestParam("idKatalog") UUID idKatalog,
            @RequestParam("idSatMan") UUID idSatMan){
		
		AjaxResponse response = new AjaxResponse();
		if(idKatalog != null) katalogSatMan.setKatalog(katalogAlihjenjangService.getById(idKatalog));
		if(idSatMan != null) katalogSatMan.setSatMan(satManService.getById(idSatMan));
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
        
        List<KatalogSatMan> katalogsatman = katalogSatManService.get("katalog.idKatalogAlihjenjang = '"+katalogSatMan.getKatalog().getIdKatalogAlihjenjang()+"' AND satMan.idSatMan = '"+katalogSatMan.getSatMan().getIdSatMan()+"'");
        
        if(katalogsatman.size() > 0)
        {
        	response.setStatus("error");
        	response.setMessage("Data sudah ada");
        	return response;
        }
        response.setStatus("ok");
        
        response.setData(katalogSatManService.save(katalogSatMan));
        if(response.getData()!=null) response.setMessage("Data berhasil disimpan");
        else 
        {
        	response.setStatus("error");
        	response.setMessage("");
        }
        return response;
    }
	
	
	@RequestMapping(value = "/deletemany", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse deletemanykatalogsatman(HttpSession session, @RequestParam("idKatalogSatMan[]") UUID[] idKatalogSatMan) {
		
		AjaxResponse response;
		for (UUID uuid : idKatalogSatMan) {
			KatalogSatMan katalogSatMan = katalogSatManService.getById(uuid);
			List<Kurikulum> listKurikulum = kurikulumService.get("satMan.idSatMan = '"+katalogSatMan.getSatMan().getIdSatMan()+"'");
			String filter = "";
			for(int i=0;i<listKurikulum.size();i++)
			{
				filter += "kurikulum.idKurikulum = '"+listKurikulum.get(i).getIdKurikulum()+"'";
				if(i+1<listKurikulum.size())
					filter += " OR ";
			}
			List<RelasiMKAlihjenjang> listRelasi = relasiMKAlihjenjangService.get(filter);
			for (RelasiMKAlihjenjang relasiMKAlihjenjang : listRelasi) {
				List<EkuivalensiMKAlihjenjang> listEkuivalensi = ekuivalensiMKAlihjenjangService.get("relasiMKAlihjenjang.idRelasiMKAlihjenjang = '"+relasiMKAlihjenjang.getIdRelasiMKAlihjenjang()+"'");
				for (EkuivalensiMKAlihjenjang ekuivalensiMKAlihjenjang : listEkuivalensi) {
					ekuivalensiMKAlihjenjangService.delete(ekuivalensiMKAlihjenjang.getIdEkuivalensiMKAlihjenjang());
				}
				relasiMKAlihjenjangService.delete(relasiMKAlihjenjang.getIdRelasiMKAlihjenjang());
			}
			katalogSatManService.delete(uuid);
		}
		response = new AjaxResponse("ok","Data dihapus",null);
        return response;
    }
	
	
	@RequestMapping(value = "/jsonsatman", method = RequestMethod.POST)
	public @ResponseBody Datatable jsonsatman(HttpSession session,
			@RequestParam("sEcho") String sEcho, 
			@RequestParam("iDisplayLength") int iDisplayLength,
            @RequestParam("iSortCol_0") int iSortCol_0,
            @RequestParam("sSortDir_0") String sSortDir_0,
            @RequestParam("sSearch") String sSearch,
			@RequestParam("iDisplayStart") int iDisplayStart
            ) {
		String filter = "satMan.aSatManAktif = false AND satMan.isSatManHasKurikulum = true";
		Datatable katalogAlihjenjangDatatable = satManService.getdatatable(sEcho, iDisplayLength, iDisplayStart, iSortCol_0, sSortDir_0, sSearch,filter);
		return katalogAlihjenjangDatatable;
	}
	
	
	@RequestMapping(value = "/jsonkatalogkurikulum", method = RequestMethod.POST)
	public @ResponseBody Datatable jsonkurikulum(HttpSession session,
			@RequestParam("sEcho") String sEcho, 
			@RequestParam("iDisplayLength") int iDisplayLength,
            @RequestParam("iSortCol_0") int iSortCol_0,
            @RequestParam("sSortDir_0") String sSortDir_0,
            @RequestParam("sSearch") String sSearch,
			@RequestParam("iDisplayStart") int iDisplayStart,
			@RequestParam("idKatalog") UUID idKatalog,
			@RequestParam("idSatMan") UUID idSatMan
            ) {
		
		String filter = "";
		if(idKatalog!=null) filter = "katalog.idKatalogAlihjenjang = '"+idKatalog.toString()+"'";
		if(idSatMan != null)
		{
			List<Kurikulum> listKurikulum = kurikulumService.get("satMan.idSatMan = '"+idSatMan+"'");
			if(filter != "") filter += " AND ";
			filter += "(";
			for(int i=0;i<listKurikulum.size();i++)
			{
				filter += "kurikulum.idKurikulum = '"+listKurikulum.get(i).getIdKurikulum()+"'";
				if(i+1<listKurikulum.size())
					filter += " OR ";
			}
			filter +=")";
		}
		Datatable mkAlihjenjangDatatable = relasiMKAlihjenjangService.getdatatable(sEcho, iDisplayLength, iDisplayStart, iSortCol_0, sSortDir_0, sSearch,filter);
		return mkAlihjenjangDatatable;
	}
	
	
	@RequestMapping(value = "/jsonmodalkurikulum", method = RequestMethod.POST)
	public @ResponseBody Datatable jsonmodalkurikulum(HttpSession session,
			@RequestParam("sEcho") String sEcho, 
			@RequestParam("iDisplayLength") int iDisplayLength,
            @RequestParam("iSortCol_0") int iSortCol_0,
            @RequestParam("sSortDir_0") String sSortDir_0,
            @RequestParam("sSearch") String sSearch,
			@RequestParam("iDisplayStart") int iDisplayStart,
			@RequestParam("idKatalogSatMan") UUID idKatalogSatMan
            ) {
		PeranPengguna peranPengguna = (PeranPengguna) session.getAttribute("userRoleSession");
		//add filter status hapus kurikulum
		String filter = "kurikulum.statusKurikulum = false";
		if(idKatalogSatMan != null)
		{
			KatalogSatMan katalogSatMan = katalogSatManService.getById(idKatalogSatMan);
			KatalogAlihjenjang katalog = katalogAlihjenjangService.getById(katalogSatMan.getKatalog().getIdKatalogAlihjenjang());			
			List<Object[]> listObject = relasiMKAlihjenjangService.getRelasi("katalog.idKatalogAlihjenjang = '"+katalog.getIdKatalogAlihjenjang()+"'", "", -1, -1);
			filter += " AND satMan.idSatMan = '"+katalogSatMan.getSatMan().getIdSatMan()+"'";
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
	
	
	@RequestMapping(value = "/jsonmkalihjenjangbykatalog", method = RequestMethod.POST)
	public @ResponseBody Datatable jsonmkalihjenjangbykatalog(HttpSession session,
			@RequestParam("sEcho") String sEcho, 
			@RequestParam("iDisplayLength") int iDisplayLength,
            @RequestParam("iSortCol_0") int iSortCol_0,
            @RequestParam("sSortDir_0") String sSortDir_0,
            @RequestParam("sSearch") String sSearch,
			@RequestParam("iDisplayStart") int iDisplayStart,
			@RequestParam("idKatalog") String idKatalog
            ) {
		
		String filter = "mkAlihjenjang.aMKAlihjenjangTerhapus = false";
		if(idKatalog != "")
			filter = "katalog.idKatalogAlihjenjang = '"+idKatalog.toString()+"' AND "+filter;
		Datatable mkAlihjenjangDatatable = mkAlihjenjangService.getdatatable(sEcho, iDisplayLength, iDisplayStart, iSortCol_0, sSortDir_0, sSearch,filter);
		return mkAlihjenjangDatatable;
	}
	
	
	@RequestMapping(value = "/jsonmkbykurikulum", method = RequestMethod.POST)
	public @ResponseBody Datatable jsonmkbykurikulum(HttpSession session,
			@RequestParam("sEcho") String sEcho, 
			@RequestParam("iDisplayLength") int iDisplayLength,
            @RequestParam("iSortCol_0") int iSortCol_0,
            @RequestParam("sSortDir_0") String sSortDir_0,
            @RequestParam("sSearch") String sSearch,
			@RequestParam("iDisplayStart") int iDisplayStart,
			@RequestParam("idKurikulum") String idKurikulum
            ) {
		
		String filter = "mk.statusMK = false";
		if(idKurikulum != "")
		{						
			filter += " AND kurikulum.idKurikulum = '"+idKurikulum+"'";
		}
		Datatable mkDatatable = mkService.getdatatable(sEcho, iDisplayLength, iDisplayStart, iSortCol_0, sSortDir_0, sSearch,filter);
		return mkDatatable;
	}
	
	
	@RequestMapping(value = "/cekprasyarat", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse cekprasyarat(HttpSession session, @RequestParam("idMK") UUID idMK) {
		
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
	
	
	@RequestMapping(value = "/getlistmkekuivalen", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse getlistmkekuivalen(HttpSession session, @RequestParam("idKatalogAlihjenjang") UUID idKatalogAlihjenjang, 
    		@RequestParam("idKurikulum") UUID idKurikulum) {
		
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
//				String[] semesterMKSatMan = new String[mkSatMan.size()];
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
//						semesterMKSatMan[i] = list.get(0).getTingkatPemb().toString(); 
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
//				ekuivalenMKAlihjenjang.setSemesterMKSatMan(semesterMKSatMan);
				ekuivalenMKAlihjenjang.setSifatMKSatMan(sifatMKSatMan);
				ekuivalenMKAlihjenjang.setRelasiMKSatMan(relasiMK);
				listEkuivalenMKAlihjenjang.add(ekuivalenMKAlihjenjang);
			}
			response.setData(listEkuivalenMKAlihjenjang);
		}
		response.setStatus("ok");
		return response;
    }
	
	
	@RequestMapping(value = "saveekuivalen", method = RequestMethod.POST, produces="application/json")
    public @ResponseBody AjaxResponse saveekuivalen(HttpSession session, @RequestBody EkuivalenMKAlihjenjang[] ekuivalenMKAlihjenjang) {
		
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
	
	private String generateDetailRelasi(String relasi, List<String> listIDMK)
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
}
