package com.its.sia.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sia.modul.domain.AturanPengganti;
import com.sia.modul.domain.BatasAmbilSks;
import com.sia.modul.domain.Ipk;
import com.sia.modul.domain.Ips;
import com.sia.modul.domain.Krs;
import com.sia.modul.domain.Pd;
import com.sia.modul.domain.Pemb;
import com.sia.modul.domain.Pembayaran;
import com.sia.modul.domain.PeranPengguna;
import com.sia.modul.domain.SatMan;
import com.sia.modul.domain.SatManMK;
import com.sia.modul.domain.StatusKuisioner;
import com.sia.modul.domain.TglSmt;

@Service
public class ManajemenKrsServiceImpl implements ManajemenKrsService {
	
	@Autowired
	private KrsService krsService;

	@Autowired
	private PembService pembService;
	
	@Autowired
	private SatManService satManService;
	
	@Autowired
	private PdService pdService;
	
	@Autowired
	private TglSmtService tglSmtService ;
	
	@Autowired
	private AturanPenggantiService aturanPenggantiService;
	
	@Autowired
	private IpsService ipsService;
	
	@Autowired
	private IpkService ipkService;

	@Autowired
	private BatasAmbilSksService batasAmbilSksService;

	@Autowired
	private PembayaranService pembayaranService;

	@Autowired
	private StatusKuisionerService statusKuisionerService;
	
	@Autowired
	private PeranPenggunaService peranPenggunaService;
	
	@Autowired
	private SatManMKService satManMKService;
	
	@Override
	public AjaxResponse ambilmk(UUID idPemb, UUID idPd ,UUID idSatMan) {
		if(!dapatDisusun(idSatMan,idPd)) return new AjaxResponse("error","Tidak dapat menyusun KRS",null);
		List<Krs> queryResult = krsService.get("krs.aKrsDisetujui = true and krs.aKrsTerhapus = false and tglSmt.aTglSmtAktif = true and pd.idPd ='"+idPd+"'");
		if(queryResult.size()==0){
			Pemb pemb = pembService.getById(idPemb);
			if(pemb.getKuota() == Integer.parseInt(krsService.count("krs.aKrsTerhapus = false and krs.aKrsBatal = false and pemb.idPemb ='"+pemb.getIdPemb()+"'").toString()))
				return new AjaxResponse("error","Kuota Penuh",null);
			if(pemb == null || pemb.getTglSmt().getaTglSmtAktif()==false) return new AjaxResponse("error","Pembelajaran tidak diketahui",null);
			
			Ips ipsTerakhir = ipsService.getIpsTerakhir(idPd);
			System.out.println(ipsTerakhir.getNilaiIps());
			
			List<BatasAmbilSks> listBatas = batasAmbilSksService.get("batasBawahIps <= "+(ipsTerakhir==null?"0":String.valueOf(ipsTerakhir.getNilaiIps())),"batasPengambilanSks desc");
			Integer jumlahBatas = listBatas.size()>0?listBatas.get(0).getBatasPengambilanSks():0;
			List<Krs> listKrs = krsService.get("krs.aKrsTerhapus = false and krs.aKrsBatal = false and tglSmt.aTglSmtAktif = true and pd.idPd ='"+idPd+"'","mk.namaMK asc, pemb.nmPemb asc");
			Integer jumlahSKS=0;
			for (Krs krs : listKrs) {
				jumlahSKS+=krs.getPemb().getMk().getJumlahSKS();
			}
			System.out.println(jumlahSKS+"+"+pemb.getMk().getJumlahSKS()+" "+jumlahBatas);
			if(jumlahSKS+pemb.getMk().getJumlahSKS()>jumlahBatas) return new AjaxResponse("error","Pengambilan melebihi batas SKS",null);
				
			Krs krs = new Krs();
			krs.setPd(pdService.getById(UUID.fromString(idPd.toString())));
			krs.setPemb(pemb);
			String idKrsSementara = krsService.save(krs);
			listKrs = krsService.get("krs.aKrsTerhapus = false and krs.aKrsBatal = false and tglSmt.aTglSmtAktif = true and pd.idPd ='"+idPd+"'","mk.namaMK asc, pemb.nmPemb asc");
			KrsTransaction krsTransaction = new KrsTransaction();
			List<SatManMK> listSatManMK = new ArrayList<SatManMK>();
			for (Krs krs2 : listKrs) {
				List<SatManMK> query = satManMKService.get("mk.idMK = '"+krs2.getPemb().getMk().getIdMK()+"' and satManUntuk.idSatMan = '"+idSatMan+"'");
				if(query.size()>0) listSatManMK.add(query.get(0));
				else listSatManMK.add(null);
			}
			krsTransaction.setListKrs(listKrs);
			krsTransaction.setListSatManMK(listSatManMK);
			if(idKrsSementara==null) return new AjaxResponse("error","Matakuliah sudah diambil",null);
			else return new AjaxResponse("ok","Pembelajaran berhasil diambil",krsTransaction);
			
		}
		else return new AjaxResponse("error","KRS sudah di setujui",null);
	}

