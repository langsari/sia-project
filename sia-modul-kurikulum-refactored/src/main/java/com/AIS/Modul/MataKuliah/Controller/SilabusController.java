package com.AIS.Modul.MataKuliah.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.Max;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.taglibs.standard.tei.ForEachTEI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.AIS.Modul.MataKuliah.Service.AjaxResponse;
import com.AIS.Modul.MataKuliah.Service.CapPembMKService;
import com.AIS.Modul.MataKuliah.Service.CapPembMKTemporary;
import com.AIS.Modul.MataKuliah.Service.CapPembService;
import com.AIS.Modul.MataKuliah.Service.DetailPustakaService;
import com.AIS.Modul.MataKuliah.Service.DetailSilabusService;
import com.AIS.Modul.MataKuliah.Service.MKService;
import com.AIS.Modul.MataKuliah.Service.PemetaanSilabusService;
import com.AIS.Modul.MataKuliah.Service.PrasyaratMKService;
import com.AIS.Modul.MataKuliah.Service.PustakaService;
import com.AIS.Modul.MataKuliah.Service.SatManMKService;
import com.AIS.Modul.MataKuliah.Service.SatManMKTemporary;
import com.AIS.Modul.MataKuliah.Service.SilabusDataReport;
import com.AIS.Modul.MataKuliah.Service.SilabusService;
import com.AIS.Modul.MataKuliah.Service.SubCapPembMKService;
import com.sia.modul.domain.CapPemb;
import com.sia.modul.domain.CapPembMK;
import com.sia.modul.domain.DetailPemetaan;
import com.sia.modul.domain.DetailPustaka;
import com.sia.modul.domain.DetailSilabus;
import com.sia.modul.domain.Kurikulum;
import com.sia.modul.domain.MK;
import com.sia.modul.domain.PemetaanSilabus;
import com.sia.modul.domain.PrasyaratMK;
import com.sia.modul.domain.Pustaka;
import com.sia.modul.domain.RumpunMK;
import com.sia.modul.domain.SatManMK;
import com.sia.modul.domain.Silabus;
import com.sia.modul.domain.SubCapPemb;
import com.sia.modul.domain.SubCapPembMK;

@Controller
@RequestMapping(value = "/silabus/kelola")
public class SilabusController {

	@Autowired
	private MKService mkServ;
	
	@Autowired
	private SilabusService silabusServ;
	
	@Autowired
	private CapPembMKService capPembMKServ;
	
	@Autowired
	private DetailSilabusService detailSilabusServ;

	@Autowired
	private PemetaanSilabusService pemetaanSilabusServ;
	
	@Autowired
	private PustakaService pustakaServ;
	
	@Autowired
	private DetailPustakaService detailPustakaServ;
	
	@Autowired
	private SubCapPembMKService subCapPembMKServ;
	
	@Autowired
	private PrasyaratMKService prasyaratMKServ;
	
	@Autowired
	private CapPembService capPembServ;
	
	@Autowired
	private SatManMKService satManMKServ;
	
	
	private static final Logger logger = LoggerFactory.getLogger(SilabusController.class);

	@Secured(value = { "ROLE_Kepala" })	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView datatable(Locale locale, Model model, HttpSession session) { 
		List<MK> mkList = mkServ.findAll();
		List<CapPembMK> cpmkList = capPembMKServ.findAll();
		List<Pustaka> pustakaList = pustakaServ.findAll();
		ModelAndView mav = new ModelAndView();  
		mav.addObject("mkList", mkList);
		mav.addObject("cpmkList", cpmkList);
		mav.addObject("pustakaList", pustakaList);
		mav.setViewName("ViewSilabus");
		mav.addObject ("menuActive","Kelola Silabus");
		return mav;
	}

	@Secured(value = { "ROLE_Kepala" })	
	@RequestMapping(value="/simpan", method = RequestMethod.POST)
	public @ResponseBody AjaxResponse simpanSilabus(@RequestParam("idMK") UUID idMK) {  
		AjaxResponse response = new AjaxResponse(); 
		Silabus cekSilabus = silabusServ.findByMK(idMK);
		if(cekSilabus==null){
			MK mk = mkServ.findById(idMK); 
			Silabus silabusNew = new Silabus();
			silabusNew.setMk(mk); 
			silabusServ.save(silabusNew);
			response.setData(silabusNew);
			response.setMessage("Data berhasil disimpan"); 
		}   
		else{
			response.setData(cekSilabus); 
			response.setMessage("Silabus ditampilkan yang sudah ada"); 
		} 
        return response; 
	}

