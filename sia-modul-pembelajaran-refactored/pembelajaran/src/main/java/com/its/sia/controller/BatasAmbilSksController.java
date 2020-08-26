package com.its.sia.controller;
import com.sia.modul.domain.*;
import com.its.sia.service.AjaxResponse;
import com.its.sia.service.BatasAmbilSksService;
import com.its.sia.service.Datatable;
import com.its.sia.service.StsKehadiranService;

import java.io.Console;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.xml.crypto.dsig.keyinfo.KeyValue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
/**
 * Handles requests for the application home page.
 */
@Controller
@Secured(value = { "ROLE_Admin" })
@RequestMapping(value = "/batasambilsks")
public class BatasAmbilSksController {
	
	private static final Logger logger = LoggerFactory.getLogger(BatasAmbilSksController.class);
	
	@Autowired
	private BatasAmbilSksService batasAmbilSksService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView datatable(HttpSession session,Locale locale, Model model) {
		BatasAmbilSks batasAmbilSks = new BatasAmbilSks();
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("MasterBatasAmbilSks");
		mav.addObject("batasAmbilSks", batasAmbilSks);
		
		mav.addObject ("menuActive","Kelola Batas Pengambilan KRS");
		
		return mav;
	}
	
	
	@RequestMapping(value = "/json", method = RequestMethod.POST)
	public @ResponseBody Datatable json(
			HttpSession session,
			@RequestParam("sEcho") String sEcho, 
			@RequestParam("iDisplayLength") int iDisplayLength,
            @RequestParam("iSortCol_0") int iSortCol_0,
            @RequestParam("sSortDir_0") String sSortDir_0,
            @RequestParam("sSearch") String sSearch,
			@RequestParam("iDisplayStart") int iDisplayStart
		) {
	
		String filter = "";
		Datatable thnAjaranDatatable = batasAmbilSksService.getdatatable(sEcho, iDisplayLength, iDisplayStart, iSortCol_0, sSortDir_0, sSearch,filter);
		return thnAjaranDatatable;
	}
	
	
	@RequestMapping(value = "/simpan", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse simpan(HttpSession session,@Valid @ModelAttribute("batasAmbilSks") BatasAmbilSks batasAmbilSks,
            BindingResult result, Map<String, Object> model) {
		
		AjaxResponse response = new AjaxResponse();

        if (result.hasErrors()) {
        	response.setStatus("error");
        	List<FieldError> fieldError = result.getFieldErrors();
        	String message ="";
    		if(fieldError.get(0).isBindingFailure()) message += "Salah satu input tidak valid";
    		else message += fieldError.get(0).getDefaultMessage();
        	for(int i=1;i<fieldError.size();i++)
        	{
        		if(fieldError.get(i).isBindingFailure()) message += "<br/>Salah satu input tidak valid";
        		else message += "<br/>"+fieldError.get(i).getDefaultMessage();
        	}
        	response.setMessage(message);
        	response.setData(fieldError);
            return response;
        }
        response.setData(batasAmbilSksService.save(batasAmbilSks));
        if(response.getData()!=null) response.setMessage("Data berhasil disimpan");
        else 
        {
        	response.setStatus("error");
        	response.setMessage("Batas pengambilan SKS dengan batas bawah IPS tersebut sudah ada");
        }
        return response;
    }
	
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse edit(HttpSession session,@RequestParam("idBatasAmbilSks") UUID idBatasAmbilSks) {
		AjaxResponse response;
		BatasAmbilSks batasAmbilSks = batasAmbilSksService.getById(idBatasAmbilSks);
		if(batasAmbilSks == null) response = new AjaxResponse("error","Data tidak ditemukan",null);
		else response = new AjaxResponse("ok","Data ditemukan",batasAmbilSks);
        return response;
    }
	
	
	@RequestMapping(value = "/deletemany", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse deleteMany(HttpSession session,@RequestParam("idBatasAmbilSks[]") UUID[] idBatasAmbilSks) {
		
		AjaxResponse response;
		for (UUID uuid : idBatasAmbilSks) {
			batasAmbilSksService.delete(uuid);
		}
		response = new AjaxResponse("ok","Data dihapus",null);
        return response;
    }
	
}
