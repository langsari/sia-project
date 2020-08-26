package com.siakad.modul_penilaian.controller;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.siakad.modul_penilaian.service.AjaxResponse;

@Controller
public class ControllerFile {
	
	@RequestMapping(value = "/kelola_nilai/unggah_file/", method = RequestMethod.POST)
	public @ResponseBody AjaxResponse unggahFile(@RequestParam("file") Object fileExcel, @RequestParam("daftarKomponen") String[] daftarKomponen) throws Exception {
		MultipartFile multipartFile = (MultipartFile)fileExcel;
		if(multipartFile != null) {
			try {
				InputStream inputStream = multipartFile.getInputStream();
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
				
				boolean apakahKomponenSesuai = true;
				
				String line = "";
				line = bufferedReader.readLine();
				String[] barisPertama = line.split(",");
				for(int i=0; i<daftarKomponen.length; i++) {
					barisPertama[i+2] = barisPertama[i+2].replaceAll("^\"|\"$", "");
					System.out.println(daftarKomponen[i] + " -> " + barisPertama[i+2]);
					if(!daftarKomponen[i].equalsIgnoreCase(barisPertama[i+2]))
						apakahKomponenSesuai = false;
				}
				
				if(apakahKomponenSesuai) {
					List<String[]> daftarString = new ArrayList<String[]>();
					while((line = bufferedReader.readLine()) != null) {
						String[] elemen = line.split(",");
						for (int i=0; i<elemen.length; i++) {
							elemen[i] = elemen[i].replaceAll("^\"|\"$", "");
						}
						daftarString.add(elemen);
					}
					for (String[] strings : daftarString) {
						for(int i=0; i<strings.length; i++) {
							System.out.print(strings[i] + " ");
						}
						System.out.println();
					}
					bufferedReader.close();
					return new AjaxResponse("ok", "File berhasil diupload", daftarString);
				}
				else {
					return new AjaxResponse("warning", "Komponen nilai pada file tidak sesuai", null);
				}
			}
			catch(Exception ex) {
				System.out.println(ex.getMessage());
				return new AjaxResponse("fail", "Terjadi error pada server", null);
			}
		}
		else {
			return new AjaxResponse("fail", "File tidak ditemukan", null);
		}
	}
}