	@Secured(value = { "ROLE_Kepala" })	
	@RequestMapping(value="/simpandetail", method = RequestMethod.POST)
	public @ResponseBody AjaxResponse simpanDetailSilabus(@RequestParam("idSilabus") UUID idSilabus,
			@RequestParam("pokokBahasan") String pokokBahasan) {
		AjaxResponse response = new AjaxResponse(); 
		Silabus silabus = silabusServ.findById(idSilabus); 
		DetailSilabus detailSilabus = new DetailSilabus();
		
		detailSilabus.setSilabus(silabus);
		detailSilabus.setPokokBahasan(pokokBahasan);
		detailSilabusServ.save(detailSilabus);
		
		response.setData(detailSilabus);
		response.setStatus("ok");
		response.setMessage("Data detail silabus tersimpan");
		return response;
	}

	@Secured(value = { "ROLE_Kepala" })	
	@RequestMapping(value="/editdetail", method = RequestMethod.POST)
	public @ResponseBody AjaxResponse editDetailSilabus(@RequestParam("idDetailSilabus") UUID idDetailSilabus,
			@RequestParam("pokokBahasan") String pokokBahasan) {
		AjaxResponse response = null; 
		DetailSilabus detailSilabus = detailSilabusServ.findById(idDetailSilabus);    
		detailSilabus.setPokokBahasan(pokokBahasan);  
		if(detailSilabusServ.save(detailSilabus)==null){
			response = new AjaxResponse("error", "data pokok bahasan tidak ditemukan", null);
		}
		else{
			response = new AjaxResponse("ok", "data pokok bahasan diperbaharui", detailSilabus); 
		}
		return response;
	}

	@Secured(value = { "ROLE_Kepala" })	
	@RequestMapping(value="/hapusdetail", method = RequestMethod.POST)
	public @ResponseBody AjaxResponse hapusDetailSilabus(@RequestParam("idDetailSilabus") UUID idDetailSilabus) {    
		detailSilabusServ.delete(idDetailSilabus); 
		
		return new AjaxResponse();
	}

	@Secured(value = { "ROLE_Kepala" })	
	@RequestMapping(value="/simpanpemetaan", method = RequestMethod.POST)
	public @ResponseBody AjaxResponse simpanPemetaan(@RequestParam("idDetailSilabus") UUID idDetailSilabus,
			@RequestParam("idCapPembMK") UUID idCapPembMK) {
		AjaxResponse response = new AjaxResponse(); 
		DetailSilabus ds = detailSilabusServ.findById(idDetailSilabus); 
		CapPembMK cpmk = capPembMKServ.findById(idCapPembMK); 
		PemetaanSilabus ps = new PemetaanSilabus(); 
		
		ps.setCapPembMK(cpmk);
		ps.setDetailSilabus(ds);
		pemetaanSilabusServ.save(ps);
		
		response.setData(ps);
		response.setStatus("ok");
		response.setMessage("Data pemetaan tersimpan");
		return response;
	}

	@Secured(value = { "ROLE_Kepala" })	
	@RequestMapping(value="/simpanpustaka", method = RequestMethod.POST)
	public @ResponseBody AjaxResponse simpanDetailPustaka(@RequestParam("idPustaka") UUID idPustaka,
			@RequestParam("idSilabus") UUID idSilabus) {
		AjaxResponse response = new AjaxResponse(); 
		Silabus silabus = silabusServ.findById(idSilabus); 
		Pustaka pustaka = pustakaServ.findById(idPustaka); 
		DetailPustaka dp = new DetailPustaka();  
		
		dp.setPustaka(pustaka);
		dp.setSilabus(silabus);
		detailPustakaServ.save(dp);
		
		response.setData(dp);
		response.setStatus("ok");
		response.setMessage("Data detail pustaka tersimpan");
		return response;
	}

	@Secured(value = { "ROLE_Kepala" })	
	@RequestMapping(value="/getpustaka", method = RequestMethod.POST)
	public @ResponseBody AjaxResponse simpanDetailPustaka(@RequestParam("idSilabus") UUID idSilabus) {
		AjaxResponse response = new AjaxResponse(); 
		List<DetailPustaka> dpList = detailPustakaServ.findBySilabus(idSilabus);  
		response.setData(dpList);
		response.setStatus("ok");
		response.setMessage("Data detail pustaka tersimpan");
		return response;
	}

	@Secured(value = { "ROLE_Kepala" })	
	@RequestMapping(value="/getpokokbahasanlist", method=RequestMethod.GET)
	public @ResponseBody AjaxResponse getPokokBahasanList(@RequestParam("idSilabus") UUID idSilabus){
		AjaxResponse response = null;
		List<DetailSilabus> pokokBahasanList = detailSilabusServ.findBySilabus(idSilabus);
		if(pokokBahasanList!=null){
			response = new AjaxResponse("ok", "list pokok bahasan ada", pokokBahasanList);
		}
		else response = new AjaxResponse("", "", null);
		return response; 
	}

