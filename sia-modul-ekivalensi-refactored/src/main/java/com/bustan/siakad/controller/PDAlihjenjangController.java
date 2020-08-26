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
import com.bustan.siakad.service.CalonPDService;
import com.sia.main.domain.PeranPengguna;
import com.sia.modul.domain.CalonPD;
import com.sia.modul.domain.MKAlihjenjang;
import com.sia.modul.domain.KatalogAlihjenjang;
import com.sia.modul.domain.SatMan;

@Controller
@RequestMapping(value = "/pdalihjenjang")
public class PDAlihjenjangController {
	
	@Autowired
	KatalogAlihjenjangService katalogAlihjenjangService;
	
	@Autowired
	MKAlihjenjangService mkAlihjenjangService;	
	
	@Autowired
	SatManService satManService;
	
	@Autowired
	CalonPDService calonPDService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView datatable(HttpSession session, Locale locale, Model model) {
		ModelAndView mav = new ModelAndView();
		
		List<KatalogAlihjenjang> listKatalog = katalogAlihjenjangService.get("katalog.aTerhapus = false");
		CalonPD calonPD = new CalonPD();
		
		PeranPengguna peranPengguna = (PeranPengguna) session.getAttribute("userRoleSession");
		List<SatMan> listSatMan = satManService.listChild(peranPengguna.getSatMan().getIdSatMan());		
		
		mav.addObject("calonPD",calonPD);
		mav.addObject("listSatMan",listSatMan);
		mav.addObject("listKatalog",listKatalog);
		mav.setViewName("pdalihjenjang/home");
		
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
			@RequestParam("idKatalog") UUID idKatalog,
			@RequestParam("idSatMan") UUID idSatMan
            ) {
		String filter = "";
		
		if(idKatalog != null)
		{
			filter += "katalog.idKatalogAlihjenjang = '"+idKatalog+"'";
		}		
		if(idSatMan != null)
		{
			if(filter != "") filter += " AND ";
			filter += "satMan.idSatMan = '"+idSatMan+"'";
		}		
		
		Datatable mkAlihjenjangDatatable = calonPDService.getdatatable(sEcho, iDisplayLength, iDisplayStart, iSortCol_0, sSortDir_0, sSearch,filter);
		return mkAlihjenjangDatatable;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse edit(HttpSession session, @RequestParam("idCalonPD") UUID idCalonPD) {
		
		AjaxResponse response;
		CalonPD calonPD = calonPDService.getById(idCalonPD);
		if(calonPD == null) response = new AjaxResponse("error","Data tidak ditemukan",null);
		else response = new AjaxResponse("ok","Data ditemukan",calonPD);
        return response;
    }
	
	@RequestMapping(value = "/simpan", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse simpan(HttpSession session, @Valid @ModelAttribute("calonPD") CalonPD calonPD,
            BindingResult result, Map<String, Object> model,
            @RequestParam("idSatMan") UUID idSatMan,
            @RequestParam("idKatalogAlihjenjang") UUID idKatalog){
		
		AjaxResponse response = new AjaxResponse();
		if(idKatalog!=null)calonPD.setKatalogAlihjenjang(katalogAlihjenjangService.getById(idKatalog));
		if(idSatMan!=null)calonPD.setSatMan(satManService.getById(idSatMan));
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
        response.setData(calonPDService.save(calonPD));
        if(response.getData()!=null) response.setMessage("Data berhasil disimpan");
        else 
        {
        	response.setStatus("error");
        	response.setMessage("");
        }
        return response;
    }
	
	@RequestMapping(value = "/deletemany", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse deleteMany(HttpSession session, @RequestParam("idCalonPD[]") UUID[] idCalonPD) {
		
		AjaxResponse response;
		for (UUID uuid : idCalonPD) {
			calonPDService.delete(uuid);
		}
		response = new AjaxResponse("ok","Data dihapus",null);
        return response;
    }
}
