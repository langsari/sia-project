package com.AIS.Modul.MataKuliah.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.AIS.Modul.MataKuliah.Service.AjaxResponse;
import com.AIS.Modul.MataKuliah.Service.BentukPenilaianService;
import com.AIS.Modul.MataKuliah.Service.CapPembMKService;
import com.AIS.Modul.MataKuliah.Service.DetailPemetaanService;
import com.AIS.Modul.MataKuliah.Service.Datatable;
import com.AIS.Modul.MataKuliah.Service.DetailPustakaService;
import com.AIS.Modul.MataKuliah.Service.DetailSilabusService;
import com.AIS.Modul.MataKuliah.Service.MKService;
import com.AIS.Modul.MataKuliah.Service.MateriSilabusService;
import com.AIS.Modul.MataKuliah.Service.MetodePembService;
import com.AIS.Modul.MataKuliah.Service.PemetaanSilabusService;
import com.AIS.Modul.MataKuliah.Service.PrasyaratMKService;
import com.AIS.Modul.MataKuliah.Service.RPBentukPenilaianService;
import com.AIS.Modul.MataKuliah.Service.RPDataReport;
import com.AIS.Modul.MataKuliah.Service.RPDetailReport;
import com.AIS.Modul.MataKuliah.Service.RPMetodePembService;
import com.AIS.Modul.MataKuliah.Service.RPPerTemuService;
import com.AIS.Modul.MataKuliah.Service.RPService;
import com.AIS.Modul.MataKuliah.Service.SatManMKService;
import com.AIS.Modul.MataKuliah.Service.SilabusDataReport;
import com.AIS.Modul.MataKuliah.Service.SilabusService;
import com.AIS.Modul.MataKuliah.Service.SubCapPembMKService;
import com.sia.modul.domain.*;

@Controller
@RequestMapping(value = "/rencanapembelajaran/kelola")
public class RPController {

	@Autowired
	private MKService mkServ;
	
	@Autowired
	private CapPembMKService capPembMKServ;
	
	@Autowired
	private RPService rpServ;
	
	@Autowired
	private DetailPemetaanService detailPemetaanServ;
	
	@Autowired
	private RPPerTemuService rpPerTemuServ;
	
	@Autowired
	private SilabusService silabusServ;
	
	@Autowired
	private MetodePembService metodePembServ;
	
	@Autowired
	private BentukPenilaianService bentukServ;
	
	@Autowired
	private DetailSilabusService detailSilabusServ;
	 
	@Autowired
	private MateriSilabusService materiSilabusServ;
	
	@Autowired
	private DetailPustakaService detailPustakaServ;
	
	@Autowired
	private SubCapPembMKService subCapPembMKServ;
	
	@Autowired
	private PrasyaratMKService prasyaratMKServ;
	
	@Autowired
	private PemetaanSilabusService pemetaanSilabusServ;
	
	@Autowired
	private RPMetodePembService rpMetodePembServ;
	
	@Autowired
	private RPBentukPenilaianService rpBentukPenilaianServ;
	
	@Autowired
	private SatManMKService satManMKServ;
	
	@Secured(value = { "ROLE_Pendidik" })
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView datatable(Locale locale, Model model, HttpSession session) { 
		ModelAndView mav = new ModelAndView(); 
		List<MK> mkList = mkServ.findAll(); 
		List<MetodePemb> metodePembList = metodePembServ.findAll();
		List<BentukPenilaian> bentukList = bentukServ.findAll();
		List<DetailSilabus> dsList = detailSilabusServ.findAll();
		mav.addObject("mkList", mkList);   
		mav.addObject("metodePembList", metodePembList); 
		mav.addObject("bentukList", bentukList); 
		mav.addObject("dsList", dsList); 
		mav.setViewName("ViewRencana");  
		mav.addObject ("menuActive","Kelola Rencana Pembelajaran"); 
		return mav;
	}   
	
