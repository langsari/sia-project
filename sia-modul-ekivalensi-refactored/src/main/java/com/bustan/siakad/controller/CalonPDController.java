package com.bustan.siakad.controller;

import java.io.Console;
import java.sql.Struct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Stack;
import java.util.UUID;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
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

import com.bustan.siakad.service.AlihJenjangMKTerakuiService;
import com.bustan.siakad.service.CalonPDService;
import com.bustan.siakad.service.Datatable;
import com.bustan.siakad.service.AjaxResponse;
import com.bustan.siakad.service.CalonPDService;
import com.bustan.siakad.service.EkuivalensiMKAlihjenjangService;
import com.bustan.siakad.service.EkuivalensiMKPekuivalensiService;
import com.bustan.siakad.service.KatalogAlihjenjangService;
import com.bustan.siakad.service.KatalogSatManService;
import com.bustan.siakad.service.KonversiNilaiService;
import com.bustan.siakad.service.KrsCalonPDService;
import com.bustan.siakad.service.KurikulumService;
import com.bustan.siakad.service.MKAlihjenjangService;
import com.bustan.siakad.service.MKService;
import com.bustan.siakad.service.MKWajibCalonPDService;
import com.bustan.siakad.service.RelasiMKAlihjenjangService;
import com.bustan.siakad.service.RelasiMKPekuivalensiService;
import com.bustan.siakad.service.SatManService;
import com.bustan.siakad.service.SatManMKService;
import com.sia.main.domain.PeranPengguna;
import com.sia.modul.domain.AlihJenjangMKTerakui;
import com.sia.modul.domain.CalonPD;
import com.sia.modul.domain.EkuivalenMKAlihjenjang;
import com.sia.modul.domain.EkuivalensiMKPekuivalensi;
import com.sia.modul.domain.EkuivalensiPd;
import com.sia.modul.domain.KatalogAlihjenjang;
import com.sia.modul.domain.KatalogSatMan;
import com.sia.modul.domain.KonversiNilai;
import com.sia.modul.domain.Krs;
import com.sia.modul.domain.KrsCalonPD;
import com.sia.modul.domain.KrsHapus;
import com.sia.modul.domain.Kurikulum;
import com.sia.modul.domain.MK;
import com.sia.modul.domain.MKAlihjenjang;
import com.sia.modul.domain.MKBaruPdf;
import com.sia.modul.domain.MKEkuivalen;
import com.sia.modul.domain.MKLamaPdf;
import com.sia.modul.domain.MKLuar;
import com.sia.modul.domain.MKWajibAlihJenjang;
import com.sia.modul.domain.MKWajibCalonPD;
import com.sia.modul.domain.MKWajibPd;
import com.sia.modul.domain.Pd;
import com.sia.modul.domain.PedomanEkuivalensi;
import com.sia.modul.domain.RelasiMKAlihjenjang;
import com.sia.modul.domain.RelasiMKPekuivalensi;
import com.sia.modul.domain.SatMan;
import com.sia.modul.domain.SatManMK;

@Controller
@RequestMapping(value = "/calonpd")
public class CalonPDController {
	
	@Autowired
	CalonPDService calonPDService;
	
	@Autowired
	SatManService satManService;
	
	@Autowired
	KrsCalonPDService krsCalonPDService;
	
	@Autowired
	AlihJenjangMKTerakuiService alihJenjangMKTerakuiService;
	
	@Autowired
	MKAlihjenjangService mkAlihjenjangService;
	
	@Autowired
	MKService mkService;
	
	@Autowired
	KatalogAlihjenjangService katalogAlihjenjangService;
	
	@Autowired
	RelasiMKAlihjenjangService relasiMKAlihjenjangService;
	
	@Autowired
	EkuivalensiMKAlihjenjangService ekuivalensiMKAlihjenjangService;
	
	@Autowired
	KonversiNilaiService konversiNilaiService;
	
	@Autowired
	MKWajibCalonPDService mkWajibCalonPDService;
	
	@Autowired
	KurikulumService kurikulumService;
	
	@Autowired
	SatManMKService satManMKService;
	
	@Autowired
	KatalogSatManService katalogSatManService;
	/*
	 * URL Pendaftaran Mahasiswa Alihjenjang
	 * Hak Akses : Kepala
	 * return ModelAndView
	 */
	
	@Secured(value = { "ROLE_Tim Ekuivalensi" })
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView datatable(HttpSession session, Locale locale, Model model) {
		ModelAndView mav = new ModelAndView();
		
		PeranPengguna peranPengguna = (PeranPengguna) session.getAttribute("userRoleSession");
		List<SatMan> listSatMan = new ArrayList<SatMan>();
		
		String admin = "false";
		if(peranPengguna.getPeran().getNamaPeran().compareToIgnoreCase("admin") == 0)
			admin = "true";
		
		CalonPD calonPD = new CalonPD();
		
		List<KonversiNilai> listKonversiNilai = konversiNilaiService.get("konversiNilai.aStatusKonversiAktif = true","konversiNilai.huruf ASC");
		
		mav.addObject("admin",admin);
		mav.addObject("listKonversiNilai",listKonversiNilai);
		mav.setViewName("calonpd/home");
		
		mav.addObject ("menuActive","Manajemen Mahasiswa Alihjenjang"); 
		
		return mav;
	}
	
	/*
	 * Json Datatable Mahasiswa Alihjenjang
	 * Hak Akses : Kepala
	 * return Datatable
	 */	

	@Secured(value = { "ROLE_Tim Ekuivalensi" })
	@RequestMapping(value = "/json", method = RequestMethod.POST)
	public @ResponseBody Datatable json(HttpSession session,
			@RequestParam("sEcho") String sEcho, 
			@RequestParam("iDisplayLength") int iDisplayLength,
            @RequestParam("iSortCol_0") int iSortCol_0,
            @RequestParam("sSortDir_0") String sSortDir_0,
            @RequestParam("sSearch") String sSearch,
			@RequestParam("iDisplayStart") int iDisplayStart,
			@RequestParam("aEkuivalensi") String aEkuivalensi
            ) {
		//add filter simpan permanen false (jika non admin)
		
		String filter = "CAST(calonPD.aEkuivalensi as string) LIKE '%"+aEkuivalensi+"%'";
		
		PeranPengguna peranPengguna = (PeranPengguna) session.getAttribute("userRoleSession");
		List<KatalogSatMan> listKatalogSatMan = new ArrayList<KatalogSatMan>();
		//non admin
		if(peranPengguna.getPeran().getNamaPeran().compareToIgnoreCase("admin") != 0)
		{
			listKatalogSatMan = katalogSatManService.get("satMan.idSatMan = '"+peranPengguna.getSatMan().getIdSatMan()+"'");
			filter += " AND katalog.idKatalogAlihjenjang != null";
		}
		
		if(listKatalogSatMan.size() > 0)
			filter += " AND (";
		for(int i=0;i<listKatalogSatMan.size();i++)
		{
			filter += "katalog.idKatalogAlihjenjang = '"+listKatalogSatMan.get(i).getKatalog().getIdKatalogAlihjenjang().toString()+"'";
			if(i+1 < listKatalogSatMan.size())
				filter += " OR ";
		}
		if(listKatalogSatMan.size() > 0)
			filter += ")";
		Datatable calonPDDatatable = calonPDService.getdatatable(sEcho, iDisplayLength, iDisplayStart, iSortCol_0, sSortDir_0, sSearch,filter);
		return calonPDDatatable;
	}
	
	/*
	 * Ajax edit Mahasiswa Alihjenjang
	 * Hak Akses : Kepala
	 * return mahasiswa alihjenjang
	 */

	@Secured(value = { "ROLE_Tim Ekuivalensi" })
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse edit(HttpSession session, @RequestParam("idCalonPD") UUID idCalonPD) {
		
		AjaxResponse response;
		CalonPD calonPD = calonPDService.getById(idCalonPD);
		if(calonPD == null) response = new AjaxResponse("error","Data tidak ditemukan",null);
		else response = new AjaxResponse("ok","Data ditemukan",calonPD);
        return response;
    }

