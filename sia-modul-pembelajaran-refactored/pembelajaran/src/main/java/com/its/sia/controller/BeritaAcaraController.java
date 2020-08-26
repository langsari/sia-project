package com.its.sia.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sia.main.domain.PeranPengguna;
import com.sia.modul.domain.AnggotaRombel;
import com.sia.modul.domain.Kurikulum;
import com.sia.modul.domain.MK;
import com.sia.modul.domain.Pd;
import com.sia.modul.domain.Pemb;
import com.sia.modul.domain.PendidikPengajar;
import com.sia.modul.domain.PertemuanPembelajaran;
import com.sia.modul.domain.Ptk;
import com.sia.modul.domain.Rombel;
import com.sia.modul.domain.SatMan;
import com.sia.modul.domain.TglSmt;
import com.sia.modul.domain.PembSatMan;
import com.its.sia.service.AjaxResponse;
import com.its.sia.service.Datatable;
import com.its.sia.service.KrsService;
import com.its.sia.service.KurikulumService;
import com.its.sia.service.MKService;
import com.its.sia.service.PdService;
import com.its.sia.service.PembSatManService;
import com.its.sia.service.PembService;
import com.its.sia.service.PendidikPengajarService;
import com.its.sia.service.PertemuanPembelajaranService;
import com.its.sia.service.PtkService;
import com.its.sia.service.SatManService;
import com.its.sia.service.TglSmtService;

@Controller
@Secured(value = { "ROLE_Pendidik" })
@RequestMapping(value = "/beritaacara")
public class BeritaAcaraController {

	private static final Logger logger = LoggerFactory.getLogger(BeritaAcaraController.class);
	
	@Autowired
	private PembService pembService;
	
	@Autowired
	private TglSmtService tglSmtService;
		
	@Autowired
	private PendidikPengajarService pendidikPengajarService;
	
	@Autowired
	private PtkService ptkService;
	
	@Autowired
	private PertemuanPembelajaranService pertemuanPembelajaranService;