	@Override
	public AjaxResponse hapusmk(UUID idKrs, UUID idPd,UUID idSatMan) {
		if(!dapatDisusun(idSatMan,idPd)) return new AjaxResponse("error","Tidak dapat menyusun KRS",null);
		List<Krs> queryResult = krsService.get("krs.aKrsDisetujui = false and krs.aKrsTerhapus = false and tglSmt.aTglSmtAktif = true and pd.idPd ='"+idPd+"'");
		if(queryResult.size()>0){
			List<Krs> listKrs = krsService.get("krs.idKrs = '"+idKrs+"' AND tglSmt.aTglSmtAktif = true AND pd.idPd ='"+idPd+"' ");
			if(listKrs.size()==0) return new AjaxResponse("error","Pembelajaran tidak diketahui",null);
			else
			{
				krsService.delete(idKrs);
				String mkdrop = listKrs.get(0).getPemb().getMk().getNamaMK()+" "+listKrs.get(0).getPemb().getNmPemb();
				listKrs = krsService.get("krs.aKrsTerhapus = false and krs.aKrsBatal = false and tglSmt.aTglSmtAktif = true and pd.idPd ='"+idPd+"'","mk.namaMK asc, pemb.nmPemb asc");
				KrsTransaction krsTransaction = new KrsTransaction();
				List<SatManMK> listSatManMK = new ArrayList<SatManMK>();
				for (Krs krs2 : listKrs) {
					List<SatManMK> query = satManMKService.get("mk.idMK = '"+krs2.getPemb().getMk().getIdMK()+"' and satManUntuk.idSatMan = '"+idSatMan+"'");
					if(query.size()>0) listSatManMK.add(query.get(0));
					else listSatManMK.add(null);
				}
				krsTransaction.setListKrs(listKrs);
				krsTransaction.setListSatManMK(listSatManMK);
				return new AjaxResponse("ok",mkdrop+" berhasil terhapus",krsTransaction);
			}
		}
		else return new AjaxResponse("error","KRS sudah di setujui",null);
	}

	@Override
	public AjaxResponse dosenhapusmk(UUID idKrs, UUID idPtk, UUID idPd) {
		PeranPengguna peranPengguna = peranPenggunaService.getByPenggunaPeran(idPd,"Peserta Didik");
		if(!dapatDirubah(peranPengguna.getSatMan().getIdSatMan())) return new AjaxResponse("error","Tidak dapat mengubah KRS",null);
		
		List<Krs> listKrs = krsService.get("krs.idKrs = '"+idKrs+"' AND tglSmt.aTglSmtAktif = true AND pd.idPd ='"+idPd+"' AND ptk.idPtk='"+idPtk+"' ");
		if(listKrs.size()==0) return new AjaxResponse("error","Pembelajaran tidak diketahui",null);
		else
		{
			krsService.delete(idKrs);
			String mkdrop = listKrs.get(0).getPemb().getMk().getNamaMK()+" "+listKrs.get(0).getPemb().getNmPemb();
			listKrs = krsService.get("krs.aKrsTerhapus = false and tglSmt.aTglSmtAktif = true and pd.idPd ='"+idPd+"'","mk.namaMK asc, pemb.nmPemb asc");
			UUID idSatMan = peranPengguna.getSatMan().getIdSatMan();
			KrsTransaction krsTransaction = new KrsTransaction();
			List<SatManMK> listSatManMK = new ArrayList<SatManMK>();
			for (Krs krs2 : listKrs) {
				List<SatManMK> query = satManMKService.get("mk.idMK = '"+krs2.getPemb().getMk().getIdMK()+"' and satManUntuk.idSatMan = '"+idSatMan+"'");
				if(query.size()>0) listSatManMK.add(query.get(0));
				else listSatManMK.add(null);
			}
			krsTransaction.setListKrs(listKrs);
			krsTransaction.setListSatManMK(listSatManMK);
			return new AjaxResponse("ok",mkdrop+" berhasil terhapus",krsTransaction);
		}
	}

	@Override
	public AjaxResponse batalmk(UUID idKrs, UUID idPtk, UUID idPd) {
		PeranPengguna peranPengguna = peranPenggunaService.getByPenggunaPeran(idPd,"Peserta Didik");
		
		UUID idSatMan = peranPengguna.getSatMan().getIdSatMan();
		if(!dapatDibatalkan(idSatMan)) return new AjaxResponse("error","Tidak dapat membatalkan KRS",null);
		List<Krs> listKrs = krsService.get("krs.idKrs = '"+idKrs+"' AND krs.aKrsDisetujui = true AND tglSmt.aTglSmtAktif = true AND pd.idPd ='"+idPd+"' AND ptk.idPtk='"+idPtk+"' ");
		if(listKrs.size()==0) return new AjaxResponse("error","Pembelajaran tidak diketahui",null);
		else
		{
			krsService.batal(idKrs);
			listKrs = krsService.get("krs.aKrsTerhapus = false and tglSmt.aTglSmtAktif = true and pd.idPd ='"+idPd+"'","mk.namaMK asc, pemb.nmPemb asc");
			KrsTransaction krsTransaction = new KrsTransaction();
			List<SatManMK> listSatManMK = new ArrayList<SatManMK>();
			for (Krs krs2 : listKrs) {
				List<SatManMK> query = satManMKService.get("mk.idMK = '"+krs2.getPemb().getMk().getIdMK()+"' and satManUntuk.idSatMan = '"+idSatMan+"'");
				if(query.size()>0) listSatManMK.add(query.get(0));
				else listSatManMK.add(null);
			}
			krsTransaction.setListKrs(listKrs);
			krsTransaction.setListSatManMK(listSatManMK);
			return new AjaxResponse("ok","Pembelajaran berhasil terdrop",krsTransaction);
		}
	}

