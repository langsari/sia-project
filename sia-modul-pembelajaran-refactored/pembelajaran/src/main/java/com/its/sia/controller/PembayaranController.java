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
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sia.modul.domain.Pembayaran;
import com.sia.main.domain.PeranPengguna;
import com.sia.modul.domain.SatMan;
import com.sia.modul.domain.Smt;
import com.sia.modul.domain.TglSmt;
import com.sia.modul.domain.ThnAjaran;
import com.its.sia.service.AjaxResponse;
import com.its.sia.service.AnggotaRombelService;
import com.its.sia.service.Datatable;
import com.its.sia.service.PdService;
import com.its.sia.service.PembayaranService;
import com.its.sia.service.SatManService;
import com.its.sia.service.SmtService;
import com.its.sia.service.TglSmtService;
import com.its.sia.service.ThnAjaranService;

@Controller
@Secured(value = { "ROLE_Kepala" })
@RequestMapping(value = "/pembayaran")
public class PembayaranController {

	@Autowired
	private PembayaranService pembayaranService;
	@Autowired
	private ThnAjaranService thnAjaranService;
	@Autowired
	private SmtService smtService;
	@Autowired
	private SatManService satManService;
	@Autowired
	private TglSmtService tglSmtService;
	@Autowired
	private PdService pdService;
	
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView datatable(HttpSession session, Locale locale, Model model) {
		ModelAndView mav = new ModelAndView();
		
		PeranPengguna peranPengguna = (PeranPengguna) session.getAttribute("userRoleSession");
		
		List<ThnAjaran> listThnAjaran = thnAjaranService.get("aThnAjaranTerhapus = false","thnThnAjaran desc");
		List<Smt> listSmt = smtService.get("aSmthapus = false","jenisSmt asc");
		List<Integer> listAngkatan = pdService.getAngkatan("","pd.angkatanPd desc");
		UUID idSatMan = peranPengguna.getSatMan().getIdSatMan();
		List<SatMan> listSatMan = satManService.listChild(idSatMan);
		TglSmt smtAktif = tglSmtService.getAktif();
		
		mav.setViewName("MasterPembayaran");
		mav.addObject("listThnAjaran", listThnAjaran);
		mav.addObject("listSmt", listSmt);
		mav.addObject("listSatMan", listSatMan);
		mav.addObject("smtAktif", smtAktif);
		mav.addObject("listAngkatan", listAngkatan);
		mav.addObject ("menuActive","Laporan Pembayaran");
			 
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
			@RequestParam("idThnAjaran") UUID idThnAjaran,
			@RequestParam("idSmt") UUID idSmt,
			@RequestParam("idSatMan") UUID idSatMan,
			@RequestParam("angkatan") String angkatan
            ) {
		
		String filter = "";
		filter +=" thnAjaran.idThnAjaran ='"+idThnAjaran+"'";
		//if(idSatMan!=null) filter +=" AND satMan.idSatMan ='"+idSatMan+"'";
		filter +=" AND smt.idSmt ='"+idSmt+"'";
		filter += " AND CAST (pd.angkatanPd as string) LIKE '%"+angkatan+"%'";
		
		Datatable pembayaranDatatable = pembayaranService.getdatatable(sEcho, iDisplayLength, iDisplayStart, iSortCol_0, sSortDir_0, sSearch,filter);
		return pembayaranDatatable;
	}
	
	
	@RequestMapping(value = "/simpan", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse simpan(HttpSession session,@Valid @ModelAttribute("Pembayaran") Pembayaran pembayaran, 
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
        response.setData(pembayaranService.save(pembayaran));
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
    public @ResponseBody AjaxResponse edit(HttpSession session,@RequestParam("idPembayaran") UUID idPembayaran) {
		
		AjaxResponse response;
		Pembayaran pembayaran = pembayaranService.getById(idPembayaran);
		if(pembayaran == null) response = new AjaxResponse("error","Data tidak ditemukan",null);
		else response = new AjaxResponse("ok","Data ditemukan",pembayaran);
        return response;
    }
	
	
	@RequestMapping(value = "/deletemany", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse deleteMany(HttpSession session,@RequestParam("idPembayaranSementara[]") UUID[] idPembayaranSementara) {
		
		AjaxResponse response;
		for (UUID uuid : idPembayaranSementara) {
			pembayaranService.delete(uuid);
		}
		response = new AjaxResponse("ok","Data dihapus",null);
        return response;
    }
	
}
