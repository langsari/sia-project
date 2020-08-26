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

import com.sia.modul.domain.AnggotaRombel;
import com.sia.modul.domain.Kurikulum;
import com.sia.modul.domain.MK;
import com.sia.modul.domain.Pd;
import com.sia.modul.domain.Pemb;
import com.sia.modul.domain.PendidikPengajar;
import com.sia.main.domain.PeranPengguna;
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
import com.its.sia.service.PtkService;
import com.its.sia.service.RombelService;
import com.its.sia.service.SatManService;
import com.its.sia.service.TglSmtService;

@Controller
@Secured(value = { "ROLE_Tenaga Kependidikan" })
@RequestMapping(value = "/pembelajaran")
public class PembController {

	private static final Logger logger = LoggerFactory.getLogger(PembController.class);
	
	@Autowired
	private PembService pembService;
	
	@Autowired
	private MKService mkService;
	
	@Autowired
	private TglSmtService tglSmtService;
	
	@Autowired
	private KurikulumService kurikulumService;
	
	@Autowired
	private PendidikPengajarService pendidikPengajarService;
	
	@Autowired
	private PtkService ptkService;

	@Autowired
	private PdService pdService;
	
	@Autowired
	private KrsService krsService;
	
	@Autowired
	private SatManService satManService;
	
	@Autowired
	private PembSatManService pembSatManService;
	