	@Override
	public AjaxResponse getkrs(UUID idPd, UUID idPtk) {
		List<Krs> listKrs = krsService.get("krs.aKrsTerhapus = false and ptk.idPtk='"+idPtk+"' and tglSmt.aTglSmtAktif = true and pd.idPd ='"+idPd+"'","mk.namaMK asc, pemb.nmPemb asc");
		return new AjaxResponse("ok","Memuat KRS",listKrs);
	}

	@Override
	public AjaxResponse setujuikrs(UUID idPd, UUID idPtk) {
		PeranPengguna peranPengguna = peranPenggunaService.getByPenggunaPeran(idPd,"Peserta Didik");
		//if(!dapatDirubah(peranPengguna.getSatMan().getIdSatMan()))  return new AjaxResponse("error","Tidak dapat mengubah KRS",null);
		
		List<Krs> listKrs = krsService.get("krs.aKrsTerhapus = false AND tglSmt.aTglSmtAktif = true AND pd.idPd ='"+idPd+"' AND ptk.idPtk='"+idPtk+"' ");
		if(listKrs.size()==0) return new AjaxResponse("error","Tidak ada krs yang disetujui",null);
		else
		{
			for (Krs krs : listKrs) {
				krs.setaKrsDisetujui(true);
				krsService.save(krs);
			}
			listKrs = krsService.get("krs.aKrsTerhapus = false and ptk.idPtk='"+idPtk+"' AND tglSmt.aTglSmtAktif = true and pd.idPd ='"+idPd+"'","mk.namaMK asc, pemb.nmPemb asc");
			UUID idSatMan = peranPengguna.getSatMan().getIdSatMan();
			KrsTransaction krsTransaction = new KrsTransaction();
			List<SatManMK> listSatManMK = new ArrayList<SatManMK>();
			for (Krs krs2 : listKrs) {
				List<SatManMK> query = satManMKService.get("mk.idMK = '"+krs2.getPemb().getMk().getIdMK()+"' and satManUntuk.idSatMan = '"+idSatMan+"'");
				if(query.size()>0) listSatManMK.add(query.get(0));
				else listSatManMK.add(null);
			}
			krsTransaction.setListKrs(listKrs);
			krsTransaction.setListSatManMK(listSatManMK);
			return new AjaxResponse("ok","Persetujuan berhasil",krsTransaction);
		}
	}

	@Override
	public AjaxResponse batalsetuju(UUID idPd, UUID idPtk) {
		PeranPengguna peranPengguna = peranPenggunaService.getByPenggunaPeran(idPd,"Peserta Didik");
		if(!dapatDirubah(peranPengguna.getSatMan().getIdSatMan())) return new AjaxResponse("error","Batas perubahan KRS terlewati",null);
		
		List<Krs> listKrs = krsService.get("krs.aKrsTerhapus = false AND krs.aKrsBatal = 'false' AND tglSmt.aTglSmtAktif = true AND pd.idPd ='"+idPd+"' AND ptk.idPtk='"+idPtk+"' ");
		if(listKrs.size()==0) return new AjaxResponse("error","Tidak ada persetujuan krs yang dibatalakan",null);
		else
		{
			for (Krs krs : listKrs) {
				krs.setaKrsDisetujui(false);
				String status = krsService.save(krs);
			}
			listKrs = krsService.get("krs.aKrsTerhapus = false and ptk.idPtk='"+idPtk+"' AND tglSmt.aTglSmtAktif = true and pd.idPd ='"+idPd+"'","mk.namaMK asc, pemb.nmPemb asc");
			UUID idSatMan = peranPengguna.getSatMan().getIdSatMan();
			KrsTransaction krsTransaction = new KrsTransaction();
			List<SatManMK> listSatManMK = new ArrayList<SatManMK>();
			for (Krs krs2 : listKrs) {
				List<SatManMK> query = satManMKService.get("mk.idMK = '"+krs2.getPemb().getMk().getIdMK()+"' and satManUntuk.idSatMan = '"+idSatMan+"'");
				if(query.size()>0) listSatManMK.add(query.get(0));
				else listSatManMK.add(null);
			}
			krsTransaction.setListKrs(listKrs);
			krsTransaction.setListSatManMK(listSatManMK);
			return new AjaxResponse("ok","Persetujuan berhasil",krsTransaction);
		}
	}

	@Override
	public TglSmt getPeriodeAktif() {
		List<TglSmt> listTglSmt = tglSmtService.get("tglSmt.aTglSmtAktif = true");
		
		return listTglSmt.size()==0?null:listTglSmt.get(0);
	}

