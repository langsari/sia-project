package com.AIS.Modul.MataKuliah.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.AIS.Modul.MataKuliah.Service.AjaxResponse;
import com.AIS.Modul.MataKuliah.Service.CapPembMKService;
import com.AIS.Modul.MataKuliah.Service.CapPembService;
import com.AIS.Modul.MataKuliah.Service.Datatable;
import com.AIS.Modul.MataKuliah.Service.KurikulumService;
import com.AIS.Modul.MataKuliah.Service.MKService;
import com.AIS.Modul.MataKuliah.Service.SubCapPembMKService;
import com.sia.modul.domain.CapPemb;
import com.sia.modul.domain.CapPembMK;
import com.sia.modul.domain.Kurikulum;
import com.sia.modul.domain.MK;
import com.sia.modul.domain.SatMan;
import com.sia.modul.domain.SubCapPemb;
import com.sia.modul.domain.SubCapPembMK;

@Controller
@Secured(value = { "ROLE_Kepala" })
@RequestMapping(value="/matakuliah/capaianbelajar")
public class CapPembMKController {

	@Autowired
	private MKService mkServ;
	
	@Autowired
	private SubCapPembMKService subCapPembMKServ;
	
	@Autowired
	private CapPembMKService capPembMKServ;
	 
	@Autowired
	private CapPembService capPembServ;
	
	private static final Logger logger = LoggerFactory.getLogger(CapPembMKController.class); 
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public ModelAndView showCapPembMK(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		List<MK> mkList = mkServ.findAll();
		CapPembMK capPembMK = new CapPembMK(); 
		mav.addObject("mkList", mkList); 
		mav.addObject("capPembMK", capPembMK);
		mav.setViewName("ViewCapaianMK");
		mav.addObject ("menuActive","Kelola Capaian Belajar Mata Kuliah"); 
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
			@RequestParam("statusCapPembMK") String statusCapPembMK
            ) {
		String filter = "CAST( cpmk.statusCapPembMK as string) LIKE '%"+statusCapPembMK+"%'";
		Datatable capPembMKDatatable = subCapPembMKServ.getdatatable(sEcho, iDisplayLength, iDisplayStart, iSortCol_0, sSortDir_0, sSearch,filter);
		return capPembMKDatatable;
	}	
	
	@RequestMapping(value="/subcapaian/json", method=RequestMethod.POST)
	public @ResponseBody Datatable json1(
			@RequestParam("sEcho") String sEcho, 
			@RequestParam("iDisplayLength") int iDisplayLength,
            @RequestParam("iSortCol_0") int iSortCol_0,
            @RequestParam("sSortDir_0") String sSortDir_0,
            @RequestParam("sSearch") String sSearch,
			@RequestParam("iDisplayStart") int iDisplayStart
            ) {
		Datatable capPembDatatable = capPembServ.getdatatable1(sEcho, iDisplayLength, iDisplayStart, iSortCol_0, sSortDir_0, sSearch);
		return capPembDatatable;
	}	
	
	@RequestMapping(value = "/simpan", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse simpan(@Valid @ModelAttribute("capPembMK") CapPembMK capPembMK, 
    		@RequestParam ("idMK") UUID idMK, @RequestParam ("idCapPemb[]") UUID[] idCapPemb,
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
        MK mk = mkServ.findById(idMK);  
		capPembMK.setMk(mk);
        response.setData(capPembMKServ.save(capPembMK));   
        //get data
        List<SubCapPembMK> scpMKList = subCapPembMKServ.findCapPemb(capPembMK.getIdCapPembMK().toString()); 
        if(scpMKList!=null){
        	for(SubCapPembMK scpmk : scpMKList){
        		subCapPembMKServ.delete(scpmk.getIdSubCapPembMK()); 
        	} 
        }
        //save
        if(idCapPemb.length>1){
        	for (UUID uuid : idCapPemb) {
        		System.out.println(uuid);
	        	if(uuid!=null){ 
	        		CapPemb capPemb = capPembServ.findById(uuid);
		        	SubCapPembMK subCapPembMKNew = new SubCapPembMK();
		        	subCapPembMKNew.setCapPembMK(capPembMK);
		        	subCapPembMKNew.setCapPemb(capPemb);
		            response.setData(subCapPembMKServ.save(subCapPembMKNew)); 
		            System.out.println("sub capaian sudah ditambahkan");
	        	}
        	}	  
        }
    	else{ 
        	SubCapPembMK subCapPembMKNew = new SubCapPembMK();
        	subCapPembMKNew.setCapPembMK(capPembMK);
        	subCapPembMKNew.setCapPemb(null);
            response.setData(subCapPembMKServ.save(subCapPembMKNew)); 
            System.out.println("sub capaian sudah ditambahkan");
        }
        
        if(response.getData()!=null) response.setMessage("Data berhasil disimpan");
        else
        {
        	response.setStatus("error");
        	response.setMessage("Capaian Pembelajaran sudah ada");
        }
        return response;
    }
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse edit(@RequestParam("idCapPembMK") UUID idCapPembMK) {
		AjaxResponse response;    
		CapPembMK capPembMK = capPembMKServ.findById(idCapPembMK);  
		if(capPembMK == null) response = new AjaxResponse("error","Data tidak ditemukan",null);
		else response = new AjaxResponse("ok","Data ditemukan",capPembMK);
        return response;
    }
	
	@RequestMapping(value = "/deletemany", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse deleteMany(@RequestParam("idCapPembMK[]") UUID[] idCapPembMK) {
		AjaxResponse response; 
		for (UUID uuid : idCapPembMK) {
			capPembMKServ.delete(uuid);
		}
		response = new AjaxResponse("ok","Data non-aktif",null);
        return response;
    } 
	
	@RequestMapping(value="/getcappemblist", method = RequestMethod.GET)
	public @ResponseBody AjaxResponse getCapPembList(@RequestParam("idCapPembMK") String idCapPembMK) {
		AjaxResponse respongan = null;
		List<UUID> idCapPemb = new ArrayList<UUID>();  
		System.out.println(idCapPembMK);
		List<SubCapPembMK> scpMKList = subCapPembMKServ.findCapPemb(idCapPembMK.toString());  
		if(scpMKList!=null){
			for(SubCapPembMK scpmk : scpMKList){   
				if(scpmk.getCapPemb()!=null){ 
					idCapPemb.add(scpmk.getCapPemb().getIdCapPemb());
				}
				else{
					idCapPemb.add(null);
				}
			} 
			respongan = new AjaxResponse("ok","Success",scpMKList); 
		} 
		else{
			respongan = new AjaxResponse("ok","Tidak ada cap pemb MK",null); 
		}
		return respongan;
	}
}
