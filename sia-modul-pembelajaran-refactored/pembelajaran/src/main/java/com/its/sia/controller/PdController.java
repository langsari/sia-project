package com.its.sia.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import javax.validation.Valid;
import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sia.modul.domain.Ptk;
import com.sia.modul.domain.Pd;
import com.its.sia.service.AjaxResponse;
import com.its.sia.service.Datatable;
import com.its.sia.service.PtkService;
import com.its.sia.service.PdService;

@Controller
@RequestMapping(value = "/pesertadidik")
public class PdController {

	private static final Logger logger = LoggerFactory.getLogger(PdController.class);
	
	@Autowired
	private PdService pdService;
	
	@Autowired
	private PtkService ptkServices;
	
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView datatable(Locale locale, Model model) {
		Pd pd = new Pd();
		List<Ptk> listPtk = ptkServices.get("aPtkTerhapus = false","nmPtk asc");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("MasterPd");
		mav.addObject("pd", pd);
		mav.addObject("listPtk", listPtk);
		
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
			@RequestParam("a_pd_terhapus") String a_pd_terhapus
            ) {
		String filter = "CAST( pd.aPdTerhapus as string) LIKE '%"+a_pd_terhapus+"%'";
		logger.info("datatable {}.", a_pd_terhapus);
		Datatable pdDatatable = pdService.getdatatable(sEcho, iDisplayLength, iDisplayStart, iSortCol_0, sSortDir_0, sSearch,filter);
		return pdDatatable;
	}
	
	
	@RequestMapping(value = "/simpan", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse simpan(@Valid @ModelAttribute("tglSmt") Pd pd, 
            BindingResult result, Map<String, Object> model,
    		@RequestParam("idPtk") UUID idPtk) {
		AjaxResponse response = new AjaxResponse();

		if(idPtk!=null)pd.setPtk(ptkServices.getById(idPtk));
		
        if (result.hasErrors()) {
        	response.setStatus("error");
        	List<FieldError> fieldError = result.getFieldErrors();
        	String message ="";
    		if(fieldError.get(0).isBindingFailure()) message += "Salah satu input tiak valid";
    		else message += fieldError.get(0).getDefaultMessage();
        	for(int i=1;i<fieldError.size();i++)
        	{
        		if(fieldError.get(i).isBindingFailure()) message += "<br/>Salah satu input tiak valid";
        		else message += "<br/>"+fieldError.get(i).getDefaultMessage();
        	}
        	response.setMessage(message);
        	response.setData(fieldError);
            return response;
        }      
    	response.setStatus("ok");
        response.setData(pdService.save(pd));
        if(response.getData()!=null) response.setMessage("Data berhasil disimpan");
        else 
        {
        	response.setStatus("error");
        	response.setMessage("NIM terpakai");
        }
        return response;
    }
	
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse edit(@RequestParam("idPd") UUID idPd) {
		AjaxResponse response;
		Pd pd = pdService.getById(idPd);
		Pd tmpPd = new Pd(pd);
		
		if(pd == null) response = new AjaxResponse("error","Data tidak ditemukan",null);
		else response = new AjaxResponse("ok","Data ditemukan",tmpPd);
        return response;
    }
	
	
	@RequestMapping(value = "/deletemany", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse deleteMany(@RequestParam("idPd[]") UUID[] idPd) {
		AjaxResponse response;
		for (UUID uuid : idPd) {
			pdService.delete(uuid);
		}
		response = new AjaxResponse("ok","Data dihapus",null);
        return response;
    }
	
}
