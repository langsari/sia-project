package com.its.sia.model;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class TglSmtValidator implements Validator{

	@Override
	public boolean supports(Class<?> arg0) {
		// TODO Auto-generated method stub
		return ThnAjaran.class.equals(arg0);
	}

	@Override
	public void validate(Object thnAjaran, Errors errors) {
		// TODO Auto-generated method stub
		TglSmt thnAjaranValid = (TglSmt)thnAjaran;
		if(thnAjaranValid.getThnAjaran() == null)  errors.rejectValue("thnAjaran","ThnAjaranNull","Tahun Ajaran tidak boleh kosong");
		if(thnAjaranValid.getSmt() == null)  errors.rejectValue("smt","SmtNull","Semester tidak boleh kosong");
		if(thnAjaranValid.getTglAwalSusunKrs() == null && (thnAjaranValid.getTglAkhirSusunKrs()!=null || thnAjaranValid.getTglAkhirSetujuKrs()!=null))  errors.rejectValue("tglAwalSusunKrs","tglAwalSusunKrsNull","Tanggal awal penyusunan krs tidak boleh kosong");
		if((thnAjaranValid.getTglAwalSusunKrs() != null || thnAjaranValid.getTglAkhirSetujuKrs()!=null) && thnAjaranValid.getTglAkhirSusunKrs()==null)  errors.rejectValue("tglAkhirSusunKrs","tglAkhirSusunKrsNull","Tanggal akhir penyusunan krs tidak boleh kosong");
		if((thnAjaranValid.getTglAwalSusunKrs() != null || thnAjaranValid.getTglAkhirSusunKrs()!=null) && thnAjaranValid.getTglAkhirSetujuKrs()==null)  errors.rejectValue("tglAkhirSetujuKrs","tglAkhirSetujuKrsNull","Tanggal akhir persetujuan krs tidak boleh kosong");
		
		if(thnAjaranValid.getTglAwalSusunKrs() != null && thnAjaranValid.getTglAkhirSusunKrs()!=null && (thnAjaranValid.getTglAwalSusunKrs().isAfter(thnAjaranValid.getTglAkhirSusunKrs()) || thnAjaranValid.getTglAwalSusunKrs().isEqual(thnAjaranValid.getTglAkhirSusunKrs())))  
			errors.rejectValue("tglAkhirSusunKrs","tglAkhirSusunKrsExecption","Tanggal akhir penyusunan KRS tidak boleh kurang dari tanggal awal");
		
		if(thnAjaranValid.getTglAkhirSusunKrs() != null && thnAjaranValid.getTglAkhirSetujuKrs()!=null && (thnAjaranValid.getTglAkhirSusunKrs().isAfter( thnAjaranValid.getTglAkhirSetujuKrs()) || thnAjaranValid.getTglAkhirSetujuKrs().isEqual(thnAjaranValid.getTglAkhirSusunKrs())))  
			errors.rejectValue("tglAkhirSetujuKrs","tglAkhirSetujuKrsExecption","Tanggal akhir persetujuan KRS tidak boleh kurang dari tanggal akhir penyusunan KRS");
		
		if(thnAjaranValid.getTglAkhirSetujuKrs() != null && thnAjaranValid.getTglAkhirBatalMk()!=null && (thnAjaranValid.getTglAkhirSetujuKrs().isAfter(thnAjaranValid.getTglAkhirBatalMk()) || thnAjaranValid.getTglAkhirBatalMk().isEqual(thnAjaranValid.getTglAkhirSetujuKrs())))  
			errors.rejectValue("tglAkhirBatalMk","tglAkhirBatalMkExecption","Tanggal akhir pembatalan MK tidak boleh kurang dari tanggal akhir persetujuan KRS");

		if(thnAjaranValid.getTglAkhirSetujuKrs() != null && thnAjaranValid.getTglAkhirPenilaian()!=null && (thnAjaranValid.getTglAkhirSetujuKrs().isAfter(thnAjaranValid.getTglAkhirPenilaian()) || thnAjaranValid.getTglAkhirSetujuKrs().isEqual(thnAjaranValid.getTglAkhirPenilaian())))  
			errors.rejectValue("tglAkhirPenilaian","tglAkhirPenilaianExecption","Tanggal akhir penilaian tidak boleh kurang dari tanggal akhir persetujuan KRS");
		
		if(thnAjaranValid.getTglAkhirBatalMk() != null && thnAjaranValid.getTglAkhirPenilaian()!=null && (thnAjaranValid.getTglAkhirBatalMk().isAfter(thnAjaranValid.getTglAkhirPenilaian()) || thnAjaranValid.getTglAkhirBatalMk().isEqual(thnAjaranValid.getTglAkhirPenilaian())))  
			errors.rejectValue("tglAkhirPenilaian","tglAkhirPenilaianExecption","Tanggal akhir penilaian tidak boleh kurang dari tanggal akhir pembatalan MK");
		
	}

}
