package com.sia.main.web.controllers;

import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.sia.main.domain.Peran;
import com.sia.main.plugin.common.Response;
import com.sia.main.service.services.PeranService;

@Controller
@Secured(value = { "ROLE_System Admin" })
@RequestMapping(value = "/admin/role")
public class RoleController {
	
	@Autowired
	PeranService peranService;
	
	@RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
	public ModelAndView kelolaPeran(HttpSession session) {
		ModelAndView modelAndView = new ModelAndView();
		if(session.getAttribute("addResponse") != null) {
			modelAndView.addObject("addResponse", session.getAttribute("addResponse"));
			session.removeAttribute("addResponse");
		}
		modelAndView.addObject("menuActive", "Kelola Peran");
		modelAndView.addObject("daftarPeran", peranService.getAll());
		modelAndView.setViewName("PengelolaanPeran");
		return modelAndView;
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ModelAndView tambahPeran(@RequestParam("namaPeran") String namaPeran, HttpSession session) {
		ModelAndView modelAndView = new ModelAndView();
		Response response;
		Peran peran = new Peran();
		peran.setNamaPeran(namaPeran);
		Peran res = peranService.insertInto(peran);
		if(res != null) {
			response = new Response("ok", "peran berhasil ditambahkan", res);
		} else {
			response = new Response("error", "peran gagal ditambahkan", null);
		}
		session.setAttribute("addResponse", response);
		modelAndView.setViewName("redirect:/admin/role/");
		return modelAndView;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ModelAndView ubahPeran(@RequestParam("idPeran") String idPeran, @RequestParam("namaPeran") String namaPeran, HttpSession session) {
		ModelAndView modelAndView = new ModelAndView();
		Response response;
		Peran peran = new Peran();
		peran.setIdPeran(UUID.fromString(idPeran));
		peran.setNamaPeran(namaPeran);
		Peran res = peranService.update(peran);
		if(res != null) {
			response = new Response("ok", "peran " + peran.getNamaPeran() + " berhasil diubah", res);
		} else {
			response = new Response("error","peran " + peran.getNamaPeran() + " gagal diubah", null );
		}
		session.setAttribute("addResponse", response);
		modelAndView.setViewName("redirect:/admin/role/");
		return modelAndView;
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public ModelAndView hapusPeran(@RequestParam("idPeran") String idPeran, HttpSession session) {
		ModelAndView modelAndView = new ModelAndView();
		Response response;
		Peran peran = peranService.getById(UUID.fromString(idPeran));
		Peran res = peranService.delete(peran);
		if(res != null) {
			response = new Response("ok", "peran " + peran.getNamaPeran() + " berhasil dihapus", res);
		} else {
			response = new Response("error","peran " + peran.getNamaPeran() + " gagal dihapus", null );
		}
		session.setAttribute("addResponse", response);
		modelAndView.setViewName("redirect:/admin/role/");
		return modelAndView;
	}
	
}
