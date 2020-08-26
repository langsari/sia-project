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

import com.sia.main.data.dao.PenggunaDAO;
import com.sia.main.data.dao.PeranDAO;
import com.sia.main.data.dao.SatuanManajemenDAO;
import com.sia.main.domain.Pengguna;
import com.sia.main.domain.Peran;
import com.sia.main.domain.PeranPengguna;
import com.sia.main.domain.SatMan;
import com.sia.main.plugin.common.Response;
import com.sia.main.service.services.PeranPenggunaService;

@Controller
@Secured(value = { "ROLE_System Admin" })
@RequestMapping(value = "/admin/userRole")
public class UserRoleController {

	@Autowired
	private PeranPenggunaService peranPenggunaService;
	
	@Autowired
	private PeranDAO peranDAO;
	
	@Autowired
	private SatuanManajemenDAO satManDAO;
	
	@Autowired
	private PenggunaDAO penggunaDAO;
	
	@RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
	public ModelAndView home(@RequestParam(value = "userId", required = false) UUID userId, HttpSession session) {
		ModelAndView modelAndView = new ModelAndView();
		if(userId != null && !userId.equals("")) {
			Pengguna pengguna = this.penggunaDAO.getById(userId);
			modelAndView.addObject("user", pengguna);
			modelAndView.addObject("peranList", peranDAO.getByParam("where tipePengguna.idTipe = '" + pengguna.getTipePengguna().getIdTipe() + "'"));
			if(pengguna.getTipePengguna().getNamaTipe().toLowerCase().equals("pd")) {
				modelAndView.addObject("satManList", satManDAO.getByParam("where aSatManProdi = " + true));
			} else {
				modelAndView.addObject("satManList", satManDAO.getAll());
			}
			modelAndView.addObject("peranPenggunaList", this.peranPenggunaService.getByParam("where pengguna.idPengguna = '" + userId + "'"));
			if(session.getAttribute("addResponse") != null) {
				modelAndView.addObject("addResponse", session.getAttribute("addResponse"));
				session.removeAttribute("addResponse");
			}
		}
		modelAndView.addObject("menuActive", "Kelola Hak Akses Pengguna");
		modelAndView.setViewName("PengelolaanPeranPengguna");
		return modelAndView;
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ModelAndView add(@RequestParam("id") UUID userId, @RequestParam("peran") String namaPeran, @RequestParam("satMan") String namaSatMan, HttpSession session) {
		ModelAndView modelAndView = new ModelAndView();
		Pengguna pengguna = this.penggunaDAO.getById(userId);
		Peran peran = this.peranDAO.getByParam("where namaPeran = '" + namaPeran + "'").get(0);
		SatMan satMan = this.satManDAO.getByParam("where nmSatMan = '" + namaSatMan + "'").get(0);
		PeranPengguna peranPengguna = new PeranPengguna(null, pengguna, peran, satMan);
		if(peranPenggunaService.insertInto(peranPengguna) != null) {
			Response response = new Response("success", "peran pengguna berhasil ditambah", null);
			session.setAttribute("addResponse", response);
		} else {
			Response response = new Response("error", "peran pengguna gagal ditambah", null);
			session.setAttribute("addResponse", response);
		}
		modelAndView.setViewName("redirect:/admin/userRole?userId=" + userId);
		return modelAndView;
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public ModelAndView delete(@RequestParam("id") UUID peranPenggunaId, HttpSession session) {
		ModelAndView modelAndView = new ModelAndView();
		PeranPengguna peranPengguna = this.peranPenggunaService.getById(peranPenggunaId);
		if(this.peranPenggunaService.delete(peranPengguna) != null) {
			Response response = new Response("success", "peran pengguna berhasil dihapus", null);
			session.setAttribute("addResponse", response);
		} else {
			Response response = new Response("error", "peran pengguna gagal dihapus", null);
			session.setAttribute("addResponse", response);
		}
		modelAndView.setViewName("redirect:/admin/userRole?userId=" + peranPengguna.getPengguna().getIdPengguna());
		return modelAndView;
	}
	
}
