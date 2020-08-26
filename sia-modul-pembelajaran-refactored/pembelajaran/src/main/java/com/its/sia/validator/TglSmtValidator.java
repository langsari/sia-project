package com.its.sia.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.sia.modul.domain.TglSmt;
import com.sia.modul.domain.ThnAjaran;

public class TglSmtValidator implements Validator{

	@Override
	public boolean supports(Class<?> arg0) {
		// TODO Auto-generated method stub
		return ThnAjaran.class.equals(arg0);
	}

	@Override
	public void validate(Object thnAjaran, Errors errors) {
		// TODO Auto-generated method stub
		
		System.out.println("VALIDATING");
		
		TglSmt thnAjaranValid = (TglSmt)thnAjaran;
		if(thnAjaranValid.getThnAjaran() == null) {
			System.out.println("SATU");
			errors.rejectValue("thnAjaran","ThnAjaranNull","Tahun Ajaran tidak boleh kosong");
		} 
		if(thnAjaranValid.getSmt() == null) {
			System.out.println("DUA");
			errors.rejectValue("smt","SmtNull","Semester tidak boleh kosong");
		}
		if(thnAjaranValid.getTglAwalSusunKrs() == null && (thnAjaranValid.getTglAkhirSusunKrs()!=null || thnAjaranValid.getTglAkhirUbahKrs()!=null)) {
			System.out.println("EMPAT");
			errors.rejectValue("tglAwalSusunKrs","tglAwalSusunKrsNull","Tanggal awal penyusunan krs tidak boleh kosong");
		}
		if((thnAjaranValid.getTglAwalSusunKrs() != null || thnAjaranValid.getTglAkhirUbahKrs()!=null) && thnAjaranValid.getTglAkhirSusunKrs()==null) {
			System.out.println("EMPAT");
			errors.rejectValue("tglAkhirSusunKrs","tglAkhirSusunKrsNull","Tanggal akhir penyusunan krs tidak boleh kosong");
		}
		if((thnAjaranValid.getTglAwalSusunKrs() != null || thnAjaranValid.getTglAkhirSusunKrs()!=null) && thnAjaranValid.getTglAkhirUbahKrs()==null) {
			System.out.println("LIMA");
			errors.rejectValue("tglAkhirUbahKrs","tglAkhirUbahKrsNull","Tanggal akhir perubahan krs tidak boleh kosong");
		}
		
		if(thnAjaranValid.getTglAwalSusunKrs() != null && thnAjaranValid.getTglAkhirSusunKrs()!=null && (thnAjaranValid.getTglAwalSusunKrs().isAfter(thnAjaranValid.getTglAkhirSusunKrs()) || thnAjaranValid.getTglAwalSusunKrs().isEqual(thnAjaranValid.getTglAkhirSusunKrs()))) {
			System.out.println("ENAM");
			errors.rejectValue("tglAkhirSusunKrs","tglAkhirSusunKrsExecption","Tanggal akhir penyusunan KRS tidak boleh kurang dari tanggal awal");
		}
			
		
		if(thnAjaranValid.getTglAkhirSusunKrs() != null && thnAjaranValid.getTglAkhirUbahKrs()!=null && (thnAjaranValid.getTglAkhirSusunKrs().isAfter( thnAjaranValid.getTglAkhirUbahKrs()) || thnAjaranValid.getTglAkhirUbahKrs().isEqual(thnAjaranValid.getTglAkhirSusunKrs()))) {
			System.out.println("TUJUH");
			errors.rejectValue("tglAkhirUbahKrs","tglAkhirUbahKrsExecption","Tanggal akhir perubahan KRS tidak boleh kurang dari tanggal akhir penyusunan KRS");
		}
		if(thnAjaranValid.getTglAkhirUbahKrs() != null && thnAjaranValid.getTglAkhirBatalMk()!=null && (thnAjaranValid.getTglAkhirUbahKrs().isAfter(thnAjaranValid.getTglAkhirBatalMk()) || thnAjaranValid.getTglAkhirBatalMk().isEqual(thnAjaranValid.getTglAkhirUbahKrs()))) {
			System.out.println("DELAPAN");
			errors.rejectValue("tglAkhirBatalMk","tglAkhirBatalMkExecption","Tanggal akhir pembatalan MK tidak boleh kurang dari tanggal akhir persetujuan KRS");
		}
		if(thnAjaranValid.getTglAkhirUbahKrs() != null && thnAjaranValid.getTglAkhirPenilaian()!=null && (thnAjaranValid.getTglAkhirUbahKrs().isAfter(thnAjaranValid.getTglAkhirPenilaian()) || thnAjaranValid.getTglAkhirUbahKrs().isEqual(thnAjaranValid.getTglAkhirPenilaian()))) {  
			System.out.println("SEMBILAN");
			errors.rejectValue("tglAkhirPenilaian","tglAkhirPenilaianExecption","Tanggal akhir penilaian tidak boleh kurang dari tanggal akhir persetujuan KRS");
		}
		if(thnAjaranValid.getTglAkhirBatalMk() != null && thnAjaranValid.getTglAkhirPenilaian()!=null && (thnAjaranValid.getTglAkhirBatalMk().isAfter(thnAjaranValid.getTglAkhirPenilaian()) || thnAjaranValid.getTglAkhirBatalMk().isEqual(thnAjaranValid.getTglAkhirPenilaian()))) {
			System.out.println("SEMBILAN");
			errors.rejectValue("tglAkhirPenilaian","tglAkhirPenilaianExecption","Tanggal akhir penilaian tidak boleh kurang dari tanggal akhir pembatalan MK");
		}
	}

}