	@Secured(value = { "ROLE_Pendidik" })
	@RequestMapping(value = "/deletemany", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse deleteMany(@RequestParam("idRPPerTemu[]") UUID[] idRPPerTemu) {
		AjaxResponse response;
		
		for (UUID uuid : idRPPerTemu) {
			rpPerTemuServ.delete(uuid);
		}
		response = new AjaxResponse("ok","Data non-aktif",null);
        return response;
    } 
	
	@Secured(value = { "ROLE_Pendidik" })
	@RequestMapping(value="/getsilabus", method=RequestMethod.GET)
	public @ResponseBody AjaxResponse getSilabus(@RequestParam("idMK") UUID idMK){
		AjaxResponse response = new AjaxResponse(); 
		Silabus silabus = silabusServ.findByMK(idMK);
		if(silabus!=null){
			response.setData(silabus);
		}
		else{
			response.setMessage("Silabus tidak ditemukan");
			response.setData(null);
		}
		return response;
	}
	
	@Secured(value = { "ROLE_Pendidik" })
	@RequestMapping(value="/getrp", method=RequestMethod.GET)
	public @ResponseBody AjaxResponse getRP(@RequestParam("idSilabus") UUID idSilabus){
		AjaxResponse response = new AjaxResponse();  
		RP rp = rpServ.findBySilabus(idSilabus);
		if(rp!=null){
			response.setData(rp);
		}
		else{
			response.setData(null);
		} 
		return response;
	}

	@Secured(value = { "ROLE_Pendidik" })
	@RequestMapping(value="/editrp", method = RequestMethod.POST)
	public @ResponseBody AjaxResponse editRP(@RequestParam("idRP") UUID idRP,
			@RequestParam("idSilabus") UUID idSilabus,
			@RequestParam("bahanKajian") String bahanKajian) {
		AjaxResponse response = null; 
		RP rp = rpServ.findById(idRP); 
		Silabus silabus = silabusServ.findById(idSilabus);
		rp.setBahanKajian(bahanKajian);  
		rp.setSilabus(silabus);
		if(rpServ.save(rp)==null){
			response = new AjaxResponse("error", "RP tidak ditemukan", null);
		}
		else{
			response = new AjaxResponse("ok", "RP diperbaharui", rp); 
		}
		return response;
	}

	@Secured(value = { "ROLE_Pendidik" })
	@RequestMapping(value = "/simpanrp", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse simpan(@RequestParam("idSilabus") UUID idSilabus, 
    		@RequestParam("bahanKajian") String bahanKajian) {
		AjaxResponse response = new AjaxResponse();   
		Silabus silabus = silabusServ.findById(idSilabus); 
		RP rpNew = new RP();
		rpNew.setBahanKajian(bahanKajian);
		rpNew.setSilabus(silabus);
		rpServ.save(rpNew); 
		response.setData(rpNew);
        return response; 
    } 
	
	@Secured(value = { "ROLE_Pendidik" })
	@RequestMapping(value="/getrppertemu", method=RequestMethod.GET)
	public @ResponseBody AjaxResponse getRPPerTemu(@RequestParam("idRP") UUID idRP){
		AjaxResponse response = new AjaxResponse(); 
		List<RPPerTemu> rpptList = rpPerTemuServ.findByRP(idRP);  
		if(rpptList!=null){
			response.setMessage("rpPerTemu");
			response.setData(rpptList);
		} 
		return response;
	}
	
	@Secured(value = { "ROLE_Pendidik" })
	@RequestMapping(value="/editrppertemu", method=RequestMethod.GET)
	public @ResponseBody AjaxResponse editRPPerTemu(@RequestParam("idRPPerTemu") UUID idRPPerTemu){
		AjaxResponse response = new AjaxResponse(); 
		RPPerTemu rppt = rpPerTemuServ.findById(idRPPerTemu);  
		if(rppt!=null){
			response.setMessage("rpPerTemu");
			response.setData(rppt);
		} 
		return response;
	}

	@Secured(value = { "ROLE_Pendidik" })
	@RequestMapping(value="/simpanrppertemu", method=RequestMethod.POST)
	public @ResponseBody AjaxResponse simpanRPPerTemu(@RequestParam("idRPPerTemu") UUID idRPPerTemu, @RequestParam("idRP") UUID idRP,
			@RequestParam("mingguPemb") int mingguPemb, @RequestParam("waktuPemb") int waktuPemb,
			@RequestParam("indikatorPenilaian") String indikatorPenilaian,
			@RequestParam("bobotPenilaian") double bobotPenilaian){
		AjaxResponse response = new AjaxResponse(); 
		 
		RPPerTemu rppt=null;
		
		RP rp = rpServ.findById(idRP);   
		if(idRPPerTemu==null){
			rppt = new RPPerTemu();
		}
		else{
			rppt = rpPerTemuServ.findById(idRPPerTemu);
		} 
		rppt.setBobotPenilaian(bobotPenilaian);
		if(indikatorPenilaian==""){
			rppt.setIndikatorPenilaian(null);  
		}
		else{
			rppt.setIndikatorPenilaian(indikatorPenilaian);  
		}
		rppt.setMingguPembKe(mingguPemb);
		rppt.setWaktuPemb(waktuPemb);
		rppt.setRp(rp); 
		
		rpPerTemuServ.save(rppt);
		
		response.setData(rppt);
		return response;
		
	} 

	@Secured(value = { "ROLE_Pendidik" })
	@RequestMapping(value="/simpanmateri", method=RequestMethod.POST)
	public @ResponseBody AjaxResponse simpanMateri(@RequestParam("idRPPerTemu") UUID idRPPerTemu,
			@RequestParam("idDetailSilabus") UUID idDetailSilabus){
		AjaxResponse response = new AjaxResponse(); 
		DetailSilabus detailSilabus = detailSilabusServ.findById(idDetailSilabus);
		RPPerTemu rppt = rpPerTemuServ.findById(idRPPerTemu);
		MateriSilabus mp = new MateriSilabus();
		mp.setRpPerTemu(rppt);
		mp.setDetailSilabus(detailSilabus);
		materiSilabusServ.save(mp);
		response.setData(mp);
		return response;
	} 

	@Secured(value = { "ROLE_Pendidik" })
	@RequestMapping(value="/simpanmetode", method=RequestMethod.POST)
	public @ResponseBody AjaxResponse simpanMetode(@RequestParam("idRPPerTemu") UUID idRPPerTemu,
			@RequestParam("idMetodePemb") UUID idMetodePemb){
		AjaxResponse response = new AjaxResponse(); 
		MetodePemb metodePemb = metodePembServ.findById(idMetodePemb); 
		RPPerTemu rppt = rpPerTemuServ.findById(idRPPerTemu);
		RPMetodePemb mp = new RPMetodePemb();
		mp.setRpPerTemu(rppt);
		mp.setMetodePemb(metodePemb);
		rpMetodePembServ.save(mp);
		response.setData(mp);
		return response;
	} 

	@Secured(value = { "ROLE_Pendidik" })
	@RequestMapping(value="/simpanbentuk", method=RequestMethod.POST)
	public @ResponseBody AjaxResponse simpanBentuk(@RequestParam("idRPPerTemu") UUID idRPPerTemu,
			@RequestParam("idBentukPenilaian") UUID idBentuk){ 
		AjaxResponse response = new AjaxResponse();  
		RPPerTemu rppt = rpPerTemuServ.findById(idRPPerTemu);
		RPBentukPenilaian mp = new RPBentukPenilaian();
		if(idBentuk!=null){
			BentukPenilaian bp = bentukServ.findById(idBentuk);
			mp.setBentukPenilaian(bp);
		}
		else{
			mp.setBentukPenilaian(null);
		}
		mp.setRpPerTemu(rppt); 
		rpBentukPenilaianServ.save(mp);
		response.setData(mp);
		return response;
	} 

	@Secured(value = { "ROLE_Pendidik" })
	@RequestMapping(value="/deletemateri", method=RequestMethod.POST)
	public @ResponseBody AjaxResponse hapusMateri(@RequestParam("idMateriSilabus") UUID idMateriSilabus){
		AjaxResponse response = new AjaxResponse();  
		materiSilabusServ.delete(idMateriSilabus);
		response.setMessage("Data sudah dihapus");
		return response;
	}

	@Secured(value = { "ROLE_Pendidik" })
	@RequestMapping(value="/deletemetode", method=RequestMethod.POST)
	public @ResponseBody AjaxResponse hapusMetode(@RequestParam("idRPMetodePemb") UUID idRPMetodePemb){
		AjaxResponse response = new AjaxResponse();  
		rpMetodePembServ.delete(idRPMetodePemb);
		response.setMessage("Data sudah dihapus");
		return response;
	}
	
	@Secured(value = { "ROLE_Pendidik" })
	@RequestMapping(value="/getmateri", method=RequestMethod.GET)
	public @ResponseBody AjaxResponse getMateri(@RequestParam("idRPPerTemu") UUID idRPPerTemu){
		AjaxResponse response = new AjaxResponse(); 
		List<MateriSilabus> msList = materiSilabusServ.findByRPPerTemu(idRPPerTemu);  
		response.setData(msList);
		return response;
	}
	
	@Secured(value = { "ROLE_Pendidik" })
	@RequestMapping(value="/getmetode", method=RequestMethod.GET)
	public @ResponseBody AjaxResponse getMetode(@RequestParam("idRPPerTemu") UUID idRPPerTemu){
		AjaxResponse response = new AjaxResponse();  
		List<RPMetodePemb> rpmbList = rpMetodePembServ.findByRPPerTemu(idRPPerTemu);   
		response.setData(rpmbList);
		return response;
	}
	
	@Secured(value = { "ROLE_Pendidik" })
	@RequestMapping(value="/getbentuk", method=RequestMethod.GET)
	public @ResponseBody AjaxResponse getBentuk(@RequestParam("idRPPerTemu") UUID idRPPerTemu){
		AjaxResponse response = new AjaxResponse();  
		List<RPBentukPenilaian> rpbpList = rpBentukPenilaianServ.findByRPPerTemu(idRPPerTemu);   
		response.setData(rpbpList);
		return response;
	}
	
	@Secured(value = { "ROLE_Pendidik", "ROLE_Peserta Didik" })
	@RequestMapping(value="/laporan", method = RequestMethod.GET)
	public ModelAndView showSilabus(Locale locale, Model model, HttpSession session) {  
		ModelAndView mav = new ModelAndView();
		List<MK> mkList = mkServ.findAll(); 
		String message = null;
		mav.addObject("message", message);
		mav.addObject("mkList", mkList);
		mav.setViewName("DaftarReportRencanaPembelajaran");
		return mav;
	}
	
	@Secured(value = { "ROLE_Pendidik", "ROLE_Peserta Didik" })
	@RequestMapping(value="/laporan", method = RequestMethod.POST)
	public ModelAndView getRPElement(Locale locale, Model model, @RequestParam("idMK") UUID idMK, HttpSession session) {  
		ModelAndView mav = new ModelAndView(); 
		MK mk2 = mkServ.findById(idMK); //dapat objek MK
		Silabus silabus = silabusServ.findByMK(idMK);//dapat silabusnya
		if(silabus == null){
			List<MK> mkList = mkServ.findAll(); 
			mav.addObject("mkList", mkList);
			String message = "Rencana pembelajaran mata kuliah tidak ada";
			mav.addObject("message", message);
			mav.setViewName("DaftarReportRencanaPembelajaran");
			return mav;
		}
		else { 
			RP rp = rpServ.findBySilabus(silabus.getIdSilabus());//dapat bahan kajian
			List<DetailSilabus> dsList = detailSilabusServ.findByMK(idMK);//dapat pokok bahasannya
			List<DetailPustaka> dpList = detailPustakaServ.findBySilabus(silabus.getIdSilabus()); //dapat pustakanya     
			List<CapPembMK> cpmkList = capPembMKServ.findByMK(idMK); //dapat capaian mata kuliah
			List<CapPemb> cpList = subCapPembMKServ.findByMK(idMK); //dapat capaian prodi   
			List<PrasyaratMK> prasyaratList = prasyaratMKServ.findParentMK(idMK); //dapat MK prasyarat 
			List<RPMetodePemb> rpmbList = rpMetodePembServ.findAll();//dapat metode pembelajaran
			List<RPBentukPenilaian> rpbpList = rpBentukPenilaianServ.findAll();//dapat bentuk penilaian
			List<SatManMK> smmkList = satManMKServ.findByMK(idMK);//dapat satuan manajemen MK 	 
			try{
				List<RPPerTemu> rpPerTemuList = rpPerTemuServ.findByRP(rp.getIdRP());//dapat RP Per Temu 
				List<MateriSilabus> msNewList = new ArrayList<MateriSilabus>();
				List<PemetaanSilabus> psNewList = new ArrayList<PemetaanSilabus>();
				
				HashMap<UUID, Boolean> hashMsList = new HashMap<UUID, Boolean>();
				HashMap<UUID, Boolean> hashPsList = new HashMap<UUID, Boolean>();
				
				//materi silabus (detail silabus [punya pokok bahasan di dalamnya] & rp per pertemuan)
				//mendapatkan materi silabus untuk tiap pertemuan
				for(RPPerTemu rppt : rpPerTemuList){ 
					List<MateriSilabus> msList = materiSilabusServ.findByRPPerTemu(rppt.getIdRPPerTemu());//dapat materi pembelajaran
					for (MateriSilabus ms : msList) {
						if(!hashMsList.containsKey(ms.getIdMateriSilabus())) {					
							hashMsList.put(ms.getIdMateriSilabus(), true);
							msNewList.add(ms);
						}
					}
				}
				
				//pemetaan silabus (detail silabus & cap pemb mk)
				//mendapatkan pemetaan silabus untuk tiap materi silabus
				for(MateriSilabus ms : msNewList){
					List<PemetaanSilabus> psList = pemetaanSilabusServ.findByDetailSilabus(ms.getDetailSilabus().getIdDetailSilabus());//dapat capaian pembelajaran mata kuliah
					for (PemetaanSilabus ps : psList) {
						if(!hashPsList.containsKey(ps.getIdPemetaanSilabus())) {
							hashPsList.put(ps.getIdPemetaanSilabus(), true);
							psNewList.add(ps);
						}
					}
				}
				
				HashMap<UUID, List<CapPembMK>> hashCPMKPerTemu = new HashMap<UUID, List<CapPembMK>>();
				
				//mendapatkan capaian pembelajaran MK yang didapatkan dari menyamakan pemetaan silabu dan materi silabus
				for (RPPerTemu rppt : rpPerTemuList) {
					HashMap<UUID, Boolean> hashCPMKSementara = new HashMap<UUID, Boolean>();
					List<CapPembMK> listCPMKSementara = new ArrayList<CapPembMK>();
					for (PemetaanSilabus ps : psNewList) {
						for (MateriSilabus ms : msNewList) {
							if(ms.getRpPerTemu().getIdRPPerTemu() == rppt.getIdRPPerTemu() 
									&& ms.getDetailSilabus().getIdDetailSilabus() == ps.getDetailSilabus().getIdDetailSilabus()) {
								if(!hashCPMKSementara.containsKey(ps.getCapPembMK().getIdCapPembMK())) {
									hashCPMKSementara.put(ps.getCapPembMK().getIdCapPembMK(), true);
									listCPMKSementara.add(ps.getCapPembMK());
								}
							}
						}
					}
					//rp per pertemuan yang ke-IdRPPerTemu, punya cap pemb mk yang mana
					hashCPMKPerTemu.put(rppt.getIdRPPerTemu(), listCPMKSementara); 
				}
				 
				mav.addObject("rp", rp); 
				mav.addObject("msNewList", msNewList);
				mav.addObject("psNewList", psNewList);
				mav.addObject("rpPerTemuList", rpPerTemuList);
				mav.addObject("hashCPMKPerTemu", hashCPMKPerTemu);
			}
			catch(Exception e){  
			}
			mav.addObject("mk2", mk2);
			mav.addObject("silabus", silabus);
			mav.addObject("dsList", dsList);
			mav.addObject("dpList", dpList);  
			mav.addObject("cpmkList", cpmkList); 
			mav.addObject("cpList", cpList); 
			mav.addObject("prasyaratList", prasyaratList);
			mav.addObject("rpmbList", rpmbList);
			mav.addObject("rpbpList", rpbpList);
			mav.addObject("smmkList", smmkList);
			mav.setViewName("ReportRencanaPembelajaran");
			return mav;
		}   
	}
	
	@Secured(value = { "ROLE_Pendidik", "ROLE_Peserta Didik" })
	@RequestMapping(value="/laporan/{idMK}", method = RequestMethod.GET)
	public ModelAndView getRPReport(Locale locale, Model model, @PathVariable("idMK") UUID idMK) {  
		//inisialisasi
		ModelAndView mav = new ModelAndView();
		Map<String,Object> parameterMap = new HashMap<String,Object>(); 
		List<RPDataReport> listObj = new ArrayList<RPDataReport>();  
		List<MateriSilabus> msNewList = new ArrayList<MateriSilabus>();
		List<PemetaanSilabus> psNewList = new ArrayList<PemetaanSilabus>();
		HashMap<UUID, Boolean> hashMsList = new HashMap<UUID, Boolean>();
		HashMap<UUID, Boolean> hashPsList = new HashMap<UUID, Boolean>();
		RPDataReport obj = new RPDataReport();   
		HashMap<UUID, List<CapPembMK>> hashCPMKPerTemu = new HashMap<UUID, List<CapPembMK>>();
		List<DetailSilabus> dsListTemp;
		List<MetodePemb> metodePembTemp;
		List<BentukPenilaian> bentukTemp;
		List<RPDetailReport> rpdrList = new ArrayList<RPDetailReport>();
        
		MK mk = mkServ.findById(idMK); //dapat objek MK
		Kurikulum kur = mk.getKurikulum();
		RumpunMK rumpunMK = mk.getRumpunMK();
		Silabus silabus = silabusServ.findByMK(idMK);//dapat silabusnya
		RP rp = rpServ.findBySilabus(silabus.getIdSilabus());//dapat bahan kajian
		List<DetailSilabus> dsList = detailSilabusServ.findByMK(idMK);//dapat pokok bahasannya 
		List<RPPerTemu> rpPerTemuList = rpPerTemuServ.findByRP(rp.getIdRP());//dapat RP Per Temu 
		List<RPMetodePemb> rpmbList = rpMetodePembServ.findAll();//dapat metode pembelajaran
		List<RPBentukPenilaian> rpbpList = rpBentukPenilaianServ.findAll();//dapat bentuk penilaian 
		
		//materi silabus (detail silabus [punya pokok bahasan di dalamnya] & rp per pertemuan)
		//mendapatkan materi silabus untuk tiap pertemuan
		for(RPPerTemu rppt : rpPerTemuList){ 
			List<MateriSilabus> msList = materiSilabusServ.findByRPPerTemu(rppt.getIdRPPerTemu());//dapat materi pembelajaran
			for (MateriSilabus ms : msList) {
				if(!hashMsList.containsKey(ms.getIdMateriSilabus())) {					
					hashMsList.put(ms.getIdMateriSilabus(), true);
					msNewList.add(ms);
				}
			}
		}
		
		//pemetaan silabus (detail silabus & cap pemb mk)
		//mendapatkan pemetaan silabus untuk tiap materi silabus
		//digunakan untuk mengetahui cap pemb mk mana yang dipakai untuk materi pokok bahasan
		for(MateriSilabus ms : msNewList){
			List<PemetaanSilabus> psList = pemetaanSilabusServ.findByDetailSilabus(ms.getDetailSilabus().getIdDetailSilabus());//dapat capaian pembelajaran mata kuliah
			for (PemetaanSilabus ps : psList) {
				if(!hashPsList.containsKey(ps.getIdPemetaanSilabus())) {
					hashPsList.put(ps.getIdPemetaanSilabus(), true);
					psNewList.add(ps);
				}
			}
		}
		
		//mendapatkan capaian pembelajaran MK yang didapatkan dari menyamakan pemetaan silabu dan materi silabus
		for (RPPerTemu rppt : rpPerTemuList) {
			HashMap<UUID, Boolean> hashCPMKSementara = new HashMap<UUID, Boolean>();
			List<CapPembMK> listCPMKSementara = new ArrayList<CapPembMK>();
			for (PemetaanSilabus ps : psNewList) {
				for (MateriSilabus ms : msNewList) {
					if(ms.getRpPerTemu().getIdRPPerTemu() == rppt.getIdRPPerTemu() 
							&& ms.getDetailSilabus().getIdDetailSilabus() == ps.getDetailSilabus().getIdDetailSilabus()) {
						if(!hashCPMKSementara.containsKey(ps.getCapPembMK().getIdCapPembMK())) {
							hashCPMKSementara.put(ps.getCapPembMK().getIdCapPembMK(), true);
							listCPMKSementara.add(ps.getCapPembMK());
						}
					}
				}
			}
			//rp per pertemuan yang ke-IdRPPerTemu, punya cap pemb mk yang mana
			hashCPMKPerTemu.put(rppt.getIdRPPerTemu(), listCPMKSementara); 
		} 
		
		//assign data-data untuk ditampilkan di report
		for (RPPerTemu rppt : rpPerTemuList) {
			//inisialisasi memasukkan data ke list rp per pertemuan
			RPDetailReport rpdr = new RPDetailReport();
			dsListTemp = new ArrayList<DetailSilabus>();
			metodePembTemp = new ArrayList<MetodePemb>();
			bentukTemp = new ArrayList<BentukPenilaian>();  
//			rpdr.setRPPerTemu(rppt);//memasukkan minggu keberapa di rp per temu
			rpdr.setMingguKe(rppt.getMingguPembKe());
			if(rppt.getIndikatorPenilaian()!=null){
				rpdr.setIndikatorPenilaian(rppt.getIndikatorPenilaian());
			}
			else{
				rpdr.setIndikatorPenilaian("Tidak ada");
			}
			rpdr.setBobotPenilaian(rppt.getBobotPenilaian());
			for(Map.Entry<UUID, List<CapPembMK>> entry : hashCPMKPerTemu.entrySet()){
				if(rppt.getIdRPPerTemu()==entry.getKey()){
					rpdr.setCapPembMKReport(entry.getValue());//memasukkan cap pemb mk di rp per temu
				}
			}
			for(MateriSilabus ms : msNewList){ 
				if(ms.getRpPerTemu().getIdRPPerTemu()==rppt.getIdRPPerTemu()){
					dsListTemp.add(ms.getDetailSilabus());//memasukkan pokok bahasan di rp per temu
				}
			}
			rpdr.setDetailSilabusReport(dsListTemp);//memasukkan ke list pokok bahasan di rp per temu 
			for(RPMetodePemb rpmb : rpmbList){
				if(rpmb.getRpPerTemu().getIdRPPerTemu()==rppt.getIdRPPerTemu()){
					metodePembTemp.add(rpmb.getMetodePemb());
				}
			}
			rpdr.setMetodePembReport(metodePembTemp);//memasukkan ke list metode pembelajaran di rp per temu
			for(RPBentukPenilaian rpbp : rpbpList){
				if(rpbp.getRpPerTemu().getIdRPPerTemu()==rppt.getIdRPPerTemu()){
					if(rpbp.getBentukPenilaian()!=null){ 
						bentukTemp.add(rpbp.getBentukPenilaian()); 
					} 
				}
			}
			rpdr.setBentukPenilaianReport(bentukTemp);//memasukkan ke list bentuk penilaian di rp per temu
			System.out.println(bentukTemp.size());
			rpdrList.add(rpdr);
			
		}  
		obj.setPokokBahasanReport(dsList);
		obj.setRpDetailReport(rpdrList);
		listObj.add(obj);
		JRDataSource jRdataSource = new JRBeanCollectionDataSource(listObj);
		parameterMap.put("mk", mk); 
        parameterMap.put("kur", kur);
        parameterMap.put("rp", rp);
        parameterMap.put("rumpunMK", rumpunMK);
        parameterMap.put("datasource", jRdataSource); 
        mav = new ModelAndView("rencanaPembelajaran", parameterMap);
		return mav; 
	}
}