	@Autowired
	private SatManService satManService;
	
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView datatable(HttpSession session, Locale locale, Model model) {
		Pemb pemb = new Pemb();
		ModelAndView mav = new ModelAndView();
		List<TglSmt> listTglSmt = tglSmtService.get("tglSmt.aTglSmtTerhapus = false","thnAjaran.thnThnAjaran asc, smt.nmSmt asc");
		
		mav.setViewName("Pertemuan");
		mav.addObject("pemb", pemb);
		mav.addObject("listTglSmt", listTglSmt);
		
		mav.addObject ("menuActive","Berita Acara"); 
		
		return mav;
	}

	
	@RequestMapping(value = "/{idPemb}", method = RequestMethod.GET)
	public ModelAndView beritaacara(HttpSession session,Locale locale, @PathVariable("idPemb") String idPemb) {
		ModelAndView mav = new ModelAndView();
		PertemuanPembelajaran pertemuanPembelajaran = new PertemuanPembelajaran();
		Pemb pemb = pembService.getById(UUID.fromString(idPemb));
		/*if not hak akses throw 404*/
		List<PertemuanPembelajaran> listPertemuanPembelajaran = pertemuanPembelajaranService.get("pemb.idPemb ='"+pemb.getIdPemb()+"'","pertemuanPembelajaran.pertemuan asc");
		Integer jumlahPertemuan = pemb.getTglSmt().getSmt().getJmlPertemuan() * pemb.getTemuDalamSeminggu();
		List<PendidikPengajar> listPendidikPengajar = pendidikPengajarService.get("pemb.idPemb ='"+pemb.getIdPemb()+"'");
		mav.setViewName("RekapPertemuan");
		mav.addObject("pemb", pemb);
		mav.addObject("listPertemuanPembelajaran", listPertemuanPembelajaran);
		mav.addObject("jumlahPertemuan", jumlahPertemuan);
		mav.addObject("pertemuanPembelajaran", pertemuanPembelajaran);
		mav.addObject("listPendidikPengajar", listPendidikPengajar);
		
		mav.addObject ("menuActive","Berita Acara");
		
		return mav;
	}
	
	
	@RequestMapping(value = "/laporan/{idPemb}", method = RequestMethod.GET)
	public ModelAndView laporanBeritaAcara(HttpSession session,Locale locale, @PathVariable("idPemb") String idPemb) {
		ModelAndView mav = new ModelAndView();
		PertemuanPembelajaran pertemuanPembelajaran = new PertemuanPembelajaran();
		Pemb pemb = pembService.getById(UUID.fromString(idPemb));
		/*if not hak akses throw 404*/
		List<PertemuanPembelajaran> listPertemuanPembelajaran = pertemuanPembelajaranService.get("pemb.idPemb ='"+pemb.getIdPemb()+"'","pertemuanPembelajaran.pertemuan asc");
		Integer jumlahPertemuan = pemb.getTglSmt().getSmt().getJmlPertemuan() * pemb.getTemuDalamSeminggu();
		List<PendidikPengajar> listPendidikPengajar = pendidikPengajarService.get("pemb.idPemb ='"+pemb.getIdPemb()+"' and pendidikPengajar.aPendidikPengajarTerhapus=false","pendidikPengajar.aPendidikPengajarKetua desc");
		
		
		mav.setViewName("LaporanBeritaAcara");
		mav.addObject("pemb", pemb);
		mav.addObject("listPertemuanPembelajaran", listPertemuanPembelajaran);
		mav.addObject("max", listPertemuanPembelajaran.size());
		mav.addObject("jumlahPertemuan", jumlahPertemuan);
		mav.addObject("pertemuanPembelajaran", pertemuanPembelajaran);
		mav.addObject("listPendidikPengajar", listPendidikPengajar);
		
		mav.addObject ("menuActive","Berita Acara");
		
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
			@RequestParam("id_tgl_smt") UUID id_tgl_smt
            ) {
		PeranPengguna peranPengguna = (PeranPengguna)session.getAttribute("userRoleSession");
		
		String filter = "ptk.idPtk='"+peranPengguna.getPengguna().getPtk().getIdPtk()+"' and pendidikPengajar.aPendidikPengajarTerhapus = false and pemb.aPembTerhapus = false ";
		if(id_tgl_smt!=null) filter+= " AND tglSmt.idTglSmt = '"+id_tgl_smt+"'";
		Datatable thnAjaranDatatable = pendidikPengajarService.getPembDatatable(sEcho, iDisplayLength, iDisplayStart, iSortCol_0, sSortDir_0, sSearch,filter);
		return thnAjaranDatatable;
	}
	
	
	@RequestMapping(value = "/simpan", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse simpan(HttpSession session,@Valid @ModelAttribute("pertemuanPembelajaran") PertemuanPembelajaran pertemuanPembelajaran, 
            BindingResult result, Map<String, Object> model,
    		@RequestParam("idPemb") UUID idPemb) {
		AjaxResponse response = new AjaxResponse();
		if(idPemb == null) return new AjaxResponse("error","Pembelajaran tidak di ketahui",null);
		Pemb pemb = pembService.getById(idPemb);
		if(pemb == null) return new AjaxResponse("error","Pembelajaran tidak di ketahui",null);
		pertemuanPembelajaran.setPemb(pemb);
		long jumlahBeritaAcara = pertemuanPembelajaranService.count("pemb.idPemb ='"+pemb.getIdPemb()+"'");
		if(jumlahBeritaAcara+1<pertemuanPembelajaran.getPertemuan()) return new AjaxResponse("error","Isi berita acara sebelumnya terlebih dahulu",null);
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
		System.out.println(pertemuanPembelajaran.getKendalaPerkuliahan()+" "+pertemuanPembelajaran.getMateri());
    	response.setStatus("ok");
    	String idPertemuanPembelajaran = pertemuanPembelajaranService.save(pertemuanPembelajaran);
        response.setData(idPertemuanPembelajaran);
        if(response.getData()!=null) response.setMessage("Data berhasil disimpan");
        else 
        {
        	response.setStatus("sudah ada");
        	response.setMessage("Berita acara untuk pertemuan tersebut sudah diisi");
        }
        return response;
    }
	
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse edit(HttpSession session,@RequestParam("idPertemuanPembelajaran") UUID idPertemuanPembelajaran) {
		
		AjaxResponse response;
		PertemuanPembelajaran pertemuanPembelajaran = pertemuanPembelajaranService.getById(idPertemuanPembelajaran);
		if(pertemuanPembelajaran == null) response = new AjaxResponse("error","Data tidak ditemukan",null);
		else 
		{
			response = new AjaxResponse("ok","Data ditemukan",pertemuanPembelajaran);
		}
        return response;
    }
	
}