	@Secured(value = { "ROLE_Tim Ekuivalensi" })
	@RequestMapping(value = "/getcalonpddetail", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse getcalonpddetail(HttpSession session, @RequestParam("idCalonPD") UUID idCalonPD) {
		
		AjaxResponse response;
		CalonPD calonPD = calonPDService.getById(idCalonPD);
		if(calonPD == null) response = new AjaxResponse("error","Data tidak ditemukan",null);
		else response = new AjaxResponse("ok","Data ditemukan",calonPD);
        return response;
    }

	@Secured(value = { "ROLE_Tim Ekuivalensi" })
	@RequestMapping(value = "/simpan", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse simpan(HttpSession session, @Valid @ModelAttribute("calonPD") CalonPD calonPD,
            BindingResult result, Map<String, Object> model,
            @RequestParam("idSatMan") UUID idSatMan, @RequestParam("idKatalogAlihjenjang") UUID idKatalogAlihjenjang,
            @RequestParam("idKurikulum") UUID idKurikulum){
		
		AjaxResponse response = new AjaxResponse();
		if(idSatMan!=null)calonPD.setSatMan(satManService.getById(idSatMan));
		if(idKatalogAlihjenjang != null)
		{
			calonPD.setKatalogAlihjenjang(katalogAlihjenjangService.getById(idKatalogAlihjenjang));
		}
		else{
			calonPD.setKatalogAlihjenjang(null);
		}
        if(result.hasErrors()) {
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
        response.setData(calonPDService.save(calonPD));
        if(response.getData()!=null) response.setMessage("Data berhasil disimpan");
        else 
        {
        	response.setStatus("error");
        	response.setMessage("");
        }
        return response;
    }

	@Secured(value = { "ROLE_Tim Ekuivalensi" })
	@RequestMapping(value = "/deletemany", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse deleteMany(HttpSession session, @RequestParam("idCalonPD[]") UUID[] idCalonPD) {
		
		AjaxResponse response;
		for (UUID uuid : idCalonPD) {
			List<KrsCalonPD> listKrsCalonPd = krsCalonPDService.get("calonPD.idCalonPD = '"+uuid.toString()+"'");
			for (KrsCalonPD krsCalonPD : listKrsCalonPd) {
				krsCalonPDService.delete(krsCalonPD.getIdKrsCalonPD());
			}
			List<AlihJenjangMKTerakui> listMKTerakui = alihJenjangMKTerakuiService.get("calonPD.idCalonPD = '"+uuid.toString()+"'");
			for (AlihJenjangMKTerakui alihJenjangMKTerakui : listMKTerakui) {
				alihJenjangMKTerakuiService.delete(alihJenjangMKTerakui.getIdAlihJenjangMKTerakui());
			}
			List<MKWajibCalonPD> listMKWajibCalonPD = mkWajibCalonPDService.get("calonPD.idCalonPD = '"+uuid.toString()+"'");
			for (MKWajibCalonPD mkWajibCalonPD : listMKWajibCalonPD) {
				mkWajibCalonPDService.delete(mkWajibCalonPD.getIdMKWajibCalonPD());
			}
			calonPDService.delete(uuid);
		}
		response = new AjaxResponse("ok","Data dihapus",null);
        return response;
    }

	@Secured(value = { "ROLE_Tim Ekuivalensi" })
	@RequestMapping(value = "/jsonmodalkurikulum", method = RequestMethod.POST)
	public @ResponseBody Datatable jsonmodalkurikulum(HttpSession session,
			@RequestParam("sEcho") String sEcho, 
			@RequestParam("iDisplayLength") int iDisplayLength,
            @RequestParam("iSortCol_0") int iSortCol_0,
            @RequestParam("sSortDir_0") String sSortDir_0,
            @RequestParam("sSearch") String sSearch,
			@RequestParam("iDisplayStart") int iDisplayStart,
			@RequestParam("idCalonPD") UUID idCalonPD,
			@RequestParam("statusBerlaku") String statusBerlaku
            ) {
		PeranPengguna peranPengguna = (PeranPengguna) session.getAttribute("userRoleSession");
		//add filter status hapus kurikulum
		String filter = "(CAST(kurikulum.statusBerlaku as string) LIKE '%"+statusBerlaku+"%' AND kurikulum.statusKurikulum = false)";
		if(idCalonPD != null)
		{
			CalonPD calonPD = calonPDService.getById(idCalonPD);
			if(calonPD.getKatalogAlihjenjang() != null)
			{
				List<KatalogSatMan> listKatalogSatMan = new ArrayList<KatalogSatMan>();
				if(peranPengguna.getPeran().getNamaPeran().compareToIgnoreCase("admin") == 0)
				{
					listKatalogSatMan = katalogSatManService.get("katalog.idKatalogAlihjenjang = '"+calonPD.getKatalogAlihjenjang().getIdKatalogAlihjenjang()+"'");
				}
				else
				{
					listKatalogSatMan = katalogSatManService.get("katalog.idKatalogAlihjenjang = '"+calonPD.getKatalogAlihjenjang().getIdKatalogAlihjenjang()+"'"
							+ " AND satMan.idSatMan = '"+peranPengguna.getSatMan().getIdSatMan()+"'");
				}
				if(listKatalogSatMan.size() > 0)
					filter += " AND (";
				for(int i=0;i<listKatalogSatMan.size();i++)
				{
					filter += "satMan.idSatMan = '"+listKatalogSatMan.get(i).getSatMan().getIdSatMan()+"'";
					if(i+1 < listKatalogSatMan.size())
						filter += " OR ";
					
				}
				if(listKatalogSatMan.size() > 0)
					filter += ")";
			}
			else
			{
				List<Kurikulum> listKurikulum = satManMKService.getKurikulumDistinct("satMan.idSatMan = '"+calonPD.getSatMan().getIdSatMan()+"' AND "+filter);
				if(listKurikulum.size() > 0)
					filter += " AND (";				
				for(int i=0;i<listKurikulum.size();i++) {
					filter += "kurikulum.idKurikulum = '"+listKurikulum.get(i).getIdKurikulum()+"'";
					if(i+1 < listKurikulum.size())
						filter += " OR ";
				}
				if(listKurikulum.size() > 0)
					filter += ")";
			}
		}
		System.out.print(filter);
		Datatable kurikulumDatatable = kurikulumService.getdatatable(sEcho, iDisplayLength, iDisplayStart, iSortCol_0, sSortDir_0, sSearch,filter);
		return kurikulumDatatable;
	}

	@Secured(value = { "ROLE_Tim Ekuivalensi" })
	@RequestMapping(value = "/jsonekuivalensi", method = RequestMethod.POST)
	public @ResponseBody Datatable jsonekuivalensi(HttpSession session,
			@RequestParam("sEcho") String sEcho, 
			@RequestParam("iDisplayLength") int iDisplayLength,
            @RequestParam("iSortCol_0") int iSortCol_0,
            @RequestParam("sSortDir_0") String sSortDir_0,
            @RequestParam("sSearch") String sSearch,
			@RequestParam("iDisplayStart") int iDisplayStart,
			@RequestParam("idCalonPD") UUID idCalonPD,
			@RequestParam("statusBerlaku") String statusBerlaku
            ) {
		PeranPengguna peranPengguna = (PeranPengguna) session.getAttribute("userRoleSession");
		String filter = "CAST(kurikulum.statusBerlaku as string) LIKE '%"+statusBerlaku+"%'";
		if(idCalonPD!=null)
		{
			CalonPD calonPD = calonPDService.getById(idCalonPD);
			//if non admin
			List<KatalogSatMan> listKatalogSatMan = new ArrayList<KatalogSatMan>();
			if(peranPengguna.getPeran().getNamaPeran().compareToIgnoreCase("admin") == 0)
			{
				listKatalogSatMan = katalogSatManService.get("katalog.idKatalogAlihjenjang = '"+calonPD.getKatalogAlihjenjang().getIdKatalogAlihjenjang()+"'");
			}
			else
			{
				listKatalogSatMan = katalogSatManService.get("satMan.idSatMan = '"+peranPengguna.getSatMan().getIdSatMan()+"' AND "
					+ "katalog.idKatalogAlihjenjang = '"+calonPD.getKatalogAlihjenjang().getIdKatalogAlihjenjang()+"'");
			}
			if(listKatalogSatMan.size() > 0)
				filter += " AND (";
			for(int i=0;i<listKatalogSatMan.size();i++) 
			{
				filter += "satMan.idSatMan = '"+listKatalogSatMan.get(i).getSatMan().getIdSatMan()+"'";
				if(i+1<listKatalogSatMan.size())
					filter += " OR ";
			}
			if(listKatalogSatMan.size() > 0)
				filter += ")";
			
			List<Kurikulum> listKurikulum = kurikulumService.get(filter+" AND kurikulum.statusKurikulum = false");
			filter ="katalog.idKatalogAlihjenjang = '"+calonPD.getKatalogAlihjenjang().getIdKatalogAlihjenjang()+"'";
			if(listKurikulum.size() > 0)
				filter += " AND (";
			for(int i=0;i<listKurikulum.size();i++)
			{
				filter += "kurikulum.idKurikulum = '"+listKurikulum.get(i).getIdKurikulum().toString()+"'";
				if(i+1<listKurikulum.size())
					filter += " OR ";
			}
			if(listKurikulum.size() > 0)
				filter += ")";
			
			
		}
//		else
//		{
//			String tmp = "";
//			PeranPengguna peranPengguna = (PeranPengguna) session.getAttribute("userRoleSession");
//			List<SatMan> listSatMan = satManService.listChild(peranPengguna.getSatMan().getIdSatMan());
//			for(int i=0;i<listSatMan.size();i++)
//			{
//				tmp += "satMan.idSatMan = '"+listSatMan.get(i).getIdSatMan()+"'";
//				if(i+1<listSatMan.size())
//					tmp += " OR ";
//			}
//			List<Kurikulum> listKurikulum = kurikulumService.get(tmp);
//			for(int i=0;i<listKurikulum.size();i++)
//			{
//				filter += "kurikulum.idKurikulum = '"+listKurikulum.get(i).getIdKurikulum().toString()+"'";
//				if(i+1<listKurikulum.size())
//					filter += " OR ";
//			}
//		}
			
		Datatable mkAlihjenjangDatatable = relasiMKAlihjenjangService.getdatatable(sEcho, iDisplayLength, iDisplayStart, iSortCol_0, sSortDir_0, sSearch,filter);
		return mkAlihjenjangDatatable;
	}

	@Secured(value = { "ROLE_Tim Ekuivalensi" })
	@RequestMapping(value = "/getdatablemkluar", method = RequestMethod.POST)
	public @ResponseBody Datatable getdatablemkluar(HttpSession session,
			@RequestParam("sEcho") String sEcho, 
			@RequestParam("iDisplayLength") int iDisplayLength,
            @RequestParam("iSortCol_0") int iSortCol_0,
            @RequestParam("sSortDir_0") String sSortDir_0,
            @RequestParam("sSearch") String sSearch,
			@RequestParam("iDisplayStart") int iDisplayStart,
			@RequestParam("idCalonPD") UUID idCalonPD
            ) {
		
		String filter = "mkAlihjenjang.aMKAlihjenjangTerhapus = false";
		if(idCalonPD != null) filter = "calonPD.idCalonPD = '"+idCalonPD.toString()+"' AND "+filter;
		Datatable krsCalonPDDatatable = krsCalonPDService.getdatatable(sEcho, iDisplayLength, iDisplayStart, iSortCol_0, sSortDir_0, sSearch,filter);
		return krsCalonPDDatatable;
	}

	@Secured(value = { "ROLE_Tim Ekuivalensi" })
	@RequestMapping(value = "/getdatablemk", method = RequestMethod.POST)
	public @ResponseBody Datatable getdatablemk(HttpSession session,
			@RequestParam("sEcho") String sEcho, 
			@RequestParam("iDisplayLength") int iDisplayLength,
            @RequestParam("iSortCol_0") int iSortCol_0,
            @RequestParam("sSortDir_0") String sSortDir_0,
            @RequestParam("sSearch") String sSearch,
			@RequestParam("iDisplayStart") int iDisplayStart,
			@RequestParam("idCalonPD") UUID idCalonPD,
			@RequestParam("idKurikulum") UUID idKurikulum
            ) {
		
		String filter = "mk.statusMK = false";
		PeranPengguna peranPengguna = (PeranPengguna) session.getAttribute("userRoleSession");
		//non admin
		
		if(idCalonPD != null) filter = "calonPD.idCalonPD = '"+idCalonPD.toString()+"' AND "+filter;
		if(idKurikulum != null) filter = "kurikulum.idKurikulum = '"+idKurikulum.toString()+"' AND "+filter;
		
		
		Datatable alihJenjangMKTerakuiDatatable = alihJenjangMKTerakuiService.getdatatable(sEcho, iDisplayLength, iDisplayStart, iSortCol_0, sSortDir_0, sSearch,filter);
		return alihJenjangMKTerakuiDatatable;
	}

	@Secured(value = { "ROLE_Tim Ekuivalensi" })
	@RequestMapping(value = "/getmkluar", method = RequestMethod.POST)
	public @ResponseBody Datatable getmkluar(HttpSession session,
			@RequestParam("sEcho") String sEcho, 
			@RequestParam("iDisplayLength") int iDisplayLength,
            @RequestParam("iSortCol_0") int iSortCol_0,
            @RequestParam("sSortDir_0") String sSortDir_0,
            @RequestParam("sSearch") String sSearch,
			@RequestParam("iDisplayStart") int iDisplayStart,
			@RequestParam("idCalonPD") UUID idCalonPD
            ) {
		
		String filter = "mkAlihjenjang.aMKAlihjenjangTerhapus = false";
		if(idCalonPD != null)
		{
			CalonPD calonPD = calonPDService.getById(idCalonPD);
			if(calonPD != null)
			{
				filter += " AND katalog.idKatalogAlihjenjang = '"+calonPD.getKatalogAlihjenjang().getIdKatalogAlihjenjang().toString()+"'";
			}
			List<KrsCalonPD> listKRS = krsCalonPDService.get("calonPD.idCalonPD = '"+idCalonPD.toString()+"'");
			for (int i = 0; i < listKRS.size(); i++) 
			{
				if(i==0)
					filter += " AND ";
				filter += "mkAlihjenjang.idMKAlihjenjang != '"+listKRS.get(i).getMkAlihjenjang().getIdMKAlihjenjang().toString()+"'";
				if(i+1 != listKRS.size())
					filter += " AND ";
			}				
		}
		Datatable mkAlihjenjangDatatable = mkAlihjenjangService.getdatatable(sEcho, iDisplayLength, iDisplayStart, iSortCol_0, sSortDir_0, sSearch, filter);
		return mkAlihjenjangDatatable;
	}

	@Secured(value = { "ROLE_Tim Ekuivalensi" })
	@RequestMapping(value = "/getmk", method = RequestMethod.POST)
	public @ResponseBody Datatable getmk(HttpSession session,
			@RequestParam("sEcho") String sEcho, 
			@RequestParam("iDisplayLength") int iDisplayLength,
            @RequestParam("iSortCol_0") int iSortCol_0,
            @RequestParam("sSortDir_0") String sSortDir_0,
            @RequestParam("sSearch") String sSearch,
			@RequestParam("iDisplayStart") int iDisplayStart,
			@RequestParam("idKurikulum") UUID idKurikulum,
			@RequestParam("idCalonPD") UUID idCalonPD
            ) {
		String filter = "mk.statusMK = false";
		
		if(idCalonPD != null && idKurikulum != null)
		{	
			CalonPD calonPD = calonPDService.getById(idCalonPD);
			Kurikulum kurikulum = kurikulumService.getById(idKurikulum);
			if(calonPD == null || kurikulum == null) return null;
			filter += " AND kurikulum.idKurikulum = '"+kurikulum.getIdKurikulum().toString()+"'";
			List<AlihJenjangMKTerakui> listMKTerakui = alihJenjangMKTerakuiService.get("calonPD.idCalonPD = '"+calonPD.getIdCalonPD()+"'");
			for (int i = 0; i < listMKTerakui.size(); i++) {
				filter += " AND ";
				filter += "mk.idMK != '"+listMKTerakui.get(i).getMk().getIdMK().toString()+"'";
//				if(i+1 != listMKTerakui.size())
//					filter += " AND ";
			}				
		}
//		System.out.print("filter "+filter);
		Datatable mkLuarDatatable = mkService.getdatatable(sEcho, iDisplayLength, iDisplayStart, iSortCol_0, sSortDir_0, sSearch, filter);
		return mkLuarDatatable;
	}

	@Secured(value = { "ROLE_Tim Ekuivalensi" })
	@RequestMapping(value = "/getmkwajibs", method = RequestMethod.POST)
	public @ResponseBody AjaxResponse getmkwajibs(HttpSession session, @RequestParam("idCalonPD") UUID idCalonPD) {
		
		AjaxResponse response = new AjaxResponse();
		String filter = "mk.statusMK = false AND mk.sifatMK = true AND kurikulum.statusKurikulum = true";
		CalonPD calonPD = calonPDService.getById(idCalonPD);
		if(calonPD == null) return new AjaxResponse("error","Data tidak ditemukan",null);
		SatMan satMan = satManService.getById(calonPD.getSatMan().getIdSatMan());
		if(satMan == null) return new AjaxResponse("error","Data tidak ditemukan",null);
		filter += " AND satMan.idSatMan = '"+satMan.getIdSatMan().toString()+"'";
		
		MKWajibAlihJenjang mkWajibAlihJenjang = new MKWajibAlihJenjang();
		
		List<MKWajibCalonPD> listMKWajibCalonPD = mkWajibCalonPDService.get("calonPD.idCalonPD = '"+calonPD.getIdCalonPD().toString()+"'");
		if(listMKWajibCalonPD.size()==0)
		{
			List<SatManMK> listMKWajib = satManMKService.get(filter,"mk.tingkatPemb ASC");			
			String[] idMK = new String[listMKWajib.size()];
			String[] kodeMK = new String[listMKWajib.size()];
			String[] namaMK = new String[listMKWajib.size()];
			String[] jumlahSKS = new String[listMKWajib.size()];
			String[] status = new String[listMKWajib.size()];
			
			List<AlihJenjangMKTerakui> listMKTerakui = alihJenjangMKTerakuiService.get("calonPD.idCalonPD = '"+calonPD.getIdCalonPD().toString()+"'");
			HashMap<String, Boolean> hasMapMKTerakui = new HashMap<String, Boolean>();
			for (AlihJenjangMKTerakui mkTerakui : listMKTerakui) 
			{
				hasMapMKTerakui.put(mkTerakui.getMk().getIdMK().toString(), true);
			}
			
			for(int i=0;i<listMKWajib.size();i++)
			{
				idMK[i] = listMKWajib.get(i).getMk().getIdMK().toString();
				kodeMK[i] = listMKWajib.get(i).getMk().getKodeMK();
				namaMK[i] = listMKWajib.get(i).getMk().getNamaMK();
				jumlahSKS[i] = listMKWajib.get(i).getMk().getJumlahSKS().toString();
				
				if(!hasMapMKTerakui.containsKey(listMKWajib.get(i).getMk().getIdMK().toString()))
					status[i] = "true";
				else status[i] = "false";
			}
			
			mkWajibAlihJenjang.setIdMK(idMK);
			mkWajibAlihJenjang.setKodeMK(kodeMK);
			mkWajibAlihJenjang.setNamaMK(namaMK);
			mkWajibAlihJenjang.setJumlahSKS(jumlahSKS);
			mkWajibAlihJenjang.setStatus(status);
		}
		else
		{	
			String[] idMK = new String[listMKWajibCalonPD.size()];
			String[] kodeMK = new String[listMKWajibCalonPD.size()];
			String[] namaMK = new String[listMKWajibCalonPD.size()];
			String[] jumlahSKS = new String[listMKWajibCalonPD.size()];
			String[] status = new String[listMKWajibCalonPD.size()];
			
			for(int i=0;i<listMKWajibCalonPD.size();i++)
			{
				idMK[i] = listMKWajibCalonPD.get(i).getMk().getIdMK().toString();
				kodeMK[i] = listMKWajibCalonPD.get(i).getMk().getKodeMK();
				namaMK[i] = listMKWajibCalonPD.get(i).getMk().getNamaMK();
				jumlahSKS[i] = listMKWajibCalonPD.get(i).getMk().getJumlahSKS().toString();
				status[i] = String.valueOf(listMKWajibCalonPD.get(i).isaStatusAmbil());				
			}
			
			mkWajibAlihJenjang.setIdMK(idMK);
			mkWajibAlihJenjang.setKodeMK(kodeMK);
			mkWajibAlihJenjang.setNamaMK(namaMK);
			mkWajibAlihJenjang.setJumlahSKS(jumlahSKS);
			mkWajibAlihJenjang.setStatus(status);
		}
		response.setStatus("ok");
		response.setMessage("ok");
		response.setData(mkWajibAlihJenjang);
		return response;
	}

	@Secured(value = { "ROLE_Tim Ekuivalensi" })
	@RequestMapping(value = "/getmkwajib", method = RequestMethod.POST)
	public @ResponseBody AjaxResponse getmkwajib(HttpSession session, @RequestParam("idCalonPD") UUID idCalonPD,
			@RequestParam("idKurikulum") UUID idKurikulum) {
		
		AjaxResponse response = new AjaxResponse();
		String filter = "mk.statusMK = false AND mk.sifatMK = true AND kurikulum.statusKurikulum = true";
		CalonPD calonPD = calonPDService.getById(idCalonPD);
		if(calonPD == null) return new AjaxResponse("error","Data tidak ditemukan",null);
			
		
		List<MKWajibCalonPD> listMKWajibCalonPD = mkWajibCalonPDService.get("calonPD.idCalonPD = '"+calonPD.getIdCalonPD().toString()+"' AND kurikulum.idKurikulum = '"+idKurikulum+"'");
		HashMap<String, Boolean> hashMapListMKWajibPD = new HashMap<String, Boolean>();
		for (MKWajibCalonPD mkWajibCalonPD : listMKWajibCalonPD) {
			hashMapListMKWajibPD.put(mkWajibCalonPD.getMk().getIdMK().toString(), mkWajibCalonPD.isaStatusAmbil());
		}		
		
		List<AlihJenjangMKTerakui> listMKTerakui = alihJenjangMKTerakuiService.get("calonPD.idCalonPD = '"+calonPD.getIdCalonPD().toString()+"' AND kurikulum.idKurikulum = '"+idKurikulum+"'");
		HashMap<String, Boolean> hashMapListMKTerakui = new HashMap<String, Boolean>();
		for (AlihJenjangMKTerakui alihJenjangMKTerakui : listMKTerakui) {
			hashMapListMKTerakui.put(alihJenjangMKTerakui.getMk().getIdMK().toString(), true);
		}
		
		List<KrsCalonPD> listKrsCalonPD = krsCalonPDService.get("calonPD.idCalonPD = '"+calonPD.getIdCalonPD().toString()+"'");
		HashMap<String, Boolean> hashMapListKrsCalonPD = new HashMap<String, Boolean>();
		for (KrsCalonPD krsCalonPD : listKrsCalonPD) {
			hashMapListKrsCalonPD.put(krsCalonPD.getMkAlihjenjang().getIdMKAlihjenjang().toString(), krsCalonPD.isaLulus());
		}
		System.out.print(listMKTerakui.size()+"-"+listMKWajibCalonPD.size());
		List<MKAlihjenjang> listMKAlihjenjang = new ArrayList<MKAlihjenjang>();
		List<MK> listMK = mkService.get("kurikulum.idKurikulum = '"+idKurikulum+"' AND mk.statusMK = false","mk.namaMK ASC");
		List<EkuivalenMKAlihjenjang> listEkuivalenMKAlihjenjang = new ArrayList<EkuivalenMKAlihjenjang>();
		if(calonPD.getKatalogAlihjenjang() != null)
		{		
			
			List<RelasiMKAlihjenjang> listRelasiMKAlihjenjang = relasiMKAlihjenjangService.get("katalog.idKatalogAlihjenjang = '"
					+calonPD.getKatalogAlihjenjang().getIdKatalogAlihjenjang().toString()+"' AND kurikulum.idKurikulum = '"+idKurikulum+"'");			
			
			listMKAlihjenjang = mkAlihjenjangService.get("katalog.idKatalogAlihjenjang = '"+calonPD.getKatalogAlihjenjang().getIdKatalogAlihjenjang()+"' AND mkAlihjenjang.aMKAlihjenjangTerhapus = false");
			
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
				String[] statusMKAlihjenjang = new String[mkAlihjenjang.size()];
				
				String[] idMKSatMan = new String[mkSatMan.size()];
				String[] kodeMKSatMan = new String[mkSatMan.size()];
				String[] namaMKSatMan = new String[mkSatMan.size()];
				String[] sksMKSatMan = new String[mkSatMan.size()];
				String[] semesterMKSatMan = new String[mkSatMan.size()];
				String[] sifatMKSatMan = new String[mkSatMan.size()];
				String[] statusMKSatMan = new String[mkSatMan.size()];
				
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
						listMKAlihjenjang.remove(mkAlihjenjang.get(i));
						idMKAlihjenjang[i] = mkAlihjenjang.get(i).getIdMKAlihjenjang().toString();
						kodeMKAlihjenjang[i] = mkAlihjenjang.get(i).getKodeMKAlihjenjang();
						namaMKAlihjenjang[i] = mkAlihjenjang.get(i).getNmMKAlihjenjang();
						sksMKAlihjenjang[i] = mkAlihjenjang.get(i).getJumlahSKS().toString();
						if(CekRelasi(relasiMKAlihjenjang, idCalonPD, hashMapListKrsCalonPD))
							statusMKAlihjenjang[i] = "Diakui";
						else if(hashMapListKrsCalonPD.containsKey(mkAlihjenjang.get(i).getIdMKAlihjenjang().toString()))
						{
							if(hashMapListKrsCalonPD.get(mkAlihjenjang.get(i).getIdMKAlihjenjang().toString()))
								statusMKAlihjenjang[i] = "Tidak Diakui";
							else statusMKAlihjenjang[i] = "Tidak Lulus";
						}
							
						else statusMKAlihjenjang[i] = "Tidak Ambil";
	//					if(hashMapListMKTerakui.containsKey(mkAlihjenjang.get(i).getIdMKAlihjenjang().toString()))
	//						statusMKAlihjenjang[i] = hashMapListMKTerakui.get(mkAlihjenjang.get(i).getIdMKAlihjenjang().toString()).toString();
	//					else statusMKAlihjenjang[i] = "false";
					}
					if(i<mkSatMan.size())
					{
						// Wajib atau bebas
						
						listMK.remove(mkSatMan.get(i));
						idMKSatMan[i] = mkSatMan.get(i).getIdMK().toString();
						kodeMKSatMan[i] = mkSatMan.get(i).getKodeMK().toString();
						namaMKSatMan[i] = mkSatMan.get(i).getNamaMK();
						sksMKSatMan[i] = mkSatMan.get(i).getJumlahSKS().toString();
						if(mkSatMan.get(i).getSifatMK())
							sifatMKSatMan[i] = "Wajib";
						else sifatMKSatMan[i] = "Pilihan";
						if(hashMapListMKWajibPD.containsKey(mkSatMan.get(i).getIdMK().toString()))
						{
							if(hashMapListMKWajibPD.get(mkSatMan.get(i).getIdMK().toString()))
							statusMKSatMan[i] = "Ambil";
							else statusMKSatMan[i] = "Bebas";
						}							
						else if(!CekRelasiKanan(relasiMKAlihjenjang, idCalonPD, hashMapListMKTerakui))
						{						
							//Sesuai sifat
							if(hashMapListMKTerakui.containsKey(mkSatMan.get(i).getIdMK().toString()))
								statusMKSatMan[i] = "Bebas";
							else
							{
								if(mkSatMan.get(i).getSifatMK())
									statusMKSatMan[i] = "Ambil";
								else statusMKSatMan[i] = "Bebas";	
							}
						}
						//Bebas
						else statusMKSatMan[i] = "Bebas";
					}
				}
				EkuivalenMKAlihjenjang ekuivalenMKAlihjenjang = new EkuivalenMKAlihjenjang();
				ekuivalenMKAlihjenjang.setIdMKAlihjenjang(idMKAlihjenjang);
				ekuivalenMKAlihjenjang.setKodeMKAlihjenjang(kodeMKAlihjenjang);
				ekuivalenMKAlihjenjang.setNamaMKAlihjenjang(namaMKAlihjenjang);
				ekuivalenMKAlihjenjang.setSksMKAlihjenjang(sksMKAlihjenjang);
				ekuivalenMKAlihjenjang.setRelasiMKAlihjenjang(relasi);
				ekuivalenMKAlihjenjang.setStatusMKAlihjenjang(statusMKAlihjenjang);
				
				ekuivalenMKAlihjenjang.setIdMKSatMan(idMKSatMan);
				ekuivalenMKAlihjenjang.setKodeMKSatMan(kodeMKSatMan);
				ekuivalenMKAlihjenjang.setNamaMKSatMan(namaMKSatMan);
				ekuivalenMKAlihjenjang.setSksMKSatMan(sksMKSatMan);
	//			ekuivalenMKAlihjenjang.setSemesterMKSatMan(semesterMKSatMan);
				ekuivalenMKAlihjenjang.setSifatMKSatMan(sifatMKSatMan);
				ekuivalenMKAlihjenjang.setRelasiMKSatMan(relasiMK);
				ekuivalenMKAlihjenjang.setStatusMKSatMan(statusMKSatMan);
				listEkuivalenMKAlihjenjang.add(ekuivalenMKAlihjenjang);
			}
		}
		int max = Math.max(listMK.size(), listMKAlihjenjang.size());
		for(int i=0;i<max;i++)
		{
			String[] idMKAlihjenjang = new String[1];
			String[] kodeMKAlihjenjang = new String[1];
			String[] namaMKAlihjenjang = new String[1];
			String[] sksMKAlihjenjang = new String[1];
			String[] statusMKAlihjenjang = new String[1];
			
			String[] idMKSatMan = new String[1];
			String[] kodeMKSatMan = new String[1];
			String[] namaMKSatMan = new String[1];
			String[] sksMKSatMan = new String[1];
			String[] semesterMKSatMan = new String[1];
			String[] sifatMKSatMan = new String[1];
			String[] statusMKSatMan = new String[1];
			
			if(i<listMKAlihjenjang.size())
			{
				idMKAlihjenjang[0] = listMKAlihjenjang.get(i).getIdMKAlihjenjang().toString();
				kodeMKAlihjenjang[0] = listMKAlihjenjang.get(i).getKodeMKAlihjenjang();
				namaMKAlihjenjang[0] = listMKAlihjenjang.get(i).getNmMKAlihjenjang();
				sksMKAlihjenjang[0] = listMKAlihjenjang.get(i).getJumlahSKS().toString();
				if(hashMapListKrsCalonPD.containsKey(listMKAlihjenjang.get(i).getIdMKAlihjenjang().toString()))
				{
					if(hashMapListKrsCalonPD.get(listMKAlihjenjang.get(i).getIdMKAlihjenjang().toString()))
						statusMKAlihjenjang[0] = "Lulus";
					else statusMKAlihjenjang[0] = "Tidak Lulus";
				}
					
				else statusMKAlihjenjang[0] = "Tidak Ambil";
			}
			
			if(i<listMK.size())
			{
				idMKSatMan[0] = listMK.get(i).getIdMK().toString();
				kodeMKSatMan[0] = listMK.get(i).getKodeMK().toString();
				namaMKSatMan[0] = listMK.get(i).getNamaMK();
				sksMKSatMan[0] = listMK.get(i).getJumlahSKS().toString();
				if(listMK.get(i).getSifatMK())
				{
					sifatMKSatMan[0] = "Wajib";
					statusMKSatMan[0] = "Ambil";
				}
				else
				{
					sifatMKSatMan[0] = "Pilihan";
					statusMKSatMan[0] = "Bebas";
				}
				if(hashMapListMKWajibPD.containsKey(listMK.get(i).getIdMK().toString()))
				{
					if(hashMapListMKWajibPD.get(listMK.get(i).getIdMK().toString()))
						statusMKSatMan[0] = "Ambil";
					else statusMKSatMan[0] = "Bebas";
				}
				else if(hashMapListMKTerakui.containsKey(listMK.get(i).getIdMK().toString()))
					statusMKSatMan[0] = "Bebas"; 
			}
			
			EkuivalenMKAlihjenjang ekuivalenMKAlihjenjang = new EkuivalenMKAlihjenjang();
			ekuivalenMKAlihjenjang.setIdMKAlihjenjang(idMKAlihjenjang);
			ekuivalenMKAlihjenjang.setKodeMKAlihjenjang(kodeMKAlihjenjang);
			ekuivalenMKAlihjenjang.setNamaMKAlihjenjang(namaMKAlihjenjang);
			ekuivalenMKAlihjenjang.setSksMKAlihjenjang(sksMKAlihjenjang);
			ekuivalenMKAlihjenjang.setRelasiMKAlihjenjang("null");
			ekuivalenMKAlihjenjang.setStatusMKAlihjenjang(statusMKAlihjenjang);
			
			ekuivalenMKAlihjenjang.setIdMKSatMan(idMKSatMan);
			ekuivalenMKAlihjenjang.setKodeMKSatMan(kodeMKSatMan);
			ekuivalenMKAlihjenjang.setNamaMKSatMan(namaMKSatMan);
			ekuivalenMKAlihjenjang.setSksMKSatMan(sksMKSatMan);
			ekuivalenMKAlihjenjang.setSemesterMKSatMan(semesterMKSatMan);
			ekuivalenMKAlihjenjang.setSifatMKSatMan(sifatMKSatMan);
			ekuivalenMKAlihjenjang.setRelasiMKSatMan("null");
			ekuivalenMKAlihjenjang.setStatusMKSatMan(statusMKSatMan);
			listEkuivalenMKAlihjenjang.add(ekuivalenMKAlihjenjang);
		}
		
		response.setData(listEkuivalenMKAlihjenjang);
		
		return response;
	}

	@Secured(value = { "ROLE_Tim Ekuivalensi" })
	@RequestMapping(value = "savemkwajib", method = RequestMethod.POST, produces="application/json")
    public @ResponseBody AjaxResponse savemkwajib(HttpSession session, @RequestBody MKWajibAlihJenjang mkWajibAlihJenjang) {
		
		AjaxResponse response = new AjaxResponse();
		CalonPD calonPD = calonPDService.getById(UUID.fromString(mkWajibAlihJenjang.getIdCalonPD()));
		if(calonPD == null) return new AjaxResponse("error","Data tidak ditemukan",null);
		List<MKWajibCalonPD> listMKWajibCalonPD = mkWajibCalonPDService.get("calonPD.idCalonPD = '"+calonPD.getIdCalonPD().toString()+"' AND kurikulum.idKurikulum = '"+mkWajibAlihJenjang.getIdKurikulum()+"'");
		for (MKWajibCalonPD mkWajibCalonPD : listMKWajibCalonPD) {
			mkWajibCalonPDService.delete(mkWajibCalonPD.getIdMKWajibCalonPD());
		}
		for(int i=0;i<mkWajibAlihJenjang.getIdMK().length;i++) {
			MKWajibCalonPD mkWajibCalonPD = new MKWajibCalonPD();
			if(mkWajibCalonPDService.get("mk.idMK = '"+mkWajibAlihJenjang.getIdMK()[i]+"' AND calonPD.idCalonPD = '"+mkWajibAlihJenjang.getIdCalonPD()+"'").size() < 1)
			{
				mkWajibCalonPD.setCalonPD(calonPD);
				mkWajibCalonPD.setMk(mkService.getById(UUID.fromString(mkWajibAlihJenjang.getIdMK()[i])));
				if(mkWajibAlihJenjang.getStatus()[i].compareToIgnoreCase("true") == 0)
				{
					mkWajibCalonPD.setaStatusAmbil(true);
				}				
				else mkWajibCalonPD.setaStatusAmbil(false);
				mkWajibCalonPDService.save(mkWajibCalonPD);
			}
		}
		response.setStatus("ok");
		response.setData(mkWajibAlihJenjang);
		return response;
	}

	@Secured(value = { "ROLE_Tim Ekuivalensi" })
	@RequestMapping(value = "/simpanpermanen2", method = RequestMethod.POST)
	public @ResponseBody AjaxResponse simpanpermanen2(HttpSession session, @RequestBody MKWajibAlihJenjang mkWajibAlihJenjang) {
		
		AjaxResponse response = new AjaxResponse();
		CalonPD calonPD = calonPDService.getById(UUID.fromString(mkWajibAlihJenjang.getIdCalonPD()));
		if(calonPD == null) return new AjaxResponse("error","Data tidak ditemukan",null);
		List<MKWajibCalonPD> listMKWajibCalonPD = mkWajibCalonPDService.get("calonPD.idCalonPD = '"+calonPD.getIdCalonPD().toString()+"'");
		for (MKWajibCalonPD mkWajibCalonPD : listMKWajibCalonPD) {
			mkWajibCalonPDService.delete(mkWajibCalonPD.getIdMKWajibCalonPD());
		}
		for(int i=0;i<mkWajibAlihJenjang.getIdMK().length;i++) {
			MKWajibCalonPD mkWajibCalonPD = new MKWajibCalonPD();
			mkWajibCalonPD.setCalonPD(calonPD);
			mkWajibCalonPD.setMk(mkService.getById(UUID.fromString(mkWajibAlihJenjang.getIdMK()[i])));
			if(mkWajibAlihJenjang.getStatus()[i].compareToIgnoreCase("true") == 0)
			{
				mkWajibCalonPD.setaStatusAmbil(true);
			}				
			else mkWajibCalonPD.setaStatusAmbil(false);
			mkWajibCalonPDService.save(mkWajibCalonPD);
		}
		calonPD.setaEkuivalensi(true);
		response.setData(calonPDService.save(calonPD));
		response.setMessage("ok");
		return response;
	}

	@Secured(value = { "ROLE_Tim Ekuivalensi" })
	@RequestMapping(value = "/simpanpermanen", method = RequestMethod.POST)
	public @ResponseBody AjaxResponse simpanpermanen(HttpSession session, @RequestParam UUID idCalonPD) {
		AjaxResponse response = new AjaxResponse();
		CalonPD calonPD = calonPDService.getById(idCalonPD);
		if(calonPD == null) return new AjaxResponse("error","Data tidak ditemukan",null);
		calonPD.setaEkuivalensi(true);
		response.setData(calonPDService.save(calonPD));
		response.setMessage("ok");
		return response;
	}

	@Secured(value = { "ROLE_Tim Ekuivalensi" })
	@RequestMapping(value = "/tambahmkluar", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse tambahmkluar(HttpSession session, @RequestParam("idCalonPD") UUID idCalonPD, 
    		@RequestParam("idMKLuar") UUID idMKLuar, @RequestParam("idKonversi") UUID idKonversi,
    		@RequestParam("aLulus") boolean aLulus) {
		
		AjaxResponse response = new AjaxResponse();
		CalonPD calonPD = calonPDService.getById(idCalonPD);
		MKAlihjenjang mkAlihjenjang = mkAlihjenjangService.getById(idMKLuar);
		KonversiNilai konversiNilai = konversiNilaiService.getById(idKonversi);
		if(calonPD == null) return new AjaxResponse("error","Data tidak ditemukan",null);
		if(mkAlihjenjang == null) return new AjaxResponse("error","Data tidak ditemukan",null);
		if(konversiNilai == null) return new AjaxResponse("error","Data tidak ditemukan",null);
		KrsCalonPD krsCalonPD = new KrsCalonPD();
		krsCalonPD.setCalonPD(calonPD);
		krsCalonPD.setMkAlihjenjang(mkAlihjenjang);
		krsCalonPD.setKonversiNilai(konversiNilai);
		krsCalonPD.setaLulus(aLulus);
		response.setMessage("ok");
		response.setData(krsCalonPDService.save(krsCalonPD));		
        return response;
    }

	@Secured(value = { "ROLE_Tim Ekuivalensi" })
	@RequestMapping(value = "/cekkrs", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse cekkrs(HttpSession session, @RequestParam("idCalonPD") UUID idCalonPD) {
			
		AjaxResponse response = new AjaxResponse();
		CalonPD calonPD = calonPDService.getById(idCalonPD);
		if(calonPD == null) response = new AjaxResponse("error","Data tidak ditemukan",null);
		List<KrsCalonPD> listKrsCalonPD = krsCalonPDService.get("calonPD.idCalonPD = '"+idCalonPD.toString()+"'");
		List<AlihJenjangMKTerakui> listMKTerakui = alihJenjangMKTerakuiService.get("calonPD.idCalonPD = '"+idCalonPD.toString()+"'");
		if(listKrsCalonPD.size() == 0 && listMKTerakui.size() == 0)
		{
			response.setData("true");
		}
		else response.setData("false");
		response.setMessage("ok");
			
        return response;
    }

	@Secured(value = { "ROLE_Tim Ekuivalensi" })
	@RequestMapping(value = "/tambahmk", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse tambahmk(HttpSession session, @RequestParam("idCalonPD") UUID idCalonPD, 
    		@RequestParam("idMK") UUID idMK) {
		AjaxResponse response = new AjaxResponse();
		CalonPD calonPD = calonPDService.getById(idCalonPD);
		MK mk = mkService.getById(idMK);
		if(calonPD == null) response = new AjaxResponse("error","Data tidak ditemukan",null);
		if(mk == null) response = new AjaxResponse("error","Data tidak ditemukan",null);
		AlihJenjangMKTerakui alihJenjangMKTerakui = new AlihJenjangMKTerakui();
		alihJenjangMKTerakui.setCalonPD(calonPD);
		alihJenjangMKTerakui.setMk(mk);
		response.setMessage("ok");
		response.setData(alihJenjangMKTerakuiService.save(alihJenjangMKTerakui));		
        return response;
    }

	@Secured(value = { "ROLE_Tim Ekuivalensi" })
	@RequestMapping(value = "/deletemanykrs", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse deletemanykrs(HttpSession session, @RequestParam("idKrsCalonPD[]") UUID[] idKrsCalonPD) {
		AjaxResponse response;
		for (UUID uuid : idKrsCalonPD) {
			krsCalonPDService.delete(uuid);
		}
		response = new AjaxResponse("ok","Data dihapus",null);
        return response;
    }

	@Secured(value = { "ROLE_Tim Ekuivalensi" })
	@RequestMapping(value = "/deletemanymkterakui", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse deletemanymkterakui(HttpSession session, @RequestParam("idAlihJenjangMKTerakui[]") UUID[] idAlihJenjangMKTerakui) {
		AjaxResponse response;
		for (UUID uuid : idAlihJenjangMKTerakui) {
			alihJenjangMKTerakuiService.delete(uuid);
		}
		response = new AjaxResponse("ok","Data dihapus",null);
        return response;
    }

	@Secured(value = { "ROLE_Tim Ekuivalensi" })
	@RequestMapping(value = "/bukaekuivalensi", method = RequestMethod.POST)
	public @ResponseBody AjaxResponse bukaekuivalensi(HttpSession session, @RequestParam("idCalonPD") UUID idCalonPD) {
		AjaxResponse response = new AjaxResponse();
		CalonPD calonPD = calonPDService.getById(idCalonPD);
		if(calonPD == null) return new AjaxResponse("error","Data tidak ditemukan",null);
		calonPD.setaEkuivalensi(false);
		response.setData(calonPDService.save(calonPD));
		response.setMessage("ok");
		return response;
	}

	@Secured(value = { "ROLE_Tim Ekuivalensi" })
	@RequestMapping(value = "/cekekuivalensi", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse cekekuivalensi(HttpSession session, @RequestParam("idCalonPD") UUID idCalonPD) {
		
		AjaxResponse response = new AjaxResponse();
		CalonPD calonPD = calonPDService.getById(idCalonPD);
		if(calonPD == null) response = new AjaxResponse("error","Data tidak ditemukan",null);
		KatalogAlihjenjang katalogAlihjenjang = katalogAlihjenjangService.getById(calonPD.getKatalogAlihjenjang().getIdKatalogAlihjenjang());
		if(katalogAlihjenjang == null) response = new AjaxResponse("error","Data tidak ditemukan",null);
		
		//Kosongi mk terakui
		List<AlihJenjangMKTerakui> resetMKTerakui = alihJenjangMKTerakuiService.get("calonPD.idCalonPD = '"+calonPD.getIdCalonPD().toString()+"'");
		for (AlihJenjangMKTerakui mkTerakui : resetMKTerakui) 
		{
			alihJenjangMKTerakuiService.delete(mkTerakui.getIdAlihJenjangMKTerakui());
		}
		
		List<KrsCalonPD> listKrsCalonPD = krsCalonPDService.get("calonPD.idCalonPD = '"+calonPD.getIdCalonPD().toString()+"'"
				+ " AND krsCalonPD.aLulus = true");
		HashMap<String, Boolean> hasMapKRS = new HashMap<String, Boolean>();
		for (KrsCalonPD krsCalonPD : listKrsCalonPD) {
			hasMapKRS.put(krsCalonPD.getMkAlihjenjang().getIdMKAlihjenjang().toString(), true);
		}
		List<RelasiMKAlihjenjang> listRelasiMK = relasiMKAlihjenjangService.get("katalog.idKatalogAlihjenjang ='"
		+katalogAlihjenjang.getIdKatalogAlihjenjang().toString()+"'");
		for (RelasiMKAlihjenjang relasiMKAlihjenjang : listRelasiMK) 
		{
			//Insert MK yang ekuivalen jika relasi terpenuhi
			if(CekRelasi(relasiMKAlihjenjang, idCalonPD, hasMapKRS))
			{
				List<AlihJenjangMKTerakui> listMKTerakui = alihJenjangMKTerakuiService.get("calonPD.idCalonPD = '"+calonPD.getIdCalonPD().toString()+"'");
				HashMap<String, Boolean> hasMapMKTerakui = new HashMap<String, Boolean>();
				for (AlihJenjangMKTerakui mkTerakui : listMKTerakui) 
				{
					hasMapMKTerakui.put(mkTerakui.getMk().getIdMK().toString(), true);
				}
				List<MK> listEkuivalensiMK = ekuivalensiMKAlihjenjangService.getMKDistinct(relasiMKAlihjenjang.getIdRelasiMKAlihjenjang());
				for (MK mk : listEkuivalensiMK) 
				{					
					//Insert jika MK belum terakui
					if(!hasMapMKTerakui.containsKey(mk.getIdMK().toString()))
					{
						AlihJenjangMKTerakui alihJenjangMKTerakui = new AlihJenjangMKTerakui();
						alihJenjangMKTerakui.setCalonPD(calonPD);
						alihJenjangMKTerakui.setMk(mk);
						alihJenjangMKTerakuiService.save(alihJenjangMKTerakui);
					}
				}
			}			
		}
		response.setMessage("ok");
        return response;
    }
	
	/*-----------------------------------------------Laporan Alihjenjang---------------------------------------------*/

	@Secured(value = { "ROLE_Admin" })
	@RequestMapping(value = "/laporan", method = RequestMethod.GET)
	public ModelAndView laporan(HttpSession session, Locale locale, Model model) {
		ModelAndView mav = new ModelAndView();
		
		String filter = "";
		//add filter satman
		List<SatMan> listSatManProdi = new ArrayList<SatMan>();
		PeranPengguna peranPengguna = (PeranPengguna) session.getAttribute("userRoleSession");
		if(peranPengguna.getPeran().getNamaPeran().compareToIgnoreCase("admin") != 0)
		{
			List<Kurikulum> listKurikulum = kurikulumService.get("satMan.idSatMan = '"+peranPengguna.getSatMan().getIdSatMan()+"' AND kurikulum.statusKurikulum = false");
			
			for(int i=0;i<listKurikulum.size();i++)
			{
				filter += "kurikulum.idKurikulum = '"+listKurikulum.get(i).getIdKurikulum()+"'";
				if(i+1<listKurikulum.size())
					filter += " OR ";
			}
			
			listSatManProdi = satManMKService.getSatManDistinct(filter);
			
			if(listSatManProdi.size() < 1)
				listSatManProdi = satManService.get("satMan.idSatMan = '"+peranPengguna.getSatMan().getIdSatMan()+"'");
		}
		else
		{
			listSatManProdi = satManService.listChild(peranPengguna.getSatMan().getIdSatMan());
		}
		
		mav.addObject("listSatManProdi", listSatManProdi);
		mav.setViewName("calonpd/laporan");
		
		mav.addObject ("menuActive","Laporan Alihjenjang"); 
		
		return mav;
	}

	@Secured(value = { "ROLE_Admin" })
	@RequestMapping(value = "/laporan/jsonpd", method = RequestMethod.POST)
	public @ResponseBody Datatable laporanjsonpd(HttpSession session,
			@RequestParam("sEcho") String sEcho, 
			@RequestParam("iDisplayLength") int iDisplayLength,
            @RequestParam("iSortCol_0") int iSortCol_0,
            @RequestParam("sSortDir_0") String sSortDir_0,
            @RequestParam("sSearch") String sSearch,
			@RequestParam("iDisplayStart") int iDisplayStart,
			@RequestParam("idSatMan") UUID idSatMan
            ) {
		
		Datatable relasiDatatable = new Datatable();
		String filter = "";
		filter += "calonPD.aEkuivalensi = true";
		//add filter satman by session
		if(idSatMan != null)
		{
			filter += " AND satMan.idSatMan = '"+idSatMan.toString()+"'";
		}
		//calonpd yang sudah simpan permanen		
		
		relasiDatatable = calonPDService.getdatatable(sEcho, iDisplayLength, iDisplayStart, iSortCol_0, sSortDir_0, sSearch,filter);
		return relasiDatatable;
	}

	@Secured(value = { "ROLE_Admin" })
	@RequestMapping(value = "/laporan/getmkbycalonpd", method = RequestMethod.POST)
	public @ResponseBody AjaxResponse getmkbycalonpd(HttpSession session, @RequestParam("idCalonPD") UUID idCalonPD) {
		
		AjaxResponse response = new AjaxResponse();
		if(idCalonPD == null) return new AjaxResponse("error","Data tidak ditemukan",null);
		CalonPD calonPD = calonPDService.getById(idCalonPD);
		if(calonPD == null) return new AjaxResponse("error","Data tidak ditemukan",null);
		//add filter sudah terekuivalensi
		if(!calonPD.isaEkuivalensi()) return new AjaxResponse("ok","Data tidak ditemukan",null);
		MKWajibAlihJenjang mkWajib = new MKWajibAlihJenjang();
		String filter = "calonPD.idCalonPD = '"+idCalonPD.toString()+"'";
		
		List<MKWajibCalonPD> listMKWajibCalonPD = mkWajibCalonPDService.get(filter);
		
		if(listMKWajibCalonPD.size() < 1)
		{
			return new AjaxResponse("ok","Data tidak ditemukan",null);
		}
		String[] idMK = new String[listMKWajibCalonPD.size()];
		String[] kodeMK = new String[listMKWajibCalonPD.size()];
		String[] namaMK = new String[listMKWajibCalonPD.size()];
		String[] jumlahSKS = new String[listMKWajibCalonPD.size()];
		String[] statusMK = new String[listMKWajibCalonPD.size()];
		/*
		 * 1. Diakui
		 * 2. Wajib
		 * 3. Pilihan
		 * 4. Bebas
		 */
		for(int i=0;i<listMKWajibCalonPD.size();i++)
		{
			idMK[i] = listMKWajibCalonPD.get(i).getMk().getIdMK().toString();
			kodeMK[i] = listMKWajibCalonPD.get(i).getMk().getKodeMK();
			namaMK[i] = listMKWajibCalonPD.get(i).getMk().getNamaMK();
			jumlahSKS[i] = listMKWajibCalonPD.get(i).getMk().getJumlahSKS().toString();
			
			
			if(listMKWajibCalonPD.get(i).getMk().getSifatMK())
			{
				if(listMKWajibCalonPD.get(i).isaStatusAmbil())
					statusMK[i] = "2;true";
				else statusMK[i] = "4;true";
			}				
			else
				statusMK[i] = "3;false";
		}
		mkWajib.setIdMK(idMK);
		mkWajib.setJumlahSKS(jumlahSKS);
		mkWajib.setKodeMK(kodeMK);
		mkWajib.setNamaMK(namaMK);
		mkWajib.setStatus(statusMK);
		
		response.setMessage("ok");
		response.setData(mkWajib);
		return response;
	}

	@Secured(value = { "ROLE_Admin" })
	@RequestMapping(value = "/cetak/{idCalonPD}", method = RequestMethod.GET)
    public ModelAndView generatePdfReport(ModelAndView modelAndView,
    		@PathVariable("idCalonPD") UUID idCalonPD,
    		HttpSession session){
 
		
        Map<String,Object> parameterMap = new HashMap<String,Object>();
        /*Memuat peserta didik*/
        CalonPD calonPD = null;
        if(idCalonPD != null)
        	calonPD = calonPDService.getById(idCalonPD);
        else { modelAndView.setViewName("redirect:/");return modelAndView;}
        
        if(!calonPD.isaEkuivalensi())
        	{ modelAndView.setViewName("redirect:/");return modelAndView;}
        
        
        List<MKWajibCalonPD> listMKWajibCalonPD = mkWajibCalonPDService.get("calonPD.idCalonPD = '"+idCalonPD.toString()+"'", "nm_mk ASC");
		HashMap<UUID, Boolean> hashMapMKWajibCalonPD = new HashMap<UUID, Boolean>(); 
		for (MKWajibCalonPD mkWajibPd : listMKWajibCalonPD) {
			hashMapMKWajibCalonPD.put(mkWajibPd.getMk().getIdMK(), mkWajibPd.isaStatusAmbil());
		}
		
		List<AlihJenjangMKTerakui> listAlihJenjangMKTerakui = alihJenjangMKTerakuiService.get("calonPD.idCalonPD = '"+idCalonPD.toString()+"'");
		HashMap<UUID, Boolean> hashMapAlihJenjangMKTerakui = new HashMap<UUID, Boolean>(); 
		for (AlihJenjangMKTerakui alihJenjangMKTerakui : listAlihJenjangMKTerakui) {
			hashMapAlihJenjangMKTerakui.put(alihJenjangMKTerakui.getMk().getIdMK(), true);
		}
		
		List<Kurikulum> listKurikulum = satManMKService.getKurikulumDistinct("satMan.idSatMan = '"+calonPD.getSatMan().getIdSatMan()+"'");
		String filter = "";
		
		for(int i=0;i<listKurikulum.size();i++)
		{
			filter += "kurikulum.idKurikulum = '"+listKurikulum.get(i).getIdKurikulum()+"'";
			if(i+1 <listKurikulum.size())
				filter += " OR ";
		}		
		if(listKurikulum.size()>0)
			filter = "("+filter+")";
		System.out.print(filter);
		
		List<MKBaruPdf> listMKBaruPdf = new ArrayList<MKBaruPdf>();
		int sksDiakui = 0;
		for (MKWajibCalonPD mkWajib : listMKWajibCalonPD) {			
			MKBaruPdf mkBaruPdf = new MKBaruPdf();
			mkBaruPdf.setKodeMKBaru(mkWajib.getMk().getKodeMK());
			mkBaruPdf.setNamaMKBaru(mkWajib.getMk().getNamaMK());
			mkBaruPdf.setSksMKBaru(mkWajib.getMk().getJumlahSKS().toString());
			if(mkWajib.getMk().getSifatMK())
				mkBaruPdf.setSifatMKBaru("Wajib");
			else mkBaruPdf.setSifatMKBaru("Pilihan");
			if(mkWajib.isaStatusAmbil())
				mkBaruPdf.setStatusMKBaru("Wajib");
			else
			{
				
				if(mkWajib.getMk().getSifatMK())
					mkBaruPdf.setStatusMKBaru("Bebas");
				else mkBaruPdf.setStatusMKBaru("Pilihan");
			}
			listMKBaruPdf.add(mkBaruPdf);			
		}
		
//		JRDataSource dsKodeMKLama = new JRBeanCollectionDataSource(listMKLamaPdf);
		JRDataSource dsKodeMKBaru = new JRBeanCollectionDataSource(listMKBaruPdf);
		final org.joda.time.format.DateTimeFormatter dtf = DateTimeFormat.forPattern("dd-MM-yyyy");
		String date = calonPD.getTglLahir().getDayOfMonth()+"-"+calonPD.getTglLahir().getMonthOfYear()+"-"+calonPD.getTglLahir().getYear();
		DateTime dd = dtf.parseDateTime(date);
		final LocalDate dt = dd.toLocalDate();
		System.out.print(date);
		System.out.print(dt);
		calonPD.setTglLahir(dt);
		System.out.print(calonPD.getTglLahir());
		System.out.print("sdsda-"+listMKBaruPdf.size());
		parameterMap.put("calonPD", calonPD);
//		parameterMap.put("ptk", ekuivalensiPd.getPtk());
//		parameterMap.put("kurikulum", ekuivalensiPd.getKurikulumBaru());
        parameterMap.put("datasource", dsKodeMKBaru);

        modelAndView = new ModelAndView("pdfEkuivalensiAlihjenjang", parameterMap);
 
        return modelAndView;
 
    }//generatePdfReport
	
	
	private boolean CekRelasi(RelasiMKAlihjenjang relasiMKAlihjenjang, UUID idCalonPD, HashMap<String, Boolean> hasMapKRS)
	{
		String relasi = relasiMKAlihjenjang.getRelasiMKAlihjenjang();
		boolean status = true;
		if(relasi.length() > 2)
		{
			HashMap<Character, String> simbolMK = new HashMap<Character, String>();		
			relasi = createpostfix(relasi);
			String[] temp = relasiMKAlihjenjang.getDetailRelasiMKAlihjenjang().split(";");
			for (String string : temp) {
				String[] tmp = string.split("=");
				simbolMK.put(tmp[0].charAt(0), tmp[1]);
			}
			Stack<Boolean> hasil = new Stack<Boolean>();
			for(int i=0;i<relasi.length();i++)
			{
				if(Character.isLetter(relasi.charAt(i)))
				{
					if(hasMapKRS.containsKey(simbolMK.get(relasi.charAt(i))))
					{
						if(hasMapKRS.get(simbolMK.get(relasi.charAt(i))))
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
			List<MKAlihjenjang> listMKAlihjenjang = ekuivalensiMKAlihjenjangService.getMKAlihjenjangDistinct(relasiMKAlihjenjang.getIdRelasiMKAlihjenjang());
			if(listMKAlihjenjang == null) return false;
			if(relasi.compareToIgnoreCase("-") == 0)
			{
				String temp = listMKAlihjenjang.get(0).getIdMKAlihjenjang().toString();
				if(hasMapKRS.containsKey(temp))
				{
					if(hasMapKRS.get(temp))
						return true;
					else return false;
				}
				else return false;
			}
			else
			{
				status = true;
				if(relasi.compareToIgnoreCase("^") == 0)
				{
					for (MKAlihjenjang mkAlihjenjang : listMKAlihjenjang) 
					{
						if(!hasMapKRS.containsKey(mkAlihjenjang.getIdMKAlihjenjang().toString()))
							status = false;
						else
						{
							if(!hasMapKRS.get(mkAlihjenjang.getIdMKAlihjenjang().toString()))
								status = false;
						}
							
					}
				}
				else if(relasi.compareToIgnoreCase("/") == 0)
				{
					status = false;
					for (MKAlihjenjang mkAlihjenjang : listMKAlihjenjang) 
					{
						if(hasMapKRS.containsKey(mkAlihjenjang.getIdMKAlihjenjang().toString()))
						{
							if(hasMapKRS.get(mkAlihjenjang.getIdMKAlihjenjang().toString()))
								status = true;
						}
					}
				}
			}
			return status;
		}
	}
	
	private boolean CekRelasiKanan(RelasiMKAlihjenjang relasiMKAlihjenjang, UUID idCalonPD, HashMap<String, Boolean> hasMapKRS)
	{
		String relasi = relasiMKAlihjenjang.getRelasiMK();
		boolean status = true;
		if(relasi.length() > 2)
		{
			HashMap<Character, String> simbolMK = new HashMap<Character, String>();		
			relasi = createpostfix(relasi);
			String[] temp = relasiMKAlihjenjang.getDetailRelasiMK().split(";");
			for (String string : temp) {
				String[] tmp = string.split("=");
				simbolMK.put(tmp[0].charAt(0), tmp[1]);
			}
			Stack<Boolean> hasil = new Stack<Boolean>();
			for(int i=0;i<relasi.length();i++)
			{
				if(Character.isLetter(relasi.charAt(i)))
				{
					if(hasMapKRS.containsKey(simbolMK.get(relasi.charAt(i))))
					{
						if(hasMapKRS.get(simbolMK.get(relasi.charAt(i))))
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
			List<MK> listMK = ekuivalensiMKAlihjenjangService.getMKDistinct(relasiMKAlihjenjang.getIdRelasiMKAlihjenjang());
			if(listMK == null) return false;
			if(relasi.compareToIgnoreCase("-") == 0)
			{
				
				String temp = listMK.get(0).getIdMK().toString();
				System.out.print("-"+temp);
				if(hasMapKRS.containsKey(temp))
				{
					if(hasMapKRS.get(temp))
					return true;
					else return false;
				}
					
				else return false;
			}
			else
			{
				status = true;
				if(relasi.compareToIgnoreCase("^") == 0)
				{
					for (MK mk : listMK) 
					{
						if(!hasMapKRS.containsKey(mk.getIdMK().toString()))
							status = false;
						else
						{
							if(!hasMapKRS.get(mk.getIdMK().toString()))
								status = false;
						}
					}
				}
				else if(relasi.compareToIgnoreCase("/") == 0)
				{
					status = false;
					for (MK mk : listMK) 
					{
						if(hasMapKRS.containsKey(mk.getIdMK().toString()))
						{
							if(hasMapKRS.get(mk.getIdMK().toString()))
								return true;							
						}
						
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
