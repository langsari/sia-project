package com.its.sia.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;
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

import com.sia.modul.domain.AturanPengganti;
import com.sia.modul.domain.SatMan;
import com.sia.modul.domain.Smt;
import com.sia.modul.domain.TglSmt;
import com.sia.modul.domain.ThnAjaran;
import com.its.sia.service.AjaxResponse;
import com.its.sia.service.AturanPenggantiService;
import com.its.sia.service.Datatable;
import com.its.sia.service.SatManService;
import com.its.sia.service.SmtService;
import com.its.sia.service.TglSmtService;
import com.its.sia.service.ThnAjaranService;
import com.its.sia.validator.AturanPenggantiValidator;
import com.its.sia.validator.TglSmtValidator;

@Controller
@Secured(value = { "ROLE_Admin" })
@RequestMapping(value = "/aturanpengganti")
public class AturanPenggantiController {

	private static final Logger logger = LoggerFactory.getLogger(AturanPenggantiController.class);
	
	@Autowired
	private TglSmtService tglSmtService;
	
	@Autowired
	private ThnAjaranService thnAjaranService;
	
	@Autowired
	private SmtService smtService;
	
	@Autowired
	private SatManService satManService;
	
	@Autowired
	private AturanPenggantiService aturanPenggantiService;
	
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView datatable(HttpSession session,Locale locale, Model model) {
		ModelAndView mav = new ModelAndView();
		
		AturanPengganti aturanPengganti = new AturanPengganti();
		List<TglSmt> listTglSmt = tglSmtService.get("tglSmt.aTglSmtTerhapus = false","thnAjaran.thnThnAjaran desc, smt.nmSmt desc");
		List<SatMan> listSatMan = satManService.get("","nmSatMan asc");
		
		mav.setViewName("MasterAturanPengganti");
		mav.addObject("aturanPengganti", aturanPengganti);
		mav.addObject("listTglSmt", listTglSmt);
		mav.addObject("listSatMan", listSatMan);
		
		mav.addObject ("menuActive","Kelola Aturan Pengganti");
		
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
		Datatable thnAjaranDatatable = aturanPenggantiService.getdatatable(sEcho, iDisplayLength, iDisplayStart, iSortCol_0, sSortDir_0, sSearch,filter);
		return thnAjaranDatatable;
	}
	
	
	@RequestMapping(value = "/simpan", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse simpan(HttpSession session,@Valid @ModelAttribute("aturanPengganti") AturanPengganti aturanPengganti, 
            BindingResult result, Map<String, Object> model,
    		@RequestParam("idTglSmt") UUID idTglSmt, @RequestParam("idSatMan") UUID idSatMan) {
		
		AjaxResponse response = new AjaxResponse();

		aturanPengganti.setTglSmt(tglSmtService.getById(idTglSmt));
		aturanPengganti.setSatMan(satManService.getById(idSatMan));
		
		AturanPenggantiValidator aturanPenggantiValidator = new AturanPenggantiValidator();
		aturanPenggantiValidator.validate(aturanPengganti,result);
        
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
        response.setData(aturanPenggantiService.save(aturanPengganti));
        if(response.getData()!=null) response.setMessage("Data berhasil disimpan");
        else 
        {
        	response.setStatus("error");
        	response.setMessage("Aturan pengganti untuk tahun ajaran dan semester tersebut sudah ada");
        }
        return response;
    }
	
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse edit(HttpSession session,@RequestParam("idAturanPengganti") UUID idAturanPengganti) {
		
		AjaxResponse response;
		AturanPengganti aturanPengganti = aturanPenggantiService.getById(idAturanPengganti);
		if(aturanPengganti == null) response = new AjaxResponse("error","Data tidak ditemukan",null);
		else response = new AjaxResponse("ok","Data ditemukan",aturanPengganti);
        return response;
    }
	
	
	@RequestMapping(value = "/deletemany", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse deleteMany(HttpSession session,@RequestParam("idAturanPengganti[]") UUID[] idAturanPengganti) {
		
		AjaxResponse response;
		for (UUID uuid : idAturanPengganti) {
			aturanPenggantiService.delete(uuid);
		}
		response = new AjaxResponse("ok","Data dihapus",null);
        return response;
    }
	
}
