package com.its.sia.controller;
import com.sia.modul.domain.*;
import com.its.sia.service.AjaxResponse;
import com.its.sia.service.Datatable;
import com.its.sia.service.ThnAjaranService;

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
@RequestMapping(value = "/tahunajaran")
public class ThnAjaranController {
	
	private static final Logger logger = LoggerFactory.getLogger(ThnAjaranController.class);
	
	@Autowired
	private ThnAjaranService thnAjaranService;
	
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView datatable(HttpSession session, Locale locale, Model model) {
		ThnAjaran thnAjaran = new ThnAjaran();
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("MasterThnAjaran");
		mav.addObject("thnAjaran", thnAjaran);
		mav.addObject ("menuActive","Kelola Tahun Ajaran");
				
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
			@RequestParam("iDisplayStart") int iDisplayStart,
			@RequestParam("a_thn_ajaran_terhapus") String a_thn_ajaran_terhapus
            ) {
		String filter = "CAST( aThnAjaranTerhapus as string) LIKE '%"+a_thn_ajaran_terhapus+"%'";
		Datatable thnAjaranDatatable = thnAjaranService.getdatatable(sEcho, iDisplayLength, iDisplayStart, iSortCol_0, sSortDir_0, sSearch,filter);
		return thnAjaranDatatable;
	}
	
	
	@RequestMapping(value = "/simpan", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse simpan(HttpSession session,@Valid @ModelAttribute("thnAjaran") ThnAjaran thnAjaran,
            BindingResult result, Map<String, Object> model) {
		
		AjaxResponse response = new AjaxResponse();

		logger.info(String.valueOf(thnAjaran.getThnThnAjaran()));
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
        response.setData(thnAjaranService.save(thnAjaran));
        if(response.getData()!=null) response.setMessage("Data berhasil disimpan");
        else 
        {
        	response.setStatus("error");
        	response.setMessage("Tahun ajaran sudah ada");
        }
        return response;
    }
	
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse edit(HttpSession session,@RequestParam("idThnAjaran") UUID idThnAjaran) {
		
		AjaxResponse response;
		ThnAjaran thnAjaran = thnAjaranService.getById(idThnAjaran);
		if(thnAjaran == null) response = new AjaxResponse("error","Data tidak ditemukan",null);
		else response = new AjaxResponse("ok","Data ditemukan",thnAjaran);
        return response;
    }
	
	
	@RequestMapping(value = "/deletemany", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse deleteMany(HttpSession session,@RequestParam("idThnAjaran[]") UUID[] idThnAjaran) {
		
		AjaxResponse response;
		for (UUID uuid : idThnAjaran) {
			thnAjaranService.delete(uuid);
		}
		response = new AjaxResponse("ok","Data dihapus",null);
        return response;
    }
	
}