	@Secured(value = { "ROLE_Kepala" })	
	@RequestMapping(value="/getpemetaanlist", method=RequestMethod.GET)
	public @ResponseBody AjaxResponse getPemetaanList(@RequestParam("idDetailSilabus") UUID idDetailSilabus){
		AjaxResponse response = null;
		List<PemetaanSilabus> pemetaanSilabusList = pemetaanSilabusServ.findByDetailSilabus(idDetailSilabus);
		if(pemetaanSilabusList!=null){
			response = new AjaxResponse("ok", "list pemetaan silabus ada", pemetaanSilabusList);
		}
		else response = new AjaxResponse("", "", null);
		return response; 
	}

	@Secured(value = { "ROLE_Kepala" })	
	@RequestMapping(value="/hapuspemetaan", method = RequestMethod.POST)
	public @ResponseBody AjaxResponse deletePemetaan(@RequestParam("idPemetaanSilabus") UUID idPemetaanSilabus) {    
		pemetaanSilabusServ.delete(idPemetaanSilabus);  
		return new AjaxResponse("","Data pemetaan pokok bahasan sudah dihapus",null);
	}

	@Secured(value = { "ROLE_Kepala" })	
	@RequestMapping(value="/hapuspustaka", method = RequestMethod.POST)
	public @ResponseBody AjaxResponse deletePustaka (@RequestParam("idDetailPustaka") UUID idDetailPustaka) {    
		detailPustakaServ.delete(idDetailPustaka);  
		return new AjaxResponse("","Data pemetaan pokok bahasan sudah dihapus",null);
	}

	@Secured(value = { "ROLE_Kepala", "ROLE_Pendidik", "ROLE_Peserta Didik" })	
	@RequestMapping(value="/laporan", method = RequestMethod.GET)
	public ModelAndView showSilabus(Locale locale, Model model, HttpSession session) {  
		ModelAndView mav = new ModelAndView();
		List<MK> mkList = mkServ.findAll();  
		List<Silabus> silabusList = silabusServ.findAll();
		String message = null;
		mav.addObject("message", message);
		mav.addObject("mkList", mkList);
		mav.addObject("silabusList", silabusList);
		mav.setViewName("DaftarReportSilabus");
		mav.addObject ("menuActive","Kelola Silabus"); 
		return mav;
	}

	@Secured(value = { "ROLE_Kepala", "ROLE_Pendidik", "ROLE_Peserta Didik" })
	@RequestMapping(value="/laporan", method = RequestMethod.POST)
	public ModelAndView getSilabusElement(Locale locale, Model model, @RequestParam("idMK") UUID idMK, HttpSession session ) {  
		ModelAndView mav = new ModelAndView();    
		MK mk2 = mkServ.findById(idMK); //dapat objek MK
		Silabus silabus = silabusServ.findByMK(idMK);//dapat silabusnya 
		if(silabus == null){  
			String message = "Silabus mata kuliah tidak ada";
			List<MK> mkList = mkServ.findAll(); 
			mav.addObject("mkList", mkList);
			mav.addObject("message", message);
			mav.setViewName("DaftarReportSilabus");
			return mav;
		}
		else {
			List<DetailSilabus> dsList = detailSilabusServ.findByMK(idMK);//dapat pokok bahasannya
			List<DetailPustaka> dpList = detailPustakaServ.findBySilabus(silabus.getIdSilabus()); //dapat pustakanya     
			List<CapPembMK> cpmkList = capPembMKServ.findByMK(idMK); //dapat capaian mata kuliah
			List<CapPemb> cpList = subCapPembMKServ.findByMK(idMK); //dapat capaian prodi  
			List<PrasyaratMK> prasyaratList = prasyaratMKServ.findParentMK(idMK); 
			List<SatManMK> smmkList = satManMKServ.findByMK(idMK);//dapat satuan manajemen MK 	 
			mav.addObject("mk2", mk2);
			mav.addObject("silabus", silabus);
			mav.addObject("dsList", dsList);
			mav.addObject("dpList", dpList); 
			mav.addObject("prasyaratList", prasyaratList); 
			mav.addObject("cpmkList", cpmkList); 
			mav.addObject("cpList", cpList); 
			mav.addObject("smmkList", smmkList);
			mav.setViewName("ReportSilabus");
			return mav;
		} 
	}