	@Override
	public Boolean dapatDisusun(UUID idSatMan, UUID idPd) {
		TglSmt periodeAktif = getPeriodeAktif();
		if(periodeAktif == null ) return false;
		else{
			List<AturanPengganti> listAturanPengganti = aturanPenggantiService.get("tglSmt.idTglSmt = '"+periodeAktif.getIdTglSmt()+"' and satMan.idSatMan ='"+idSatMan+"'");
			if(listAturanPengganti.size()>0)
			{
				AturanPengganti aturanPengganti = listAturanPengganti.get(0);
				List<Pembayaran> listPembayaran = pembayaranService.get("tglSmt.idTglSmt = '"+periodeAktif.getIdTglSmt()+"' and pd.idPd ='"+idPd+"'");
				if(aturanPengganti.getTglAkhirBayar().isBefore(LocalDate.now()) && listPembayaran.size()==0)
					return false;
				if(aturanPengganti.getTglAwalSusunKrs()==null) return false;
				else if(aturanPengganti.getTglAwalSusunKrs().isAfter(LocalDate.now()) || aturanPengganti.getTglAkhirSusunKrs().isBefore(LocalDate.now()))
					return false;
				else return true;
			}
			else
			{
				List<Pembayaran> listPembayaran = pembayaranService.get("tglSmt.idTglSmt = '"+periodeAktif.getIdTglSmt()+"' and pd.idPd ='"+idPd+"'");
				if(periodeAktif.getTglAkhirBayar().isBefore(LocalDate.now()) && listPembayaran.size()==0)
					return false;
				if(periodeAktif.getTglAwalSusunKrs()==null) return false;
				else if(periodeAktif.getTglAwalSusunKrs().isAfter(LocalDate.now()) || periodeAktif.getTglAkhirSusunKrs().isBefore(LocalDate.now()))
					return false;
				else return true;
			}
		}
	}

	@Override
	public Boolean dapatDibatalkan(UUID idSatMan) {
		TglSmt periodeAktif = getPeriodeAktif();
		if(periodeAktif == null ) return false;
		else{
			List<AturanPengganti> listAturanPengganti = aturanPenggantiService.get("tglSmt.idTglSmt = '"+periodeAktif.getIdTglSmt()+"' and satMan.idSatMan ='"+idSatMan+"'");
			if(listAturanPengganti.size()>0)
			{
				AturanPengganti aturanPengganti = listAturanPengganti.get(0);
				if(aturanPengganti.getTglAkhirBatalMk()==null) return false;
				else if(aturanPengganti.getTglAkhirBatalMk().isBefore(LocalDate.now()))
					return false;
				else return true;
			}
			else
			{
				if(periodeAktif.getTglAkhirBatalMk()==null) return false;
				else if(periodeAktif.getTglAkhirBatalMk().isBefore(LocalDate.now()))
					return false;
				else return true;
			}
		}
	}

	@Override
	public Boolean dapatDirubah(UUID idSatMan) {
		TglSmt periodeAktif = getPeriodeAktif();
		if(periodeAktif == null ) return false;
		else{
			List<AturanPengganti> listAturanPengganti = aturanPenggantiService.get("tglSmt.idTglSmt = '"+periodeAktif.getIdTglSmt()+"' and satMan.idSatMan ='"+idSatMan+"'");
			if(listAturanPengganti.size()>0)
			{
				AturanPengganti aturanPengganti = listAturanPengganti.get(0);
				if(aturanPengganti.getTglAkhirUbahKrs()==null) return false;
				else if(aturanPengganti.getTglAkhirUbahKrs().isBefore(LocalDate.now()))
					return false;
				else return true;
			}
			else
			{
				if(periodeAktif.getTglAkhirUbahKrs()==null) return false;
				else if(periodeAktif.getTglAkhirUbahKrs().isBefore(LocalDate.now()))
					return false;
				else return true;
			}
		}
	}
	