	@Autowired
	private RombelService rombelService;
	
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView datatable(HttpSession session, Locale locale, Model model) {
		Pemb pemb = new Pemb();
		ModelAndView mav = new ModelAndView();
		
		PeranPengguna peranPengguna = (PeranPengguna)session.getAttribute("userRoleSession");
		
		List<TglSmt> listTglSmt = tglSmtService.get("tglSmt.aTglSmtTerhapus = false","thnAjaran.thnThnAjaran desc, smt.nmSmt desc");
		/*kurikulum filter satman*/
		List<Kurikulum> listKurikulum = kurikulumService.get("satMan.idSatMan = '"+peranPengguna.getSatMan().getIdSatMan()+"' and kurikulum.statusKurikulum = false","kurikulum.thnMulai desc");
		/*list anak satuan manajemen*/
		List<SatMan> listAnak;
		List<SatMan> listSatManPtk;
		if(peranPengguna.getSatMan().isaSatManProdi()==false)
		{
			listAnak = satManService.get("aSatManProdi = true","nmSatMan asc");
			/*list anak satuan manajemen*/
			listSatManPtk = satManService.get("","nmSatMan asc");
		}
		else
		{
			listAnak = satManService.get("idSatMan = '"+peranPengguna.getSatMan().getIdSatMan()+"'","nmSatMan asc");
			/*list anak satuan manajemen*/
			listSatManPtk = satManService.get("idSatMan = '"+peranPengguna.getSatMan().getIdSatMan()+"'","nmSatMan asc");
		}
		List<Integer> listAngkatan = pdService.getAngkatan();
		
		mav.setViewName("MasterPemb");
		mav.addObject("pemb", pemb);
		mav.addObject("listTglSmt", listTglSmt);
		mav.addObject("listKurikulum", listKurikulum);
		mav.addObject("listAnak", listAnak);
		mav.addObject("listAngkatan", listAngkatan);
		mav.addObject("listSatManPtk", listSatManPtk);
		mav.addObject("idSatMan", peranPengguna.getSatMan().getIdSatMan());
		mav.addObject ("menuActive","Kelola Pembelajaran");
				
		return mav;
	}
	
	
	@RequestMapping(value = "/peserta/{idPemb}", method = RequestMethod.GET)
	public ModelAndView peserta(HttpSession session, Locale locale, @PathVariable("idPemb") String idPemb) {
		ModelAndView mav = new ModelAndView();
		
		List<Pd> listPd = krsService.getPeserta("pemb.idPemb = '"+idPemb+"' and krs.aKrsTerhapus = false and krs.aKrsBatal = false","pd.nimPd asc");
		Pemb pemb = pembService.getById(UUID.fromString(idPemb));
		mav.setViewName("PesertaPembelajaran");
		mav.addObject("listPd", listPd);
		mav.addObject("pemb", pemb);
		mav.addObject ("menuActive","Kelola Pembelajaran");
		
		return mav;
	}
	
	
	@RequestMapping(value = "/pendidik/{idPemb}", method = RequestMethod.GET)
	public ModelAndView pendidik(HttpSession session, Locale locale, @PathVariable("idPemb") String idPemb) {
		ModelAndView mav = new ModelAndView();
		
		List<PendidikPengajar> ListPendidikPengajar = pendidikPengajarService.get("pemb.idPemb = '"+idPemb+"' and pendidikPengajar.aPendidikPengajarTerhapus = false","ptk.nipPtk asc");
		Pemb pemb = pembService.getById(UUID.fromString(idPemb));
		mav.setViewName("PendidikPembelajaran");
		mav.addObject("ListPendidikPengajar", ListPendidikPengajar);
		mav.addObject("pemb", pemb);
		mav.addObject ("menuActive","Kelola Pembelajaran");
		
		return mav;
	}
	
	
	@RequestMapping(value = "/getsatmanmk", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse getSatManMK(HttpSession session, @RequestParam("idMK") UUID idMK) {
		
		List<SatMan> lisSatMan = satManService.listSatManInMK(idMK);
		if(lisSatMan.size()>0)
			return new AjaxResponse("ok", "Data ditemukan", lisSatMan);
		else return new AjaxResponse("error", "Matakuliah belum ditempatkan pada satuan manajemen manapun", null);
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
		
		String filter = "pemb.aPembTerhapus = false and satMan.idSatMan ='"+peranPengguna.getSatMan().getIdSatMan()+"'";
		if(id_tgl_smt!=null) filter+= " AND tglSmt.idTglSmt = '"+id_tgl_smt+"'";
		Datatable thnAjaranDatatable = pembService.getdatatable(sEcho, iDisplayLength, iDisplayStart, iSortCol_0, sSortDir_0, sSearch,filter);
		return thnAjaranDatatable;
	}
	
	
	@RequestMapping(value = "/simpan", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse simpan(
			HttpSession session,
    		@Valid @ModelAttribute("pemb") Pemb pemb, 
            BindingResult result, Map<String, Object> model,
    		@RequestParam("idMK") UUID idMK,@RequestParam("satMan[]") UUID[] satMan) {
		
		AjaxResponse response = new AjaxResponse();
		List<TglSmt> listTglSmt = tglSmtService.get("tglSmt.aTglSmtAktif = true");
		if(idMK==null) return new AjaxResponse("error","Matakuliah tidak boleh kosong",null);
		MK mk = mkService.getById(idMK);
		if(mk == null) return new AjaxResponse("error","Matakuliah tidak diketahui",null);
		if(listTglSmt.size()==0) return new AjaxResponse("error","Tidak ada semester aktif",null);
		String where ="";
		if(pemb.getIdPemb()!=null) where="pemb.idPemb!='"+pemb.getIdPemb()+"' AND "; 
		List<Pemb> listPemb = pembService.get(where + "pemb.nmPemb = '"+pemb.getNmPemb()+"' and tglSmt.idTglSmt ='"+listTglSmt.get(0).getIdTglSmt().toString()+"' and mk.idMK ='"+mk.getIdMK()+"'");
		if(listPemb.size()>0) return new AjaxResponse("error","Pembelajaran sudah ada",null);
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
        pemb.setTglSmt(listTglSmt.get(0));
        pemb.setMk(mk);
    	response.setStatus("ok");
    	String idPemb = pembService.save(pemb);
    	pemb.setIdPemb(UUID.fromString(idPemb));
    	List<PembSatMan> lisPembSatMan = pembSatManService.get("pemb.idPemb = '"+pemb.getIdPemb()+"'");
    	for (PembSatMan pembSatMan : lisPembSatMan) {
			pembSatManService.delete(pembSatMan.getIdPembSatMan());
		}
        response.setData(idPemb);
        for (UUID idSatman : satMan) {
			SatMan satManQuery = satManService.getById(idSatman);
			if(!satManQuery.equals(null))
			{
				PembSatMan pembSatMan = new PembSatMan();
				pembSatMan.setPemb(pemb);
				pembSatMan.setSatMan(satManQuery);
				pembSatManService.save(pembSatMan);
			}
		}
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
    public @ResponseBody AjaxResponse edit(HttpSession session, @RequestParam("idPemb") UUID idPemb) {
		
		AjaxResponse response;
		Pemb pemb = pembService.getById(idPemb);
		if(pemb == null) response = new AjaxResponse("error","Data tidak ditemukan",null);
		else 
		{
			List<PembSatMan> listPembSatMan = pembSatManService.get("pemb.idPemb ='"+idPemb+"'");
			List<UUID> listIdSatMan = new ArrayList<UUID>();
			for (PembSatMan pembSatMan : listPembSatMan) {
				listIdSatMan.add(pembSatMan.getSatMan().getIdSatMan());
			}
			pemb.setListSatManList(listIdSatMan);
			response = new AjaxResponse("ok","Data ditemukan",pemb);
		}
        return response;
    }
	
	
	@RequestMapping(value = "/deletemany", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse deleteMany(HttpSession session, @RequestParam("idPemb[]") UUID[] idPemb) {
		
		AjaxResponse response;
		for (UUID uuid : idPemb) {
			pembService.delete(uuid);
		}
		response = new AjaxResponse("ok","Data dihapus",null);
        return response;
    }
	
	
	@RequestMapping(value = "/jsonmatakuliah", method = RequestMethod.POST)
	public @ResponseBody Datatable jsonmatakuliah(
			HttpSession session, 
			@RequestParam("sEcho") String sEcho, 
			@RequestParam("iDisplayLength") int iDisplayLength,
            @RequestParam("iSortCol_0") int iSortCol_0,
            @RequestParam("sSortDir_0") String sSortDir_0,
            @RequestParam("sSearch") String sSearch,
			@RequestParam("iDisplayStart") int iDisplayStart,
			@RequestParam("idKurikulum") UUID idKurikulum
            ) {
		
		PeranPengguna peranPengguna = (PeranPengguna)session.getAttribute("userRoleSession");
		
		String filter = "satMan.idSatMan='"+peranPengguna.getSatMan().getIdSatMan()+"' and kurikulum.statusKurikulum = false ";
		if(idKurikulum!=null) filter+= " AND kurikulum.idKurikulum = '"+idKurikulum+"'";
		Datatable thnAjaranDatatable = mkService.getdatatable(sEcho, iDisplayLength, iDisplayStart, iSortCol_0, sSortDir_0, sSearch,filter);
		return thnAjaranDatatable;
	}
	
	
	@RequestMapping(value = "/jsonsatman", method = RequestMethod.POST)
	public @ResponseBody Datatable jsonsatman(
			HttpSession session, 
			@RequestParam("sEcho") String sEcho, 
			@RequestParam("iDisplayLength") int iDisplayLength,
            @RequestParam("iSortCol_0") int iSortCol_0,
            @RequestParam("sSortDir_0") String sSortDir_0,
            @RequestParam("sSearch") String sSearch,
			@RequestParam("iDisplayStart") int iDisplayStart
            ) {
		
		String filter = "aSatManAktif = true";
		Datatable thnAjaranDatatable = satManService.getdatatable(sEcho, iDisplayLength, iDisplayStart, iSortCol_0, sSortDir_0, sSearch,filter);
		return thnAjaranDatatable;
	}
	
	
	@RequestMapping(value = "/editmatakuliah", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse editmatakuliah(HttpSession session, @RequestParam("idPemb") UUID idPemb) {
		
		AjaxResponse response;
		Pemb pemb = pembService.getById(idPemb);
		if(pemb == null) response = new AjaxResponse("error","Data tidak ditemukan",null);
		else response = new AjaxResponse("ok","Data ditemukan",pemb);
        return response;
    }
	
	
	@RequestMapping(value = "/jsonpendidikpengajar", method = RequestMethod.POST)
	public @ResponseBody Datatable jsonpendidikpengajar(
			HttpSession session, 
			@RequestParam("sEcho") String sEcho, 
			@RequestParam("iDisplayLength") int iDisplayLength,
            @RequestParam("iSortCol_0") int iSortCol_0,
            @RequestParam("sSortDir_0") String sSortDir_0,
            @RequestParam("sSearch") String sSearch,
			@RequestParam("iDisplayStart") int iDisplayStart,
			@RequestParam("idPemb") UUID idPemb
            ) {
		String filter = "pendidikPengajar.aPendidikPengajarTerhapus = false";
		if(idPemb!=null) filter+= " AND pemb.idPemb = '"+idPemb+"'";
		Datatable pendidikPengajarDatatable = pendidikPengajarService.getdatatable(sEcho, iDisplayLength, iDisplayStart, iSortCol_0, sSortDir_0, sSearch,filter);
		return pendidikPengajarDatatable;
	}
	
	
	@RequestMapping(value = "/jsonpendidik", method = RequestMethod.POST)
	public @ResponseBody Datatable jsonpendidik(
			HttpSession session, 
			@RequestParam("sEcho") String sEcho, 
			@RequestParam("iDisplayLength") int iDisplayLength,
            @RequestParam("iSortCol_0") int iSortCol_0,
            @RequestParam("sSortDir_0") String sSortDir_0,
            @RequestParam("sSearch") String sSearch,
			@RequestParam("iDisplayStart") int iDisplayStart,
			@RequestParam("idSatMan") UUID idSatMan
            ) {
		String where="";
		if(idSatMan!=null) where+=" AND satMan='"+idSatMan+"'";
		String filter = "ptk.statusPtk = true and ptk.aPtkTerhapus=false "+where;
		Datatable ptkDatatable = ptkService.getdatatable(sEcho, iDisplayLength, iDisplayStart, iSortCol_0, sSortDir_0, sSearch,filter);
		return ptkDatatable;
	}
	
	
	@RequestMapping(value = "/tambahkanpendidik", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse tambahkanpendidik(HttpSession session, @RequestParam("idPemb") UUID idPemb,@RequestParam("idPtk") UUID idPtk) {
		
		AjaxResponse response = new AjaxResponse();
		PendidikPengajar pendidikPengajar = new PendidikPengajar();
		Ptk ptk = ptkService.getById(idPtk);
		if(ptk==null) return new AjaxResponse("error","Pendidik tidak diketahui",null);
		Pemb pemb = pembService.getById(idPemb);
		if(pemb==null) return new AjaxResponse("error","Pembelajaran tidak diketahui",null);
		if(pemb.getTglSmt().getaTglSmtAktif() == false) return new AjaxResponse("error","Periode terlewati",null);
		
		pendidikPengajar.setPtk(ptk);
		pendidikPengajar.setPemb(pemb);
		response.setData(pendidikPengajarService.save(pendidikPengajar));
		response.setMessage("Penambahan sukses");
		if(response.getData()==null)
		{
			return new AjaxResponse("error", "Pendidik sudah ada dalam pembelajaran", null);
		}
		else return response;
    }
	
	
	@RequestMapping(value = "/tetapkanketua", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse tetapkanketua(HttpSession session, @RequestParam("idPendidikPengajar") UUID idPendidikPengajar) {
		
		AjaxResponse response = new AjaxResponse();
		PendidikPengajar pendidikPengajar = pendidikPengajarService.getById(idPendidikPengajar);
		if(pendidikPengajar.getPemb().getTglSmt().getaTglSmtAktif() == false) return new AjaxResponse("error","Periode terlewati",null);
		if(pendidikPengajar == null || pendidikPengajar.isaPendidikPengajarTerhapus()) return new AjaxResponse("Error","Pengajar tidak di ketahui",null);
		pendidikPengajar.setaPendidikPengajarKetua(true);
		List<PendidikPengajar> listPendidikPengajar = pendidikPengajarService.get("pemb.idPemb ='"+pendidikPengajar.getPemb().getIdPemb()+"' and pendidikPengajar.aPendidikPengajarTerhapus = false and pendidikPengajar.aPendidikPengajarKetua = true");
		for (PendidikPengajar pendidikPengajar2 : listPendidikPengajar) {
			pendidikPengajar2.setaPendidikPengajarKetua(false);
			pendidikPengajarService.save(pendidikPengajar2);
		}
		System.out.println(pendidikPengajar.isaPendidikPengajarKetua());
		response.setData(pendidikPengajarService.save(pendidikPengajar));
		response.setMessage("Perubahan disimpan");
		return response;
    }
	
	
	@RequestMapping(value = "/tambahkanmany", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse tambahkanMany(HttpSession session, @RequestParam("idPemb") UUID idPemb,@RequestParam("idPtk[]") UUID[] idPtk) {
		
		AjaxResponse response = new AjaxResponse();
		Pemb pemb = pembService.getById(idPemb);
		if(pemb==null) return new AjaxResponse("error","Pembelajaran tidak diketahui",null);
		if(pemb.getTglSmt().getaTglSmtAktif() == false) return new AjaxResponse("error","Periode terlewati",null);
		for (UUID uuid : idPtk) {
			PendidikPengajar pendidikPengajar = new PendidikPengajar();
			Ptk ptk = ptkService.getById(uuid);
			if(ptk!=null && ptk.isaPtkTerhapus()==false)
			{
				pendidikPengajar.setPtk(ptk);
				pendidikPengajar.setPemb(pemb);
				pendidikPengajarService.save(pendidikPengajar);
			}
		}
		return response;
    }
	
	
	@RequestMapping(value = "/deletependidikpengajar", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse deletePendidikPengajar(HttpSession session, @RequestParam("idPendidikPengajar[]") UUID[] idPendidikPengajar) {
		
		AjaxResponse response;
		for (UUID uuid : idPendidikPengajar) {
			PendidikPengajar pendidikPengajar = pendidikPengajarService.getById(uuid);
			if(pendidikPengajar.getPemb().getTglSmt().getaTglSmtAktif() == false) return new AjaxResponse("error","Periode terlewati",null);
			if(uuid!=null)pendidikPengajarService.delete(uuid);
		}
		response = new AjaxResponse("ok","Data dihapus",null);
		return response;
    }
	
	
	@RequestMapping(value = "/jsonpeserta", method = RequestMethod.POST)
	public @ResponseBody Datatable jsonpeserta(
			HttpSession session, 
			@RequestParam("sEcho") String sEcho, 
			@RequestParam("iDisplayLength") int iDisplayLength,
            @RequestParam("iSortCol_0") int iSortCol_0,
            @RequestParam("sSortDir_0") String sSortDir_0,
            @RequestParam("sSearch") String sSearch,
			@RequestParam("iDisplayStart") int iDisplayStart,
			@RequestParam("idPemb") UUID idPemb
            ) {
		
		String filter = "krs.aKrsTerhapus = false and krs.aKrsBatal = false";
		if(idPemb !=null) filter+=" AND pemb.idPemb ='"+idPemb+"'";
		Datatable ptkDatatable = krsService.getPesertaDatatable(sEcho, iDisplayLength, iDisplayStart, iSortCol_0, sSortDir_0, sSearch,filter);
		return ptkDatatable;
	}
	
	
	@RequestMapping(value = "/jsonrombel", method = RequestMethod.POST)
	public @ResponseBody Datatable jsonrombel(
			HttpSession session, 
			@RequestParam("sEcho") String sEcho, 
			@RequestParam("iDisplayLength") int iDisplayLength,
            @RequestParam("iSortCol_0") int iSortCol_0,
            @RequestParam("sSortDir_0") String sSortDir_0,
            @RequestParam("sSearch") String sSearch,
			@RequestParam("iDisplayStart") int iDisplayStart
            ) {
		
		PeranPengguna peranPengguna = (PeranPengguna)session.getAttribute("userRoleSession");
		
		String filter = "rombel.aRombelTerhapus = false and satMan.idSatMan ='"+peranPengguna.getSatMan().getIdSatMan()+"'";
		Datatable rombelDatatable = rombelService.getdatatable(sEcho, iDisplayLength, iDisplayStart, iSortCol_0, sSortDir_0, sSearch,filter);
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
		Datatable rombelDatatable = pdService.getdatatable(sEcho, iDisplayLength, iDisplayStart, iSortCol_0, sSortDir_0, sSearch,filter);
		return rombelDatatable;
	}
	
}
