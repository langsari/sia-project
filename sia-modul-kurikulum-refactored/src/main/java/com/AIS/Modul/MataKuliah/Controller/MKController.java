package com.AIS.Modul.MataKuliah.Controller;

import java.util.ArrayList;
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
import com.AIS.Modul.MataKuliah.Service.KonversiNilaiService;
import com.AIS.Modul.MataKuliah.Service.KurikulumService;
import com.AIS.Modul.MataKuliah.Service.MKService;
import com.AIS.Modul.MataKuliah.Service.RumpunMKService;
import com.AIS.Modul.MataKuliah.Service.SatManMKService;
import com.AIS.Modul.MataKuliah.Service.SatManService;
import com.sia.modul.domain.KonversiNilai;
import com.sia.modul.domain.Kurikulum;
import com.sia.modul.domain.MK;
import com.sia.modul.domain.RumpunMK;
import com.sia.modul.domain.SatMan;

@Controller
@Secured(value = { "ROLE_Admin" })
@RequestMapping(value = "/matakuliah/kelola")
public class MKController {
	 
	@Autowired
	private KurikulumService kurikulumServ;
	
	@Autowired
	private RumpunMKService rumpunMKServ;
	
	@Autowired
	private MKService mkServ;
	
	@Autowired
	private KonversiNilaiService konversiNilaiServ;
	
	@Autowired
	private SatManService satManServ;
	
	private static final Logger logger = LoggerFactory.getLogger(MKController.class);
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView datatable(Locale locale, Model model, HttpSession session) {
		MK mk = new MK(); 
		List<Kurikulum> kurikulumList = kurikulumServ.findAll();
		List<RumpunMK> rumpunMKList = rumpunMKServ.findAll();
		List<SatMan> satManList = satManServ.findAll();
		List<KonversiNilai> konversiNilaiList = konversiNilaiServ.findAll();
		ModelAndView mav = new ModelAndView();
		mav.addObject("kurikulumList", kurikulumList);
		mav.addObject("rumpunMKList", rumpunMKList);
		mav.addObject("satManList", satManList);
		mav.addObject("konversiNilaiList", konversiNilaiList);
		mav.addObject("mk", mk);
		mav.setViewName("ViewMK");  
		mav.addObject ("menuActive","Kelola Mata Kuliah"); 
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
			@RequestParam("statusMK") String statusMK
            ) {
		String filter = "CAST(mk.statusMK as string) LIKE '%"+statusMK+"%'";
		Datatable rumpunMKDatatable = mkServ.getdatatable(sEcho, iDisplayLength, iDisplayStart, iSortCol_0, sSortDir_0, sSearch, filter);
		
		return rumpunMKDatatable;
	} 
	
	@RequestMapping(value = "/simpan", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse simpan(@Valid @ModelAttribute("MK") MK mk, 
    		@RequestParam("idKurikulum") UUID idKurikulum, 
    		@RequestParam("idRumpunMK") UUID idRumpunMK,
    		@RequestParam("idKonversi") UUID idKonversi,
    		BindingResult result, Map<String, Object> model) { 
		AjaxResponse response = new AjaxResponse(); 
		if(idRumpunMK!=null){ 
			RumpunMK rumpunMKObj = rumpunMKServ.findById(idRumpunMK);
			mk.setRumpunMK(rumpunMKObj); 
		} 
		else{ 
			mk.setRumpunMK(null);
		}  
		KonversiNilai konvNilai = konversiNilaiServ.findById(idKonversi);
		Kurikulum kurikulumObj = kurikulumServ.findById(idKurikulum);
		mk.setKurikulum(kurikulumObj);
		mk.setKonversiNilai(konvNilai);
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
        response.setData(mkServ.save(mk));
        if(response.getData()!=null) response.setMessage("Data berhasil disimpan");
        else 
        {
        	response.setStatus("error");
        	response.setMessage("Mata kuliah sudah ada");
        }
        return response;
    }
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse edit(@RequestParam("idMK") UUID idMK) {
		AjaxResponse response;
		MK mk = mkServ.findById(idMK);
		System.out.println(mk.getKonversiNilai().getHuruf()); 
		if(mk == null) response = new AjaxResponse("error","Data tidak ditemukan",null);
		else response = new AjaxResponse("ok","Data ditemukan",mk);
        return response;
    }
	
	@RequestMapping(value = "/deletemany", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse deleteMany(@RequestParam("idMK[]") UUID[] idMK) {
		AjaxResponse response;
		for (UUID uuid : idMK) {
			mkServ.delete(uuid);
		}
		response = new AjaxResponse("ok","Data telah di non-aktifkan",null);
        return response;
    }  
}
