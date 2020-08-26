package com.its.sia.controller;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sia.modul.domain.Pd;
import com.sia.main.domain.PeranPengguna;
import com.sia.modul.domain.Ptk;
import com.its.sia.service.AjaxResponse;
import com.its.sia.service.Datatable;
import com.its.sia.service.PdService;
import com.its.sia.service.PtkService;;

@Controller
@Secured(value = { "ROLE_Tenaga Kependidikan" })
@RequestMapping(value = "/perwalian")
public class PerwalianController {

	private static final Logger logger = LoggerFactory.getLogger(PerwalianController.class);
	
	@Autowired
	private PtkService ptkService;
	
	@Autowired
	private PdService pdService;
	
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView perwalian(HttpSession session, Locale locale, Model model) {
		ModelAndView mav = new ModelAndView();
		
		List<Integer> listAngkatan = pdService.getAngkatan("","pd.angkatanPd desc");
		mav.setViewName("Perwalian");
		mav.addObject("listAngkatan", listAngkatan);
		mav.addObject ("menuActive","Kelola Pendamping Akademik");
		
		return mav;
	}
	
	
	@RequestMapping(value = "/jsonperwalian", method = RequestMethod.POST)
	public @ResponseBody Datatable jsonperwalian(
			HttpSession session,
			@RequestParam("sEcho") String sEcho, 
			@RequestParam("iDisplayLength") int iDisplayLength,
            @RequestParam("iSortCol_0") int iSortCol_0,
            @RequestParam("sSortDir_0") String sSortDir_0,
            @RequestParam("sSearch") String sSearch,
			@RequestParam("iDisplayStart") int iDisplayStart
            ) {
		
		PeranPengguna peranPengguna = (PeranPengguna)session.getAttribute("userRoleSession");
		
		String filter = "ptk.aPtkTerhapus = false AND ptk.statusPtk = true and satMan.idSatMan='"+peranPengguna.getSatMan().getIdSatMan()+"'";
		Datatable ptkDatatable = ptkService.getPerwalianDatatable(sEcho, iDisplayLength, iDisplayStart, iSortCol_0, sSortDir_0, sSearch,filter);
		return ptkDatatable;
	}
	
	
	@RequestMapping(value = "/jsonanakwali", method = RequestMethod.POST)
	public @ResponseBody Datatable jsonanakwali(
			HttpSession session,
			@RequestParam("sEcho") String sEcho, 
			@RequestParam("iDisplayLength") int iDisplayLength,
            @RequestParam("iSortCol_0") int iSortCol_0,
            @RequestParam("sSortDir_0") String sSortDir_0,
            @RequestParam("sSearch") String sSearch,
			@RequestParam("iDisplayStart") int iDisplayStart,
			@RequestParam("idPtk") UUID idPtk,
			@RequestParam("angkatan_pd") Integer angkatan_pd
            ) {
		
		PeranPengguna peranPengguna = (PeranPengguna)session.getAttribute("userRoleSession");
		
		String filter = "pd.aPdTerhapus = false and satMan.idSatMan='"+peranPengguna.getSatMan().getIdSatMan()+"'";
		if(angkatan_pd!=null) filter = "pd.angkatanPd = "+angkatan_pd.toString()+" AND "+filter;
		if(idPtk!=null) filter = "ptk.idPtk = '"+idPtk.toString()+"' AND "+filter;
		Datatable pdDatatable = pdService.getAnakWali(sEcho, iDisplayLength, iDisplayStart, iSortCol_0, sSortDir_0, sSearch,filter);
		return pdDatatable;
	}
	
	
	@RequestMapping(value = "/jsonnonwali", method = RequestMethod.POST)
	public @ResponseBody Datatable jsonnonwali(
			HttpSession session,
			@RequestParam("sEcho") String sEcho, 
			@RequestParam("iDisplayLength") int iDisplayLength,
            @RequestParam("iSortCol_0") int iSortCol_0,
            @RequestParam("sSortDir_0") String sSortDir_0,
            @RequestParam("sSearch") String sSearch,
			@RequestParam("iDisplayStart") int iDisplayStart,
			@RequestParam("angkatan_pd") Integer angkatan_pd
            ) {
		
		PeranPengguna peranPengguna = (PeranPengguna)session.getAttribute("userRoleSession");

		String filter = "ptk.idPtk = null and satMan.idSatMan='"+peranPengguna.getSatMan().getIdSatMan()+"'";
		if(angkatan_pd!=null) filter = "pd.angkatanPd = "+angkatan_pd.toString()+" AND "+filter;
		Datatable pdDatatable = pdService.getAnakWali(sEcho, iDisplayLength, iDisplayStart, iSortCol_0, sSortDir_0, sSearch,filter);
		return pdDatatable;
	}
	
	
	@RequestMapping(value = "/lepaskan", method = RequestMethod.POST)
	public @ResponseBody AjaxResponse lepaskan(
			HttpSession session,
			@RequestParam("idPd[]") UUID[] idPd, 
			@RequestParam("idPtk") UUID idPtk
            ) {
		
		for (UUID uuid : idPd) {
			Pd pd = pdService.getById(uuid);
			if(pd!=null)
			{
				if(pd.getPtk().getIdPtk().equals(idPtk))
				{
					pd.setPtk(null);
					pdService.save(pd);
				}
			}
		}
		return new AjaxResponse("ok","Pelepasan berhasil",null);
	}
	
	
	@RequestMapping(value = "/tambahkan", method = RequestMethod.POST)
	public @ResponseBody AjaxResponse tambahkan(
			HttpSession session,
			@RequestParam("idPd[]") UUID idPd[], 
			@RequestParam("idPtk") UUID idPtk
            ) {
		
		Ptk ptk = ptkService.getById(idPtk);
		if(ptk!=null)
		{
			for (UUID uuid : idPd) {
				Pd pd = pdService.getById(uuid);
				if(pd.getPtk()==null)
				{
					pd.setPtk(ptk);
					pdService.save(pd);
				}
			}
		}
		return new AjaxResponse("ok","Penambahan berhasil",null);
	}
	
}
