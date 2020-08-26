package com.its.sia.service;

import java.util.UUID;

import com.sia.modul.domain.TglSmt;

public interface ManajemenKrsService {
	AjaxResponse ambilmk(UUID idPemb,UUID idPd,UUID idSatMan);
	AjaxResponse hapusmk(UUID idKrs,UUID idPd,UUID idSatMan);
	AjaxResponse dosenhapusmk(UUID idKrs,UUID idPtk,UUID idPd);
	AjaxResponse pendidikambilmk(UUID idPemb,UUID idPtk,UUID idPd);
	AjaxResponse batalmk(UUID idKrs,UUID idPtk,UUID idPd);
	AjaxResponse getkrs(UUID idPd,UUID idPtk);
	AjaxResponse setujuikrs(UUID idPd,UUID idPtk);
	AjaxResponse batalsetuju(UUID idPd,UUID idPtk);
	AjaxResponse getKrsPeriode(UUID idThnAjaran,UUID idSmt,UUID idPd, UUID idSatMan);
	AjaxResponse ptkGetKrsPeriode(UUID idThnAjaran,UUID idSmt,UUID idPd,UUID idPtk);
	TglSmt getPeriodeAktif();
	Boolean dapatDisusun(UUID idSatMan,UUID idPd);
	Boolean dapatDirubah(UUID idSatMan);
	Boolean dapatDibatalkan(UUID idSatMan);
}
