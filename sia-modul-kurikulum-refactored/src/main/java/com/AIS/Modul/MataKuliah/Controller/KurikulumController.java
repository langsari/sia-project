package com.AIS.Modul.MataKuliah.Controller;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.sia.modul.domain.Kurikulum;
import com.sia.modul.domain.SatMan;
import com.AIS.Modul.MataKuliah.Service.AjaxResponse;
import com.AIS.Modul.MataKuliah.Service.Datatable;
import com.AIS.Modul.MataKuliah.Service.KurikulumService;
import com.AIS.Modul.MataKuliah.Service.SatManService; 

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Secured(value = { "ROLE_Admin" })
@RequestMapping(value = "/kurikulum")
public class KurikulumController {
	 
	@Autowired
	private KurikulumService kurikulumServ;
	 
	@Autowired
	private SatManService satManServ;
	
	private static final Logger logger = LoggerFactory.getLogger(KurikulumController.class);
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView datatable(Locale locale, Model model, HttpSession session) {
		Kurikulum kurikulum = new Kurikulum();
		List<SatMan> satManList = satManServ.findAll();
		ModelAndView mav = new ModelAndView();
		mav.setViewName("ViewKurikulum");
		mav.addObject("kurikulum", kurikulum);
		mav.addObject("satManList", satManList);
		mav.addObject ("menuActive","Kelola Kurikulum"); 
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
			@RequestParam("statusKurikulum") String statusKurikulum
            ) {
		String filter = "CAST( k.statusKurikulum as string) LIKE '%"+statusKurikulum+"%'";
		Datatable kurikulumDatatable = kurikulumServ.getdatatable(sEcho, iDisplayLength, iDisplayStart, iSortCol_0, sSortDir_0, sSearch,filter);
		return kurikulumDatatable;
	}
	
	@RequestMapping(value = "/simpan", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse simpan(@Valid @ModelAttribute("kurikulum") Kurikulum kurikulum, 
    		@RequestParam ("idSatMan") UUID idSatMan, BindingResult result, Map<String, Object> model) {
		AjaxResponse response = new AjaxResponse();
		SatMan satManObj = satManServ.findById(idSatMan);
		logger.info(String.valueOf(kurikulum.getThnMulai()));
		kurikulum.setSatMan(satManObj);
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
        response.setData(kurikulumServ.save(kurikulum));
        if(response.getData()!=null) response.setMessage("Data berhasil disimpan");
        else 
        {
        	response.setStatus("error");
        	response.setMessage("Kurikulum sudah ada");
        }
        return response;
    }
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse edit(@RequestParam("idKurikulum") UUID idKurikulum) {
		AjaxResponse response;
		Kurikulum kurikulum = kurikulumServ.findById(idKurikulum);
		if(kurikulum == null) response = new AjaxResponse("error","Data tidak ditemukan",null);
		else response = new AjaxResponse("ok","Data ditemukan",kurikulum);
        return response;
    }
	
	@RequestMapping(value = "/deletemany", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse deleteMany(@RequestParam("idKurikulum[]") UUID[] idKurikulum) {
		AjaxResponse response;
		for (UUID uuid : idKurikulum) {
			kurikulumServ.delete(uuid);
		}
		response = new AjaxResponse("ok","Data dihapus",null);
        return response;
    } 
}
