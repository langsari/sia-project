package com.its.sia.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.PageContext;
import javax.validation.Valid;
import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
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

import com.sia.modul.domain.AnggotaRombel;
import com.sia.modul.domain.Krs;
import com.sia.modul.domain.Kurikulum;
import com.sia.modul.domain.MK;
import com.sia.modul.domain.Pd;
import com.sia.modul.domain.Pemb;
import com.sia.modul.domain.PendidikPengajar;
import com.sia.modul.domain.Ptk;
import com.sia.modul.domain.Rombel;
import com.sia.modul.domain.SatMan;
import com.sia.modul.domain.TglSmt;
import com.its.sia.service.AjaxResponse;
import com.its.sia.service.AnggotaRombelService;
import com.its.sia.service.Datatable;
import com.its.sia.service.KrsService;
import com.its.sia.service.KurikulumService;
import com.its.sia.service.MKService;
import com.its.sia.service.PdService;
import com.its.sia.service.PembService;
import com.its.sia.service.PendidikPengajarService;
import com.its.sia.service.SatManService;
import com.its.sia.service.TglSmtService;

@Controller
@Secured(value = { "ROLE_Admin" })
@RequestMapping(value = "/pengambilankrs")
public class KrsController {

	private static final Logger logger = LoggerFactory.getLogger(KrsController.class);
	
	@Autowired
	private KrsService krsService;
	@Autowired
	private TglSmtService tglSmtService;
	@Autowired
	private PdService pdService;
	@Autowired
	private PembService pembService;
	@Autowired
	private AnggotaRombelService anggotaRombelService;
	
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView datatable(HttpSession session, Locale locale, Model model) {
		ModelAndView mav = new ModelAndView();
		
		List<TglSmt> listTglSmt = tglSmtService.get("tglSmt.aTglSmtTerhapus = false","thnAjaran.thnThnAjaran asc, smt.nmSmt asc");
		mav.setViewName("MasterKrs");
		mav.addObject("listTglSmt", listTglSmt);
		 
		mav.addObject ("menuActive","Monitoring Pengambilan KRS");
		
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
			@RequestParam("id_tgl_smt") UUID id_tgl_smt,
			@RequestParam("aKrsTerhapus") String aKrsTerhapus,
			@RequestParam("aKrsDisetujui") String aKrsDisetujui,
			@RequestParam("aKrsBatal") String aKrsBatal
            ) {
		
		String filter = "CAST (krs.aKrsTerhapus as string) LIKE '%"+aKrsTerhapus+"%' and CAST (krs.aKrsDisetujui as string) LIKE '%"+aKrsDisetujui+"%' and CAST (krs.aKrsBatal as string) LIKE '%"+aKrsBatal+"%'";
		if(id_tgl_smt!=null) filter+= " AND tglSmt.idTglSmt = '"+id_tgl_smt+"'";
		Datatable thnAjaranDatatable = krsService.getdatatable(sEcho, iDisplayLength, iDisplayStart, iSortCol_0, sSortDir_0, sSearch,filter);
		return thnAjaranDatatable;
	}
	
	
	@RequestMapping(value = "/simpan", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse simpan(HttpSession session,@Valid @ModelAttribute("Krs") Krs krs, 
            BindingResult result, Map<String, Object> model) {
		
		AjaxResponse response = new AjaxResponse();
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
        response.setData(krsService.save(krs));
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
    public @ResponseBody AjaxResponse edit(HttpSession session,@RequestParam("idKrs") UUID idKrs) {
		
		AjaxResponse response;
		Krs krs = krsService.getById(idKrs);
		if(krs == null) response = new AjaxResponse("error","Data tidak ditemukan",null);
		else response = new AjaxResponse("ok","Data ditemukan",krs);
        return response;
    }
		
	
	@RequestMapping(value = "/deletepdinpemb", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse deletePdInPemb(HttpSession session,@RequestParam("idPd[]") UUID[] idPd, @RequestParam("idPemb") UUID idPemb) {
		
		AjaxResponse response;
		if(idPemb == null) return new AjaxResponse("error","Pembelajaran tidak diketahui",null);
		for (UUID uuid : idPd) {
			krsService.deletePdInKrs(uuid, idPemb);
		}
		response = new AjaxResponse("ok","Data dihapus",null);
        return response;
    }
	
	
	@RequestMapping(value = "/tambahrombel", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse tambahrombel(HttpSession session,@RequestParam("idRombel[]") UUID[] idRombel, @RequestParam("idPemb") UUID idPemb) {
		
		AjaxResponse response;
		if(idPemb == null) return new AjaxResponse("error","Pembelajaran tidak diketahui",null);
		Pemb pemb = pembService.getById(idPemb);
		Long jumlahAnggota = (long) 0;
		for (UUID uuid : idRombel) {
			jumlahAnggota += anggotaRombelService.countAnggota("rombel.idRombel='"+uuid+"'");
		}
		
		if((pemb.getKuota()-krsService.getPeserta("pemb.idPemb ='"+pemb.getIdPemb()+"' and krs.aKrsTerhapus = false and krs.aKrsBatal = false").size())<jumlahAnggota) return new AjaxResponse("error","Peserta didik pada rombel terpilih melebihi kuota",null);
		
		for (UUID uuid : idRombel) {
			krsService.addFromRombel(uuid, idPemb);
		}
		response = new AjaxResponse("ok","Penambahan berhasil",null);
        return response;
    }
	
	
	@RequestMapping(value = "/tambahpd", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse tambahpd(HttpSession session,@RequestParam("idPd[]") UUID[] idPd, @RequestParam("idPemb") UUID idPemb) {
		
		AjaxResponse response;
		if(idPemb == null) return new AjaxResponse("error","Pembelajaran tidak diketahui",null);
		Pemb pemb = pembService.getById(idPemb);
		if(pemb!=null)
		{
			if((pemb.getKuota()-krsService.getPeserta("pemb.idPemb ='"+pemb.getIdPemb()+"' and krs.aKrsTerhapus = false and krs.aKrsBatal = false").size())<idPd.length) return new AjaxResponse("error","Peserta didik terpilih melebihi kuota",null);
			for (UUID uuid : idPd) {
				Pd pd = pdService.getById(uuid);
				if(pd!=null)
				{
					Krs krs = new Krs();
					krs.setPd(pd);
					krs.setPemb(pemb);
					krs.setaKrsDisetujui(true);
					krsService.save(krs);
				}
			}
		}
		response = new AjaxResponse("ok","Penambahan berhasil",null);
        return response;
    }
	
	@MessageMapping(value = "/jumlahpeserta")
    @SendTo("/topic/getjumlahpeserta")
    public AjaxResponse jumlahpeserta(String idPemb) {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // simulated delay
		if(idPemb == null) return new AjaxResponse("error","Pembelajaran tidak diketahui",null);
		Pemb pemb = pembService.getById(UUID.fromString(idPemb));
		if(pemb!=null)
		{
			return new AjaxResponse("ok","",krsService.getPeserta("pemb.idPemb ='"+pemb.getIdPemb()+"' and krs.aKrsTerhapus = false and krs.aKrsBatal = false").size());
		}
		else return new AjaxResponse("error","pembelajaran tidak di ketahui",null);
    }
	
	
	@RequestMapping(value = "/stomp/", method = RequestMethod.GET)
	public ModelAndView stomp(Locale locale, Model model) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("peserta");
		
		mav.addObject ("menuActive","Monitoring Pengambilan KRS");
		 
		return mav;
	}
}