	@Secured(value = { "ROLE_Kepala", "ROLE_Pendidik", "ROLE_Peserta Didik" })
	@RequestMapping(value="/laporan/{idMK}", method = RequestMethod.GET)
	public ModelAndView showSilabusTemp(Locale locale, Model model, @PathVariable("idMK") UUID idMK){
		ModelAndView modelAndView = new ModelAndView();  
		Map<String,Object> parameterMap = new HashMap<String,Object>(); 
		 
		//inisialisasi
		List<SilabusDataReport> listObj = new ArrayList<SilabusDataReport>();
        SilabusDataReport obj = new SilabusDataReport();  
		List<Pustaka> pustakaPendukungTemp = new ArrayList<Pustaka>();
		List<Pustaka> pustakaUtamaTemp = new ArrayList<Pustaka>(); 
		List<SatManMKTemporary> satManMKTemp = new ArrayList<SatManMKTemporary>(); 
		List<CapPembMKTemporary> cpmkTemp = new ArrayList<CapPembMKTemporary>();
		List<MK> mkTemp = new ArrayList<MK>();
		
        MK mk = mkServ.findById(idMK); //dapat mk nya
        Kurikulum kur = mk.getKurikulum();// dapat kurikulum nya
        RumpunMK rumpunMK = mk.getRumpunMK();
        Silabus silabus = silabusServ.findByMK(idMK);//dapat silabusnya 
        
        List<CapPemb> cpList = subCapPembMKServ.findByMK(idMK); //dapat capaian prodi    
        
        List<CapPembMK> cpmkList = capPembMKServ.findByMK(idMK); //dapat capaian mata kuliah
        for (CapPembMK capPembMK : cpmkList) {
        	CapPembMKTemporary cpmkObjTemp = new CapPembMKTemporary();
        	cpmkObjTemp.setNamaCapPembMK(capPembMK.getNamaCapPembMK());
        	cpmkObjTemp.setDeskripsiCapPembMK(capPembMK.getDeskripsiCapPembMK());
        	cpmkTemp.add(cpmkObjTemp);
		}
        List<DetailSilabus> dsList = detailSilabusServ.findByMK(idMK);//dapat pokok bahasannya 
        
        List<DetailPustaka> dpList = detailPustakaServ.findBySilabus(silabus.getIdSilabus()); //dapat pustakanya     
        for (DetailPustaka detailPustaka : dpList) {
        	if(detailPustaka.getPustaka().getSifatPustaka().equals("U")){ //kondisi jika pustaka utama
        		System.out.println("ini utama "+detailPustaka.getPustaka().getSifatPustaka());
        		pustakaUtamaTemp.add(detailPustaka.getPustaka());
        	}
        	else if(detailPustaka.getPustaka().getSifatPustaka().equals("P")){//kondisi jika pustaka pendukung
        		System.out.println("ini pendukung "+detailPustaka.getPustaka().getSifatPustaka());
        		pustakaPendukungTemp.add(detailPustaka.getPustaka()); 
        	}
		}
        List<PrasyaratMK> prasyaratList = prasyaratMKServ.findParentMK(idMK); //dapat prasyarat MK 
        if(prasyaratList!=null){//kondisi jika prasyarat ada
        	for (PrasyaratMK prasyaratMK : prasyaratList) { 
            	mkTemp.add(prasyaratMK.getParentMK());
    		}
        }  
        else{//kondisi jika ada prasyarat tidak ada
        	MK mkNull = new MK();
        	mkNull.setKodeMK("-");
        	mkNull.setNamaMK("Tidak ada prasyarat");
        	mkTemp.add(mkNull);
        }
		List<SatManMK> smmkList = satManMKServ.findByMK(idMK);//dapat satuan manajemen MK 	 
        for (SatManMK satManMK : smmkList) {
        	SatManMKTemporary smmkTemp = new SatManMKTemporary();
        	smmkTemp.setNmSatMan(satManMK.getSatMan().getNmSatMan());
        	smmkTemp.setTingkatPemb(satManMK.getTingkatPemb());
			satManMKTemp.add(smmkTemp); 
		}
        //memasukkan data  
        obj.setCapPembMKReport(cpmkTemp); 
        obj.setCapPembReport(cpList); 
        obj.setPokokBahasanReport(dsList);
        obj.setPrasyaratMKReport(mkTemp);
        obj.setPustakaUtamaReport(pustakaUtamaTemp);
        obj.setPustakaPendukungReport(pustakaPendukungTemp);
        obj.setSatManMKReport(satManMKTemp);
        listObj.add(obj);
        
        JRDataSource jRdataSource = new JRBeanCollectionDataSource(listObj);  
        parameterMap.put("mk", mk);
        parameterMap.put("rumpunMK", rumpunMK);
        parameterMap.put("kur", kur);
        parameterMap.put("datasource", jRdataSource); 
        modelAndView = new ModelAndView("reportSilabus", parameterMap);
        
		return modelAndView; 
         
	}
}
