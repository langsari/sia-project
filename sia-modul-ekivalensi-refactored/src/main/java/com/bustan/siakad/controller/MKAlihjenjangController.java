package com.bustan.siakad.controller;

import java.io.Console;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bustan.siakad.service.Datatable;
import com.bustan.siakad.service.AjaxResponse;
import com.bustan.siakad.service.MKAlihjenjangService;
import com.bustan.siakad.service.KatalogAlihjenjangService;
import com.bustan.siakad.service.SatManService;
import com.sia.modul.domain.MKAlihjenjang;
import com.sia.main.domain.PeranPengguna;
import com.sia.modul.domain.KatalogAlihjenjang;
import com.sia.modul.domain.SatMan;

@Controller
@Secured(value = { "ROLE_Admin" })
@RequestMapping(value = "/mkalihjenjang")
public class MKAlihjenjangController {
	
	@Autowired
	KatalogAlihjenjangService katalogAlihjenjangService;
	
	@Autowired
	MKAlihjenjangService mkAlihjenjangService;
	
	@Autowired
	SatManService satManService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView datatable(HttpSession session, Locale locale, Model model) {
		ModelAndView mav = new ModelAndView();
		
		MKAlihjenjang mkAlihjenjang = new MKAlihjenjang();
		List<KatalogAlihjenjang> listKatalogAlihjenjang = katalogAlihjenjangService.get("katalog.aTerhapus = false");
		
		mav.addObject("mkAlihjenjang",mkAlihjenjang);
		mav.addObject("listKatalogAlihjenjang",listKatalogAlihjenjang);
		mav.setViewName("mkalihjenjang/home");
		
		mav.addObject ("menuActive","Manajemen Matakuliah Alihjenjang"); 
		
		return mav;
	}
	
	@RequestMapping(value = "/json", method = RequestMethod.POST)
	public @ResponseBody Datatable json(HttpSession session,
			@RequestParam("sEcho") String sEcho, 
			@RequestParam("iDisplayLength") int iDisplayLength,
            @RequestParam("iSortCol_0") int iSortCol_0,
            @RequestParam("sSortDir_0") String sSortDir_0,
            @RequestParam("sSearch") String sSearch,
			@RequestParam("iDisplayStart") int iDisplayStart,
			@RequestParam("idKatalogAlihjenjang") UUID idKatalogAlihjenjang
            ) {
		
		String filter = "mkAlihjenjang.aMKAlihjenjangTerhapus = false";
		
		if(idKatalogAlihjenjang != null)
		{
			filter += " AND katalog.idKatalogAlihjenjang = '"+idKatalogAlihjenjang+"'";
			PeranPengguna peranPengguna = (PeranPengguna) session.getAttribute("userRoleSession");
//			List<SatMan> listSatManProdi = new ArrayList<SatMan>();
			if(peranPengguna.getPeran().getNamaPeran().compareToIgnoreCase("admin") != 0)
			{
//				listSatManProdi = satManService.listChild(peranPengguna.getSatMan().getIdSatMan());
//				if(listSatManProdi.size() < 1)
//					listSatManProdi = satManService.get("satMan.idSatMan = '"+peranPengguna.getSatMan().getIdSatMan()+"'");
			}
			else
			{
//				List<SatMan> satManInduk = satManService.get("idSatManInduk = null");
//				listSatManProdi = satManService.listChild(satManInduk.get(0).getIdSatMan());
			}
			
//			for(int i=0;i<listSatManProdi.size();i++)
//			{
//				filter += "satMan.idSatMan = '"+listSatManProdi.get(i).getIdSatMan().toString()+"'";
//				if(i+1 < listSatManProdi.size())
//					filter += " OR ";
//			}
		}
//		else
			
		
		Datatable mkAlihjenjangDatatable = mkAlihjenjangService.getdatatable(sEcho, iDisplayLength, iDisplayStart, iSortCol_0, sSortDir_0, sSearch,filter);
		return mkAlihjenjangDatatable;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse edit(HttpSession session, @RequestParam("idMKAlihjenjang") UUID idMKAlihjenjang) {
		
		AjaxResponse response;
		MKAlihjenjang mkAlihjenjang = mkAlihjenjangService.getById(idMKAlihjenjang);
		if(mkAlihjenjang == null) response = new AjaxResponse("error","Data tidak ditemukan",null);
		else response = new AjaxResponse("ok","Data ditemukan",mkAlihjenjang);
        return response;
    }
	
	@RequestMapping(value = "/simpan", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse simpan(HttpSession session, @Valid @ModelAttribute("mkAlihjenjang") MKAlihjenjang mkAlihjenjang,
            BindingResult result, Map<String, Object> model,
            @RequestParam("idKatalogAlihjenjang") UUID idKatalogAlihjenjang){
		AjaxResponse response = new AjaxResponse();
		if(idKatalogAlihjenjang!=null)mkAlihjenjang.setKatalogAlihjenjang(katalogAlihjenjangService.getById(idKatalogAlihjenjang));
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
        response.setData(mkAlihjenjangService.save(mkAlihjenjang));
        if(response.getData()!=null) response.setMessage("Data berhasil disimpan");
        else 
        {
        	response.setStatus("error");
        	response.setMessage("");
        }
        return response;
    }
	
	@RequestMapping(value = "/deletemany", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse deleteMany(HttpSession session, @RequestParam("idMKAlihjenjang[]") UUID[] idMKAlihjenjang) {
		
		AjaxResponse response;
		for (UUID uuid : idMKAlihjenjang) {
			mkAlihjenjangService.delete(uuid);
		}
		response = new AjaxResponse("ok","Data dihapus",null);
        return response;
    }
}
