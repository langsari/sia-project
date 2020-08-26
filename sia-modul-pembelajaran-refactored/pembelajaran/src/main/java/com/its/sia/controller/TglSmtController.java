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

import com.sia.modul.domain.Smt;
import com.sia.modul.domain.TglSmt;
import com.sia.modul.domain.ThnAjaran;
import com.its.sia.service.AjaxResponse;
import com.its.sia.service.Datatable;
import com.its.sia.service.SmtService;
import com.its.sia.service.TglSmtService;
import com.its.sia.service.ThnAjaranService;
import com.its.sia.validator.TglSmtValidator;

@Controller
@Secured(value = { "ROLE_Admin" })
@RequestMapping(value = "/tglsemester")
public class TglSmtController {

	private static final Logger logger = LoggerFactory.getLogger(TglSmtController.class);
	
	@Autowired
	private TglSmtService tglSmtService;
	
	@Autowired
	private ThnAjaranService thnAjaranService;
	
	@Autowired
	private SmtService smtService;
	
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView datatable(HttpSession session, Locale locale, Model model) {
		ModelAndView mav = new ModelAndView();
		
		TglSmt tglSmt = new TglSmt();
		tglSmt.setTglAwalSusunKrs(null);
		List<ThnAjaran> listThnAjaran = thnAjaranService.get("aThnAjaranTerhapus = false","thnThnAjaran desc");
		List<Smt> listSmt = smtService.get("aSmthapus = false","nmSmt asc");
		mav.setViewName("MasterTglSmt");
		mav.addObject("tglSmt", tglSmt);
		mav.addObject("listThnAjaran", listThnAjaran);
		mav.addObject("listSmt", listSmt);
		mav.addObject ("menuActive","Kelola Periode");
		
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
			@RequestParam("a_tgl_smt_terhapus") String a_tgl_smt_terhapus
            ) {
		
		String filter = "CAST( tglSmt.aTglSmtAktif as string) LIKE '%"+a_tgl_smt_terhapus+"%'";
		Datatable thnAjaranDatatable = tglSmtService.getdatatable(sEcho, iDisplayLength, iDisplayStart, iSortCol_0, sSortDir_0, sSearch,filter);
		return thnAjaranDatatable;
	}
	
	
	@RequestMapping(value = "/simpan", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse simpan(HttpSession session,@Valid @ModelAttribute("tglSmt") TglSmt tglSmt, 
            BindingResult result, Map<String, Object> model,
    		@RequestParam("idThnAjaran") UUID idThnAjaran, @RequestParam("idSmt") UUID idSmt) {
		
		AjaxResponse response = new AjaxResponse();
		
		System.out.println("MENYIMPAN");
		System.out.println("MENYIMPAN");
		System.out.println("MENYIMPAN");
		System.out.println("MENYIMPAN");
		System.out.println("MENYIMPAN");
		
		System.out.println("MENYIMPAN");System.out.println("MENYIMPAN");
		
		System.out.println("MENYIMPAN");
		System.out.println("MENYIMPAN");
		System.out.println("MENYIMPAN");
		System.out.println("MENYIMPAN");
		
		if(result.hasErrors()) {
			System.out.println("RESULT HAS ERRORS");
		} else {
			System.out.println("RESULT HAS NO ERRORS");
		}
		
        tglSmt.setThnAjaran(thnAjaranService.getById(idThnAjaran));
        tglSmt.setSmt(smtService.getById(idSmt));
		
		TglSmtValidator tglSmtValidator = new TglSmtValidator();
		tglSmtValidator.validate(tglSmt,result);
        
		if(result.hasErrors()) {
			System.out.println("RESULT HAS ERRORS");
		} else {
			System.out.println("RESULT HAS NO ERRORS");
		}
		
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
        response.setData(tglSmtService.save(tglSmt));
        if(response.getData().equals("tglAmtAktifException")){
        	response.setStatus("error");
        	response.setMessage("Sudah ada Tanggal penting yang aktif. Non Aktifkan terlebih dahulu");
        }
        else if(response.getData()!=null) response.setMessage("Data berhasil disimpan");
        else 
        {
        	response.setStatus("error");
        	response.setMessage("Tanggal penting untuk tahun ajaran dan semester tersebut sudah ada");
        }
        return response;
    }
	
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse edit(HttpSession session,@RequestParam("idTglSmt") UUID idTglSmt) {
		
		AjaxResponse response;
		TglSmt tglSmt = tglSmtService.getById(idTglSmt);
		if(tglSmt == null) response = new AjaxResponse("error","Data tidak ditemukan",null);
		else response = new AjaxResponse("ok","Data ditemukan",tglSmt);
        return response;
    }
	
	
	@RequestMapping(value = "/deletemany", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse deleteMany(HttpSession session,@RequestParam("idTglSmt[]") UUID[] idTglSmt) {
		
		AjaxResponse response;
		for (UUID uuid : idTglSmt) {
			tglSmtService.delete(uuid);
		}
		response = new AjaxResponse("ok","Data dihapus",null);
        return response;
    }
	
}
