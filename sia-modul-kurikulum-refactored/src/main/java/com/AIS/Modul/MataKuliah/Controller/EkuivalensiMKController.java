package com.AIS.Modul.MataKuliah.Controller;

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

import com.AIS.Modul.MataKuliah.Service.AjaxResponse;
import com.AIS.Modul.MataKuliah.Service.Datatable;
import com.AIS.Modul.MataKuliah.Service.EkuivalensiMKService;
import com.AIS.Modul.MataKuliah.Service.KurikulumService;
import com.AIS.Modul.MataKuliah.Service.MKService; 
import com.sia.modul.domain.EkuivalensiMK;
import com.sia.modul.domain.MK;
import com.sia.modul.domain.PrasyaratMK;

@Controller
@RequestMapping("/matakuliah/ekuivalensi")
public class EkuivalensiMKController {
	
	@Autowired
	private MKService mkServ;
	
	@Autowired
	private EkuivalensiMKService ekuivalensiMKServ;
	
	@Autowired
	private KurikulumService kurikulumServ;
	
	private static final Logger logger = LoggerFactory.getLogger(EkuivalensiMKController.class);
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView datatable(Locale locale, Model model) {
		EkuivalensiMK ekMK = new EkuivalensiMK();  
		List<MK> mkList = mkServ.findAll();
		ModelAndView mav = new ModelAndView();
		mav.addObject("mkList", mkList);
		mav.addObject("ekMK", ekMK);
		mav.setViewName("ViewEkuivalensiMK"); 

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
			@RequestParam("statusEkuivalensi") String statusEkuivalensi
            ) 
	{
		String filter = "CAST(ekMK.statusEkuivalensi as string) LIKE '%"+statusEkuivalensi+"%'";
		Datatable prasyaratMKDatatable = ekuivalensiMKServ.getdatatable(sEcho, iDisplayLength, iDisplayStart, iSortCol_0, sSortDir_0, sSearch,filter);
		return prasyaratMKDatatable;
	}
	
	@RequestMapping(value = "/simpan", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse simpan(@Valid @ModelAttribute("ekuivalensiMK") EkuivalensiMK ekuivalensiMK, 
    		@RequestParam("idMK") UUID idMK, @RequestParam("mkIdMK") UUID mkIdMK, 
    		 BindingResult result, Map<String, Object> model) { 
		System.out.println("lalallla");
		MK childMK = mkServ.findById(idMK);
		MK parentMK = mkServ.findById(mkIdMK);
		//ekuivalensiMK.setChildMK(childMK);
		//ekuivalensiMK.setParentMK(parentMK);
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
        response.setData(ekuivalensiMKServ.save(ekuivalensiMK));
        if(response.getData()!=null) response.setMessage("Data berhasil disimpan");
        else 
        {
        	response.setStatus("error");
        	response.setMessage("Ekuivalensi mata kuliah sudah ada");
        }
        return response;
    }
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse edit(@RequestParam("idEkuivalensiMK") UUID idEkuivalensiMK) {
		AjaxResponse response;
		EkuivalensiMK ekuivalensiMK = ekuivalensiMKServ.findById(idEkuivalensiMK);
		if(ekuivalensiMK == null) response = new AjaxResponse("error","Data tidak ditemukan",null);
		else response = new AjaxResponse("ok","Data ditemukan",ekuivalensiMK);
        return response;
    } 
	
	@RequestMapping(value = "/deletemany", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse deleteMany(@RequestParam("idEkuivalensiMK[]") UUID[] idEkuivalensiMK) {
		AjaxResponse response;
		for (UUID uuid : idEkuivalensiMK) {
			ekuivalensiMKServ.delete(uuid);
		}
		response = new AjaxResponse("ok","Data dihapus",null);
        return response;
    }
}
