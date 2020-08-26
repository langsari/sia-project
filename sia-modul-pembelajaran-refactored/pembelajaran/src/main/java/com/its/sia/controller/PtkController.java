package com.its.sia.controller;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

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

import com.sia.modul.domain.Ptk;
import com.its.sia.service.AjaxResponse;
import com.its.sia.service.Datatable;
import com.its.sia.service.PdService;
import com.its.sia.service.PtkService;;

@Controller
@RequestMapping(value = "/ptk")
public class PtkController {

	private static final Logger logger = LoggerFactory.getLogger(PtkController.class);
	
	@Autowired
	private PtkService ptkService;
	
	@Autowired
	private PdService pdService;
	
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView datatable(Locale locale, Model model) {
		Ptk ptk = new Ptk();
		ModelAndView mav = new ModelAndView();
		mav.setViewName("MasterPtk");
		mav.addObject("ptk", ptk);
		
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
			@RequestParam("a_ptk_terhapus") String a_ptk_terhapus,
			@RequestParam("status_ptk") String status_ptk
            ) {
		String filter = "CAST( ptk.aPtkTerhapus as string) LIKE '%"+a_ptk_terhapus+"%' AND CAST( ptk.statusPtk as string) LIKE '%"+status_ptk+"%'";
		Datatable ptkDatatable = ptkService.getdatatable(sEcho, iDisplayLength, iDisplayStart, iSortCol_0, sSortDir_0, sSearch,filter);
		return ptkDatatable;
	}
	
	
	@RequestMapping(value = "/simpan", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse simpan(@Valid @ModelAttribute("smt") Ptk ptk,
            BindingResult result, Map<String, Object> model) {
		AjaxResponse response = new AjaxResponse();

        if (result.hasErrors()) {
        	response.setStatus("error");
        	List<FieldError> fieldError = result.getFieldErrors();
        	String message ="";
    		if(fieldError.get(0).isBindingFailure()) message += "Salah satu input tiak valid";
    		else message += fieldError.get(0).getDefaultMessage();
        	for(int i=1;i<fieldError.size();i++)
        	{
        		if(fieldError.get(i).isBindingFailure()) message += "Salah satu input tiak valid";
        		else message += "<br/>"+fieldError.get(i).getDefaultMessage();
        	}
        	response.setMessage(message);
        	response.setData(fieldError);
            return response;
        }
        response.setData(ptkService.save(ptk));
        if(response.getData()!=null) response.setMessage("Data berhasil disimpan");
        else 
        {
        	response.setStatus("error");
        	response.setMessage("NIP terpakai");
        }
        return response;
    }
	
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse edit(@RequestParam("idPtk") UUID idPtk) {
		AjaxResponse response;
		Ptk ptk = ptkService.getById(idPtk);
		if(ptk == null) response = new AjaxResponse("error","Data tidak ditemukan",null);
		else response = new AjaxResponse("ok","Data ditemukan",ptk);
        return response;
    }
	
	
	@RequestMapping(value = "/deletemany", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse deleteMany(@RequestParam("idPtk[]") UUID[] idPtk) {
		AjaxResponse response;
		for (UUID uuid : idPtk) {
			ptkService.delete(uuid);
		}
		response = new AjaxResponse("ok","Data dihapus",null);
        return response;
    }
	
}
