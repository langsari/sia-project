package com.its.sia.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.sia.modul.domain.AturanPengganti;
import com.sia.modul.domain.TglSmt;
import com.sia.modul.domain.ThnAjaran;

public class AturanPenggantiValidator implements Validator{

	@Override
	public boolean supports(Class<?> arg0) {
		// TODO Auto-generated method stub
		return AturanPengganti.class.equals(arg0);
	}

	@Override
	public void validate(Object aturanPengganti, Errors errors) {
		// TODO Auto-generated method stub
		AturanPengganti aturanPenggantiValid = (AturanPengganti)aturanPengganti;
		if(aturanPenggantiValid.getTglSmt() == null)  errors.rejectValue("tglSmt","TglSmtNull","Periode tidak boleh kosong");
		if(aturanPenggantiValid.getSatMan() == null)  errors.rejectValue("satMan","SatManNull","Satuan Manajemen tidak boleh kosong");
		if(aturanPenggantiValid.getTglAwalSusunKrs() == null && (aturanPenggantiValid.getTglAkhirSusunKrs()!=null || aturanPenggantiValid.getTglAkhirUbahKrs()!=null))  errors.rejectValue("tglAwalSusunKrs","tglAwalSusunKrsNull","Tanggal awal penyusunan krs tidak boleh kosong");
		if((aturanPenggantiValid.getTglAwalSusunKrs() != null || aturanPenggantiValid.getTglAkhirUbahKrs()!=null) && aturanPenggantiValid.getTglAkhirSusunKrs()==null)  errors.rejectValue("tglAkhirSusunKrs","tglAkhirSusunKrsNull","Tanggal akhir penyusunan krs tidak boleh kosong");
		if((aturanPenggantiValid.getTglAwalSusunKrs() != null || aturanPenggantiValid.getTglAkhirSusunKrs()!=null) && aturanPenggantiValid.getTglAkhirUbahKrs()==null)  errors.rejectValue("tglAkhirUbahKrs","tglAkhirUbahKrsNull","Tanggal akhir perubahan krs tidak boleh kosong");
		
		if(aturanPenggantiValid.getTglAwalSusunKrs() != null && aturanPenggantiValid.getTglAkhirSusunKrs()!=null && (aturanPenggantiValid.getTglAwalSusunKrs().isAfter(aturanPenggantiValid.getTglAkhirSusunKrs()) || aturanPenggantiValid.getTglAwalSusunKrs().isEqual(aturanPenggantiValid.getTglAkhirSusunKrs())))  
			errors.rejectValue("tglAkhirSusunKrs","tglAkhirSusunKrsExecption","Tanggal akhir penyusunan KRS tidak boleh kurang dari tanggal awal");
		
		if(aturanPenggantiValid.getTglAkhirSusunKrs() != null && aturanPenggantiValid.getTglAkhirUbahKrs()!=null && (aturanPenggantiValid.getTglAkhirSusunKrs().isAfter( aturanPenggantiValid.getTglAkhirUbahKrs()) || aturanPenggantiValid.getTglAkhirUbahKrs().isEqual(aturanPenggantiValid.getTglAkhirSusunKrs())))  
			errors.rejectValue("tglAkhirUbahKrs","tglAkhirUbahKrsExecption","Tanggal akhir perubahan KRS tidak boleh kurang dari tanggal akhir penyusunan KRS");
		
		if(aturanPenggantiValid.getTglAkhirUbahKrs() != null && aturanPenggantiValid.getTglAkhirBatalMk()!=null && (aturanPenggantiValid.getTglAkhirUbahKrs().isAfter(aturanPenggantiValid.getTglAkhirBatalMk()) || aturanPenggantiValid.getTglAkhirBatalMk().isEqual(aturanPenggantiValid.getTglAkhirUbahKrs())))  
			errors.rejectValue("tglAkhirBatalMk","tglAkhirBatalMkExecption","Tanggal akhir pembatalan MK tidak boleh kurang dari tanggal akhir persetujuan KRS");

		if(aturanPenggantiValid.getTglAkhirUbahKrs() != null && aturanPenggantiValid.getTglAkhirPenilaian()!=null && (aturanPenggantiValid.getTglAkhirUbahKrs().isAfter(aturanPenggantiValid.getTglAkhirPenilaian()) || aturanPenggantiValid.getTglAkhirUbahKrs().isEqual(aturanPenggantiValid.getTglAkhirPenilaian())))  
			errors.rejectValue("tglAkhirPenilaian","tglAkhirPenilaianExecption","Tanggal akhir penilaian tidak boleh kurang dari tanggal akhir persetujuan KRS");
		
		if(aturanPenggantiValid.getTglAkhirBatalMk() != null && aturanPenggantiValid.getTglAkhirPenilaian()!=null && (aturanPenggantiValid.getTglAkhirBatalMk().isAfter(aturanPenggantiValid.getTglAkhirPenilaian()) || aturanPenggantiValid.getTglAkhirBatalMk().isEqual(aturanPenggantiValid.getTglAkhirPenilaian())))  
			errors.rejectValue("tglAkhirPenilaian","tglAkhirPenilaianExecption","Tanggal akhir penilaian tidak boleh kurang dari tanggal akhir pembatalan MK");
		
	}

}
