package com.AIS.Modul.MataKuliah.Controller;

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

import com.AIS.Modul.MataKuliah.Service.AjaxResponse;
import com.AIS.Modul.MataKuliah.Service.Datatable;
import com.AIS.Modul.MataKuliah.Service.PustakaService;
import com.sia.modul.domain.Pustaka;
import com.sia.modul.domain.RumpunMK;

@Controller
@Secured(value = { "ROLE_Admin" })
@RequestMapping(value = "/silabus/pustaka")
public class PustakaController {

	@Autowired
	private PustakaService pustakaServ;
	
	private static final Logger logger = LoggerFactory.getLogger(RumpunMKController.class);
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView datatable(Locale locale, Model model, HttpSession session) {
		Pustaka pustaka = new Pustaka(); 
		ModelAndView mav = new ModelAndView();
		mav.addObject("pustaka", pustaka);
		mav.setViewName("ViewPustaka"); 
		mav.addObject ("menuActive","Kelola Pustaka Mata Kuliah"); 
		return mav;
	}
	
	@RequestMapping(value = "/json", method = RequestMethod.POST)
	public @ResponseBody Datatable json(
			@RequestParam("sEcho") String sEcho, 
			@RequestParam("iDisplayLength") int iDisplayLength,
            @RequestParam("iSortCol_0") int iSortCol_0,
            @RequestParam("sSortDir_0") String sSortDir_0,
            @RequestParam("sSearch") String sSearch,
			@RequestParam("iDisplayStart") int iDisplayStart,
			@RequestParam("statusPustaka") String statusPustaka
            ) {
		String filter = "CAST( statusPustaka as string) LIKE '%"+statusPustaka+"%'";
		Datatable pustakaDatatable = pustakaServ.getdatatable(sEcho, iDisplayLength, iDisplayStart, iSortCol_0, sSortDir_0, sSearch,filter);
		return pustakaDatatable;
	}
	
	@RequestMapping(value = "/simpan", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse simpan(@Valid @ModelAttribute("pustaka") Pustaka pustaka,
    		@RequestParam("sifatPustaka") String sifatPustaka, BindingResult result, 
    		Map<String, Object> model) {
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
        if(sifatPustaka.contentEquals("U")){
        	pustaka.setSifatPustaka("U");  
        }
        else{
        	pustaka.setSifatPustaka("P"); 
        }
        
        response.setData(pustakaServ.save(pustaka));
        if(response.getData()!=null) response.setMessage("Data berhasil disimpan");
        else 
        {
        	response.setStatus("error");
        	response.setMessage("Pustaka mata kuliah sudah ada");
        }
        return response;
    }
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse edit(@RequestParam("idPustaka") UUID idPustaka) {
		AjaxResponse response;
		Pustaka pustaka = pustakaServ.findById(idPustaka);
		pustaka.setStatusPustaka((pustaka.isStatusPustaka())); 
		if(pustaka == null) response = new AjaxResponse("error","Data tidak ditemukan",null);
		else response = new AjaxResponse("ok","Data ditemukan",pustaka);
        return response;
    } 
	
	@RequestMapping(value = "/deletemany", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse deleteMany(@RequestParam("idPustaka[]") UUID[] idPustaka) {
		AjaxResponse response;
		for (UUID uuid : idPustaka) {
			pustakaServ.delete(uuid);
		}
		response = new AjaxResponse("ok","Data dihapus",null);
        return response;
    }
}