	@Override
	public AjaxResponse pendidikambilmk(UUID idPemb, UUID idPtk, UUID idPd) {
		List<Pd> queryResult = pdService.get("pd.idPd ='"+idPd+"' AND ptk.idPtk='"+idPtk+"' ");
		if(queryResult.size()==0) return new AjaxResponse("error","Peserta Didik tidak diketahui",null);
		
		PeranPengguna peranPengguna = peranPenggunaService.getByPenggunaPeran(idPd,"Peserta Didik");
		if(!dapatDirubah(peranPengguna.getSatMan().getIdSatMan())) return new AjaxResponse("error","KRS tidak dapat dirubah",null);
		
		Pemb pemb = pembService.getById(idPemb);
		if(pemb.getKuota() == Integer.parseInt(krsService.count("krs.aKrsTerhapus = false and krs.aKrsBatal = false and pemb.idPemb ='"+pemb.getIdPemb()+"'").toString()))
			return new AjaxResponse("error","Kuota Penuh",null);
		
		List<Krs> queryKrs = krsService.get("krs.aKrsDisetujui = true and krs.aKrsTerhapus = false and tglSmt.aTglSmtAktif = true and pd.idPd ='"+idPd+"'");
		if(queryKrs.size()==0){
		
		if(pemb == null || pemb.getTglSmt().getaTglSmtAktif()==false) return new AjaxResponse("error","Pembelajaran tidak diketahui",null);
		
		Krs krs = new Krs();
		krs.setPd(pdService.getById(UUID.fromString(idPd.toString())));
		krs.setPemb(pemb);
		krs.setaKrsDisetujui(false);
		String idKrsSementara = krsService.save(krs);
		List<Krs> listKrs = krsService.get("krs.aKrsTerhapus = false and tglSmt.aTglSmtAktif = true and pd.idPd ='"+idPd+"'","mk.namaMK asc, pemb.nmPemb asc");
		UUID idSatMan = peranPengguna.getSatMan().getIdSatMan();
		KrsTransaction krsTransaction = new KrsTransaction();
		List<SatManMK> listSatManMK = new ArrayList<SatManMK>();
		for (Krs krs2 : listKrs) {
			List<SatManMK> query = satManMKService.get("mk.idMK = '"+krs2.getPemb().getMk().getIdMK()+"' and satManUntuk.idSatMan = '"+idSatMan+"'");
			if(query.size()>0) listSatManMK.add(query.get(0));
			else listSatManMK.add(null);
		}
		krsTransaction.setListKrs(listKrs);
		krsTransaction.setListSatManMK(listSatManMK);
		if(idKrsSementara==null) return new AjaxResponse("error","Matakuliah sudah diambil",null);
		else return new AjaxResponse("ok","Pembelajaran berhasil diambil",krsTransaction);
		
		}
		else return new AjaxResponse("error","KRS sudah di setujui. Batalkan terlebih dahulu",null);
	}

