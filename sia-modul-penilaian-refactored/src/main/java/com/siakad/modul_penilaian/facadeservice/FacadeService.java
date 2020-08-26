package com.siakad.modul_penilaian.facadeservice;

/**
 * @author Kholed Lansari
 * @copyright 2017,  BuduEngine
 * @license OpenSource, Your Own Risk
 * @date 6/11/17.
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.siakad.modul_penilaian.service.IpkService;
import com.siakad.modul_penilaian.service.IpsService;
import com.siakad.modul_penilaian.service.KomponenNilaiService;
import com.siakad.modul_penilaian.service.KonversiNilaiService;
import com.siakad.modul_penilaian.service.KrsService;
import com.siakad.modul_penilaian.service.KuisionerService;
import com.siakad.modul_penilaian.service.NilaiKuisionerService;
import com.siakad.modul_penilaian.service.NilaiService;
import com.siakad.modul_penilaian.service.PdService;
import com.siakad.modul_penilaian.service.PembService;
import com.siakad.modul_penilaian.service.PendidikPengajarService;
import com.siakad.modul_penilaian.service.PertanyaanKuisionerService;
import com.siakad.modul_penilaian.service.StatusKuisionerService;
import com.siakad.modul_penilaian.service.TglSmtService;

import com.siakad.modul_penilaian.controller.ControllerIP;
import com.siakad.modul_penilaian.controller.ControllerKuisioner;
import com.siakad.modul_penilaian.controller.ControllerLaporan;
import com.siakad.modul_penilaian.controller.ControllerNilai;

@Controller
public class FacadeService {

	//// Service ///
	//in ControllerNilai
	@Autowired
	//public PembService servicePemb;
    public PembService servicePemb;

	@Autowired
	private KrsService serviceKrs;
	
	@Autowired
	private KomponenNilaiService serviceKomp;
	
	@Autowired
	private NilaiService serviceNilai;
	
	@Autowired
	private KonversiNilaiService serviceKonversi;
	
	@Autowired
	private TglSmtService serviceTglSmt;
	
	// Controller IP
	@Autowired
	private PdService servicePd;
	
	@Autowired
	private IpsService serviceIps;
	
	@Autowired
	private IpkService serviceIpk;
	
	// Controller Kuisioner
	
	@Autowired
	private KuisionerService serviceKuisioner;
	
	@Autowired
	private PertanyaanKuisionerService servicePertanyaan;
	
	@Autowired
	private NilaiKuisionerService serviceNilaiKuisioner;
	
	@Autowired
	private PendidikPengajarService servicePendidikPengajar;
	
	@Autowired
	private StatusKuisionerService serviceStatus;

	// Controller Laporan
	
	// Controller File 
	// HAVE NO DEPENDENCY to other class
	
	// Controller //
//	@Autowired
//	private ControllerNilai facadeControllerNilai;
//
//	@Autowired
//	private ControllerKuisioner facadeControllerKuisioner;
//
//	@Autowired
//	private ControllerLaporan facadeControllerLaporan;
//
//	@Autowired
//	private ControllerIP facadeControllerIP;
	
	
//	public ControllerNilai ControllerNilaiToPembService() {
//		return facadeControllerNilai;
//	}
//
//    public ControllerKuisioner ControllerKuisionerToPemService() {
//        return facadeControllerKuisioner;
//    }
//

}

