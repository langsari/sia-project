package com.AIS.Modul.MataKuliah.Controller;

import java.util.ArrayList;
import java.util.HashMap;
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

import com.sia.modul.domain.*;
import com.AIS.Modul.MataKuliah.Service.AjaxResponse;
import com.AIS.Modul.MataKuliah.Service.CapPembService;
import com.AIS.Modul.MataKuliah.Service.Datatable;
import com.AIS.Modul.MataKuliah.Service.KurikulumService;
import com.AIS.Modul.MataKuliah.Service.SatManService;
import com.AIS.Modul.MataKuliah.Service.SubCapPembService;

@Controller
@Secured(value = { "ROLE_Kepala" })
@RequestMapping(value="/capaianbelajar/satuanmanajemen")
public class CapPembController {
		
	@Autowired
	private KurikulumService kurikulumServ;
	
	@Autowired 
	private SatManService satManServ;

	@Autowired 
	private CapPembService capPembServ;
	
	@Autowired
	private SubCapPembService subCapPembServ;
	
	private static final Logger logger = LoggerFactory.getLogger(CapPembController.class); 
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public ModelAndView showCapPembSatMan(HttpSession session) {
		ModelAndView mav = new ModelAndView(); 
		mav.addObject ("menuActive","Kelola Capaian Belajar Satuan Manajemen");
		List<Kurikulum> kurikulumList = kurikulumServ.findAll();
		List<SatMan> satManList	= satManServ.findAll(); 
		CapPemb capPemb = new CapPemb(); 
		mav.addObject("kurikulumList", kurikulumList);
		mav.addObject("satManList", satManList);
		mav.addObject("capPemb", capPemb); 
		mav.setViewName("ViewCapaianSatMan");

		return mav;
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
	
	@RequestMapping(value = "/json", method = RequestMethod.POST)
	public @ResponseBody Datatable json(
			@RequestParam("sEcho") String sEcho, 
			@RequestParam("iDisplayLength") int iDisplayLength,
            @RequestParam("iSortCol_0") int iSortCol_0,
            @RequestParam("sSortDir_0") String sSortDir_0,
            @RequestParam("sSearch") String sSearch,
			@RequestParam("iDisplayStart") int iDisplayStart,
			@RequestParam("statusCapPemb") String statusCapPemb
            ) {
		String filter = "CAST( child.statusCapPemb as string) LIKE '%"+statusCapPemb+"%'"; 
		Datatable subCapPembDatatable = subCapPembServ.getdatatable(sEcho, iDisplayLength, iDisplayStart, iSortCol_0, sSortDir_0, sSearch,filter);
//		return capPembDatatable;
		return subCapPembDatatable;
	}	
	
	@RequestMapping(value = "/simpan", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse simpan(@Valid @ModelAttribute("capPemb") CapPemb capPemb, 
    		@RequestParam ("idKurikulum") UUID idKurikulum, @RequestParam ("idSatMan") UUID idSatMan, 
    		@RequestParam ("idIndukCapPemb[]") UUID[] idIndukCapPemb,
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
        //save
    	SatMan satManObj = satManServ.findById(idSatMan); 
		Kurikulum kur = kurikulumServ.findById(idKurikulum); 
		capPemb.setSatMan(satManObj);
		capPemb.setKurikulum(kur);
        response.setData(capPembServ.save(capPemb));  

        //edit parent cappemb
        List<SubCapPemb> scpList = subCapPembServ.findParent(capPemb.getIdCapPemb().toString()); 
	        if(scpList!=null){
	        	for(SubCapPemb scp : scpList){
	        		subCapPembServ.delete(scp.getIdSubCapPemb()); 
	        } 
        }
        
        if(idIndukCapPemb.length>1){
        	for (UUID uuid : idIndukCapPemb) {
        		System.out.println(uuid);
	        	if(uuid!=null){ 
	        		CapPemb parentCapPemb = capPembServ.findById(uuid);
		        	SubCapPemb subCapPembNew = new SubCapPemb();
		            subCapPembNew.setParentCapPemb(parentCapPemb);
		            subCapPembNew.setChildCapPemb(capPemb);
		            response.setData(subCapPembServ.save(subCapPembNew)); 
		            System.out.println("sub capaian sudah ditambahkan");
	        	}
        	}	  
        }
    	else{
        	SubCapPemb subCapPembNew = new SubCapPemb();
            subCapPembNew.setParentCapPemb(null);
            subCapPembNew.setChildCapPemb(capPemb);
            response.setData(subCapPembServ.save(subCapPembNew));
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
    public @ResponseBody AjaxResponse edit(@RequestParam("idCapPemb") UUID idCapPemb ) {
		AjaxResponse response; 
		CapPemb capPemb = capPembServ.findById(idCapPemb); 
		if(capPemb == null) response = new AjaxResponse("error","Data tidak ditemukan",null);
		else response = new AjaxResponse("ok","Data ditemukan",capPemb);
        return response;
    }
	
	@RequestMapping(value = "/deletemany", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse deleteMany(@RequestParam("idCapPemb[]") UUID[] idCapPemb) {
		AjaxResponse response;
		
		for (UUID uuid : idCapPemb) {
			capPembServ.delete(uuid);
		}
		response = new AjaxResponse("ok","Data non-aktif",null);
        return response;
    } 
	
	@RequestMapping(value="/getparentlist", method = RequestMethod.GET)
	public @ResponseBody AjaxResponse getParentList(@RequestParam("idCapPemb") String idCapPemb) {
		AjaxResponse respongan = null;
		List<UUID> idIndukCapPemb = new ArrayList<UUID>();  
		List<SubCapPemb> scpList = subCapPembServ.findParent(idCapPemb);  
		if(scpList!=null){
			for(SubCapPemb scp : scpList){  
				if(scp.getParentCapPemb()!=null){ 
					idIndukCapPemb.add(scp.getParentCapPemb().getIdCapPemb()); 
				} 
				else{
					idIndukCapPemb.add(null); 
				}
			} 
			respongan = new AjaxResponse("ok","Success",scpList); 
		}  
		return respongan;
	}
	
	@RequestMapping(value = "/laporan", method = RequestMethod.POST)
    public ModelAndView getCapPembReport(@RequestParam("idKurikulum") UUID idKurikulum) {
		ModelAndView mav = new ModelAndView();
		List<CapPemb> cpList = capPembServ.findByKurikulum(idKurikulum); 
		List < List<CapPemb> > matrixCapPemb = new ArrayList< List<CapPemb> >();
		for(CapPemb cp : cpList){
			List<CapPemb> listSementara = new ArrayList<CapPemb>();
			listSementara.add(cp);
			
			List<CapPemb> cpChild = capPembServ.findByParent(cp.getIdCapPemb());
			if(cpChild != null)
				listSementara.addAll(cpChild);
			
			matrixCapPemb.add(listSementara);
		}
		
		HashMap<CapPemb, Boolean> isChild = new HashMap<CapPemb, Boolean>();
		for (List<CapPemb> list : matrixCapPemb) {
			if(list.size() > 1) {
				for(int i=1; i<list.size(); i++) {
					if(!isChild.containsKey(list.get(i))) {
						isChild.put(list.get(i), true);
					}
				}
			}
		}
		
		List<CapPemb> roots = new ArrayList<CapPemb>();
		HashMap<CapPemb, Boolean> isRoot = new HashMap<CapPemb, Boolean>();
		for (CapPemb cp : cpList) {
			if(!isChild.containsKey(cp)) {
				roots.add(cp);
				isRoot.put(cp, true);
			}
		}
		
		System.out.println("Parents: ");
		for (CapPemb capPemb : roots) {
			for (List<CapPemb> list : matrixCapPemb) {
				if(isRoot.containsKey(list.get(0))) {
					System.out.println("Parent " + list.get(0) + ": ");
				}
			}
		}
		
        return mav;
    } 
	
	@RequestMapping(value = "/laporan", method = RequestMethod.GET)
    public ModelAndView showParent(Locale locale, Model model) {
		ModelAndView mav = new ModelAndView();  
		List<Kurikulum> kurikulum = kurikulumServ.findAll();
		mav.addObject(kurikulum);
		mav.setViewName("DaftarReportCapaianPembelajaran");
		mav.addObject ("menuActive","Kelola Capaian Belajar Satuan Manajemen"); 
        return mav;
    } 
}
