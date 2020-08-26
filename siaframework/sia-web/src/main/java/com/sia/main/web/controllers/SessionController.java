package com.sia.main.web.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sia.main.data.dao.BasicDAO;
import com.sia.main.domain.Menu;
import com.sia.main.domain.MenuPeran;
import com.sia.main.domain.Modul;
import com.sia.main.domain.Pengguna;
import com.sia.main.domain.Peran;
import com.sia.main.domain.PeranPengguna;
import com.sia.main.plugin.common.Response;
import com.sia.main.service.services.MenuPeranService;
import com.sia.main.service.services.PeranPenggunaService;
import com.sia.main.web.AdministratorModuleManager;
import com.sia.main.web.json_model.Role;
import com.sia.main.web.json_model.SatMan;
import com.sia.main.web.security.SIAUser;

@Controller
@RequestMapping(value = "/session")
public class SessionController {
	
	@Autowired
	private PeranPenggunaService peranPenggunaService;
	
	@Autowired
	private MenuPeranService menuPeranService;
	
	@Autowired
	private BasicDAO basicDAO;
	
	@RequestMapping(value = "/chooseUserRole", method = RequestMethod.GET)
	public ModelAndView showAvailableRole(HttpSession session) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			SIAUser user = (SIAUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Pengguna pengguna = user.getUserDetail();
			List<PeranPengguna> peranPenggunaList = this.peranPenggunaService.getByParam("where pengguna.idPengguna = '" + pengguna.getIdPengguna() + "'");
			if(peranPenggunaList == null || peranPenggunaList.size() == 0) throw new NullPointerException(); 
			List<Peran> roleList = new ArrayList<Peran>();
			for(Object obj: this.basicDAO.getObjects("select distinct pp.peran from PeranPengguna pp where pp.pengguna.idPengguna = '" + pengguna.getIdPengguna() + "'")) {
				roleList.add((Peran) obj);
			}
			if(peranPenggunaList.size() > 1) {
				modelAndView.setViewName("PilihPeran");
				modelAndView.addObject("peranList", roleList);
				if(session.getAttribute("userRoleFail") != null) {
					modelAndView.addObject("userRoleFail", (Response)session.getAttribute("userRoleFail"));
					session.removeAttribute("userRoleFail");
				} 
			} else {
				PeranPengguna peranPengguna = peranPenggunaList.get(0);
				session.setAttribute("userRoleSession", peranPengguna);
				if(peranPengguna.getPeran().getNamaPeran().equals("System Admin")) {
					session.setAttribute("moduleSession", AdministratorModuleManager.getInstance().getModules());
				} else {
					session.setAttribute("moduleSession", buildRoleModules(peranPenggunaList.get(0).getPeran().getIdPeran()));
					session.setAttribute("modulUrlPrefix", "/modul");
				}
				if(session.getAttribute("savedRequestUrl") != null) {
					modelAndView.setViewName("redirect:" + (String)session.getAttribute("savedRequestUrl"));
				} else {
					modelAndView.setViewName("redirect:/home");
				}					
			}
		} catch(NullPointerException npe) {
			npe.printStackTrace();
			modelAndView.setViewName("redirect:/j_spring_security_logout");
		}
		return modelAndView;
	}

	@RequestMapping(value = "/getSatMan", method = RequestMethod.POST)
	public @ResponseBody SatMan[] getSatMan(@RequestParam("idPeran") UUID idPeran, @RequestParam("idPengguna") UUID idPengguna) {
		try{
			if(idPengguna == null) {
				SIAUser user = (SIAUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				idPengguna = user.getUserDetail().getIdPengguna();
			}
			List<PeranPengguna> userRolesFound = peranPenggunaService.getByParam("where pengguna.idPengguna = '" + idPengguna.toString() + "' and peran.idPeran = '" + idPeran.toString() + "'");
			List<SatMan> results = new ArrayList<SatMan>();
			for(PeranPengguna peranPengguna: userRolesFound) {
				results.add(new SatMan(peranPengguna.getSatMan().getIdSatMan().toString(), peranPengguna.getSatMan().getNmSatMan()));
			}
			return results.toArray(new SatMan[results.size()]);
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
 	}
	
	@RequestMapping(value = "/getRoles", method = RequestMethod.POST)
	public @ResponseBody Role[] getRoles(@RequestParam("idPengguna") UUID idPengguna) {
		try {
			List<Role> roleList = new ArrayList<Role>();
			for(Object obj: this.basicDAO.getObjects("select distinct pp.peran from PeranPengguna pp where pp.pengguna.idPengguna = '" + idPengguna.toString() + "'")) {
				Peran temp = (Peran) obj;
				roleList.add(new Role(temp.getIdPeran(), temp.getNamaPeran()));
			}
			return roleList.toArray(new Role[roleList.size()]);
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping(value = "/chooseUserRole/", method = RequestMethod.POST)
	public ModelAndView submitRole(HttpSession session, @RequestParam("idPeran") UUID idPeran, @RequestParam("idSatMan") UUID idSatMan) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			SIAUser user = (SIAUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Pengguna pengguna = user.getUserDetail();
			List<PeranPengguna> results = peranPenggunaService.getByParam("where pengguna.idPengguna = '" + pengguna.getIdPengguna() + "' and peran.idPeran = '" + idPeran.toString() + "' and satMan.idSatMan = '" + idSatMan.toString() + "'");
			if(results == null || results.size() == 0) throw new NullPointerException();
			PeranPengguna peranPengguna = results.get(0);
			session.setAttribute("userRoleSession", peranPengguna);
			if(peranPengguna.getPeran().getNamaPeran().equals("System Admin")) {
				session.setAttribute("moduleSession", AdministratorModuleManager.getInstance().getModules());
			} else {
				session.setAttribute("moduleSession", buildRoleModules(idPeran));
				session.setAttribute("modulUrlPrefix", "/modul");
			}
			modelAndView.setViewName("redirect:/home");
		} catch(NullPointerException npe) {
			npe.printStackTrace();
			session.setAttribute("userRoleFail", new Response(Response.error, "Pengguna tidak memiliki hak akses yang diinginkan", null));
			modelAndView.setViewName("redirect:/session/chooseUserRole");
		}
		return modelAndView;
	}
	
	private List<Modul> buildRoleModules(UUID roleId) {
		List<Modul> roleModules = new ArrayList<Modul>();
		List<MenuPeran> roleMenues = this.menuPeranService.getByParam("where peran.idPeran = '" + roleId + "'");
		for(MenuPeran mp: roleMenues) {
			Modul modulFound = null;
			for(Modul modul: roleModules) {
				if(mp.getMenu().getModul().getNamaModul().equals(modul.getNamaModul())) {
					modulFound = modul;
					break;
				}
			}
			if(modulFound == null) {
				Modul modul = mp.getMenu().getModul();
				modul.setMenus(new ArrayList<Menu>());
				modul.addMenu(mp.getMenu());
				roleModules.add(modul);
			} else {
				modulFound.addMenu(mp.getMenu());
			};
		}
		return roleModules;
	}
	
}
