package com.its.sia.controller;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sia.modul.domain.AnggotaRombel;
import com.sia.modul.domain.Pd;
import com.sia.modul.domain.Pemb;
import com.sia.main.domain.Pengguna;
import com.sia.main.domain.PeranPengguna;
import com.sia.modul.domain.Rombel;
import com.sia.modul.domain.SatMan;
import com.its.sia.service.AjaxResponse;
import com.its.sia.service.AnggotaRombelService;
import com.its.sia.service.Datatable;
import com.its.sia.service.PdService;
import com.its.sia.service.RombelService;
import com.its.sia.service.SatManService;

@Controller
@Secured(value = { "ROLE_Tenaga Kependidikan" })
@RequestMapping(value = "/rombel")
public class RombelController {

	private static final Logger logger = LoggerFactory.getLogger(RombelController.class);
	
	@Autowired
	private RombelService rombelService;
	
	@Autowired
	private AnggotaRombelService anggotaRombelService;
	
	@Autowired
	private PdService pdService;
	
	@Autowired
	private SatManService satManService;
	
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView datatable(HttpSession session, Locale locale, Model model) {
		ModelAndView mav = new ModelAndView();
		
		PeranPengguna peranPengguna = (PeranPengguna)session.getAttribute("userRoleSession");
		Rombel rombel = new Rombel();
		List<Integer> listAngkatan = pdService.getAngkatan();
		List<SatMan> listSatMan = satManService.listChild(peranPengguna.getSatMan().getIdSatMan());
		if(peranPengguna.getSatMan().isaSatManProdi()==false)
		{
			listSatMan = satManService.get("aSatManProdi = true","nmSatMan asc");
		}
		else
		{
			 listSatMan = satManService.get("idSatMan = '"+peranPengguna.getSatMan().getIdSatMan()+"'","nmSatMan asc");
		}
		mav.setViewName("MasterRombel");
		mav.addObject("rombel", rombel);
		mav.addObject("listAngkatan", listAngkatan);
		mav.addObject("listSatMan", listSatMan);
		mav.addObject ("menuActive","Kelola Rombongan Belajar");
		
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
			@RequestParam("a_rombel_terhapus") String a_rombel_terhapus
            ) {

		PeranPengguna peranPengguna = (PeranPengguna)session.getAttribute("userRoleSession");
		
		String filter = "CAST( rombel.aRombelTerhapus as string) LIKE '%"+a_rombel_terhapus+"%' and satMan.idSatMan='"+peranPengguna.getSatMan().getIdSatMan()+"'";
		Datatable rombelDatatable = rombelService.getdatatable(sEcho, iDisplayLength, iDisplayStart, iSortCol_0, sSortDir_0, sSearch,filter);
		return rombelDatatable;
	}
	
	
	@RequestMapping(value = "/simpan", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse simpan(HttpSession session, @Valid @ModelAttribute("smt") Rombel rombel,
            BindingResult result, Map<String, Object> model) {
		
		PeranPengguna peranPengguna = (PeranPengguna)session.getAttribute("userRoleSession");
		AjaxResponse response = new AjaxResponse();
		SatMan satMan = new SatMan();
		satMan.setIdSatMan(peranPengguna.getSatMan().getIdSatMan());
		satMan.setaSatManAktif(peranPengguna.getSatMan().isaSatManAktif());
		satMan.setIdSatManInduk(peranPengguna.getSatMan().getSatManInduk().getIdSatMan());
		satMan.setNmSatMan(peranPengguna.getSatMan().getNmSatMan());
		satMan.setSatManHasKurikulum(peranPengguna.getSatMan().isSatManHasKurikulum());
		
		rombel.setSatMan(satMan);
        if (result.hasErrors()) {
        	response.setStatus("error");
        	List<FieldError> fieldError = result.getFieldErrors();
        	String message ="";
    		if(fieldError.get(0).isBindingFailure()) message += "Salah satu input tiak valid";
    		else message += fieldError.get(0).getDefaultMessage();
        	for(int i=1;i<fieldError.size();i++)
        	{
        		if(fieldError.get(i).isBindingFailure()) message += "Salah satu input tiak valid";
        		else message += "<br/>"+fieldError.get(i).getDefaultMessage();
        	}
        	response.setMessage(message);
        	response.setData(fieldError);
            return response;
        }
        response.setData(rombelService.save(rombel));
        if(response.getData()!=null) response.setMessage("Data berhasil disimpan");
        else 
        {
        	response.setStatus("error");
        	response.setMessage("Rombel sudah ada");
        }
        return response;
    }
	
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse edit(HttpSession session, @RequestParam("idRombel") UUID idRombel) {
		AjaxResponse response;
		Rombel rombel = rombelService.getById(idRombel);
		if(rombel == null) response = new AjaxResponse("error","Data tidak ditemukan",null);
		else response = new AjaxResponse("ok","Data ditemukan",rombel);
        return response;
    }
	
	
	@RequestMapping(value = "/deletemany", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse deleteMany(HttpSession session, @RequestParam("idRombel[]") UUID[] idRombel) {
		AjaxResponse response;
		for (UUID uuid : idRombel) {
			rombelService.delete(uuid);
		}
		response = new AjaxResponse("ok","Data dihapus",null);
        return response;
    }
	
	
	@RequestMapping(value = "/jsonanggotarombel", method = RequestMethod.POST)
	public @ResponseBody Datatable jsonanggotarombel(
			HttpSession session,
			@RequestParam("sEcho") String sEcho, 
			@RequestParam("iDisplayLength") int iDisplayLength,
            @RequestParam("iSortCol_0") int iSortCol_0,
            @RequestParam("sSortDir_0") String sSortDir_0,
            @RequestParam("sSearch") String sSearch,
			@RequestParam("iDisplayStart") int iDisplayStart,
			@RequestParam("idRombel") UUID idRombel,
			@RequestParam("angkatan_pd") Integer angkatan_pd
            ) {
		String filter = "pd.aPdTerhapus = false";
		if(idRombel!=null) filter = "rombel.idRombel = '"+idRombel.toString()+"' AND "+filter;
		if(angkatan_pd!=null) filter = "pd.angkatanPd = "+angkatan_pd.toString()+" AND "+filter;
		Datatable rombelDatatable = anggotaRombelService.getdatatable(sEcho, iDisplayLength, iDisplayStart, iSortCol_0, sSortDir_0, sSearch,filter);
		return rombelDatatable;
	}
	
	
	@RequestMapping(value = "/jsonpesertadidik", method = RequestMethod.POST)
	public @ResponseBody Datatable jsonpesertadidik(
			HttpSession session,
			@RequestParam("sEcho") String sEcho, 
			@RequestParam("iDisplayLength") int iDisplayLength,
            @RequestParam("iSortCol_0") int iSortCol_0,
            @RequestParam("sSortDir_0") String sSortDir_0,
            @RequestParam("sSearch") String sSearch,
			@RequestParam("iDisplayStart") int iDisplayStart,
			@RequestParam("angkatan_pd") Integer angkatan_pd,
			@RequestParam("idSatMan") UUID idSatMan
            ) {
		
		String filter = "pd.aPdTerhapus = false and satMan.idSatMan='"+idSatMan+"'";
		if(angkatan_pd!=null) filter = "pd.angkatanPd = "+angkatan_pd.toString()+" AND "+filter;
		Datatable rombelDatatable = pdService.getAnggotaRombel(sEcho, iDisplayLength, iDisplayStart, iSortCol_0, sSortDir_0, sSearch,filter);
		return rombelDatatable;
	}
	
	
	@RequestMapping(value = "/tambahkan", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse tambahkan(HttpSession session,@RequestParam("idRombel") UUID idRombel,@RequestParam("idPd") UUID idPd) {
		AjaxResponse response = new AjaxResponse();
		AnggotaRombel anggotaRombel = new AnggotaRombel();
		Pd pd = pdService.getById(idPd);
		if(pd==null) return new AjaxResponse("error","Mahasiswa tidak diketahui",null);
		Rombel rombel = rombelService.getById(idRombel);
		if(rombel==null) return new AjaxResponse("error","Rombel tidak diketahui",null);
		
		anggotaRombel.setPd(pd);
		anggotaRombel.setRombel(rombel);
		response.setData(anggotaRombelService.save(anggotaRombel));
		response.setMessage("Penambahan sukses");
		if(response.getData()==null)
		{
			return new AjaxResponse("error", "Mahasiswa sudah ada dalam rombongan belajar", null);
		}
		else return response;
    }
	
	
	@RequestMapping(value = "/tambahkanmany", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse tambahkanmany(HttpSession session,@RequestParam("idRombel") UUID idRombel,@RequestParam("idPd[]") UUID[] idPd) {
		AjaxResponse response = new AjaxResponse();
		Rombel rombel = rombelService.getById(idRombel);
		if(rombel==null) return new AjaxResponse("error","Rombel tidak diketahui",null);
		for (UUID uuid : idPd) {
			AnggotaRombel anggotaRombel = new AnggotaRombel();
			Pd pd = pdService.getById(uuid);
			if(pd!=null && pd.isaPdTerhapus()==false)
			{
				anggotaRombel.setPd(pd);
				anggotaRombel.setRombel(rombel);
				anggotaRombelService.save(anggotaRombel);
			}
		}
		response.setData(null);
		response.setMessage("Penambahan sukses");
		return response;
    }
	
	
	@RequestMapping(value = "/deleteanggotarombel", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse deleteanggotarombel(HttpSession session,@RequestParam("idAnggotaRombel[]") UUID[] idAnggotaRombel) {
		AjaxResponse response;
		for (UUID uuid : idAnggotaRombel) {
			anggotaRombelService.delete(uuid);
		}
		response = new AjaxResponse("ok","Data dihapus",null);
        return response;
    }
	
	
	@RequestMapping(value = "/anggota/{idRombel}", method = RequestMethod.GET)
	public ModelAndView peserta(HttpSession session,Locale locale, @PathVariable("idRombel") String idRombel) {
		//List<Pd> listPd = pdService.get("");	
		List<AnggotaRombel> listAnggotaRombel = anggotaRombelService.get("rombel.idRombel = '"+idRombel+"'","pd.nimPd asc");
		Rombel rombel = rombelService.getById(UUID.fromString(idRombel));
		ModelAndView mav = new ModelAndView();
		mav.setViewName("AnggotaRombel");
		mav.addObject("listAnggotaRombel", listAnggotaRombel);
		mav.addObject("rombel", rombel);
		mav.addObject ("menuActive","Kelola Pendamping Akademik");
		
		return mav;
	}
	
}