	@Override
	public AjaxResponse getKrsPeriode(UUID idThnAjaran, UUID idSmt, UUID idPd, UUID idSatMan) {
		KrsReport krsReport = new KrsReport();
		List<TglSmt> listTglSmt = tglSmtService.get("thnAjaran.idThnAjaran ='"+idThnAjaran+"' and smt.idSmt ='"+idSmt+"'");
		if(listTglSmt.size()==0) return new AjaxResponse("error","Data tidak di temukan",null);
		else
		{
			krsReport.setTglSmt(listTglSmt.get(0));
			if(krsReport.getTglSmt().getTglAwalSusunKrs()!=null)
			{
				krsReport.setDapatDisusun(true);
				if(LocalDate.now().isBefore(krsReport.getTglSmt().getTglAwalSusunKrs()) || LocalDate.now().isAfter(krsReport.getTglSmt().getTglAkhirSusunKrs())) krsReport.setDapatDisusun(false);
			}
			
			krsReport.setAwalPenyusunan(krsReport.getTglSmt().getTglAwalSusunKrs()!=null?krsReport.getTglSmt().getTglAwalSusunKrs().toString("dd-MM-yyyy"):"-");
			krsReport.setAkhirPenyusunan(krsReport.getTglSmt().getTglAkhirSusunKrs()!=null?krsReport.getTglSmt().getTglAkhirSusunKrs().toString("dd-MM-yyyy"):"-");
			krsReport.setAwalPerubahan(krsReport.getTglSmt().getTglAkhirSusunKrs()!=null?krsReport.getTglSmt().getTglAkhirSusunKrs().plusDays(1).toString("dd-MM-yyyy"):"-");
			krsReport.setAkhirPerubahan(krsReport.getTglSmt().getTglAkhirUbahKrs()!=null?krsReport.getTglSmt().getTglAkhirUbahKrs().toString("dd-MM-yyyy"):"-");
			krsReport.setAwalPembatalan(krsReport.getTglSmt().getTglAkhirUbahKrs()!=null && krsReport.getTglSmt().getTglAkhirBatalMk()!=null?krsReport.getTglSmt().getTglAkhirUbahKrs().plusDays(1).toString("dd-MM-yyyy"):"-");
			krsReport.setAkhirPembatalan(krsReport.getTglSmt().getTglAkhirBatalMk()!=null?krsReport.getTglSmt().getTglAkhirBatalMk().toString("dd-MM-yyyy"):"-");
			
			List<AturanPengganti> listAturanPengganti = aturanPenggantiService.get("tglSmt.idTglSmt ='"+krsReport.getTglSmt().getIdTglSmt()+"' and satMan.idSatMan ='"+idSatMan+"'");
			if(listAturanPengganti.size()>0)
			{
				System.out.print("asdadasdasdas\n");
				krsReport.setAturanPengganti(listAturanPengganti.get(0));
				if(krsReport.getAturanPengganti()!=null)
				{
					krsReport.setDapatDisusun(true);
					if(!dapatDisusun(idSatMan, idPd))krsReport.setDapatDisusun(false);
				}
				krsReport.setAwalPenyusunan(krsReport.getAturanPengganti().getTglAwalSusunKrs()!=null?krsReport.getAturanPengganti().getTglAwalSusunKrs().toString("dd-MM-yyyy"):"-");
				krsReport.setAkhirPenyusunan(krsReport.getAturanPengganti().getTglAkhirSusunKrs()!=null?krsReport.getAturanPengganti().getTglAkhirSusunKrs().toString("dd-MM-yyyy"):"-");
				krsReport.setAwalPerubahan(krsReport.getAturanPengganti().getTglAkhirSusunKrs()!=null?krsReport.getAturanPengganti().getTglAkhirSusunKrs().plusDays(1).toString("dd-MM-yyyy"):"-");
				krsReport.setAkhirPerubahan(krsReport.getAturanPengganti().getTglAkhirUbahKrs()!=null?krsReport.getAturanPengganti().getTglAkhirUbahKrs().toString("dd-MM-yyyy"):"-");
				krsReport.setAwalPembatalan(krsReport.getAturanPengganti().getTglAkhirUbahKrs()!=null && krsReport.getAturanPengganti().getTglAkhirBatalMk()!=null?krsReport.getAturanPengganti().getTglAkhirUbahKrs().plusDays(1).toString("dd-MM-yyyy"):"-");
				krsReport.setAkhirPembatalan(krsReport.getAturanPengganti().getTglAkhirBatalMk()!=null?krsReport.getAturanPengganti().getTglAkhirBatalMk().toString("dd-MM-yyyy"):"-");
				
			}
			Integer jenisSmt = krsReport.getTglSmt().getSmt().getJenisSmt();
			String where = "";
			if(jenisSmt == 0) where = "and thnAjaran.thnThnAjaran < "+krsReport.getTglSmt().getThnAjaran().getThnThnAjaran()+" and smt.jenisSmt = 1";
			
			else if(jenisSmt == 1) where ="and thnAjaran.thnThnAjaran = "+krsReport.getTglSmt().getThnAjaran().getThnThnAjaran()+" and smt.jenisSmt = 0";
			List<Ips> listIps = ipsService.get("pd.idPd = '"+idPd+"' "+where, "ips.tglBuatIps desc", 1, -1);
			if(listIps.size()>0) listIps.get(0).setNilaiIps(Math.round( listIps.get(0).getNilaiIps() * 100.0) / 100.0);
			krsReport.setIps(listIps.size()>0?listIps.get(0):null);

			List<Boolean> listKuisioner = new ArrayList<Boolean>();
			List<Krs> listKrs = krsService.get("pd.idPd = '"+idPd+"' and tglSmt.idTglSmt = '"+krsReport.getTglSmt().getIdTglSmt()+"' and krs.aKrsTerhapus = false and krs.aKrsBatal = false","mk.namaMK asc");
			List<SatManMK> listSatManMK = new ArrayList<SatManMK>();
			for (Krs krs : listKrs) {
				List<StatusKuisioner> listStatusKuisioner = statusKuisionerService.get("krs.idKrs='"+krs.getIdKrs()+"'");
				if(listStatusKuisioner.size()>0) listKuisioner.add(new Boolean(true));
				else listKuisioner.add(new Boolean(false));
				List<SatManMK> query = satManMKService.get("mk.idMK = '"+krs.getPemb().getMk().getIdMK()+"' and satManUntuk.idSatMan = '"+idSatMan+"'");
				if(query.size()>0) listSatManMK.add(query.get(0));
				else listSatManMK.add(null);
			}
			krsReport.setListKrs(listKrs);
			krsReport.setListSatManMK(listSatManMK);
			krsReport.setListKuisioner(listKuisioner);
			List<BatasAmbilSks> listBatas = batasAmbilSksService.get("batasBawahIps <= "+(krsReport.getIps()==null?"0":String.valueOf(krsReport.getIps().getNilaiIps())),"batasPengambilanSks desc");
			krsReport.setBatasPengambilan(listBatas.size()>0?listBatas.get(0):null);
			
			/*Memuat status pembayaran*/
			List<Pembayaran> listPembayaran = pembayaranService.get("tglSmt.idTglSmt = '"+krsReport.getTglSmt().getIdTglSmt()+"' and pd.idPd ='"+idPd+"'");
			krsReport.setStatusPembayaran(listPembayaran.size()>0?true:false);
			
			/*memuat tingkat mhs pada periode tersebut*/
			Pd pd = pdService.getById(idPd);
			Integer tingkat = (krsReport.getTglSmt().getThnAjaran().getThnThnAjaran()-pd.getAngkatanPd())*2;
			if(krsReport.getTglSmt().getSmt().getJenisSmt()==0)
				tingkat+=1;
			else if(krsReport.getTglSmt().getSmt().getJenisSmt()==1)
				tingkat+=2;
			else tingkat = null;
			krsReport.setTingkat(tingkat);
			return new AjaxResponse("ok","Data di temukan",krsReport);
		}
	}
	
