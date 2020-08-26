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
import com.AIS.Modul.MataKuliah.Service.MKService;
import com.AIS.Modul.MataKuliah.Service.PrasyaratMKService;
import com.sia.modul.domain.MK;
import com.sia.modul.domain.PrasyaratMK;
import com.sia.modul.domain.RumpunMK;
 
@Controller
@Secured(value = { "ROLE_Admin" })
@RequestMapping("/matakuliah/prasyarat")
public class PrasyaratMKController { 
	
	@Autowired
	private MKService mkServ;
	
	@Autowired
	private PrasyaratMKService prasyaratMKServ;
	
	private static final Logger logger = LoggerFactory.getLogger(PrasyaratMKController.class);
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView datatable(Locale locale, Model model, HttpSession session) {
		PrasyaratMK pMK = new PrasyaratMK();  
		List<MK> mkList = mkServ.findAll();
		ModelAndView mav = new ModelAndView();
		mav.addObject("mkList", mkList);
		mav.addObject("pMK", pMK);
		mav.setViewName("ViewPrasyaratMK"); 
		mav.addObject ("menuActive","Kelola Mata Kuliah Prasyarat"); 
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
			@RequestParam("statusPrasyarat") String statusPrasyarat
            ) 
	{
		String filter = "CAST(pMK.statusPrasyarat as string) LIKE '%"+statusPrasyarat+"%'";
		Datatable prasyaratMKDatatable = prasyaratMKServ.getdatatable(sEcho, iDisplayLength, iDisplayStart, iSortCol_0, sSortDir_0, sSearch,filter);
		return prasyaratMKDatatable;
	}
	
	@RequestMapping(value = "/simpan", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse simpan(@Valid @ModelAttribute("prasyaratMK") PrasyaratMK prasyaratMK, 
    		@RequestParam("idPrasyaratMK") UUID idPrasyaratMK,
    		@RequestParam("idMK") UUID idMK, @RequestParam("mkIdMK") UUID mkIdMK, 
    		 BindingResult result, Map<String, Object> model) {
		AjaxResponse response = new AjaxResponse();  
		MK childMK = mkServ.findById(idMK);
		MK parentMK = mkServ.findById(mkIdMK);
		prasyaratMK.setChildMK(childMK);
		prasyaratMK.setParentMK(parentMK); 
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
        if(idPrasyaratMK!=null){
        	prasyaratMK.setIdPrasyaratMK(idPrasyaratMK);
        } 
        response.setData(prasyaratMKServ.save(prasyaratMK));
        if(response.getData()!=null) response.setMessage("Data berhasil disimpan");
        else 
        {
        	response.setStatus("error");
        	response.setMessage("Prasyarat mata kuliah sudah ada");
        }
        return response;
    }
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse edit(@RequestParam("idPrasyaratMK") UUID idPrasyaratMK) {
		AjaxResponse response;
		PrasyaratMK prasyaratMK = prasyaratMKServ.findById(idPrasyaratMK);
		if(prasyaratMK == null) response = new AjaxResponse("error","Data tidak ditemukan",null);
		else response = new AjaxResponse("ok","Data ditemukan",prasyaratMK);
        return response;
    } 
	
	@RequestMapping(value = "/deletemany", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse deleteMany(@RequestParam("idPrasyaratMK[]") UUID[] idPrasyaratMK) {
		AjaxResponse response;
		for (UUID uuid : idPrasyaratMK) {
			prasyaratMKServ.delete(uuid);
		}
		response = new AjaxResponse("ok","Data dihapus",null);
        return response;
    }
}