	@Override
	public AjaxResponse ptkGetKrsPeriode(UUID idThnAjaran, UUID idSmt, UUID idPd, UUID idPtk) {
		List<Pd> queryResult = pdService.get("pd.idPd ='"+idPd+"' AND ptk.idPtk='"+idPtk+"' ");
		if(queryResult.size()==0) return new AjaxResponse("error","Peserta Didik tidak diketahui",null);
		
		PeranPengguna peranPengguna = peranPenggunaService.getByPenggunaPeran(idPd,"Peserta Didik");
		if(peranPengguna!=null) System.out.println(peranPengguna.getPeran().getNamaPeran());
		UUID idSatMan = peranPengguna.getSatMan().getIdSatMan();
		
		KrsReport krsReport = new KrsReport();
		List<TglSmt> listTglSmt = tglSmtService.get("thnAjaran.idThnAjaran ='"+idThnAjaran+"' and smt.idSmt ='"+idSmt+"'");
		if(listTglSmt.size()==0) return new AjaxResponse("error","Data tidak di temukan",null);
		else
		{
			krsReport.setTglSmt(listTglSmt.get(0));
			if(krsReport.getTglSmt().getTglAwalSusunKrs()!=null)
			{
				krsReport.setDapatDisusun(true);
				krsReport.setDapatDiubah(true);
				krsReport.setDapatDibatalkan(true);
				if(LocalDate.now().isBefore(krsReport.getTglSmt().getTglAwalSusunKrs()) || LocalDate.now().isAfter(krsReport.getTglSmt().getTglAkhirSusunKrs())) krsReport.setDapatDisusun(false);
				if(LocalDate.now().isAfter(krsReport.getTglSmt().getTglAkhirUbahKrs())) krsReport.setDapatDiubah(false);
				if(krsReport.getTglSmt().getTglAkhirBatalMk()==null || LocalDate.now().isAfter(krsReport.getTglSmt().getTglAkhirBatalMk())) krsReport.setDapatDibatalkan(false);
			}
			
			krsReport.setAwalPenyusunan(krsReport.getTglSmt().getTglAwalSusunKrs()!=null?krsReport.getTglSmt().getTglAwalSusunKrs().toString("dd-MM-yyyy"):"-");
			krsReport.setAkhirPenyusunan(krsReport.getTglSmt().getTglAkhirSusunKrs()!=null?krsReport.getTglSmt().getTglAkhirSusunKrs().toString("dd-MM-yyyy"):"-");
			krsReport.setAwalPerubahan(krsReport.getTglSmt().getTglAkhirSusunKrs()!=null?krsReport.getTglSmt().getTglAkhirSusunKrs().plusDays(1).toString("dd-MM-yyyy"):"-");
			krsReport.setAkhirPerubahan(krsReport.getTglSmt().getTglAkhirUbahKrs()!=null?krsReport.getTglSmt().getTglAkhirUbahKrs().toString("dd-MM-yyyy"):"");
			krsReport.setAwalPembatalan(krsReport.getTglSmt().getTglAkhirUbahKrs()!=null && krsReport.getTglSmt().getTglAkhirBatalMk()!=null?krsReport.getTglSmt().getTglAkhirUbahKrs().plusDays(1).toString("dd-MM-yyyy"):"-");
			krsReport.setAkhirPembatalan(krsReport.getTglSmt().getTglAkhirBatalMk()!=null?krsReport.getTglSmt().getTglAkhirBatalMk().toString("dd-MM-yyyy"):"-");
			
			List<AturanPengganti> listAturanPengganti = aturanPenggantiService.get("tglSmt.idTglSmt ='"+krsReport.getTglSmt().getIdTglSmt()+"' and satMan.idSatMan ='"+idSatMan+"'");
			if(listAturanPengganti.size()>0)
			{
				krsReport.setAturanPengganti(listAturanPengganti.get(0));
				if(krsReport.getAturanPengganti()!=null)
				{
					krsReport.setDapatDisusun(true);
					krsReport.setDapatDiubah(true);
					krsReport.setDapatDibatalkan(true);
					if(LocalDate.now().isBefore(krsReport.getAturanPengganti().getTglAwalSusunKrs()) || LocalDate.now().isAfter(krsReport.getAturanPengganti().getTglAkhirSusunKrs())) krsReport.setDapatDisusun(false);
					if(LocalDate.now().isAfter(krsReport.getAturanPengganti().getTglAkhirUbahKrs())) krsReport.setDapatDiubah(false);
					if(krsReport.getAturanPengganti().getTglAkhirBatalMk()==null || LocalDate.now().isAfter(krsReport.getAturanPengganti().getTglAkhirBatalMk())) krsReport.setDapatDibatalkan(false);
				
				}
				krsReport.setAwalPenyusunan(krsReport.getAturanPengganti().getTglAwalSusunKrs()!=null?krsReport.getAturanPengganti().getTglAwalSusunKrs().toString("dd-MM-yyyy"):"-");
				krsReport.setAkhirPenyusunan(krsReport.getAturanPengganti().getTglAkhirSusunKrs()!=null?krsReport.getAturanPengganti().getTglAkhirSusunKrs().toString("dd-MM-yyyy"):"-");
				krsReport.setAwalPerubahan(krsReport.getAturanPengganti().getTglAkhirSusunKrs()!=null?krsReport.getAturanPengganti().getTglAkhirSusunKrs().plusDays(1).toString("dd-MM-yyyy"):"-");
				krsReport.setAkhirPerubahan(krsReport.getAturanPengganti().getTglAkhirUbahKrs()!=null?krsReport.getAturanPengganti().getTglAkhirUbahKrs().toString("dd-MM-yyyy"):"-");
				krsReport.setAwalPembatalan(krsReport.getAturanPengganti().getTglAkhirUbahKrs()!=null && krsReport.getAturanPengganti().getTglAkhirBatalMk()!=null?krsReport.getAturanPengganti().getTglAkhirUbahKrs().plusDays(1).toString("dd-MM-yyyy"):"-");
				krsReport.setAkhirPembatalan(krsReport.getAturanPengganti().getTglAkhirBatalMk()!=null?krsReport.getAturanPengganti().getTglAkhirBatalMk().toString("dd-MM-yyyy"):"-");
				
			}
			Integer jenisSmt = krsReport.getTglSmt().getSmt().getJenisSmt();
			String where = "and thnAjaran.thnThnAjaran <= "+krsReport.getTglSmt().getThnAjaran().getThnThnAjaran()+"";
			if(jenisSmt == 0) where = "and thnAjaran.thnThnAjaran < "+krsReport.getTglSmt().getThnAjaran().getThnThnAjaran()+" and smt.jenisSmt = 1";
			else if(jenisSmt == 1) where ="and thnAjaran.thnThnAjaran = "+krsReport.getTglSmt().getThnAjaran().getThnThnAjaran()+" and smt.jenisSmt = 0";
			List<Ips> listIps = ipsService.get("pd.idPd = '"+idPd+"' "+where, "ips.tglBuatIps desc", 1, -1);
			if(listIps.size()>0) listIps.get(0).setNilaiIps(Math.round( listIps.get(0).getNilaiIps() * 100.0) / 100.0);
			krsReport.setIps(listIps.size()>0?listIps.get(0):null);
			
			List<Boolean> listKuisioner = new ArrayList<Boolean>();
			List<Krs> listKrs = krsService.get("pd.idPd = '"+idPd+"' and tglSmt.idTglSmt = '"+krsReport.getTglSmt().getIdTglSmt()+"' and krs.aKrsTerhapus = false","mk.namaMK asc");
			List<SatManMK> listSatManMK = new ArrayList<SatManMK>();
			for (Krs krs : listKrs) {
				List<StatusKuisioner> listStatusKuisioner = statusKuisionerService.get("krs.idKrs='"+krs.getIdKrs()+"'");
				if(listStatusKuisioner.size()>0) listKuisioner.add(new Boolean(true));
				else listKuisioner.add(new Boolean(false));
				List<SatManMK> query = satManMKService.get("mk.idMK = '"+krs.getPemb().getMk().getIdMK()+"' and satManUntuk.idSatMan = '"+idSatMan+"'");
				if(query.size()>0) listSatManMK.add(query.get(0));
				else listSatManMK.add(null);
			}
			krsReport.setListKrs(listKrs);
			krsReport.setListSatManMK(listSatManMK);
			krsReport.setListKuisioner(listKuisioner);
			List<BatasAmbilSks> listBatas = batasAmbilSksService.get("batasBawahIps <= "+(krsReport.getIps()==null?"0":String.valueOf(krsReport.getIps().getNilaiIps())),"batasPengambilanSks desc");
			krsReport.setBatasPengambilan(listBatas.size()>0?listBatas.get(0):null);
			
			/*memuat ipk*/
			Ipk ipk = ipkService.getByPd(pdService.getById(idPd));
			if(ipk != null) 
			{
				ipk.setNilaiIpk( Math.round( ipk.getNilaiIpk() * 100.0) / 100.0);	
			}
			krsReport.setIpk(ipk);
			
			/*Memuat status pembayaran*/
			List<Pembayaran> listPembayaran = pembayaranService.get("tglSmt.idTglSmt = '"+krsReport.getTglSmt().getIdTglSmt()+"' and pd.idPd ='"+idPd+"'");
			krsReport.setStatusPembayaran(listPembayaran.size()>0?true:false);
			
			/*memuat tingkat mhs pada periode tersebut*/
			Pd pd = pdService.getById(idPd);
			Integer tingkat = (krsReport.getTglSmt().getThnAjaran().getThnThnAjaran()-pd.getAngkatanPd())*2;
			if(krsReport.getTglSmt().getSmt().getJenisSmt()==0)
				tingkat+=1;
			else if(krsReport.getTglSmt().getSmt().getJenisSmt()==1)
				tingkat+=2;
			else tingkat = null;
			krsReport.setTingkat(tingkat);
			
			List<SatMan> listSatman = pembService.getSatManKurikulum("satMan.idSatMan ='"+idSatMan+"' and tglSmt.aTglSmtAktif = true");
			
			krsReport.setListSatMan(listSatman);
			/*Memuat Pembelajaran*/
			List<List<Pemb>> listListPemb = new ArrayList<List<Pemb>>();
			for (SatMan satManleaf : listSatman) {
				List<Pemb> listPemb = pembService.getPembInSatMan("tglSmt.aTglSmtAktif = true and satMan.idSatMan ='"+idSatMan+"' "
						+ "and milikSatMan.idSatMan = '"+satManleaf.getIdSatMan()+"' and pemb.aPembTerhapus = false ","mk.namaMK asc, pemb.nmPemb asc");
				listListPemb.add(listPemb);
			}
			krsReport.setListListPemb(listListPemb);
			
			return new AjaxResponse("ok","Data di temukan",krsReport);
		}
	}
	
}
